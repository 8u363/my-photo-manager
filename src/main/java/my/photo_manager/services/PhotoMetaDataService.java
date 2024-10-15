package my.photo_manager.services;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photo_manager.model.metadata.PhotoMetaData;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Log4j2
public class PhotoMetaDataService {

    /**
     * build photo meta-data from photo file
     *
     * @param photoFile the photo file
     * @return the build meta-data
     */
    public PhotoMetaData buildPhotoMetaData(@NonNull File photoFile) {
        var longitude = getLongitude(photoFile);
        var latitude = getLatitude(photoFile);
        var addressJson = getAddressJsonFromOpenStreetMap(longitude, latitude);

        var photoMetaData = PhotoMetaData.builder()
                .withHeight(getHeight(photoFile))
                .withWidth(getWidth(photoFile))
                .withCreationTimeStamp(getCreationTimeStamp(photoFile))
                .withCountry(addressJson.optString("country", Strings.EMPTY))
                .withCity(addressJson.optString("city", Strings.EMPTY))
                .withPostCode(addressJson.optString("postcode", Strings.EMPTY))
                .withStreet(addressJson.optString("road", Strings.EMPTY))
                .withHouseNumber(addressJson.optString("house_number", Strings.EMPTY))
                .build();
        log.debug("build {}", kv("photoMetaData", photoMetaData));

        return photoMetaData;
    }

    protected int getHeight(@NonNull File photoFile) {
        var height = 0;

        try {
            var imageInfo = Imaging.getImageInfo(photoFile);
            height = imageInfo.getHeight();
            log.info("{} has {}", kv("photoFile", photoFile), kv("height", height));
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
        }

        return height;
    }

    protected int getWidth(@NonNull File photoFile) {
        var width = 0;

        try {
            var imageInfo = Imaging.getImageInfo(photoFile);
            width = imageInfo.getWidth();
            log.info("{} has {}", kv("photoFile", photoFile), kv("width", width));
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
        }

        return width;
    }

    protected String getCreationTimeStamp(@NonNull File photoFile) {
        var creationTimeStamp = Strings.EMPTY;

        try {
            var jpegImageMetadata = (JpegImageMetadata) Imaging.getMetadata(photoFile);
            if (jpegImageMetadata != null) {
                var exifValue = jpegImageMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
                if (exifValue != null) {
                    creationTimeStamp = exifValue.getStringValue();
                    log.info("{} has {}", kv("photoFile", photoFile), kv("creationTimeStamp", creationTimeStamp));
                }
            }
        } catch (ImageReadException | IOException ignored) {
        }

        return creationTimeStamp;
    }

    private double getLongitude(@NonNull File photoFile) {
        var longitude = 0.0;

        try {
            var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photoFile);
            if (jpegImageMetaData != null) {
                var exifData = jpegImageMetaData.getExif();
                if (exifData != null) {
                    var gpsInfo = exifData.getGPS();
                    if (gpsInfo != null) {
                        longitude = gpsInfo.getLongitudeAsDegreesEast();
                        log.info("{} has {}", kv("photoFile", photoFile), kv("longitude", longitude));
                    }
                }
            }
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
        }

        return longitude;
    }

    private double getLatitude(@NonNull File photoFile) {
        var latitude = 0.0;

        try {
            var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photoFile);
            if (jpegImageMetaData != null) {
                var exifData = jpegImageMetaData.getExif();
                if (exifData != null) {
                    var gpsInfo = exifData.getGPS();
                    if (gpsInfo != null) {
                        latitude = gpsInfo.getLatitudeAsDegreesNorth();
                        log.info("{} has {}", kv("photoFile", photoFile), kv("latitude", latitude));
                    }
                }
            }
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
        }

        return latitude;
    }

    private JSONObject getAddressJsonFromOpenStreetMap(double longitude, double latitude) {
        var addressJson = new JSONObject();
        var openStreetMapURL = "https://nominatim.openstreetmap.org/reverse.php?lat=" + latitude + "&lon=" + longitude
                + "&format=jsonv2";
        try {
            var jsonResponse = new JSONObject(IOUtils.toString(new URL(openStreetMapURL), StandardCharsets.UTF_8));
            if (jsonResponse.has("address")) {
                addressJson = jsonResponse.getJSONObject("address");
            }
        } catch (IOException ignored) {
        }

        return addressJson;
    }
}

package my.photoapi.service.metadataservice;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Log4j2
public class MetaDataService implements IMetaDataService {

    @Override
    public MetaData createMetaData(@NonNull Path photoFilePath) {
        log.debug("create meta data from {}", kv("photoFilePath", photoFilePath));

        var metaData = MetaData.builder(getHeight(photoFilePath.toFile()), getWidth(photoFilePath.toFile()), getCreationTimeStamp(photoFilePath.toFile()))
                .withLongitude(getLongitude(photoFilePath.toFile()))
                .withLatitude(getLatitude(photoFilePath.toFile()))
                .build();

        log.info("created {}", kv("meta data", metaData));
        return metaData;
    }

    private int getHeight(@NonNull File photo) {
        var height = 0;

        try {
            var imageInfo = Imaging.getImageInfo(photo);
            height = imageInfo.getHeight();
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
            // ignore the exception and return a height of 0
        }

        log.debug("{} has {}", kv("photo", photo), kv("height", height));
        return height;
    }

    private int getWidth(@NonNull File photo) {
        var width = 0;

        try {
            var imageInfo = Imaging.getImageInfo(photo);
            width = imageInfo.getWidth();
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
            // ignore the exception and return a width of 0
        }

        log.debug("{} has {}", kv("photo", photo), kv("width", width));
        return width;
    }

    private String getCreationTimeStamp(@NonNull File photo) {
        var creationTimeStamp = Strings.EMPTY;

        try {
            var jpegImageMetadata = (JpegImageMetadata) Imaging.getMetadata(photo);
            if (jpegImageMetadata != null) {
                var exifValue = jpegImageMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
                if (exifValue != null) {
                    creationTimeStamp = exifValue.getStringValue();
                }
            }
        } catch (ImageReadException | IOException ignored) {
            // ignore the exception and return an empty string
        }

        log.debug("{} has {}", kv("photo", photo), kv("creationTimeStamp", creationTimeStamp));
        return creationTimeStamp;
    }

    private double getLongitude(@NonNull File photo) {
        var longitude = 0.0;

        try {
            var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photo);
            if (jpegImageMetaData != null) {
                var exifData = jpegImageMetaData.getExif();
                if (exifData != null) {
                    var gpsInfo = exifData.getGPS();
                    if (gpsInfo != null) {
                        longitude = gpsInfo.getLongitudeAsDegreesEast();
                    }
                }
            }
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
            // ignore the exception and return a longitude of 0
        }

        log.debug("{} has {}", kv("photo", photo), kv("longitude", longitude));
        return longitude;
    }

    private double getLatitude(@NonNull File photo) {
        var latitude = 0.0;

        try {
            var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photo);
            if (jpegImageMetaData != null) {
                var exifData = jpegImageMetaData.getExif();
                if (exifData != null) {
                    var gpsInfo = exifData.getGPS();
                    if (gpsInfo != null) {
                        latitude = gpsInfo.getLatitudeAsDegreesNorth();
                    }
                }
            }
        } catch (IllegalArgumentException | IOException | ImageReadException ignored) {
            // ignore the exception and return a latitude of 0
        }

        log.debug("{} has {}", kv("photo", photo), kv("latitude", latitude));
        return latitude;
    }
}

package my.photoapi.service.metadataservice;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
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
    public MetaData createMetaDataFromPhotoFile(@NonNull Path photoFilePath) {
        log.debug("create meta data from {}", kv("photoFilePath", photoFilePath));

        var photoHeight = readHeightFromPhotoMetaData(photoFilePath.toFile());
        var photoWidth = readWidthPhotoFromPhotoMetaData(photoFilePath.toFile());
        var photoCreationTimeStamp = readCreationTimeStampFromPhotoMetaData(photoFilePath.toFile());

        var metaData = MetaData.builder(photoHeight, photoWidth, photoCreationTimeStamp)
                .withLongitude(readLongitudeFromPhotoMetaData(photoFilePath.toFile()))
                .withLatitude(readLatitudeFromPhotoMetaData(photoFilePath.toFile()))
                .build();

        log.info("created {}", kv("meta data", metaData));
        return metaData;
    }

    private int readHeightFromPhotoMetaData(@NonNull File photo) {
        var height = 0;

        try {
            var imageInfo = Imaging.getImageInfo(photo);
            height = imageInfo.getHeight();
        } catch (IllegalArgumentException | IOException  e) {
            log.info("an exception occurred {}. will return 0 as height", e.getMessage());
        }

        log.debug("{} has {}", kv("photo", photo), kv("height", height));
        return height;
    }

    private int readWidthPhotoFromPhotoMetaData(@NonNull File photo) {
        var width = 0;

        try {
            var imageInfo = Imaging.getImageInfo(photo);
            width = imageInfo.getWidth();
        } catch (IllegalArgumentException | IOException e) {
            log.info("an exception occurred {}. will return 0 as width", e.getMessage());
        }

        log.debug("{} has {}", kv("photo", photo), kv("width", width));
        return width;
    }

    private String readCreationTimeStampFromPhotoMetaData(@NonNull File photo) {
        var creationTimeStamp = Strings.EMPTY;

        try {
            var jpegImageMetadata = (JpegImageMetadata) Imaging.getMetadata(photo);
            if (jpegImageMetadata != null) {
                var exifValue = jpegImageMetadata.findExifValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
                if (exifValue != null) {
                    creationTimeStamp = exifValue.getStringValue();
                }
            }
        } catch (IllegalArgumentException | IOException e) {
            log.info("an exception occurred {}. will return empty string as creation time stamp", e.getMessage());
        }

        log.debug("{} has {}", kv("photo", photo), kv("creationTimeStamp", creationTimeStamp));
        return creationTimeStamp;
    }

    private double readLongitudeFromPhotoMetaData(@NonNull File photo) {
        var longitude = 0.0;

        try {
            var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photo);
            if (jpegImageMetaData != null) {
                var exifData = jpegImageMetaData.getExif();
                if (exifData != null) {
                    var gpsInfo = exifData.getGpsInfo();
                    if (gpsInfo != null) {
                        longitude = gpsInfo.getLongitudeAsDegreesEast();
                    }
                }
            }
        } catch (IllegalArgumentException | IOException  ignored) {
            // ignore the exception and return a longitude of 0
        }

        log.debug("{} has {}", kv("photo", photo), kv("longitude", longitude));
        return longitude;
    }

    private double readLatitudeFromPhotoMetaData(@NonNull File photo) {
        var latitude = 0.0;

        try {
            var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photo);
            if (jpegImageMetaData != null) {
                var exifData = jpegImageMetaData.getExif();
                if (exifData != null) {
                    var gpsInfo = exifData.getGpsInfo();
                    if (gpsInfo != null) {
                        latitude = gpsInfo.getLatitudeAsDegreesNorth();
                    }
                }
            }
        } catch (IllegalArgumentException | IOException  ignored) {
            // ignore the exception and return a latitude of 0
        }

        log.debug("{} has {}", kv("photo", photo), kv("latitude", latitude));
        return latitude;
    }
}

package my.photomanager.photo.metadata;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata.GpsInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.webp.WebPImageMetadata;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MetaDataService {

    /**
     * create a MetaData object of the photo file
     * 
     * @param photoFile
     * @return the created meta data object
     * @throws IOException
     */
    public MetaData createMetaData(File photoFile) throws IOException {
        log.debug("create meta data object of {}", kv("photo file", photoFile.getAbsolutePath()));

        var photoHeight = getPhotoHeight(photoFile);
        var photoWidth = getPhotoWidth(photoFile);
        var photoCreationTimeStamp = getPhotoCreationDate(photoFile);
        var photoGpsLongitude = getPhotoGpsLongitude(photoFile);
        var photoGpsLatitude = getPhotoGpsLatitude(photoFile);
        var metaData = new MetaData(photoWidth, photoHeight, photoCreationTimeStamp,
                photoGpsLongitude, photoGpsLatitude);

        log.info("created {}", kv("metadata object", metaData));
        return metaData;
    }

    /**
     * read the photo height of the image info object
     * 
     * @param photoFile
     * @return the photo height
     * @throws IOException
     */
    private int getPhotoHeight(@NonNull File photoFile) throws IOException {
        var photoHeight = 0;

        try {
            ImageInfo imageInfo = Imaging.getImageInfo(photoFile);
            photoHeight = imageInfo.getHeight();
        } catch (IllegalArgumentException e) {
            log.warn("can not read photo height beacuse of {}", kv("exception", e.getMessage()));
        }

        return photoHeight;
    }

    /**
     * read the photo width of the image info object
     * 
     * @param photoFile
     * @return the photo width
     * @throws IOException
     */
    private int getPhotoWidth(@NonNull File photoFile) throws IOException {
        var photoWidth = 0;

        try {
            ImageInfo imageInfo = Imaging.getImageInfo(photoFile);
            photoWidth = imageInfo.getWidth();
        } catch (IllegalArgumentException e) {
            log.warn("can not read photo width beacuse of {}", kv("exception", e.getMessage()));
        }

        return photoWidth;
    }

    /**
     * read the photo creation date of the exif data
     * 
     * @param photoFile
     * @return the photo creation date
     * @throws IOException
     */
    private LocalDate getPhotoCreationDate(@NonNull File photoFile) throws IOException {
        var photoCreationDate = LocalDate.of(1900, 1, 1);
        var exifData = getExifDataIfExists(photoFile);

        if (exifData.isEmpty()) {
            log.warn("can not read photo creation date because photo file contains no exif data");
        } else {
            var creationTimeStampText =
                    exifData.get().findField(TiffTagConstants.TIFF_TAG_DATE_TIME).getStringValue();
            var matchResult =
                    Pattern.compile("(\\d{4}):(\\d{2}):(\\d{2})").matcher(creationTimeStampText);

            if (matchResult.find()) {
                var year = Integer.parseInt(matchResult.group(1));
                var month = Integer.parseInt(matchResult.group(2));
                var day = Integer.parseInt(matchResult.group(3));
                photoCreationDate = LocalDate.of(year, month, day);
            }
        }

        return photoCreationDate;
    }

    /**
     * read the photo gps longitude of the exif data
     * 
     * @param photoFile
     * @return the photo gps longitude
     * @throws IOException
     */
    private double getPhotoGpsLongitude(@NonNull File photoFile) throws IOException {
        var photoGpsLongitude = 0d;
        var exifData = getExifDataIfExists(photoFile);

        if (exifData.isEmpty()) {
            log.warn("can not read photo gps longitude because photo file contains no exif data");
        } else {
            GpsInfo gpsInfo = exifData.get().getGpsInfo();
            photoGpsLongitude = gpsInfo.getLongitudeAsDegreesEast();
        }

        return photoGpsLongitude;
    }

    /**
     * read the photo gps latitude of the exif data
     * 
     * @param photoFile
     * @return the photo gps latitude
     * @throws IOException
     */
    private double getPhotoGpsLatitude(@NonNull File photoFile) throws IOException {
        var photoGpsLatitude = 0d;
        var exifData = getExifDataIfExists(photoFile);

        if (exifData.isEmpty()) {
            log.warn("can not read photo gps latitude because photo file contains no exif data");
        } else {
            GpsInfo gpsInfo = exifData.get().getGpsInfo();
            photoGpsLatitude = gpsInfo.getLatitudeAsDegreesNorth();
        }

        return photoGpsLatitude;
    }

    /**
     * read the photo exif data
     * 
     * @param photoFile
     * @return the photo exif data
     * @throws IOException
     */
    private Optional<TiffImageMetadata> getExifDataIfExists(@NonNull File photoFile)
            throws IOException {
        if (photoFile.getAbsolutePath().toLowerCase().endsWith("jpg")) {
            return getJpegExifDataIfExists(photoFile);
        }

        if (photoFile.getAbsolutePath().toLowerCase().endsWith("webp")) {
            return getWepExifDataIfExists(photoFile);
        }

        return Optional.empty();
    }

    /**
     * read the exif data of a jpeg photo
     * 
     * @param photoFile
     * @return exif data
     * @throws IOException
     */
    @SneakyThrows(IllegalArgumentException.class)
    private Optional<TiffImageMetadata> getJpegExifDataIfExists(@NonNull File photoFile)
            throws IOException {

        JpegImageMetadata jpegImageMetadata = (JpegImageMetadata) Imaging.getMetadata(photoFile);
        if (jpegImageMetadata != null) {
            return Optional.ofNullable(jpegImageMetadata.getExif());
        }

        return Optional.empty();
    }

    /**
     * read the exif data of a wep photo
     * 
     * @param photoFile
     * @return the exif data
     * @throws IOException
     */
    @SneakyThrows(IllegalArgumentException.class)
    private Optional<TiffImageMetadata> getWepExifDataIfExists(@NonNull File photoFile)
            throws IOException {

        WebPImageMetadata imageMetaData = (WebPImageMetadata) Imaging.getMetadata(photoFile);

        if (imageMetaData != null) {
            return Optional.ofNullable(imageMetaData.getExif());
        }

        return Optional.empty();
    }
}

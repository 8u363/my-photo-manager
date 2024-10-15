package my.photo_manager;

import my.photo_manager.model.metadata.PhotoMetaData;
import org.apache.logging.log4j.util.Strings;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    // TEST DATA

    public static Path TEST_PHOTO_PATH_WITH_EXIF_DATA = Paths.get("src", "test", "resources", "TestPhotos", "TestPhotoWithExifData.jpg");
    public static Path TEST_PHOTO_PATH_WITHOUT_EXIF_DATA = Paths.get("src", "test", "resources", "TestPhotos", "TestPhotoWithoutExifData.jpg");
    public static Path TEST_IMAGE_PATH = Paths.get("src", "test", "resources", "TestPhotos", "TestImage.jpg");

    public final static String TEST_HASH_VALUE = "TestHashValue";
    public final static String TEST_FILE_PATH = "TestFilePath";

    public final static PhotoMetaData META_DATA_OF_PHOTO_WITH_EXIF_DATA = PhotoMetaData.builder()
            .withHeight(4291)
            .withWidth(3218)
            .withCreationTimeStamp("2016:12:02 11:10:20")
            .withCountry("United States")
            .withCity("City of New York")
            .withPostCode("10038")
            .withStreet("Saint James Place")
            .withHouseNumber("5")
            .build();
    public final static PhotoMetaData META_DATA_OF_PHOTO_WITHOUT_EXIF_DATA =  PhotoMetaData.builder()
            .withHeight(4291)
            .withWidth(3218)
            .withCreationTimeStamp(Strings.EMPTY)
            .withCountry(Strings.EMPTY)
            .withCity(Strings.EMPTY)
            .withPostCode(Strings.EMPTY)
            .withStreet(Strings.EMPTY)
            .withHouseNumber(Strings.EMPTY)
            .build();
    public final static PhotoMetaData META_DATA_OF_IMAGE = PhotoMetaData.builder()
            .withHeight(2160)
            .withWidth(3840)
            .withCreationTimeStamp("2023:11:05 10:28:05")
            .withCountry(Strings.EMPTY)
            .withCity(Strings.EMPTY)
            .withPostCode(Strings.EMPTY)
            .withStreet(Strings.EMPTY)
            .withHouseNumber(Strings.EMPTY)
            .build();

    public static PhotoMetaData buildMetaDataWithGermanyLocation(int height, int width, String creationTimeStamp) {
        return PhotoMetaData.builder()
                .withHeight(height)
                .withWidth(width)
                .withCreationTimeStamp(creationTimeStamp)
                .withCountry("Germany")
                .withCity("Berlin")
                .withStreet("Alexanderplatz")
                .withHouseNumber("1")
                .build();
    }

    public static PhotoMetaData buildMetaDataWithUSALocation(int height, int width, String creationTimeStamp) {
        return PhotoMetaData.builder()
                .withHeight(height)
                .withWidth(width)
                .withCreationTimeStamp(creationTimeStamp)
                .withCountry("United States")
                .withCity("City of New York")
                .withPostCode("10038")
                .withStreet("Saint James Place")
                .withHouseNumber("5")
                .build();
    }
}

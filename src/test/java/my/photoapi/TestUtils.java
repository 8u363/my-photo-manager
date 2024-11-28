package my.photoapi;

import my.photoapi.service.locationservice.Location;
import my.photoapi.service.metadataservice.MetaData;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TestUtils {

    public final static String TEST_HASH_VALUE = "TestHashValue";
    public final static String TEST_FILE_PATH = "TestFilePath";

    public final static Path TEST_FOLDER_PATH = Path.of("src", "test", "resources", "TestPhotos");

    public static Stream<Path> getPhotoFiles() {
        return Stream.concat(getPhotoFilesWithMetaData(), getPhotoFilesWithoutMetaData());
    }

    public static Stream<Path> getPhotoFilesWithMetaData() {
        return Stream.of(
                TEST_FOLDER_PATH.resolve("Brandenburg_Gate_with_metadata.webp"),
                TEST_FOLDER_PATH.resolve("Brandenburg_Gate_with_metadata.jpg")
        );
    }

    public static Stream<Path> getPhotoFilesWithoutMetaData() {
        return Stream.of(
                TEST_FOLDER_PATH.resolve("Brandenburg_Gate_no_metadata.webp"),
                TEST_FOLDER_PATH.resolve("Brandenburg_Gate_no_metadata.jpg")
        );
    }

    public static Location getDefaultLocation() {
        return Location.builder()
                .withCountry("Germany")
                .withCity("Berlin")
                .withPostalCode("10117")
                .withStreet("Pariser Platz")
                .build();
    }

    public static MetaData getDefaultMetaData() {
        return MetaData.builder(1024, 768, LocalDateTime.now().toString()).build();
    }
}

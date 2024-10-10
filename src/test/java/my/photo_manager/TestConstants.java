package my.photo_manager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestConstants {

    // Test parameter
    public static Path testPhotoWithExifDataPath = Paths.get("src", "test", "resources", "TestPhotos", "TestPhotoWithExifData.jpg");
    public static Path testPhotoWithoutExifDataPath = Paths.get("src", "test", "resources", "TestPhotos", "TestPhotoWithoutExifData.jpg");
    public static Path testImagePath = Paths.get("src", "test", "resources", "TestPhotos", "TestImage.jpg");

}

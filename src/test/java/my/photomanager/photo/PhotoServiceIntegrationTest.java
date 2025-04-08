package my.photomanager.photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PhotoServiceIntegrationTest {

    // TODO will be done in #41
    /*
     * private final PhotoService photoFactory = new PhotoService();
     * 
     * static Stream<File> getPhotoFilesWithExifData() { return Stream.of( Path.of("src", "test",
     * "resources", "TestFiles", "Berlin_WithExifData.jpg") .toFile(), Path.of("src", "test",
     * "resources", "TestFiles", "Berlin_WithExifData.webp") .toFile()); }
     * 
     * @ParameterizedTest
     * 
     * @MethodSource("getPhotoFilesWithExifData") void
     * shouldCreatePhotoObjectOfPhotoFileWithExifData(File photoFile) throws IOException { var photo
     * = photoFactory.createPhoto(photoFile);
     * 
     * assertThat(photo).isNotNull(); assertThat(photo.getHeight()).isEqualTo(PHOTO_HEIGHT);
     * assertThat(photo.getWidth()).isEqualTo(PHOTO_WIDTH);
     * assertThat(photo.getCreationDate()).isEqualTo(LocalDate.of(2025, 1, 1));
     * assertThat(photo.getCountry()).isEqualTo("Deutschland");
     * assertThat(photo.getCity()).isEqualTo("Berlin");
     * assertThat(photo.getPostalCode()).isEqualTo("10557");
     * assertThat(photo.getRoad()).isEqualTo("Platz der Republik"); }
     * 
     * static Stream<File> getPhotoFilesWithoutExifData() { return Stream.of( Path.of("src", "test",
     * "resources", "TestFiles", "Berlin_WithoutExifData.jpg") .toFile(), Path.of("src", "test",
     * "resources", "TestFiles", "Berlin_WithoutExifData.webp") .toFile()); }
     * 
     * @ParameterizedTest
     * 
     * @MethodSource("getPhotoFilesWithoutExifData") void
     * shouldCreatePhotoObjectOfPhotoFileWithoutExifData(File photoFile) throws IOException { var
     * photo = photoFactory.createPhoto(photoFile);
     * 
     * assertThat(photo).isNotNull(); assertThat(photo.getHeight()).isEqualTo(PHOTO_HEIGHT);
     * assertThat(photo.getWidth()).isEqualTo(1024);
     * assertThat(photo.getCreationDate()).isEqualTo(LocalDate.of(1900, 1, 1));
     * assertThat(photo.getCountry()).isEmpty(); assertThat(photo.getCity()).isEmpty();
     * assertThat(photo.getPostalCode()).isEmpty(); assertThat(photo.getRoad()).isEmpty(); }
     */
}

package my.photomanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import my.photomanager.photo.Photo;

@ExtendWith(SpringExtension.class)
class ThumbnailServiceTest {

    private ThumbnailService thumbnailService;

    @BeforeEach
    void setup() {
        thumbnailService = new ThumbnailService();
    }

    @Test
    void shouldCreateBase64OfPhoto() {
        var testHashValue = "abc";
        var testPhoto = Photo
                .builder(Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile().getAbsolutePath(), testHashValue)
                .build();

        var thumbnailBase64 = thumbnailService.getThumbnailBase64OfPhoto(testPhoto);

        assertThat(thumbnailBase64).isNotNull();
        assertThat(Path.of("thumbnails", testHashValue + ".jpg").toFile()).exists();
    }

    @Test
    void shouldCreateBase64OfCachedThumbnail() throws IOException {
        var testHashValue = "abc";

        Files.delete(Path.of("thumbnails", testHashValue + ".jpg"));
        Files.createFile(Path.of("thumbnails", testHashValue + ".jpg"));

        var testPhoto = Photo
                .builder(Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile().getAbsolutePath(), testHashValue)
                .build();

        var thumbnailBase64 = thumbnailService.getThumbnailBase64OfPhoto(testPhoto);
        assertThat(thumbnailBase64).isNotNull();
        assertThat(Path.of("thumbnails", testHashValue + ".jpg").toFile()).exists();

        Files.delete(Path.of("thumbnails", testHashValue + ".jpg"));
    }

}

package my.photomanager.thumbnail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static my.photomanager.TestUtils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileSystemUtils;
import lombok.SneakyThrows;
import my.photomanager.config.Config;
import my.photomanager.photo.Photo;

@ExtendWith(SpringExtension.class)
class ThumbnailServiceTest {

    private ThumbnailService thumbnailService;

    @Mock
    private Config configuration;

    @BeforeEach
    void setup() {
        thumbnailService = new ThumbnailService(configuration);
    }

    @AfterEach
    @SneakyThrows(IOException.class)
    void cleanup() {
        FileSystemUtils.deleteRecursively(Path.of(THUMBAIL_FOLDER));
    }

    @Test
    void shouldCreateBase64OfPhoto() {
        when(configuration.getThumbnailFolder()).thenReturn(THUMBAIL_FOLDER);
        when(configuration.getThumbnailWidth()).thenReturn(THUMBNAIL_WIDTH);

        var testHashValue = PHOTO_HASH_VALUE;
        var testPhoto = Photo
                .builder(Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile().getAbsolutePath(), testHashValue)
                .build();
        var thumbnailBase64 = thumbnailService.createThumbnailBase64OfPhoto(testPhoto);

        assertThat(thumbnailBase64).isNotNull();
        assertThat(Path.of(THUMBAIL_FOLDER, testHashValue + ".jpg").toFile()).exists();
    }

    @Test
    void shouldNotCreateBase64OfPhotoIfCachedThumbnailExistsAlready() throws IOException {
        when(configuration.getThumbnailFolder()).thenReturn(THUMBAIL_FOLDER);

        Files.createDirectories(Path.of(THUMBAIL_FOLDER));
        Files.createFile(Path.of(THUMBAIL_FOLDER, PHOTO_HASH_VALUE + ".jpg"));

        var testPhoto = Photo
                .builder(Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile().getAbsolutePath(), PHOTO_HASH_VALUE)
                .build();
        var thumbnailBase64 = thumbnailService.createThumbnailBase64OfPhoto(testPhoto);

        assertThat(thumbnailBase64).isNotNull();
        assertThat(Path.of(THUMBAIL_FOLDER, PHOTO_HASH_VALUE + ".jpg").toFile()).exists();
    }

}

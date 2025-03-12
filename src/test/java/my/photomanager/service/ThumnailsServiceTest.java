package my.photomanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
import my.photomanager.configuration.PhotoManagerConfiguration;
import my.photomanager.photo.Photo;

@ExtendWith(SpringExtension.class)
class ThumbnailServiceTest {

    private final String THUMBAIL_FOLDER = "testThumbnails";

    private ThumbnailService thumbnailService;

    @Mock
    private PhotoManagerConfiguration configuration;

    @BeforeEach
    void setup() {
        thumbnailService = new ThumbnailService(configuration);
    }

    @AfterEach
    void cleanup() throws IOException {
        FileSystemUtils.deleteRecursively(Path.of(THUMBAIL_FOLDER));
    }

    @Test
    void shouldCreateBase64OfPhoto() {
        when(configuration.getThumbnailFolder()).thenReturn(THUMBAIL_FOLDER);
        when(configuration.getThumbnailWidth()).thenReturn(150);

        var testHashValue = "abc";
        var testPhoto = Photo
                .builder(Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile().getAbsolutePath(), testHashValue)
                .build();

        var thumbnailBase64 = thumbnailService.getThumbnailBase64OfPhoto(testPhoto);

        assertThat(thumbnailBase64).isNotNull();
        assertThat(Path.of(THUMBAIL_FOLDER, testHashValue + ".jpg").toFile()).exists();
    }

    @Test
    void shouldCreateBase64OfCachedThumbnail() throws IOException {
        when(configuration.getThumbnailFolder()).thenReturn(THUMBAIL_FOLDER);

        var testHashValue = "abc";

        Files.createDirectories(Path.of(THUMBAIL_FOLDER));
        Files.createFile(Path.of(THUMBAIL_FOLDER, testHashValue + ".jpg"));

        var testPhoto = Photo
                .builder(Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile().getAbsolutePath(), testHashValue)
                .build();

        var thumbnailBase64 = thumbnailService.getThumbnailBase64OfPhoto(testPhoto);
        assertThat(thumbnailBase64).isNotNull();
        assertThat(Path.of(THUMBAIL_FOLDER, testHashValue + ".jpg").toFile()).exists();
    }

}

package my.photomanager.photo.metadata;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DefaultMetaDataFactoryTest {

    private final DefaultMetaDataFactory metaDataFactory = new DefaultMetaDataFactory();

    private final int PHOTO_HEIGHT = 768;
    private final int PHOTO_WIDTH = 1024;

    static Stream<File> getPhotoFilesWithExifData() {
        return Stream.of(
                Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.jpg")
                        .toFile(),
                Path.of("src", "test", "resources", "TestFiles", "Berlin_WithExifData.webp")
                        .toFile());
    }

    @ParameterizedTest
    @MethodSource("getPhotoFilesWithExifData")
    void shouldCreateMetaDataObjectOfPhotoFileWithExifData(File photoFile) throws IOException {
        var metaData = metaDataFactory.createMetaData(photoFile);

        assertThat(metaData).isNotNull();
        assertThat(metaData.height()).isEqualTo(PHOTO_HEIGHT);
        assertThat(metaData.width()).isEqualTo(PHOTO_WIDTH);
        assertThat(metaData.creationDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(metaData.longitude()).isEqualTo(13.376194444444446);
        assertThat(metaData.latitude()).isEqualTo(52.518680555555555);
    }

    static Stream<File> getPhotoFilesWithoutExifData() {
        return Stream.of(
                Path.of("src", "test", "resources", "TestFiles", "Berlin_WithoutExifData.jpg")
                        .toFile(),
                Path.of("src", "test", "resources", "TestFiles", "Berlin_WithoutExifData.webp")
                        .toFile());
    }

    @ParameterizedTest
    @MethodSource("getPhotoFilesWithoutExifData")
    void shouldCreateMetaDataObjectOfPhotoFileWithoutExifData(File photoFile) throws IOException {
        var metaData = metaDataFactory.createMetaData(photoFile);

        assertThat(metaData).isNotNull();
        assertThat(metaData.height()).isEqualTo(PHOTO_HEIGHT);
        assertThat(metaData.width()).isEqualTo(PHOTO_WIDTH);
        assertThat(metaData.creationDate()).isEqualTo(LocalDate.of(1900, 1, 1));
        assertThat(metaData.longitude()).isZero();
        assertThat(metaData.latitude()).isZero();
    }

    @Test
    void shouldCreateEmptyMetaDataObjectOfNonPhotoFile() throws IOException {
        var metaData = metaDataFactory.createMetaData(
                Path.of("src", "test", "resources", "TestFiles", "TextFile.txt").toFile());

        assertThat(metaData).isNotNull();
        assertThat(metaData.height()).isZero();
        assertThat(metaData.width()).isZero();
        assertThat(metaData.creationDate()).isEqualTo(LocalDate.of(1900, 1, 1));
        assertThat(metaData.longitude()).isZero();
        assertThat(metaData.latitude()).isZero();
    }
}

package my.photoapi.service.fileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static my.photoapi.TestUtils.TEST_FOLDER_PATH;
import static org.assertj.core.api.Assertions.assertThat;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setup() {
        fileService = new FileService();
    }

    @Test
    void getPhotosFromSourceFolder() {
        var photos = fileService.getPhotosFromSource(TEST_FOLDER_PATH);

        assertThat(photos).isNotNull();
        assertThat(photos).isNotEmpty();
    }

    @Test
    void getJpegPhotosFromSourceFolder(){
        var jpegPhotos = fileService.getPhotosFromSource(TEST_FOLDER_PATH, true, List.of("jpg"));

        assertThat(jpegPhotos).isNotNull();
        assertThat(jpegPhotos).isNotEmpty();
        assertThat(jpegPhotos.stream().filter(photo -> photo.getName().endsWith("jpg")).collect(Collectors.toList()).size()).isEqualTo(jpegPhotos.size());
    }
}
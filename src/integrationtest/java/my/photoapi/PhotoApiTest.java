package my.photoapi;

import my.photoapi.service.fileService.FileService;
import my.photoapi.service.photoservice.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static my.photoapi.TestUtils.TEST_FOLDER_PATH;

@SpringBootTest
public class PhotoApiTest {

    private FileService fileService;
    private PhotoService photoService;

    @BeforeEach
    void setup() {
        var photos = fileService.getPhotosFromSource(TEST_FOLDER_PATH);
        photos.stream().forEach(photo -> photoService.createPhoto(photo.toPath()));
    }

    @Test
    void filterPhotosByLabels() {
        //throw new UnsupportedOperationException("Not yet implemented");

    }
}

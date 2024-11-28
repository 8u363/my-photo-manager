package my.photoapi.service.photoService;

import my.photoapi.repository.PhotoRepository;
import my.photoapi.service.locationservice.OpenMapService;
import my.photoapi.service.metadataservice.MetaDataService;
import my.photoapi.service.photoservice.PhotoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static my.photoapi.TestUtils.TEST_FOLDER_PATH;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
class PhotoServiceTest {

    private PhotoService photoService;
    @Autowired
    private PhotoRepository repository;
    @Autowired
    private MetaDataService metaDataService;
    @Autowired
    private OpenMapService locationService;

    @BeforeEach
    void setup() {
        photoService = new PhotoService(repository, metaDataService, locationService);
    }

    @AfterEach
    void cleanup(){
        repository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("my.photoapi.TestUtils#getPhotoFiles")
    void savePhotoObjectIfNotExistsAlready() {
        // when
        var photo = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("Brandenburg_Gate_with_metadata.jpg"));

        // then
        assertThat(photo).isNotNull();
        assertThat(photo.getID()).isNotZero();
        assertThat(photo.getFilePath()).isNotEmpty();
        assertThat(photo.getHashValue()).isNotEmpty();
        assertThat(photo.getLabels()).isNotEmpty();
    }

    @Test
    void notSavePhotoObjectWhenExistsAlready() {
        // when
        var firstPhoto = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("Brandenburg_Gate_with_metadata.jpg"));
        var secondPhoto = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("Brandenburg_Gate_with_metadata.jpg"));

        // then
        assertThat(firstPhoto).isNotNull();
        assertThat(secondPhoto).isNotNull();
        assertThat(firstPhoto.getID()).isEqualTo(secondPhoto.getID());
    }


    @Test
    void getAllPhotosAtOnePage() {
        // given
        var photoLondon = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("London_landscape.jpeg"));
        var photoParis = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("Paris_landscape.jpeg"));
        var photoNewYork = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("New_York_landscape.jpeg"));

        // when
        var firstPage = photoService.getPhotos(0, 10);
        var secondPage = photoService.getPhotos(1, 10);

        // then
        assertThat(firstPage).isNotNull();
        assertThat(firstPage.getContent().size()).isEqualTo(3);
        assertThat(secondPage).isNotNull();
        assertThat(secondPage.getContent().size()).isEqualTo(0);

    }

    @Test
    void getPhotosOnDifferentPage(){
        // given
        var photoLondon = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("London_landscape.jpeg"));
        var photoParis = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("Paris_landscape.jpeg"));
        var photoNewYork = photoService.savePhotoIfNotExists(TEST_FOLDER_PATH.resolve("New_York_landscape.jpeg"));

        // when
        var firstPage = photoService.getPhotos(0, 2);
        var secondPage = photoService.getPhotos(1, 2);

        // then
        assertThat(firstPage).isNotNull();
        assertThat(firstPage.getContent().size()).isEqualTo(2);
        assertThat(secondPage).isNotNull();
        assertThat(secondPage.getContent().size()).isEqualTo(1);

    }
}

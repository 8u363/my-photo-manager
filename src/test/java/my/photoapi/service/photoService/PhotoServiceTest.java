package my.photoapi.service.photoService;

import lombok.extern.log4j.Log4j2;
import my.photoapi.repository.PhotoRepository;
import my.photoapi.service.locationservice.OpenMapService;
import my.photoapi.service.metadataservice.MetaDataService;
import my.photoapi.service.photoservice.PhotoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static my.photoapi.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag(INTEGRATION_TEST)
@Log4j2
class PhotoServiceTest {

    private PhotoService photoService;
    @Autowired
    private PhotoRepository repository;
    @Autowired
    private MetaDataService metaDataService;
    @Autowired
    private OpenMapService locationService;

    @BeforeAll
    static void startTest() {
        log.info("start {}", PhotoServiceTest.class.getSimpleName());
    }

    @AfterAll
    static void finishTest() {
        log.info("finish {}", PhotoServiceTest.class.getSimpleName());
    }

    @BeforeEach
    void setup() {
        photoService = new PhotoService(repository, metaDataService, locationService);
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("my.photoapi.TestUtils#getPhotoFilesWithMetaData")
    void should_return_saved_photo_if_not_exists_already(Path photoFile) {
        // when
        var photo = photoService.savePhotoIfNotExists(photoFile);

        // then
        assertThat(photo).isNotNull();
        assertThat(photo.getID()).isNotZero();
        assertThat(photo.getFilePath()).isNotEmpty();
        assertThat(photo.getHashValue()).isNotEmpty();
        assertThat(photo.getLabels()).isNotEmpty();
    }

    @Test
    void should_return_existing_photo_when_try_to_save_again() {
        // when
        var firstPhoto = photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg")));
        var secondPhoto = photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg")));

        // then
        assertThat(firstPhoto).isNotNull();
        assertThat(secondPhoto).isNotNull();
        assertThat(firstPhoto.getID()).isEqualTo(secondPhoto.getID());
    }

    @Test
    void should_return_all_photos_in_one_page() {
        // given
        photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON);
        photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS);
        photoService.savePhotoIfNotExists(TEST_PHOTO_New_York);

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
    void should_return_all_photos_in_multiple_pages() {
        // given
        photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON);
        photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS);
        photoService.savePhotoIfNotExists(TEST_PHOTO_New_York);

        // when
        var firstPage = photoService.getPhotos(0, 2);
        var secondPage = photoService.getPhotos(1, 2);

        // then
        assertThat(firstPage).isNotNull();
        assertThat(firstPage.getContent().size()).isEqualTo(2);
        assertThat(secondPage).isNotNull();
        assertThat(secondPage.getContent().size()).isEqualTo(1);
    }


    @ParameterizedTest
    @CsvSource({"1024, 3"})
    void test_get_photos_by_page_filtered_by_labels(String labels, int expectedPageContent) {
        // given
        photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON);
        photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS);
        photoService.savePhotoIfNotExists(TEST_PHOTO_New_York);
        photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg")));

        // when
        var firstPage = photoService.getPhotos(0, 10, Arrays.stream(labels.split(",")).toList());

        // then
        assertThat(firstPage).isNotNull();
        assertThat(firstPage.getContent().size()).isEqualTo(2);

    }

    @Test
    void should_return_empty_page_when_labels_are_unknown() {
        // given
        photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON);
        photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS);
        photoService.savePhotoIfNotExists(TEST_PHOTO_New_York);
        photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg")));

        // when
        var firstPage = photoService.getPhotos(0, 10, List.of("unknown label"));

        // then
        assertThat(firstPage).isNotNull();
        assertThat(firstPage.getContent().size()).isEqualTo(0);
    }

    @Test
    void should_return_all_labels() {
        // given
        photoService.savePhotoIfNotExists(TEST_PHOTO_New_York);
        photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg")));

        // when
        var labels = photoService.getLabels();

        // then
        assertThat(labels).isNotNull();
        assertThat(labels.size()).isNotZero();
        //TODO check content of labels
    }
}

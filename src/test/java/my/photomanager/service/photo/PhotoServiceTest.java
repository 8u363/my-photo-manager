package my.photomanager.service.photo;

import static my.photomanager.TestConstants.INTEGRATION_TEST;
import static my.photomanager.TestConstants.TEST_PHOPTOS_PATH;
import static my.photomanager.TestConstants.TEST_PHOTO_LONDON;
import static my.photomanager.TestConstants.TEST_PHOTO_New_York;
import static my.photomanager.TestConstants.TEST_PHOTO_PARIS;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import my.photomanager.repository.PhotoRepository;
import my.photomanager.service.location.LocationService;
import my.photomanager.service.metaData.MetaDataService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
	private LocationService locationService;

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
	@MethodSource("my.photomanager.TestConstants#getPhotoFilesWithMetaData")
	void should_return_saved_photo_if_not_exists_already(Path photoFilePath) {
		// when
		var photo = photoService.savePhotoIfNotExists(photoFilePath.toFile());

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
		var firstPhoto = photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg"))
				.toFile());
		var secondPhoto = photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg"))
				.toFile());

		// then
		assertThat(firstPhoto).isNotNull();
		assertThat(secondPhoto).isNotNull();
		assertThat(firstPhoto.getID()).isEqualTo(secondPhoto.getID());
	}

	@Test
	void should_return_all_photos_in_one_page() {
		// given
		photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_New_York.toFile());

		// when
		var firstPage = photoService.getPhotos(0, 10);
		var secondPage = photoService.getPhotos(1, 10);

		// then
		assertThat(firstPage).isNotNull();
		assertThat(firstPage.getContent()
				.size()).isEqualTo(3);
		assertThat(secondPage).isNotNull();
		assertThat(secondPage.getContent()
				.size()).isEqualTo(0);
	}

	@Test
	void should_return_all_photos_in_multiple_pages() {
		// given
		photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_New_York.toFile());

		// when
		var firstPage = photoService.getPhotos(0, 2);
		var secondPage = photoService.getPhotos(1, 2);

		// then
		assertThat(firstPage).isNotNull();
		assertThat(firstPage.getContent()
				.size()).isEqualTo(2);
		assertThat(secondPage).isNotNull();
		assertThat(secondPage.getContent()
				.size()).isEqualTo(1);
	}


	@ParameterizedTest
	@CsvSource({"1024, 4", "768, 1"})
	void test_get_photos_by_page_filtered_by_labels(String labels, int expectedPageContentSize) {
		// given
		photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_New_York.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg"))
				.toFile());

		// when
		var firstPage = photoService.getPhotos(0, 10, Arrays.stream(labels.split(","))
				.toList());

		// then
		assertThat(firstPage).isNotNull();
		assertThat(firstPage.getContent()
				.size()).isEqualTo(expectedPageContentSize);

	}

	@Test
	void should_return_empty_page_when_labels_are_unknown() {
		// given
		photoService.savePhotoIfNotExists(TEST_PHOTO_LONDON.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_PARIS.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOTO_New_York.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg"))
				.toFile());

		// when
		var firstPage = photoService.getPhotos(0, 10, List.of("unknown label"));

		// then
		assertThat(firstPage).isNotNull();
		assertThat(firstPage.getContent()
				.size()).isEqualTo(0);
	}

	@Test
	void should_return_all_labels() {
		// given
		photoService.savePhotoIfNotExists(TEST_PHOTO_New_York.toFile());
		photoService.savePhotoIfNotExists(TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg"))
				.toFile());

		// when
		var labels = photoService.getLabels();

		// then
		assertThat(labels).isNotNull();
		assertThat(labels.size()).isNotZero();
		//TODO check content of labels
	}
}
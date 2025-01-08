package my.photomanager.v1.service;

import static my.photomanager.TestConstants.TEST_FILE_PATH;
import static my.photomanager.TestConstants.UNIT_TEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Tag(UNIT_TEST)
@Log4j2
class MetaDataServiceTest {

	private MetaDataService metaDataService;

	@BeforeAll
	static void startTest() {
		log.info("start {}", MetaDataServiceTest.class.getSimpleName());
	}

	@AfterAll
	static void finishTest() {
		log.info("finish {}", MetaDataServiceTest.class.getSimpleName());
	}

	@BeforeEach
	void setup() {
		metaDataService = new MetaDataService();
	}

	@ParameterizedTest
	@MethodSource("my.photomanager.TestConstants#getPhotoFilesWithMetaData")
	void should_creat_meta_data_object_from_photo_file(Path photoFilePath) {
		// when
		var createdMetaData = metaDataService.createMetaDataObjectOfPhotoFile(photoFilePath.toFile());

		// then
		assertThat(createdMetaData).isNotNull();
		assertThat(createdMetaData.height()).isGreaterThan(0);
		assertThat(createdMetaData.width()).isGreaterThan(0);
		assertThat(createdMetaData.creationTimeStamp()).isEmpty();
		assertThat(createdMetaData.longitude()).isEqualTo(0);
		assertThat(createdMetaData.latitude()).isEqualTo(0);
	}

	@ParameterizedTest
	@MethodSource("my.photomanager.TestConstants#getPhotoFilesWithoutMetaData")
	void should_return_empty_meta_data_object_from_photo_file_without_meta_data(Path photoFilePath) {
		// when
		var createdMetaData = metaDataService.createMetaDataObjectOfPhotoFile(photoFilePath.toFile());

		// then
		assertThat(createdMetaData).isNotNull();
		assertThat(createdMetaData.height()).isGreaterThan(0);
		assertThat(createdMetaData.width()).isGreaterThan(0);
		assertThat(createdMetaData.creationTimeStamp()).isEmpty();
		assertThat(createdMetaData.longitude()).isEqualTo(0);
		assertThat(createdMetaData.latitude()).isEqualTo(0);
	}

	@Test
	void should_throw_exception_when_create_meta_data_from_non_photo_file() {
		assertThrows(IllegalArgumentException.class,
				() -> metaDataService.createMetaDataObjectOfPhotoFile(TEST_FILE_PATH.resolve("TextFile.txt")
						.toFile()));
	}
}

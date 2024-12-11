package my.photomanager.service.file;

import static my.photomanager.TestConstants.TEST_PHOPTOS_PATH;
import static my.photomanager.TestConstants.UNIT_TEST;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag(UNIT_TEST)
@Log4j2
class FileServiceTest {

	private FileService fileService;

	@BeforeAll
	static void startTest() {
		log.info("start {}", FileServiceTest.class.getSimpleName());
	}

	@AfterAll
	static void finishTest() {
		log.info("finish {}", FileServiceTest.class.getSimpleName());
	}

	@BeforeEach
	void setup() {
		fileService = new FileService();
	}

	@Test
	void should_return_photos_from_source_folder() {
		// when
		var photoFiles = fileService.getPhotosFilesFromSourceFolder(TEST_PHOPTOS_PATH);

		// then
		assertThat(photoFiles).isNotNull();
		assertThat(photoFiles.size()).isEqualTo(3);
	}

	@Test
	void should_return_photos_from_source_folder_recursively() {
		// when
		var photoFiles = fileService.getPhotosFilesFromSourceFolder(TEST_PHOPTOS_PATH, true);

		// then
		assertThat(photoFiles).isNotNull();
		assertThat(photoFiles.size()).isEqualTo(5);
	}

	@Test
	void should_return_photos_from_source_folder_recursively_by_file_type() {
		// when
		var photoFiles = fileService.getPhotosFilesFromSourceFolder(TEST_PHOPTOS_PATH, true, Lists.newArrayList("jpeg", "JPG", "webp"));

		// then
		assertThat(photoFiles).isNotNull();
		assertThat(photoFiles.size()).isEqualTo(7);
	}
}
package my.photoapi.service.metadataservice;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;

import static my.photoapi.TestUtils.TEST_FILE_PATH;
import static my.photoapi.TestUtils.UNIT_TEST;
import static org.assertj.core.api.Assertions.assertThat;

@Tag(UNIT_TEST)
@Log4j2
class PhotoMetaDataServiceTest {

    private MetaDataService metaDataService;

    @BeforeAll
    static void startTest() {
        log.info("start {}", PhotoMetaDataServiceTest.class.getSimpleName());
    }

    @AfterAll
    static void finishTest() {
        log.info("finish {}", PhotoMetaDataServiceTest.class.getSimpleName());
    }

    @BeforeEach
    void setup() {
        metaDataService = new MetaDataService();
    }

    @ParameterizedTest
    @MethodSource("my.photoapi.TestUtils#getPhotoFilesWithMetaData")
    void should_return_meta_data_object_from_photo_file(Path photoFile) {
        // when
        var metaData = metaDataService.createMetaDataFromPhotoFile(photoFile);

        // then
        assertThat(metaData).isNotNull();
        assertThat(metaData.getHeight()).isGreaterThan(0);
        assertThat(metaData.getWidth()).isGreaterThan(0);
        assertThat(metaData.getCreationTimeStamp()).isEmpty();
        assertThat(metaData.getLongitude()).isEqualTo(0);
        assertThat(metaData.getLatitude()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("my.photoapi.TestUtils#getPhotoFilesWithoutMetaData")
    void should_return_empty_meta_data_object_from_photo_file_without_meta_data(Path photoFile){
        // when
        var metaData = metaDataService.createMetaDataFromPhotoFile(photoFile);

        // then
        assertThat(metaData).isNotNull();
        assertThat(metaData.getHeight()).isGreaterThan(0);
        assertThat(metaData.getWidth()).isGreaterThan(0);
        assertThat(metaData.getCreationTimeStamp()).isEmpty();
        assertThat(metaData.getLongitude()).isEqualTo(0);
        assertThat(metaData.getLatitude()).isEqualTo(0);
    }

    @Test
    void test_create_meta_data_from_non_photo_file() {
        // when
        var metaData = metaDataService.createMetaDataFromPhotoFile(TEST_FILE_PATH.resolve("TextFile.txt"));

        // then
        assertThat(metaData).isNotNull();
        assertThat(metaData.getHeight()).isEqualTo(0);
        assertThat(metaData.getWidth()).isEqualTo(0);
        assertThat(metaData.getCreationTimeStamp()).isEmpty();
        assertThat(metaData.getLongitude()).isEqualTo(0);
        assertThat(metaData.getLatitude()).isEqualTo(0);
    }
}
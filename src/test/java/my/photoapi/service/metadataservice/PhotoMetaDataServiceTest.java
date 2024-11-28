package my.photoapi.service.metadataservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoMetaDataServiceTest {

    private MetaDataService metaDataService;

    @BeforeEach
    void setup() {
        // given
        metaDataService = new MetaDataService();
    }

    @ParameterizedTest
    @MethodSource("my.photoapi.TestUtils#getPhotoFilesWithMetaData")
    void createMetaDataFromPhotoFileWithMetaData(Path photoFile) {
        // when
        var metaData = metaDataService.createMetaData(photoFile);

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
    void createMetaDataFromPhotoFileWithoutMetaData(Path photoFile) {
        // when
        var metaData = metaDataService.createMetaData(photoFile);

        // then
        assertThat(metaData).isNotNull();
        assertThat(metaData.getHeight()).isGreaterThan(0);
        assertThat(metaData.getWidth()).isGreaterThan(0);
        assertThat(metaData.getCreationTimeStamp()).isEmpty();
        assertThat(metaData.getLongitude()).isEqualTo(0);
        assertThat(metaData.getLatitude()).isEqualTo(0);
    }
}
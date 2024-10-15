package my.photo_manager.services;

import my.photo_manager.model.metadata.PhotoMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.stream.Stream;

import static my.photo_manager.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PhotoMetaDataServiceTest {

    private PhotoMetaDataService metaDataService;

    @BeforeEach
    void init() {
        metaDataService = new PhotoMetaDataService();
    }

    // Test Data
    static Stream<Arguments> getTestDataForBuildPhoto() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA, META_DATA_OF_PHOTO_WITH_EXIF_DATA),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA, META_DATA_OF_PHOTO_WITHOUT_EXIF_DATA),
                Arguments.of(TEST_IMAGE_PATH, META_DATA_OF_IMAGE)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataForBuildPhoto")
    void shouldBuildPhotoMetaData(Path testPhotoPath, PhotoMetaData expectedPhotoMetaData) {
        var metaData = metaDataService.buildPhotoMetaData(testPhotoPath.toFile());

        assertThat(metaData).isNotNull();
        assertThat(metaData).usingRecursiveComparison().isEqualTo(expectedPhotoMetaData);
    }

    static Stream<Arguments> getTestDataForGetPhotoHeight() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA, META_DATA_OF_PHOTO_WITH_EXIF_DATA.getHeight()),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA, META_DATA_OF_PHOTO_WITHOUT_EXIF_DATA.getHeight()),
                Arguments.of(TEST_IMAGE_PATH, META_DATA_OF_IMAGE.getHeight())
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataForGetPhotoHeight")
    void shouldReturnHeightOfPhoto(Path testPhotoPath, int expectedPhotoHeight) {
        var height = metaDataService.getHeight(testPhotoPath.toFile());

        assertThat(height).isEqualTo(expectedPhotoHeight);
    }

    static Stream<Arguments> getTestDataForGetPhotoWidth() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA, META_DATA_OF_PHOTO_WITH_EXIF_DATA.getWidth()),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA, META_DATA_OF_PHOTO_WITHOUT_EXIF_DATA.getWidth()),
                Arguments.of(TEST_IMAGE_PATH, META_DATA_OF_IMAGE.getWidth())
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataForGetPhotoWidth")
    void shouldReturnWidthOfPhoto(Path testPhotoPath, int expectedPhotoWidth) {
        var width = metaDataService.getWidth(testPhotoPath.toFile());

        assertThat(width).isEqualTo(expectedPhotoWidth);
    }

    static Stream<Arguments> getTestDataForGetPhotoCreationTimeStamp() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA, META_DATA_OF_PHOTO_WITH_EXIF_DATA.getCreationTimeStamp()),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA, META_DATA_OF_PHOTO_WITHOUT_EXIF_DATA.getCreationTimeStamp()),
                Arguments.of(TEST_IMAGE_PATH, META_DATA_OF_IMAGE.getCreationTimeStamp())
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataForGetPhotoCreationTimeStamp")
    void shouldReturnCreationTimeStampOfPhoto(Path testPhotoPath, String expectedPhotoCreationTimeStamp) {
        var creationTimeStamp = metaDataService.getCreationTimeStamp(testPhotoPath.toFile());

        assertThat(creationTimeStamp).isEqualTo(expectedPhotoCreationTimeStamp);
    }
}

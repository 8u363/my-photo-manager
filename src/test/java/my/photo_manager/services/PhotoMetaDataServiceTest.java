package my.photo_manager.services;

import my.photo_manager.TestConstants;
import my.photo_manager.model.metadata.PhotoMetaData;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PhotoMetaDataServiceTest {

    private PhotoMetaDataService metaDataService;

    @BeforeEach
    void init() {
        metaDataService = new PhotoMetaDataService();
    }

    // Test Data
    private final static int heightOfPhotoWithExifData = 4291;
    private final static int widthOfPhotoWithExifData = 3218;
    private final static String creationTimeStampOfPhotoWithExifData = "2016:12:02 11:10:20";

    private final static int heightOfPhotoWithoutExifData = 4291;
    private final static int widthOfPhotoWithoutExifData = 3218;
    private final static String creationTimeStampOfPhotoWithoutExifData = Strings.EMPTY;

    private final static int heightOfImage = 2160;
    private final static int widthOfIMage = 3840;
    private final static String creationTimeStampOfImage = "2023:11:05 10:28:05";


    static Stream<Arguments> getTestDataForBuildPhoto() {
        var metaDataOfPhotoWithExifData = PhotoMetaData.builder()
                .withHeight(heightOfPhotoWithExifData)
                .withWidth(widthOfPhotoWithExifData)
                .withCreationTimeStamp(creationTimeStampOfPhotoWithExifData)
                .withCountry("United States")
                .withCity("City of New York")
                .withPostCode("10038")
                .withStreet("Saint James Place")
                .withHouseNumber("5")
                .build();

        var metaDataOfPhotoWithoutExifData = PhotoMetaData.builder()
                .withHeight(heightOfPhotoWithoutExifData)
                .withWidth(widthOfPhotoWithoutExifData)
                .withCreationTimeStamp(creationTimeStampOfPhotoWithoutExifData)
                .withCountry(Strings.EMPTY)
                .withCity(Strings.EMPTY)
                .withPostCode(Strings.EMPTY)
                .withStreet(Strings.EMPTY)
                .withHouseNumber(Strings.EMPTY)
                .build();

        var metaDataOfImage = PhotoMetaData.builder()
                .withHeight(heightOfImage)
                .withWidth(widthOfIMage)
                .withCreationTimeStamp(creationTimeStampOfImage)
                .withCountry(Strings.EMPTY)
                .withCity(Strings.EMPTY)
                .withPostCode(Strings.EMPTY)
                .withStreet(Strings.EMPTY)
                .withHouseNumber(Strings.EMPTY)
                .build();


        return Stream.of(
                Arguments.of(TestConstants.testPhotoWithExifDataPath, metaDataOfPhotoWithExifData),
                Arguments.of(TestConstants.testPhotoWithoutExifDataPath, metaDataOfPhotoWithoutExifData),
                Arguments.of(TestConstants.testImagePath, metaDataOfImage)
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
                Arguments.of(TestConstants.testPhotoWithExifDataPath, heightOfPhotoWithExifData),
                Arguments.of(TestConstants.testPhotoWithoutExifDataPath, heightOfPhotoWithoutExifData),
                Arguments.of(TestConstants.testImagePath, heightOfImage)
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
                Arguments.of(TestConstants.testPhotoWithExifDataPath, widthOfPhotoWithExifData),
                Arguments.of(TestConstants.testPhotoWithoutExifDataPath, widthOfPhotoWithoutExifData),
                Arguments.of(TestConstants.testImagePath, widthOfIMage)
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
                Arguments.of(TestConstants.testPhotoWithExifDataPath, creationTimeStampOfPhotoWithExifData),
                Arguments.of(TestConstants.testPhotoWithoutExifDataPath, creationTimeStampOfPhotoWithoutExifData),
                Arguments.of(TestConstants.testImagePath, creationTimeStampOfImage)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataForGetPhotoCreationTimeStamp")
    void shouldReturnCreationTimeStampOfPhoto(Path testPhotoPath, String expectedPhotoCreationTimeStamp) {
        var creationTimeStamp = metaDataService.getCreationTimeStamp(testPhotoPath.toFile());

        assertThat(creationTimeStamp).isEqualTo(expectedPhotoCreationTimeStamp);
    }
}

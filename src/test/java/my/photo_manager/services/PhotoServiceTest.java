package my.photo_manager.services;

import my.photo_manager.config.PhotoConfiguration;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import static my.photo_manager.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoServiceTest {

    @Mock
    private PhotoConfiguration configuration;

    @Mock
    private PhotoRepository repository;

    @Mock
    private PhotoMetaDataService metaDataService;

    @Mock
    private PhotoFilterService filterService;

    private PhotoService photoService;

    @BeforeEach
    void init() {
        photoService = new PhotoService(configuration, repository, metaDataService, filterService);
    }

    @Test
    void shouldReturnAllPhotoObjects() {
        photoService.getAll();

        verify(repository).findAll();
    }

    static Stream<Arguments> getTestDataForGetHashValue() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA),
                Arguments.of(TEST_IMAGE_PATH)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataForGetHashValue")
    void shouldReturnHashValueOfPhotoFile(Path testPhotoPath) throws IOException {
        var hashValue = photoService.getHashValue(testPhotoPath.toFile());

        assertThat(hashValue).isNotNull();
        assertThat(hashValue).isNotEmpty();
    }

    static Stream<Arguments> getTestDataForBuildPhotoObject() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA),
                Arguments.of(TEST_IMAGE_PATH)
        );
    }


    @ParameterizedTest
    @MethodSource("getTestDataForBuildPhotoObject")
    void shouldBuildPhotoObject(Path testPhotoPath) {
        var photoObject = photoService.buildPhotoObject(testPhotoPath.toFile());

        assertThat(photoObject).isNotNull();
        assertThat(photoObject.getFilePath()).isNotNull();
        assertThat(photoObject.getFilePath()).isNotEmpty();
        assertThat(photoObject.getHashValue()).isNotNull();
        assertThat(photoObject.getHashValue()).isNotEmpty();
    }

    @Test
    void shouldSavePhotoObject() {
        photoService.savePhotoObject(mock(Photo.class));

        verify(repository).saveAndFlush(any(Photo.class));
    }

    @Test
    void shouldNotSavePhotoObjectWhenExistsAlready() {
        given(repository.findByHashValue(TEST_HASH_VALUE))
                .willReturn(Optional.of((mock(Photo.class))));

        var photo = Photo.builder().withFilePath(TEST_FILE_PATH).withHashValue(TEST_HASH_VALUE).build();
        photoService.savePhotoObject(photo);

        verify(repository, never()).saveAndFlush(any(Photo.class));
    }

    static Stream<Arguments> getTestDataForBuildAndSavePhotoObject() {
        return Stream.of(
                Arguments.of(TEST_PHOTO_PATH_WITH_EXIF_DATA),
                Arguments.of(TEST_PHOTO_PATH_WITHOUT_EXIF_DATA),
                Arguments.of(TEST_IMAGE_PATH)
        );
    }


    @ParameterizedTest
    @MethodSource("getTestDataForBuildAndSavePhotoObject")
    void shouldBuildAndSavePhotoObject(Path testPhotoPath) {
        photoService.buildAndSavePhotoObject(testPhotoPath.toFile());

        verify(repository).saveAndFlush(any(Photo.class));
    }
}
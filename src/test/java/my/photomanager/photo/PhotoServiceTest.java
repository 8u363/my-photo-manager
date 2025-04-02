package my.photomanager.photo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static my.photomanager.TestUtils.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import my.photomanager.photo.location.LocationService;
import my.photomanager.photo.metadata.MetaDataService;
import my.photomanager.thumbnail.ThumbnailService;

@ExtendWith(SpringExtension.class)
class PhotoServiceTest {

    private PhotoService photoService;

    @Mock
    private PhotoRepository repository;

    @Mock
    private LocationService locationService;

    @Mock
    private MetaDataService metaDataService;

    @Mock
    private ThumbnailService thumbnailService;

    @BeforeEach
    void setup() {
        photoService =
                new PhotoService(repository, locationService, metaDataService, thumbnailService);
    }

    @Test
    void shouldSavePhotoIfNotExists() {
        when(repository.findByHashValue(PHOTO_HASH_VALUE)).thenReturn(Optional.empty());

        var photo = buildDefaultPhoto();
        photoService.savePhoto(photo);
        verify(repository).saveAndFlush(photo);
    }

    @Test
    void shouldNotSavePhotoIfExistsAlready() {
        when(repository.findByHashValue(PHOTO_HASH_VALUE))
                .thenReturn(Optional.of(mock(Photo.class)));

        photoService.savePhoto(buildDefaultPhoto());
        verify(repository, never()).saveAndFlush(any(Photo.class));
    }

    @Test
    void shouldUpdateExistingPhoto() {
        when(repository.findById(PHOTO_ID)).thenReturn(Optional.of(mock(Photo.class)));

        photoService.updatePhoto(PHOTO_ID);
        verify(repository).saveAndFlush(any(Photo.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdateNotExistingPhoto() {
        when(repository.findById(PHOTO_ID)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> photoService.updatePhoto(PHOTO_ID));
    }

    @Test
    void shouldDeleteExistingPhoto() {
        when(repository.findById(PHOTO_ID)).thenReturn(Optional.of(mock(Photo.class)));

        photoService.deletePhoto(PHOTO_ID);
        verify(repository).delete(any(Photo.class));
    }

    @Test
    void shouldThrowExceptionWhenDeleteNotExistingPhoto() {
        when(repository.findById(PHOTO_ID)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> photoService.deletePhoto(PHOTO_ID));
    }
}

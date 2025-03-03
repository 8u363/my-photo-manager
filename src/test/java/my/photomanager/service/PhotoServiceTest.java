package my.photomanager.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import my.photomanager.photo.Photo;
import my.photomanager.repository.PhotoRepository;

@ExtendWith(SpringExtension.class)
class PhotoServiceTest {

    private final long PHOTO_ID = 1L;
    private final String PHOTO_HASH_VALUE = "1234567890";
    private final String PHOTO_FILE_PATH = "testPhoto.jpg";
    private final Photo photo = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).build();

    private PhotoService photoService;
    @Mock
    private PhotoRepository repository;


    @BeforeEach
    void setup() {
        photoService = new PhotoService(repository);
    }

    @Test
    void shouldSavePhotoIfNotExists() {
        when(repository.findByHashValue(PHOTO_HASH_VALUE)).thenReturn(Optional.empty());

        photoService.savePhoto(photo);
        verify(repository).saveAndFlush(photo);
    }

    @Test
    void shouldNotSavePhotoIfExistsAlready() {
        when(repository.findByHashValue(PHOTO_HASH_VALUE))
                .thenReturn(Optional.of(mock(Photo.class)));

        photoService.savePhoto(photo);
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

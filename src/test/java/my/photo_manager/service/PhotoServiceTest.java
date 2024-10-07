package my.photo_manager.service;

import my.photo_manager.configuration.PhotoConfiguration;
import my.photo_manager.photo.Photo;
import my.photo_manager.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PhotoServiceTest {

    @Mock
    private PhotoConfiguration configuration;

    @Mock
    private PhotoRepository repository;

    private  PhotoService photoService;

    @BeforeEach
    void init(){
        photoService = new PhotoService(configuration, repository);
    }

    @Test
    void canGetHashValueOfPhotoFileHashValue() throws IOException {
        var testPhotoFile = Paths.get("src", "test", "resources", "TestPhotos","Photo1.jpg").toFile();
        var hashValue = photoService.getHashValue(testPhotoFile);

        assertThat(hashValue).isNotNull();
        assertThat(hashValue).isNotEmpty();
    }

    @Test
    void canGetAllPhotosOfSourceFolder(){
        var testPhotos = photoService.getAllPhotoFilesInSourceFolder(Paths.get("src", "test", "resources", "TestPhotos"));

        assertThat(testPhotos).isNotNull();
        assertThat(testPhotos).isNotEmpty();
    }

    @Test
    void canBuildPhotoObject(){
        var testPhotoFile = Paths.get("src", "test", "resources", "TestPhotos","Photo1.jpg").toFile();
        var photoObject = photoService.buildPhotoObject(testPhotoFile);

        assertThat(photoObject).isNotNull();
        assertThat(photoObject.getFilePath()).isNotNull();
        assertThat(photoObject.getFilePath()).isNotEmpty();
        assertThat(photoObject.getHashValue()).isNotNull();
        assertThat(photoObject.getHashValue()).isNotEmpty();

    }

    @Test
    void canSavePhotoObject(){
        photoService.savePhotoObject(mock(Photo.class));

        verify(repository).saveAndFlush(any(Photo.class));
    }

    @Test
    void canGetAllPhotoObjects(){
        photoService.getAll();

        verify(repository).findAll();
    }
}
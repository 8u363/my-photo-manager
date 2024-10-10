package my.photo_manager.repository;

import my.photo_manager.model.photo.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository repository;

    @Test
    void shouldReturnPhotoWhenFindByHashValue() {
        var hashValue = "TestHashValue";

        repository.saveAndFlush(Photo.builder()
                .withHashValue(hashValue)
                .withFilePath("TestFilePath")
                .build());
        assertThat(repository.findByHashValue(hashValue)).isPresent();

    }

    @Test
    void shouldNotReturnPhotoWhenFindByHashValueIsNotPresent() {
        var hashValue = "TestHashValue";

        assertThat(repository.findByHashValue(hashValue)).isNotPresent();
    }

    @Test
    void shouldThrowExceptionWhenSaveDuplicatedPhoto() {
        var hashValue = "TestHashValue";
        var photo1 = Photo.builder()
                .withHashValue(hashValue)
                .withFilePath("TestFilePath")
                .build();
        var photo2 = Photo.builder()
                .withHashValue(hashValue)
                .withFilePath("TestFilePath2")
                .build();

        repository.saveAndFlush(photo1);
        assertThat(repository.findByHashValue(hashValue)).isPresent();

        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(photo2));
    }
}
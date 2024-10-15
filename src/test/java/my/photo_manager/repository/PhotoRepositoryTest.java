package my.photo_manager.repository;

import my.photo_manager.model.photo.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static my.photo_manager.TestUtils.TEST_FILE_PATH;
import static my.photo_manager.TestUtils.TEST_HASH_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository repository;

    @Test
    void shouldReturnPhotoWhenFindByHashValue() {
        repository.saveAndFlush(Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath(TEST_FILE_PATH)
                .build());
        assertThat(repository.findByHashValue(TEST_HASH_VALUE)).isPresent();

    }

    @Test
    void shouldNotReturnPhotoWhenFindByHashValueIsNotPresent() {
        assertThat(repository.findByHashValue(TEST_HASH_VALUE)).isNotPresent();
    }

    @Test
    void shouldThrowExceptionWhenSaveDuplicatedPhoto() {
        var photo1 = Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath(TEST_FILE_PATH)
                .build();
        var photo2 = Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath("TestFilePath2")
                .build();

        repository.saveAndFlush(photo1);
        assertThat(repository.findByHashValue(TEST_HASH_VALUE)).isPresent();

        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(photo2));
    }
}
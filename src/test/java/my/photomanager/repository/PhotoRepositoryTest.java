package my.photomanager.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import my.photomanager.photo.Photo;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository repository;

    @Test
    void shouldThrowExceptionWhenConstraintsCheckFailed() {
        var PHOTO_HASH_VALUE = "1234567890";
        var PHOTO_FILE_PATH = "testPhoto.jpg";

        repository.saveAndFlush(Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).build());

        assertThrows(DataIntegrityViolationException.class, () -> repository
                .saveAndFlush(Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).build()));
    }

}

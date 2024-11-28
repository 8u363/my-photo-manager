package my.photoapi.repository;

import my.photoapi.model.photo.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static my.photoapi.TestUtils.TEST_FILE_PATH;
import static my.photoapi.TestUtils.TEST_HASH_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository repository;

    @Test
    void throwExceptionWhenSaveDuplicatedPhoto() {
        // given
        var photo1 = Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath(TEST_FILE_PATH)
                .build();
        var photo2 = Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath("TestFilePath2")
                .build();

        // when
        repository.saveAndFlush(photo1);

        // then
        assertThat(repository.findByHashValue(TEST_HASH_VALUE)).isPresent();
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(photo2));
    }
}
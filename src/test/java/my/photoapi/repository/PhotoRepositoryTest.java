package my.photoapi.repository;

import lombok.extern.log4j.Log4j2;
import my.photoapi.model.photo.Photo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static my.photoapi.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@DataJpaTest
@Tag(INTEGRATION_TEST)
@Log4j2
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository repository;

    @BeforeAll
    static void startTest() {
        log.info("start {}", PhotoRepositoryTest.class.getSimpleName());
    }

    @AfterAll
    static void finishTest() {
        log.info("finish {}", PhotoRepositoryTest.class.getSimpleName());
    }

    @Test
    void should_save_photo_object(){
        // given
        var photo1 = Photo.builder(TEST_PHOTO_FILE_PATH, TEST_PHOTO_HASH_VALUE)
                .build();

        // when
        repository.saveAndFlush(photo1);

        // then
        assertThat(repository.findByHashValue(TEST_PHOTO_HASH_VALUE)).isPresent();
    }

    @Test
    void  should_throw_exception_when_save_existing_photo(){
        // given
        var photo1 = Photo.builder(TEST_PHOTO_FILE_PATH, TEST_PHOTO_HASH_VALUE)
                .build();
        var photo2 = Photo.builder("TestFilePath2", TEST_PHOTO_HASH_VALUE)
                .build();

        // when
        repository.saveAndFlush(photo1);

        // then
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(photo2));
    }
/*
    @Test
    void throwExceptionWhenSaveDuplicatedPhoto() {
        // given
        var photo1 = Photo.builder(TEST_FILE_PATH, TEST_HASH_VALUE)
                .build();
        var photo2 = Photo.builder("TestFilePath2", TEST_HASH_VALUE)
                .build();

        // when
        repository.saveAndFlush(photo1);

        // then
        assertThat(repository.findByHashValue(TEST_HASH_VALUE)).isPresent();
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(photo2));
    }*/
}
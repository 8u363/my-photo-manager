package my.photomanager.photo;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static my.photomanager.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import my.photomanager.TestUtils;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository repository;

    @Test
    void shouldThrowExceptionWhenConstraintsCheckFailed() {
        repository.saveAndFlush(TestUtils.buildPhoto());

        assertThrowsExactly(DataIntegrityViolationException.class,
                () -> repository.saveAndFlush(TestUtils.buildPhoto()));
    }
}

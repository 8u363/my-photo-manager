package my.photomanager.v1.repository;

import static my.photomanager.TestConstants.INTEGRATION_TEST;
import static my.photomanager.TestConstants.TEST_PHOPTOS_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.log4j.Log4j2;
import my.photomanager.v1.model.Configuration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@Tag(INTEGRATION_TEST)
@Log4j2
class ConfigurationRepositoryTest {

	@Autowired
	private ConfigurationRepository repository;

	@BeforeAll
	static void startTest() {
		log.info("start {}", ConfigurationRepositoryTest.class.getSimpleName());
	}

	@AfterAll
	static void finishTest() {
		log.info("finish {}", ConfigurationRepositoryTest.class.getSimpleName());
	}

	@Test
	void should_save_configuration_object() {
		// given
		var configuration1 = new Configuration(TEST_PHOPTOS_PATH.toString(), "");

		// when
		repository.saveAndFlush(configuration1);

		// then
		assertThat(repository.findAll().size()).isOne();
	}

	@Test
	void should_throw_exception_when_save_existing_configuration() {
		// given
		var configuration1 = new Configuration(TEST_PHOPTOS_PATH.toString(), "");
		var configuration2 = new Configuration(TEST_PHOPTOS_PATH.toString(), "");

		// when
		repository.saveAndFlush(configuration1);

		// then
		assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(configuration2));
	}
}
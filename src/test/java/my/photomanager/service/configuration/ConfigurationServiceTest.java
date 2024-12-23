package my.photomanager.service.configuration;

import static my.photomanager.TestConstants.INTEGRATION_TEST;

import lombok.extern.log4j.Log4j2;
import my.photomanager.repository.ConfigurationRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag(INTEGRATION_TEST)
@Log4j2
class ConfigurationServiceTest {

	@Autowired
	private ConfigurationRepository repository;

	@BeforeAll
	static void startTest() {
		log.info("start {}", ConfigurationServiceTest.class.getSimpleName());
	}

	@AfterAll
	static void finishTest() {
		log.info("finish {}", ConfigurationServiceTest.class.getSimpleName());
	}

}
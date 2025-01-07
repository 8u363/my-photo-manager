package my.photomanager.v1.service;

import java.util.List;
import static net.logstash.logback.argument.StructuredArguments.kv;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photomanager.v1.model.Configuration;
import my.photomanager.v1.repository.ConfigurationRepository;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConfigurationService {

	private final ConfigurationRepository repository;

	public List<Configuration> getConfigurations() {
		return repository.findAll();
	}

	public Configuration saveConfiguration(@NonNull Configuration configuration) {
		repository.findByFolderPathAndIndexInterval(
				configuration.getFolderPath(), configuration.getUpdateInterval())
				.ifPresent(config -> new InternalError(""));

		var savedConfiguration = repository.saveAndFlush(configuration);
		log.info("saved {}", kv("configuration", savedConfiguration));

		return savedConfiguration;
	}

	public Configuration updateConfiguration(long ID, @NonNull String folderPath, @NonNull String updateInterval) {
		return null;
	}

	public void deleteConfiguration(long ID) {

	}
}

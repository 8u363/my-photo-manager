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

	/**
	 * @return a list with all configuration objects
	 */
	public List<Configuration> getConfigurations() {
		return repository.findAll();
	}

	/**
	 * save a new configuration object
	 * check if the configuration exists already
	 * 
	 * @param configuration the configuration
	 * @return the saved configuration
	 */
	public Configuration saveConfiguration(@NonNull Configuration configuration) {
		log.info("save {}", kv("configuration", configuration));

		var folderPath = configuration.getFolderPath();
		var indexInterval = configuration.getIndexInterval();
		repository.findByFolderPathAndIndexInterval(folderPath, indexInterval)
				.ifPresent(config -> new InternalError("configuration [" + configuration + "] exists already"));

		var savedConfiguration = repository.saveAndFlush(configuration);
		log.info("saved {}", kv("configuration", savedConfiguration));

		return savedConfiguration;
	}

	/**
	 * update an existing configuration object
	 * 
	 * @param ID            the existing configuration id
	 * @param folderPath    the new folder path
	 * @param indexInterval the new index interval
	 * @return the updated configuration
	 */
	public Configuration updateConfiguration(long ID, @NonNull String folderPath, @NonNull String indexInterval) {
		Configuration configuration = repository.findById(ID)
				.orElseThrow(() -> new InternalError("no configuration found with ID " + ID));

		log.info("update {}", kv("configuration", configuration));
		return null;
	}

	/**
	 * delete an existing configuration
	 * 
	 * @param ID the existing configuration id
	 */
	public void deleteConfiguration(long ID) {
		Configuration configuration = repository.findById(ID)
				.orElseThrow(() -> new InternalError("no configuration found with ID " + ID));

		log.info("delete {}", kv("configuration", configuration));

	}
}

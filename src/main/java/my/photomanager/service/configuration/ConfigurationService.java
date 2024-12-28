package my.photomanager.service.configuration;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photomanager.repository.ConfigurationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConfigurationService implements IConfigurationService<Configuration> {

	private final ConfigurationRepository repository;

	@Override
	public List<Configuration> getConfigurations() {
		return repository.findAll();
	}

	@Override
	public Configuration saveConfiguration(@NonNull Configuration configuration) {
		log.info("save {} if not exists", kv("configuration", configuration));

		var folderPath = configuration.getFolderPath();
		var scanInterval = configuration.getScanInterval();
		repository.findByFolderPathAndUpdateInterval(folderPath, scanInterval)
				.ifPresent(config -> new InternalError(""));


		var savedConfiguration = repository.saveAndFlush(configuration);
		log.info("saved {}", kv("configuration", savedConfiguration));

		return savedConfiguration;
	}

	@Override
	public Configuration updateConfiguration(long ID, @NonNull String folderPath, @NonNull String scanInterval) {
		//TODO update log
		log.info("update configuration if exists");

		Configuration configuration = repository.findById(ID)
				.orElseThrow(() -> new InternalError("no configuration found with ID " + ID));
		log.debug("found exiting {}", kv("configuration", configuration));

		configuration.setFolderPath(folderPath);
		configuration.setScanInterval(scanInterval);

		configuration = repository.saveAndFlush(configuration);
		log.info("updated {}", kv("configuration", configuration));

		return configuration;
	}

	@Override
	public void deleteConfiguration(long ID) {
		//TODO update log
		log.info("delete configuration if exists");

		Configuration configuration = repository.findById(ID)
				.orElseThrow(() -> new InternalError("no configuration found with ID " + ID));
		log.debug("found exiting {}", kv("configuration", configuration));

		repository.deleteById(ID);
		log.info("delete {}", kv("configuration", configuration));
	}
}

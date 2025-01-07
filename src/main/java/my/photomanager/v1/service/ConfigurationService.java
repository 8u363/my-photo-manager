package my.photomanager.v1.service;

import com.google.common.collect.Lists;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photomanager.v1.model.Configuration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConfigurationService {

	/**
	 * @return a list with all configuration objects
	 */
	public List<Configuration> getConfigurations() {
		return Lists.newArrayList();
	}

	/**
	 * save a new configuration object
	 * check if the configuration exists already
	 * 
	 * @param configuration the configuration
	 * @return the saved configuration
	 */
	public Configuration saveConfiguration(@NonNull Configuration configuration) {
		// TODO save configuration
		return null;
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
		// TODO update configuration
		return null;
	}

	/**
	 * delete an existing configuration
	 * 
	 * @param ID the existing configuration id
	 */
	public void deleteConfiguration(long ID) {
		// TODO delete configuration

	}
}

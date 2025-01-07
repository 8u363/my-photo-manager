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

	public List<Configuration> getConfigurations() {
		return Lists.newArrayList();
	}

	public Configuration saveConfiguration(@NonNull Configuration configuration) {
		return null;
	}

	public Configuration updateConfiguration(long ID, @NonNull String folderPath, @NonNull String updateInterval) {
		return null;
	}

	public void deleteConfiguration(long ID) {

	}
}

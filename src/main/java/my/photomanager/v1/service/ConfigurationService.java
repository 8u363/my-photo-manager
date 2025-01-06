package my.photomanager.v1.service;

import com.google.common.collect.Lists;
import java.util.List;
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

	public Configuration saveConfiguration(Configuration configuration) {
		return null;
	}

	public Configuration updateConfiguration(long ID, String folderPath, String scanInterval) {
		return null;
	}

	public void deleteConfiguration(long ID) {

	}
}

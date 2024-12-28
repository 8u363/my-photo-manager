package my.photomanager.service.configuration;

import java.util.List;

public interface IConfigurationService<T extends Configuration> {

	List<T> getConfigurations();

	T saveConfiguration(T configuration);

	T updateConfiguration(long ID, String folderPath, String scanInterval);

	void deleteConfiguration(long ID);
}

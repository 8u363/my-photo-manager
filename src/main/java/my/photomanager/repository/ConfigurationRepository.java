package my.photomanager.repository;

import java.util.Optional;
import my.photomanager.service.configuration.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

	Optional<Configuration> findByFolderPathAndScanInterval(String folderPath, String scanInterval);
}

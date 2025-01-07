package my.photomanager.v1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.NonNull;
import my.photomanager.v1.model.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    Optional<Configuration> findByFolderPathAndIndexInterval(@NonNull String folderPath,
            @NonNull String indexInterval);
}

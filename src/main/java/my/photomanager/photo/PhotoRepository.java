package my.photomanager.photo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import lombok.NonNull;

public interface PhotoRepository
                extends JpaRepository<Photo, Long>, JpaSpecificationExecutor<Photo> {

        Optional<Photo> findByHashValue(@NonNull String hashValue);
}

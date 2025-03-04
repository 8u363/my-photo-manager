package my.photomanager.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import my.photomanager.photo.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByHashValue(@NonNull String hashValue);
}

package my.photo_manager.repository;

import my.photo_manager.model.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByHashValue(String hashValue);

}

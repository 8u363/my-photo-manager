package my.photo_manager.repository;

import my.photo_manager.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}

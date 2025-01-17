package my.photomanager.photo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.NonNull;

public interface PhotoRepository extends JpaRepository<Photo, Long>{

    Optional<Photo> findByPhotoHashValue(@NonNull String photoHashValue);
}

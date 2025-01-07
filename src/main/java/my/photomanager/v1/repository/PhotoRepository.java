package my.photomanager.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.photomanager.v1.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}

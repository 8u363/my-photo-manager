package my.photomanager.v1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lombok.NonNull;
import my.photomanager.v1.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByHashValue(@NonNull String hashValue);

    @Query("SELECT p FROM Photo p JOIN p.labels l WHERE l.text IN :labelNames")
    Page<Photo> findByLabelNames(@Param("labelNames") List<String> labelNames, Pageable pageable);
}

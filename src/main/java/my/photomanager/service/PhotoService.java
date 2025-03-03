package my.photomanager.service;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photomanager.photo.Photo;
import my.photomanager.repository.PhotoRepository;
import my.photomanager.web.PhotoDTO;

@Service
@Log4j2
public class PhotoService {

    private final PhotoRepository repository;

    protected PhotoService(@NonNull PhotoRepository repository) {
        this.repository = repository;
    }

    public List<PhotoDTO> getPhotos(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).stream().map(photo -> new PhotoDTO())
                .toList();
    }

    public Photo savePhoto(@NonNull Photo photo) {
        return repository.findByHashValue(photo.getHashValue()).orElseGet(() -> {
            var savedPhoto = repository.saveAndFlush(photo);
            log.info("{}", kv("saved photo", savedPhoto));

            return savedPhoto;
        });
    }

    public Photo updatePhoto(long id) {
        var photo = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("photo with ID " + id + " does not exist"));

        // TODO update gps data
        photo.setCountry("newCountry");
        photo.setCity("newCity");
        photo.setPostalCode("newPostalCode");
        photo.setRoad("newRoad");

        var updatedPhoto = repository.saveAndFlush(photo);
        log.info("{}", kv("updated photo", updatedPhoto));

        return updatedPhoto;
    }

    public void deletePhoto(long id) {
        var photo = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("photo with ID " + id + " does not exist"));

        repository.delete(photo);
        log.info("{}", kv("deleted photo", photo));
    }
}

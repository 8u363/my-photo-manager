package my.photomanager.photo;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.argument.StructuredArguments.v;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photomanager.photo.location.LocationService;
import my.photomanager.photo.metadata.MetaDataService;
import my.photomanager.thumbnail.ThumbnailService;

@Service
@Log4j2
public class PhotoService {

    private final PhotoRepository repository;
    private final LocationService locationService;
    private final MetaDataService metaDataService;
    private final ThumbnailService thumbnailService;

    protected PhotoService(@NonNull PhotoRepository repository,
            @NonNull LocationService locationService, @NonNull MetaDataService metaDataService,
            @NonNull ThumbnailService thumbnailService) {
        this.repository = repository;
        this.locationService = locationService;
        this.metaDataService = metaDataService;
        this.thumbnailService = thumbnailService;
    }

    /**
     * get a page with photo thumbnails
     * 
     * @param page
     * @param size
     * @return
     */
    public List<PhotoDTO> getPhotos(int page, int size) {
        log.debug("get photos of {} with max. {} elements", kv("page", page), v("size", size));

        return repository.findAll(PageRequest.of(page, size)).stream()
                .map(photo -> new PhotoDTO(photo.getId(),
                        thumbnailService.createThumbnailBase64OfPhoto(photo)))
                .toList();
    }


    /**
     * create a Photo object of the photo file
     * 
     * @param photoFile
     * @return the created photo object
     * @throws IOException
     */
    public Photo createPhoto(File photoFile) throws IOException {
        log.debug("create photo object of {}", kv("photo file", photoFile.getAbsolutePath()));

        var metaData = metaDataService.createMetaData(photoFile);
        var locationData =
                locationService.createLocation(metaData.longitude(), metaData.latitude());
        var photo = Photo.builder(photoFile.getAbsolutePath(), createPhotoHashValue(photoFile))
                .withHeight(metaData.height()).withWidth(metaData.width())
                .withCreationDate(metaData.creationDate()).withCountry(locationData.country())
                .withCity(locationData.city()).withPostalCode(locationData.postalCode())
                .withRoad(locationData.road()).build();

        log.info("created {}", kv("photo object", photo));
        return photo;
    }

    /**
     * saved the photo object if not exists
     * 
     * @param photo
     * @return the saved photo object
     */
    public Photo savePhoto(@NonNull Photo photo) {
        log.debug("save {}", kv("photo", photo));

        var savedPhoto = repository.findByHashValue(photo.getHashValue()).orElseGet(() -> {
            return repository.saveAndFlush(photo);
        });

        log.info("{}", kv("save photo", savedPhoto));
        return savedPhoto;
    }

    /**
     * update an existing photo
     * 
     * @param id
     * @return the updated photo object
     */
    public Photo updatePhoto(long id) {
        var photo = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("photo with ID " + id + " does not exist"));

        log.debug("update {}", kv("photo", photo));

        // TODO update photo properties
        photo.setCountry("newCountry");
        photo.setCity("newCity");
        photo.setPostalCode("newPostalCode");
        photo.setRoad("newRoad");
        var updatedPhoto = repository.saveAndFlush(photo);

        log.info("{}", kv("updated photo", updatedPhoto));
        return updatedPhoto;
    }

    /**
     * delete an existing photo
     * 
     * @param id
     */
    public void deletePhoto(long id) {
        var photo = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("photo with ID " + id + " does not exist"));

        log.debug("delete {}", kv("photo", photo));

        repository.delete(photo);

        log.info("{}", kv("deleted photo", photo));
    }

    /**
     * 
     * @param photoFile
     * @return
     * @throws IOException
     */
    private String createPhotoHashValue(@NonNull File photoFile) throws IOException {
        return DigestUtils.md5DigestAsHex(new FileInputStream(photoFile));
    }
}

package my.photo_manager.services;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.model.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Log4j2
public class PhotoService implements IPhotoService<Photo> {


    private final PhotoRepository repository;

    protected PhotoService(PhotoRepository repository) {
        this.repository = repository;
    }

    @Override
    @SneakyThrows(IOException.class)
    public Photo buildPhotoObject(@NonNull File photoFile) {
        var photoObject = Photo.builder()
                .withHashValue(getHashValue(photoFile))
                .withFilePath(photoFile.getAbsolutePath())
                .build();
        log.info("build {}", kv("photoObject", photoObject));

        return photoObject;
    }

    @Override
    public Photo savePhotoObject(@NonNull Photo photoObject) {
        Photo savedPhoto = null;
        Optional<Photo> optionalPhoto = repository.findByHashValue(photoObject.getHashValue());

        if (optionalPhoto.isEmpty()) {
            savedPhoto = repository.saveAndFlush(photoObject);
            log.info("save {}", kv("photoObject", savedPhoto));
        } else {
            savedPhoto = optionalPhoto.get();
            log.info("{} exists already", kv("photoObject", savedPhoto));
        }

        return savedPhoto;
    }

    @Override
    public Collection<Photo> getAll() {
        return repository.findAll();
    }

}

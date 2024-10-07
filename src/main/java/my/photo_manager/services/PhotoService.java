package my.photo_manager.services;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import my.photo_manager.config.PhotoConfiguration;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.model.repository.PhotoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Log4j2
public class PhotoService implements IPhotoService<Photo> {

    private final PhotoConfiguration configuration;
    private final PhotoRepository repository;

    protected PhotoService(PhotoConfiguration configuration, PhotoRepository repository) {
        this.configuration = configuration;
        this.repository = repository;
    }

    @Override
    public Collection<File> getAllPhotoFilesInSourceFolder(@NonNull Path sourceFolderPath) {
        return FileUtils.listFiles(sourceFolderPath.toFile(), new String[]{"jpg"}, true);
    }

    @Override
    @SneakyThrows(IOException.class)
    public Photo buildPhotoObject(@NonNull File photoFile) {
        var photoObject = Photo.builder()
                .withHashValue(getHashValue(photoFile))
                .withFilePath(photoFile.getAbsolutePath())
                .build();
        log.info("build {} from {}", kv("photoObject", photoObject), kv("photoFile", photoFile.getAbsolutePath()));

        return photoObject;
    }

    @Override
    public Photo savePhotoObject(@NonNull Photo photoObject) {

        // TODO check if photo object exists already
        return repository.saveAndFlush(photoObject);
    }

    @Override
    public Collection<Photo> getAll() {
        return repository.findAll();
    }

}

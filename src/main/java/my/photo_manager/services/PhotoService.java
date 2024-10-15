package my.photo_manager.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import my.photo_manager.config.PhotoConfiguration;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.repository.PhotoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.argument.StructuredArguments.v;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService {

    private final PhotoConfiguration configuration;
    private final PhotoRepository repository;
    private final PhotoMetaDataService metaDataService;
    private final PhotoFilterService filterService;

    /**
     * @return a list of all photo objects
     */
    public Collection<Photo> getAll() {
        return repository.findAll();
    }

    /**
     * import the photos from the source directory
     */
    @Scheduled(fixedRate = 30000)
    public void importPhotos() {
        var sources = configuration.getSource();

        if (configuration.getSource() != null) {
            sources.forEach(sourceFolder -> {
                var photoFiles = FileUtils.listFiles(new File(sourceFolder), new String[]{"jpg"}, true);
                log.info("found {} photo files in {}", v("number of jpeg files", photoFiles.size()),
                        kv("source folder", sourceFolder));

                photoFiles.forEach(this::buildAndSavePhotoObject);
            });
        }
    }

    /**
     * build and save a photo object
     *
     * @param photoFile the photo file
     */
    public void buildAndSavePhotoObject(@NonNull File photoFile) {
        Photo photoObject = buildPhotoObject(photoFile);
        savePhotoObject(photoObject);
    }

    protected String getHashValue(@NonNull File photoFile) throws IOException {
        return DigestUtils.md5DigestAsHex(new FileInputStream(photoFile));
    }

    @SneakyThrows(IOException.class)
    protected Photo buildPhotoObject(@NonNull File photoFile) {
        var photoObject = Photo.builder()
                .withHashValue(getHashValue(photoFile))
                .withFilePath(photoFile.getAbsolutePath())
                .withMetaData(metaDataService.buildPhotoMetaData(photoFile))
                .build();
        log.debug("build {}", kv("photoObject", photoObject));

        return photoObject;
    }

    protected Photo savePhotoObject(@NonNull Photo photoObject) {
        Photo savedPhoto;
        Optional<Photo> optionalPhoto = repository.findByHashValue(photoObject.getHashValue());

        if (optionalPhoto.isEmpty()) {
            savedPhoto = repository.saveAndFlush(photoObject);
            log.info("save {}", kv("photoObject", savedPhoto));
            filterService.createFilterObject(savedPhoto);
        } else {
            savedPhoto = optionalPhoto.get();
            log.info("{} exists already", kv("photoObject", savedPhoto));
        }

        return savedPhoto;
    }
}

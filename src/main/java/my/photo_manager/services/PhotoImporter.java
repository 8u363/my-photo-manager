package my.photo_manager.services;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photo_manager.config.PhotoConfiguration;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.argument.StructuredArguments.v;

@Component
@Log4j2
public class PhotoImporter {

    private final PhotoService photoService;
    private final PhotoConfiguration configuration;

    protected PhotoImporter(@NonNull PhotoService photoService, @NonNull PhotoConfiguration configuration) {
        this.photoService = photoService;
        this.configuration = configuration;
    }

    @Scheduled(fixedRate = 30000)
    protected void importPhotos() {
        var sources = configuration.getSource();

        sources.forEach(sourceFolder -> {
            var photoFiles = FileUtils.listFiles(new File(sourceFolder), new String[]{"jpg"}, true);
            log.info("found {} photo files in {}", v("number of jpeg files", photoFiles.size()),
                    kv("source folder", sourceFolder));

            photoFiles.forEach(photoService::buildAndSavePhotoObject);
        });
    }
}

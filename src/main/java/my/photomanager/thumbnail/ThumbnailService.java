package my.photomanager.thumbnail;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photomanager.config.Config;
import my.photomanager.photo.Photo;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Log4j2
public class ThumbnailService {

    private final Config configuration;

    protected ThumbnailService(@NonNull Config configuration) {
        this.configuration = configuration;
    }

    /**
     * create the thumbnail base 64 string of the photo object
     * 
     * @param photo
     * @return the created thumbnail string
     */
    public String createThumbnailBase64OfPhoto(@NonNull Photo photo) {
        log.debug("create thumbnail base 64 string of {}", kv("photo", photo));

        var thumbnailBase64 = Strings.EMPTY;
        try {
            createThumbnailDirectoryIfNotexists();
            var thumbnailFile = createNewThumbnailOrGetCached(photo);
            thumbnailBase64 = createThumbnailBase64(thumbnailFile);
        } catch (IOException e) {
            log.warn("can not create thumbnail of {} because of {}",
                    kv("photo", photo.getFilePath()), kv("exception", e.getMessage()));
        }

        log.info("created {}", kv("thumbnail base 64 string", thumbnailBase64));
        return thumbnailBase64;
    }

    /**
     * create the thumbnail directory if not exists use the Configuration thumbnai folder
     * 
     * @throws IOException
     */
    private void createThumbnailDirectoryIfNotexists() throws IOException {
        log.debug("create {} if not exists",
                kv("thumbnail folder", configuration.getThumbnailFolder()));

        FileUtils.forceMkdir(new File(configuration.getThumbnailFolder()));
    }

    /**
     * create a new thumbnail file if no cached file exists
     * 
     * @param photo
     * @return the created thumbnail
     * @throws IOException
     */
    private File createNewThumbnailOrGetCached(@NonNull Photo photo) throws IOException {
        log.debug("create thumbnail if not exists of {}", kv("photo", photo));

        var thumbnailPath =
                Path.of(configuration.getThumbnailFolder(), photo.getHashValue() + ".jpg");
        File thumbnailFile =
                Path.of(configuration.getThumbnailFolder(), photo.getHashValue() + ".jpg").toFile();

        if (!Files.exists(thumbnailPath)) {
            log.info("create {}", kv("thumbnail", thumbnailFile.getAbsolutePath()));
            Thumbnails.of(new File(photo.getFilePath())).width(configuration.getThumbnailWidth())
                    .toFile(thumbnailFile);
        }

        log.info("created {}", kv("thumbail", thumbnailFile.getAbsolutePath()));
        return thumbnailFile;
    }

    private String createThumbnailBase64(@NonNull File thumnailFile) throws IOException {
        return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(thumnailFile));
    }
}

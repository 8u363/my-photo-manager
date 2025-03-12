package my.photomanager.service;

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
import my.photomanager.photo.Photo;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Log4j2
public class ThumbnailService {

    private static final String THUMBNAIL_DIR = "thumbnails";

    public String getThumbnailBase64OfPhoto(@NonNull Photo photo) {
        var thumbnailBase64 = Strings.EMPTY;

        try {
            createThumbnailDirectoryIfNotexists();
            var thumbnailFile = getCachedThumbnailOrCreateNew(photo);
            thumbnailBase64 = createThumbnailBase64(thumbnailFile);
        } catch (IOException e) {
            log.warn("can not create thumbnail of {} because of {}",
                    kv("photo", photo.getFilePath()), kv("exception", e.getMessage()));
        }

        return thumbnailBase64;
    }

    private void createThumbnailDirectoryIfNotexists() throws IOException {
        FileUtils.forceMkdir(new File(THUMBNAIL_DIR));
    }

    private File getCachedThumbnailOrCreateNew(@NonNull Photo photo) throws IOException {
        var thumbnailPath = Path.of(THUMBNAIL_DIR, photo.getHashValue() + ".jpg");
        File thumbnailFile = thumbnailPath.toFile();

        if (!Files.exists(thumbnailPath)) {
            log.info("create {}", kv("thumbnail", thumbnailFile.getAbsolutePath()));
            Thumbnails.of(new File(photo.getFilePath())).width(150).toFile(thumbnailFile);
        }

        return thumbnailFile;
    }

    private String createThumbnailBase64(@NonNull File thumnailFile) throws IOException {
        return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(thumnailFile));
    }
}

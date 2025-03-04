package my.photomanager.photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.util.DigestUtils;
import lombok.NonNull;


public interface IPhotoFactory {

    Photo createPhoto(File photoFile) throws IOException;

    default String getPhotoHashValue(@NonNull File photoFile) throws IOException {
        return DigestUtils.md5DigestAsHex(new FileInputStream(photoFile));
    }
}

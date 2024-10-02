package my.photo_manager.service;

import lombok.NonNull;
import my.photo_manager.photo.Photo;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface IPhotoService<T extends Photo> {

    default String getHashValue(@NonNull File photoFile) throws IOException {
        return  DigestUtils.md5DigestAsHex(new FileInputStream(photoFile));
    }

    /**
     * get all photo files of the source folder
     *
     * @param sourceFolderPath the path of the source folder
     * @return a list with photo files
     */
    Collection<File> getAllPhotoFilesInSourceFolder(@NonNull Path sourceFolderPath);

    /**
     * build a photo object from the photo file
     *
     * @param photoFile the photo file
     * @return the build photo object
     */
    T buildPhotoObject(@NonNull File photoFile);


    /**
     * save the photo object
     *
     * @return the saved photo object
     */
    T savePhotoObject(@NonNull Photo photoObject);
}

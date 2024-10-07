package my.photo_manager.services;

import lombok.NonNull;
import my.photo_manager.model.photo.Photo;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

public interface IPhotoService<T extends Photo> {

    default String getHashValue(@NonNull File photoFile) throws IOException {
        return DigestUtils.md5DigestAsHex(new FileInputStream(photoFile));
    }

    default void buildAndSavePhotoObject(@NonNull File photoFile) {
        T photoObject = buildPhotoObject(photoFile);
        savePhotoObject(photoObject);
    }

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

    /**
     * @return a collection of all photos
     */
    Collection<T> getAll();
}

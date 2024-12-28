package my.photomanager.service.photo;

import java.io.File;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPhotoService<T extends Photo> {

	Page<T> getPhotos(int page, int size);

	Page<T> getPhotos(int page, int size, List<String> labelNames);

	T savePhoto(@NonNull File photoFile);

	T updatePhoto(long ID, List<? extends ILabel> labels);

	void deletePhoto(long ID);
}

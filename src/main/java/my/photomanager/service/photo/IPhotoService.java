package my.photomanager.service.photo;

import java.io.File;
import lombok.NonNull;
import my.photomanager.model.label.ILabel;
import my.photomanager.model.photo.Photo;
import org.springframework.data.domain.Page;

import java.nio.file.Path;
import java.util.List;

public interface IPhotoService<T extends Photo> {

	T savePhotoIfNotExists(@NonNull File photoFile);

	Page<T> getPhotos(int page, int size);

	Page<T> getPhotos(int page, int size, List<String> labelNames);

	List<? extends ILabel> getLabels();
}

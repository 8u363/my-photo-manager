package my.photoapi.service.photoservice;

import lombok.NonNull;
import my.photoapi.model.label.ILabel;
import my.photoapi.model.photo.Photo;
import org.springframework.data.domain.Page;

import java.nio.file.Path;
import java.util.List;

public interface IPhotoService<T extends Photo> {

    T savePhotoIfNotExists(@NonNull Path photoFilePath);

    Page<T> getPhotos(int page, int size);

    Page<T> getPhotos(int page, int size, List<ILabel> labels);
}

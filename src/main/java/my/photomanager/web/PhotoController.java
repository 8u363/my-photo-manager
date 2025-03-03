package my.photomanager.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.NonNull;
import my.photomanager.photo.DefaultPhotoFactory;
import my.photomanager.photo.Photo;
import my.photomanager.service.PhotoService;

@RestController
@RequestMapping
public class PhotoController {

    private final PhotoService photoService;
    private final DefaultPhotoFactory photoFactory;

    protected PhotoController(@NonNull PhotoService photoService,
            @NonNull DefaultPhotoFactory photoFactory) {
        this.photoService = photoService;
        this.photoFactory = photoFactory;
    }

    @GetMapping(value = "/photos")
    public List<PhotoDTO> getPhotos(@RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size) {
        return photoService.getPhotos(page, size);
    }

    @PutMapping("/save")
    public Photo savePhoto(
            @RequestParam(value = "filePath", required = true) @NonNull String filePath)
            throws IOException {
        var photo = photoFactory.createPhoto(new File(filePath));

        return photoService.savePhoto(photo);
    }

    @DeleteMapping("/delete")
    public void deletePhoto(@RequestParam(value = "id", required = true) long id) {
        photoService.deletePhoto(id);
    }
}

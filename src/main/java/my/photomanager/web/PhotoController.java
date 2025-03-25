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
import my.photomanager.filter.FilterService;
import my.photomanager.photo.DefaultPhotoFactory;
import my.photomanager.photo.Photo;
import my.photomanager.service.PhotoService;

@RestController
@RequestMapping
public class PhotoController {

    private final PhotoService photoService;
    private final DefaultPhotoFactory photoFactory;
    private final FilterService filterService;

    protected PhotoController(@NonNull PhotoService photoService,
            @NonNull DefaultPhotoFactory photoFactory, @NonNull FilterService filterService) {
        this.photoService = photoService;
        this.photoFactory = photoFactory;
        this.filterService = filterService;
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

        var savedPhoto = photoService.savePhoto(photo);
        filterService.updateFilterOptions(savedPhoto);
        return savedPhoto;
    }

    @DeleteMapping("/delete")
    public void deletePhoto(@RequestParam(value = "id", required = true) long id) {
        photoService.deletePhoto(id);
    }
}

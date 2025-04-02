package my.photomanager.photo;

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

@RestController
@RequestMapping
public class PhotoController {

    private final PhotoService photoService;
    private final FilterService filterService;

    protected PhotoController(@NonNull PhotoService photoService,
            @NonNull FilterService filterService) {
        this.photoService = photoService;
        this.filterService = filterService;
    }

    @GetMapping(value = "/photos")
    public List<PhotoDTO> getPhotos(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "100") int size) {
        return photoService.getPhotos(page, size);
    }

    @PutMapping("/save")
    public Photo savePhoto(
            @RequestParam(value = "filePath", required = true) @NonNull String filePath)
            throws IOException {
        var photo = photoService.createPhoto(new File(filePath));

        var savedPhoto = photoService.savePhoto(photo);
        filterService.updateFilterOptions(savedPhoto);
        return savedPhoto;
    }

    @DeleteMapping("/delete")
    public void deletePhoto(@RequestParam(value = "id", required = true) long id) {
        photoService.deletePhoto(id);
    }
}

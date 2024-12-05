package my.photoapi.controller;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import my.photoapi.model.photo.Photo;
import my.photoapi.service.photoservice.PhotoService;
import my.photoapi.web.PhotoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Base64;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/photos")
    public List<PhotoDTO> getPhotos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "25") int size,
                                    @RequestParam(value = "labels", required = false, defaultValue = "25") List<String> labels) {
        return photoService.getPhotos(page, size, labels).
                stream().
                map(this::mapPhotoToDTO).
                toList();
    }

    @SneakyThrows
    private PhotoDTO mapPhotoToDTO(@NonNull Photo photo) {
        return new PhotoDTO(photo.getID(), Base64.getEncoder().encodeToString(readFileToByteArray(new File(photo.getFilePath()))));
    }
}

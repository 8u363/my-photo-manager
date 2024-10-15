package my.photo_manager.controller;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import my.photo_manager.filter.IFilter;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.services.PhotoFilterService;
import my.photo_manager.web.FilterDTO;
import my.photo_manager.web.PhotoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Base64;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoController {

    private final PhotoFilterService filterService;

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("filter", filterService.getFilterList()
                .stream()
                .map(this::mapFilterToDTO)
                .collect(Collectors.toList()));

        model.addAttribute("photos", filterService.filter()
                .stream()
                .map(this::mapPhotoToDTO)
                .collect(Collectors.toList()));

        return "index";
    }

    @SneakyThrows
    private PhotoDTO mapPhotoToDTO(@NonNull Photo photo) {
        return new PhotoDTO(photo.getID(), Base64.getEncoder().encodeToString(readFileToByteArray(new File(photo.getFilePath()))));
    }

    private FilterDTO mapFilterToDTO(@NonNull IFilter filter) {
        return new FilterDTO(filter.getCategory(), filter.getText());
    }
}

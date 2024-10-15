package my.photo_manager.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import my.photo_manager.services.PhotoFilterService;
import my.photo_manager.web.FilterDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Profile("dev")
public class DevController {

    private final PhotoFilterService metaDataFilterService;

    @GetMapping("/getAllFilter")
    public List<FilterDTO> getAllFilter() {
        return metaDataFilterService.getFilterList()
                .stream()
                .map(filter -> new FilterDTO(filter.getCategory(), filter.getText()))
                .toList();
    }
}

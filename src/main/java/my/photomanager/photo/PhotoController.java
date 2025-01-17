package my.photomanager.photo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.collect.Lists;

public class PhotoController {

    @GetMapping
    public List<PhotoDTO> getPhotos(){
        return Lists.newArrayList();
    }
    
}

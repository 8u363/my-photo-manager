package my.photomanager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

@Configuration

public class PhotoManagerConfiguration {

    @Value("${photomanager.thumbnail.folder:thumbnails}")
    @Getter
    private String thumbnailFolder;

    @Value("${photomanager.thumbnail.width:150}")
    @Getter
    private int thumbnailWidth;

}

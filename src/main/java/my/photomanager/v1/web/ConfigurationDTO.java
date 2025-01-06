package my.photomanager.v1.web;

import lombok.NonNull;

public record ConfigurationDTO (long ID, @NonNull String folderPath, @NonNull String scanInterval){

}

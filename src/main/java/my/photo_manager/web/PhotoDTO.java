package my.photo_manager.web;

import lombok.NonNull;

public record PhotoDTO (long ID, @NonNull String rawData){
}

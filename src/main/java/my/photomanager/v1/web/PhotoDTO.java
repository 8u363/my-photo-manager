package my.photomanager.v1.web;

import lombok.NonNull;

public record PhotoDTO(long ID, @NonNull String rawData){
}

package my.photoapi.web;

import lombok.NonNull;

public record PhotoDTO(long ID, @NonNull String rawData){
}

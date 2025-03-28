package my.photomanager.photo;

import lombok.NonNull;

public record PhotoDTO(long id, @NonNull String photoData) {

}

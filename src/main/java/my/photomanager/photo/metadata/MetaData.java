package my.photomanager.photo.metadata;

import lombok.NonNull;

public record MetaData(int width, int height, @NonNull String creationTimeStamp, double longitude,
                double latitude) {

}

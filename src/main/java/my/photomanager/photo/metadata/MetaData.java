package my.photomanager.photo.metadata;

import java.time.LocalDate;
import lombok.NonNull;

public record MetaData(int width, int height, @NonNull LocalDate creationDate, double longitude,
                double latitude) {

}

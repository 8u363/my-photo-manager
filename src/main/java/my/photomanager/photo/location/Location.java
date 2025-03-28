package my.photomanager.photo.location;

import lombok.NonNull;

public record Location(@NonNull String country, @NonNull String city, @NonNull String postalCode,
                @NonNull String road) {
}

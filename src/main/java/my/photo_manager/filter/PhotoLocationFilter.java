package my.photo_manager.filter;

import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import my.photo_manager.model.metadata.PhotoMetaData;
import my.photo_manager.model.photo.Photo;

@Getter
@ToString
public class PhotoLocationFilter implements IFilter {

    private String text;
    private final String country;
    private final String city;
    private final String postalCode;
    private final String street;
    private final String houseNumber;

    public PhotoLocationFilter(@NonNull Photo photo) {
        country = photo.getMetaData().getCountry();
        city = photo.getMetaData().getCity();
        postalCode = photo.getMetaData().getPostCode();
        street = photo.getMetaData().getStreet();
        houseNumber = photo.getMetaData().getHouseNumber();

        text = country + " " + city + " " + postalCode + " " + street + " " + houseNumber;
        text = text.trim();
    }

    @VisibleForTesting
    public PhotoLocationFilter(@NonNull PhotoMetaData photoMetaData) {
        country = photoMetaData.getCountry();
        city = photoMetaData.getCity();
        postalCode = photoMetaData.getPostCode();
        street = photoMetaData.getStreet();
        houseNumber = photoMetaData.getHouseNumber();

        text = country + " " + city + " " + postalCode + " " + street + " " + houseNumber;
        text = text.trim();
    }

    @Override
    public FilterCategory getCategory() {
        return FilterCategory.LOCATION;
    }
}

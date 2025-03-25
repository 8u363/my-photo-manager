package my.photomanager.filter.locationFilter;

import org.apache.logging.log4j.util.Strings;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import my.photomanager.filter.IFilter;
import my.photomanager.photo.Photo;

@Builder(setterPrefix = "with")
@EqualsAndHashCode
@ToString
public class LocationFilter implements IFilter {

    @NonNull
    @Builder.Default
    private String country = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String city = Strings.EMPTY;

    @Builder.Default
    private boolean active = false;

    @Override
    public boolean test(@NonNull Photo photo) {
        return photo.getCountry().equals(country) && photo.getCity().equals(city);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive() {
        active = true;
    }

    @Override
    public void setInActive() {
        active = false;
    }
}

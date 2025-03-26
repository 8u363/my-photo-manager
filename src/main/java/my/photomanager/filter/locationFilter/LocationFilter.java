package my.photomanager.filter.locationFilter;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.util.function.Predicate;
import org.apache.logging.log4j.util.Strings;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import my.photomanager.filter.IFilter;
import my.photomanager.photo.Photo;

@Builder(setterPrefix = "with")
@EqualsAndHashCode
@ToString
@Log4j2
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
        Predicate<Photo> isCountryEmpty =
                filteredPhoto -> filteredPhoto.getCountry().equals(Strings.EMPTY);
        Predicate<Photo> isCountrySet = isCountryEmpty.negate();

        Predicate<Photo> isCityEmpty =
                filteredPhoto -> filteredPhoto.getCity().equals(Strings.EMPTY);
        Predicate<Photo> isCitySet = isCityEmpty.negate();

        Predicate<Photo> isCountryAndCitySet = isCountrySet.and(isCitySet);
        Predicate<Photo> isCountrySetAndCityEmpty = isCountrySet.and(isCityEmpty);
        Predicate<Photo> isCountryEmptyAndCitySet = isCountryEmpty.and(isCitySet);

        if (isCountryAndCitySet.test(photo))
            return photo.getCountry().equals(country) && photo.getCity().equals(city);

        if (isCountrySetAndCityEmpty.test(photo))
            return photo.getCountry().equals(country);

        if (isCountryEmptyAndCitySet.test(photo))
            return photo.getCity().equals(city);

        log.warn("no match found for {}", kv("photo", photo));
        return false;

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

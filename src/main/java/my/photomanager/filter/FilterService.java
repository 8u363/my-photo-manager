package my.photomanager.filter;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photomanager.filter.locationFilter.LocationFilter;
import my.photomanager.filter.orientationFilter.Orientation;
import my.photomanager.filter.orientationFilter.OrientationFilter;
import my.photomanager.filter.timeStampFilter.TimeStampFilter;
import my.photomanager.photo.Photo;

@Service
@Log4j2
public class FilterService {

    private Map<FilterCategory, Set<String>> filterOptions = Maps.newHashMap();

    private Map<FilterCategory, Set<? extends IFilter>> filterList = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    public Map<FilterCategory, Set<? extends IFilter>> updateFilterOptions(@NonNull Photo photo) {
        log.info("update filter options");

        HashSet<LocationFilter> locationFilters = new HashSet<>();
        if (filterList.containsKey(FilterCategory.LOCATION)) {
            locationFilters = (HashSet<LocationFilter>) filterList.get(FilterCategory.LOCATION);
        }
        locationFilters.add(createLocationFilter(photo));
        log.info("{}", kv("location filters", locationFilters));
        filterList.put(FilterCategory.LOCATION, locationFilters);

        HashSet<TimeStampFilter> timeStampFilters = new HashSet<>();
        if (filterList.containsKey(FilterCategory.CREATION_YEAR)) {
            timeStampFilters =
                    (HashSet<TimeStampFilter>) filterList.get(FilterCategory.CREATION_YEAR);
        }
        timeStampFilters.add(createTimeStampFilter(photo));
        log.info("{}", kv("timestamp filters", timeStampFilters));
        filterList.put(FilterCategory.CREATION_YEAR, timeStampFilters);

        HashSet<OrientationFilter> orientationFilters = new HashSet<>();
        if (filterList.containsKey(FilterCategory.ORIENTATION)) {
            orientationFilters =
                    (HashSet<OrientationFilter>) filterList.get(FilterCategory.ORIENTATION);
        }
        orientationFilters.add(createOrientationFilter(photo));
        log.info("{}", kv("orientation filters", orientationFilters));
        filterList.put(FilterCategory.ORIENTATION, orientationFilters);

        return filterList;
    }

    private TimeStampFilter createTimeStampFilter(@NonNull Photo photo) {
        var creationYear = Pattern.compile("\\d{4}").matcher(photo.getCreationTimeStamp()).results()
                .map(MatchResult::group).findAny().orElse(Strings.EMPTY);

        return TimeStampFilter.builder().withYear(creationYear).build();
    }

    private LocationFilter createLocationFilter(@NonNull Photo photo) {
        return LocationFilter.builder().withCountry(photo.getCountry()).withCity(photo.getCity())
                .build();
    }

    private OrientationFilter createOrientationFilter(@NonNull Photo photo) {
        var orientation = Orientation.LANDSCAPE;

        if (photo.getHeight() < photo.getWidth())
            orientation = Orientation.PORTRAIT;

        if (photo.getHeight() == photo.getWidth()) {
            orientation = Orientation.SQUARE;
        }

        return OrientationFilter.builder().withOrientation(orientation).build();

    }
}

package my.photomanager.filter;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import my.photomanager.filter.orientationfilter.Orientation;
import my.photomanager.filter.orientationfilter.OrientationFilter;
import my.photomanager.photo.Photo;

@Service
@Log4j2
public class FilterService {

    private Map<FilterCategory, Set<? extends IFilter>> filterList = Maps.newHashMap();

    /**
     * update the filter options
     * 
     * @param photo
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<FilterCategory, Set<? extends IFilter>> updateFilterOptions(@NonNull Photo photo) {
        log.info("update filter options");



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



    /**
     * crete orientation filter of photo filter
     * 
     * @param photo
     * @return the created orientation filter
     */
    private OrientationFilter createOrientationFilter(@NonNull Photo photo) {
        log.debug("create orientation filter of {}", kv("photo", photo));

        var orientation = Orientation.LANDSCAPE;

        if (photo.getHeight() < photo.getWidth())
            orientation = Orientation.PORTRAIT;

        if (photo.getHeight() == photo.getWidth()) {
            orientation = Orientation.SQUARE;
        }

        var orientationFilter = OrientationFilter.builder().withOrientation(orientation).build();

        log.info("created {}", kv("orientation filter", orientationFilter));
        return orientationFilter;
    }
}

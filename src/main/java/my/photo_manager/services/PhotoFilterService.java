package my.photo_manager.services;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photo_manager.filter.IFilter;
import my.photo_manager.filter.PhotoDimensionFilter;
import my.photo_manager.filter.PhotoLocationFilter;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public class PhotoFilterService {

    private final PhotoRepository repository;
    private final List<IFilter> filterList = Lists.newArrayList();
    private final List<IFilter> activeFilter = Lists.newArrayList();

    public List<Photo> filter() {
        if (activeFilter.isEmpty()) {
            return repository.findAll();
        } else {
            List<Photo> photos = Lists.newArrayList();
            var savedPhotos = repository.findAll();

            activeFilter
                    .forEach(activeFilter -> {
                        log.info("filter by {}", kv("activeFilter", activeFilter));

                        if (activeFilter instanceof PhotoDimensionFilter) {
                            var filter = (PhotoDimensionFilter) activeFilter;
                            photos.addAll(savedPhotos.stream()
                                    .filter(photo -> photo.getMetaData().getWidth() == filter.getWidth()
                                            && photo.getMetaData().getHeight() == filter.getHeight())
                                    .toList());
                        }

                        if (activeFilter instanceof PhotoLocationFilter) {
                            var filter = (PhotoLocationFilter) activeFilter;
                            photos.addAll(savedPhotos.stream()
                                    .filter(photo -> photo.getMetaData().getCountry().equals(filter.getCountry())
                                            && photo.getMetaData().getCity().equals(filter.getCity())
                                            && photo.getMetaData().getPostCode().equals(filter.getPostalCode())
                                            && photo.getMetaData().getStreet().equals(filter.getStreet())
                                            && photo.getMetaData().getHouseNumber().equals(filter.getHouseNumber()))
                                    .toList());
                        }
                    });
            return photos;
        }

    }

    public List<IFilter> getFilterList() {
        return filterList;
    }

    public void createFilterObject(@NonNull Photo photo) {

        Predicate<IFilter> isFilterTextEmpty = (newFilter) -> newFilter.getText().isEmpty();

        Predicate<IFilter> existsFilter = (newFilter) -> filterList.stream()
                .anyMatch(filter -> filter.getText().equals(newFilter.getText()));

        Lists.newArrayList(new PhotoDimensionFilter(photo), new PhotoLocationFilter(photo));

        Stream.of(new PhotoDimensionFilter(photo),
                new PhotoLocationFilter(photo)).filter(filter -> isFilterTextEmpty.negate().test(filter) && existsFilter.negate().test(filter)).forEach(filter -> {
            filterList.add(filter);
            log.info("create {} to filterlist", kv("filter", filter));
        });
    }

    public void activateFilter(@NonNull IFilter filter) {
        this.activeFilter.add(filter);
        log.info("add {} to activeFilterList", kv("filter", filter));
    }

    public void deactivateFilter(@NonNull IFilter filter) {
        this.activeFilter.remove(filter);
        log.info("remove {} to activeFilterList", kv("filter", filter));
    }
}

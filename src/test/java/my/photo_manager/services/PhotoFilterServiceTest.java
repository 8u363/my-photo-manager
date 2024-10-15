package my.photo_manager.services;

import my.photo_manager.TestUtils;
import my.photo_manager.filter.IFilter;
import my.photo_manager.filter.PhotoDimensionFilter;
import my.photo_manager.filter.PhotoLocationFilter;
import my.photo_manager.model.photo.Photo;
import my.photo_manager.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static my.photo_manager.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class PhotoFilterServiceTest {

    private PhotoFilterService filterService;

    @Mock
    private PhotoRepository repository;

    @BeforeEach
    void init() {
        filterService = new PhotoFilterService(repository);
    }

    @Test
    void shouldAvoidDuplicatedFilterElements() {
        var photo1 = Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath(TEST_FILE_PATH)
                .withMetaData(TestUtils.buildMetaDataWithGermanyLocation(768, 1024, "TEST_CREATION_TIME_STAMP"))
                .build();

        var photo2 = Photo.builder()
                .withHashValue(TEST_HASH_VALUE)
                .withFilePath(TEST_FILE_PATH)
                .withMetaData(buildMetaDataWithUSALocation(768, 1024, "TEST_CREATION_TIME_STAMP"))
                .build();

        filterService.createFilterObject(photo1);
        filterService.createFilterObject(photo2);

        List<IFilter> filterList = filterService.getFilterList();

        assertThat(filterList).isNotNull();
        assertThat(filterList.size()).isEqualTo(3);
        assertThat(filterList.stream().filter(filter -> filter.getClass().equals(PhotoDimensionFilter.class)).toList().size()).isEqualTo(1);
        assertThat(filterList.stream().filter(filter -> filter.getClass().equals(PhotoLocationFilter.class)).toList().size()).isEqualTo(2);
    }
}
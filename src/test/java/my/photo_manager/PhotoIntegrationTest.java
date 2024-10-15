package my.photo_manager;

import my.photo_manager.config.PhotoConfiguration;
import my.photo_manager.filter.PhotoDimensionFilter;
import my.photo_manager.filter.PhotoLocationFilter;
import my.photo_manager.repository.PhotoRepository;
import my.photo_manager.services.PhotoFilterService;
import my.photo_manager.services.PhotoMetaDataService;
import my.photo_manager.services.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static my.photo_manager.TestUtils.META_DATA_OF_PHOTO_WITH_EXIF_DATA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class PhotoIntegrationTest {

    private PhotoService photoService;

    @Mock
    private PhotoConfiguration configuration;

    @Autowired
    private PhotoRepository repository;

    @Autowired
    private PhotoMetaDataService metaDataService;

    @Autowired
    private PhotoFilterService filterService;

    @BeforeEach
    void init() {
        photoService = new PhotoService(configuration, repository, metaDataService, filterService);
    }

    @Test
    void shouldImportAndFilterPhotos() {
        given(configuration.getSource()).willReturn(List.of(Paths.get("src", "test", "resources", "TestPhotos").toString()));

        photoService.importPhotos();
        await()
                .atMost(20, SECONDS)
                .pollDelay(1, SECONDS)
                .until(() -> photoService.getAll().size() > 0);
        assertThat(filterService.getFilterList().size()).isEqualTo(3);

        // filter 2 photos by dimension
        var photoDimensionFilter = new PhotoDimensionFilter(META_DATA_OF_PHOTO_WITH_EXIF_DATA);
        assertThat(filterService.filter().size()).isEqualTo(3);
        filterService.activateFilter(photoDimensionFilter);
        assertThat(filterService.filter().size()).isEqualTo(2);

        filterService.deactivateFilter(photoDimensionFilter);
        assertThat(filterService.filter().size()).isEqualTo(3);

        // filter 1 photo by location
        var photoLocationFilter = new PhotoLocationFilter(META_DATA_OF_PHOTO_WITH_EXIF_DATA);
        filterService.activateFilter(photoLocationFilter);
        assertThat(filterService.filter().size()).isEqualTo(1);

        filterService.deactivateFilter(photoLocationFilter);
        assertThat(filterService.filter().size()).isEqualTo(3);
    }
}
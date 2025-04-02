package my.photomanager.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static my.photomanager.TestUtils.*;

import java.time.LocalDate;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import my.photomanager.filter.orientationfilter.Orientation;
import my.photomanager.filter.orientationfilter.OrientationFilter;
import my.photomanager.photo.Photo;

@ExtendWith(SpringExtension.class)
class FilterServiceTest {

        private FilterService filterService;



        private Photo photo1 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                        .withWidth(PHOTO_WIDTH).withHeight(768)
                        .withCreationDate(LocalDate.of(2025, 1, 1)).withCountry("COUNTRY1").build();

        private Photo photo2 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).withWidth(768)
                        .withHeight(1024).withCreationDate(LocalDate.of(2025, 1, 1))
                        .withCountry("COUNTRY2").build();

        private Photo photo3 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).withWidth(768)
                        .withHeight(768).withCreationDate(LocalDate.of(2023, 1, 1))
                        .withCountry("COUNTRY1").build();

        @BeforeEach
        void setup() {
                filterService = new FilterService();
        }


        @Test
        @SuppressWarnings("unchecked")
        void shouldUpdateOrientationFilterOptions() {
                filterService.updateFilterOptions(photo1);
                filterService.updateFilterOptions(photo2);
                var filterOptions = filterService.updateFilterOptions(photo3);

                assertThat(filterOptions).containsKey(FilterCategory.ORIENTATION);
                var orientationFilter = (Set<OrientationFilter>) filterOptions
                                .get(FilterCategory.ORIENTATION);

                assertThat(orientationFilter).usingRecursiveFieldByFieldElementComparator()
                                .containsExactlyInAnyOrder(
                                                OrientationFilter.builder().withOrientation(
                                                                Orientation.LANDSCAPE).build(),
                                                OrientationFilter.builder().withOrientation(
                                                                Orientation.PORTRAIT).build(),
                                                OrientationFilter.builder()
                                                                .withOrientation(Orientation.SQUARE)
                                                                .build());
        }
}

package my.photomanager.filter.creationdatefilter;

import static org.assertj.core.api.Assertions.assertThat;
import static my.photomanager.TestUtils.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import my.photomanager.photo.Photo;

class CreationDateFilterTest {

        @Test
        void shouldActivateFilter() {
                var creationDateFilter = CreationDateFilter.builder().build();

                assertThat(creationDateFilter.isActive()).isFalse();
                creationDateFilter.setActive();
                assertThat(creationDateFilter.isActive()).isTrue();
        }

        @Test
        void shouldDeactivateFilter() {
                var creationDateFilter = CreationDateFilter.builder().build();

                assertThat(creationDateFilter.isActive()).isFalse();
                creationDateFilter.setInactive();
                assertThat(creationDateFilter.isActive()).isFalse();
        }

        static Stream<Arguments> getFilterData() {
                var photoOfJanuary2025 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCreationDate(LocalDate.of(2025, Month.JANUARY, 1)).build();
                var photoOfJanuary2023 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCreationDate(LocalDate.of(2023, Month.JANUARY, 1)).build();
                var photoOfApril2023 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCreationDate(LocalDate.of(2023, Month.APRIL, 1)).build();

                return Stream.of(Arguments.of(photoOfJanuary2025, 2025, Month.JANUARY, true),
                                Arguments.of(photoOfJanuary2023, 2025, Month.JANUARY, false),
                                Arguments.of(photoOfApril2023, 2025, Month.JANUARY, false),

                                Arguments.of(photoOfJanuary2025, 2023, 0, false),
                                Arguments.of(photoOfJanuary2023, 2023, 0, true),
                                Arguments.of(photoOfApril2023, 2023, 0, true),

                                Arguments.of(photoOfJanuary2025, 0, Month.JANUARY, true),
                                Arguments.of(photoOfJanuary2023, 0, Month.JANUARY, true),
                                Arguments.of(photoOfApril2023, 0, 0, false));
        }

        @ParameterizedTest
        @MethodSource("getFilterData")
        void shouldTestPhotosWithCreationDate(Photo photo, int year, Month month,
                        boolean expectedTest) {

                var creationDateFilter = CreationDateFilter.builder().withMonth(month)
                                .withYear(year).build();

                assertThat(creationDateFilter.test(photo)).isEqualTo(expectedTest);
        }

}

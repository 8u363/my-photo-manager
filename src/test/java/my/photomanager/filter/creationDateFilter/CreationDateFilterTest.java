package my.photomanager.filter.creationDateFilter;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
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
                creationDateFilter.setInActive();
                assertThat(creationDateFilter.isActive()).isFalse();
        }

        static Stream<Arguments> getFilterData() {
                var PHOTO_HASH_VALUE = "1234567890";
                var PHOTO_FILE_PATH = "testPhoto.jpg";

                var photoOfJanuary2025 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCreationDate(LocalDate.of(2025, 1, 1)).build();
                var photoOfJanuary2023 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCreationDate(LocalDate.of(2023, 1, 1)).build();
                var photoOfApril2023 = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCreationDate(LocalDate.of(2023, 4, 1)).build();

                return Stream.of(Arguments.of(photoOfJanuary2025, 2025, 1, true),
                                Arguments.of(photoOfJanuary2023, 2025, 1, false),
                                Arguments.of(photoOfApril2023, 2025, 1, false),

                                Arguments.of(photoOfJanuary2025, 2023, 0, false),
                                Arguments.of(photoOfJanuary2023, 2023, 0, true),
                                Arguments.of(photoOfApril2023, 2023, 0, true),

                                Arguments.of(photoOfJanuary2025, 0, 1, true),
                                Arguments.of(photoOfJanuary2023, 0, 1, true),
                                Arguments.of(photoOfApril2023, 0, 0, false));
        }

        @ParameterizedTest
        @MethodSource("getFilterData")
        void shouldTestPhotosWithCreationDate(Photo photo, int year, int month,
                        boolean expectedTest) {

                var creationDateFilter = CreationDateFilter.builder().withMonth(month)
                                .withYear(year).build();

                assertThat(creationDateFilter.test(photo)).isEqualTo(expectedTest);
        }

}

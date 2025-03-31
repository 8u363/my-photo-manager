package my.photomanager.filter.locationFilter;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import my.photomanager.filter.locationfilter.LocationFilter;
import my.photomanager.photo.Photo;

class LocationFilterTest {

        @Test
        void shouldActivateFilter() {
                var locationFilter = LocationFilter.builder().build();

                assertThat(locationFilter.isActive()).isFalse();
                locationFilter.setActive();
                assertThat(locationFilter.isActive()).isTrue();
        }

        @Test
        void shouldDeactivateFilter() {
                var locationFilter = LocationFilter.builder().build();

                assertThat(locationFilter.isActive()).isFalse();
                locationFilter.setInactive();
                assertThat(locationFilter.isActive()).isFalse();
        }

        static Stream<Arguments> getFilterData() {
                var PHOTO_HASH_VALUE = "1234567890";
                var PHOTO_FILE_PATH = "testPhoto.jpg";
                var PHOTO_COUNTRY = "COUNTRY";
                var PHOTO_CITY = "CITY";

                var photoWithCountryAndCity = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCountry(PHOTO_COUNTRY).withCity(PHOTO_CITY).build();
                var photoWithCountyAndDifferentCity = Photo
                                .builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCountry(PHOTO_COUNTRY).withCity("DIFFERENT_CITY").build();
                var photoWithDiffenrentCountyAndCity = Photo
                                .builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCountry("DIFFERENT_COUNTRY").withCity(PHOTO_CITY).build();
                var photoWithoutCountyAndCity = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCity(PHOTO_CITY).build();
                var photoWithCountyAndWithoutCity = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withCountry(PHOTO_COUNTRY).build();

                return Stream.of(
                                Arguments.of(photoWithCountryAndCity, PHOTO_COUNTRY, PHOTO_CITY,
                                                true),
                                Arguments.of(photoWithCountyAndDifferentCity, PHOTO_COUNTRY,
                                                PHOTO_CITY, false),
                                Arguments.of(photoWithDiffenrentCountyAndCity, PHOTO_COUNTRY,
                                                PHOTO_CITY, false),
                                Arguments.of(photoWithoutCountyAndCity, PHOTO_COUNTRY, PHOTO_CITY,
                                                true),
                                Arguments.of(photoWithCountyAndWithoutCity, PHOTO_COUNTRY,
                                                PHOTO_CITY, true));
        }

        @ParameterizedTest
        @MethodSource("getFilterData")
        void shouldTestPhotos(Photo photo, String country, String city, boolean expectedTest) {
                var locationFilter = LocationFilter.builder().withCountry(country).withCity(city)
                                .build();

                assertThat(locationFilter.test(photo)).isEqualTo(expectedTest);
        }

}

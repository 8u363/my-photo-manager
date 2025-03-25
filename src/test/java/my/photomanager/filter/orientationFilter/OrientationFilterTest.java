package my.photomanager.filter.orientationFilter;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import my.photomanager.photo.Photo;

class OrientationFilterTest {

        @Test
        void shouldActivateFilter() {
                var orienationFilter = OrientationFilter.builder()
                                .withOrientation(Orientation.LANDSCAPE).build();

                assertThat(orienationFilter.isActive()).isFalse();
                orienationFilter.setActive();
                assertThat(orienationFilter.isActive()).isTrue();
        }

        @Test
        void shouldDeactivateFilter() {
                var orienationFilter = OrientationFilter.builder()
                                .withOrientation(Orientation.LANDSCAPE).build();

                assertThat(orienationFilter.isActive()).isFalse();
                orienationFilter.setInActive();
                assertThat(orienationFilter.isActive()).isFalse();
        }

        static Stream<Arguments> getFilterData() {
                var PHOTO_HASH_VALUE = "1234567890";
                var PHOTO_FILE_PATH = "testPhoto.jpg";
                var landscapePhoto = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE)
                                .withWidth(1024).withHeight(768).build();
                var portraitPhoto = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).withWidth(768)
                                .withHeight(1024).build();
                var squarePhoto = Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).withWidth(768)
                                .withHeight(768).build();

                return Stream.of(Arguments.of(landscapePhoto, Orientation.LANDSCAPE, true),
                                Arguments.of(landscapePhoto, Orientation.PORTRAIT, false),
                                Arguments.of(landscapePhoto, Orientation.SQUARE, false),
                                Arguments.of(portraitPhoto, Orientation.LANDSCAPE, false),
                                Arguments.of(portraitPhoto, Orientation.PORTRAIT, true),
                                Arguments.of(portraitPhoto, Orientation.SQUARE, false),
                                Arguments.of(squarePhoto, Orientation.LANDSCAPE, false),
                                Arguments.of(squarePhoto, Orientation.PORTRAIT, false),
                                Arguments.of(squarePhoto, Orientation.SQUARE, true));
        }

        @ParameterizedTest
        @MethodSource("getFilterData")
        void shouldTestPhotos(Photo photo, Orientation filterOrientation, boolean expectedTest) {
                var orienationFilter = OrientationFilter.builder()
                                .withOrientation(filterOrientation).build();

                assertThat(orienationFilter.test(photo)).isEqualTo(expectedTest);
        }
}

package my.photoapi.service.locationservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OpenMapServiceTest {

    private OpenMapService openMapService;

    @BeforeEach
    void setup() {
        openMapService = new OpenMapService();
    }

    @ParameterizedTest
    @MethodSource("getValidGpsData()")
    void createLocationWithValidGpsData(double latitude, double longitude) {
        // when
        var location = openMapService.createLocation(latitude, longitude);

        // then
        assertThat(location).isNotNull();
        assertThat(location.getCountry()).isNotEmpty();
        assertThat(location.getCity()).isNotEmpty();
        assertThat(location.getPostalCode()).isNotEmpty();
        assertThat(location.getStreet()).isNotEmpty();
    }

    private static Stream<Arguments> getValidGpsData() {
        return Stream.of(
                Arguments.of(48.86, 2.35),
                Arguments.of(45.23, -73.58),
                Arguments.of(-33.87, 151.21),
                Arguments.of(-22.90, -43.17)
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidGpsData")
    void createEmptyLocationWithNonExistingGPSData(long latitude, long longitude) {
        // when
        var location = openMapService.createLocation(latitude, longitude);

        // then
        assertThat(location).isNotNull();
        assertThat(location.getCountry()).isEmpty();
        assertThat(location.getCity()).isEmpty();
        assertThat(location.getPostalCode()).isEmpty();
        assertThat(location.getStreet()).isEmpty();
        assertThat(location.getHouseNumber()).isEmpty();
    }

    private final static Stream<Arguments> getInvalidGpsData() {
        return Stream.of(
                Arguments.of(91, 0),
                Arguments.of(-91, 0),
                Arguments.of(0, 181),
                Arguments.of(0, -181)
        );
    }
}
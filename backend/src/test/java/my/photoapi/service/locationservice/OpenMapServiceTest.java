package my.photoapi.service.locationservice;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static my.photoapi.TestUtils.UNIT_TEST;
import static org.assertj.core.api.Assertions.assertThat;


@Tag(UNIT_TEST)
@Log4j2
class OpenMapServiceTest {

    private OpenMapService openMapService;

    @BeforeAll
    static void startTest() {
        log.info("start {}", OpenMapServiceTest.class.getSimpleName());
    }

    @AfterAll
    static void finishTest() {
        log.info("finish {}", OpenMapServiceTest.class.getSimpleName());
    }

    @BeforeEach
    void setup() {
        openMapService = new OpenMapService();
    }

    @ParameterizedTest
    @CsvSource({"48.86, 2.35", "45.24, -73.56", "-33.87, 151.21", "-22.90, -43.17"})
    void should_return_location_object_from_latitude_and_longitude(double latitude, double longitude) {
        // when
        var location = openMapService.createLocationFromLatitudeAndLongitude(latitude, longitude);

        // then
        assertThat(location).isNotNull();
        assertThat(location.getCountry()).isNotEmpty();
        assertThat(location.getCity()).isNotEmpty();
        assertThat(location.getStreet()).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource({"91, 0", "-91, 0", "0, 181", "0, -181"})
    void should_return_empty_location_object_from_latitude_and_longitude(double latitude, double longitude) {
        // when
        var location = openMapService.createLocationFromLatitudeAndLongitude(latitude, longitude);

        // then
        assertThat(location).isNotNull();
        assertThat(location.getCountry()).isEmpty();
        assertThat(location.getCity()).isEmpty();
        assertThat(location.getPostalCode()).isEmpty();
        assertThat(location.getStreet()).isEmpty();
        assertThat(location.getHouseNumber()).isEmpty();
    }
}
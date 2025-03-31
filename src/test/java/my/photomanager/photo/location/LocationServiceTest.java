package my.photomanager.photo.location;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class LocationServiceTest {

    private final LocationService locationService = new LocationService();

    @Test
    void shouldCreateLocationObjectByLongitudeAndLatitude() {
        var location = locationService.createLocation(13.376193270526054, 52.51868001370377);

        assertThat(location).isNotNull();
        assertThat(location.country()).isEqualTo("Deutschland");
        assertThat(location.city()).isEqualTo("Berlin");
        assertThat(location.postalCode()).isEqualTo("10557");
        assertThat(location.road()).isEqualTo("Platz der Republik");
    }
}

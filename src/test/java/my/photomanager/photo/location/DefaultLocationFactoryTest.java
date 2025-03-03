package my.photomanager.photo.location;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class DefaultLocationFactoryTest {

    private final DefaultLocationFactory locationFactory = new DefaultLocationFactory();

    @Test
    void shouldCreateLocationObjectByLongitudeAndLatitude() {
        var location = locationFactory.createLocation(13.376193270526054, 52.51868001370377);

        assertThat(location).isNotNull();
        assertThat(location.country()).isEqualTo("Deutschland");
        assertThat(location.city()).isEqualTo("Berlin");
        assertThat(location.postalCode()).isEqualTo("10557");
        assertThat(location.road()).isEqualTo("Platz der Republik");
    }
}

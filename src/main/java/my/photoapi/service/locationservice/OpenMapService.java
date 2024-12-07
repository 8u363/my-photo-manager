package my.photoapi.service.locationservice;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Log4j2
public class OpenMapService implements ILocationService {

    @Override
    public ILocation createLocationFromLatitudeAndLongitude(double latitude, double longitude) {
        log.debug("create location from {}, {}", kv("latitude", latitude), kv("longitude", longitude));

        var openStreetMapURL = "https://nominatim.openstreetmap.org/reverse.php?lat=" + latitude + "&lon=" + longitude
                + "&format=jsonv2";
        log.debug("use {}", kv("openStreetMapURL", openStreetMapURL));

        var location = Location.builder().build();
        try {
            var jsonResponse = new JSONObject(IOUtils.toString(new URL(openStreetMapURL), StandardCharsets.UTF_8));
            if (jsonResponse.has("address")) {
                var addressJson = jsonResponse.getJSONObject("address");

                var country = addressJson.optString("country", Strings.EMPTY);
                var city = Strings.EMPTY;
                if (addressJson.has("city")){
                    city = addressJson.optString("city", Strings.EMPTY);
                }
                if (addressJson.has("village")){
                    city = addressJson.optString("village", Strings.EMPTY);
                }
                var postcode = addressJson.optString("postcode", Strings.EMPTY);
                var street = addressJson.optString("road", Strings.EMPTY);
                var houseNumber = addressJson.optString("house_number", Strings.EMPTY);

                location = Location.builder()
                        .withCountry(country)
                        .withCity(city)
                        .withPostalCode(postcode)
                        .withStreet(street)
                        .withHouseNumber(houseNumber)
                        .build();
            }
        } catch (IOException ignored) {
            // ignore this exception and return an empty location object
        }

        log.info("created {}", kv("location", location));
        return location;
    }
}

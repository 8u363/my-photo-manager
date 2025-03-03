package my.photomanager.photo.location;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultLocationFactory implements ILocationFactory {

    private static final String JSON_ADDRESS_KEY = "address";
    private static final String JSON_COUNTRY_KEY = "country";
    private static final String JSON_CITY_KEY = "city";
    private static final String JSON_POSTCODE_KEY = "postcode";
    private static final String JSON_ROAD_KEY = "road";

    @Override
    public Location createLocation(double longitude, double latitude) {
        log.debug("create location object of {} and {}", kv("longitude", longitude),
                kv("latitude", latitude));

        var addressDetails = fetchOpenStreetMapAdressDetails(longitude, latitude);
        log.debug("{}", kv("open steet map adress details", addressDetails));

        var location = new Location("", "", "", "");

        // check if addressDetails has address field
        if (addressDetails.has(JSON_ADDRESS_KEY)) {
            var addressJSON = addressDetails.getJSONObject(JSON_ADDRESS_KEY);
            log.debug("{}", kv("address json", addressJSON));

            var country = addressJSON.optString(JSON_COUNTRY_KEY);
            var city = addressJSON.optString(JSON_CITY_KEY);
            var postcode = addressJSON.optString(JSON_POSTCODE_KEY);
            var road = addressJSON.optString(JSON_ROAD_KEY);

            location = new Location(country, city, postcode, road);
        } else {
            log.warn("open steet map adress details of {} and {} contains no address field",
                    kv("longitude", longitude), kv("latitude", latitude));
        }
        log.info("created {}", kv("location object", location));

        return location;
    }

    /**
     * use longitude latitude to fetch address deztails from open street map in case of an error
     * return an empty json object
     * 
     * @param longitude
     * @param latitude
     * @return the json address details or an empty json object
     */
    private JSONObject fetchOpenStreetMapAdressDetails(double longitude, double latitude) {
        var openStreetMapURL = "https://nominatim.openstreetmap.org/reverse.php?lon=" + longitude
                + "&lat=" + latitude + "&format=jsonv2&accept-language=de";
        log.debug("{}", kv("open street map URL", openStreetMapURL));

        var addressDetails = new JSONObject();
        try {
            addressDetails = new JSONObject(
                    IOUtils.toString(new URL(openStreetMapURL), StandardCharsets.UTF_8));
        } catch (JSONException | IOException e) {
            log.info("an {} occurred during fetch address details from open street map",
                    kv("exception", e.getMessage()));
        }

        return addressDetails;
    }
}

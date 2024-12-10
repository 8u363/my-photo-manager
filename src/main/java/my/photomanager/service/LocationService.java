package my.photomanager.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LocationService implements ILocationService<Location> {

	@Override
	public Location buildLocationFromLongitudeAndLatitude(double longitude, double latitude) {
		log.debug("create location from {}, {}", kv("longitude", longitude), kv("latitude", latitude));

		var openStreetMapURL = "https://nominatim.openstreetmap.org/reverse.php?lat=" + latitude + "&lon=" + longitude
				+ "&format=jsonv2";
		log.debug("use {}", kv("openStreetMapURL", openStreetMapURL));

		var country = Strings.EMPTY;
		var city = Strings.EMPTY;
		var postcode = Strings.EMPTY;
		var street = Strings.EMPTY;
		var houseNumber = Strings.EMPTY;

		try {
			var jsonResponse = new JSONObject(IOUtils.toString(new URL(openStreetMapURL), StandardCharsets.UTF_8));
			if (jsonResponse.has("address")) {
				var addressJson = jsonResponse.getJSONObject("address");

				country = addressJson.optString("country", Strings.EMPTY);
				if (addressJson.has("city")) {
					city = addressJson.optString("city", Strings.EMPTY);
				}
				if (addressJson.has("village")) {
					city = addressJson.optString("village", Strings.EMPTY);
				}
				postcode = addressJson.optString("postcode", Strings.EMPTY);
				street = addressJson.optString("road", Strings.EMPTY);
				houseNumber = addressJson.optString("house_number", Strings.EMPTY);
			}
		} catch (IOException ignored) {
			// ignore this exception and return an empty location object
		}

		var location = new Location(country, city, postcode, street, houseNumber);
		log.info("created {}", kv("location", location));
		return location;
	}
}

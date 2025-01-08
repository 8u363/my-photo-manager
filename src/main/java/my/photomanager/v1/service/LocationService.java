package my.photomanager.v1.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocationService {

	/**
	 * build a location object
	 * use open street map to get the detailed data of the longitude and latitude
	 * 
	 * @param longitude the longitude
	 * @param latitude  the latitude
	 * @return the created location object
	 * @throws IOException
	 */
	protected Location createLocationObjectLocationOfLongitudeAndLatitude(double longitude, double latitude)
			throws IOException {
		log.info("create location object of {}, {}", kv("longitude", longitude), kv("latitude", latitude));

		var openStreetMapURL = "https://nominatim.openstreetmap.org/reverse.php?lat=" + latitude + "&lon=" + longitude
				+ "&format=jsonv2";
		log.debug("{}", kv("openStreetMapURL", openStreetMapURL));

		var country = Strings.EMPTY;
		var city = Strings.EMPTY;
		var postcode = Strings.EMPTY;
		var street = Strings.EMPTY;
		var houseNumber = Strings.EMPTY;

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

		var location = new Location(country, city, postcode, street, houseNumber);
		log.info("created {}", kv("location object", location));

		return location;
	}

	protected record Location(@NonNull String country, @NonNull String city, @NonNull String postalCode,
			@NonNull String street, @NonNull String houseNumber) {

	}
}

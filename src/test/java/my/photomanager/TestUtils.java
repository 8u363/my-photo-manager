package my.photomanager;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;
import my.photomanager.photo.Photo;

public class TestUtils {

        public static final String THUMBAIL_FOLDER = "testThumbnails";
        public static final int THUMBNAIL_WIDTH = 150;

        public static final long PHOTO_ID = 1L;
        public static final String PHOTO_HASH_VALUE = "1234567890";
        public static final String PHOTO_FILE_PATH = "testPhoto.jpg";
        public static final int PHOTO_HEIGHT = 768;
        public static final int PHOTO_WIDTH = 1024;

        public static final LocalDate PHOTO_CREATION_DATE_DECEMBER_2024 =
                        LocalDate.of(2024, Month.DECEMBER, 24);
        public static final LocalDate PHOTO_CREATION_DATE_DECEMBER_2025 =
                        LocalDate.of(2025, Month.DECEMBER, 24);
        public static final LocalDate PHOTO_CREATION_DATE_APRIL_2025 =
                        LocalDate.of(2025, Month.APRIL, 1);

        public static final String PHOTO_COUNTRY_GERMANY = "Deutschland";
        public static final String PHOTO_CITY_BERLIN = "Berlin";
        public static final String PHOTO_CITY_HAMBURG = "Hamburg";
        public static final String PHOTO_COUNTRY_FRANCE = "Frankreich";
        public static final String PHOTO_CITY_PARIS = "Paris";

        public static final Photo buildDefaultPhoto() {
                return Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).withHeight(PHOTO_HEIGHT)
                                .withWidth(PHOTO_WIDTH).build();
        }

        public static final Photo buildPhotoWithRandomUUID() {
                return Photo.builder(PHOTO_FILE_PATH, UUID.randomUUID().toString())
                                .withHeight(PHOTO_HEIGHT).withWidth(PHOTO_WIDTH).build();
        }

        public static final Photo buildPhoto(String hashValue, LocalDate creationDate) {
                return buildPhoto(hashValue, creationDate, null, null);
        }

        public static final Photo buildPhoto(String hashValue, String country, String city) {
                return buildPhoto(hashValue, null, country, city);
        }

        public static final Photo buildPhoto(String hashValue, LocalDate creationDate,
                        String country, String city) {
                var photo = buildDefaultPhoto().toBuilder().withHashValue(hashValue).build();

                if (creationDate != null) {
                        photo = photo.toBuilder().withCreationDate(creationDate).build();
                }

                if (country != null) {
                        photo = photo.toBuilder().withCountry(country).build();
                }

                if (city != null) {
                        photo = photo.toBuilder().withCity(city).build();
                }

                return photo;
        }
}

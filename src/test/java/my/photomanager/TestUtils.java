package my.photomanager;

import java.time.LocalDate;
import java.time.Month;
import my.photomanager.photo.Photo;

public class TestUtils {

        public static final String THUMBAIL_FOLDER = "testThumbnails";
        public static final int THUMBNAIL_WIDTH = 150;

        public static final long PHOTO_ID = 1L;
        public static final String PHOTO_HASH_VALUE = "1234567890";
        public static final String PHOTO_FILE_PATH = "testPhoto.jpg";
        public static final LocalDate PHOTO_CREATION_DATE = LocalDate.of(2025, Month.DECEMBER, 24);
        public static final int PHOTO_HEIGHT = 768;
        public static final int PHOTO_WIDTH = 1024;
        public static final String PHOTO_COUNTRY = "Deutschland";
        public static final String PHOTO_CITY = "Berlin";

        public static final Photo buildPhoto() {
                return buildPhoto(PHOTO_HEIGHT, PHOTO_WIDTH, PHOTO_CREATION_DATE, PHOTO_COUNTRY,
                                PHOTO_CITY);
        }

        public static Photo buildPhoto(int height, int width, LocalDate creationDate,
                        String country, String city) {
                return Photo.builder(PHOTO_FILE_PATH, PHOTO_HASH_VALUE).withHeight(height)
                                .withWidth(width).withCreationDate(creationDate)
                                .withCountry(country).withCity(city).build();
        }
}

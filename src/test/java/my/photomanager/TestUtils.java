package my.photomanager;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.UUID;
import my.photomanager.photo.Photo;

public class TestUtils {

        public static final String THUMBAIL_FOLDER = "testThumbnails";
        public static final int THUMBNAIL_WIDTH = 150;

        public static final long PHOTO_ID = 1L;
        public static final String PHOTO_HASH_VALUE = "1234567890";
        public static final String PHOTO_FILE_PATH = "testPhoto.jpg";
        public static final int PHOTO_HEIGHT_768 = 768;
        public static final int PHOTO_WIDTH_1024 = 1024;
        public static final LocalDate PHOTO_CREATION_DATE = LocalDate.of(2025, Month.JANUARY, 1);

        public static Photo buildDefaultPhoto() {
                return buildPhoto(PHOTO_HASH_VALUE, PHOTO_CREATION_DATE, PHOTO_WIDTH_1024,
                                PHOTO_HEIGHT_768, PHOTO_HASH_VALUE, PHOTO_FILE_PATH);
        }

        public static final Photo buildPhoto(String hashValue, LocalDate creationDate,
                        int photoHeight, int photoWidth, String country, String city) {
                return Photo.builder(PHOTO_FILE_PATH, hashValue).withCreationDate(creationDate)
                                .withHeight(photoHeight).withWidth(photoWidth).withCountry(country)
                                .withCity(city).build();
        }
}

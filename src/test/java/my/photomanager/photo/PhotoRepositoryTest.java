package my.photomanager.photo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static my.photomanager.TestUtils.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import com.google.common.collect.Lists;
import my.photomanager.TestUtils;

@DataJpaTest
@Log4j2
class PhotoRepositoryTest {

        @Autowired
        private PhotoRepository repository;

        @Test
        void shouldThrowExceptionWhenConstraintsCheckFailed() {
                repository.saveAndFlush(TestUtils.buildDefaultPhoto());

                assertThrowsExactly(DataIntegrityViolationException.class,
                                () -> repository.saveAndFlush(TestUtils.buildDefaultPhoto()));
        }

        @Nested
        class FilterTests {
                // Test paramater
                int PHOTO_HEIGHT_1024 = 1024;
                static int PHOTO_HEIGHT_768 = 768;

                static int PHOTO_WIDTH_1024 = 1024;
                static int PHOTO_WIDTH_768 = 768;

                static String COUNTRY_A = "COUNTRY_A";
                static String COUNTRY_B = "COUNTRY_B";

                static String CITY_A = "CITY_A";
                static String CITY_B = "CITY_B";

                static Month CREATION_MONTH_JULY = Month.JULY;
                static Month CREATION_MONTH_DECEMBER = Month.DECEMBER;

                static int CREATION_YEAR_2023 = 2023;
                static int CREATION_YEAR_2024 = 2024;

                static LocalDate PHOTO_CREATION_DATE_JULY_2023 =
                                LocalDate.of(CREATION_YEAR_2023, CREATION_MONTH_JULY, 1);
                static LocalDate PHOTO_CREATION_DATE_DECEMBER_2023 =
                                LocalDate.of(CREATION_YEAR_2023, CREATION_MONTH_DECEMBER, 1);
                static LocalDate PHOTO_CREATION_DATE_JULY_2024 =
                                LocalDate.of(CREATION_YEAR_2024, CREATION_MONTH_JULY, 1);
                static LocalDate PHOTO_CREATION_DATE_DECEMBER_2024 =
                                LocalDate.of(CREATION_YEAR_2024, CREATION_MONTH_DECEMBER, 1);

                // Test photos
                static Photo PHOTO_1;
                static Photo PHOTO_2;
                static Photo PHOTO_3;
                static Photo PHOTO_4;
                static Photo PHOTO_5;

                @BeforeEach
                void setup() {
                        // July 2023, 1024x768, countryA, cityA
                        PHOTO_1 = buildPhoto(UUID.randomUUID().toString(),
                                        PHOTO_CREATION_DATE_JULY_2023, PHOTO_HEIGHT_1024,
                                        PHOTO_WIDTH_768, COUNTRY_A, CITY_A);

                        // DECEMBER 2023, 768x1024, countryA, cityA
                        PHOTO_2 = buildPhoto(UUID.randomUUID().toString(),
                                        PHOTO_CREATION_DATE_DECEMBER_2023, PHOTO_HEIGHT_768,
                                        PHOTO_WIDTH_1024, COUNTRY_A, CITY_A);

                        // July 2023, 1024x768, countryB, cityA
                        PHOTO_3 = buildPhoto(UUID.randomUUID().toString(),
                                        PHOTO_CREATION_DATE_JULY_2023, PHOTO_HEIGHT_1024,
                                        PHOTO_WIDTH_768, COUNTRY_B, CITY_A);

                        // DECEMBER 2023, 768x1024, countryA, cityB
                        PHOTO_4 = buildPhoto(UUID.randomUUID().toString(),
                                        PHOTO_CREATION_DATE_DECEMBER_2023, PHOTO_HEIGHT_768,
                                        PHOTO_WIDTH_1024, COUNTRY_A, CITY_B);

                        // July 2024, 768x768, countryB, cityA
                        PHOTO_5 = buildPhoto(UUID.randomUUID().toString(),
                                        PHOTO_CREATION_DATE_JULY_2024, PHOTO_HEIGHT_768,
                                        PHOTO_WIDTH_768, COUNTRY_B, CITY_A);


                        repository.deleteAll();
                        repository.saveAllAndFlush(Arrays.asList(PHOTO_1, PHOTO_2, PHOTO_3, PHOTO_3,
                                        PHOTO_4, PHOTO_5));
                }

                static Stream<Arguments> getFilterParameter() {
                        return Stream.of(
                                        Arguments.of(CREATION_YEAR_2023, null, null, null, null,
                                                        Lists.newArrayList(PHOTO_1, PHOTO_2,
                                                                        PHOTO_3, PHOTO_4)),

                                        Arguments.of(CREATION_YEAR_2023, Month.JULY, null, null,
                                                        null, Lists.newArrayList(PHOTO_1, PHOTO_3)),

                                        Arguments.of(CREATION_YEAR_2023, Month.DECEMBER, null,
                                                        COUNTRY_A, null,
                                                        Lists.newArrayList(PHOTO_2, PHOTO_4)),

                                        Arguments.of(CREATION_YEAR_2023, Month.DECEMBER, null,
                                                        COUNTRY_A, CITY_B,
                                                        Lists.newArrayList(PHOTO_4)),

                                        Arguments.of(null, null, Orientation.LANDSCAPE, null, null,
                                                        Lists.newArrayList(PHOTO_1, PHOTO_3)),

                                        Arguments.of(null, null, Orientation.SQUARE, null, null,
                                                        Lists.newArrayList(PHOTO_5)));
                }

                @ParameterizedTest
                @MethodSource("getFilterParameter")
                void shouldFindPhotos(Integer creationYear, Month creationMonth,
                                Orientation orientation, String country, String city,
                                List<Photo> expectedPhotos) {



                        var spec = PhotoRepositorySpecification.findBy(country, city, creationYear,
                                        creationMonth, orientation);

                        var filteredPhotos = repository.findAll(spec);

                        assertThat(filteredPhotos).hasSameSizeAs(expectedPhotos);
                        filteredPhotos.stream().forEach(expectedPhoto -> assertThat(
                                        filteredPhotos.contains(expectedPhoto)));
                }

        }
}

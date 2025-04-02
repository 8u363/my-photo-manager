package my.photomanager.photo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static my.photomanager.TestUtils.*;

import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import my.photomanager.TestUtils;

@DataJpaTest
class PhotoRepositoryTest {

        @Autowired
        private PhotoRepository repository;

        // test photos without location and date
        static Photo testPhotoOfApril2025 = TestUtils.buildPhoto(UUID.randomUUID().toString(),
                        PHOTO_CREATION_DATE_APRIL_2025);
        static Photo testPhotoOfDecember2025 = TestUtils.buildPhoto(UUID.randomUUID().toString(),
                        PHOTO_CREATION_DATE_DECEMBER_2025);
        static Photo testPhotoOfDecember2024 = TestUtils.buildPhoto(UUID.randomUUID().toString(),
                        PHOTO_CREATION_DATE_DECEMBER_2024);

        // test photos with location without date
        static Photo testPhotoOfBerlinInGermany = TestUtils.buildPhoto(UUID.randomUUID().toString(),
                        PHOTO_COUNTRY_GERMANY, PHOTO_CITY_BERLIN);
        static Photo testPhotoOfHamburgInGermany = TestUtils.buildPhoto(
                        UUID.randomUUID().toString(), PHOTO_COUNTRY_GERMANY, PHOTO_CITY_HAMBURG);
        static Photo testPhotoOfParisInFrance = TestUtils.buildPhoto(UUID.randomUUID().toString(),
                        PHOTO_COUNTRY_FRANCE, PHOTO_CITY_PARIS);

        // test photos with location and date
        static Photo testPhotoOfBerlinInGermanyOfApril2025 = TestUtils.buildPhoto(
                        UUID.randomUUID().toString(), PHOTO_CREATION_DATE_APRIL_2025,
                        PHOTO_COUNTRY_GERMANY, PHOTO_CITY_BERLIN);
        static Photo testPhotoOfHamburgInGermanyOfDecember2025 = TestUtils.buildPhoto(
                        UUID.randomUUID().toString(), PHOTO_CREATION_DATE_DECEMBER_2025,
                        PHOTO_COUNTRY_GERMANY, PHOTO_CITY_HAMBURG);
        static Photo testPhotoOfParisInFranceOfDecember2024 = TestUtils.buildPhoto(
                        UUID.randomUUID().toString(), PHOTO_CREATION_DATE_DECEMBER_2024,
                        PHOTO_COUNTRY_FRANCE, PHOTO_CITY_PARIS);

        @BeforeEach
        void setup() {
                // test photos without location and date
                repository.saveAndFlush(TestUtils.buildPhoto(testPhotoOfApril2025.getHashValue(),
                                PHOTO_CREATION_DATE_APRIL_2025));
                repository.saveAndFlush(TestUtils.buildPhoto(testPhotoOfDecember2025.getHashValue(),
                                PHOTO_CREATION_DATE_DECEMBER_2025));
                repository.saveAndFlush(TestUtils.buildPhoto(testPhotoOfDecember2024.getHashValue(),
                                PHOTO_CREATION_DATE_DECEMBER_2024));

                // test photos with location without date
                repository.saveAndFlush(
                                TestUtils.buildPhoto(testPhotoOfBerlinInGermany.getHashValue(),
                                                PHOTO_COUNTRY_GERMANY, PHOTO_CITY_BERLIN));
                repository.saveAndFlush(
                                TestUtils.buildPhoto(testPhotoOfHamburgInGermany.getHashValue(),
                                                PHOTO_COUNTRY_GERMANY, PHOTO_CITY_HAMBURG));
                repository.saveAndFlush(
                                TestUtils.buildPhoto(testPhotoOfParisInFrance.getHashValue(),
                                                PHOTO_COUNTRY_FRANCE, PHOTO_CITY_PARIS));

                // test photos with location and date
                repository.saveAndFlush(TestUtils.buildPhoto(
                                testPhotoOfBerlinInGermanyOfApril2025.getHashValue(),
                                PHOTO_CREATION_DATE_APRIL_2025, PHOTO_COUNTRY_GERMANY,
                                PHOTO_CITY_BERLIN));
                repository.saveAndFlush(TestUtils.buildPhoto(
                                testPhotoOfHamburgInGermanyOfDecember2025.getHashValue(),
                                PHOTO_CREATION_DATE_DECEMBER_2025, PHOTO_COUNTRY_GERMANY,
                                PHOTO_CITY_HAMBURG));
                repository.saveAndFlush(TestUtils.buildPhoto(
                                testPhotoOfParisInFranceOfDecember2024.getHashValue(),
                                PHOTO_CREATION_DATE_DECEMBER_2024, PHOTO_COUNTRY_FRANCE,
                                PHOTO_CITY_PARIS));
        }

        @AfterEach
        void cleanUp() {
                repository.deleteAll();
        }

        @Test
        void shouldThrowExceptionWhenConstraintsCheckFailed() {
                repository.saveAndFlush(TestUtils.buildDefaultPhoto());

                assertThrowsExactly(DataIntegrityViolationException.class,
                                () -> repository.saveAndFlush(TestUtils.buildDefaultPhoto()));
        }



        private static Stream<Arguments> getFilterParameter() {
                return Stream.of(
                                // all photos of 2025
                                Arguments.of(null, null, 2025, null, List.of(testPhotoOfApril2025,
                                                testPhotoOfDecember2025,
                                                testPhotoOfBerlinInGermanyOfApril2025,
                                                testPhotoOfHamburgInGermanyOfDecember2025)),

                                // all photos of december
                                Arguments.of(null, null, null, Month.DECEMBER, List.of(
                                                testPhotoOfDecember2025, testPhotoOfDecember2024,
                                                testPhotoOfHamburgInGermanyOfDecember2025,
                                                testPhotoOfParisInFranceOfDecember2024)),

                                // all photos of december 2024
                                Arguments.of(null, null, 2024, Month.DECEMBER, List.of(
                                                testPhotoOfDecember2024,
                                                testPhotoOfParisInFranceOfDecember2024)),

                                // all photos of germany
                                Arguments.of(PHOTO_COUNTRY_GERMANY, null, null, null, List.of(
                                                testPhotoOfBerlinInGermany,
                                                testPhotoOfHamburgInGermany,
                                                testPhotoOfBerlinInGermanyOfApril2025,
                                                testPhotoOfHamburgInGermanyOfDecember2025)),

                                // all photos of germany berlin
                                Arguments.of(PHOTO_COUNTRY_GERMANY, PHOTO_CITY_BERLIN, null, null,
                                                List.of(testPhotoOfBerlinInGermany,
                                                                testPhotoOfBerlinInGermanyOfApril2025)),

                                // all photos of germany of 2025
                                Arguments.of(PHOTO_COUNTRY_GERMANY, null, 2025, null, List.of(
                                                testPhotoOfBerlinInGermanyOfApril2025,
                                                testPhotoOfHamburgInGermanyOfDecember2025)),

                                // all photos of germany in december 2025
                                Arguments.of(PHOTO_COUNTRY_GERMANY, null, 2025, Month.DECEMBER, List
                                                .of(testPhotoOfHamburgInGermanyOfDecember2025)));
        }

        @ParameterizedTest
        @MethodSource("getFilterParameter")
        void shouldFindPhotosByParameters(String country, String city, Integer year, Month month,
                        List<Photo> filteredPhotos) {
                var spec = PhotoReposiorySpecification.findBy(country, city, year, month);
                var filterResult = repository.findAll(spec);

                assertThat(filteredPhotos).hasSameSizeAs(filterResult);
                filteredPhotos.stream().forEach(
                                expectedPhoto -> assertThat(filterResult.contains(expectedPhoto)));
        }
}

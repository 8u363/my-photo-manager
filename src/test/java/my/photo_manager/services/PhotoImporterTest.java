package my.photo_manager.services;

import my.photo_manager.config.PhotoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class PhotoImporterTest {

    private PhotoImporter importer;

    @Autowired
    private PhotoService photoService;

    @Mock
    private PhotoConfiguration configuration;

    @BeforeEach
    void init() {
        importer = new PhotoImporter(photoService, configuration);
    }


    @Test
    void shouldImportPhotosFromSourceFolder() {
        given(configuration.getSource()).willReturn(List.of(Paths.get("src", "test", "resources", "TestPhotos").toString()));

        importer.importPhotos();
        assertThat(photoService.getAll().size()).isEqualTo(3);
    }

}
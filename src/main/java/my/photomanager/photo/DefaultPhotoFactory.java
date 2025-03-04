package my.photomanager.photo;

import static net.logstash.logback.argument.StructuredArguments.kv;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import my.photomanager.photo.location.DefaultLocationFactory;
import my.photomanager.photo.metadata.DefaultMetaDataFactory;

@Service
@Log4j2
public class DefaultPhotoFactory implements IPhotoFactory {

        private final DefaultLocationFactory locationFactory = new DefaultLocationFactory();
        private final DefaultMetaDataFactory metaDataFactory = new DefaultMetaDataFactory();

        @Override
        public Photo createPhoto(File photoFile) throws IOException {
                log.debug("create photo  object of {}",
                                kv("photo file", photoFile.getAbsolutePath()));

                var metaData = metaDataFactory.createMetaData(photoFile);
                log.debug("{}", kv("meta data", metaData));

                var locationData = locationFactory.createLocation(metaData.longitude(),
                                metaData.latitude());
                log.debug("{}", kv("location data", locationData));

                var photo = Photo.builder(photoFile.getAbsolutePath(), getPhotoHashValue(photoFile))
                                .withHeight(metaData.height()).withWidth(metaData.width())
                                .withCreationTimeStamp(metaData.creationTimeStamp())
                                .withCountry(locationData.country()).withCity(locationData.city())
                                .withPostalCode(locationData.postalCode())
                                .withRoad(locationData.road()).build();
                log.info("created {}", kv("photo object", photo));

                return photo;
        }

}

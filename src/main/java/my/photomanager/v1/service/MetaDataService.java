package my.photomanager.v1.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.imaging.Imaging;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MetaDataService {

	public MetaData getMetaDataOfPhotoFile(@NonNull File photoFile) {
		log.debug("get meta data of {}", kv("photoFilePath", photoFile));

		var photoHeight = getPhotoHeight(photoFile);
		var photoWidth = getPhotoWidth(photoFile);
		var photoCreationTimeStamp = readCreationTimeStampFromPhotoMetaData(photoFile);
		var longitude = readLongitudeFromPhotoMetaData(photoFile);
		var latitude = readLatitudeFromPhotoMetaData(photoFile);

		var metaData = new MetaData(photoWidth, photoHeight, photoCreationTimeStamp, longitude, latitude);
		log.info("{}", kv("meta data", metaData));

		return metaData;
	}

	private int getPhotoHeight(@NonNull File photo) {
		var height = Imaging.getImageInfo(photo)
				.getHeight();

		log.debug("{} has {}", kv("photo", photo), kv("height", height));
		return height;
	}

	private int getPhotoWidth(@NonNull File photo) throws IOException {
		var width = Imaging.getImageInfo(photo)
				.getWidth();

		log.debug("{} has {}", kv("photo", photo), kv("width", width));
		return width;
	}

	private record MetaData(int width, int height, @NonNull String creationTimeStamp, double longitude, double latitude) {

	}
};

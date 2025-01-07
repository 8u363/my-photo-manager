package my.photomanager.v1.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MetaDataService {

	public MetaData getMetaDataOfPhotoFile(@NonNull File photoFile) {
		log.debug("get meta data of {}", kv("photoFilePath", photoFile));

		var photoHeight = getPhotoHeight(photoFile);
		var photoWidth = getPhotoWidth(photoFile);
		var photoCreationTimeStamp = getPhotoCreationTimeStamp(photoFile);
		var longitude = readLongitudeFromPhotoMetaData(photoFile);
		var latitude = readLatitudeFromPhotoMetaData(photoFile);

		var metaData = new MetaData(photoWidth, photoHeight, photoCreationTimeStamp, longitude, latitude);
		log.info("{}", kv("meta data", metaData));

		return metaData;
	}

	private int getPhotoHeight(@NonNull File photoFile) throws IOException {
		return Imaging.getImageInfo(photoFile)
				.getHeight();
	}

	private int getPhotoWidth(@NonNull File photoFile) throws IOException {
		return Imaging.getImageInfo(photoFile)
				.getWidth();
	}

	private String getPhotoCreationTimeStamp(@NonNull File photoFile) throws IOException {
		var creationTimeStamp = Strings.EMPTY;

		var jpegImageMetadata = (JpegImageMetadata) Imaging.getMetadata(photoFile);
		if (jpegImageMetadata != null) {
			var exifValue = jpegImageMetadata.findExifValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
			if (exifValue != null) {
				creationTimeStamp = exifValue.getStringValue();
			}
		}
		return creationTimeStamp;
	}

	private record MetaData(int width, int height, @NonNull String creationTimeStamp, double longitude,
			double latitude) {

	}
};

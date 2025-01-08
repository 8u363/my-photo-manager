package my.photomanager.v1.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MetaDataService {

	/**
	 * read the meta data of the photo file
	 * create a meta data object with these informations
	 * 
	 * @param photoFile the photo file
	 * @return the created meta data object
	 */
	protected MetaData createMetaDataObjectOfPhotoFile(@NonNull File photoFile) {
		log.debug("create meta data object of {}", kv("photoFilePath", photoFile));

		var photoHeight = getPhotoHeight(photoFile);
		var photoWidth = getPhotoWidth(photoFile);
		var photoCreationTimeStamp = getPhotoCreationTimeStamp(photoFile);
		var longitude = readLongitudeFromPhotoMetaData(photoFile);
		var latitude = readLatitudeFromPhotoMetaData(photoFile);

		var metaData = new MetaData(photoWidth, photoHeight, photoCreationTimeStamp, longitude, latitude);
		log.info("created {}", kv("meta data object", metaData));

		return metaData;
	}

	private int getPhotoHeight(@NonNull File photoFile) {
		var photoHeight = 0;

		try {
			photoHeight = Imaging.getImageInfo(photoFile)
					.getHeight();
		} catch (IOException e) {
			log.warn("an exception occurred {}. will return 0 as photo height", e.getMessage());
		}

		return photoHeight;
	}

	private int getPhotoWidth(@NonNull File photoFile) {
		var photoWidth = 0;

		try {
			photoWidth = Imaging.getImageInfo(photoFile)
					.getWidth();
		} catch (IOException e) {
			log.warn("an exception occurred {}. will return 0 as photo width", e.getMessage());
		}

		return photoWidth;
	}

	private String getPhotoCreationTimeStamp(@NonNull File photoFile) {
		var creationTimeStamp = Strings.EMPTY;

		try {
			var jpegImageMetadata = (JpegImageMetadata) Imaging.getMetadata(photoFile);
			if (jpegImageMetadata != null) {
				var exifValue = jpegImageMetadata.findExifValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
				if (exifValue != null) {
					creationTimeStamp = exifValue.getStringValue();
				}
			}
		} catch (IllegalArgumentException | IOException e) {
			log.warn("an exception occurred {}. will return empty string as creation time stamp", e.getMessage());
		}

		return creationTimeStamp;
	}

	private double readLongitudeFromPhotoMetaData(@NonNull File photo) {
		var longitude = 0.0;

		try {
			var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photo);
			if (jpegImageMetaData != null) {
				var exifData = jpegImageMetaData.getExif();
				if (exifData != null) {
					var gpsInfo = exifData.getGpsInfo();
					if (gpsInfo != null) {
						longitude = gpsInfo.getLongitudeAsDegreesEast();
					}
				}
			}
		} catch (IllegalArgumentException | IOException e) {
			log.warn("an exception occurred {}. will return 0 as longitude", e.getMessage());
		}

		return longitude;
	}

	private double readLatitudeFromPhotoMetaData(@NonNull File photo) {
		var latitude = 0.0;

		try {
			var jpegImageMetaData = (JpegImageMetadata) Imaging.getMetadata(photo);
			if (jpegImageMetaData != null) {
				var exifData = jpegImageMetaData.getExif();
				if (exifData != null) {
					var gpsInfo = exifData.getGpsInfo();
					if (gpsInfo != null) {
						latitude = gpsInfo.getLatitudeAsDegreesNorth();
					}
				}
			}
		} catch (IllegalArgumentException | IOException e) {
			log.warn("an exception occurred {}. will return 0 as latitude", e.getMessage());
		}

		return latitude;
	}

	protected record MetaData(int width, int height, @NonNull String creationTimeStamp, double longitude,
			double latitude) {

	}
};

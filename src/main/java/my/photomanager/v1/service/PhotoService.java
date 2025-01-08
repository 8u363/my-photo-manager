package my.photomanager.v1.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photomanager.v1.model.Label;
import my.photomanager.v1.model.Label.LabelCategory;
import my.photomanager.v1.model.Photo;
import my.photomanager.v1.repository.PhotoRepository;
import my.photomanager.v1.service.MetaDataService.MetaData;
import my.photomanager.v1.service.LocationService.Location;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.google.common.collect.Lists;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService {

	private final PhotoRepository repository;
	private final MetaDataService metaDataService;
	private final LocationService locationService;

	public Photo createPhotoObjectOfPhotoFile(File photoFile) throws IOException {
		log.debug("create photo object of {}, {}", kv("photoFile", photoFile));

		String hashValue = DigestUtils.md5DigestAsHex(new FileInputStream(photoFile));
		var metaData = metaDataService.createMetaDataObjectOfPhotoFile(photoFile);
		var location = locationService.createLocationObjectLocationOfLongitudeAndLatitude(metaData.longitude(),
				metaData.latitude());

		List<Label> labels = Lists.newArrayList();
		labels.addAll(getLabelsFromLocation(location));
		labels.addAll(getLabelsFromMetaData(metaData));
		var photo = new Photo(photoFile.getAbsolutePath(), hashValue,
				labels);

		log.info("created {}", kv("photo object", photo));
		return photo;
	}

	/**
	 * save a new photo object
	 * check if the photo exists already
	 * 
	 * @param photo the photo
	 * @return the saved photo
	 */
	public Photo savePhoto(@NonNull Photo photo) {
		var hashValue = photo.getHashValue();
		repository.findByHashValue(hashValue)
				.ifPresent(existingPhoto -> new InternalError("photo [" + photo + "] exists already"));

		var savedPhoto = repository.saveAndFlush(photo);
		log.info("saved {}", kv("photo", savedPhoto));

		return savedPhoto;
	}

	/**
	 * update an existing photo object
	 * 
	 * @param ID the existing photo id
	 * @return the updated photo
	 */
	public Photo updatePhoto(long ID) {
		Photo photo = repository.findById(ID)
				.orElseThrow(() -> new InternalError("no photo found with ID " + ID));

		log.info("update {}", kv("photo", photo));
		return null;
	}

	/**
	 * delete an existing photo
	 * 
	 * @param ID the existing photo id
	 */
	public void deletePhoto(long ID) {
		Photo photo = repository.findById(ID)
				.orElseThrow(() -> new InternalError("no photo found with ID " + ID));

		log.info("delete {}", kv("photo", photo));
	}

	private List<Label> getLabelsFromLocation(@NonNull Location location) {
		var countryTag = new Label(location.country(), LabelCategory.LOCATION);
		var cityTag = new Label(location.city(), LabelCategory.LOCATION);

		return Lists.newArrayList(countryTag, cityTag);
	}

	private List<Label> getLabelsFromMetaData(@NonNull MetaData metaData) {
		var dimensionWidthTag = new Label(Integer.toString(metaData.width()), LabelCategory.DIMENSION);
		var dimensionHeightTag = new Label(Integer.toString(metaData.height()), LabelCategory.DIMENSION);

		return Lists.newArrayList(dimensionWidthTag, dimensionHeightTag);
	}
}

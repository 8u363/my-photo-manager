package my.photomanager.service.photo;

import static net.logstash.logback.argument.StructuredArguments.kv;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import my.photomanager.model.label.Label;
import my.photomanager.model.label.LabelCategory;
import my.photomanager.model.photo.Photo;
import my.photomanager.repository.PhotoRepository;
import my.photomanager.service.location.Location;
import my.photomanager.service.location.LocationService;
import my.photomanager.service.metaData.MetaData;
import my.photomanager.service.metaData.MetaDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService implements IPhotoService<Photo> {

	private final PhotoRepository repository;
	private final MetaDataService metaDataService;
	private final LocationService locationService;

	@SneakyThrows
	@Override
	public Photo savePhotoIfNotExists(@NonNull File photoFile) {
		var photo = buildPhoto(photoFile);
		log.info("save {} if not exists", kv("photo", photo));

		Photo savedPhoto;
		Optional<Photo> optionalPhoto = repository.findByHashValue(photo.getHashValue());

		if (optionalPhoto.isEmpty()) {
			savedPhoto = repository.saveAndFlush(photo);
			log.info("saved {}", kv("photo", savedPhoto));
		} else {
			savedPhoto = optionalPhoto.get();
			log.info("{} exists already", kv("photo", savedPhoto));
		}

		return savedPhoto;
	}

	@Override
	public Page<Photo> getPhotos(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);

		return repository.findAll(pageRequest);
	}

	@Override
	public Page<Photo> getPhotos(int page, int size, List<String> labelNames) {
		log.info("{}", kv("labelNames", labelNames));
		Pageable pageRequest = PageRequest.of(page, size);

		if (labelNames.isEmpty()) {
			return getPhotos(page, size);
		}

		//return repository.findByLabelNames(String.join(" ", labelNames), pageRequest);
		return repository.findByLabelNames(labelNames, pageRequest);

	}

	@Override
	public List<Label> getLabels() {
		return repository.findAll()
				.stream()
				.map(Photo::getLabels)
				.flatMap(List::stream)
				.distinct()
				.collect(Collectors.toList());
	}

	private Photo buildPhoto(@NonNull File photoFile) throws IOException {
		var metaData = metaDataService.buildMetaDataFromPhotoFile(photoFile);
		var location = locationService.buildLocationFromLongitudeAndLatitude(metaData.getLongitude(), metaData.getLatitude());

		var photo = Photo.builder(photoFile.getAbsolutePath(), getHashValue(photoFile))
				.withLabels(getLabelsFromMetaData(metaData))
				.withLabels(getLabelsFromLocation(location))
				.build();

		log.debug("created {}", kv("photo", photo));
		return photo;
	}

	private String getHashValue(@NonNull File photo) throws IOException {
		String hashValue = DigestUtils.md5DigestAsHex(new FileInputStream(photo));

		log.debug("{} has {}", kv("photo", photo), kv("hashValue", hashValue));
		return hashValue;
	}

	private List<Label> getLabelsFromLocation(@NonNull Location location) {
		var additionalContent = location.getCountry() + " " + location.getCity() + " " + location.getPostalCode() + " " + location.getStreet();
		var countryTag = Label.builder(location.getCountry(), LabelCategory.LOCATION)
				.withAdditionalContent(additionalContent)
				.build();
		var cityTag = Label.builder(location.getCity(), LabelCategory.LOCATION)
				.withAdditionalContent(additionalContent)
				.build();

		return Lists.newArrayList(countryTag, cityTag);
	}

	private List<Label> getLabelsFromMetaData(@NonNull MetaData metaData) {
		var dimensionWidthTag = Label.builder(Integer.toString(metaData.getWidth()), LabelCategory.DIMENSION)
				.build();
		var dimensionHeightTag = Label.builder(Integer.toString(metaData.getHeight()), LabelCategory.DIMENSION)
				.build();

		return Lists.newArrayList(dimensionWidthTag, dimensionHeightTag);
	}
}

package my.photoapi.service.photoservice;

import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import my.photoapi.model.label.Label;
import my.photoapi.model.label.LabelCategory;
import my.photoapi.model.photo.Photo;
import my.photoapi.repository.PhotoRepository;
import my.photoapi.service.locationservice.Location;
import my.photoapi.service.locationservice.OpenMapService;
import my.photoapi.service.metadataservice.MetaData;
import my.photoapi.service.metadataservice.MetaDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService implements IPhotoService<Photo> {

    private final PhotoRepository repository;
    private final MetaDataService metaDataService;
    private final OpenMapService locationService;

    @SneakyThrows
    @Override
    public Photo savePhotoIfNotExists(@NonNull Path photoFilePath) {
        var photo = createPhoto(photoFilePath);
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
        var pageContent = repository.findAll(pageRequest);

        return pageContent;
    }

    @Override
    public Page<Photo> getPhotos(int page, int size, List<String> labelNames) {
        Pageable pageRequest = PageRequest.of(page, size);

        if (labelNames.isEmpty()) {
            return getPhotos(page, size);
        }

        return repository.findByLabelNames(String.join(" ", labelNames), pageRequest);
    }

    @Override
    public List<Label> getLabels() {
        return repository.findAll().stream()
                .map(photo -> photo.getLabels())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private Photo createPhoto(@NonNull Path photoFilePath) throws IOException {
        var metaData = metaDataService.createMetaDataFromPhotoFile(photoFilePath);
        var location = locationService.createLocationFromLatitudeAndLongitude(metaData.getLatitude(), metaData.getLongitude());

        var photo = Photo.builder(photoFilePath.toAbsolutePath().toString(), getHashValue(photoFilePath.toFile()))
                .withLabels(getLabelsFromMetaData(metaData))
                .withLabels(getLabelsFromLocation((Location) location))
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
        var countryTag = Label.builder(location.getCountry(), LabelCategory.LOCATION).withAdditionalContent(additionalContent).build();
        var cityTag = Label.builder(location.getCity(), LabelCategory.LOCATION).withAdditionalContent(additionalContent).build();

        return Lists.newArrayList(countryTag, cityTag);
    }

    private List<Label> getLabelsFromMetaData(@NonNull MetaData metaData) {
        var dimensionTag = Label.builder(metaData.getWidth() + " " + metaData.getHeight(), LabelCategory.DIMENSION).build();

        return Lists.newArrayList(dimensionTag);
    }
}

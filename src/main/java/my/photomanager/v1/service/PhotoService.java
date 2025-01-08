package my.photomanager.v1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photomanager.v1.model.Photo;
import my.photomanager.v1.repository.PhotoRepository;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService {

	private final PhotoRepository repository;

	/**
	 * save a new photo object
	 * check if the photo exists already
	 * 
	 * @param photo the photo
	 * @return the saved photo
	 */
	public Photo savePhoto(Photo photo) {
		var hashValue = photo.getHashValue();
		repository.findByHashValue(hashValue)
				.ifPresent(config -> new InternalError("photo [" + photo + "] exists already"));

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
}

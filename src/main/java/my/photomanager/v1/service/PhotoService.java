package my.photomanager.v1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.photomanager.repository.PhotoRepository;
import my.photomanager.v1.model.Photo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService {

	private final PhotoRepository repository;

	public Photo savePhoto(Photo photo) {
		return null;
	}

	public Photo updatePhoto(long ID) {
		return null;
	}

	public void deletePhoto(long ID) {

	}
}

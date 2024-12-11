package my.photomanager.service.file;

import com.google.common.collect.Lists;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;

public interface IFileService {

	default Collection<File> getPhotosFilesFromSourceFolder(Path sourceFolderPath) {
		return getPhotosFilesFromSourceFolder(sourceFolderPath, false);
	}

	default Collection<File> getPhotosFilesFromSourceFolder(Path sourceFolderPath, boolean recursive) {
		return getPhotosFilesFromSourceFolder(sourceFolderPath, recursive, Lists.newArrayList("jpeg", "jpg"));
	}

	default Collection<File> getPhotosFilesFromSourceFolder(Path sourceFolderPath, boolean recursive, List<String> photoExtension) {
		return FileUtils.listFiles(sourceFolderPath.toFile(),
				photoExtension.stream()
						.map(String::toLowerCase)
						.toArray(String[]::new),
				recursive);
	}
}

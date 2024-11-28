package my.photoapi.service.fileService;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface IFileService {

    default Collection<File> getPhotosFromSource(Path sourceFolderPath) {
        return getPhotosFromSource(sourceFolderPath, true, Lists.newArrayList("jpg"));
    }

    default Collection<File> getPhotosFromSource(Path sourceFolderPath, boolean recursive, List<String> photoExtension) {
        return FileUtils.listFiles(sourceFolderPath.toFile(), photoExtension.toArray(String[]::new), recursive);
    }
}

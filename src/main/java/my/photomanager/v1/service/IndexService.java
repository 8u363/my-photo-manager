package my.photomanager.v1.service;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class IndexService {

    /**
     * get all default photos from source folder
     * 
     * @param sourceFolderPath the folder to index
     * @return a collection of photos files
     */
    public Collection<File> getPhotosFilesFromSourceFolder(@NonNull Path sourceFolderPath) {
        return getPhotosFilesFromSourceFolder(sourceFolderPath, false);
    }

    /**
     * get all default photos from source folder recursivly
     * 
     * @param sourceFolderPath the folder to index
     * @param recursive        if set scan the source folder recursivly
     * @return a collection of photos files
     */
    public Collection<File> getPhotosFilesFromSourceFolder(@NonNull Path sourceFolderPath, boolean recursive) {
        return getPhotosFilesFromSourceFolder(sourceFolderPath, recursive, Lists.newArrayList("jpeg", "jpg"));
    }

    /**
     * get all photos from source folder
     * 
     * @param sourceFolderPath the folder to index
     * @param recursive        if set scan the source folder recursivly
     * @param photoExtension 
     * @return
     */
    public Collection<File> getPhotosFilesFromSourceFolder(@NonNull Path sourceFolderPath, boolean recursive,
            List<String> photoExtension) {
        return FileUtils.listFiles(sourceFolderPath.toFile(),
                photoExtension.stream()
                        .map(String::toLowerCase)
                        .toArray(String[]::new),
                recursive);
    }

}

package my.photoapi.service.metadataservice;

import lombok.NonNull;

import java.nio.file.Path;

public interface IMetaDataService<T extends IMetaData> {

    T createMetaData(@NonNull Path photoFilePath);
}

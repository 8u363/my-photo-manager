package my.photomanager.photo.metadata;

import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface IMetaDataFactory {

    MetaData createMetaData(File photoFile) throws IOException;
}

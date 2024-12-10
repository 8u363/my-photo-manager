package my.photomanager.service.metaData;

import java.io.File;
import lombok.NonNull;

public interface IMetaDataService<T extends  IMetaData> {

	T buildMetaDataFromPhotoFile(@NonNull File photoFile);
}

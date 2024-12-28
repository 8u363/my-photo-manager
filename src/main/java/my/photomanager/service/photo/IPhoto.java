package my.photomanager.service.photo;

import java.util.List;
import my.photomanager.service.IDatabaseEntity;

public interface IPhoto extends IDatabaseEntity {

	String getFilePath();

	String getHashValue();

	List<? extends ILabel> getLabels();
}

package my.photomanager.model.photo;

import java.util.List;
import my.photomanager.model.label.ILabel;

public interface IPhoto {

	long getID();

	String getFilePath();

	String getHashValue();

	List<? extends ILabel> getLabels();
}

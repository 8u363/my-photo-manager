package my.photoapi.model.photo;

import my.photoapi.model.label.ILabel;

import java.util.List;

public interface IPhoto {

    long getID();

    String getFilePath();

    String getHashValue();

    List<? extends ILabel> getLabels();
}

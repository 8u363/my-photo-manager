package my.photo_manager.model.photo;

import java.util.UUID;

public interface IPhoto {

    long getID();

    String getHashValue();

    String getFilePath();
}

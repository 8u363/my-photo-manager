package my.photo_manager.photo;

import java.util.UUID;

public interface IPhoto {

    long getID();

    String getHashValue();

    String getFilePath();

    int getHeight();

    int getWidth();

}

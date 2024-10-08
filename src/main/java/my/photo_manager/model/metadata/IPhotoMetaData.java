package my.photo_manager.model.metadata;

import lombok.NonNull;

public interface IPhotoMetaData {

    int getHeight();

    int getWidth();

    String getCreationTimeStamp();

    default boolean containsSearchString(@NonNull String searchString) {
        var height = String.valueOf(getHeight());
        var width = String.valueOf(getWidth());
        var creationTimeStamp = getCreationTimeStamp();

        return height.contains(searchString) || width.contains(searchString) || creationTimeStamp.contains(searchString);
    }
}
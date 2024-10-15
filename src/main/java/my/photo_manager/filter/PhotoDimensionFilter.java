package my.photo_manager.filter;

import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import my.photo_manager.model.metadata.PhotoMetaData;
import my.photo_manager.model.photo.Photo;

@Getter
@ToString
public class PhotoDimensionFilter implements IFilter {

    private String text;
    private final int width;
    private final int height;

    public PhotoDimensionFilter(@NonNull Photo photo) {
        height = photo.getMetaData().getHeight();
        width = photo.getMetaData().getWidth();

        text = height + "x" + width;
        text = text.trim();
    }

    @VisibleForTesting
    public PhotoDimensionFilter(@NonNull PhotoMetaData photoMetaData) {
        height = photoMetaData.getHeight();
        width = photoMetaData.getWidth();

        text = height + "x" + width;
        text.trim();
    }


    @Override
    public FilterCategory getCategory() {
        return FilterCategory.DIMENSION;
    }
}

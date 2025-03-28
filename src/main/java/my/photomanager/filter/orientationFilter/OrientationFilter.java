package my.photomanager.filter.orientationfilter;

import java.util.function.Predicate;
import lombok.Builder;
import lombok.NonNull;
import my.photomanager.filter.IFilter;
import my.photomanager.photo.Photo;

@Builder(setterPrefix = "with")
public class OrientationFilter implements IFilter {

    @NonNull
    private Orientation orientation;

    @Builder.Default
    private boolean active = false;

    @Override
    public boolean test(@NonNull Photo photo) {
        Predicate<Photo> isLandscape =
                filteredPhoto -> filteredPhoto.getWidth() > filteredPhoto.getHeight();
        Predicate<Photo> isPortrait =
                filteredPhoto -> filteredPhoto.getHeight() > filteredPhoto.getWidth();

        Predicate<Photo> isSquare =
                filteredPhoto -> filteredPhoto.getHeight() == filteredPhoto.getWidth();

        switch (orientation) {
            case LANDSCAPE:
                return isLandscape.test(photo);
            case PORTRAIT:
                return isPortrait.test(photo);
            case SQUARE:
                return isSquare.test(photo);
            default:
                return false;
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive() {
        active = true;
    }

    @Override
    public void setInactive() {
        active = false;
    }
}

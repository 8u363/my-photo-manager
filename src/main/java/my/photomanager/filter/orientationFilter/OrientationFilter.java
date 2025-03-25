package my.photomanager.filter.orientationFilter;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'test'");
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
    public void setInActive() {
        active = false;
    }
}

package my.photomanager.filter.timeStampFilter;

import org.apache.logging.log4j.util.Strings;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import my.photomanager.filter.IFilter;
import my.photomanager.photo.Photo;

@Builder(setterPrefix = "with")
@EqualsAndHashCode
@ToString
public class TimeStampFilter implements IFilter {

    @NonNull
    @Builder.Default
    private String year = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String month = Strings.EMPTY;

    @Builder.Default
    private boolean active = false;

    @Override
    public boolean test(Photo t) {
        return false;
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

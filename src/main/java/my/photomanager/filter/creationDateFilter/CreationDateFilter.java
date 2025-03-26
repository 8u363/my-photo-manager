package my.photomanager.filter.creationDateFilter;

import static net.logstash.logback.argument.StructuredArguments.kv;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import my.photomanager.filter.IFilter;
import my.photomanager.photo.Photo;

@Builder(setterPrefix = "with")
@EqualsAndHashCode
@ToString
@Log4j2
public class CreationDateFilter implements IFilter {

    private int year;

    private int month;

    @Builder.Default
    private boolean active = false;

    @Override
    public boolean test(Photo photo) {
        if (year > 0 && month > 0) {
            return photo.getCreationDate().getYear() == year
                    && photo.getCreationDate().getMonthValue() == month;
        }

        if (year > 0 && month == 0) {
            return photo.getCreationDate().getYear() == year;
        }


        if (year == 0 && month > 0) {
            return photo.getCreationDate().getMonthValue() == month;
        }

        log.warn("no match found for {}", kv("photo", photo));
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

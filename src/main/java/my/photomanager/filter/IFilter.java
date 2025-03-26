package my.photomanager.filter;

import java.util.function.Predicate;
import lombok.NonNull;
import my.photomanager.photo.Photo;

public interface IFilter extends Predicate<Photo> {

    boolean isActive();

    void setActive();

    void setInActive();

    boolean test(@NonNull Photo photo);
}

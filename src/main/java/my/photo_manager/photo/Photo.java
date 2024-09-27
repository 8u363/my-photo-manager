package my.photo_manager.photo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Entity
@Table(name = "photo")
@ToString
public class Photo implements IPhoto {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private long ID;

    @NonNull
    private String hashValue;

    @NonNull
    private String filePath;

    private int width;
    private int height;
}

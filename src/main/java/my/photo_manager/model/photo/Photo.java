package my.photo_manager.model.photo;

import jakarta.persistence.*;
import lombok.*;

@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

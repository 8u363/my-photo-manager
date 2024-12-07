package my.photoapi.model.photo;

import jakarta.persistence.*;
import lombok.*;
import my.photoapi.model.label.Label;

import java.util.List;

@Builder(setterPrefix = "with", builderMethodName = "")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo")
@EqualsAndHashCode
@ToString
@Getter
public class Photo implements IPhoto {

    public static Photo.PhotoBuilder builder(String filePath, String hashValue) {
        return new Photo.PhotoBuilder().withFilePath(filePath).withHashValue(hashValue);
    }

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private long ID;
    @NonNull
    private String filePath;
    @NonNull
    @Column(unique = true)
    private String hashValue;
    @NonNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Singular
    private List<Label> labels;
}


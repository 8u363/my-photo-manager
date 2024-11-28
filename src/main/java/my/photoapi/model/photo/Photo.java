package my.photoapi.model.photo;

import jakarta.persistence.*;
import lombok.*;
import my.photoapi.model.label.Label;

import java.util.List;

@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo")
@EqualsAndHashCode
@ToString
@Getter
public class Photo implements IPhoto {
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Singular
    private List<Label> labels;
}


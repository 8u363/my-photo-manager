package my.photo_manager.model.photo;

import jakarta.persistence.*;
import lombok.*;
import my.photo_manager.model.metadata.PhotoMetaData;

@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "PHOTO")
@Getter
public class Photo implements IPhoto {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private long ID;

    @NonNull
    private String hashValue;

    @NonNull
    private String filePath;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PhotoMetaData metaData;
}

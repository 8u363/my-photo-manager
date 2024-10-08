package my.photo_manager.model.metadata;

import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Strings;

@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Entity
@Table(name = "PHOTO_META_DATA")
@Getter
public class PhotoMetaData implements IPhotoMetaData {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private long ID;

    private int height;

    private int width;

    @NonNull
    @Builder.Default
    private String creationTimeStamp = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String country = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String city = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String postCode = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String street = Strings.EMPTY;

    @NonNull
    @Builder.Default
    private String houseNumber = Strings.EMPTY;
}

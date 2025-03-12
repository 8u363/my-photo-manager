package my.photomanager.photo;

import org.apache.logging.log4j.util.Strings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder(setterPrefix = "with")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PHOTO")
@ToString
public class Photo {

    // required parameter
    @Id
    @GeneratedValue
    @Column(updatable = false)
    @Getter
    private long id;

    @NonNull
    @Getter
    @Builder.Default
    private final String filePath = Strings.EMPTY;

    @NonNull
    @Column(unique = true)
    @Getter
    @Builder.Default
    private final String hashValue = Strings.EMPTY;

    // meta data parameter
    @Getter
    @Builder.Default
    private final int width = 0;

    @Getter
    @Builder.Default
    private final int height = 0;

    @NonNull
    @Getter
    @Builder.Default
    private final String creationTimeStamp = "";

    // optional gps parameter
    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String country = "";

    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String city = "";

    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String postalCode = "";

    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String road = "";

    public static PhotoBuilder builder(@NonNull String filePath, @NonNull String hashValue) {
        return new Photo.PhotoBuilder().withFilePath(filePath).withHashValue(hashValue);
    }
}

package my.photomanager.photo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PHOTO")
public class Photo {

    // required parameter
    @Id
    @GeneratedValue
    @Column(updatable = false)
    @Getter
    private long id;

    @NonNull
    @Getter
    private final String filePath;

    @NonNull
    @Column(unique = true)
    @Getter
    private final String hashValue;

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

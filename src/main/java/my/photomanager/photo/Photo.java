package my.photomanager.photo;

import java.time.LocalDate;
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

    @Getter
    @Builder.Default
    private LocalDate creationDate = LocalDate.of(1900, 1, 1);

    // optional gps parameter
    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String country = Strings.EMPTY;

    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String city = Strings.EMPTY;

    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String postalCode = Strings.EMPTY;

    @NonNull
    @Getter
    @Setter
    @Builder.Default
    private String road = Strings.EMPTY;

    public static PhotoBuilder builder(@NonNull String filePath, @NonNull String hashValue) {
        return new Photo.PhotoBuilder().withFilePath(filePath).withHashValue(hashValue);
    }
}

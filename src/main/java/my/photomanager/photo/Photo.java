package my.photomanager.photo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PHOTO")
@EqualsAndHashCode
@ToString
public class Photo {

    @Id
	@GeneratedValue
	@Column(updatable = false)
	private long ID;

    @NonNull
    @Column(updatable = false)
    private String photoHashValue;

    @NonNull
    @Column(updatable = false)
    private  String photoFilePath;
    
}

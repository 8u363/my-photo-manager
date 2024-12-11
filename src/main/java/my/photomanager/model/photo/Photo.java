package my.photomanager.model.photo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import my.photomanager.model.label.Label;

@Builder(setterPrefix = "with", builderMethodName = "")
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Singular
	private List<Label> labels;

	public static PhotoBuilder builder(String filePath, String hashValue) {
		return new PhotoBuilder().withFilePath(filePath)
				.withHashValue(hashValue);
	}
}


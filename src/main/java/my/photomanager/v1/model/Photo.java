package my.photomanager.v1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "photo")
@EqualsAndHashCode
@ToString
public class Photo {

	@Id
	@GeneratedValue
	@Column(updatable = false)
	private long ID;

	@NonNull
	@Getter
	private String filePath;

	@NonNull
	@Getter
	@Column(unique = true)
	private String hashValue;

	@NonNull
	@Getter
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Label> labels;
}

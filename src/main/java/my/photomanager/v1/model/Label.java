package my.photomanager.v1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "label")
@EqualsAndHashCode
@ToString
public class Label {

	public enum LabelCategory {
		TIMESTAMP,
		DIMENSION,
		LOCATION
	}

	@Getter
	@Id
	@GeneratedValue
	@Column(updatable = false)
	private long ID;

	@NonNull
	@Getter
	private String text;

	@NonNull
	@Getter
	private LabelCategory category;
}

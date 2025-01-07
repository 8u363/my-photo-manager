package my.photomanager.v1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "configuration")
@EqualsAndHashCode
@ToString
public class Configuration {

	@Getter
	@Id
	@GeneratedValue
	@Column(updatable = false)
	private long ID;

	@NonNull
	@Getter
	@Setter
	@Column(unique = true)
	private String folderPath;

	@NonNull
	@Getter
	@Setter
	private String updateInterval;
}

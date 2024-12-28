package my.photomanager.service.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder(setterPrefix = "with", builderMethodName = "")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "configuration")
@EqualsAndHashCode
@ToString
@Getter
public class Configuration {

	@Id
	@GeneratedValue
	@Column(updatable = false)
	private long ID;
	@NonNull
	@Setter
	@Column(unique = true)
	private String folderPath;
	@NonNull
	@Setter
	private String scanInterval;

	public static Configuration.ConfigurationBuilder builder(String folderPath) {
		return new Configuration.ConfigurationBuilder().withFolderPath(folderPath)
				.withScanInterval("every 5 minutes");
	}
}

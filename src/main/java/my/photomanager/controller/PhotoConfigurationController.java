package my.photomanager.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import my.photomanager.service.configuration.Configuration;
import my.photomanager.service.configuration.ConfigurationService;
import my.photomanager.web.ConfigurationDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "configuration")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoConfigurationController {

	private final ConfigurationService configurationService;

	@GetMapping("/getAll")
	public List<ConfigurationDTO> getConfigurations() {
		return configurationService.getConfigurations()
				.stream()
				.map(this::mapConfigurationToDTO)
				.toList();
	}

	@PostMapping("/add")
	public ConfigurationDTO addConfiguration(@RequestParam(value = "folderPath") String folderPath,
			@RequestParam(value = "updateInterval", required = false) String updateInterval) {
		// TODO
		return null;
	}

	@PutMapping("/update")
	public ConfigurationDTO updateConfiguration(@RequestParam(value = "ID") long ID,
			@RequestParam(value = "folderPath") String newFolderPath,
			@RequestParam(value = "updateInterval", required = false) String newUpdateInterval) {
		// TODO
		return null;
	}

	@DeleteMapping("/delete")
	public void deleteConfiguration(@RequestParam(value = "ID") long ID) {
		// TODO
	}

	private ConfigurationDTO mapConfigurationToDTO(@NonNull Configuration configuration) {
		// TODO
		return null;
	}
}

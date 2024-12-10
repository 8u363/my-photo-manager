package my.photomanager;

import java.nio.file.Path;
import java.util.stream.Stream;

public class TestConstants {
	public final static String INTEGRATION_TEST = "INTEGRATION-TEST";
	public final static String UNIT_TEST = "UNIT-TEST";

	public final static Path TEST_FILE_PATH = Path.of("src", "test", "resources", "TestFiles");
	public final static Path TEST_PHOPTOS_PATH = TEST_FILE_PATH.resolve("photos");


	public static Stream<Path> getPhotoFilesWithMetaData() {
		return Stream.of(
				TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.webp")),
				TEST_PHOPTOS_PATH.resolve(Path.of("withMetaData", "Brandenburg_Gate_with_metadata.jpg"))
		);
	}

	public static Stream<Path> getPhotoFilesWithoutMetaData() {
		return Stream.of(
				TEST_PHOPTOS_PATH.resolve(Path.of("withoutMetaData", "Brandenburg_Gate_no_metadata.webp")),
				TEST_PHOPTOS_PATH.resolve(Path.of("withoutMetaData", "Brandenburg_Gate_no_metadata.jpg"))
		);
	}
}

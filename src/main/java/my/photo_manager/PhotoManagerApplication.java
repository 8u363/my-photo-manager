package my.photo_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhotoManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoManagerApplication.class, args);
	}

}

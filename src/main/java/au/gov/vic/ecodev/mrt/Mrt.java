package au.gov.vic.ecodev.mrt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Mrt {
	
	public static void main(String[] args) {
		SpringApplication.run(Mrt.class, args);
	}

}

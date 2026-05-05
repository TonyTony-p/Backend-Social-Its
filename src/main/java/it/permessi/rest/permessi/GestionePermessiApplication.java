package it.permessi.rest.permessi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling

public class GestionePermessiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionePermessiApplication.class, args);
	}

}

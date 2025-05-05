package org.angelesyvalientes.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("org.angelesyvalientes.api.persistence.repository")
public class AngelesyvalientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngelesyvalientesApplication.class, args);
	}

}

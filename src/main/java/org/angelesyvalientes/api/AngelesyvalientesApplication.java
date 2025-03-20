package org.angelesyvalientes.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Ángeles y Valientes",
		version = "1.0.3",
		description = "API - Ángeles y Valientes",
		license = @License(name = "Apache 2.0"),
		contact = @Contact(url = "S", name = "MS-nameMS")),
	security = {
			@SecurityRequirement(name = "")
	},
	servers = {
			@Server(description = "Ambiente Local", url = "https://localhost:8080/")
	}
)

@SpringBootApplication
public class AngelesyvalientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngelesyvalientesApplication.class, args);
	}

}

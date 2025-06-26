package dev.eliezer.lojaonline;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Loja Online",
				description = "API do Loja Online",
				version = "0.0.1-dev"
		))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

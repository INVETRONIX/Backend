package com.invetronix.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.load();
			// Configurar las variables de entorno desde el archivo .env
			System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
			System.setProperty("spring.datasource.url", dotenv.get("DATABASE_URL"));
			System.setProperty("spring.datasource.username", dotenv.get("DATABASE_USERNAME"));
			System.setProperty("spring.datasource.password", dotenv.get("DATABASE_PASSWORD"));
		} catch (DotenvException e) {
			// Si no se encuentra el archivo .env, usar las variables de entorno del sistema
			System.out.println("No se encontró el archivo .env, usando variables de entorno del sistema");
		}
		SpringApplication.run(BackendApplication.class, args);
	}
}

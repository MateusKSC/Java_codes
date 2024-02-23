package Mateus_Academia.Biblioteca_Virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BibliotecaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaVirtualApplication.class, args);
	}

}

package snya.reina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@EntityScan(basePackages = { "snya.general.modelo", "snya.usuarios.modelo", "snya.reina.modelo", "snya.reinaweb.comun", "snya.archivoscliente", "snya.reina.config" })
@SpringBootApplication(scanBasePackages = { "snya.reinaweb", "snya.reina", "snya.general", "snya.archivoscliente", "snya.reina.config", "snya.reina.rest.llamadas" })
@EnableCaching
//@EnableWebMvc
public class ReinaExternosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReinaExternosApplication.class, args);
	}

}
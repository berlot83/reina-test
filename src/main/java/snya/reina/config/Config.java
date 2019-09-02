package snya.reina.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import snya.archivoscliente.ArchivoCliente;
import snya.notificacionescliente.NotificacionCliente;

@Configuration
public class Config {
	@Bean
	public ArchivoCliente archivoCliente() {
		return new ArchivoCliente();
	}

	@Bean
	public NotificacionCliente notificacionesCliente() {
		return new NotificacionCliente();
	}
}

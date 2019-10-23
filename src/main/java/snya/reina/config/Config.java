package snya.reina.config;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import snya.archivoscliente.ArchivoCliente;
import snya.notificacionescliente.NotificacionCliente;
import snya.reina.rest.seguridad.DatosUsuarioInterceptor;

@Configuration
public class Config{
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Bean
	public ArchivoCliente archivoCliente() {
		return new ArchivoCliente();
	}

	@Bean
	public NotificacionCliente notificacionesCliente() {
		return new NotificacionCliente();
	}
	
	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor a = new DatosUsuarioInterceptor();
        List<ClientHttpRequestInterceptor> interceptors = Arrays.asList(a);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }


}

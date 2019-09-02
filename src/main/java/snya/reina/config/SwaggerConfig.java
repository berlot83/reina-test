package snya.reina.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/* Clase de configuración de documentador automático de la API REST */
@Configuration
/*
 * Desactivado porque mi máquina no tiene la RAM suficiente para tener en
 * memoria todas las entidades que lee, Swagger no funciona si en alguna parte
 * del proyecto tiene la @EnableWebMvc aunque esté en @EnableSwagger2
 */
// @EnableSwagger2
@ComponentScan({ "snya.reina.rest" })
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("snya.reina.rest")).paths(PathSelectors.any()).build();
	}

	public ApiInfo getApiInfo() {
		Contact contact = new Contact("Secretaría Nacional de la Niñez", "https://sistemas.snya.reina.gba.gov.ar",
				"sistemas@snya.gba.gob.ar");

		ApiInfoBuilder apiBuilder = new ApiInfoBuilder();
		return apiBuilder.description(
				"Documentación de la API REST para el manejo de las entidades de la Secretaría de Nacional de la Niñez. Interface de Programación de la Aplicación por sus siglas en español para intercambio de información desde otras aplicaciones.\n")
				.title("S.N.A API - Documentación Oficial").contact(contact).version("v1").build();
	}
}

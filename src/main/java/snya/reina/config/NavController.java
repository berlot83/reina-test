package snya.reina.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("snya.reina.controllers")
@EnableWebMvc
public abstract class NavController implements WebMvcConfigurer{
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/error").setViewName("error");
		registry.addViewController("/swagger-ui.html#!").setViewName("swagger-ui.html#!");
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
            "/src/img/**",
            "/src/css/**",
            "/src/js/**")
            .addResourceLocations(
                    "classpath:/static/src/img/",
                    "classpath:/static/src/css/",
                    "classpath:/static/src/js/");
    }
}

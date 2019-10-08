package snya.reina.config;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	/* Axel Berlot
//	 * Saltea archivos varios de recursos
//	 */
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**");
//	}
//
//	public final static String roles(int numero) {
//		String rol = null;
//		switch(numero) {
//		case 1:
//			rol = "FUNCIONARIO";
//		case 2:
//			rol = "CONSULTA";
//		case 3:
//			rol = "ROL_VISTA_LEGAJO";
//		case 4:
//			rol = "ROL_VISTA_LEGAJO_UBICACION";
//		case 5:
//			rol = "ROL_VISTA_LEGAJO_TECNICO";
//		}
//		return rol;
//	}
//
//	
//	/* Definir como va a llegar el token, si va a llegar por header o si se extrae de una session */
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		 http.csrf().disable()
//		 .authorizeRequests().antMatchers("/").hasRole(roles(1))
//		 .and().authorizeRequests().antMatchers("/rolconsulta").hasRole(roles(2))
////		 .and().authorizeRequests().antMatchers("/api/jovenes/simple/**").authenticated()
//		 .and().authorizeRequests().antMatchers("/api/jovenes/simple/**").hasRole(roles(1)).anyRequest().authenticated()
//		 .and().authorizeRequests().antMatchers("/", "/index").permitAll()
//         .antMatchers("/login*").permitAll()
//         .anyRequest().authenticated()
//         .and()
//         .formLogin()
////         .loginPage("/login.html")
////         .loginPage("/login")
//         .loginProcessingUrl("/login")
//         .defaultSuccessUrl("/index.html", true);
//		 
//	}
//
//}

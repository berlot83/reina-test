package snya.reina.rest.llamadas;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.GenericWebApplicationContext;

import snya.reina.rest.seguridad.JwtUtil;
import snya.reina.rest.seguridad.modelo.UsuarioToken;

public class ConsultasServidor {
	@Autowired
	static GenericWebApplicationContext context;
	/* Devuelve Joven determinado de reuna */
//	public static String getJovenReuna(String buscar, UsuarioToken usuarioToken) {
//		String token = JwtUtil.generateToken(usuarioToken);
//		HttpHeaders headers = new HttpHeaders();
//	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
//	    headers.set("token", token);
//	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
//	    ResponseEntity<String> response = new RestTemplate().exchange("http://163.10.35.7:8080/reuna/api2/jovenes?buscar="+buscar, HttpMethod.GET, parameters, String.class);
//	   return response.getBody();
//	}
	
	public static String getJovenReuna(String buscar, UsuarioToken usuarioToken) {
		usuarioToken = new UsuarioToken();
		String token = JwtUtil.generateToken(usuarioToken);
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	    headers.set("token", token);
	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
	    ResponseEntity<String> response = new RestTemplate().exchange("http://163.10.35.7:8080/reuna/api2/jovenes?buscar="+buscar, HttpMethod.GET, parameters, String.class);
	   return response.getBody();
	}
	
	public static void main(String[] args) {
		//System.out.println(ConsultasServidor.getJovenReuna("25", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg"));
	}
	
}

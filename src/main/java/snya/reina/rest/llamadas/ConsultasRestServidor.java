package snya.reina.rest.llamadas;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import snya.reina.rest.dto.IntervencionDTO;
import snya.reina.rest.dto.JovenReunaDTO;


/* Archivo de controllers Rest de servidor Reina a servidor Reuna */
@Service
public class ConsultasRestServidor {
	
	public static final String urlJovenReunaBuscar = "http://163.10.35.7:8080/reuna/api2/jovenes?buscar=";
	public static final String urlJovenReunaPorLegajo = "http://163.10.35.7:8080/reuna/api2/dameLegajo?id=";
	public static final String urlIntervencionReuna = "http://163.10.35.7:8080/reuna/api2/dameIntervencion?id=";
	
	@Autowired
	RestTemplate restTemplate; /* Este objeto extrañamente me está viniendo null */
	
	public void getJovenReunaBuscar(String buscar) throws JsonProcessingException {
	HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
	    headers.add("Content-Type", "application/json");
	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
	    ResponseEntity<List<JovenReunaDTO>> response = restTemplate.exchange(urlJovenReunaBuscar + buscar,
	    		HttpMethod.GET,
	    		parameters,
	    		new ParameterizedTypeReference<List<JovenReunaDTO>>(){});
	   List<JovenReunaDTO> joven = response.getBody();
	   ObjectMapper mapper = new ObjectMapper();
	   String json = mapper.writeValueAsString(joven);
	   System.out.println(json);
	}
	
	
	
	/* Devuelve Joven determinado de reuna */
	public static void getJovenReunaPorLegajo(String legajo, String token) throws JsonProcessingException {
//		UsuarioToken usuarioToken = new UsuarioToken();
//		token = JwtUtil.generateToken(usuarioToken);
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
	    headers.add("Content-Type", "application/json");
	    headers.set("token", token);	    
	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
	    ResponseEntity<List<JovenReunaDTO>> response = new RestTemplate().exchange(urlJovenReunaPorLegajo + legajo,
	    		HttpMethod.GET,
	    		parameters,
	    		new ParameterizedTypeReference<List<JovenReunaDTO>>(){});
	   List<JovenReunaDTO> joven = response.getBody();
	}
	
	/* Devuelve Joven determinado de reuna */
	public static void getIntervencionReuna(String id, String token) throws JsonProcessingException {
//		UsuarioToken usuarioToken = new UsuarioToken();
//		token = JwtUtil.generateToken(usuarioToken);
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
	    headers.add("Content-Type", "application/json");
	    headers.set("token", token);	    
	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
	    ResponseEntity<List<IntervencionDTO>> response = new RestTemplate().exchange(urlIntervencionReuna + id,
	    		HttpMethod.GET,
	    		parameters,
	    		new ParameterizedTypeReference<List<IntervencionDTO>>(){});
	   List<IntervencionDTO> intervencion = response.getBody();
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		ConsultasRestServidor consultasRestServidor = new ConsultasRestServidor();
		
		//		ConsultasRestServidor.getJovenReunaPorLegajo("68676", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg");
		System.out.println("");
		System.out.println("");
//		consultasRestServidor.getJovenReunaBuscar("25", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg");

		consultasRestServidor.getJovenReunaBuscar("25");

		
		//		ConsultasRestServidor.getIntervencionReuna("340163", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg");
		
	}
	
}

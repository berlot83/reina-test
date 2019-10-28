package snya.reina.rest.llamadas;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import snya.reina.rest.dto.IntervencionDTO;
import snya.reina.rest.dto.JovenReunaDTO;


/* Archivo de controllers Rest de servidor Reina a servidor Reuna */
public class ConsultasRestServidor {
	
	public static final String urlJovenReunaBuscar = "http://163.10.35.7:8080/reuna/api2/jovenes?buscar=";
	public static final String urlJovenReunaPorLegajo = "http://163.10.35.7:8080/reuna/api2/dameLegajo?id=";
	public static final String urlIntervencionReuna = "http://163.10.35.7:8080/reuna/api2/dameIntervencion?id=";
	
	
	/* Devuelve Joven determinado de reuna */
//	public static void getJovenReunaBuscar(String buscar, String token, HttpServletRequest request) throws JsonProcessingException {
//String session = request.getSession().getAttribute(""); 
//		//		UsuarioToken usuarioToken = new UsuarioToken();
////		token = JwtUtil.generateToken(usuarioToken);
//		HttpHeaders headers = new HttpHeaders();
//	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
//	    headers.add("Content-Type", "application/json");
//	    headers.set("token", token);	    
//	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
//	    ResponseEntity<List<JovenReunaDTO>> response = new RestTemplate().exchange(urlJovenReunaBuscar + buscar,
//	    		HttpMethod.GET,
//	    		parameters,
//	    		new ParameterizedTypeReference<List<JovenReunaDTO>>(){});
//	   List<JovenReunaDTO> joven = response.getBody();
//	   ObjectMapper mapper = new ObjectMapper();
//	   String json = mapper.writeValueAsString(joven);
//	   System.out.println(json);
//	}
	
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
//		ConsultasRestServidor.getJovenReunaPorLegajo("68676", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg");
//		ConsultasRestServidor.getJovenReunaBuscar("100", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg");
//		ConsultasRestServidor.getIntervencionReuna("340163", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg");
		
	}
	
}

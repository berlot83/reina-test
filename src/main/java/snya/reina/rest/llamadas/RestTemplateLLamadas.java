package snya.reina.rest.llamadas;

import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateLLamadas {
	
	/* Devuelve Joven determinado de reuna */
	public static String getJovenReuna(String buscar, String token) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	    headers.set("JWT", token);
	    HttpEntity<String> parameters = new HttpEntity<>("params", headers);
	    ResponseEntity<String> response = new RestTemplate().exchange("http://163.10.35.7:8080/reuna/api2/jovenes?buscar="+buscar, HttpMethod.GET, parameters, String.class);
	   return response.getBody();
	}
	
	public static void main(String[] args) {
		System.out.println(RestTemplateLLamadas.getJovenReuna("25", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYWRtIiwiTm9tYnJlIjoiQURNSU5JU1RSQURPUiIsIkFwZWxsaWRvIjoiREVMIFNJU1RFTUEiLCJFbWFpbCI6IiIsIlJvbCI6IkFETUlOSVNUUkFET1IiLCJTZWN0b3IiOiJMQSBQTEFUQSJ9.F3lrC_88gNcy9l-8iGjIm2NvTNRWyxVqXx68bA8NHg6YN22QmGpRgy2oKKk4H6f1Io56BnPAdKU9pUZkdNNqCQ"));
	}
	
}

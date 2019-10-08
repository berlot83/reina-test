package snya.reina.utilidades;

import org.springframework.security.jwt.JwtHelper;

public class DecodificadorJwt {

	
	public void crearToken() {
		
	}
	
	public static String decodificarClaim(String jwt) {
		String token = JwtHelper.decode(jwt).getClaims();
		return token;
	}
	
	public static void main(String[] args) {
		System.out.println(DecodificadorJwt.decodificarClaim("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9leGFtcGxlLm9yZyIsImF1ZCI6Imh0dHA6XC9cL2V4YW1wbGUuY29tIiwiaWF0IjoxMzU2OTk5NTI0LCJuYmYiOjEzNTcwMDAwMDAsImRhdGEiOnsiVXN1YXJpbyI6ImFkbSIsIk5vbWJyZSI6IkFETUlOSVNUUkFET1IiLCJBcGVsbGlkbyI6IkRFTCBTSVNURU1BIiwiRW1haWwiOiIiLCJJZF9QZXJtaXNvcyI6IjkifX0.DViva2RsSWSOMm8nNBozHpBw8WXGWLdSYLTKq3Zu9gE"));
	}

}

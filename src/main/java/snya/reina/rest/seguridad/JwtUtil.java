package snya.reina.rest.seguridad;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import snya.reina.rest.seguridad.modelo.UsuarioToken;

public class JwtUtil {

	private static final String secretKey = "snya";

	/* Captura la Session y el token desde el Header */
    public static UsuarioToken obtenerUsuarioToken(HttpServletRequest request) {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        UsuarioToken ut = JwtUtil.parseToken(token);
        return ut;
    }
	
    /* Recibir y decodificar de Reuna */
    public static UsuarioToken parseToken(String headertoken) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(headertoken)
                    .getBody();

			        UsuarioToken u = new UsuarioToken();
			        u.setUsuario((String) body.get("Usuario"));
			        u.setSector((String) body.get("Sector"));
			        u.setNombre((String) body.get("Nombre"));
			        u.setApellido((String) body.get("Apellido"));
			        u.setEmail((String) body.get("Email"));
			        u.setRol((String) body.get("Rol"));

            return u;
        } catch (JwtException e) {
            return null;
        } catch (ClassCastException e) {
            return null;
        }
    }

    /* Codificar y enviar a Reuna */
    public static String generateToken(UsuarioToken usuarioToken) {
        Claims claims = Jwts.claims().setSubject("Token de seguridad");
        claims.put("Usuario", usuarioToken.getUsuario());
        claims.put("Nombre", usuarioToken.getNombre());
        claims.put("Apellido", usuarioToken.getApellido());
        claims.put("Email", usuarioToken.getEmail());
        claims.put("Rol", usuarioToken.getRol());
        claims.put("Sector", usuarioToken.getSector());
        
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    /* Pruebas */
    public static void main(String[] args) {
    	UsuarioToken user = new UsuarioToken();
    	user.setApellido("Pereyra");
    	user.setNombre("Marcelo");
    	user.setEmail("marcelo@snya.com.ar");
    	user.setRol("ADMINISTRADOR");
    	user.setSector("SNYA La Plata");
    	System.out.println(generateToken(user));
    	
//    	Claims claims = Jwts.parser()
//    	            .setSigningKey(secretKey.getBytes())
////    	            .parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYWRtIiwiTm9tYnJlIjoiQURNSU5JU1RSQURPUiIsIkFwZWxsaWRvIjoiREVMIFNJU1RFTUEiLCJFbWFpbCI6IiIsIlJvbCI6IkFETUlOSVNUUkFET1IiLCJTZWN0b3IiOiJMQSBQTEFUQSJ9.tJ83pjHmf0cAZF1R3dFfHgAJLhKBL8VdfWVVuxf3rnE8BojIo_Pe37Ym3bzZJ7H38nRGpNzEYFYAgMwUU1zC9w").getBody();
//    	.parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYWRtIiwiTm9tYnJlIjoiQURNSU5JU1RSQURPUiIsIkFwZWxsaWRvIjoiREVMIFNJU1RFTUEiLCJFbWFpbCI6IiIsIlJvbCI6IkFETUlOSVNUUkFET1IiLCJTZWN0b3IiOiJMQSBQTEFUQSJ9.tJ83pjHmf0cAZF1R3dFfHgAJLhKBL8VdfWVVuxf3rnE8BojIo_Pe37Ym3bzZJ7H38nRGpNzEYFYAgMwUU1zC9w").getBody();       
//    	System.out.println("----------------------------");
//    	        System.out.println("Nombre: " + claims.get("Nombre").toString());
//    	        System.out.println("Apellido: " + claims.get("Apellido").toString());
//    	        System.out.println("Rol: " + claims.get("Rol").toString());
//    	        System.out.println("Email: " + claims.get("Email").toString());
//    	        System.out.println("Sector: " + claims.get("Sector").toString());
    }
    
}

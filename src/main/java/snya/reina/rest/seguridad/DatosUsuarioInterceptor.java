package snya.reina.rest.seguridad;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import snya.reina.rest.seguridad.modelo.UsuarioToken;

public class DatosUsuarioInterceptor implements ClientHttpRequestInterceptor {

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
            UsuarioToken usuarioToken = new UsuarioToken(session);
            HttpHeaders headers = request.getHeaders();
            headers.add("token", JwtUtil.generateToken(usuarioToken));
        return execution.execute(request, body);
    }
}

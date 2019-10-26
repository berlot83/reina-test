package snya.reina.rest.interfaces;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import snya.reina.modelo.informe.Informe;
import snya.reina.rest.dto.InformeDTO;

public interface InformeRest {
	/* Tiene un problema de recursividad en producción */
	public Informe traerInforme(@PathVariable Integer id);
	
	/* Funciona perfecto en producción */
	public ResponseEntity<InformeDTO> traerInformeSimplificado(@PathVariable Integer id, HttpServletRequest request) throws MalformedURLException, Exception ;
}

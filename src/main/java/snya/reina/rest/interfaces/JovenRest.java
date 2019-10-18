package snya.reina.rest.interfaces;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import snya.reina.rest.dto.AsociadorDTO;
import snya.reina.rest.dto.JovenSimpleDTO;

/* Esta interface está principalmente para obligar al la clase que la implementa a utilizar el HttpServletRequest request para obtener el token */
public interface JovenRest {
	public ResponseEntity<JovenSimpleDTO> obtenerJovenSimplificado(Integer id, HttpServletRequest request);
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto1(String buscar, HttpServletRequest request);
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto2(String numeroDocumento, String apellidos, String nombres, HttpServletRequest request);
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto3(String buscar, HttpServletRequest request);
	public ResponseEntity<String> actualizarJoven(Integer id, AsociadorDTO asociador, HttpServletRequest request);
}

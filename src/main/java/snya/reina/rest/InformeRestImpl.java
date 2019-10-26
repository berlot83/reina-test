package snya.reina.rest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import snya.reina.ReinaCte;
import snya.reina.modelo.informe.Informe;
import snya.reina.rest.dto.InformeDTO;
import snya.reina.rest.interfaces.InformeRest;
import snya.reina.rest.seguridad.FuncionesSeguridad;
import snya.reina.rest.seguridad.JwtUtil;
import snya.reina.rest.seguridad.modelo.UsuarioToken;
import snya.reina.servicio.InformeServicioImpl;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/api/informes")
@Api("Informe - Servicio web REST")
public class InformeRestImpl implements InformeRest{

	@Autowired
	InformeServicioImpl informeServicioImpl;

	/* Este endpoint tiene un problema de recursividad */
	@GetMapping("/{id}")
	public Informe traerInforme(@PathVariable Integer id) {
		return informeServicioImpl.traerInforme(id);
	}
	
	@GetMapping("/simple/{id}")
	public ResponseEntity<InformeDTO> traerInformeSimplificado(@PathVariable Integer id, HttpServletRequest request) throws Exception {
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
			if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
				return ResponseEntity.ok(informeServicioImpl.traerInformeSimplificado(id));
			}else {
				return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
			}
		}
}

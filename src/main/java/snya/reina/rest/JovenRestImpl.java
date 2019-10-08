package snya.reina.rest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import snya.reina.ReinaCte;
import snya.reina.modelo.joven.Joven;
import snya.reina.rest.dto.AsociadorDTO;
import snya.reina.rest.dto.JovenSimpleDTO;
import snya.reina.rest.interfaces.JovenRest;
import snya.reina.rest.seguridad.FuncionesSeguridad;
import snya.reina.rest.seguridad.JwtUtil;
import snya.reina.rest.seguridad.modelo.UsuarioToken;
import snya.reina.servicio.ExpedienteServicioImpl;
import snya.reina.servicio.JovenServicioImpl;
import snya.reina.servicio.PersonaServicioImpl;

/* No usamos ninguna librería para conversión a JSON porque Spring-Boot lo devuelve por defecto con Jackson2 - algunos métodos son nativos de la JpaRepository interface */
/* Estructura básica REST CRUD start */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/api/jovenes")
@Api("Joven - Servicio web REST")
public class JovenRestImpl implements JovenRest{

	@Autowired
	JovenServicioImpl jovenServicioImpl;

	@Autowired
	PersonaServicioImpl personaServicioImpl;
	
	@Autowired
	ExpedienteServicioImpl expedienteServicioImpl;

	@GetMapping("/{id}")
	public Joven obtenerJoven(@PathVariable Integer id) {
		return jovenServicioImpl.findById(id);
	}

	@GetMapping("/simple/{id}")
	public ResponseEntity<JovenSimpleDTO> obtenerJovenSimplificado(@PathVariable Integer id, HttpServletRequest request) {
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			Joven joven = jovenServicioImpl.traerPorId(id);
			JovenSimpleDTO js = new JovenSimpleDTO(joven);
			return ResponseEntity.ok(js);	
		}else {
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/simple/busqueda1")
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto1(@RequestParam String buscar, HttpServletRequest request) {
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			return ResponseEntity.ok(jovenServicioImpl.traerPorCriterioDeBusquedaMixto1(buscar));
		}else{
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/simple/busqueda2")
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto2(@RequestParam String numeroDocumento, @RequestParam String apellidos, @RequestParam String nombres, HttpServletRequest request){
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			return ResponseEntity.ok(jovenServicioImpl.traerPorCriterioDeBusquedaMixto2(numeroDocumento, apellidos, nombres));
		}else {
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/expediente/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody ResponseEntity<String> actualizarJoven(@PathVariable Integer id, @RequestBody AsociadorDTO asociador, HttpServletRequest request) {
//		Joven jovenEnDB = null;
//		if (id != null){
//				if(jovenServicioImpl.traerPorId(id) != null) {
//					jovenEnDB = jovenServicioImpl.traerPorId(id);
//					
//					if (legajo != null) {
						/* Asociación de legajos */
						try{
							expedienteServicioImpl.agergarExpedienteExterno(id, asociador.getIdCaratulador(), asociador.getLegajo());
							return ResponseEntity.ok().body("Operación realizada con éxito");
						}catch(Exception error) {
							return ResponseEntity.badRequest().body("El registro no fue encontrado o sólo no fue exitosa la insercción");
						}
						/* debería ser 201 "Created" o 204 "No Content" para PUT, sin embargo puede dejarse 200 por defecto */
//						/* Asociación de legajos */
//					} else {
//						return ResponseEntity.badRequest().body("El legajo es nulo");
//					}					
//				}else {
//					throw new ReinaException(ReinaCte.VALOR_NULO + ", el objeto buscado es nulo y no se puede encontrar");
//				}
//		}else {
//			throw new ReinaException(ReinaCte.VALOR_NULO + ", parámetro 'id' null");
//		}
	}

}

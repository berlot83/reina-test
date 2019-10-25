package snya.reina.rest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.Api;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.joven.Caratulador;
import snya.reina.modelo.joven.Expediente;
import snya.reina.modelo.joven.Joven;
import snya.reina.repositorios.ExpedienteRepositorio;
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

	@Autowired
	ExpedienteRepositorio expedienteRepositorio;
	
	@CrossOrigin
	@GetMapping("/{id}")
	public Joven obtenerJoven(@PathVariable Integer id) {
		return jovenServicioImpl.findById(id);
	}

	@CrossOrigin
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
	
	@CrossOrigin
	@GetMapping("/simple/busqueda1")
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto1(@RequestParam String buscar, HttpServletRequest request) {
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			return ResponseEntity.ok(jovenServicioImpl.traerPorCriterioDeBusquedaMixto1(buscar));
		}else{
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@CrossOrigin
	@GetMapping("/simple/busqueda2")
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto2(@RequestParam String numeroDocumento, @RequestParam String apellidos, @RequestParam String nombres, HttpServletRequest request){
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			return ResponseEntity.ok(jovenServicioImpl.traerPorCriterioDeBusquedaMixto2(numeroDocumento, apellidos, nombres));
		}else {
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@CrossOrigin
	@GetMapping("/simple/busqueda3")
	public ResponseEntity<List<JovenSimpleDTO>> obtenerJovenSimplificadoBusquedaMixto3(@RequestParam String buscar, HttpServletRequest request) {
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			return ResponseEntity.ok(jovenServicioImpl.traerPorCriterioDeBusquedaMixto3(buscar));
		}else{
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}
	
	/* Este enpoint asocia un registro de Reina con un registro de Reuna */
	@CrossOrigin
	@RequestMapping(value = "/expediente/{idJoven}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody ResponseEntity<String> actualizarJoven(@PathVariable Integer idJoven, @RequestBody AsociadorDTO asociador, HttpServletRequest request) throws ReinaException {
		Expediente exp = expedienteRepositorio.findOne(idJoven);
		
		if( exp.getLegajo() == null ) {
			if(idJoven != null && asociador.getIdCaratulador() != null && asociador.getLegajo() != null) {
				expedienteServicioImpl.agergarExpedienteExterno(idJoven, asociador.getIdCaratulador(), asociador.getLegajo());
				return  new ResponseEntity("El objeto fue modificado correctamente", HttpStatus.CREATED);
			}else {
				return new ResponseEntity("Revise los parámetros que está enviando al servidor.", HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity("El joven ya tiene asignado otro legajo.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@CrossOrigin
	@GetMapping("/expediente/{id}")
	public ResponseEntity<String> getExpediente(@PathVariable Integer id) {
		Expediente exp = expedienteRepositorio.findOne(id);
		System.out.println(exp.getLegajo());
		if( exp.getLegajo() == null ) {
			return new ResponseEntity("Entró", HttpStatus.OK);
		}else {
			return new ResponseEntity("No entró", HttpStatus.BAD_REQUEST);
		}
	}

}

package snya.reina.rest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import snya.reina.ReinaCte;
import snya.reina.datos.beneficio.TipoDeBeneficioDAOImpl;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.rest.interfaces.TipoBeneficioRest;
import snya.reina.rest.seguridad.FuncionesSeguridad;
import snya.reina.rest.seguridad.JwtUtil;
import snya.reina.rest.seguridad.modelo.UsuarioToken;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/api/TipoBeneficio")
@Api("TipoBeneficio - Servicio web REST")
public class TipoBeneficioRestImpl implements TipoBeneficioRest{
	
	@Autowired
	TipoDeBeneficioDAOImpl tipoDeBeneficioDAOImpl;
	
	/* Este método funciona y está activo para probar cualquiera de los las funciones de segurida roles */
	@GetMapping("/{id}")
	public ResponseEntity<TipoDeBeneficio> obtenerTipoBeneficio(@PathVariable Integer id, HttpServletRequest request) {
		UsuarioToken usuarioToken = JwtUtil.obtenerUsuarioToken(request);
		if(FuncionesSeguridad.autorizar(usuarioToken, usuarioToken.getRol())) {
			return ResponseEntity.ok(tipoDeBeneficioDAOImpl.traerPorId(id));
		}else {
			return new ResponseEntity(ReinaCte.NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/activos")
	public List<TipoDeBeneficio> obtenerTipoBeneficiosActivos() {
		return tipoDeBeneficioDAOImpl.traerTodosActivos();
	}
	
	@GetMapping("/tarjeta_activos")
	public List<TipoDeBeneficio> obtenerTodosTarjetaActivos() {
		return tipoDeBeneficioDAOImpl.traerTodosTarjetaActivos();
	}
	
	@GetMapping("/pensiones_activos")
	public List<TipoDeBeneficio> obtenerTodosPensionesActivas() {
		return tipoDeBeneficioDAOImpl.traerPensionesActivas();
	}
	
	@GetMapping("/beneficiarios_activos")
	public List<EstadisticaBeneficio> obtenerTodosBeneficiariosActivas(@PathVariable Integer idBeneficio,@PathVariable String idInstitucion){
		return tipoDeBeneficioDAOImpl.listaBeneficiariosActivos(idBeneficio, idInstitucion);
	}
	
	@GetMapping("/beneficiarios_activos/{idBeneficio}/{idInstitucion}")
	public List<EstadisticaBeneficio> obtenerTodosBeneficiariosParaOperativos(@PathVariable Integer idBeneficio,@PathVariable String idInstitucion){
		return tipoDeBeneficioDAOImpl.listaBeneficiosParaOperativo(idBeneficio, idInstitucion);
	}
	
	@PutMapping("/")
	public void actualizar(TipoDeBeneficio tipo) {
		 tipoDeBeneficioDAOImpl.actualizar(tipo);
	}

}

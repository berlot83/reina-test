package snya.reina.rest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import snya.reina.datos.beneficio.TipoDeBeneficioDAOImpl;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;

@RestController
@RequestMapping("/api/TipoBeneficio")
@Api("TipoBeneficio - Servicio web REST")
public class TipoBeneficioRest {
	
	@Autowired
	TipoDeBeneficioDAOImpl tipoDeBeneficioDAOImpl;
	
	@GetMapping("/{id}")
	public TipoDeBeneficio obtenerTipoBeneficio(@PathVariable Integer id) {
		return tipoDeBeneficioDAOImpl.traerPorId(id);
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

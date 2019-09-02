package snya.reina.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import snya.reina.datos.beneficio.EstadoBeneficioDAOImpl;
import snya.reina.modelo.beneficio.EstadoBeneficio;

@RestController
@RequestMapping("/api/beneficio")
@Api("Beneficio - Servicio web REST")
public class BeneficioRest {
	
	@Autowired
	EstadoBeneficioDAOImpl estadoBeneficioDAOImpl;
	
	@GetMapping("/{id}")
	public EstadoBeneficio obtenerBeneficio(@PathVariable Integer id) {
		return estadoBeneficioDAOImpl.traerPorId(id);
	}
	
	@GetMapping("/")
	public List<EstadoBeneficio> obtenerBeneficios() {
		return estadoBeneficioDAOImpl.traerTodos();
	}
}

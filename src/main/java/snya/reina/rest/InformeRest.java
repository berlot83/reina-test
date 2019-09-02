package snya.reina.rest;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import snya.reina.modelo.informe.Informe;
import snya.reina.rest.dto.InformeDTO;
import snya.reina.servicio.InformeServicioImpl;

@RestController
@RequestMapping("/api/informes")
@Api("Informe - Servicio web REST")
public class InformeRest {

	@Autowired
	InformeServicioImpl informeServicioImpl;

	/* Muestra el Informe Detallado con los subobjetos y son la lista de URL de archivos */
	@GetMapping("/{id}")
	public Informe traerInforme(@PathVariable Integer id) {
		return informeServicioImpl.traerInforme(id);
	}
	
	/* Muestra el InformeDTO con datos resumidos en Strings sin los objetos completos */
	@GetMapping("/simplificado/{id}")
	public InformeDTO traerInformeSimplificado(@PathVariable Integer id) throws MalformedURLException {
		return informeServicioImpl.traerInformeSimplificado(id);
	}
}

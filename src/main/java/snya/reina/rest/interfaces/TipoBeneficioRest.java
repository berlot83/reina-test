package snya.reina.rest.interfaces;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import snya.reina.modelo.beneficio.TipoDeBeneficio;

public interface TipoBeneficioRest {
	public ResponseEntity<TipoDeBeneficio> obtenerTipoBeneficio(Integer id, HttpServletRequest request);
}

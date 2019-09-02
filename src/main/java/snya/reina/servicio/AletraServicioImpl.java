package snya.reina.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.reina.datos.estadistica.AlertaDAO;
import snya.reina.modelo.MedidaImpuestaAlerta;

@Service
public class AletraServicioImpl {

	@Autowired
	private AlertaDAO alertaDAO;
	
	
	public List<MedidaImpuestaAlerta> listaAletaVencia_y_AVencer(int dias, Integer[] tipos, Integer[] recursos, 
			String propiedad, String orden) {
		return alertaDAO.listaAletaVencia_y_AVencer(dias, tipos, recursos, propiedad, orden);
	}
}

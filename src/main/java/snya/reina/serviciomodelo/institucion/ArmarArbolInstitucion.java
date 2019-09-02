package snya.reina.serviciomodelo.institucion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import snya.reina.datos.institucion.InstitucionDAO;
import snya.reina.modelo.institucion.Institucion;

@Component
public class ArmarArbolInstitucion {

	@Autowired
	private InstitucionDAO institucionREINADAO;

	public List<Institucion> traerSectorConHijos(Integer idSector) {		
		return _traerSectorConHijos(idSector);
	}
	
	private List<Institucion> _traerSectorConHijos(Integer idSector) {
		List<Institucion> lista = new ArrayList<Institucion>();
		Institucion solicitante = institucionREINADAO.traerPorIdCompleto(idSector);		
		List<Institucion> hijos = institucionREINADAO.traerHijosDelPadre(idSector);
		
		// caso base
		lista.add(solicitante);

		// recursion		
		for (Institucion hijo : hijos) {
			if (hijo.getEstaActivo()) {
				List<Institucion> hijos2 = traerSectorConHijos(hijo.getId());
				lista.addAll(hijos2);
			}
		}
				
		return lista;
	}
}

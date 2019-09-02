package snya.reina.serviciomodelo.intervencion;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import snya.reina.datos.intervencion.TipoDeIntervencionDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.TipoDeIntervencion;
import snya.reina.modelo.joven.Joven;

@Component
public class GeneradorIntervencion {

	@Autowired
	private TipoDeIntervencionDAO tipoDeIntervencionDAO;
	@Autowired
	private JovenDAO jovenDAO;

	
	public Intervencion crearIntervencion(Joven joven, Integer idTipoDeIntervencion) {
		// Genero la intervencion
		TipoDeIntervencion tipo = tipoDeIntervencionDAO.traerPorId(idTipoDeIntervencion);
		Intervencion intervencion = joven.agregarIntervencion(tipo);
		return intervencion;
	}
	
	
	public Intervencion generarIntervencionMonitoreo(Joven joven) {
		// Genero la intervencion
		TipoDeIntervencion tipo = tipoDeIntervencionDAO.traerPorId(TipoDeIntervencion.ID_MONITOREO_TERRITORIAL);
		Intervencion intervencion = joven.agregarIntervencion(tipo);
		jovenDAO.guardarIntervencion(intervencion);
		return intervencion;
	}

	public Intervencion recuperarIntervencionMonitoreo(Joven joven) {
		Iterator<Intervencion> iter = joven.getIntervenciones().iterator();
		Intervencion actual = null;

		while ((actual == null) && iter.hasNext()) {
			Intervencion presente = (Intervencion) iter.next();
			if (presente.getTipo().getId() == TipoDeIntervencion.ID_MONITOREO_TERRITORIAL && presente.getEstaActivo() && !presente.getEliminada())
				actual = presente;
		}

		return actual;
	}
	
	public Intervencion generarIntervencionContextoEncierro(Joven joven) {
		TipoDeIntervencion tipo = tipoDeIntervencionDAO.traerPorId(TipoDeIntervencion.ID_CONTEXTO_ENCIERRO);
		Intervencion intervencion = joven.agregarIntervencion(tipo);
		jovenDAO.guardarIntervencion(intervencion);
		return intervencion;
	}

	public Intervencion recuperarIntervencionContextoEncierro(Joven joven) {
		Iterator<Intervencion> iter = joven.getIntervenciones().iterator();
		Intervencion actual = null;

		while ((actual == null) && iter.hasNext()) {
			Intervencion presente = (Intervencion) iter.next();
			if (presente.getTipo().getId() == TipoDeIntervencion.ID_CONTEXTO_ENCIERRO && presente.getEstaActivo() && !presente.getEliminada())
				actual = presente;
		}

		return actual;
	}

	public Intervencion generarIntervencionAdmision(Joven joven) {
		TipoDeIntervencion tipo = tipoDeIntervencionDAO.traerPorId(TipoDeIntervencion.ID_ADMISION_CAD);
		Intervencion intervencion = joven.agregarIntervencion(tipo);
		jovenDAO.guardarIntervencion(intervencion);
		return intervencion;
	}
	
	public Intervencion recuperarIntervencionAdmision(Joven joven) {
		Iterator<Intervencion> iter = joven.getIntervenciones().iterator();
		Intervencion actual = null;

		while ((actual == null) && iter.hasNext()) {
			Intervencion presente = (Intervencion) iter.next();
			if (presente.getTipo().getId() == TipoDeIntervencion.ID_ADMISION_CAD && presente.getEstaActivo() && !presente.getEliminada())
				actual = presente;
		}

		return actual;
	}
}

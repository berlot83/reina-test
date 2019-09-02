package snya.reina.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.reina.ReinaException;
import snya.reina.datos.educacion.CapacitacionDAO;
import snya.reina.datos.educacion.TematicaDeCapacitacionDAO;
import snya.reina.datos.educacion.TipoDeCapacitacionDAO;
import snya.reina.datos.educacion.TipoDeEstadoCapacitacionJovenDAO;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.educacion.Capacitacion;
import snya.reina.modelo.educacion.CapacitacionJoven;
import snya.reina.modelo.educacion.Dictado;
import snya.reina.modelo.educacion.TematicaDeCapacitacion;
import snya.reina.modelo.educacion.TipoDeCapacitacion;
import snya.reina.modelo.educacion.TipoDeEstadoCapacitacionJoven;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.escolaridad.ReguladorDeEscolaridad;

@Service
public class CapacitacionServicioImpl {

	@Autowired
	private CapacitacionDAO capacitacionDAO;
	@Autowired
	private ReguladorDeEscolaridad regulador;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private TipoDeCapacitacionDAO tipoDeCapacitacionDAO;
	@Autowired
	private TematicaDeCapacitacionDAO tematicaDeCapacitacionDAO;
	@Autowired
	private RecursoREINADAO recursoDAO;
	@Autowired
	private TipoDeEstadoCapacitacionJovenDAO estadoCapacitacionDAO;
	
	//
	// CAPACITACION
	//
	@Transactional
	public Capacitacion agregarCapacitacion(String nombre, Integer idTipo, Integer idTematica, boolean tieneCertificacion,
			String institucionFinancia, String institucionEjecutora, Integer duracion, String descripcion) throws ReinaException {
		TipoDeCapacitacion tipo = tipoDeCapacitacionDAO.traerPorId(idTipo);
		TematicaDeCapacitacion tematica = tematicaDeCapacitacionDAO.traerPorId(idTematica);
				
		Capacitacion capacitacion = new Capacitacion(nombre, tipo, tematica, tieneCertificacion, null, true,
				institucionFinancia, institucionEjecutora, duracion, descripcion, false, null);
		
		capacitacionDAO.insertar(capacitacion);
		return capacitacion;
	}

	@Transactional
	public void actualizarCapacitacion(Integer id, String nombre, Integer idTipo, Integer idTematica,
			boolean tieneCertificacion, String institucionFinancia, String institucionEjecutora, Integer duracion,
			String descripcion) throws ReinaException {
		TipoDeCapacitacion tipo = tipoDeCapacitacionDAO.traerPorId(idTipo);
		TematicaDeCapacitacion tematica = tematicaDeCapacitacionDAO.traerPorId(idTematica);
				
		Capacitacion capacitacion = this.traerPorId(id);
		capacitacion.setNombre(nombre);
		capacitacion.setTipo(tipo);
		capacitacion.setTematica(tematica);
		capacitacion.setTieneCertificacion(tieneCertificacion);
		capacitacion.setInstitucionFinancia(institucionFinancia);
		capacitacion.setInstitucionEjecutora(institucionEjecutora);
		capacitacion.setDuracion(duracion);
		capacitacion.setDescripcion(descripcion);
		
		capacitacionDAO.actualizar(capacitacion);
	}

	@Transactional
	public void agregarDictado(Integer idCapacitacion, String nombre, String idInstitucion, 
			Date fInicio, Date fFin, String descripcion) throws ReinaException {
		String[] ids = idInstitucion.split(",");
		List<Recurso> recursos = new ArrayList<Recurso>();
		for (String id : ids) {
			recursos.add( recursoDAO.traerPorId( new Integer(id.trim()) ) );
		}
				
		Capacitacion capacitacion = this.traerPorId(idCapacitacion);
		capacitacion.agregarDictado(nombre, recursos, fInicio, fFin, descripcion);
		
		capacitacionDAO.actualizar(capacitacion);		
	}

	@Transactional
	public void actualizarDictado(Integer idCapacitacion, Integer id, String nombre, String idInstitucion, 
			Date fInicio, Date fFin, String descripcion) throws ReinaException {
		String[] ids = idInstitucion.split(",");
		List<Recurso> recursos = new ArrayList<Recurso>();
		for (String idR : ids) {
			recursos.add( recursoDAO.traerPorId( new Integer(idR.trim()) ) );
		}
				
		Capacitacion capacitacion = this.traerPorId(idCapacitacion);
		Dictado dictado = capacitacion.traerDictado(id);
		dictado.setNombre(nombre);
		dictado.setFechaInicio(fInicio);
		dictado.setFechaFin(fFin);
		dictado.setDescripcion(descripcion);
		dictado.actualizarAlcance(recursos);
		
		capacitacionDAO.actualizar(capacitacion);		
	}
	
	public Capacitacion traerPorId(Integer id) {
		Capacitacion cap = capacitacionDAO.traerPorId(id, false);
		
		if (cap != null) {
			for (Dictado dictado : cap.getDictados()) {
				for (Recurso recurso : dictado.getInstituciones()) {
					recurso.getComponente().getNombre();
				}
			}
		}
		
		return capacitacionDAO.traerPorId(id, false);
	}

	public List<TipoDeCapacitacion> traerTiposCapacitacionActivos() {
		return tipoDeCapacitacionDAO.traerTodosActivos();
	}

	public List<TematicaDeCapacitacion> traerTematicaDeCapacitacionActivos() {
		return tematicaDeCapacitacionDAO.traerTodosActivos();
	}

	
	//
	// CAPACITACION JOVEN
	//
	@Transactional
	public void agregarCapacitacionJoven(Integer idJoven, Integer idCapacitacion, Integer idDictado, Date fInicio, Date fFin,
			Integer idEstado, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Capacitacion capacitacion = capacitacionDAO.traerPorId(idCapacitacion, false);
		Dictado dictado = this.traerDictadoPorId(capacitacion, idDictado);
		TipoDeEstadoCapacitacionJoven estado = estadoCapacitacionDAO.traerPorId(idEstado);
		
		regulador.agregarCapacitacion(_traerCapacitacionActivas(joven), joven, capacitacion, dictado, fInicio, fFin, estado, observacion, null);
		jovenDAO.actualizar(joven);		
	}

	@Transactional
	public void actualizarCapacitacionJoven(Integer idJoven, Integer id, Integer idCapacitacion, Integer idDictado,
			Date fInicio, Date fFin, Integer idEstado, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		CapacitacionJoven capacitacionJoven = this.traerCapacitacion(joven, id);
		Capacitacion capacitacion = capacitacionDAO.traerPorId(idCapacitacion, false);
		Dictado dictado = this.traerDictadoPorId(capacitacion, idDictado);
		TipoDeEstadoCapacitacionJoven estado = estadoCapacitacionDAO.traerPorId(idEstado);
		
		regulador.modificarCapacitacion(joven, capacitacionJoven, capacitacion, dictado, fInicio, fFin, estado, observacion, null);
		jovenDAO.actualizar(joven);		
	}

	@Transactional
	public void eliminarCapacitacionJoven(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		CapacitacionJoven capacitacion = this.traerCapacitacion(joven, id);
		
		regulador.eliminarCapacitacion(joven, capacitacion);
		jovenDAO.actualizar(joven);		
	}
	
	public List<Capacitacion> traerCapacitaciones() {
		return capacitacionDAO.traerTodos(false);
	}
	
	public List<Capacitacion> traerCapacitacionesActivas() {
		return capacitacionDAO.traerTodosActivos(false);
	}
	
	public List<Dictado> traerDictadosActivasPorCapacitacion(Integer id) {
		return capacitacionDAO.traerDictadosTodosActivosPorId(id, new Integer[0]);
	}
	
	
	private Dictado traerDictadoPorId(Capacitacion capacitacion, Integer idDictado) {
		java.util.Iterator<Dictado> iter = capacitacion.getDictados().iterator();
		Dictado dictado = null;
		
		while (iter.hasNext()) {
			Dictado dictadoAct = iter.next();
			if (dictadoAct.getId().equals(idDictado))
				dictado = dictadoAct;
		}
		
		return dictado;
	}
	
	private CapacitacionJoven traerCapacitacion(Joven joven, Integer id) {
		java.util.Iterator<CapacitacionJoven> iter = joven.getCapacitaciones().iterator();
		CapacitacionJoven cap = null;
		
		while (iter.hasNext()) {
			CapacitacionJoven capaAct = iter.next();
			if (capaAct.getId().equals(id))
				cap = capaAct;
		}
		
		return cap;
	}
	
	private List<Capacitacion> _traerCapacitacionActivas(Joven joven) {
		List<AmbitoEjecucion> ambitos = joven.traerAmbitosPresentes();
		Integer[] ids = new Integer[ambitos.size()];
		int i = 0;

		for (AmbitoEjecucion amb : ambitos) {
			ids[i++] = amb.getId(); 
		}
		
		return capacitacionDAO.traerTodosActivosEnAmbito(false, ids);
	}
}

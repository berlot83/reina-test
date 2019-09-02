package snya.reina.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.reina.ReinaException;
import snya.reina.datos.beneficio.TipoDeBeneficioDAO;
import snya.reina.datos.educacion.CapacitacionDAO;
import snya.reina.datos.educacion.TematicaDeCapacitacionDAO;
import snya.reina.datos.educacion.TipoDeCapacitacionDAO;
import snya.reina.datos.educacion.TipoDeEstadoCapacitacionJovenDAO;
import snya.reina.datos.estadistica.AlertaDAO;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.educacion.Capacitacion;
import snya.reina.modelo.educacion.CapacitacionJoven;
import snya.reina.modelo.educacion.Dictado;
import snya.reina.modelo.educacion.TematicaDeCapacitacion;
import snya.reina.modelo.educacion.TipoDeCapacitacion;
import snya.reina.modelo.educacion.TipoDeEstadoCapacitacionJoven;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.escolaridad.ReguladorDeEscolaridad;

@Service
public class FormacionLaboralServicioImpl {

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
	@Autowired
	private AlertaDAO alertaDAO;
	@Autowired
	private TipoDeBeneficioDAO tipoDeBeneficio;
	
	//
	// FORMACION LABORAL
	//
	@Transactional
	public Capacitacion agregarFormacionLaboral(String nombre, Integer idTipo, Integer idTematica, boolean tieneCertificacion,
			String institucionFinancia, String institucionEjecutora, Integer duracion, String descripcion, Institucion supervisa, Integer idBeneficio) throws ReinaException {
		TipoDeCapacitacion tipo = tipoDeCapacitacionDAO.traerPorId(idTipo);
		TematicaDeCapacitacion tematica = tematicaDeCapacitacionDAO.traerPorId(idTematica);
		TipoDeBeneficio beneficio = (idBeneficio == null || idBeneficio == 0) ? null : tipoDeBeneficio.traerPorId(idBeneficio);

		// comprobar que sea un nombre valido
		if (nombre == null || nombre.equals(""))
			throw new ReinaException("Debe consignar el nombre del proyecto de formación laboral");
		
		Capacitacion formacionLaboral = new Capacitacion(nombre, tipo, tematica, tieneCertificacion, supervisa, true,
				institucionFinancia, institucionEjecutora, duracion, descripcion, true, beneficio);
		
		capacitacionDAO.insertar(formacionLaboral);
		return formacionLaboral;
	}

	@Transactional
	public void actualizarFormacionLaboral(Integer id, String nombre, Integer idTipo, Integer idTematica,
			boolean tieneCertificacion, String institucionFinancia, String institucionEjecutora, Integer duracion,
			String descripcion, Integer idBeneficio) throws ReinaException {
		TipoDeCapacitacion tipo = tipoDeCapacitacionDAO.traerPorId(idTipo);
		TematicaDeCapacitacion tematica = tematicaDeCapacitacionDAO.traerPorId(idTematica);			
		TipoDeBeneficio beneficio = (idBeneficio == null || idBeneficio == 0) ? null : tipoDeBeneficio.traerPorId(idBeneficio);
		
		// comprobar que sea un nombre valido
		if (nombre == null || nombre.equals(""))
			throw new ReinaException("Debe consignar el nombre del proyecto de formación laboral");
				
		Capacitacion formacionLaboral = this.traerPorId(id);
		formacionLaboral.setNombre(nombre);
		formacionLaboral.setTipo(tipo);
		formacionLaboral.setTematica(tematica);
		formacionLaboral.setTieneCertificacion(tieneCertificacion);
		formacionLaboral.setInstitucionFinancia(institucionFinancia);
		formacionLaboral.setInstitucionEjecutora(institucionEjecutora);
		formacionLaboral.setDuracion(duracion);
		formacionLaboral.setDescripcion(descripcion);
		formacionLaboral.setBeneficio(beneficio);

		capacitacionDAO.actualizar(formacionLaboral);
	}

	@Transactional
	public void agregarDictado(Integer idCapacitacion, String nombre, String idInstitucion, 
			Date fInicio, Date fFin, String descripcion) throws ReinaException {
		String[] ids = idInstitucion.split(",");
		List<Recurso> recursos = new ArrayList<Recurso>();
		for (String id : ids) {
			recursos.add( recursoDAO.traerPorId( new Integer(id.trim()) ) );
		}

		// comprobar que la formacion solicitada es valida 
		Capacitacion formacionLaboral = this.traerPorId(idCapacitacion);
		if (formacionLaboral == null)
			throw new ReinaException("Debe consignar un proyecto de formacion laboral");			
		// comprobar que sea un nombre valido
		if (nombre == null || nombre.equals(""))
			throw new ReinaException("Debe consignar el nombre del dictado");
		// que no quiera comenzar antes del inicio del dictado
		if (fFin != null && fInicio.after(fFin))
			throw new  ReinaException("La fecha de inicio del dictado de la formación laboral " + formacionLaboral.getNombre() + " debe ser anterior a la fecha de finalización");
		
		formacionLaboral.agregarDictado(nombre, recursos, fInicio, fFin, descripcion);		
		capacitacionDAO.actualizar(formacionLaboral);		
	}

	@Transactional
	public void actualizarDictado(Integer idCapacitacion, Integer id, String nombre, String idInstitucion, 
			Date fInicio, Date fFin, String descripcion) throws ReinaException {
		String[] ids = idInstitucion.split(",");
		List<Recurso> recursos = new ArrayList<Recurso>();
		for (String idR : ids) {
			recursos.add( recursoDAO.traerPorId( new Integer(idR.trim()) ) );
		}
		
		// comprobar que la formacion solicitada es valida
		Capacitacion formacionLaboral = this.traerPorId(idCapacitacion);
		if (formacionLaboral == null)
			throw new ReinaException("Debe consignar un proyecto de formacion laboral");
		// que no quiera comenzar antes del inicio del dictado
		if (fFin != null && fInicio.after(fFin))
			throw new  ReinaException("La fecha de inicio del dictado de la formación laboral " + formacionLaboral.getNombre() + " debe ser anterior a la fecha de finalización");

		Dictado dictado = formacionLaboral.traerDictado(id);
		dictado.setNombre(nombre);
		dictado.setFechaInicio(fInicio);
		dictado.setFechaFin(fFin);
		dictado.setDescripcion(descripcion);
		dictado.actualizarAlcance(recursos);
		
		capacitacionDAO.actualizar(formacionLaboral);		
	}
	
	@Transactional
	public void finalizarDictado(Integer idCapacitacion, Integer idDictado, Date fFecha, List<EstadisticaFormacionLaboral> jovenes) throws ReinaException {
		// Valido que no pongan que siguen cursando
		for (EstadisticaFormacionLaboral jo : jovenes) {
			if (jo.getIdEstado().equals(TipoDeEstadoCapacitacionJoven.CURSANDO))
				throw new ReinaException("Existen jovenes que aún se indican cursando el proyecto de formacion laboral");
		}
		
		// Actualizo formacion laboral
		// comprobar que la formacion solicitada es valida
		Capacitacion formacionLaboral = this.traerPorId(idCapacitacion);
		if (formacionLaboral == null)
			throw new ReinaException("Debe consignar un proyecto de formacion laboral");
		
		Dictado dictado = formacionLaboral.traerDictado(idDictado);
		// que no quiera comenzar antes del inicio del dictado
		if (fFecha != null && dictado.getFechaInicio().after(fFecha))
			throw new  ReinaException("La fecha de inicio del dictado de la formación laboral " + formacionLaboral.getNombre() + " debe ser anterior a la fecha de finalización");
		
		dictado.setFechaFin(fFecha);
		dictado.setEstaActivo(false);
		capacitacionDAO.actualizar(formacionLaboral);
		
		// Actualizo el joven
		for (EstadisticaFormacionLaboral jo : jovenes) {
			Joven joven = jovenDAO.traerPorId(jo.getIdJoven());
			CapacitacionJoven capacitacionJoven = this.traerCapacitacion(joven, idCapacitacion, idDictado);
			
			TipoDeEstadoCapacitacionJoven estado = estadoCapacitacionDAO.traerPorId(jo.getIdEstado());
						
			capacitacionJoven.setEstado(estado);
			capacitacionJoven.setFechaFin(fFecha);
			jovenDAO.actualizar(joven);
		}
	}
	
	@Transactional
	public void certificarDictado(Integer idCapacitacion, Integer idDictado, Date fFecha, String[] ids) throws ReinaException {
		
		// Actualizo formacion laboral
		// comprobar que la formacion solicitada es valida
		Capacitacion formacionLaboral = this.traerPorId(idCapacitacion);
		if (formacionLaboral == null)
			throw new ReinaException("Debe consignar un proyecto de formacion laboral");
		
		Dictado dictado = formacionLaboral.traerDictado(idDictado);
		// que no quiera comenzar antes del inicio del dictado
		if (fFecha != null && dictado.getFechaInicio().after(fFecha))
			throw new  ReinaException("La fecha de inicio del dictado de la formación laboral " + formacionLaboral.getNombre() + " debe ser anterior a la fecha de certificación");
		
		
		// Actualizo el joven
		for (String id : ids) {
			Joven joven = jovenDAO.traerPorIdCapacitacion(Integer.valueOf(id));
			CapacitacionJoven capacitacionJoven = this.traerCapacitacion(joven, idCapacitacion, idDictado);
			
			capacitacionJoven.setFechaEntregaCertificado(fFecha);
			jovenDAO.actualizar(joven);
		}		
	}
	
	public Capacitacion traerPorId(Integer id) {
		Capacitacion cap = capacitacionDAO.traerPorId(id, true);
		
		if (cap != null) {
			for (Dictado dictado : cap.getDictados()) {
				for (Recurso recurso : dictado.getInstituciones()) {
					recurso.getComponente().getNombre();
				}
			}
		}
		
		return cap;
	}

	public List<TipoDeCapacitacion> traerTiposFormacionLaboralActivos() {
		return tipoDeCapacitacionDAO.traerTodosActivos();
	}

	public List<TematicaDeCapacitacion> traerTematicaDeFormacionLaboralActivos() {
		return tematicaDeCapacitacionDAO.traerTodosActivos();
	}

	public List<TipoDeEstadoCapacitacionJoven> traerTiposDeEstados() {
		return estadoCapacitacionDAO.traerTodos();
	}

	public List<EstadisticaFormacionLaboral> listaFormacionLaboralCursante(Integer idFormacion, Integer idDictado) {
		return capacitacionDAO.listaFormacionLaboralCursante(idFormacion, idDictado, null);
	}

	public List<EstadisticaFormacionLaboral> listaFormacionLaboralCursanteFinalizado(Integer idFormacion, Integer idDictado) {
		List<EstadisticaFormacionLaboral> jovenes = capacitacionDAO.listaFormacionLaboralCursante(idFormacion, idDictado, TipoDeEstadoCapacitacionJoven.APROBADO);
		jovenes.addAll( capacitacionDAO.listaFormacionLaboralCursante(idFormacion, idDictado, TipoDeEstadoCapacitacionJoven.FINALIZADO) );
		
		return jovenes;
	}

	
	//
	// FORMACION LABORAL JOVEN
	//
	@Transactional
	public void agregarFormacionLaboralJoven(Integer idJoven, Integer idCapacitacion, Integer idDictado, Date fInicio, Date fFin,
			Integer idEstado, String observacion, Date fechaEntregaCertificado) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		Capacitacion capacitacion = capacitacionDAO.traerPorId(idCapacitacion, true);
		if (capacitacion == null)
			throw new ReinaException("Debe consignar un proyecto de formacion laboral");
		
		Dictado dictado = this.traerDictadoPorId(capacitacion, idDictado);
		if (dictado == null)
			throw new ReinaException("Debe consignar un dictado del proyecto de formacion laboral");
		
		TipoDeEstadoCapacitacionJoven estado = estadoCapacitacionDAO.traerPorId(idEstado);
				
		regulador.agregarCapacitacion(_traerFormacionesLaboralesActivas(joven), joven, capacitacion, dictado, fInicio, fFin, estado, observacion, fechaEntregaCertificado);
		jovenDAO.actualizar(joven);		
	}

	@Transactional
	public void actualizarFormacionLaboralJoven(Integer idJoven, Integer id, Integer idCapacitacion, Integer idDictado,
			Date fInicio, Date fFin, Integer idEstado, String observacion, Date fechaEntregaCertificado) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		CapacitacionJoven capacitacionJoven = this.traerCapacitacion(joven, id);
		
		Capacitacion capacitacion = capacitacionDAO.traerPorId(idCapacitacion, true);
		if (capacitacion == null)
			throw new ReinaException("Debe consignar un proyecto de formacion laboral");
		
		Dictado dictado = this.traerDictadoPorId(capacitacion, idDictado);
		if (dictado == null)
			throw new ReinaException("Debe consignar un dictado del proyecto de formacion laboral");
		
		
		TipoDeEstadoCapacitacionJoven estado = estadoCapacitacionDAO.traerPorId(idEstado);
		
		regulador.modificarCapacitacion(joven, capacitacionJoven, capacitacion, dictado, fInicio, fFin, estado, observacion, fechaEntregaCertificado);
		jovenDAO.actualizar(joven);		
	}

	@Transactional
	public void eliminarFormacionLaboralJoven(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		CapacitacionJoven capacitacion = this.traerCapacitacion(joven, id);
		
		regulador.eliminarCapacitacion(joven, capacitacion);
		jovenDAO.actualizar(joven);		
	}
	
	
	//
	// ALERTA
	//
	public List<EstadisticaFormacionLaboral> listaAlertaFormacionLaboralInterrumpida(String idTipoRecurso, String idRecurso, Integer idFormacion) {
		return alertaDAO.listaAlertaFormacionLaboralInterrumpida(idTipoRecurso, idRecurso, idFormacion);
	}
	
	public List<EstadisticaFormacionLaboral> listaAlertaFormacionLaboralCertificacion(String idTipoRecurso, String idRecurso, Integer idFormacion) {
		return alertaDAO.listaAlertaFormacionLaboralCertificacion(idTipoRecurso, idRecurso, idFormacion);
	}
	
	public List<Capacitacion> traerFormacionesLaborales() {
		return capacitacionDAO.traerTodos(true);
	}
	
	public List<Capacitacion> traerFormacionesLaboralesActivas(Integer idJoven) {
		Joven joven = jovenDAO.traerPorId(idJoven);

		return _traerFormacionesLaboralesActivas(joven);
	}
	
	public List<Dictado> traerDictadosActivasPorFormacionLaboral(Integer id) {
		return capacitacionDAO.traerDictadosTodosActivosPorId(id, new Integer[0]);
	}
	
	public List<Dictado> traerDictadosActivasPorFormacionLaboralYJoven(Integer id, Integer idJoven) {
		Joven joven = jovenDAO.traerPorId(idJoven);
		List<AmbitoEjecucion> ambitos = joven.traerAmbitosPresentes();
		Integer[] ids = new Integer[ambitos.size()];
		int i = 0;

		for (AmbitoEjecucion amb : ambitos) {
			ids[i++] = amb.getId(); 
		}	
		
		return capacitacionDAO.traerDictadosTodosActivosPorId(id, ids);
	}
	
	private List<Capacitacion> _traerFormacionesLaboralesActivas(Joven joven) {
		List<AmbitoEjecucion> ambitos = joven.traerAmbitosPresentes();
		Integer[] ids = new Integer[ambitos.size()];
		int i = 0;

		for (AmbitoEjecucion amb : ambitos) {
			ids[i++] = amb.getId(); 
		}
		
		return capacitacionDAO.traerTodosActivosEnAmbito(true, ids);
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
	
	private CapacitacionJoven traerCapacitacion(Joven joven, Integer idCapacitacion, Integer idDictado) {
		java.util.Iterator<CapacitacionJoven> iter = joven.getCapacitaciones().iterator();
		CapacitacionJoven cap = null;
		
		while (iter.hasNext()) {
			CapacitacionJoven capaAct = iter.next();
			if (capaAct.getCapacitacion().getId().equals(idCapacitacion) && capaAct.getDictado().getId().equals(idDictado))
				cap = capaAct;
		}
		
		return cap;
	}
}

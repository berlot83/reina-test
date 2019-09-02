package snya.reina.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.ObraSocialDAO;
import snya.general.datos.TipoDeDiscapacidadDAO;
import snya.general.modelo.ObraSocial;
import snya.general.modelo.TipoDeDiscapacidad;
import snya.reina.ReinaException;
import snya.reina.datos.beneficio.TipoDeBeneficioDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.salud.EstadoObraSocialDAO;
import snya.reina.datos.salud.TipoDeDiagnosticoDAO;
import snya.reina.modelo.beneficio.EstadoObraSocial;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.salud.EvolucionHistoriaClinica;
import snya.reina.modelo.salud.HistoriaClinica;
import snya.reina.modelo.salud.TipoDeDiagnostico;

@Service
public class SaludServicioImpl {

	@Autowired
	private TipoDeBeneficioDAO tipoDeBeneficioDAO;
	@Autowired
	private ObraSocialDAO obraSocialDAO;
	@Autowired
	private EstadoObraSocialDAO estadoObraSocialDAO;
	@Autowired
	private TipoDeDiscapacidadDAO tipoDeDiscapacidadDAO;
	@Autowired
	private TipoDeDiagnosticoDAO tipoDeDiagnosticoDAO;		
	@Autowired
	private JovenDAO jovenDAO;
	
	@Transactional
	public void guardarObraSocialDiscapacidad(Integer idJoven,
			Boolean tieneObraSocial, Integer idObraSocial, String numeroCarnet,
			Date fVencimientoCarnet, String observacionObraSocial,
			Boolean tieneDiscapacidad, Integer idTipoDiscapacidad,
			Integer porcentajeDiscapacidad, Boolean certificadoDiscapacidad,
			Date fDiscapacidad, String observacionDiscapacidad) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);		
				
		if(tieneObraSocial) {			
			ObraSocial obraSocial = obraSocialDAO.traerPorId(idObraSocial);
			int idEstadoObraSocial = (fVencimientoCarnet != null && fVencimientoCarnet.before(new Date())) ? EstadoObraSocial.SUSPENDIDA : EstadoObraSocial.ACTIVA;
			EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(idEstadoObraSocial);

			joven.agregarObraSocial(obraSocial, numeroCarnet, fVencimientoCarnet, observacionObraSocial, estado);
		} else {
			EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(EstadoObraSocial.SE_DESCONOCE);
			joven.indicarEstadoObraSocial(estado, observacionObraSocial);
		}
			
		
		if(tieneDiscapacidad) {
			EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(EstadoObraSocial.SE_DESCONOCE);
			TipoDeDiscapacidad tipoDeDiscapacidad = tipoDeDiscapacidadDAO.traerPorId(idTipoDiscapacidad);
			joven.agregarDiscapacidad(tipoDeDiscapacidad, porcentajeDiscapacidad, certificadoDiscapacidad, fDiscapacidad, observacionDiscapacidad, estado);
		}
				
		jovenDAO.actualizar(joven);	
	}

	@Transactional
	public void guardarObraSocial(Integer idJoven, Integer idObraSocial, String numeroCarnet, Date fVencimientoCarnet, String observacionObraSocial, Integer idEstadoObraSocial) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		ObraSocial obraSocial = null;
		if(!idEstadoObraSocial.equals(EstadoObraSocial.NO_POSEE) && !idEstadoObraSocial.equals(EstadoObraSocial.SE_DESCONOCE)) {
			obraSocial = obraSocialDAO.traerPorId(idObraSocial);
		
			idEstadoObraSocial = (fVencimientoCarnet != null && fVencimientoCarnet.before(new Date()) && idEstadoObraSocial.equals(EstadoObraSocial.ACTIVA)  ) ? EstadoObraSocial.SUSPENDIDA : EstadoObraSocial.ACTIVA;
			EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(idEstadoObraSocial);
			
			joven.agregarObraSocial(obraSocial, numeroCarnet, fVencimientoCarnet, observacionObraSocial, estado);
		} else {
			EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(idEstadoObraSocial);
			joven.agregarObraSocial(null, "", null, "", estado);
		}
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarDiscapacidad(Integer idJoven, Integer idTipoDiscapacidad,
			Integer porcentajeDiscapacidad, Boolean certificadoDiscapacidad,
			Date fechaDiscapacidad, String observacionDiscapacidad) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(EstadoObraSocial.SE_DESCONOCE);
		
		TipoDeDiscapacidad tipoDeDiscapacidad = tipoDeDiscapacidadDAO.traerPorId(idTipoDiscapacidad);
		joven.agregarDiscapacidad(tipoDeDiscapacidad, porcentajeDiscapacidad, certificadoDiscapacidad, fechaDiscapacidad, observacionDiscapacidad, estado);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void registroSinDiscapacidad(Integer idJoven) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		joven.sinDiscapacidad();
		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void guardarHistoriaTratamiento(Integer idJoven,
			Date fecha, Integer idDiagnostico, String diagnostico,
			String fase, String tratamiento, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		TipoDeDiagnostico tipo = tipoDeDiagnosticoDAO.traerPorId(idDiagnostico);
				
		HistoriaClinica historia = new HistoriaClinica(fecha, tipo, diagnostico, fase, tratamiento, observacion);
		joven.getTratamientos().add(historia);
		historia.setJoven(joven);
		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void actualizarHistoriaTratamiento(Integer idJoven, Integer id,
			Integer idDiagnostico, String diagnostico, String fase,
			String tratamiento, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		TipoDeDiagnostico tipo = tipoDeDiagnosticoDAO.traerPorId(idDiagnostico);
		HistoriaClinica historia = traerHistoriaClinica(joven, id);
		
		historia.actualizarTratamiento(tipo, diagnostico, fase, tratamiento, observacion);
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional
	public void cerrarHistoriaTratamiento(Integer idJoven, Integer id, Date fecha, String fase, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		joven.getTratamientoActual().cerrarTratamiento(fecha, fase, observacion);		
		jovenDAO.actualizar(joven);
	}
	
	public List<EvolucionHistoriaClinica> traerEvoluciones(Integer idJoven, Integer idHistoria) {
		Joven joven = jovenDAO.traerPorId(idJoven);
		return traerEvoluciones(joven, idHistoria);
	}
	

	public List<TipoDeBeneficio> traerPensionesActivasTodas() {
		return tipoDeBeneficioDAO.traerPensionesActivas();
	}

	public List<ObraSocial> traerObrasSocialesActivasTodas() {
		return obraSocialDAO.traerTodos();
	}

	public List<EstadoObraSocial> traerEstadosObraSocialActivos() {
		return estadoObraSocialDAO.traerTodos();
	}
	
	public List<TipoDeDiscapacidad> traerDiscapacidadesActivosTodas() {
		return tipoDeDiscapacidadDAO.traerTodosActivos();
	}
	
	public List<TipoDeDiagnostico> traerDiagnosticosActivosTodas() {
		return tipoDeDiagnosticoDAO.traerTodosActivos();
	}
	
	public List<TipoDeDiagnostico> traerDiagnosticosActivos(String diagnostico) {
		return tipoDeDiagnosticoDAO.traerTodosActivos(diagnostico);
	}
	
	private List<EvolucionHistoriaClinica> traerEvoluciones(Joven joven, Integer idHistoria) {
		HistoriaClinica encontrado = traerHistoriaClinica(joven, idHistoria);
		
		if (encontrado != null)
			return encontrado.getEvolucion();
		else 
			return new ArrayList<EvolucionHistoriaClinica>();
	}

	private HistoriaClinica traerHistoriaClinica(Joven joven, Integer idHistoria) {
		Iterator<HistoriaClinica> iter = joven.getTratamientos().iterator();
		HistoriaClinica encontrado = null;
		
		while (encontrado == null && iter.hasNext()) {
			HistoriaClinica historia = (HistoriaClinica) iter.next();
			if (historia.getId().equals(idHistoria))
				encontrado = historia;
		}
		return encontrado;
	}
}

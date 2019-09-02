package snya.reina.servicio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.ObraSocialDAO;
import snya.general.modelo.ObraSocial;
import snya.reina.ReinaException;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.datos.intervencion.MotivoBajaIntervencionDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.movimiento.MotivoMovimientoDAO;
import snya.reina.datos.proceso.TipoDeDetalleDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMomentoProcesalDAO;
import snya.reina.datos.proceso.TipoDeMotivoIntervencionDAO;
import snya.reina.datos.proceso.TipoDeNotaProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeSituacionProcesalDAO;
import snya.reina.datos.referente.TipoDeParentescoDAO;
import snya.reina.modelo.Persona;
import snya.reina.modelo.institucion.CentroDeReferencia;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.IntervencionPenal;
import snya.reina.modelo.intervencion.MotivoBajaIntervencion;
import snya.reina.modelo.intervencion.TipoDeIntervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.proceso.CorteDeProcesoPenal;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.modelo.referente.TipoDeParentesco;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.serviciomodelo.intervencion.GeneradorIntervencion;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;

@Service
public class IntervencionPenalServicioImpl {

	@Autowired
	private MotivoMovimientoDAO motivoMovimientoDAO;
	@Autowired
	private ObraSocialDAO obraSocialDAO;
	@Autowired
	private TipoDeParentescoDAO tipoDeParentescoDAO;
	@Autowired
	private TipoDeMotivoIntervencionDAO tipoDeMotivoIntervencionDAO;
	@Autowired
	private TipoDeMomentoProcesalDAO tipoDeMomentoProcesalDAO;
	@Autowired
	private TipoDeMedidaEnProcesoPenalDAO tipoDeMedidaEnProcesoPenalDAO;
	@Autowired
	private TipoDeDetalleDeMedidaEnProcesoPenalDAO tipoDeDetalleDeMedidaEnProcesoPenalDAO;
	@Autowired
	private MotivoBajaIntervencionDAO motivoBajaIntervencionDAO;
	@Autowired
	private TipoDeSituacionProcesalDAO tipoDeSituacionProcesalDAO;
	@Autowired
	private TipoDeNotaProcesoPenalDAO tipoDeNotaProcesoPenalDAO;	
	@Autowired
	private GuiaDeRecursos recursero;
	@Autowired
	private RecursoREINADAO recursoDAO;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private GeneradorIntervencion generadorIntervencion;
	

	public IntervencionPenal crearIntervencionPenal(Joven joven, Date fecha,
			Integer idCentroDeReferencia, String observacion,
			MedidaEnProcesoPenal medidaImpuesta) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		CentroDeReferencia centroDeReferencia = recursero.traerCentroDeReferenciaPorId(idCentroDeReferencia);
		Recurso recurso = recursoDAO.traerPor(centroDeReferencia);

		Intervencion interv = generadorIntervencion.crearIntervencion(joven, TipoDeIntervencion.ID_MONITOREO_TERRITORIAL);
		IntervencionPenal intervencion = joven.agregarIntervencionPenal(interv, fecha, recurso, observacion, medidaImpuesta, escritor);
		return intervencion;
	}
		
	@Transactional
	public void guardarIntervencion(Integer idJoven, 
			Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			Integer idObraSocial, Integer idParentesco,
			
			Integer idJuzgado, Integer idFiscalia, Boolean esDefensorOficial,
			Integer idDefensoria, Integer idDefensorOficial, String abogado, String iPP,
			String nroCarpeta, String nroCausa, Integer idCaratula, Boolean gradoTentativa, 
			String observacionCaratula, Date fechaImposicion,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida,
			Date fechaIntervencion, Integer idCentroDeReferencia,
			String observacionIntervencion) throws ReinaException {
		AdministradorDeProceso administrador = traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		// agrego familiar al joven
		this.agregarFamiliar(persona, vive, convive, referente, tutor, observacion,
				idObraSocial, idParentesco, joven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.generarIntervencionMonitoreo(joven);
		
		// preparo el proceso		
		CorteDeProcesoPenal proceso = administrador.agregarProcesoPenal(joven, idJuzgado, idFiscalia,
				esDefensorOficial, idDefensoria, idDefensorOficial, abogado, iPP, nroCarpeta,
				nroCausa, idCaratula, gradoTentativa, observacionCaratula, fechaImposicion,
				idMomentoProcesal, idTipoMedida, idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);

		this.agregarIntervencionPenal(intervencion, fechaIntervencion, idCentroDeReferencia, observacionIntervencion,
				joven, proceso.getMedidaImpuesta());
				
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional
	public void guardarIntervencion(Integer idJoven, 
			Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			Integer idObraSocial, Integer idParentesco,
			Integer idProceso, Date fechaImposicion, Integer idOrganoJudicial,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida,
			Date fechaIntervencion, Integer idCentroDeReferencia,
			String observacionIntervencion) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		// agrego familiar al joven
		this.agregarFamiliar(persona, vive, convive, referente, tutor, observacion,
				idObraSocial, idParentesco, joven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.generarIntervencionMonitoreo(joven);
		
		// preparo el proceso		
		ProcesoPenal procesoPenal = administrador.traerProceso(joven, idProceso);
		CorteDeProcesoPenal proceso = administrador.agregarMomentoProcesal(joven, procesoPenal, fechaImposicion, 
				idOrganoJudicial, idMomentoProcesal, idTipoMedida, idsMedidasImpuestas,
				observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);

		this.agregarIntervencionPenal(intervencion, fechaIntervencion, idCentroDeReferencia, observacionIntervencion,
				joven, proceso.getMedidaImpuesta());	

		jovenDAO.actualizar(joven);		
	}
	
	@Transactional	
	public void guardarIntervencion(Integer idJoven, 
			Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			Integer idObraSocial, Integer idParentesco,
			Integer idProceso, Date fechaIntervencion, Integer idCentroDeReferencia,
			String observacionIntervencion) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);

		// agrego familiar al joven
		this.agregarFamiliar(persona, vive, convive, referente, tutor, observacion,
				idObraSocial, idParentesco, joven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.generarIntervencionMonitoreo(joven);
		
		// preparo el proceso
		ProcesoPenal procesoPenal = administrador.traerProceso(joven, idProceso);				
		
		// agrego la intervencion penal
		this.agregarIntervencionPenal(intervencion, fechaIntervencion, idCentroDeReferencia, observacionIntervencion,
				joven, procesoPenal.getMedidaImpuesta());
		
		IntervencionPenal interv = joven.traerUltimaIntervencion();
		Permanencia per = interv.getPermanencia();
		per.setIngreso(per.getId());
		jovenDAO.actualizar(joven);
	}

	@Transactional	
	public void cesarIntervencionPenal(Integer idJoven, Integer idIntervencion,
			Date fechaBaja, Integer idMotivoBaja, String observacionCese) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		MotivoBajaIntervencion motivoDeBaja = motivoBajaIntervencionDAO
				.traerPorId(idMotivoBaja);
		Joven joven = jovenDAO.traerPorId(idJoven);
		IntervencionPenal intervencion = this.traerIntervencion(joven, idIntervencion);

		joven.cesarIntervencionPenal(intervencion, fechaBaja, motivoDeBaja, observacionCese, escritor);
		
		jovenDAO.actualizar(intervencion.getJoven());
	}

	@Transactional	
	public void eliminarIntervencionPenal(Integer idJoven, Integer idIntervencion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		IntervencionPenal intervencion = this.traerIntervencion(joven, idIntervencion);

		joven.eliminarIntervencionPenal(intervencion);
		
		jovenDAO.actualizar(intervencion.getJoven());
	}
	
	public List<MotivoBajaIntervencion> traerMotivoBajaIntervencionTodos() {
		return motivoBajaIntervencionDAO.traerTodos();
	}
	

	private void agregarIntervencionPenal(Intervencion intervencion, Date fechaIntervencion,
			Integer idCentroDeReferencia, String observacionIntervencion,
			Joven joven, MedidaEnProcesoPenal medidaImpuesta) {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		CentroDeReferencia centroDeReferencia = recursero.traerCentroDeReferenciaPorId(idCentroDeReferencia);
		Recurso recurso = recursoDAO.traerPor(centroDeReferencia);

		// Revisar que no haya otra intervencion penal abierta
		// Que de las cerradas no se crucen fechas
		
		joven.agregarIntervencionPenal(intervencion, fechaIntervencion, recurso, observacionIntervencion, medidaImpuesta, escritor);
	}
	
	private void agregarFamiliar(Persona persona, Boolean vive,
			Boolean convive, Boolean referente, Boolean tutor,
			String observacion, Integer idObraSocial, Integer idParentesco,
			Joven joven) throws ReinaException {
		if (persona != null) {
			ObraSocial obraSocial = obraSocialDAO.traerPorId(idObraSocial);
			TipoDeParentesco parentesco = tipoDeParentescoDAO.traerPorId(idParentesco);
		
			joven.agregarFamiliar(persona, vive, convive, referente, tutor, 
					observacion, obraSocial, parentesco);
		}
	}
	
	private IntervencionPenal traerIntervencion(Joven joven, Integer id) {
		java.util.Iterator<IntervencionPenal> iter = joven
				.getIntervencionesSRPJ().iterator();
		IntervencionPenal interv = null;

		while (iter.hasNext()) {
			IntervencionPenal i = iter.next();
			if (i.getId().equals(id))
				interv = i;
		}

		return interv;
	}
	
	private AdministradorDeProceso traerAdministrador() {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		return new AdministradorDeProceso(motivoMovimientoDAO, tipoDeMotivoIntervencionDAO, 
				tipoDeMomentoProcesalDAO, tipoDeMedidaEnProcesoPenalDAO, tipoDeDetalleDeMedidaEnProcesoPenalDAO, tipoDeSituacionProcesalDAO, tipoDeNotaProcesoPenalDAO, recursero, escritor);
	}
}

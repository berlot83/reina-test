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
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.movimiento.EstadoMovimientoDAO;
import snya.reina.datos.movimiento.MotivoMovimientoDAO;
import snya.reina.datos.movimiento.TipoDeMovimientoDAO;
import snya.reina.datos.referente.TipoDeParentescoDAO;
import snya.reina.modelo.Persona;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.TipoDeIntervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.EstadoMovimiento;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.movimiento.TipoDeMotivoMovimiento;
import snya.reina.modelo.movimiento.TipoDeMovimiento;
import snya.reina.modelo.proceso.CorteDeProcesoPenal;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.modelo.referente.TipoDeParentesco;
import snya.reina.serviciomodelo.intervencion.GeneradorIntervencion;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.movimiento.GeneradorDeMovimiento;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;
import snya.reina.serviciomodelo.proceso.AdministradorDeProcesoFactory;

@Service
public class MovimientoServicioImpl {

	@Autowired
	private MotivoMovimientoDAO motivoMovimientoDAO;
	@Autowired
	private EstadoMovimientoDAO estadoMovimientoDAO;
	@Autowired
	private TipoDeMovimientoDAO tipoMovimientoDAO;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private RecursoREINADAO recursoDAO;

	@Autowired
	private ObraSocialDAO obraSocialDAO;
	@Autowired
	private TipoDeParentescoDAO tipoDeParentescoDAO;
	
	@Autowired
	private AdministradorDeProcesoFactory adminProcesoFactory;
	@Autowired
	private GeneradorDeMovimiento generador;
	@Autowired
	private GeneradorIntervencion generadorIntervencion;
	
	
	// INGRESO
	public Movimiento crearIngreso(Joven joven, MedidaEnProcesoPenal medidaImpuesta,
			Date fechaIngreso, Integer idInstitucion, String observacion) throws ReinaException {		
		Intervencion interv = generadorIntervencion.crearIntervencion(joven, TipoDeIntervencion.ID_CONTEXTO_ENCIERRO);
		Movimiento movimiento = generador.agregarIngreso(interv, fechaIngreso, idInstitucion, observacion, joven, medidaImpuesta);
		
		return movimiento;
	}

	@Transactional
	public void guardarIngreso(Integer idJoven, 
			Date fechaIngreso, Integer idInstitucion, String observacion,
			Integer idOrganoJudicial, Integer idFiscalia, Boolean esDefensorOficial, Integer idDefensoria,
			Integer idDefensorOficial, String abogado, String iPP, String nroCarpeta, String nroCausa,
			Integer idCaratula, Boolean gradoTentativa, String observacionCaratula, Date fechaImposicion,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida,
			Persona persona1, Integer idObraSocial1, Integer idParentesco1,
			Persona persona2, Integer idObraSocial2, Integer idParentesco2) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		// agrego informacion familiar
		this.agregarFamiliar(persona1, idObraSocial1, idParentesco1, joven);	
		this.agregarFamiliar(persona2, idObraSocial2, idParentesco2, joven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.generarIntervencionContextoEncierro(joven);
		
		// preparo el tema proceso
		CorteDeProcesoPenal proceso = this.agregarProcesoPenal(joven, idOrganoJudicial,
				idFiscalia, esDefensorOficial, idDefensoria, idDefensorOficial, abogado, iPP,
				nroCarpeta, nroCausa, idCaratula, gradoTentativa, observacionCaratula,
				fechaImposicion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);

		// genera el movimiento y lo que conlleva
		this.agregarIngreso(intervencion, fechaIngreso, idInstitucion, observacion, 
				joven, proceso.getMedidaImpuesta());
				
		jovenDAO.actualizar(joven);
		
		Permanencia per = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		per.setIngreso(per.getId());
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarIngreso(Integer idJoven, 
			Date fechaIngreso, Integer idInstitucion, String observacion,
			Integer idProceso, Date fechaImposicion, Integer idOrganoJudicial,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida,
			Persona persona1, Integer idObraSocial1, Integer idParentesco1,
			Persona persona2, Integer idObraSocial2, Integer idParentesco2) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		// agrego informacion familiar
		this.agregarFamiliar(persona1, idObraSocial1, idParentesco1, joven);	
		this.agregarFamiliar(persona2, idObraSocial2, idParentesco2, joven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.generarIntervencionContextoEncierro(joven);
		
		// preparo el tema proceso
		CorteDeProcesoPenal proceso = this.agregarMomentoPenal(joven, idProceso,
				idOrganoJudicial, fechaImposicion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);

		this.agregarIngreso(intervencion, fechaIngreso, idInstitucion, observacion, 
				joven, proceso.getMedidaImpuesta());
				
		jovenDAO.actualizar(joven);		
		
		Permanencia per = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		per.setIngreso(per.getId());
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void guardarIngreso(Integer idJoven, 
			Date fechaIngreso, Integer idInstitucion, String observacion,
			Integer idProceso,
			Persona persona1, Integer idObraSocial1, Integer idParentesco1,
			Persona persona2, Integer idObraSocial2, Integer idParentesco2) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		// agrego informacion familiar
		this.agregarFamiliar(persona1, idObraSocial1, idParentesco1, joven);	
		this.agregarFamiliar(persona2, idObraSocial2, idParentesco2, joven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.generarIntervencionContextoEncierro(joven);
		
		// preparo el tema proceso
		AdministradorDeProceso administrador = this.traerAdministrador();
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		this.agregarIngreso(intervencion, fechaIngreso, idInstitucion, observacion, 
				joven, proceso.getMedidaImpuesta());
				
		jovenDAO.actualizar(joven);
		
		Permanencia per = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		per.setIngreso(per.getId());
		jovenDAO.actualizar(joven);
	}

	
	// TRASLADO
	@Transactional
	public void guardarTraslado(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, Integer idDestino, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		this.agregarTraslado(intervencion, joven, idMotivoMovimiento, null, fecha, tipoDestino, idDestino,
				observacion);
				
		jovenDAO.actualizar(joven);		
	}

	@Transactional
	public void guardarTraslado(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, Integer idDestino, String observacion, Integer idProceso) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		AdministradorDeProceso administrador = this.traerAdministrador();
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		this.agregarTraslado(intervencion, joven, idMotivoMovimiento, proceso.getMedidaImpuesta(), fecha, tipoDestino, idDestino,
				observacion);
				
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional
	public void guardarTraslado(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, Integer idDestino, String observacion, 
			Integer idProceso, Date fechaSitucion,
			Integer idOrganoJudicial, Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		CorteDeProcesoPenal proceso = this.agregarMomentoPenal(joven, idProceso,
				idOrganoJudicial, fechaSitucion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);

		this.agregarTraslado(intervencion, joven, idMotivoMovimiento, proceso.getMedidaImpuesta(), fecha, tipoDestino, idDestino,
				observacion);
				
		jovenDAO.actualizar(joven);
	}

	
	// NOTIFICACIONES	
	@Transactional
	public void guardarNotificacion(Integer idJoven, Integer idMotivoMovimiento,
			Date fecha, Integer tipoDestino, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		this.agregarMovimientoNotificacion(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, null, observacion);
				
		jovenDAO.actualizar(joven);				
	}

	@Transactional
	public void guardarNotificacion(Integer idJoven, Integer idMotivoMovimiento,
			Date fecha, Integer tipoDestino, String observacion, Integer idProceso) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		AdministradorDeProceso administrador = this.traerAdministrador();
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		this.agregarMovimientoNotificacion(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, proceso.getMedidaImpuesta(), observacion);
				
		jovenDAO.actualizar(joven);				
	}
	
	@Transactional
	public void guardarNotificacion(Integer idJoven, Integer idMotivoMovimiento,
			Date fecha, Integer tipoDestino, String observacion,
			Integer idProceso, Date fechaSituacion, Integer idOrganoJudicial, 
			Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fFinDeMedida) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		CorteDeProcesoPenal proceso = this.agregarMomentoPenal(joven, idProceso,
				idOrganoJudicial, fechaSituacion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida,
				horasDeMedida, diasDeMedida, mesesDeMedida, fFinDeMedida);

		this.agregarMovimientoNotificacion(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, proceso.getMedidaImpuesta(), observacion);
				
		jovenDAO.actualizar(joven);
	}

	
	// EGRESOS	
	@Transactional
	public void guardarEgreso(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		this.agregarMovimientoEgreso(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, null,
				observacion);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarEgreso(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, String observacion, Integer idProceso) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		AdministradorDeProceso administrador = this.traerAdministrador();
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		this.agregarMovimientoEgreso(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, proceso.getMedidaImpuesta(),
				observacion);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarEgreso(Integer idJoven, Integer idMotivoMovimiento,
			Date fecha, Integer tipoDestino, String observacion,
			Integer idProceso, Date fechaSituacion, Integer idOrganoJudicial,
			Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		CorteDeProcesoPenal proceso = this.agregarMomentoPenal(joven, idProceso,
				idOrganoJudicial, fechaSituacion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		
		this.agregarMovimientoEgreso(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, proceso.getMedidaImpuesta(),
				observacion);
		
		jovenDAO.actualizar(joven);
	}

	
	// BAJAS
	@Transactional
	public void guardarBaja(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		this.agregarMovimientoBaja(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, null,
				observacion);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarBaja(Integer idJoven, Integer idMotivoMovimiento, Date fecha,
			Integer tipoDestino, String observacion, Integer idProceso) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		AdministradorDeProceso administrador = this.traerAdministrador();
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		this.agregarMovimientoBaja(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, proceso.getMedidaImpuesta(),
				observacion);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarBaja(Integer idJoven, Integer idMotivoMovimiento,
			Date fecha, Integer tipoDestino, String observacion,
			Integer idProceso, Date fechaSituacion, Integer idOrganoJudicial,
			Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
		
		CorteDeProcesoPenal proceso = this.agregarMomentoPenal(joven, idProceso,
				idOrganoJudicial, fechaSituacion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		
		this.agregarMovimientoBaja(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, proceso.getMedidaImpuesta(),
				observacion);
		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void confirmarMovimiento(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Movimiento movimiento = this.traerMovimiento(joven, id);
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
		
		//Genera la intervencion
		Intervencion intervencion = generadorIntervencion.recuperarIntervencionContextoEncierro(joven);
				
		ProcesoPenal proceso = (movimiento.getMedidaImpuesta() != null) 
				? movimiento.getMedidaImpuesta().getProceso() 
				: joven.traerUltimaPermanenciaDeCumplimientoAbierta().getProceso();
		
		generador.confirmarMovimiento(intervencion, joven, movimiento, estado, proceso);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void cancelarMovimiento(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Movimiento movimiento = this.traerMovimiento(joven, id);
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CANCELADO);
		
		joven.cancelarMovimiento(movimiento, estado);
		
		jovenDAO.actualizar(joven);
	}
		
	@Transactional
	public void actualizarMovimiento(Integer idJoven, Integer idMovimiento,
			Integer idProceso, Integer idMedida, String Observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Movimiento movimiento = this.traerMovimiento(joven, idMovimiento);

		MedidaEnProcesoPenal medida = null;
		if (idProceso != null && idMedida != null) {
			ProcesoPenal proceso = this.traerProceso(joven, idProceso);
			medida =  proceso.traerMedidaPorId(idMedida);
		}
		generador.actualizarMovimiento(movimiento, medida, Observacion);
		
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional
	public void eliminarMovimiento(Integer idJoven, Integer id) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		Joven joven = jovenDAO.traerPorId(idJoven);
		Movimiento movimiento = this.traerMovimiento(joven, id);
		
		joven.eliminarMovimiento(movimiento);
		joven.modificarPresenteEnRecurso(movimiento, escritor);
		
		jovenDAO.actualizar(joven);		
	}
	
	public List<Recurso> traerDestinosPorTipoMovimiento(int idTipoDeMovimiento) {
		TipoDeMovimiento tipo = tipoMovimientoDAO.traerPorId(idTipoDeMovimiento);
		
		return tipo.traerDestinos(recursoDAO);
	}

	public List<TipoDeMovimiento> traerPosiblesTraslados(Joven joven) {	
		return joven.traerUltimoMovimientoCorrecto().traerPosiblesTraslados();
	}

	public List<TipoDeMovimiento> traerPosiblesNotificaciones(Joven joven) {
		return joven.traerUltimoMovimientoCorrecto().traerPosiblesNotificaciones();
	}
	
	public List<TipoDeMovimiento> traerPosiblesEgresos(Joven joven) {
		return joven.traerUltimoMovimientoCorrecto().traerPosiblesEgresos();
	}

	public List<TipoDeMovimiento> traerPosiblesBajas(Joven joven) {
		return joven.traerUltimoMovimientoCorrecto().traerPosiblesBajas();
	}
	
	public List<TipoDeMotivoMovimiento> traerMotivosTodos() {
		return motivoMovimientoDAO.traerTodos();
	}

	
	private void agregarFamiliar(Persona persona, Integer idObraSocial, Integer idParentesco, Joven joven) throws ReinaException {
		if (persona != null) {
			ObraSocial obraSocial = obraSocialDAO.traerPorId(idObraSocial);
			TipoDeParentesco parentesco = tipoDeParentescoDAO.traerPorId(idParentesco);
		
			joven.agregarFamiliar(persona, null, null, null, null, null, obraSocial, parentesco);
		}
	}
	
	private CorteDeProcesoPenal agregarProcesoPenal(Joven joven, Integer idOrganoJudicial,
			Integer idFiscalia, Boolean esDefensorOficial,
			Integer idDefensoria, Integer idDefensorOficial, String abogado, String iPP,
			String nroCarpeta, String nroCausa, Integer idCaratula, Boolean gradoTentativa,
			String observacionCaratula, Date fechaImposicion,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		
		CorteDeProcesoPenal proceso = administrador.agregarProcesoPenal(joven, idOrganoJudicial, idFiscalia,
				esDefensorOficial, idDefensoria, idDefensorOficial, abogado, iPP, nroCarpeta,
				nroCausa, idCaratula, gradoTentativa, observacionCaratula, fechaImposicion,
				idMomentoProcesal, idTipoMedida, idsMedidasImpuestas, observacionMedida,
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);

		return proceso;
	}

	private CorteDeProcesoPenal agregarMomentoPenal(Joven joven, Integer idProceso, Integer idOrganoJudicial,
			Date fechaImposicion, Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		ProcesoPenal procesoPenal = administrador.traerProceso(joven, idProceso);
		
		return administrador.agregarMomentoProcesal(joven, procesoPenal, fechaImposicion, idOrganoJudicial,
				idMomentoProcesal, idTipoMedida, idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
	}

	private void agregarIngreso(Intervencion intervencion, Date fechaIngreso, Integer idInstitucion,
			String observacion, Joven joven, MedidaEnProcesoPenal medidaImpuesta) throws ReinaException {
		
		generador.agregarIngreso(intervencion, fechaIngreso, idInstitucion, observacion, joven, medidaImpuesta);
	}

	private void agregarTraslado(Intervencion intervencion, Joven joven, Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta, Date fecha,
			Integer tipoDestino, Integer idDestino, String observacion)
			throws ReinaException {
		
		generador.agregarTraslado(intervencion, joven, idMotivoMovimiento, medidaImpuesta, fecha, tipoDestino, idDestino, observacion);
	}
	
	private void agregarMovimientoNotificacion(Intervencion intervencion, Joven joven, Date fecha, Integer idTipoDestino,
			Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta, String observacion) throws ReinaException {
		
		generador.agregarMovimientoNotificacion(intervencion, joven, fecha, idTipoDestino, idMotivoMovimiento, medidaImpuesta, observacion);		
	}
	
	private void agregarMovimientoEgreso(Intervencion intervencion, Joven joven, Date fecha, Integer tipoDestino, Integer idMotivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion) throws ReinaException {
		
		generador.agregarMovimientoEgreso(intervencion, joven, fecha, tipoDestino, idMotivoMovimiento, medidaImpuesta, observacion);
	}

	private void agregarMovimientoBaja(Intervencion intervencion, Joven joven, Date fecha,
			Integer tipoDestino, Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta,
			String observacion) throws ReinaException {
		
		generador.agregarMovimientoBaja(intervencion, joven, fecha,	tipoDestino, idMotivoMovimiento, medidaImpuesta, observacion);
	}
	
	
	private Movimiento traerMovimiento(Joven joven, Integer id) {
		java.util.Iterator<Movimiento> iter = joven.getMovimientos().iterator();
		Movimiento movimiento = null;
		
		while (iter.hasNext()) {
			Movimiento mov = iter.next();
			if (mov.getId().equals(id))
				movimiento = mov;
		}
		
		return movimiento;
	}
	
	
	private ProcesoPenal traerProceso(Joven joven, Integer id) {
		java.util.Iterator<ProcesoPenal> iter = joven.getProcesos().iterator();
		ProcesoPenal proc = null;
		
		while (iter.hasNext()) {
			ProcesoPenal p = iter.next();
			if (p.getId().equals(id))
				proc = p;
		}
		
		return proc;
	}
		
	private AdministradorDeProceso traerAdministrador() {
		return adminProcesoFactory.crearAdministradorDeProceso();
	}
}

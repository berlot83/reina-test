package snya.reina.serviciomodelo.movimiento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import snya.reina.ReinaException;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.datos.movimiento.EstadoMovimientoDAO;
import snya.reina.datos.movimiento.MotivoMovimientoDAO;
import snya.reina.datos.movimiento.TipoDeMovimientoDAO;
import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.RegistroAdmision;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.EstadoMovimiento;
import snya.reina.modelo.movimiento.ManejoEventoMovimiento;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.MovimientoBaja;
import snya.reina.modelo.movimiento.MovimientoEgreso;
import snya.reina.modelo.movimiento.MovimientoIngreso;
import snya.reina.modelo.movimiento.MovimientoInternacion;
import snya.reina.modelo.movimiento.MovimientoNotificacion;
import snya.reina.modelo.movimiento.MovimientoRetornoInternacion;
import snya.reina.modelo.movimiento.MovimientoTraslado;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.movimiento.TipoDeMotivoMovimiento;
import snya.reina.modelo.movimiento.TipoDeMovimiento;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.intervencion.GeneradorIntervencion;

@Component
public class GeneradorDeMovimiento {
	
	@Autowired
	private ManejoEventoMovimiento manejoEvento;
	
	@Autowired
	private TipoDeMovimientoDAO tipoDeMovimientoDAO;
	@Autowired
	private EstadoMovimientoDAO estadoMovimientoDAO;
	@Autowired
	private MotivoMovimientoDAO motivoMovimientoDAO;
	@Autowired
	private GeneradorIntervencion generadorIntervencion;
	@Autowired
	private RecursoREINADAO recursoDAO;
	
	/* *********************************************
	 * INTERVENCION EN ADMISION DE CAD
	********************************************* */
	//
	// METODOS MOV. CAD INGRESO
	//
	public Movimiento agregarIngresoAdmision(Intervencion intervencion, Date fechaIngreso, Integer idInstitucion,
			String observacion, Joven joven, MedidaEnProcesoPenal medidaImpuesta) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		TipoDeMotivoMovimiento movitoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_ORDENJUDICIAL);
		
		AmbitoEjecucion ambito = recursoDAO.traerPorId(idInstitucion);
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(TipoDeMovimiento.ID_INGRESO_CAD);

		Movimiento movimiento = this.agregarMovimientoIngreso(intervencion, joven, fechaIngreso, tipo, ambito,
				movitoMovimiento, medidaImpuesta, observacion, estado);
		joven.agregarPresenteEnRecurso(intervencion, movimiento, medidaImpuesta.getProceso(),
				movimiento.traerAmbitoEjecucionOrigen(),
				movimiento.traerAmbitoEjecucionDestino(),
				escritor);
		
		return movimiento;
	}
	
	//
	// METODOS MOV. CAD MOVIMIENTO
	//
	public boolean puedeMoverseDeUnaAdmision(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();
		
		if (movimiento == null)
			return false;

		if ((movimiento != null) &&
			joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesMovimientosAdmision().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
			
		return true;
	}
	
	public void agregarMovimientoAdmision(Integer idTipoMovimiento, Date fechaMovimiento, String observacion,
			Integer idDestino, Joven joven, RegistroAdmision admision, Intervencion intervencion)
			throws ReinaException {
		Movimiento movimiento = null;
		TipoDeMovimiento tipoMovimiento = tipoDeMovimientoDAO.traerPorId(idTipoMovimiento);
		
		if (tipoMovimiento.getFuncionalidadMovimiento() == TipoDeMovimiento.FUNCIIONALIDAD_NOTIFICACION) {
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
			TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);	
			
			movimiento = this.agregarMovimientoNotificacion(intervencion, joven, fechaMovimiento, tipoMovimiento, motivoMovimiento, admision.getProceso().getMedidaImpuesta(), observacion, estado);
		} else if (tipoMovimiento.getFuncionalidadMovimiento() == TipoDeMovimiento.FUNCIIONALIDAD_TRASLADO){
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
			TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);
			AmbitoEjecucion ambito = recursoDAO.traerPorId(idDestino);
			
			movimiento = this.agregarMovimientoTraslado(intervencion, joven, fechaMovimiento, tipoMovimiento, ambito, motivoMovimiento, admision.getProceso().getMedidaImpuesta(), observacion, estado);			
		} else if (tipoMovimiento.getFuncionalidadMovimiento() == TipoDeMovimiento.FUNCIIONALIDAD_INTERNACION){
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
			TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);
			AmbitoEjecucion ambito = recursoDAO.traerPorId(idDestino);
			
			movimiento = this.agregarMovimientoInternacion(intervencion, joven, fechaMovimiento, tipoMovimiento, ambito, motivoMovimiento, admision.getProceso().getMedidaImpuesta(), observacion, estado);			
		} else if (tipoMovimiento.getFuncionalidadMovimiento() == TipoDeMovimiento.FUNCIIONALIDAD_RETORNOINTERNACION){
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
			TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);
			AmbitoEjecucion ambito = recursoDAO.traerPorId(admision.getInstitucion().getId());
			
			movimiento = this.agregarMovimientoRetornoInternacion(intervencion, joven, fechaMovimiento, tipoMovimiento, ambito, motivoMovimiento, admision.getProceso().getMedidaImpuesta(), observacion, estado);			
		}
		if (movimiento != null) {
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
			this.confirmarMovimientoRecienCreado(intervencion, joven, movimiento, estado, admision.getProceso());
		} else {
			throw new ReinaException("No se genero correctamente el movimiento");
		}
	}
	
	//
	// METODOS MOV. CAD EGRESO
	//
	public boolean puedeEgresarDesdeUnaAdmision(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();
		
		if (movimiento == null)
			return false;
		
		if ((movimiento != null) &&
				joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesEgresosAdmision().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
			
		return true;
	}
	
	public TipoDeMovimiento agregarEgresoAdmision(Integer idTipoMovimiento, Date fechaMovimiento, String observacion,
			Integer idDestino, Joven joven, RegistroAdmision admision, Intervencion intervencion)
			throws ReinaException {
		Movimiento movimiento = null;
		TipoDeMovimiento tipoMovimiento = tipoDeMovimientoDAO.traerPorId(idTipoMovimiento);
		
		if (tipoMovimiento.getFuncionalidadMovimiento() == TipoDeMovimiento.FUNCIIONALIDAD_TRASLADO){
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
			TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);
			AmbitoEjecucion ambito = recursoDAO.traerPorId(idDestino);
			
			intervencion.setEstaActivo(false);
			Intervencion interv = generadorIntervencion.generarIntervencionContextoEncierro(joven);
			
			movimiento = this.agregarMovimientoTraslado(interv, joven, fechaMovimiento, tipoMovimiento, ambito, motivoMovimiento, admision.getProceso().getMedidaImpuesta(), observacion, estado);			
		} else if (tipoMovimiento.getFuncionalidadMovimiento() == TipoDeMovimiento.FUNCIIONALIDAD_EGRESO){
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
			TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);
			
			intervencion.setEstaActivo(false);
			
			movimiento = this.agregarMovimientoEgreso(intervencion, joven, fechaMovimiento, tipoMovimiento, motivoMovimiento, admision.getProceso().getMedidaImpuesta(), observacion, estado);
		}
		if (movimiento != null) {
			EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
			this.confirmarMovimientoRecienCreado(intervencion, joven, movimiento, estado, admision.getProceso());
			
			admision.setFechaEgreso(fechaMovimiento);
			admision.setMotivoEgreso(tipoMovimiento);
		} else {
			throw new ReinaException("No se genero correctamente el movimiento");
		}
		
		return tipoMovimiento; 
	}
	
	/* *********************************************
	 * INTERVENCION EN CONTEXTO DE ENCIERRO
	********************************************* */
	//
	// METODOS MOV. INGRESOS
	//
	public boolean puedeIngresarAUnAmbitoEjecucion(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();
		if ((movimiento != null) &&
				joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesIngresos().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
		
		return true;
	}
	
	public Movimiento agregarIngreso(Intervencion intervencion, Date fechaIngreso, Integer idInstitucion,
			String observacion, Joven joven, MedidaEnProcesoPenal medidaImpuesta) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		TipoDeMotivoMovimiento movitoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_ORDENJUDICIAL);
		
		AmbitoEjecucion ambito = recursoDAO.traerPorId(idInstitucion);
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(TipoDeMovimiento.ID_INGRESO);

		Movimiento movimiento = this.agregarMovimientoIngreso(intervencion, joven, fechaIngreso, tipo, ambito,
				movitoMovimiento, medidaImpuesta, observacion, estado);
		joven.agregarPresenteEnRecurso(intervencion, movimiento, medidaImpuesta.getProceso(),
				movimiento.traerAmbitoEjecucionOrigen(),
				movimiento.traerAmbitoEjecucionDestino(),
				escritor);
		
		return movimiento;
	}
	
	private Movimiento agregarMovimientoIngreso(Intervencion intervencion, Joven joven, Date fechaIngreso,
			TipoDeMovimiento tipo, AmbitoEjecucion ambito,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fechaIngreso = Calendario.sumarHorario(fechaIngreso, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fechaIngreso))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw  new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( this.jovenEstaEnUnAmbitoDeEjecucion(joven, fechaIngreso) )
			throw  new ReinaException("El joven se encuentra en un ambito de cumplimiento en la fecha indicada.");
						
		MovimientoIngreso movimiento = new MovimientoIngreso(intervencion, fechaIngreso, tipo, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreIngresoAmbito(joven, fechaIngreso, ambito);

		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;
	}
	
	
	//
	// METODOS MOV. TRASLADOS
	//
	public boolean puedeTrasladarseAUnAmbitoEjecucion(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();
		
		if (movimiento == null)
			return false;

		if ((movimiento != null) &&
			joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesTraslados().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
			
		return true;
	}

	public void agregarTraslado(Intervencion intervencion, Joven joven, Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta, Date fecha,
			Integer tipoDestino, Integer idDestino, String observacion)
			throws ReinaException {
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(idMotivoMovimiento);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(tipoDestino);
		AmbitoEjecucion ambito = recursoDAO.traerPorId(idDestino);
		
		this.agregarMovimientoTraslado(intervencion, joven, fecha, tipo, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
	}
	
	private Movimiento agregarMovimientoTraslado(Intervencion intervencion, Joven joven,
			Date fecha, TipoDeMovimiento tipo, AmbitoEjecucion ambito,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fecha))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( !this.jovenEstaEnUnAmbitoDeEjecucion(joven, fecha) )
			throw new ReinaException("El joven no se encuentra en un ambito de cumplimiento en la fecha indicada.");

		if (!motivoMovimiento.esOrdenJudicial())
			medidaImpuesta = null;
		
		AmbitoEjecucion ambitoOrigen = joven.traerUltimaPermanenciaDeCumplimientoAbierta().getAmbitoEjecucion();
		
		MovimientoTraslado movimiento = new MovimientoTraslado(intervencion, fecha, tipo, ambitoOrigen, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
		
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreCambioAmbito(joven, fecha, ambito);
		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;
	}
	
	
	//
	// METODOS MOV. NOTIFICACION
	//	
	public boolean puedeRelizarseUnaNotificacion(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();

		if (movimiento == null)
			return false;

		if ((movimiento != null) &&
			joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesNotificaciones().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
			
		return true;
	}
	
	public void agregarMovimientoNotificacion(Intervencion intervencion, Joven joven, Date fecha, Integer idTipoDestino,
			Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta, String observacion) throws ReinaException {
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.CONFIRMADO);
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(idMotivoMovimiento);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(idTipoDestino);
		
		this.agregarMovimientoNotificacion(intervencion, joven, fecha, tipo, motivoMovimiento, medidaImpuesta, observacion, estado);		
	}
	
	private Movimiento agregarMovimientoNotificacion(Intervencion intervencion, Joven joven,
			Date fecha, TipoDeMovimiento tipo,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fecha))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( !this.jovenEstaEnUnAmbitoDeEjecucion(joven, fecha) )
			throw new ReinaException("El joven no se encuentra en un ambito de cumplimiento en la fecha indicada.");

		if (!motivoMovimiento.esOrdenJudicial())
			medidaImpuesta = null;
		
		Permanencia presente = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		MovimientoNotificacion movimiento = new MovimientoNotificacion(intervencion, fecha, tipo, motivoMovimiento, medidaImpuesta, observacion, estado, presente);
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreNotificacion(joven, movimiento);
			
		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;
	}
		
	
	//
	// METODOS MOV. EGRESOS
	//	
	public boolean puedeEgresarDesdeUnAmbitoEjecucion(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();
		
		if (movimiento == null)
			return false;
		
		if ((movimiento != null) &&
				joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesEgresos().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
			
		return true;
	}
	
	public void agregarMovimientoEgreso(Intervencion intervencion, Joven joven, Date fecha, Integer tipoDestino, Integer idMotivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion) throws ReinaException {
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(idMotivoMovimiento);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(tipoDestino);
		
		this.agregarMovimientoEgreso(intervencion, joven, fecha, tipo, motivoMovimiento, medidaImpuesta, observacion, estado);
	}
	
	private Movimiento agregarMovimientoEgreso(Intervencion intervencion, Joven joven, Date fecha,
			TipoDeMovimiento tipo, TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fecha))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( !this.jovenEstaEnUnAmbitoDeEjecucion(joven, fecha) )
			throw new ReinaException("El joven no se encuentra en un ambito de cumplimiento en la fecha indicada.");

		if (!motivoMovimiento.esOrdenJudicial())
			medidaImpuesta = null;
		
		Permanencia presente = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		AmbitoEjecucion ambitoOrigen = presente.getAmbitoEjecucion();
		
		MovimientoEgreso movimiento = new MovimientoEgreso(intervencion, fecha, tipo, ambitoOrigen, motivoMovimiento, medidaImpuesta, observacion, estado, presente);
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreEgreso(joven, movimiento);
		
		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;
	}


	//
	// METODOS MOV. BAJAS
	//	
	public boolean puedeRelizarseUnaBaja(Joven joven) {
		Movimiento movimiento = joven.traerUltimoMovimiento();

		if (movimiento == null)
			return false;

		if ((movimiento != null) &&
			joven.traerUltimoMovimientoCorrecto().getTipo().traerPosiblesBajas().size() == 0)
			return false;
		
		if ((movimiento != null) && !movimiento.noEstaPendiente())
			return false;
			
		return true;
	}
	
	public void agregarMovimientoBaja(Intervencion intervencion, Joven joven, Date fecha,
			Integer tipoDestino, Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta,
			String observacion) throws ReinaException {
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(idMotivoMovimiento);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(tipoDestino);
		
		this.agregarMovimientoBaja(intervencion, joven, fecha, tipo, motivoMovimiento, medidaImpuesta, observacion, estado);
	}
	
	private Movimiento agregarMovimientoBaja(Intervencion intervencion, Joven joven, Date fecha,
			TipoDeMovimiento tipo, TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fecha))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( !this.jovenEstaEnUnAmbitoDeEjecucion(joven, fecha) )
			throw new ReinaException("El joven no se encuentra en un ambito de cumplimiento en la fecha indicada.");

		if (!motivoMovimiento.esOrdenJudicial())
			medidaImpuesta = null;
				
		Permanencia presente = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		
		MovimientoBaja movimiento = new MovimientoBaja(intervencion, fecha, tipo, motivoMovimiento, medidaImpuesta, observacion, estado, presente);
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreBaja(joven, movimiento);
			
		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;		
	}
	
	//
	// METODOS MOV. INTERNACION
	//
	public void agregarInternacion(Intervencion intervencion, Joven joven, Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta, Date fecha,
			Integer tipoDestino, Integer idDestino, String observacion)
			throws ReinaException {
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(idMotivoMovimiento);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(tipoDestino);
		AmbitoEjecucion ambito = recursoDAO.traerPorId(idDestino);
		
		this.agregarMovimientoInternacion(intervencion, joven, fecha, tipo, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
	}
	
	private Movimiento agregarMovimientoInternacion(Intervencion intervencion, Joven joven,
			Date fecha, TipoDeMovimiento tipo, AmbitoEjecucion ambito,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fecha))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( !this.jovenEstaEnUnAmbitoDeEjecucion(joven, fecha) )
			throw new ReinaException("El joven no se encuentra en un ambito de cumplimiento en la fecha indicada.");

		if (!motivoMovimiento.esOrdenJudicial())
			medidaImpuesta = null;
		
		AmbitoEjecucion ambitoOrigen = joven.traerUltimaPermanenciaDeCumplimientoAbierta().getAmbitoEjecucion();
		
		MovimientoInternacion movimiento = new MovimientoInternacion(intervencion, fecha, tipo, ambitoOrigen, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
		
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreCambioAmbito(joven, fecha, ambito);
		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;
	}
	
	//
	// METODOS MOV. RETORNO INTERNACION
	//
	public void agregarRetornoInternacion(Intervencion intervencion, Joven joven, Integer idMotivoMovimiento, MedidaEnProcesoPenal medidaImpuesta, Date fecha,
			Integer tipoDestino, String observacion)
			throws ReinaException {
		EstadoMovimiento estado = estadoMovimientoDAO.traerPorId(EstadoMovimiento.PENDIENTE);
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(idMotivoMovimiento);
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(tipoDestino);
		AmbitoEjecucion ambito = joven.traerUltimaPermanenciaDeCumplimientoAbierta().getAmbitoEjecucion();
		
		this.agregarMovimientoRetornoInternacion(intervencion, joven, fecha, tipo, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
	}
	
	private Movimiento agregarMovimientoRetornoInternacion(Intervencion intervencion, Joven joven,
			Date fecha, TipoDeMovimiento tipo, AmbitoEjecucion ambito,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) throws ReinaException {
		Movimiento ultimo = joven.traerUltimoMovimiento();
		if (!tipo.getRequiereHora()) {
			if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFecha());
		}
		if (ultimo != null && ultimo.getFecha().after(fecha))
			throw new ReinaException("El movimiento tiene que ser posterior a los existentes.");
		
		if ( !this.siElMotivoEsOrdenJudicialExisteMedida(motivoMovimiento, medidaImpuesta) )
			throw new ReinaException("Si el motivos es " + motivoMovimiento.getNombre() + " debe constar la medida impuesta.");
		
		if ( !this.jovenEstaEnUnAmbitoDeEjecucion(joven, fecha) )
			throw new ReinaException("El joven no se encuentra en un ambito de cumplimiento en la fecha indicada.");

		if (!motivoMovimiento.esOrdenJudicial())
			medidaImpuesta = null;
		
		AmbitoEjecucion ambitoOrigen = joven.traerUltimaPermanenciaDeCumplimientoAbierta().getAmbitoEjecucionInternacion();
		
		MovimientoRetornoInternacion movimiento = new MovimientoRetornoInternacion(intervencion, fecha, tipo, ambitoOrigen, ambito, motivoMovimiento, medidaImpuesta, observacion, estado);
		
		manejoEvento.notificarAIntervencionesPenalesAbiertasSobreCambioAmbito(joven, fecha, ambito);
		joven.getMovimientos().add(movimiento);
		movimiento.setJoven(joven);
		
		return movimiento;
	}
	
	//
	// METODOS ASOCIACION PROCESO
	//	
	public void acomodarMovimientosDelProceso(Joven joven, ProcesoPenal proceso) {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		
		List<Movimiento> movimientos = traerMovimientosOrdenados(joven);
		Iterator<Movimiento> itm = movimientos.iterator();
		while (itm.hasNext()) {
			Movimiento movimiento = (Movimiento) itm.next();
			
			movimiento.acomodarMovimientoSegun(proceso, escritor);
		}
	}

	public void acomodarMovimientosDelProcesoAPartirDelCierre(Joven joven, ProcesoPenal proceso, ProcesoPenal procesoSeleccionado, MedidaEnProcesoPenal medida) {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		boolean acomodar = false;
		MedidaEnProcesoPenal medidaCierre = proceso.getMedidaImpuesta();
		Date fecha = medidaCierre.getFechaMedida();
		
		Movimiento anterior = null;
		Movimiento siguiente = null;
		
		// Buscar el movimiento a partir de donde se modifica el proceso por el cual esta presente
		List<Movimiento> movimientos = traerMovimientosOrdenados(joven);
		Iterator<Movimiento> itm = movimientos.iterator();
		while (!acomodar && itm.hasNext()) {
			Movimiento movimiento = (Movimiento) itm.next();
			
			acomodar = (
					movimiento.getMedidaImpuesta() != null 
					&& movimiento.getMedidaImpuesta().getProceso().getId().equals(proceso.getId())					
					&& Calendario.diaSinHora( movimiento.getFecha() ).getTime() >= Calendario.diaSinHora( fecha ).getTime()
				);
			if (!acomodar) anterior = movimiento;
			if (acomodar) siguiente = movimiento;
		}
	
		if(anterior != null)
			anterior.acomodarProcesoDePermanencia(procesoSeleccionado, medida, fecha, escritor);

		// una vez encontrado el movimiento todos los siguientes de deben ir adecuando
		if ( acomodar ) {
			actualizarDatosDelMovimiento(proceso, procesoSeleccionado, medida, escritor, siguiente);
		
			while (itm.hasNext()) {
				Movimiento movimiento = (Movimiento) itm.next();
				
				actualizarDatosDelMovimiento(proceso, procesoSeleccionado,
						medida, escritor, movimiento);
			}
		}
	}

	private void actualizarDatosDelMovimiento(ProcesoPenal proceso,
			ProcesoPenal procesoSeleccionado, MedidaEnProcesoPenal medida,
			EscritorNarrativoDeHistoria escritor, Movimiento movimiento) {
		if ( movimiento.getPermanencia().getProceso().getId().equals( proceso.getId() ) ) {		
			if ( movimiento.getMedidaImpuesta() != null )
				movimiento.setMedidaImpuesta(medida);
			
			if ( medida.vigenteAl( Calendario.diaSinHora( movimiento.getFecha() ) ) ) {
				movimiento.actualizarMovimientoSegunMedida(escritor);
			} else {						
				movimiento.acomodarMovimientoSegun(procesoSeleccionado, escritor);
			}
		}
	}
	
	//
	// METODOS VARIOS
	//
	private void confirmarMovimientoRecienCreado(Intervencion intervencion, Joven joven, Movimiento movimiento, EstadoMovimiento estado, ProcesoPenal proceso) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		
		movimiento.confirmar(estado);
		joven.agregarPresenteEnRecurso(intervencion, movimiento, proceso, movimiento.traerAmbitoEjecucionOrigen(), movimiento.traerAmbitoEjecucionDestino(), escritor);
		
		this.eventoConfirmarMovimiento(joven, movimiento);
	}
	
	public void confirmarMovimiento(Intervencion intervencion, Joven joven, Movimiento movimiento, EstadoMovimiento estado, ProcesoPenal proceso) throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		
		joven.confirmarMovimiento(movimiento, estado);
		joven.agregarPresenteEnRecurso(intervencion, movimiento, proceso, movimiento.traerAmbitoEjecucionOrigen(), movimiento.traerAmbitoEjecucionDestino(), escritor);
		
		this.eventoConfirmarMovimiento(joven, movimiento);
	}
	
	public void actualizarMovimiento(Movimiento movimiento, MedidaEnProcesoPenal medida, String observacion) {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		
		movimiento.setObservacion(observacion);
		movimiento.setMedidaImpuesta(medida);
		
		movimiento.actualizarMovimientoSegunMedida(escritor);
	}
	
	
	
	
	public List<Recurso> traerDestinosPorTipoMovimiento(int idTipoDeMovimiento) {
		TipoDeMovimiento tipo = tipoDeMovimientoDAO.traerPorId(idTipoDeMovimiento);		
		return tipo.traerDestinos(recursoDAO);
	}	
	
	private void eventoConfirmarMovimiento(Joven joven, Movimiento movimiento) {
		movimiento.eventoConfirmar(manejoEvento);		
	}
	
	private List<Movimiento> traerMovimientosOrdenados(Joven joven) {
		Comparator<Movimiento> movimiento_orden = new Comparator<Movimiento>() {
			public int compare(Movimiento m1, Movimiento m2) {
				return m1.getFecha().compareTo(m2.getFecha());
			}
		};
		List<Movimiento> movimientos = new ArrayList<Movimiento>( joven.getMovimientos() );
		Collections.sort(movimientos, movimiento_orden);
		return movimientos;
	}
	
	
	private boolean siElMotivoEsOrdenJudicialExisteMedida( TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta) {
		return !motivoMovimiento.esOrdenJudicial() || (motivoMovimiento.esOrdenJudicial() && medidaImpuesta != null);
	}

	private boolean jovenEstaEnUnAmbitoDeEjecucion(Joven joven, Date fechaIngreso) {
		return joven.estaPresenteEnAmbitoDeEjecucion(fechaIngreso);
	}
}

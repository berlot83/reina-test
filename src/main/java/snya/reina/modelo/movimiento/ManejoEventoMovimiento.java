package snya.reina.modelo.movimiento;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import snya.reina.datos.educacion.TipoDeEstadoCapacitacionJovenDAO;
import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.educacion.CapacitacionJoven;
import snya.reina.modelo.educacion.TipoDeEstadoCapacitacionJoven;
import snya.reina.modelo.joven.Joven;

@Component
public class ManejoEventoMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6845199242175264334L;
	@Autowired
	private TipoDeEstadoCapacitacionJovenDAO estadoCapacitacionDAO;
	
	public void eventoConfirmarBaja(MovimientoBaja movimiento) {
		
	}

	public void eventoConfirmarEgreso(MovimientoEgreso movimiento) {
		this.interrumpirFormacionLaboral(movimiento.getJoven(), movimiento.getFecha());
	}

	public void eventoConfirmarIngreso(MovimientoIngreso movimiento) {
		
	}

	public void eventoConfirmarNotificacion(MovimientoNotificacion movimiento) {
		
	}

	public void eventoConfirmarTraslado(MovimientoTraslado movimiento) {
		this.interrumpirFormacionLaboral(movimiento.getJoven(), movimiento.getFecha());
	}
	
	public void eventoConfirmarInternacion(MovimientoInternacion movimientoInternacion) {
		
	}
	
	public void eventoConfirmarRetornoInternacion(MovimientoRetornoInternacion movimientoRetornoInternacion) {
	
	}
	
	public void notificarAIntervencionesPenalesAbiertasSobreIngresoAmbito(Joven joven, Date fechaIngreso, AmbitoEjecucion ambito) {

	}

	public void notificarAIntervencionesPenalesAbiertasSobreCambioAmbito(
			Joven joven,Date fecha, AmbitoEjecucion ambito) {

	}
	
	public void notificarAIntervencionesPenalesAbiertasSobreNotificacion(
			Joven joven,MovimientoNotificacion movimiento) {

	}
	
	public void notificarAIntervencionesPenalesAbiertasSobreEgreso(
			Joven joven,MovimientoEgreso movimiento) {

	}
	
	public void notificarAIntervencionesPenalesAbiertasSobreBaja(
			Joven joven,MovimientoBaja movimiento) {
		
	}
	
	
	private void interrumpirFormacionLaboral(Joven joven, Date fecha) {
		TipoDeEstadoCapacitacionJoven estado = estadoCapacitacionDAO.traerPorId(TipoDeEstadoCapacitacionJoven.INTERRUMPIDO);
		
		for (CapacitacionJoven capac : joven.getCapacitaciones()) {
			if (capac.getCapacitacion().getEsFormacionLaboral() && capac.getEstado().getId().equals(TipoDeEstadoCapacitacionJoven.CURSANDO)) {
				capac.setEstado(estado);
				capac.setFechaFin(fecha);
			}
		}
	}
}

package snya.reina.modelo.movimiento;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.resultado.ResultadoPresente;

@Entity
@DiscriminatorValue("3")
@Audited
public class MovimientoNotificacion extends Movimiento {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8089693068953857085L;

	public MovimientoNotificacion(){
		
	}
	
	public MovimientoNotificacion(Intervencion intervencion, Date fecha, TipoDeMovimiento tipo,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado, Permanencia presente) {
		this.setIntervencion(intervencion);
		this.setPermanencia(presente);
		this.setFecha(fecha);
		this.setTipo(tipo);
		this.setMotivo(motivoMovimiento);
		this.setMedidaImpuesta(medidaImpuesta);
		this.setObservacion(observacion);
		this.setEstado(estado);			
	}

	@Override
	public Date traerFecha() {
		return this.getFecha();
	}

	@Override
	public AmbitoEjecucion traerAmbitoEjecucionOrigen() {
		return null;
	}

	@Override
	public AmbitoEjecucion traerAmbitoEjecucionDestino() {
		return null;
	}

	@Override
	public ResultadoPresente asentarHistorial(Joven joven, Intervencion intervencion, ProcesoPenal proceso,
			AmbitoEjecucion ambitoEjecucionOrigen,
			AmbitoEjecucion ambitoEjecucionDestino, EscritorNarrativoDeHistoria escritor) {
		return new ResultadoPresente();
	}

	@Override
	public void modificarHistorial(Joven joven, EscritorNarrativoDeHistoria escritor) {
	}
	
	@Override
	public void actualizarHistorial(EscritorNarrativoDeHistoria escritor) {		
	}
	
	@Override
	public void actualizarHistorialDelProceso(EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {		
	}
	
	@Override
	public void eventoConfirmar(ManejoEventoMovimiento manejo) {
		manejo.eventoConfirmarNotificacion(this);
	}
}

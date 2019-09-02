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
@DiscriminatorValue("5")
@Audited
public class MovimientoBaja extends Movimiento {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2224593060248626296L;

	public MovimientoBaja(){
		
	}
	
	public MovimientoBaja(Intervencion intervencion, Date fecha, TipoDeMovimiento tipo,
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
		this.getPermanencia().setFechaFin(this.getFecha());
		this.getPermanencia().asentarBaja(this, proceso, ambitoEjecucionOrigen, escritor);		
				
		intervencion.setEstaActivo(false);
		
		return new ResultadoPresente();
	}


	@Override
	public void modificarHistorial(Joven joven, EscritorNarrativoDeHistoria escritor){
		if (this.getEstado().estaConfirmado()) {
			
			Permanencia presente = joven.traerUltimaPermanenciaDeCumplimiento();
			if (presente != null) {
				presente.setFechaFin(null);
				presente.quitarBaja(this, escritor);
			}
			
			this.getIntervencion().setEstaActivo(true);
		}
	}

	@Override
	public void actualizarHistorial(EscritorNarrativoDeHistoria escritor) {
		this.getPermanencia().actualizarBaja(this, escritor);		
	}

	@Override
	public void actualizarHistorialDelProceso(EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.getPermanencia().actualizarBajaDelProceso(this, escritor, proceso);		
	}
	
	@Override
	public void eventoConfirmar(ManejoEventoMovimiento manejo) {
		manejo.eventoConfirmarBaja(this);
	}
}

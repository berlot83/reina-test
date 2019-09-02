package snya.reina.modelo.movimiento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.resultado.ResultadoPresente;

@Entity
@DiscriminatorValue("4")
@Audited
public class MovimientoEgreso extends Movimiento {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4558762724679633098L;
	@Column(name = "EsAmbitoInstitucionOrigen")
	private String esInstitucionOrigen = "I";	
	@ManyToOne(targetEntity=snya.reina.modelo.recurso.Recurso.class)
	@JoinColumn(name = "IdAmbitoDeEjecucionOrigen")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private AmbitoEjecucion ambitoOrigen;

	public MovimientoEgreso() {

	}

	public MovimientoEgreso(Intervencion intervencion, Date fecha, TipoDeMovimiento tipo,
			AmbitoEjecucion ambitoOrigen,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado, Permanencia presente) {
		this.setIntervencion(intervencion);
		this.setPermanencia(presente);
		this.setFecha(fecha);
		this.setTipo(tipo);
		this.setAmbitoOrigen(ambitoOrigen);
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
		return this.getAmbitoOrigen();
	}

	@Override
	public AmbitoEjecucion traerAmbitoEjecucionDestino() {
		return null;
	}

	@Override
	public ResultadoPresente asentarHistorial(Joven joven, Intervencion intervencion,
			ProcesoPenal proceso, AmbitoEjecucion ambitoEjecucionOrigen,
			AmbitoEjecucion ambitoEjecucionDestino,
			EscritorNarrativoDeHistoria escritor) {
		this.getPermanencia().setFechaFin(this.getFecha());
		this.getPermanencia().asentarEgreso(this, proceso,
				ambitoEjecucionOrigen, escritor);

		intervencion.setEstaActivo(false);
		
		return new ResultadoPresente();
	}

	@Override
	public void modificarHistorial(Joven joven,
			EscritorNarrativoDeHistoria escritor) {
		if (this.getEstado().estaConfirmado()) {
			Permanencia presente = joven.traerUltimaPermanenciaDeCumplimiento();
			if (presente != null) {
				presente.setFechaFin(null);
				presente.quitarEgreso(this, escritor);
			}
						
			this.getIntervencion().setEstaActivo(true);
		}
	}

	@Override
	public void actualizarHistorial(EscritorNarrativoDeHistoria escritor) {
		this.getPermanencia().actualizarEgreso(this, escritor);
	}

	@Override
	public void actualizarHistorialDelProceso(
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.getPermanencia().actualizarEgresoDelProceso(this, escritor,
				proceso);
	}

	public AmbitoEjecucion getAmbitoOrigen() {
		return ambitoOrigen;
	}

	public void setAmbitoOrigen(AmbitoEjecucion ambitoOrigen) {
		this.ambitoOrigen = ambitoOrigen;
	}
	
	@Override
	public void eventoConfirmar(ManejoEventoMovimiento manejo) {
		manejo.eventoConfirmarEgreso(this);
	}
}

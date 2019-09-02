package snya.reina.modelo.movimiento;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@DiscriminatorValue("7")
@Audited
public class MovimientoRetornoInternacion extends Movimiento {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8683797244244231413L;
	@Column(name = "EsAmbitoInstitucionOrigen")
	private String esInstitucionOrigen = "I";	
	@ManyToOne(targetEntity=snya.reina.modelo.recurso.Recurso.class)
	@JoinColumn(name = "IdAmbitoDeEjecucionOrigen")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private AmbitoEjecucion ambitoOrigen;
	
	@Column(name="EsAmbitoInstitucionDestino")
	private String esInstitucionDestino = "I";	
	@ManyToOne(targetEntity=snya.reina.modelo.recurso.Recurso.class)
    @JoinColumn(name="IdAmbitoDeEjecucionDestino")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private AmbitoEjecucion ambitoDestino;	

	
	public MovimientoRetornoInternacion(){
		
	}

	public MovimientoRetornoInternacion(Intervencion intervencion, Date fecha, TipoDeMovimiento tipo,
			AmbitoEjecucion ambitoOrigen, AmbitoEjecucion ambitoDestino,
			TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) {
		this.setIntervencion(intervencion);
		this.setFecha(fecha);
		this.setTipo(tipo);
		this.setAmbitoOrigen(ambitoOrigen);
		this.setAmbitoDestino(ambitoDestino);
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
		return this.getAmbitoDestino();
	}

	@Override
	public ResultadoPresente asentarHistorial(Joven joven, Intervencion intervencion, ProcesoPenal proceso, AmbitoEjecucion ambitoEjecucionOrigen, AmbitoEjecucion ambitoEjecucionDestino, EscritorNarrativoDeHistoria escritor) {
		MedidaEnProcesoPenal medida = (this.getMedidaImpuesta() != null) ? this.getMedidaImpuesta() : proceso.traerMedidaImpuestaAl(this.getFecha());
				
		// Cierre
		Permanencia presenteAnterior = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		presenteAnterior.setFechaFin(this.getFecha());
						
		// Apertura		
		Permanencia presente = presenteAnterior.armarContinuidadPorRetornoInternacion(this, proceso, medida, escritor);
		this.setPermanencia(presente);
		
		return new ResultadoPresente(presente);
	}
	
	@Override
	public void modificarHistorial(Joven joven, EscritorNarrativoDeHistoria escritor){
		if (this.getEstado().estaConfirmado()) {
			Integer grupo = this.getPermanencia().getGrupo();
			List<Permanencia> lista =  new ArrayList<Permanencia>(joven.getPermanencias());
			
			for (Permanencia perma : lista) {
				if (grupo.equals(perma.getGrupo())) {
					joven.quitarPermanencia(perma);
				}
			}
			
			Permanencia presenteAnterior = joven.traerUltimaPermanenciaDeCumplimiento();
			if(presenteAnterior != null) {
				presenteAnterior.setFechaFin(null);
				presenteAnterior.quitarRetornoInternacion(this, escritor);
			}
		}
	}
	
	@Override
	public void actualizarHistorial(EscritorNarrativoDeHistoria escritor) {
		this.getPermanencia().actualizarRetornoInternacion(this, escritor);
		
		Permanencia presenteAnterior = this.getJoven().traerUltimaPermanenciaDeCumplimientoAntesDe(this.getPermanencia());
		if(presenteAnterior != null) presenteAnterior.actualizarCierrePorRetornoInternacion(this, escritor);
	}
	
	
	@Override
	public void actualizarHistorialDelProceso(EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.getPermanencia().actualizarRetornoInternacionDelProceso(this, escritor, proceso);
		
		Permanencia presenteAnterior = this.getJoven().traerUltimaPermanenciaDeCumplimientoAntesDe(this.getPermanencia());
		if(presenteAnterior != null) presenteAnterior.actualizarCierrePorRetornoInternacion(this, escritor);	
	}
	
	public AmbitoEjecucion getAmbitoOrigen() {
		return ambitoOrigen;
	}

	public void setAmbitoOrigen(AmbitoEjecucion ambitoOrigen) {
		this.ambitoOrigen = ambitoOrigen;
	}

	public AmbitoEjecucion getAmbitoDestino() {
		return ambitoDestino;
	}

	public void setAmbitoDestino(AmbitoEjecucion ambitoDestino) {
		this.ambitoDestino = ambitoDestino;
	}
	
	@Override
	public void eventoConfirmar(ManejoEventoMovimiento manejo) {
		manejo.eventoConfirmarRetornoInternacion(this);
	}
}

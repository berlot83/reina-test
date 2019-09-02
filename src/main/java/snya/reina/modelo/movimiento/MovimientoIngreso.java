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
@DiscriminatorValue("1")
@Audited
public class MovimientoIngreso extends Movimiento {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8854663161132699000L;
	/* === Variables de instancia === */
	@Column(name="EsAmbitoInstitucionDestino")
	private String esInstitucionDestino = "I";	
	@ManyToOne(targetEntity=snya.reina.modelo.recurso.Recurso.class)
    @JoinColumn(name="IdAmbitoDeEjecucionDestino")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private AmbitoEjecucion ambitoEjecucion;	

	
	/* === Constructores === */
	public MovimientoIngreso(){
		
	}
	
	public MovimientoIngreso(Intervencion intervencion, Date fechaIngreso, TipoDeMovimiento tipo,
			AmbitoEjecucion ambito, TipoDeMotivoMovimiento motivoMovimiento,
			MedidaEnProcesoPenal medidaImpuesta, String observacion,
			EstadoMovimiento estado) {
		this.setIntervencion(intervencion);
		this.setFecha(fechaIngreso);
		this.setTipo(tipo);
		this.setAmbitoEjecucion(ambito);
		this.setMotivo(motivoMovimiento);
		this.setMedidaImpuesta(medidaImpuesta);
		this.setObservacion(observacion);
		this.setEstado(estado);
	}

	
	/* === Metodos === */
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
		return this.getAmbitoEjecucion();
	}
	
	@Override
	public ResultadoPresente asentarHistorial(Joven joven, Intervencion intervencion, ProcesoPenal proceso, AmbitoEjecucion ambitoEjecucionOrigen, AmbitoEjecucion ambitoEjecucionDestino, EscritorNarrativoDeHistoria escritor) {		
		Permanencia presente = new Permanencia(intervencion, joven,  this.getFecha(), ambitoEjecucionDestino, proceso, this.getMedidaImpuesta());
		presente.setIngreso(null);
		
		presente.asentarIngreso(this, escritor);
		this.setPermanencia(presente);
		
		return new ResultadoPresente(presente);
	}
	
	@Override
	public void modificarHistorial(Joven joven, EscritorNarrativoDeHistoria escritor){
		if (this.getEstado().estaConfirmado()) {
			Integer grupo = this.getPermanencia().getGrupo();
			List<Permanencia> lista = new ArrayList<Permanencia>(joven.getPermanencias());
			for (Permanencia perma : lista) {
				if (grupo.equals(perma.getGrupo()))
					joven.getPermanencias().remove(perma);	
			}
			
			this.getIntervencion().setJoven(null);
			joven.getIntervenciones().remove(this.getIntervencion());
		}
	}
	
	@Override
	public void actualizarHistorial(EscritorNarrativoDeHistoria escritor) {
		this.getPermanencia().actualizarIngreso(this, escritor);		
	}
	
	@Override
	public void actualizarHistorialDelProceso(EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.getPermanencia().actualizarIngresoDelProceso(this, escritor, proceso);		
	}
	
	/*=== Propiedades ===*/	
	public AmbitoEjecucion getAmbitoEjecucion() {
		return ambitoEjecucion;
	}

	public void setAmbitoEjecucion(AmbitoEjecucion ambito) {
		this.ambitoEjecucion = ambito;
	}
	
	
	@Override
	public void eventoConfirmar(ManejoEventoMovimiento manejo) {
		manejo.eventoConfirmarIngreso(this);
	}
}

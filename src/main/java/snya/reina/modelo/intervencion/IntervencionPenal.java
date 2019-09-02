package snya.reina.modelo.intervencion;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.reina.modelo.Calendario;
import snya.reina.modelo.institucion.CentroDeReferencia;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;

@Entity
@Table(name="Reina_Interv_IntervencionPenal", catalog="SistemasSNYA")
@Audited
public class IntervencionPenal implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2065290077105400144L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdIntervencionPenal")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@ManyToOne
	@JoinColumn(name="IdMedidaEnProcesoPenal")
	private MedidaEnProcesoPenal medida;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdCentroDeReferencia")
	private CentroDeReferencia centroDeReferencia;
	
	@Column(name="FechaIntervencion", nullable = false )
	private java.util.Date fechaIntervencion;
	
	@Column(name="FechaBajaIntervencion")
	private java.util.Date fechaBaja;
	
	@ManyToOne
	@JoinColumn(name="IdMotivoBajaIntervencion")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private MotivoBajaIntervencion motivoBajaIntervencion;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="IdIntervencion")
	private Intervencion intervencion;
	
	@ManyToOne
	@JoinColumn(name="IdPermanencia")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Permanencia permanencia;
	
	/* === Constructores === */		
	public IntervencionPenal(){
	}
	
	public IntervencionPenal(Intervencion intervencion,
			Date fechaIntervencion, CentroDeReferencia centroDeReferencia,
			MedidaEnProcesoPenal medidaImpuesta, String observacion) {
		this.intervencion = intervencion;
		this.setFechaIntervencion(fechaIntervencion);
		this.setCentroDeReferencia(centroDeReferencia);
		this.setMedida(medidaImpuesta);
		this.setObservacion(observacion);
	}

	
	/* === Metodos === */	
	public String traerFechaIntervencion() {
		return Calendario.formatearFecha(this.getFechaIntervencion());
	}

	public String traerFechaBaja() {
		return (this.getFechaBaja() == null) 
				? ""
				: Calendario.formatearFecha(this.getFechaBaja());
	}
	
	public boolean estaAbierta() {
		return this.getMotivoBajaIntervencion() == null;
	}
	
	public void actualizarHistorial(EscritorNarrativoDeHistoria escritor) {
		getPermanencia().actualizarInscripcion(this, escritor);
	}
	

	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public MedidaEnProcesoPenal getMedida() {
		return medida;
	}

	public void setMedida(MedidaEnProcesoPenal medida) {
		this.medida = medida;
	}

	public CentroDeReferencia getCentroDeReferencia() {
		return centroDeReferencia;
	}

	public void setCentroDeReferencia(CentroDeReferencia centroDeReferencia) {
		this.centroDeReferencia = centroDeReferencia;
	}

	public java.util.Date getFechaIntervencion() {
		return fechaIntervencion;
	}

	public void setFechaIntervencion(java.util.Date fechaIntervencion) {
		this.fechaIntervencion = fechaIntervencion;
	}

	public java.util.Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(java.util.Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public MotivoBajaIntervencion getMotivoBajaIntervencion() {
		return motivoBajaIntervencion;
	}

	public void setMotivoBajaIntervencion(MotivoBajaIntervencion baja) {
		this.motivoBajaIntervencion = baja;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	public void setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}
	
	public Permanencia getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(Permanencia permanencia) {
		this.permanencia = permanencia;
	}
}
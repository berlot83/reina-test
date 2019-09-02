package snya.reina.modelo.beneficio;

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

import snya.general.modelo.ObraSocial;
import snya.general.modelo.TipoDeDiscapacidad;

@Entity
@Table(name="Reina_CoberturaObraSocial", catalog="SistemasSNYA")
@Audited
public class CoberturaObraSocial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3562929475578059285L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdCoberturaObraSocial")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdObraSocial")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ObraSocial obraSocial;
	
	@Column(name="NroAfiliado")
	private String nroAfiliado;
	
	@ManyToOne
	@JoinColumn(name="IdEstadoObraSocial")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private EstadoObraSocial estado; 
	
	@Column(name="FechaVencimientoCarnet")
	private java.util.Date fechaVencimientoCarnet;
	
	@Column(name="ObservacionAfiliacion")
	private String observacionAfiliacion;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeDiscapacidad")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeDiscapacidad tipoDeDiscapacidad;
	
	@Column(name="FechaDiscapacidad")
	private java.util.Date fechaDiscapacidad;
	
	@Column(name="Porcentaje")
	private Integer porcentaje;
	
	@Column(name="ObservacionDiscapacidad")
	private String observacionDiscapacidad;
	
	@Column(name="CertificadoDiscapacidad")
	private Boolean certificadoDiscapacidad;
	
	
	/* === Constructores === */
	public CoberturaObraSocial() {
		
	}
	
	public CoberturaObraSocial(ObraSocial obraSocial, String numeroCarnet, Date fVencimientoCarnet, String observacionObraSocial, EstadoObraSocial estado) {
		this.obraSocial = obraSocial;
		this.nroAfiliado = numeroCarnet;
		this.fechaVencimientoCarnet = fVencimientoCarnet;
		this.observacionAfiliacion = observacionObraSocial;
		this.estado = estado;
		
	}

	public CoberturaObraSocial(EstadoObraSocial estado, String observacionObraSocial ) {
		this.observacionAfiliacion = observacionObraSocial;
		this.estado = estado;
	}
	
	public CoberturaObraSocial(TipoDeDiscapacidad tipoDeDiscapacidad, Integer porcentajeDiscapacidad, 
			Boolean certificadoDiscapacidad, Date fechaDiscapacidad, String observacionDiscapacidad) {
		this.tipoDeDiscapacidad = tipoDeDiscapacidad;
		this.porcentaje = porcentajeDiscapacidad;
		this.certificadoDiscapacidad = certificadoDiscapacidad;
		this.fechaDiscapacidad = fechaDiscapacidad;
		this.observacionDiscapacidad = observacionDiscapacidad;
	}

	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public ObraSocial getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(ObraSocial value) {
		obraSocial = value;
	}

	public String getNroAfiliado() {
		return nroAfiliado;
	}

	public void setNroAfiliado(String value) {
		nroAfiliado = value;
	}

	
	public Boolean getCertificadoDiscapacidad() {
		return certificadoDiscapacidad;
	}

	
	public EstadoObraSocial getEstado() {
		return estado;
	}

	public void setEstado(EstadoObraSocial estado) {
		this.estado = estado;
	}

	public void setCertificadoDiscapacidad(Boolean certificadoDiscapacidad) {
		this.certificadoDiscapacidad = certificadoDiscapacidad;
	}

	public java.util.Date getFechaVencimientoCarnet() {
		return fechaVencimientoCarnet;
	}

	public void setFechaVencimientoCarnet(java.util.Date value) {
		fechaVencimientoCarnet = value;
	}

	public String getObservacionAfiliacion() {
		return observacionAfiliacion;
	}

	public void setObservacionAfiliacion(String observacionAfiliacion) {
		this.observacionAfiliacion = observacionAfiliacion;
	}

	public TipoDeDiscapacidad getTipoDeDiscapacidad() {
		return tipoDeDiscapacidad;
	}

	public void setTipoDeDiscapacidad(TipoDeDiscapacidad tipoDeDiscapacidad) {
		this.tipoDeDiscapacidad = tipoDeDiscapacidad;
	}

	public java.util.Date getFechaDiscapacidad() {
		return fechaDiscapacidad;
	}

	public void setFechaDiscapacidad(java.util.Date fechaDiscapacidad) {
		this.fechaDiscapacidad = fechaDiscapacidad;
	}

	public Integer getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Integer porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getObservacionDiscapacidad() {
		return observacionDiscapacidad;
	}

	public void setObservacionDiscapacidad(String observacionDiscapacidad) {
		this.observacionDiscapacidad = observacionDiscapacidad;
	}
}
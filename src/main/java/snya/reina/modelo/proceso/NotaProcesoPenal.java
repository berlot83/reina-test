package snya.reina.modelo.proceso;

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

@Entity
@Table(name="Reina_NotaProcesoPenal", catalog="SistemasSNYA")
@Audited
public class NotaProcesoPenal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055155885048432829L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdNotaProcesoPenal")
	private Integer id;
	
	@Column(name="Fecha")
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="IdProcesoPenal")
	private ProcesoPenal proceso;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeNotaProcesoPenal")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeNotaProcesoPenal tipo;
	
	@Column(name="Observacion")
	private String observacion;
	
	@Column(name="IdReferencia")
	private Integer idReferencia;


	public NotaProcesoPenal() {
		
	}
	
	public NotaProcesoPenal(Date fecha, TipoDeNotaProcesoPenal tipo, String observacion) {
		this.fecha = fecha;
		this.tipo = tipo;
		this.observacion = observacion;
	}

	
	
	public String getFechaTexto() {
		return Calendario.formatearFecha(this.getFecha());
	}
	
	public boolean esDeCierre() {
		return this.getTipo().esDeCierre();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.util.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public Integer getIdReferencia() {
		return idReferencia;
	}

	public void setIdReferencia(Integer idReferencia) {
		this.idReferencia = idReferencia;
	}

	public ProcesoPenal getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoPenal proceso) {
		this.proceso = proceso;
	}

	public TipoDeNotaProcesoPenal getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeNotaProcesoPenal tipo) {
		this.tipo = tipo;
	}
}

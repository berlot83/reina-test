package snya.reina.modelo.educacion;

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

import snya.reina.modelo.joven.Joven;

@Entity
@Table(name="Reina_CapacitacionJoven", catalog="SistemasSNYA")
@Audited
public class CapacitacionJoven implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5433563780438442335L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdCapacitacionJoven")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@ManyToOne
	@JoinColumn(name="IdCapacitacion")
	private Capacitacion capacitacion;
	
	@ManyToOne
	@JoinColumn(name="IdDictado")
	private Dictado dictado;
	
	@Column(name="FechaInicio")
	private Date fechaInicio;
	
	@Column(name="FechaFin")
	private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeEstadoCapacitacionJoven")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeEstadoCapacitacionJoven estado;
	
	@Column(name="FechaEntregaCertificado")
	private Date fechaEntregaCertificado;
	
	@Column(name="Observacion")
	private String observacion;
	
	
	public CapacitacionJoven() { }
	
	public CapacitacionJoven(Joven joven, Capacitacion capacitacion, Dictado dictado, Date fInicio, Date fFin, TipoDeEstadoCapacitacionJoven estado, String observacion) {
		this.joven = joven;
		this.capacitacion = capacitacion;
		this.dictado = dictado;
		this.fechaInicio = fInicio;
		this.fechaFin = fFin;
		this.estado = estado;
		this.observacion = observacion;
	}

	
	public boolean estaVigente() {
		Integer id = this.getEstado().getId();

		return id.equals(TipoDeEstadoCapacitacionJoven.CURSANDO) ||
			id.equals(TipoDeEstadoCapacitacionJoven.FINALIZADO) ||
			id.equals(TipoDeEstadoCapacitacionJoven.APROBADO);
	}
	
	
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

	public Capacitacion getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(Capacitacion capacitacion) {
		this.capacitacion = capacitacion;
	}

	public Dictado getDictado() {
		return dictado;
	}

	public void setDictado(Dictado dictado) {
		this.dictado = dictado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public TipoDeEstadoCapacitacionJoven getEstado() {
		return estado;
	}

	public void setEstado(TipoDeEstadoCapacitacionJoven estado) {
		this.estado = estado;
	}

	public Date getFechaEntregaCertificado() {
		return fechaEntregaCertificado;
	}

	public void setFechaEntregaCertificado(Date fechaEntregaCertificado) {
		this.fechaEntregaCertificado = fechaEntregaCertificado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}

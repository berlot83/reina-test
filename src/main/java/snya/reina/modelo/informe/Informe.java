package snya.reina.modelo.informe;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;

@Entity
@Table(name="Reina_Informe_Informe", catalog="SistemasSNYA")
@Audited
public class Informe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 943721918408837738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdInforme")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeInforme")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeInforme tipo;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@ManyToOne
	@JoinColumn(name="IdInstitucion")
	private Institucion institucion;
	
	@Column(name="FechaInforme")
	private Date fechaInforme;
	
	@Column(name="Observacion")
	private String observacion;
	
	@Column(name="Autores")
	private String autores;
	
	@Enumerated(EnumType.STRING)
	@Column(name="Estado")
	private TipoDeEstadoInformeEnum estado;
	
	@Column(name="Archivo")
	private Boolean archivo;
	
	@ManyToOne
	@JoinColumn(name="IdIntervencion")
	private Intervencion intervencion;
	
	public Informe() {
		
	}
	
	public Informe(Intervencion intervencion, Date fecha, TipoDeInforme tipo, String observacion,
			Institucion institucion, String autores) {
		this.intervencion = intervencion;
		this.fechaInforme = fecha;
		this.tipo = tipo;
		this.observacion = observacion;
		this.institucion = institucion;
		this.autores = autores;
		this.estado = TipoDeEstadoInformeEnum.BORRADOR;
		this.archivo = false;
	}

	public Boolean estaImpreso() {
		return this.estado == TipoDeEstadoInformeEnum.IMPRESO;
	}
	
	public String getDetalleEstado(){
		if(this.getEstado() == TipoDeEstadoInformeEnum.BORRADOR)
			return "Borrador";
		if(this.getEstado() == TipoDeEstadoInformeEnum.EDICION)
			return "En Edición";
		if(this.getEstado() == TipoDeEstadoInformeEnum.IMPRESO)
			return "Impreso";
		
		return "";
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoDeInforme getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeInforme tipo) {
		this.tipo = tipo;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Date getFechaInforme() {
		return fechaInforme;
	}

	public void setFechaInforme(Date fechaInforme) {
		this.fechaInforme = fechaInforme;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public TipoDeEstadoInformeEnum getEstado() {
		return estado;
	}

	public void setEstado(TipoDeEstadoInformeEnum estado) {
		this.estado = estado;
	}
	
	public Boolean getArchivo() {
		return archivo;
	}

	public void setArchivo(Boolean archivo) {
		this.archivo = archivo;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	public void setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}
}

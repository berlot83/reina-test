package snya.reina.modelo.intervencion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.joven.Joven;

@Entity
@Table(name="Reina_Lineamiento", catalog="SistemasSNYA")
@Audited
public class Lineamiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2860400602752642534L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdLineamiento")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="IdInstitucion")
	private Institucion institucion;
	
	
	public Lineamiento() {
		
	}
	
	public Lineamiento(Joven joven, String observacion, Institucion institucion) {
		this.joven = joven;
		this.observacion = observacion;
		this.institucion = institucion;
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
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
}

package snya.reina.modelo.intervencion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reina_Interv_MotivoBajaIntervencion", catalog="SistemasSNYA")
public class MotivoBajaIntervencion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7917325430210651009L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMotivoBajaIntervencion")
	private Integer id;
	
	@Column(name="MotivoBajaIntervencion")
	private String nombre;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;

	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String value) {
		nombre = value;
	}

	public boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean value) {
		estaActivo = value;
	}

	public boolean getPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(boolean value) {
		porDefecto = value;
	}
}
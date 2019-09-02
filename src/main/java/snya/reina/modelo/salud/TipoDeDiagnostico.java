package snya.reina.modelo.salud;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reina_TipoDeDiagnostico", catalog="SistemasSNYA")
public class TipoDeDiagnostico implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3101209484373246888L;
	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeDiagnostico")
	private Integer id;
	@Column(name="Diagnostico")
	private String nombre;
	@Column(name="CodigoCIE")
	private String codigoCIE;
	@Column(name="CodigoDSM")
	private String codigoDSM;
	@Column(name="EstaActivo")
	private Boolean estaActivo;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoCIE() {
		return codigoCIE;
	}

	public void setCodigoCIE(String codigoCIE) {
		this.codigoCIE = codigoCIE;
	}

	public String getCodigoDSM() {
		return codigoDSM;
	}

	public void setCodigoDSM(String codigoDSM) {
		this.codigoDSM = codigoDSM;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

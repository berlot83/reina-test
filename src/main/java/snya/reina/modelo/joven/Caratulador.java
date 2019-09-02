package snya.reina.modelo.joven;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import snya.reina.modelo.institucion.Institucion;

@Entity
@Table(name="Reina_Caratulador", catalog="SistemasSNYA")
public class Caratulador implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4619014334619228484L;


	public static final String CODIGO_SRPJ = "SRPJ";
	
	public static final Integer ID_PROMOCION = 2;
	
	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdCaratulador")
	private Integer id;
	
	@Column(name="Caracteristica",nullable=false)
	private String caracteristica;
	
	@ManyToOne
	@JoinColumn(name="IdInstitucion")
	private Institucion institucion;
	
	@Column(name="Expresion")
	private String expresion;
	
	@Column(name="Mascara")
	private String mascara;


	
	public String getNombre() {
		return this.getInstitucion().getNombreCorto();
	}
	
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
}

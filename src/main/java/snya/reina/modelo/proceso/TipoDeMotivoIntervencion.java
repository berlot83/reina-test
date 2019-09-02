package snya.reina.modelo.proceso;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="Reina_TipoDeMotivoIntervencion", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeMotivoIntervencion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3993419537370020784L;

	/* === Variables de instancia === */	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeMotivoIntervencion")
	private Integer id;
	
	@Column(name="TipoDeMotivoIntervencion")
	private String nombre;
	
	//private String tipo;
	
	@Column(name="Articulo")
	private Integer articulo;
	
	@Column(name="Seccion")
	private String seccion;
	
	@Column(name="Inciso")
	private String inciso;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;

	/* === Constructores === */
	public TipoDeMotivoIntervencion() {
	}

	
	public String getDescripcion() {
		return this.getNombre() 
				+ " (" + ((this.getArticulo() != 0) ? "Art." + this.getArticulo() : "")
				+ ( !(this.getSeccion().equals("") ) ? " " + this.getSeccion() + " " : "")
				+ ( !(this.getInciso().equals("") ) ? "inc. " + this.getInciso() : "")
				+ " )";
	}
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}
/*
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String value) {
		tipo = value;
	}
*/
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String value) {
		nombre = value;
	}

	public Integer getArticulo() {
		return articulo;
	}

	public void setArticulo(Integer articulo) {
		this.articulo = articulo;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getInciso() {
		return inciso;
	}

	public void setInciso(String inciso) {
		this.inciso = inciso;
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
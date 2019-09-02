package snya.reina.modelo.referente;

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
@Table(name="Reina_TipoDeParentesco", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeParentesco implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256671328505237903L;
	public static final Integer ID_PADRE = 1;
	public static final Integer ID_MADRE = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeParentesco")
	private Integer id;
	
	@Column(name="TipoDeParentesco")
	private String tipoDeParentesco;
	
	@Column(name="EsUnico")
	private boolean unico;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return tipoDeParentesco;
	}

	public void setNombre(String tipoDeParentesco) {
		this.tipoDeParentesco = tipoDeParentesco;
	}

	public boolean isUnico() {
		return unico;
	}

	public void setUnico(boolean unico) {
		this.unico = unico;
	}

	public boolean isEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public boolean isPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(boolean porDefecto) {
		this.porDefecto = porDefecto;
	}
}
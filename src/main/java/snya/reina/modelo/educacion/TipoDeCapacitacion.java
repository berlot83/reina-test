package snya.reina.modelo.educacion;

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
@Table(name="Reina_TipoDeCapacitacion", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeCapacitacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4134954015958647276L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeCapacitacion")
	private Integer id;
	@Column(name="TipoDeCapacitacion")
	private String nombre;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	@Column(name="PorDefecto")
	private Boolean porDefecto;

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

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Boolean getPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(Boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

}

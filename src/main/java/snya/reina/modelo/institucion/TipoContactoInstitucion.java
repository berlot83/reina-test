package snya.reina.modelo.institucion;

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
@Table(name="Entidades_TipoContactoInstitucion", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoContactoInstitucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4914732800562584044L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoContactoInstitucion")
	private Integer id;
	@Column(name="TipoContactoInstitucion")
	private String nombre;

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
}

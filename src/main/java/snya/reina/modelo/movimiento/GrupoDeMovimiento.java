package snya.reina.modelo.movimiento;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "Reina_Mov_GrupoDeMovimiento", catalog = "SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class GrupoDeMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6555640813613302378L;
	
	@Id
	@Column(name = "IdGrupoDeMovimiento")
	private Integer id;
	@Column(name = "GrupoDeMovimiento")
	private String nombre;
	@Column(name = "EstaActivo")
	private boolean estaActivo;
	
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

	public boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

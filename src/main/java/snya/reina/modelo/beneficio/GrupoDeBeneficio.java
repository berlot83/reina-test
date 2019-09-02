package snya.reina.modelo.beneficio;

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
@Table(name="Reina_GrupoDeBeneficio", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class GrupoDeBeneficio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 213772724725864173L;

	public final static Integer ID_BENEFICIO_PENSION = 1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdGrupoDeBeneficio")
	private Integer id;
	
	@Column(name="GrupoDeBeneficio")
	private String nombre;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	//Constructor	
	public GrupoDeBeneficio() {
	}
	
	// Getters
	
	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
	public void setNombre(String grupoDeBeneficio) {
		this.nombre = grupoDeBeneficio;
	}
	public void setId(Integer idGrupoDeBeneficio) {
		this.id = idGrupoDeBeneficio;
	}
	
	//Setters
	
	public Boolean getEstaActivo() {
		return estaActivo;
	}
	public String getNombre() {
		return nombre;
	}
	public Integer getId() {
		return id;
	}

}

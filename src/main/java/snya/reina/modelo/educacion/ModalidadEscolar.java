package snya.reina.modelo.educacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="Reina_ModalidadEscolar", catalog="SistemasSNYA" )
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class ModalidadEscolar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -192604190629495199L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdModalidadEscolar")	
	private Integer id;
	@Column(name="ModalidadEscolar")
	private String nombre;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	@Column(name="PorDefecto")
	private Boolean porDefecto;
	@ManyToMany
	@JoinTable(
			name="Reina_ModalidadNivelEscolar",
			joinColumns=@JoinColumn(name="IdModalidadEscolar", referencedColumnName="IdModalidadEscolar"),
			inverseJoinColumns=@JoinColumn(name="IdNivelEscolar", referencedColumnName="IdNivelEscolar"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NivelEscolar> niveles;

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

	public List<NivelEscolar> getNiveles() {
		return niveles;
	}

	public void setNiveles(List<NivelEscolar> niveles) {
		this.niveles = niveles;
	}

}

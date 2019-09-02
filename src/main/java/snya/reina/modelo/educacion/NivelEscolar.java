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
@Table(name="Reina_NivelEscolar", catalog="SistemasSNYA" )
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class NivelEscolar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6465309345100658284L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdNivelEscolar")
	private Integer id;
	@Column(name="NivelEscolar")
	private String nombre;
	//private ModalidadEscolar modalidad;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	@Column(name="PorDefecto")
	private Boolean porDefecto;
	@ManyToMany
	@JoinTable(
			name="Reina_NivelAnioEscolar",
			joinColumns=@JoinColumn(name="IdNivelEscolar", referencedColumnName="IdNivelEscolar"),
			inverseJoinColumns=@JoinColumn(name="IdAnioEscolar", referencedColumnName="IdAnioEscolar"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AnioEscolar> anios;

	
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
/*
	public ModalidadEscolar getModalidad() {
		return modalidad;
	}

	public void setModalidad(ModalidadEscolar modalidad) {
		this.modalidad = modalidad;
	}
*/
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

	public List<AnioEscolar> getAnios() {
		return anios;
	}

	public void setAnios(List<AnioEscolar> anios) {
		this.anios = anios;
	}

}

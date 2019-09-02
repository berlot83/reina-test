package snya.reina.modelo.proceso;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity
@Table(name="Reina_TipoDeMomentoProcesal", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeMomentoProcesal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5934876738017215927L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeMomentoProcesal")
	private Integer id;
	
	@Column(name="TipoDeMomentoProcesal")
	private String nombre;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;
	
	@ManyToMany
	@JoinTable(
			name="Reina_TipoDeMomentoTipoDeMedidaPenalSituacion",
			joinColumns=@JoinColumn(name="IdTipoDeMomentoProcesal", referencedColumnName="IdTipoDeMomentoProcesal"),
			inverseJoinColumns=@JoinColumn(name="IdTipoDeMedidaEnProcesoPenal", referencedColumnName="IdTipoDeMedidaEnProcesoPenal"))
	private List<TipoDeMedidaEnProcesoPenal> medidas;

	/* === Constructores === */
	public TipoDeMomentoProcesal() {
		this.setMedidas(new ArrayList<TipoDeMedidaEnProcesoPenal>());
	}

	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String value) {
		nombre = value;
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

	public List<TipoDeMedidaEnProcesoPenal> getMedidas() {
		return medidas;
	}

	public void setMedidas(List<TipoDeMedidaEnProcesoPenal> medidas) {
		this.medidas = medidas;
	}
}

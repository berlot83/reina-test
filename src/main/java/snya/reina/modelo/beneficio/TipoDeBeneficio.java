package snya.reina.modelo.beneficio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import snya.reina.modelo.institucion.Institucion;

@Entity
@Table(name = "Reina_TipoDeBeneficio", catalog = "SistemasSNYA")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeBeneficio implements Comparable<TipoDeBeneficio>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1391811143773773127L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdTipoDeBeneficio")
	private Integer id;

	@Column(name = "TipoDeBeneficio")
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "IdGrupoDeBeneficio")
	private GrupoDeBeneficio grupo;

	@Column(name = "EstaActivo")
	private Boolean estaActivo;

	@Column(name = "PorDefecto")
	private Boolean porDefecto;

	@ManyToOne(optional = true)
	@JoinColumn(name = "IdResponsable")
	private Institucion responsable;

	@Column(name = "RegistrarEntregaTarjeta")
	private Boolean entregaTarjeta;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="beneficio")
	private List<Operativo> operativos;

	
	// Constructores
	public TipoDeBeneficio() {
		this.operativos = new ArrayList<Operativo>();
	}

	
	public String getNombreCompleto() {
		return this.getGrupo().getNombre() + " - " + this.getNombre();
	}

	@Override
	public int compareTo(TipoDeBeneficio o) {
		return this.getNombreCompleto().compareTo(o.getNombreCompleto());
	}

	// Getters
	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getPorDefecto() {
		return porDefecto;
	}

	public String getNombre() {
		return nombre;
	}

	// Setters
	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public void setId(Integer idTipoDeBeneficio) {
		this.id = idTipoDeBeneficio;
	}

	public void setPorDefecto(Boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public void setNombre(String tipoDeBeneficio) {
		this.nombre = tipoDeBeneficio;
	}

	public GrupoDeBeneficio getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDeBeneficio grupo) {
		this.grupo = grupo;
	}

	public Institucion getResponsable() {
		return responsable;
	}

	public void setResponsable(Institucion responsable) {
		this.responsable = responsable;
	}

	public Boolean getEntregaTarjeta() {
		return entregaTarjeta;
	}

	public void setEntregaTarjeta(Boolean entregaTarjeta) {
		this.entregaTarjeta = entregaTarjeta;
	}

	public List<Operativo> getOperativos() {
		return operativos;
	}

	public void setOperativos(List<Operativo> operativos) {
		this.operativos = operativos;
	}
}

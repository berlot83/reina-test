package snya.reina.modelo.proceso;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
@Table(name="Reina_TipoDeMedidaEnProcesoPenal", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeMedidaEnProcesoPenal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5186934164815587896L;


	public static final Integer ID_DECLINACION_MAYORES = 23;
	public static final Integer ID_APREHENSION = 1;
	
	/* === Variables de instancia === */	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeMedidaEnProcesoPenal")
	private Integer id;
	
	@Column(name="TipoDeMedidaEnProcesoPenal")
	private String nombre;
	
	@Column(name="TiempoDeHorasPredeterminado")
	private int tiempoDeHorasPredeterminado;
	
	@Column(name="TiempoDeDiasPredeterminado")
	private int tiempoDeDiasPredeterminado;
	
	@Column(name="TiempoDeMesesPredeterminado")
	private int tiempoDeMesesPredeterminado;
	
	@Column(name="CantidadDeProrrogas")
	private int cantidadDeProrrogas;
	
	@Column(name="CierraProceso")
	private boolean cierraProceso;
	
	@Column(name="ContarDesdePrincipio")
	private boolean contarDesdePrincipio;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="Reina_TipoDeMedidaEnProcesoPenalPosible",
			joinColumns=@JoinColumn(name="IdTipoDeMedidaEnProcesoPenalAnterior", referencedColumnName="IdTipoDeMedidaEnProcesoPenal"),
			inverseJoinColumns=@JoinColumn(name="IdTipoDeMedidaEnProcesoPenalPosterior", referencedColumnName="IdTipoDeMedidaEnProcesoPenal"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TipoDeMedidaEnProcesoPenal> siguientes;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="Reina_TipoDeMedidaPenalTipoDeDetalleDeMedida",
			joinColumns=@JoinColumn(name="IdTipoDeMedidaEnProcesoPenal", referencedColumnName="IdTipoDeMedidaEnProcesoPenal"),
			inverseJoinColumns=@JoinColumn(name="IdTipoDetalleDeMedidaEnProceso", referencedColumnName="IdTipoDetalleDeMedidaEnProceso"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles;

	/* === Constructores === */
	public TipoDeMedidaEnProcesoPenal() {
	}

	
	public boolean tieneTiempoPredeterminado() {
		return (this.getTiempoDeHorasPredeterminado() != 0 || this.getTiempoDeDiasPredeterminado() != 0 ||
				this.getTiempoDeMesesPredeterminado() != 0) ||
				this.isCierraProceso();
	}

	public String traerDetalleTiempoPredeterminado() {
		String detalle = "";
		if ( (this.getTiempoDeHorasPredeterminado() != 0 || this.getTiempoDeDiasPredeterminado() != 0 ||
				this.getTiempoDeMesesPredeterminado() != 0) ||
				this.isCierraProceso() ) {
			if (this.getTiempoDeMesesPredeterminado() != 0)
				detalle = (this.getTiempoDeMesesPredeterminado() == 1) ? "un mes" : this.getTiempoDeMesesPredeterminado() + " meses";
			if (this.getTiempoDeDiasPredeterminado() != 0) {
				if (!detalle.equals("")) detalle += ", ";
				detalle = (this.getTiempoDeDiasPredeterminado() == 1) ? "un dia" : this.getTiempoDeDiasPredeterminado() + " dias";
			}
			if (this.getTiempoDeHorasPredeterminado() != 0) {
				if (!detalle.equals("")) detalle += " y ";			
				detalle = (this.getTiempoDeHorasPredeterminado() == 1) ? "una hora" : this.getTiempoDeHorasPredeterminado() + " horas";
			}
		}
			
		return detalle;
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

	public int getTiempoDeHorasPredeterminado() {
		return tiempoDeHorasPredeterminado;
	}

	public void setTiempoDeHorasPredeterminado(int tiempoDeHorasPredeterminado) {
		this.tiempoDeHorasPredeterminado = tiempoDeHorasPredeterminado;
	}

	public int getTiempoDeDiasPredeterminado() {
		return tiempoDeDiasPredeterminado;
	}

	public void setTiempoDeDiasPredeterminado(int tiempoDeDiasPredeterminado) {
		this.tiempoDeDiasPredeterminado = tiempoDeDiasPredeterminado;
	}

	public int getTiempoDeMesesPredeterminado() {
		return tiempoDeMesesPredeterminado;
	}

	public void setTiempoDeMesesPredeterminado(int tiempoDeMesesPredeterminado) {
		this.tiempoDeMesesPredeterminado = tiempoDeMesesPredeterminado;
	}

	public int getCantidadDeProrrogas() {
		return cantidadDeProrrogas;
	}

	public void setCantidadDeProrrogas(int cantidadDeProrrogas) {
		this.cantidadDeProrrogas = cantidadDeProrrogas;
	}

	public boolean isCierraProceso() {
		return cierraProceso;
	}

	public void setCierraProceso(boolean cierraProceso) {
		this.cierraProceso = cierraProceso;
	}

	public boolean isContarDesdePrincipio() {
		return contarDesdePrincipio;
	}


	public void setContarDesdePrincipio(boolean contarDesdePrincipio) {
		this.contarDesdePrincipio = contarDesdePrincipio;
	}


	public boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean value) {
		estaActivo = value;
	}

	public List<TipoDeMedidaEnProcesoPenal> getSiguientes() {
		return siguientes;
	}

	public void setSiguientes(List<TipoDeMedidaEnProcesoPenal> siguientes) {
		this.siguientes = siguientes;
	}

	public List<TipoDeDetalleDeMedidaEnProcesoPenal> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles) {
		this.detalles = detalles;
	}
}
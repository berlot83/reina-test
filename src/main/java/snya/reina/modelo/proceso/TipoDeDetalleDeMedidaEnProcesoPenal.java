package snya.reina.modelo.proceso;

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
@Table(name="Reina_TipoDetalleDeMedidaEnProceso", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeDetalleDeMedidaEnProcesoPenal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2307938187147924462L;
	public static final Integer ID_APREHENSION = 1;
	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDetalleDeMedidaEnProceso")
	private Integer id;
	
	@Column(name="TipoDetalleDeMedidaEnProceso")
	private String nombre;
	
	@Column(name="TipoDetalleDeMedidaEnProcesoCorto")
	private String nombreCorto;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;

	/* === Constructores === */
	public TipoDeDetalleDeMedidaEnProcesoPenal() {
	}

	
	/* === Propiedades === */
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

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public boolean isEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

package snya.reina.modelo.movimiento;

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
@Table(name="Reina_Mov_EstadoMovimiento", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class EstadoMovimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5726796291116768275L;
	public static final int PENDIENTE = 1;
	public static final int CONFIRMADO = 2;
	public static final int CANCELADO = 3;

	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdEstadoMovimiento")
	private Integer id;
	
	@Column(name="EstadoMovimiento")
	private String nombre;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;

	
	public boolean noEstaPendiente() {
		return this.getId() != EstadoMovimiento.PENDIENTE;
	}

	public boolean estaConfirmado() {
		return this.getId() == EstadoMovimiento.CONFIRMADO;
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
}
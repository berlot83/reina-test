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
@Table(name="Reina_EstadoObraSocial", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class EstadoObraSocial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549953784068258665L;
	
	/* === Constante === */
	public static final int ACTIVA = 1;
	public static final int SUSPENDIDA = 2;
	public static final int NO_POSEE = 3;
	public static final int SE_DESCONOCE = 4;
    
		
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdEstadoObraSocial")
	private Integer id;
	
	@Column(name="Estado")
	private String nombre;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;
	
	
	/* === Constructores === */
	public EstadoObraSocial() {
		
	}

	/* == Propiedades == */
	
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

	public boolean isEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public boolean isPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	

}

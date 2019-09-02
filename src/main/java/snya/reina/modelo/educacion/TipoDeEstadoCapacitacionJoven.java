package snya.reina.modelo.educacion;

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
@Table(name="Reina_TipoDeEstadoCapacitacionJoven", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeEstadoCapacitacionJoven implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3921548921230279598L;
	
	public static final int CURSANDO = 1;
	public static final int FINALIZADO = 2;
	public static final int APROBADO = 3;
	public static final int DESAPROBADO = 4;
	public static final int INTERRUMPIDO = 5;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeEstadoCapacitacionJoven")
	private Integer id;
	@Column(name="TipoDeEstadoCapacitacionJoven")
	private String nombre;
	@Column(name="Final")
	private Boolean esFinal;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	@Column(name="PorDefecto")
	private Boolean porDefecto;

	
	public String getTieneFinalidad() {
		return (this.getId() == FINALIZADO) || (this.getId() == APROBADO) ? "si" : "no";
	}
	
	
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

	public Boolean getEsFinal() {
		return esFinal;
	}

	public void setEsFinal(Boolean esFinal) {
		this.esFinal = esFinal;
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

}

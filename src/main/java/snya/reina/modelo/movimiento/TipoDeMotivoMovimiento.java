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
@Table(name="Reina_Mov_TipoDeMotivoMovimiento", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeMotivoMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8875284967267894269L;

	public static final Integer ID_MOTIVO_ORDENJUDICIAL = 1;
	public static final Integer ID_MOTIVO_NOTIFICACION = 4;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeMotivoMovimiento")
	private Integer id;
	
	@Column(name="TipoDeMotivoMovimiento")
	private String nombre;
	
	@Column(name="EsOrdenJudicial")
	private Boolean esOrdenJudicial;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	@Column(name="PorDefecto")
	private Boolean porDefecto;
	
	

	public boolean esOrdenJudicial() {
		return this.esOrdenJudicial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEsOrdenJudicial() {
		return esOrdenJudicial;
	}

	public void setEsOrdenJudicial(Boolean esOrdenJudicial) {
		this.esOrdenJudicial = esOrdenJudicial;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
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

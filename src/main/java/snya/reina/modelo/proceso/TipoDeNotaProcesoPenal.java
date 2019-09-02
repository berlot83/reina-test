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

import snya.reina.ReinaException;
import snya.reina.modelo.proceso.comando.ComandoProceso;
import snya.reina.serviciomodelo.GeneradorDeComando;

@Entity
@Table(name="Reina_TipoDeNotaProcesoPenal", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeNotaProcesoPenal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7608853097409797123L;
	
	public static final Integer ID_APELACION_NO_SUSPENSIVA = 3;
	public static final Integer ID_APELACION_SUSPENSIVA = 3;
	public static final Integer ID_DECLINACION_COMPETENCIA = 6;
	public static final Integer ID_UNIFICADOR_PROCESO = 7;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeNotaProcesoPenal")
	private Integer id;
	
	@Column(name="TipoDeNotaProcesoPenal")
	private String nombre;
	
	@Column(name="Comando")
	private String comando;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	public boolean getRequiereJuzgado() throws ReinaException {
		ComandoProceso comando = (ComandoProceso) GeneradorDeComando.CrearComando(this.getComando());
		return comando.requiereJuzgado();
	}
	
	public boolean getRequiereMedida() throws ReinaException {
		ComandoProceso comando = (ComandoProceso) GeneradorDeComando.CrearComando(this.getComando());
		return comando.requiereMedida();
	}
	
	public boolean getRequiereProceso() throws ReinaException {
		ComandoProceso comando = (ComandoProceso) GeneradorDeComando.CrearComando(this.getComando());
		return comando.requiereProceso();		
	} 
	
	public boolean esDeCierre() {
		return this.getId().equals(TipoDeNotaProcesoPenal.ID_DECLINACION_COMPETENCIA)
				|| this.getId().equals(TipoDeNotaProcesoPenal.ID_UNIFICADOR_PROCESO);
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

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

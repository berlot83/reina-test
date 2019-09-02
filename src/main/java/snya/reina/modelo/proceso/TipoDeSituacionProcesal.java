package snya.reina.modelo.proceso;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="Reina_TipoDeSituacionProcesal", catalog="SistemasSNYA")
@NamedStoredProcedureQuery(name = "ConsultaTipoDeSituacionProcesal", procedureName = "SistemasSNYA.Reina_TraerTipoDeSituacionProcesal", resultClasses = snya.reina.modelo.proceso.TipoDeSituacionProcesal.class, parameters = {
	@StoredProcedureParameter(name = "pIdTipoDeMomentoProcesal", mode = ParameterMode.IN, type = Integer.class),
	@StoredProcedureParameter(name = "pIdTipoDeMedidaProcesal", mode = ParameterMode.IN, type = Integer.class)})
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeSituacionProcesal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8593001096448805326L;
	
	public static final Integer ID_DECLINACION_MAYORIA = 13;
	public static final Integer ID_UNIFICADO = 14;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeSituacionProcesal")
	private Integer id;
	
	@Column(name="TipoDeSituacionProcesal")
	private String nombre;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String tipoDeParentesco) {
		this.nombre = tipoDeParentesco;
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

package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(
		name = "MedidasDelJoven",
		procedureName = "SistemasSNYA.Reina_TraerMedidasEnProcesoPenalParaJoven", 
		resultClasses = snya.reina.modelo.MedidaEnProcesoDelJoven.class,
		parameters = {			
			@StoredProcedureParameter(name="idJoven", mode= ParameterMode.IN, type= Integer.class),
			@StoredProcedureParameter(name="fecha", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="idProceso", mode= ParameterMode.IN, type= Integer.class)
		}
	)
public class MedidaEnProcesoDelJoven implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2011716549618193965L;
	
	@Id 
	@Column(name="IdMedidaEnProcesoPenal")
	private Integer id;
	private Date fechaMedida;
	private Date fechaFinMedida;
	@Column(name="TipoDeMedidaEnProcesoPenal")
	private String medida; 
	private Integer idProcesoPenal;
	private String juzgado;
	private String nro;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaMedida() {
		return fechaMedida;
	}

	public void setFechaMedida(Date fechaMedida) {
		this.fechaMedida = fechaMedida;
	}

	public Date getFechaFinMedida() {
		return fechaFinMedida;
	}

	public void setFechaFinMedida(Date fechaFinMedida) {
		this.fechaFinMedida = fechaFinMedida;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public Integer getIdProcesoPenal() {
		return idProcesoPenal;
	}

	public void setIdProcesoPenal(Integer idProcesoPenal) {
		this.idProcesoPenal = idProcesoPenal;
	}

	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}
}

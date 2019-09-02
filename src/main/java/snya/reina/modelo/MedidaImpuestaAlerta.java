package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaAlertaMedida", procedureName = "SistemasSNYA.Reina_TraerAlarmaMedidaProcesal", resultClasses = snya.reina.modelo.MedidaImpuestaAlerta.class, parameters = {
	@StoredProcedureParameter(name = "dias", mode = ParameterMode.IN, type = Integer.class),
	@StoredProcedureParameter(name = "tipos", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "recursos", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "propiedad", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) })
public class MedidaImpuestaAlerta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8944256354878979085L;
	
	@Id
	private Integer idJoven;
	private Integer idProcesoPenal;
	private String expediente;
	private String nombre;
	private String juzgado;
	private String nroProceso;
	private String fiscalia;
	private String institucion;
	private String tipoDeMedidaEnProcesoPenal;
	private Date fechaMedida;
	private Date fechaLimite;
	private Integer estado;

	
	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer idJoven) {
		this.idJoven = idJoven;
	}

	public Integer getIdProcesoPenal() {
		return idProcesoPenal;
	}

	public void setIdProcesoPenal(Integer idProcesoPenal) {
		this.idProcesoPenal = idProcesoPenal;
	}
	
	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	public String getNroProceso() {
		return nroProceso;
	}

	public void setNroProceso(String nroProceso) {
		this.nroProceso = nroProceso;
	}

	public String getFiscalia() {
		return fiscalia;
	}

	public void setFiscalia(String fiscalia) {
		this.fiscalia = fiscalia;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getTipoDeMedidaEnProcesoPenal() {
		return tipoDeMedidaEnProcesoPenal;
	}

	public void setTipoDeMedidaEnProcesoPenal(String tipoDeMedidaEnProcesoPenal) {
		this.tipoDeMedidaEnProcesoPenal = tipoDeMedidaEnProcesoPenal;
	}

	public Date getFechaMedida() {
		return fechaMedida;
	}

	public void setFechaMedida(Date fechaMedida) {
		this.fechaMedida = fechaMedida;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}

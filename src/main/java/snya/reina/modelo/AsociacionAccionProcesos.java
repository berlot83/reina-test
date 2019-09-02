package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(
		name = "AsociacionProcesos",
		procedureName = "SistemasSNYA.Reina_TraerAsociacionesAccionProcesos", 
		resultClasses = snya.reina.modelo.AsociacionAccionProcesos.class,
		parameters = { 
			@StoredProcedureParameter(name="idJoven", mode= ParameterMode.IN, type= Integer.class),
			@StoredProcedureParameter(name="idProceso", mode= ParameterMode.IN, type= Integer.class)
			
		}
	)
public class AsociacionAccionProcesos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8139012178530446647L;
	
	@Id
	private Integer id;
	private Integer discAccion;
	private Integer idAccion;
	private String tipoDeAccion;
	private Date fechaAccion;
	private String origen;
	private String destino;
	private Integer idProceso;
	private String juzgado;
	private String nro;
	private Integer idMedidaEnProcesoPenal;
	private Date fechaMedida;
	private String tipoDeMedidaEnProcesoPenal;

	
	public String getDetalleMedida() {
		return Calendario.formatearFecha(fechaMedida) + " - " + this.tipoDeMedidaEnProcesoPenal + ". " + 
			this.juzgado + " " + this.nro;
	}

	public String getDetalleAccion() {
		return Calendario.formatearFecha(fechaAccion) + " - " + this.tipoDeAccion + 
			((discAccion == 0) ? " - " + this.origen + " " + this.destino : "");
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDiscAccion() {
		return discAccion;
	}

	public void setDiscAccion(Integer discAccion) {
		this.discAccion = discAccion;
	}

	public Integer getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	public String getTipoDeAccion() {
		return tipoDeAccion;
	}

	public void setTipoDeAccion(String tipoDeAccion) {
		this.tipoDeAccion = tipoDeAccion;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
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

	public Integer getIdMedidaEnProcesoPenal() {
		return idMedidaEnProcesoPenal;
	}

	public void setIdMedidaEnProcesoPenal(Integer idMedidaEnProcesoPenal) {
		this.idMedidaEnProcesoPenal = idMedidaEnProcesoPenal;
	}

	public Date getFechaMedida() {
		return fechaMedida;
	}

	public void setFechaMedida(Date fechaMedida) {
		this.fechaMedida = fechaMedida;
	}

	public String getTipoDeMedidaEnProcesoPenal() {
		return tipoDeMedidaEnProcesoPenal;
	}

	public void setTipoDeMedidaEnProcesoPenal(String tipoDeMedidaEnProcesoPenal) {
		this.tipoDeMedidaEnProcesoPenal = tipoDeMedidaEnProcesoPenal;
	}
}

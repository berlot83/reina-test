package snya.reina.modelo.estadistica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "ConsultaJovenBeneficio", procedureName = "SistemasSNYA.Reina_TraerJovenBeneficio", resultClasses = snya.reina.modelo.estadistica.EstadisticaBeneficio.class, parameters = {
			@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "idBeneficio", mode = ParameterMode.IN, type = Integer.class),
			@StoredProcedureParameter(name = "idEstado", mode = ParameterMode.IN, type = Integer.class) }),
	@NamedStoredProcedureQuery(name = "ConsultaAlertaJovenBeneficioTarjeta", procedureName = "SistemasSNYA.TraerJovenBeneficioAlertaTarjeta", resultClasses = snya.reina.modelo.estadistica.EstadisticaBeneficio.class, parameters = {
			@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "idBeneficio", mode = ParameterMode.IN, type = Integer.class) })
})
public class EstadisticaBeneficio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7817625850494341215L;
	@Id
	private Integer id;
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String documento;
	private Integer edad;
	private String tipoInstitucion;
	private String institucion;

	private String beneficio;
	private Date fechaAdhesion;
	private Date fechaFin;
	private String estado;
	private Date fechaEntregaTarjeta;
	private Integer alarma;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer idJoven) {
		this.idJoven = idJoven;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(String tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	public Date getFechaAdhesion() {
		return fechaAdhesion;
	}

	public void setFechaAdhesion(Date fechaAdhesion) {
		this.fechaAdhesion = fechaAdhesion;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaEntregaTarjeta() {
		return fechaEntregaTarjeta;
	}

	public void setFechaEntregaTarjeta(Date fechaEntregaTarjeta) {
		this.fechaEntregaTarjeta = fechaEntregaTarjeta;
	}

	public Integer getAlarma() {
		return alarma;
	}

	public void setAlarma(Integer alarma) {
		this.alarma = alarma;
	}
}

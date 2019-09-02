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
		@NamedStoredProcedureQuery(name = "ConsultaJovenFormacionLaboral", procedureName = "SistemasSNYA.Reina_TraerJovenFormacionLaboral", resultClasses = snya.reina.modelo.estadistica.EstadisticaFormacionLaboral.class, parameters = {
				@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idFormacion", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "idDictado", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "idEstado", mode = ParameterMode.IN, type = Integer.class) }),
		@NamedStoredProcedureQuery(name = "ConsultaAlertaJovenFormacionLaboralInterrumpida", procedureName = "SistemasSNYA.Reina_TraerJovenFormacionLaboralAlertaInterrumpida", resultClasses = snya.reina.modelo.estadistica.EstadisticaFormacionLaboral.class, parameters = {
				@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idFormacion", mode = ParameterMode.IN, type = Integer.class) }),
		@NamedStoredProcedureQuery(name = "ConsultaAlertaJovenFormacionLaboralCertificacion", procedureName = "SistemasSNYA.Reina_TraerJovenFormacionLaboralAlertaCertificacion", resultClasses = snya.reina.modelo.estadistica.EstadisticaFormacionLaboral.class, parameters = {
				@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idFormacion", mode = ParameterMode.IN, type = Integer.class) }) })
public class EstadisticaFormacionLaboral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7961543335264249924L;
	
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

	private Integer idFormacion;
	private String formacion;
	private Integer idDictado;
	private String dictado;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer idEstado;
	private String estado;
	private Date fechaCertificado;

	private String beneficio;
	private Date fechaAdhesionBeneficio;
	private Date fechaFinBeneficio;
	private String estadoBeneficio;
	private Date fechaEntregaTarjeta;

	private String cicloLectivo;
	private String anioEscolar;
	private String nivelEscolar;
	private String modalidadEscolar;
	private String establecimientoEscolar;
	private String periodoEvaluacion;
	private String cursando;
	private String finalizado;
	private String certificado;

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

	public Integer getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(Integer idFormacion) {
		this.idFormacion = idFormacion;
	}

	public String getFormacion() {
		return formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	public Integer getIdDictado() {
		return idDictado;
	}

	public void setIdDictado(Integer idDictado) {
		this.idDictado = idDictado;
	}

	public String getDictado() {
		return dictado;
	}

	public void setDictado(String dictado) {
		this.dictado = dictado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaCertificado() {
		return fechaCertificado;
	}

	public void setFechaCertificado(Date fechaCertificado) {
		this.fechaCertificado = fechaCertificado;
	}

	public String getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	public Date getFechaAdhesionBeneficio() {
		return fechaAdhesionBeneficio;
	}

	public void setFechaAdhesionBeneficio(Date fechaAdhesionBeneficio) {
		this.fechaAdhesionBeneficio = fechaAdhesionBeneficio;
	}

	public Date getFechaFinBeneficio() {
		return fechaFinBeneficio;
	}

	public void setFechaFinBeneficio(Date fechaFinBeneficio) {
		this.fechaFinBeneficio = fechaFinBeneficio;
	}

	public String getEstadoBeneficio() {
		return estadoBeneficio;
	}

	public void setEstadoBeneficio(String estadoBeneficio) {
		this.estadoBeneficio = estadoBeneficio;
	}

	public Date getFechaEntregaTarjeta() {
		return fechaEntregaTarjeta;
	}

	public void setFechaEntregaTarjeta(Date fechaEntregaTarjeta) {
		this.fechaEntregaTarjeta = fechaEntregaTarjeta;
	}

	public String getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(String cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public String getAnioEscolar() {
		return anioEscolar;
	}

	public void setAnioEscolar(String anioEscolar) {
		this.anioEscolar = anioEscolar;
	}

	public String getNivelEscolar() {
		return nivelEscolar;
	}

	public void setNivelEscolar(String nivelEscolar) {
		this.nivelEscolar = nivelEscolar;
	}

	public String getModalidadEscolar() {
		return modalidadEscolar;
	}

	public void setModalidadEscolar(String modalidadEscolar) {
		this.modalidadEscolar = modalidadEscolar;
	}

	public String getEstablecimientoEscolar() {
		return establecimientoEscolar;
	}

	public void setEstablecimientoEscolar(String establecimientoEscolar) {
		this.establecimientoEscolar = establecimientoEscolar;
	}

	public String getPeriodoEvaluacion() {
		return periodoEvaluacion;
	}

	public void setPeriodoEvaluacion(String periodoEvaluacion) {
		this.periodoEvaluacion = periodoEvaluacion;
	}

	public String getCursando() {
		return cursando;
	}

	public void setCursando(String cursando) {
		this.cursando = cursando;
	}

	public String getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
}

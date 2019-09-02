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
		name = "ConsultaJovenEscolaridad",
		procedureName = "SistemasSNYA.Reina_TraerEscolaridadJoven", 
		resultClasses = snya.reina.modelo.JovenEscolaridad.class,
		parameters = { 
			@StoredProcedureParameter(name="idTipo", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="idRecurso", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="apellidos", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="nombres", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="nombreCompleto", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="documento", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="expediente", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="nroJudicial", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="dptoJudicial", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="idOrganoJudicial", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="idDefensoria", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="idFiscalia", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="nroPaquete", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="fechaPaquete", mode= ParameterMode.IN, type= String.class)
		}
	)	
public class JovenEscolaridad implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -6097455263409012041L;
	
	@Id
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String documento;
	private String sexo;
	private Date fechaNac;
	private Integer edad;
	private String tipoDeInstitucion;
	private String institucion;
	private Integer cicloLectivo;
	private String anioEscolar;
	private String nivelEscolar;
	private String modalidadEscolar;
	private String establecimientoEscolar;
	private String periodoEvaluacion;
	private String cursando;
	private String finalizado;
	private String certificado;
	
	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer idJoven) {
		this.idJoven = idJoven;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getExpediente() {
		return expediente;
	}

	public String getTipoDeInstitucion() {
		return tipoDeInstitucion;
	}

	public void setTipoDeInstitucion(String tipoDeInstitucion) {
		this.tipoDeInstitucion = tipoDeInstitucion;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
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

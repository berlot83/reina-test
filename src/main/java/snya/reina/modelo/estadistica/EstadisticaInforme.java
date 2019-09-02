package snya.reina.modelo.estadistica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaEstadisticaInforme", procedureName = "SistemasSNYA.Reina_TraerEstadisticaInforme", resultClasses = snya.reina.modelo.estadistica.EstadisticaInforme.class, parameters = {
		@StoredProcedureParameter(name = "idTipoRecurso", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idGrupo", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fechaDesde", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fechaHasta", mode = ParameterMode.IN, type = String.class)
})
public class EstadisticaInforme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7344901173788809864L;
	
	@Id
	private Integer idInforme;	
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String sexo;
	private Date fechaNacimiento;
	private Integer edad;
	private String documento;

	private Date fechaDeInforme;
	private String grupoDeInforme;
	private String tipoDeInforme;
	private String tipoDeinstitucion;
	private String institucion;

	private String estaPresente;
	private String institucionPresente;

	
	public Integer getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(Integer idIntervencion) {
		this.idInforme = idIntervencion;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEstaPresente() {
		return estaPresente;
	}

	public void setEstaPresente(String estaPresente) {
		this.estaPresente = estaPresente;
	}

	public String getInstitucionPresente() {
		return institucionPresente;
	}

	public void setInstitucionPresente(String institucionPresente) {
		this.institucionPresente = institucionPresente;
	}

	public Date getFechaDeInforme() {
		return fechaDeInforme;
	}

	public void setFechaDeInforme(Date fechaDeInforme) {
		this.fechaDeInforme = fechaDeInforme;
	}

	public String getGrupoDeInforme() {
		return grupoDeInforme;
	}

	public void setGrupoDeInforme(String grupoDeInforme) {
		this.grupoDeInforme = grupoDeInforme;
	}

	public String getTipoDeInforme() {
		return tipoDeInforme;
	}

	public void setTipoDeInforme(String tipoDeInforme) {
		this.tipoDeInforme = tipoDeInforme;
	}

	public String getTipoDeinstitucion() {
		return tipoDeinstitucion;
	}

	public void setTipoDeinstitucion(String tipoDeinstitucion) {
		this.tipoDeinstitucion = tipoDeinstitucion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
}

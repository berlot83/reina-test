package snya.reina.modelo.estadistica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaEstadisticaIntervencion", procedureName = "SistemasSNYA.Reina_TraerEstadisticaIntervencion", resultClasses = snya.reina.modelo.estadistica.EstadisticaIntervencion.class, parameters = {
		@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idDptoJudicial", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idOrganoJudicial", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fechaDesde", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fechaHasta", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idMunicipio", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idEstado", mode = ParameterMode.IN, type = String.class)
})
public class EstadisticaIntervencion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7646803808469880010L;
	
	@Id
	private Integer idIntervencion;	
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String sexo;
	private Date fechaNacimiento;
	private Integer edad;
	private String documento;
	private String juzgado;
	private String departamentoJudicial;
	private String caratula;
	private String tipoDeSituacionProcesal;
	private String localidadDomicilio;
	private String municipioDomiclio;
	private Date fechaIntervencion;
	private String institucion;
	private Date fechaCese;
	private String motivoCese;
	private String estaPresente;
	private String institucionPresente;

	
	public Integer getIdIntervencion() {
		return idIntervencion;
	}

	public void setIdIntervencion(Integer idIntervencion) {
		this.idIntervencion = idIntervencion;
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

	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	public String getDepartamentoJudicial() {
		return departamentoJudicial;
	}

	public void setDepartamentoJudicial(String departamentoJudicial) {
		this.departamentoJudicial = departamentoJudicial;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public String getTipoDeSituacionProcesal() {
		return tipoDeSituacionProcesal;
	}

	public void setTipoDeSituacionProcesal(String tipoDeSituacionProcesal) {
		this.tipoDeSituacionProcesal = tipoDeSituacionProcesal;
	}

	public String getLocalidadDomicilio() {
		return localidadDomicilio;
	}

	public void setLocalidadDomicilio(String localidadDomicilio) {
		this.localidadDomicilio = localidadDomicilio;
	}

	public String getMunicipioDomiclio() {
		return municipioDomiclio;
	}

	public void setMunicipioDomiclio(String municipioDomiclio) {
		this.municipioDomiclio = municipioDomiclio;
	}

	public Date getFechaIntervencion() {
		return fechaIntervencion;
	}

	public void setFechaIntervencion(Date fechaIntervencion) {
		this.fechaIntervencion = fechaIntervencion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Date getFechaCese() {
		return fechaCese;
	}

	public void setFechaCese(Date fechaCese) {
		this.fechaCese = fechaCese;
	}

	public String getMotivoCese() {
		return motivoCese;
	}

	public void setMotivoCese(String motivoCese) {
		this.motivoCese = motivoCese;
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
}

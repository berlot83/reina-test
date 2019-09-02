package snya.reina.modelo.estadistica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaEstadisticaPresente", procedureName = "SistemasSNYA.Reina_TraerEstadisticaPresente", resultClasses = snya.reina.modelo.estadistica.EstadisticaPresente.class, 
	parameters = {
	@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idDptoJudicial", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idOrganoJudicial", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idDefensoria", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idFiscalia", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idMunicipio", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "idLocalidad", mode = ParameterMode.IN, type = String.class) })
public class EstadisticaPresente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9132836448581041596L;
	
	@Id
	private Long id;
	private Integer idJoven;
	private String tipoDeInstitucion;
	private String institucion;
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
	private Date fechaIngresoInstitucion;
	private Integer tiempoPermanencia;
	private Integer tiempoPermanenciaTotal;
	private Date fechaMedida;
	private String conLimite;
	private Integer diasLimite;
	private Date fechaLimite;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer idJoven) {
		this.idJoven = idJoven;
	}

	public String getTipoDeInstitucion() {
		return tipoDeInstitucion;
	}

	public void setTipoDeInstitucion(String tipoDeInstitucion) {
		this.tipoDeInstitucion = tipoDeInstitucion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
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

	public Date getFechaIngresoInstitucion() {
		return fechaIngresoInstitucion;
	}

	public void setFechaIngresoInstitucion(Date fechaIngresoInstitucion) {
		this.fechaIngresoInstitucion = fechaIngresoInstitucion;
	}

	public Integer getTiempoPermanencia() {
		return tiempoPermanencia;
	}

	public void setTiempoPermanencia(Integer tiempoPermanencia) {
		this.tiempoPermanencia = tiempoPermanencia;
	}

	public Integer getTiempoPermanenciaTotal() {
		return tiempoPermanenciaTotal;
	}

	public void setTiempoPermanenciaTotal(Integer tiempoPermanenciaTotal) {
		this.tiempoPermanenciaTotal = tiempoPermanenciaTotal;
	}

	public Date getFechaMedida() {
		return fechaMedida;
	}

	public void setFechaMedida(Date fechaMedida) {
		this.fechaMedida = fechaMedida;
	}

	public String getConLimite() {
		return conLimite;
	}

	public void setConLimite(String conLimite) {
		this.conLimite = conLimite;
	}

	public Integer getDiasLimite() {
		return diasLimite;
	}

	public void setDiasLimite(Integer diasLimite) {
		this.diasLimite = diasLimite;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
}

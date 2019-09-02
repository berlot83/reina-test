package snya.reina.modelo.estadistica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaEstadisticaMovimiento", procedureName = "SistemasSNYA.Reina_TraerEstadisticaMovimiento", resultClasses = snya.reina.modelo.estadistica.EstadisticaMovimiento.class, parameters = {
		@StoredProcedureParameter(name = "idTipoOrigen", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idRecursoOrigen", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idTipoDestino", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idRecursoDestino", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idGrupoDeMovimiento", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idTipoDeMovimiento", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idDptoJudicial", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idOrganoJudicial", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fechaDesde", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fechaHasta", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idMunicipio", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idLocalidad", mode = ParameterMode.IN, type = String.class) })
public class EstadisticaMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144766673537310149L;
	
	@Id
	private Integer id;
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
	private Date fechaMovimiento;
	private String grupoDeMovimiento;
	private String tipoDeMovimiento;
	private String tipoDeOrigen;
	private String origen;
	private String tipoDeDestino;
	private String destino;
	private String estaPresente;
	private String institucion;
	private String reiterancia;
	
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

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getGrupoDeMovimiento() {
		return grupoDeMovimiento;
	}

	public void setGrupoDeMovimiento(String grupoDeMovimiento) {
		this.grupoDeMovimiento = grupoDeMovimiento;
	}

	public String getTipoDeMovimiento() {
		return tipoDeMovimiento;
	}

	public void setTipoDeMovimiento(String tipoDeMovimiento) {
		this.tipoDeMovimiento = tipoDeMovimiento;
	}

	public String getTipoDeOrigen() {
		return tipoDeOrigen;
	}

	public void setTipoDeOrigen(String tipoDeOrigen) {
		this.tipoDeOrigen = tipoDeOrigen;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getTipoDeDestino() {
		return tipoDeDestino;
	}

	public void setTipoDeDestino(String tipoDeDestino) {
		this.tipoDeDestino = tipoDeDestino;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getEstaPresente() {
		return estaPresente;
	}

	public void setEstaPresente(String estaPresente) {
		this.estaPresente = estaPresente;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getReiterancia() {
		return reiterancia;
	}

	public void setReiterancia(String reiterancia) {
		this.reiterancia = reiterancia;
	}
}

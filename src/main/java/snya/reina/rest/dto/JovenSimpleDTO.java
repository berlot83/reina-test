package snya.reina.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.Provincia;
import snya.general.modelo.TipoDeDocumento;
import snya.reina.modelo.joven.Joven;

/* Esta clase sólo se usa como capa al exterior para recibir objetos DataTransferObject muestra/toma algunos datos del Object original */
public class JovenSimpleDTO {

	private Integer id;
	private Integer idJoven;
	private String apellidoYNombre;
	private String apellidoMaterno;
	private String apellidos;
	private String nombres;
	private boolean tieneDocumento;
	private TipoDeDocumento tipoDeDocumento;
	private String numeroDocumento;
	private String situacionDocumentacion;
	private String sexo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fechaNacimiento;
	private Integer edad;
	private Nacionalidad nacionalidad;
	private String cuil;
	private Provincia provinciaNacimiento;
	private Municipio municipioNacimiento;
	private Localidad localidadNacimiento;
	private String lugarDeNacimiento;
	private String domicilio;
	private String referentes;
	private String historicidad;
	
	public JovenSimpleDTO() {

	}

	/*
	 * Sólo en el caso de no usar Stored Procedures, se busca la entidad Joven y
	 * luego se pasa como parámetro resumiendo los atributos
	 */
	public JovenSimpleDTO(Joven joven) {

		this.idJoven = joven.getId();
		this.apellidoYNombre = joven.getApellidos() + " " + joven.getNombres();
		this.apellidoMaterno = joven.getApellidoMaterno();
		this.nombres = joven.getNombres();
		this.apellidos = joven.getApellidos();
		this.tieneDocumento = joven.isTieneDocumento();
		this.tipoDeDocumento = joven.getTipoDeDocumento();
		this.numeroDocumento = joven.getNumeroDocumento();
//		this.situacionDocumentacion = joven.getSituacionesTramiteDocumento().isEmpty() ? "Sin situación al momento": joven.getSituacionesTramiteDocumento().get(0).traerDetalle();
		this.sexo = joven.getSexo();
		this.fechaNacimiento = joven.getFechaNacimiento();
		this.edad = joven.getEdad();
		this.nacionalidad = joven.getNacionalidad();
		this.cuil = joven.getCuil();
		this.lugarDeNacimiento = joven.getDetalleLugarDeNacimiento();
		this.domicilio = joven.getDomicilio() == null ? null : joven.getDomicilio().getDetalle();
//		this.referentes = joven.getDetalleReferentes() == null ? null : joven.getDetalleReferentes();
		this.historicidad = joven.getDetalleHistoricidad()  == null ? null : joven.getDetalleHistoricidad();
		this.provinciaNacimiento = joven.getProvincia();
		this.municipioNacimiento = joven.getMunicipio();
		this.localidadNacimiento = joven.getLocalidad();
	}

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

	public String getApellidoYNombre() {
		return apellidoYNombre;
	}

	public void setApellidoYNombre(String apellidoYNombre) {
		this.apellidoYNombre = apellidoYNombre;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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

	public boolean isTieneDocumento() {
		return tieneDocumento;
	}

	public void setTieneDocumento(boolean tieneDocumento) {
		this.tieneDocumento = tieneDocumento;
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSituacionDocumentacion() {
		return situacionDocumentacion;
	}

	public void setSituacionDocumentacion(String situacionDocumentacion) {
		this.situacionDocumentacion = situacionDocumentacion;
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

	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public Provincia getProvinciaNacimiento() {
		return provinciaNacimiento;
	}

	public void setProvinciaNacimiento(Provincia provinciaNacimiento) {
		this.provinciaNacimiento = provinciaNacimiento;
	}

	public Municipio getMunicipioNacimiento() {
		return municipioNacimiento;
	}

	public void setMunicipioNacimiento(Municipio municipioNacimiento) {
		this.municipioNacimiento = municipioNacimiento;
	}

	public Localidad getLocalidadNacimiento() {
		return localidadNacimiento;
	}

	public void setLocalidadNacimiento(Localidad localidadNacimiento) {
		this.localidadNacimiento = localidadNacimiento;
	}

	public String getLugarDeNacimiento() {
		return lugarDeNacimiento;
	}

	public void setLugarDeNacimiento(String lugarDeNacimiento) {
		this.lugarDeNacimiento = lugarDeNacimiento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

//	public String getReferentes() {
//		return referentes;
//	}
//
//	public void setReferentes(String referentes) {
//		this.referentes = referentes;
//	}
//
	public String getHistoricidad() {
		return historicidad;
	}

	public void setHistoricidad(String historicidad) {
		this.historicidad = historicidad;
	}
}

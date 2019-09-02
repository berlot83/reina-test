package snya.reina.rest.dto;

import java.util.Date;
import java.util.List;
import snya.general.modelo.Domicilio;
import snya.general.modelo.EstadoCivil;
import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.Provincia;
import snya.general.modelo.TipoDeDocumento;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Expediente;
import snya.reina.modelo.joven.SituacionTramite;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.referente.Familiar;

/* Capa de comunicación con el exterior para transferir o recibir datos
 * 
 *  Cuando llega un PUT/ POST/ PATCH desde Reuna este objeto recibe todos los datos y luego internamente
 *  a través de un constructor puede dar forma, modificando lo necesario previas validaciones.
 *  
 *  Puede usarse para matchear datos incluso.
 *  
 *  EL JSON DEBE TRAER TODOS ESTOS OBJETOS AUNQUE VENGAN CON VALORES VACÍOS O NULL
 *  
 *  Esta clase está en desarrollo.
 *  
 *  */
public class JovenDTO {

	private Integer id;
	private String apellidoYNombre;
	private String nombres;
	private String apellidoMaterno;
	private String apellidos;
	private boolean tieneDocumento;
	private TipoDeDocumento tipoDeDocumento;
	private String numeroDocumento;
	private String sexo;
	private Date fechaNacimiento;
	private String cuil;
	private Integer edad;
	private Provincia provincia;
	private Municipio municipio;
	private Localidad localidad;
	private Domicilio domicilio;
	private EstadoCivil estadoCivil;
	private Nacionalidad nacionalidad;
	private SituacionTramite ultimaSituacionesTramiteDocumento;
	private Expediente expedienteIdentificador;
	private List<Familiar> familiares;
	private List<Informe> informes;
	private List<Permanencia> permanencias;
	private List<Expediente> expedientes;
	private List<SituacionTramite> situacionesTramiteDocumento;
	private List<Intervencion> intervenciones;
	private List<ProcesoPenal> procesos;

	public JovenDTO(Integer id, String apellidoYNombre, String nombres, String apellidoMaterno, String apellidos,
			boolean tieneDocumento, TipoDeDocumento tipoDeDocumento, String numeroDocumento, String sexo,
			Date fechaNacimiento, String cuil, Integer edad, Provincia provincia, Municipio municipio,
			Localidad localidad, Domicilio domicilio, EstadoCivil estadoCivil, Nacionalidad nacionalidad,
			SituacionTramite ultimaSituacionesTramiteDocumento, Expediente expedienteIdentificador,
			List<Familiar> familiares, List<Informe> informes, List<Permanencia> permanencias,
			List<Expediente> expedientes, List<SituacionTramite> situacionesTramiteDocumento,
			List<Intervencion> intervenciones, List<ProcesoPenal> procesos) {
		super();
		this.id = id;
		this.apellidoYNombre = apellidoYNombre;
		this.nombres = nombres;
		this.apellidoMaterno = apellidoMaterno;
		this.apellidos = apellidos;
		this.tieneDocumento = tieneDocumento;
		this.tipoDeDocumento = tipoDeDocumento;
		this.numeroDocumento = numeroDocumento;
		this.sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
		this.cuil = cuil;
		this.edad = edad;
		this.provincia = provincia;
		this.municipio = municipio;
		this.localidad = localidad;
		this.domicilio = domicilio;
		this.estadoCivil = estadoCivil;
		this.nacionalidad = nacionalidad;
		this.ultimaSituacionesTramiteDocumento = ultimaSituacionesTramiteDocumento;
		this.expedienteIdentificador = expedienteIdentificador;
		this.familiares = familiares;
		this.informes = informes;
		this.permanencias = permanencias;
		this.expedientes = expedientes;
		this.situacionesTramiteDocumento = situacionesTramiteDocumento;
		this.intervenciones = intervenciones;
		this.procesos = procesos;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApellidoYNombre() {
		return apellidoYNombre;
	}

	public void setApellidoYNombre(String apellidoYNombre) {
		this.apellidoYNombre = apellidoYNombre;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
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

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public SituacionTramite getUltimaSituacionesTramiteDocumento() {
		return ultimaSituacionesTramiteDocumento;
	}

	public void setUltimaSituacionesTramiteDocumento(SituacionTramite ultimaSituacionesTramiteDocumento) {
		this.ultimaSituacionesTramiteDocumento = ultimaSituacionesTramiteDocumento;
	}

	public Expediente getExpedienteIdentificador() {
		return expedienteIdentificador;
	}

	public void setExpedienteIdentificador(Expediente expedienteIdentificador) {
		this.expedienteIdentificador = expedienteIdentificador;
	}

	public List<Familiar> getFamiliares() {
		return familiares;
	}

	public void setFamiliares(List<Familiar> familiares) {
		this.familiares = familiares;
	}

	public List<Informe> getInformes() {
		return informes;
	}

	public void setInformes(List<Informe> informes) {
		this.informes = informes;
	}

	public List<Permanencia> getPermanencias() {
		return permanencias;
	}

	public void setPermanencias(List<Permanencia> permanencias) {
		this.permanencias = permanencias;
	}

	public List<Expediente> getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public List<SituacionTramite> getSituacionesTramiteDocumento() {
		return situacionesTramiteDocumento;
	}

	public void setSituacionesTramiteDocumento(List<SituacionTramite> situacionesTramiteDocumento) {
		this.situacionesTramiteDocumento = situacionesTramiteDocumento;
	}

	public List<Intervencion> getIntervenciones() {
		return intervenciones;
	}

	public void setIntervenciones(List<Intervencion> intervenciones) {
		this.intervenciones = intervenciones;
	}

	public List<ProcesoPenal> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<ProcesoPenal> procesos) {
		this.procesos = procesos;
	}

}

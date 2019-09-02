package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ParteIntervencionPenal", procedureName = "SistemasSNYA.Reina_ParteIntervencionPenal", resultClasses = snya.reina.modelo.JovenParteIntervencionPenal.class, parameters = {
		@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "mes", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "anio", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "situacion", mode = ParameterMode.IN, type = Integer.class) })
public class JovenParteIntervencionPenal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4454482838248762246L;
	
	@Id
	private Integer idIntervencionPenal;
	private Integer idJoven;
	private String apellidos;
	private String nombres;
	private String sexo;
	private Date fechaNacimiento;
	private Integer edad;
	private String documento;

	private String juzgado;
	private String nrocausa;
	private String caratula;
	private Date fechaMedida;
	private String tipoDeSituacionProcesal;	
	private String medidaImpuesta;
	private String detalleMedidaImpuesta;

	private Date fechaIntervencion;
	private String referenteAdulto;
	private String domicilio;
	private String localidadDomicilio;
	private String municipioDomiclio;

	private String Observacion;
	private Date FechaCese;
	private String motivoCese;
	private String institucion;

	public Integer getIdIntervencionPenal() {
		return idIntervencionPenal;
	}

	public void setIdIntervencionPenal(Integer idIntervencion) {
		this.idIntervencionPenal = idIntervencion;
	}

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

	public String getNrocausa() {
		return nrocausa;
	}

	public void setNrocausa(String nrocausa) {
		this.nrocausa = nrocausa;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public Date getFechaMedida() {
		return fechaMedida;
	}

	public void setFechaMedida(Date fechaMedida) {
		this.fechaMedida = fechaMedida;
	}

	public String getTipoDeSituacionProcesal() {
		return tipoDeSituacionProcesal;
	}

	public void setTipoDeSituacionProcesal(String tiposDeSituacionProcesal) {
		this.tipoDeSituacionProcesal = tiposDeSituacionProcesal;
	}

	public String getMedidaImpuesta() {
		return medidaImpuesta;
	}

	public void setMedidaImpuesta(String medidaImpuesta) {
		this.medidaImpuesta = medidaImpuesta;
	}

	public String getDetalleMedidaImpuesta() {
		return detalleMedidaImpuesta;
	}

	public void setDetalleMedidaImpuesta(String detalleMedidaImpuesta) {
		this.detalleMedidaImpuesta = detalleMedidaImpuesta;
	}

	public Date getFechaIntervencion() {
		return fechaIntervencion;
	}

	public void setFechaIntervencion(Date fechaIntervencion) {
		this.fechaIntervencion = fechaIntervencion;
	}

	public String getReferenteAdulto() {
		return referenteAdulto;
	}

	public void setReferenteAdulto(String referenteAdulto) {
		this.referenteAdulto = referenteAdulto;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
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

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public Date getFechaCese() {
		return FechaCese;
	}

	public void setFechaCese(Date fechaCese) {
		FechaCese = fechaCese;
	}

	public String getMotivoCese() {
		return motivoCese;
	}

	public void setMotivoCese(String motivoCese) {
		this.motivoCese = motivoCese;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
}
package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaJovenSituacionIrregular", procedureName = "SistemasSNYA.Reina_TraerJovenSituacionIrregular", resultClasses = snya.reina.modelo.JovenSituacionIrregular.class, parameters = {
		@StoredProcedureParameter(name = "tipos", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "recursos", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "propiedad", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) })
public class JovenSituacionIrregular implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6033070069725712632L;
	
	@Id
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String sexo;
	private Date fechaNac;
	private Integer edad;
	private String documento;
	private String situacionDocumento;
	private String actualizado;
	private String conPartida;
	private String observacion;
	
	private String huella;
	private String localidadNacimiento;
	private String municipioNacimiento;
	private String documentoPadre;
	private String nombrePadre;
	private String documentoMadre;
	private String nombreMadre;
	private String departamentoJudicial;
	private String localidadDomicilio;
	private String municipioDomicilio;
	private Integer idTipoSituacionTramiteDNI;
	private Date fecha;
	private String estado;
	private String nroestado;
	private String institucion;

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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getSituacionDocumento() {
		return situacionDocumento;
	}

	public void setSituacionDocumento(String situacionDocumento) {
		this.situacionDocumento = situacionDocumento;
	}

	public String getActualizado() {
		return actualizado;
	}

	public void setActualizado(String actualizado) {
		this.actualizado = actualizado;
	}

	public String getConPartida() {
		return conPartida;
	}

	public void setConPartida(String conPartida) {
		this.conPartida = conPartida;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getHuella() {
		return huella;
	}

	public void setHuella(String huella) {
		this.huella = huella;
	}

	public String getLocalidadNacimiento() {
		return localidadNacimiento;
	}

	public void setLocalidadNacimiento(String localidadNacimiento) {
		this.localidadNacimiento = localidadNacimiento;
	}

	public String getMunicipioNacimiento() {
		return municipioNacimiento;
	}

	public void setMunicipioNacimiento(String municipioNacimiento) {
		this.municipioNacimiento = municipioNacimiento;
	}

	public String getDocumentoPadre() {
		return documentoPadre;
	}

	public void setDocumentoPadre(String documentoPadre) {
		this.documentoPadre = documentoPadre;
	}

	public String getNombrePadre() {
		return nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public String getDocumentoMadre() {
		return documentoMadre;
	}

	public void setDocumentoMadre(String documentoMadre) {
		this.documentoMadre = documentoMadre;
	}

	public String getNombreMadre() {
		return nombreMadre;
	}

	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}

	public String getDepartamentoJudicial() {
		return departamentoJudicial;
	}

	public void setDepartamentoJudicial(String departamentoJudicial) {
		this.departamentoJudicial = departamentoJudicial;
	}

	public String getLocalidadDomicilio() {
		return localidadDomicilio;
	}

	public void setLocalidadDomicilio(String localidadDomicilio) {
		this.localidadDomicilio = localidadDomicilio;
	}

	public String getMunicipioDomicilio() {
		return municipioDomicilio;
	}

	public void setMunicipioDomicilio(String municipioDomicilio) {
		this.municipioDomicilio = municipioDomicilio;
	}

	public Integer getIdTipoSituacionTramiteDNI() {
		return idTipoSituacionTramiteDNI;
	}

	public void setIdTipoSituacionTramiteDNI(Integer idTipoSituacionTramiteDNI) {
		this.idTipoSituacionTramiteDNI = idTipoSituacionTramiteDNI;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNroestado() {
		return nroestado;
	}

	public void setNroestado(String nroestado) {
		this.nroestado = nroestado;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
}

package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

import com.fasterxml.jackson.annotation.JsonFormat;

import snya.reina.modelo.joven.Joven;

import javax.persistence.ParameterMode;

@Entity
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "ConsultaReporteJoven", procedureName = "SistemasSNYA.Reina_TraerJovenReporte", resultClasses = snya.reina.modelo.JovenSimplificado.class, parameters = {
				@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "apellidos", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nombres", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nombreCompleto", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "documento", mode = ParameterMode.IN, type = String.class),

				@StoredProcedureParameter(name = "expediente", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nroJudicial", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "dptoJudicial", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idOrganoJudicial", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idDefensoria", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idFiscalia", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nroPaquete", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "fechaPaquete", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "restriccionEdad", mode = ParameterMode.IN, type = Boolean.class),
				@StoredProcedureParameter(name = "propiedad", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) }),

		@NamedStoredProcedureQuery(name = "ConsultaBusquedaJoven", procedureName = "SistemasSNYA.Reina_TraerJovenCombinado", resultClasses = snya.reina.modelo.JovenSimplificado.class, parameters = {
				@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "apellidos", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nombres", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nombreCompleto", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "documento", mode = ParameterMode.IN, type = String.class),

				@StoredProcedureParameter(name = "expediente", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nroJudicial", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "dptoJudicial", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idOrganoJudicial", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idDefensoria", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idFiscalia", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nroPaquete", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "fechaPaquete", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "restriccionEdad", mode = ParameterMode.IN, type = Boolean.class),
				@StoredProcedureParameter(name = "propiedad", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) }),

		@NamedStoredProcedureQuery(name = "ConsultaJovenSinExpediente", procedureName = "SistemasSNYA.Reina_TraerJovenSinExpediente", resultClasses = snya.reina.modelo.JovenSimplificado.class, parameters = {
				@StoredProcedureParameter(name = "tipos", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "recursos", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "propiedad", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) }),

		@NamedStoredProcedureQuery(name = "ConsultaJovenPanelControl", procedureName = "SistemasSNYA.Reina_TraerJovenPanelControl", resultClasses = snya.reina.modelo.JovenSimplificado.class, parameters = {
				@StoredProcedureParameter(name = "fecha", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "idGrupo", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "idCategoria", mode = ParameterMode.IN, type = Integer.class) }) })
public class JovenSimplificado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -734774452636391420L;

	@Id
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String documento;
	// Agregar huella a JovenSimplificado
	private String huella;
	private String sexo;
	private Date fechaNac;
	private Integer edad;
	private String institucion;
	private String situacionProcesal;
	private String proceso;
	private String presente;

	public JovenSimplificado() {
		
	}
	
	/* Sólo en el caso de no usar Stored Procedures, se busca la entidad Joven y luego se pasa como parámetro resumiendo los atributos */
	
	public JovenSimplificado(Joven joven) {
		
		boolean condicionExpedientes = joven.getExpedientes().isEmpty() ? false : true;
		if(condicionExpedientes) {
			this.expediente = joven.getExpedientes().get(0).getDetalleEstado();
		}
		
		boolean condicionProcesos = joven.getProcesos().isEmpty() ? false : true;
		if(condicionProcesos) {
			this.expediente = joven.getProcesos().get(0).toString();
		}
		
		this.idJoven = joven.getId();
		this.apellidos = joven.getApellidos();
		this.nombres = joven.getNombres();
		this.documento = joven.getNumeroDocumento();
		this.huella = joven.getDedo();
		this.sexo = joven.getSexo();
		this.fechaNac = joven.getFechaNacimiento();
		this.edad = joven.getEdad();
	}

	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer id) {
		this.idJoven = id;
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

	public String getHuella() {
		return huella;
	}

	public void setHuella(String huella) {
		this.huella = huella;
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

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getSituacionProcesal() {
		return situacionProcesal;
	}

	public void setSituacionProcesal(String situacionProcesal) {
		this.situacionProcesal = situacionProcesal;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getPresente() {
		return presente;
	}

	public void setPresente(String presente) {
		this.presente = presente;
	}
}

package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaJovenTratamiento", procedureName = "SistemasSNYA.Reina_TraerJovenTratamiento", resultClasses = snya.reina.modelo.JovenTratamiento.class, 
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
public class JovenTratamiento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2480540716166538267L;
	
	@Id
	private Integer id;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String documento;
	private Integer edad;
	private String institucion;
	private Date fechaInicio;
	private Date fechaActualizacion;
	private String diagnostico;
	private String fase;
	private String tratamiento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
}

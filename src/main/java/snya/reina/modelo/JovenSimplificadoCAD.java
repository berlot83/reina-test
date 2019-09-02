package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQueries({
	//TODO
	//TODO Hacer El consulta reporte Joven CAD
	/*
	@NamedStoredProcedureQuery(name = "ConsultaReporteJovenCAD", procedureName = "SistemasSNYA.Reina_TraerJovenReporte", resultClasses = snya.reina.modelo.JovenSimplificadoCAD.class, 
			parameters = {
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
			@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) }),*/
	
	@NamedStoredProcedureQuery(name = "ConsultaBusquedaJovenCAD", procedureName = "SistemasSNYA.Reina_TraerJovenCAD", resultClasses = snya.reina.modelo.JovenSimplificadoCAD.class,
			parameters = {
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
			@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) })

	
	//TODO
})

public class JovenSimplificadoCAD implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3411031711992675366L;
	
	@Id
	private Integer idJoven;
	private String apellidos;
	private String nombres;
	private String documento;
	private Date fechaNacimiento;
	private Integer edad;
	private String institucion;
	private Date fechaIngreso;
	private Date fechaAprehension;

	private String motivoAprehension;
	private Boolean prorroga;
	
	private Boolean actaConjunta; 
	private Boolean salud; 
	private Boolean equipoTecnico; 

	public String getTiempoRestante () {
			return calcularTiempoRestante();
	}
	
	private String calcularTiempoRestante () {
		Date fechaActual = new Date();
		
		
		long diff = fechaActual.getTime() - this.fechaIngreso.getTime() ;
		long diffHours = diff / (60 * 60 * 1000); //diferencia en horas
		long diffMinutes = diff / (60 * 1000) ; //diferencia en minutos
		
		if (diffHours > 1) return  Math.toIntExact(diffHours) + " horas"; 
		else return  Math.toIntExact(diffMinutes) + " minutos";
		
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
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

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaAprehension() {
		return fechaAprehension;
	}

	public void setFechaAprehension(Date fechaAprehension) {
		this.fechaAprehension = fechaAprehension;
	}

	public String getMotivoAprehension() {
		return motivoAprehension;
	}

	public void setMotivoAprehension(String motivoAprehension) {
		this.motivoAprehension = motivoAprehension;
	}

	public Boolean getProrroga() {
		return prorroga;
	}

	public void setProrroga(Boolean prorroga) {
		this.prorroga = prorroga;
	}

	public Boolean getActaConjunta() {
		return actaConjunta;
	}

	public void setActaConjunta(Boolean actaConjunta) {
		this.actaConjunta = actaConjunta;
	}

	public Boolean getSalud() {
		return salud;
	}

	public void setSalud(Boolean salud) {
		this.salud = salud;
	}

	public Boolean getEquipoTecnico() {
		return equipoTecnico;
	}

	public void setEquipoTecnico(Boolean equipoTecnico) {
		this.equipoTecnico = equipoTecnico;
	}
}

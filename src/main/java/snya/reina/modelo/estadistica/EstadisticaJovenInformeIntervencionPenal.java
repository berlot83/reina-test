package snya.reina.modelo.estadistica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "InformesIntervencionPenal", procedureName = "SistemasSNYA.Reina_ParteInformes", resultClasses = snya.reina.modelo.estadistica.EstadisticaJovenInformeIntervencionPenal.class, parameters = {
		@StoredProcedureParameter(name = "idTipo", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "idRecurso", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "mes", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "anio", mode = ParameterMode.IN, type = Integer.class) })
public class EstadisticaJovenInformeIntervencionPenal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7676053420655415930L;
	
	@Id
	private Integer id;
	private Integer idJoven;
	private String apellidos;
	private String nombres;
	private String sexo;
	private Date fechaNacimiento;
	private Integer edad;
	private String documento;

	private String institucionInforme;
	private String grupoTipoInforme;
	private Integer cantidadInforme;

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

	public String getInstitucionInforme() {
		return institucionInforme;
	}

	public void setInstitucionInforme(String institucionInforme) {
		this.institucionInforme = institucionInforme;
	}

	public String getGrupoTipoInforme() {
		return grupoTipoInforme;
	}

	public void setGrupoTipoInforme(String grupoTipoInforme) {
		this.grupoTipoInforme = grupoTipoInforme;
	}

	public Integer getCantidadInforme() {
		return cantidadInforme;
	}

	public void setCantidadInforme(Integer cantidadInforme) {
		this.cantidadInforme = cantidadInforme;
	}
}

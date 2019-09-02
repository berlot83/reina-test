package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(
		name = "ConsultaPanelControl",
		procedureName = "SistemasSNYA.Reina_TraerPanelControl", 
		resultClasses = snya.reina.modelo.ElementoPanelControl.class,
		parameters = { 
			@StoredProcedureParameter(name="fecha", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="tipos", mode= ParameterMode.IN, type= String.class),
			@StoredProcedureParameter(name="recursos", mode= ParameterMode.IN, type= String.class)
		}
	)
public class ElementoPanelControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7599443772693469099L;
	
	@Id
	private Integer id;
	private Date fecha;
	private Integer idRecurso;
	private String institucion;
	private Integer idGrupo;
	private Integer idCategoria;
	private String categoria;
	private Integer cantidad;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}
}

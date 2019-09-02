package snya.reina.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import snya.reina.modelo.institucion.TipoDeInstitucion;

@Entity
public class ResultadoConsultaInstitucion implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6819397976615354223L;
	
	@Id
	private Integer id;
	private String nombre;
	private String nombreCorto;
	@ManyToOne
	private TipoDeInstitucion tipoDeInstitucion;
	private String tipo;
	private Boolean estaActivo;

	
	public ResultadoConsultaInstitucion() {
		
	}
		
	public ResultadoConsultaInstitucion(Integer id, String nombre,
			String nombreCorto, TipoDeInstitucion tipoDeInstitucion, String tipo, boolean estaActivo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.tipoDeInstitucion = tipoDeInstitucion;
		this.tipo = tipo;
		this.setEstaActivo(estaActivo);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public TipoDeInstitucion getTipoDeInstitucion() {
		return tipoDeInstitucion;
	}

	public void setTipoDeInstitucion(TipoDeInstitucion tipoDeInstitucion) {
		this.tipoDeInstitucion = tipoDeInstitucion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

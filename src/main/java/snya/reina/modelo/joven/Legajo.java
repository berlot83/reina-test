package snya.reina.modelo.joven;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Legajo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3218778595138634423L;

	@Column(name="FechaLegajo")
	private Date fecha;
	
	@Column(name="EstaEnRegistroLegajo")
	private Boolean estaEnRegistro;
	
	@Column(name="ObservacionLegajo")
	private String observacion;


	
	public Legajo() {
		this.fecha = new Date();
		this.estaEnRegistro = false;
		this.observacion = "";		
	}
	
	public Legajo(Date fecha) {
		this();
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getEstaEnRegistro() {
		return estaEnRegistro;
	}

	public void setEstaEnRegistro(Boolean estaEnRegistro) {
		this.estaEnRegistro = estaEnRegistro;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}

package snya.reina.modelo.habeas;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.joven.Joven;

@Embeddable
public class DestinatarioHabeas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2521537448472911260L;
	
	@ManyToOne
	@JoinColumn(name="IdDestinatarioJoven")
	private Joven joven;
	@ManyToOne
	@JoinColumn(name="IdDestinatarioRecurso")
	private InstitucionCumplimiento institucion;
	
	
	public DestinatarioHabeas() {
		this.joven = null;
		this.institucion = null;
	}
	
	public DestinatarioHabeas(Joven joven) {
		this.joven = joven;
		this.institucion = null;
	}

	public DestinatarioHabeas(InstitucionCumplimiento institucion) {
		this.joven = null;
		this.institucion = institucion;
	}


	public String getDescripcion() {
		if (this.getJoven() != null)
			return this.getJoven().getDescripcion();
		
		if (this.getInstitucion() != null)
			return this.getInstitucion().getDescripcion();
		
		return "";
	}


	public Joven getJoven() {
		return joven;
	}


	public void setJoven(Joven joven) {
		this.joven = joven;
	}


	public InstitucionCumplimiento getInstitucion() {
		return institucion;
	}


	public void setInstitucion(InstitucionCumplimiento institucion) {
		this.institucion = institucion;
	}
}

package snya.reina.modelo.institucion;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.institucion.Institucion;

@Entity
@DiscriminatorValue("5")
@Audited
public class Dependencia extends Institucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269165218487103244L;

	public Dependencia() {
		
	}
	
	public Dependencia(String nombre, String nombreCorto,
			TipoDeInstitucion tipo, Institucion padre, String observacion) {
		super(nombre, nombreCorto, tipo, padre, observacion);
	}

	@Override
	public boolean seCumplePermanencia() {
		return false;
	}
}

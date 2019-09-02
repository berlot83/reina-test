package snya.reina.modelo.institucion;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

import org.hibernate.envers.Audited;

import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.institucion.Institucion;

@Entity
@SecondaryTable(catalog="SistemasSNYA", name = "Entidades_CentroDeSalud", pkJoinColumns = @PrimaryKeyJoinColumn(name = "IdInstitucion"))
@DiscriminatorValue("14")
@Audited
public class CentroDeSalud extends Institucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 614129845093705647L;


	public CentroDeSalud() {
		
	}
	
	public CentroDeSalud(String nombre, String nombreCorto,
			TipoDeInstitucion tipo, Institucion padre, String observacion) {
		super(nombre, nombreCorto, tipo, padre, observacion);
	}

	
	@Override
	public boolean seCumplePermanencia() {
		return true;
	}
}

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
@SecondaryTable(catalog="SistemasSNYA", name = "Entidades_UnidadPenitenciaria", pkJoinColumns = @PrimaryKeyJoinColumn(name = "IdInstitucion"))
@DiscriminatorValue("15")
@Audited
public class UnidadPenitenciaria extends Institucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4463617063905166725L;

	public UnidadPenitenciaria() {
		
	}
	
	public UnidadPenitenciaria(String nombre, String nombreCorto,
			TipoDeInstitucion tipo, Institucion padre, String observacion) {
		super(nombre, nombreCorto, tipo, padre, observacion);
	}

	@Override
	public boolean seCumplePermanencia() {
		return true;
	}
}

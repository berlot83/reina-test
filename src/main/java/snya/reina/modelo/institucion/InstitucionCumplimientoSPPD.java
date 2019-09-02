package snya.reina.modelo.institucion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

import org.hibernate.envers.Audited;

@Entity
@SecondaryTable(
	    name="Entidades_InstitucionCumplimientoSPPD", catalog="SistemasSNYA",
	    pkJoinColumns=@PrimaryKeyJoinColumn(name="IdInstitucion")
	)
@DiscriminatorValue("17")
@Audited
public class InstitucionCumplimientoSPPD extends Institucion implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 3564940042813231180L;

	@Column(name="Sexo", table="Entidades_InstitucionCumplimientoSPPD")
	private String sexo;
	
	@Column(name="EdadMinima", table="Entidades_InstitucionCumplimientoSPPD")
	private Integer edadMinima;
	
	@Column(name="EdadMaxima", table="Entidades_InstitucionCumplimientoSPPD")
	private Integer edadMaxima;
	
	
	public InstitucionCumplimientoSPPD() {
		
	}
	
	public InstitucionCumplimientoSPPD(String nombre, String nombreCorto,
			TipoDeInstitucion tipo, Institucion padre, String observacion, String sexo,
			Integer edadMinima, Integer edadMaxima) {
		super(nombre, nombreCorto, tipo, padre, observacion);
		this.sexo = sexo;
		this.edadMinima = edadMinima;
		this.edadMaxima = edadMaxima;
	}

	public boolean seCumplePermanencia() {
		return true;
	}
	
	public String getDescripcion() {
		return this.getNombre();
	}
	
	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(Integer edadMinima) {
		this.edadMinima = edadMinima;
	}

	public Integer getEdadMaxima() {
		return edadMaxima;
	}

	public void setEdadMaxima(Integer edadMaxima) {
		this.edadMaxima = edadMaxima;
	}
}

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
	    name="Reina_InstitucionCumplimiento", catalog="SistemasSNYA",
	    pkJoinColumns=@PrimaryKeyJoinColumn(name="IdInstitucion")
	)
@DiscriminatorValue("11")
@Audited
public class InstitucionCumplimiento extends Institucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2971139623161273201L;
	
	private static final int CENTRO_CONTENCION = 11;
	private static final int CENTRO_RECEPCION = 12;
	private static final int CENTRO_CERRADO = 13;
	
	
	@Column(name="Sexo", table="Reina_InstitucionCumplimiento")
	private String sexo;
	
	@Column(name="EdadMinima", table="Reina_InstitucionCumplimiento")
	private Integer edadMinima;
	
	@Column(name="EdadMaxima", table="Reina_InstitucionCumplimiento")
	private Integer edadMaxima;
	
	
	public InstitucionCumplimiento() {
		
	}
	
	public InstitucionCumplimiento(String nombre, String nombreCorto,
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

	public boolean esCentroContencion() {
		return this.getTipoDeInstitucion().getId() == CENTRO_CONTENCION;
	}

	public boolean esCentroRecepcion() {
		return this.getTipoDeInstitucion().getId() == CENTRO_RECEPCION;
	}

	public boolean esCentroCerrado() {
		return this.getTipoDeInstitucion().getId() == CENTRO_CERRADO;
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

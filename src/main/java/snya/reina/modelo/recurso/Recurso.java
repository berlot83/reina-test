package snya.reina.modelo.recurso;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.institucion.CentroDeReferencia;
import snya.reina.modelo.institucion.CentroDeSalud;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.institucion.InstitucionCumplimientoSPPD;
import snya.reina.modelo.institucion.UnidadPenitenciaria;

@Entity
@Table(catalog="SistemasSNYA", name="Entidades_Recurso")
public class Recurso implements AmbitoEjecucion, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9206824478067536932L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="IdRecurso")
	private Integer id;
    @Any(optional=false, fetch=FetchType.EAGER, metaColumn=@Column(name="IdTipoDeComponente"))
    @AnyMetaDef(idType="int", metaType="int",
    	metaValues = {
    		@MetaValue(targetEntity = CentroDeReferencia.class, value = "1"),
    		@MetaValue(targetEntity = InstitucionCumplimiento.class, value = "2"),
    		@MetaValue(targetEntity = CentroDeSalud.class, value = "3"),
    		@MetaValue(targetEntity = UnidadPenitenciaria.class, value = "4"),
    		@MetaValue(targetEntity = ProgramaSimplificado.class, value = "5"),
    		@MetaValue(targetEntity = InstitucionCumplimientoSPPD.class, value = "6")
		})
    @JoinColumn(name="IdComponente")
	private ComponeRecurso componente;

	public String traerNombre() {
		return this.getComponente().getNombre();
	}
	
	public String getNombreCompleto() {
		return this.getComponente().getNombre();
	}

	public boolean seCumplePermanencia() {
		return this.componente.seCumplePermanencia();
	}
	
	public 	boolean es(ComponeRecurso componente) {
		return this.getComponente().equals(componente);
	}
	
	public boolean estaDentroDelTipo(Integer[] tipos) {
		return this.getComponente().estaDentroDelTipo(tipos);
	}
	
	public 	boolean esInstitucional() {
		return true;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ComponeRecurso getComponente() {
		return componente;
	}

	public void setComponente(ComponeRecurso componente) {
		this.componente = componente;
	}
}

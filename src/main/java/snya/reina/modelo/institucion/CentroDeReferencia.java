package snya.reina.modelo.institucion;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.general.modelo.DepartamentoJudicial;
import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.institucion.Institucion;

@Entity
@SecondaryTable(catalog="SistemasSNYA", name = "Reina_CentroDeReferencia", pkJoinColumns = @PrimaryKeyJoinColumn(name = "IdInstitucion"))
@DiscriminatorValue("10")
@Audited
public class CentroDeReferencia extends Institucion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 646705842178066235L;
	
	@ManyToOne
	@JoinColumn(name="IdDepartamentoJudicial", table="Reina_CentroDeReferencia")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private DepartamentoJudicial departamentoJudicial;
	
	public CentroDeReferencia(){
		
	}
	
	public CentroDeReferencia(String nombre, String nombreCorto,
			TipoDeInstitucion tipo, Institucion padre, String observacion,
			DepartamentoJudicial departamento) {
		super(nombre, nombreCorto, tipo, padre, observacion);
		this.departamentoJudicial = departamento;
	}

	public boolean seCumplePermanencia() {
		return false;
	}
		
	public DepartamentoJudicial getDepartamentoJudicial() {
		return departamentoJudicial;
	}
	
	public void setDepartamentoJudicial(DepartamentoJudicial departamentoJudicial) {
		this.departamentoJudicial = departamentoJudicial;
	}
}

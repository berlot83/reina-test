package snya.reina.modelo.proceso;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Table(name="Reina_TipoDetalleDeMedidaEnMedida", catalog="SistemasSNYA")
@Audited
public class DetalleMedidaEnProcesoPenal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8012366602164702835L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdDetalleMedidaEnProcesoPenal")
	private Integer id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdTipoDetalleDeMedidaEnProceso")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeDetalleDeMedidaEnProcesoPenal tipo;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdMedidaEnProcesoPenal")
	private MedidaEnProcesoPenal medida;
	
	public DetalleMedidaEnProcesoPenal(){
	}
	
	public DetalleMedidaEnProcesoPenal(TipoDeDetalleDeMedidaEnProcesoPenal tipo, MedidaEnProcesoPenal medida){
		this.setTipo(tipo);
		this.setMedida(medida);
	}
	
	public Integer getIdTipo(){
		return this.getTipo().getId();
	}

	public String getNombre(){
		return this.getTipo().getNombre();
	}
	
	public String getNombreCorto(){
		return this.getTipo().getNombreCorto();
	}		
	
	public boolean getEstaActivo(){
		return this.getTipo().isEstaActivo();
	}				
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public TipoDeDetalleDeMedidaEnProcesoPenal getTipo() {
		return tipo;
	}
	public void setTipo(TipoDeDetalleDeMedidaEnProcesoPenal tipo) {
		this.tipo = tipo;
	}
	public MedidaEnProcesoPenal getMedida() {
		return medida;
	}
	public void setMedida(MedidaEnProcesoPenal medida) {
		this.medida = medida;
	}
}

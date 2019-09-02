package snya.reina.modelo.proceso;

import java.io.Serializable;
import java.util.Date;

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


import snya.reina.modelo.institucion.OrganoJudicial;

@Entity
@Table(name="Reina_MotivoIntervencion", catalog="SistemasSNYA")
@Audited
public class MotivoIntervencionEnProcesoPenal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9069916972836037855L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMotivoIntervencion")
	private Integer id;
	
	@Column(name="FechaMotivo")
	private java.util.Date fechaMotivo;
	
	@ManyToOne
	@JoinColumn(name="IdOrganoJudicial")
	private OrganoJudicial organoJudicial;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeMotivoIntervencion")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeMotivoIntervencion tipo;
	
	@Column(name="GradoTentativa")
	private Boolean gradoTentativa;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdProcesoPenal")	
	private ProcesoPenal proceso;

	public MotivoIntervencionEnProcesoPenal() {
		super();
	}

	public MotivoIntervencionEnProcesoPenal(Date fecha,
			OrganoJudicial organoJudicial, TipoDeMotivoIntervencion tipo,
			Boolean gradoTentativa, String observacion) {
		super();
		this.fechaMotivo = fecha;
		this.organoJudicial = organoJudicial;
		this.tipo = tipo;
		this.gradoTentativa = gradoTentativa;
		this.observacion = observacion;
	}

	public String getTextoTipoMotivo() {
		return this.getTipo().getNombre() + ((this.getGradoTentativa()) ? " (Tentativa)" : "");
	}
	
	/* === Propiedades === */	
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public java.util.Date getFechaMotivo() {
		return fechaMotivo;
	}

	public void setFechaMotivo(java.util.Date value) {
		fechaMotivo = value;
	}

	public TipoDeMotivoIntervencion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMotivoIntervencion value) {
		tipo = value;
	}

	public Boolean getGradoTentativa() {
		return gradoTentativa;
	}

	public void setGradoTentativa(Boolean gradoTentativa) {
		this.gradoTentativa = gradoTentativa;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String value) {
		observacion = value;
	}

	public ProcesoPenal getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoPenal proceso) {
		this.proceso = proceso;
	}

	public OrganoJudicial getOrganoJudicial() {
		return organoJudicial;
	}

	public void setOrganoJudicial(OrganoJudicial organoJudicial) {
		this.organoJudicial = organoJudicial;
	}
}
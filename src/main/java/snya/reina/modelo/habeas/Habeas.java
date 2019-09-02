package snya.reina.modelo.habeas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.general.modelo.DepartamentoJudicial;
import snya.reina.modelo.institucion.OrganoJudicial;

@Entity
@Table(name="Reina_Habeas", catalog="SistemasSNYA")
@Audited
public class Habeas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4355767447465019081L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdHabeas")
	private Integer id;
	
	@Column(name="FechaInicio")
	private Date fechaInicio;
	 
	@Embedded
	private DestinatarioHabeas destinatario;
	 
	@ManyToOne(optional=false)
	@JoinColumn(name="IdOrganoJudicial")
	private OrganoJudicial organoJudicial;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdPromotorHabeas")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PromotorHabeas promotor; 
	
	@ManyToOne
	@JoinColumn(name="IdDepartamentoJudicialPromotor")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private DepartamentoJudicial departamentoJudicialPromotor; 
	
	@Column(name="OtroOrganismoPromotor")
	private String organismoPromotor;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="habeas")
	private List<NotaHabeas> notas;

	@Column(name="Observacion")
	private String observacion;
	
	@Column(name="FechaFin")
	private Date fechaFin;

	
	public Habeas() {
		this.setNotas(new ArrayList<NotaHabeas>());
	}
			
	public Habeas(Date fechaInicio, OrganoJudicial organoJudicial,
			DestinatarioHabeas destinatario, PromotorHabeas promotor, DepartamentoJudicial departamentoJudicialPromotor, String organismoPromotor, String observacion) {
		this.fechaInicio = fechaInicio;
		this.organoJudicial = organoJudicial;
		this.destinatario = destinatario;
		this.observacion = observacion;
		this.promotor = promotor;
		this.departamentoJudicialPromotor = departamentoJudicialPromotor;
		this.organismoPromotor = organismoPromotor;
		this.fechaFin = null;
		this.setNotas(new ArrayList<NotaHabeas>());
	}

	public String traerDescripcionDestinatario() {
		return (this.getDestinatario() == null) ? "Colectivo" : this.getDestinatario().getDescripcion();
	}
	
	public String traerDescripcionOrganoPromotor() {
		return (this.getDepartamentoJudicialPromotor() != null) 
				? this.getPromotor().getNombre() + " - " + this.getDepartamentoJudicialPromotor().getNombre()
				: this.getOrganismoPromotor();
	}
	
	public void agregarNota(NotaHabeas nota) {
		this.getNotas().add(nota);
		nota.setHabeas(this);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public DestinatarioHabeas getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(DestinatarioHabeas destinatario) {
		this.destinatario = destinatario;
	}

	public OrganoJudicial getOrganoJudicial() {
		return organoJudicial;
	}

	public void setOrganoJudicial(OrganoJudicial organoJudicial) {
		this.organoJudicial = organoJudicial;
	}

	public PromotorHabeas getPromotor() {
		return promotor;
	}

	public void setPromotor(PromotorHabeas promotor) {
		this.promotor = promotor;
	}

	public DepartamentoJudicial getDepartamentoJudicialPromotor() {
		return departamentoJudicialPromotor;
	}

	public void setDepartamentoJudicialPromotor(
			DepartamentoJudicial departamentoJudicialPromotor) {
		this.departamentoJudicialPromotor = departamentoJudicialPromotor;
	}

	public String getOrganismoPromotor() {
		return organismoPromotor;
	}

	public void setOrganismoPromotor(String organismoPromotor) {
		this.organismoPromotor = organismoPromotor;
	}

	public List<NotaHabeas> getNotas() {
		return notas;
	}

	public void setNotas(List<NotaHabeas> notas) {
		this.notas = notas;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
}

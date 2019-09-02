package snya.reina.modelo.salud;

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

@Entity
@Table(name = "Reina_EvolucionHistoriaClinica", catalog = "SistemasSNYA")
@Audited
public class EvolucionHistoriaClinica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1980457171608176260L;
	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdEvolucionHistoriaClinica")
	private Integer id;
	@Column(name = "Fecha")
	private Date fecha;
	@ManyToOne
	@JoinColumn(name = "IdTipoDeDiagnostico")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeDiagnostico tipoDiagnostico;
	@Column(name = "Diagnostico")
	private String diagnostico;
	@Column(name = "Fase")
	private String fase;
	@Column(name = "Tratamiento")
	private String tratamiento;
	@Column(name = "Observacion")
	private String observacion;
	@ManyToOne
	@JoinColumn(name = "IdHistoriaClinica")
	private HistoriaClinica historia;
	
	
	public EvolucionHistoriaClinica() {
		
	}
	
	public EvolucionHistoriaClinica(Date fecha, TipoDeDiagnostico tipoDiagnostico, String diagnostico, 
			String fase, String tratamiento, String observacion) {
		this.fecha = fecha;
		this.tipoDiagnostico = tipoDiagnostico;
		this.diagnostico = diagnostico;
		this.fase = fase;
		this.tratamiento = tratamiento;
		this.observacion = observacion;
	}
	
	
	public EvolucionHistoriaClinica nuevo(Date fecha, String fase, String observacion) {
		EvolucionHistoriaClinica ev = new EvolucionHistoriaClinica(fecha, this.tipoDiagnostico, this.diagnostico, 
				fase, this.tratamiento, observacion);
		
		return ev;
	}

	
	public String getDetalleDiagnostico() {
		// TODO ver como armarlo.
		return this.getTipoDiagnostico().getNombre();
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public TipoDeDiagnostico getTipoDiagnostico() {
		return tipoDiagnostico;
	}

	public void setTipoDiagnostico(TipoDeDiagnostico tipoDiagnostico) {
		this.tipoDiagnostico = tipoDiagnostico;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public HistoriaClinica getHistoria() {
		return historia;
	}

	public void setHistoria(HistoriaClinica historia) {
		this.historia = historia;
	}
}

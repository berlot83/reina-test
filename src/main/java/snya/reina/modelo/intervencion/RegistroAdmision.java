package snya.reina.modelo.intervencion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.TipoDeMovimiento;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.recurso.Recurso;

@Entity
@Table(name = "Reina_Interv_RegistroCAD", catalog = "SistemasSNYA")
@Audited
public class RegistroAdmision implements Comparable<RegistroAdmision>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5788231641358217314L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdRegistroCAD")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "IdJoven")
	private Joven joven;

	@ManyToOne(targetEntity=snya.reina.modelo.recurso.Recurso.class)
	@JoinColumn(name = "IdInstitucion")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private AmbitoEjecucion institucion;

	@Column(name = "Prorroga")
	private Boolean prorroga;

	@Column(name = "FechaIngreso")
	private Date fechaIngreso;

	@Column(name = "FechaAprehension")
	private Date fechaAprehension;

	@Column(name = "LugarAprehension")
	private String lugarAprehension;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "IdProcesoPenal")
	private ProcesoPenal proceso;

	@ManyToOne
	@JoinColumn(name = "IdMotivoAprehension")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private MotivoAprehension motivoAprehension;

	@Column(name = "FechaEgreso", nullable = true)
	private Date fechaEgreso;

	@ManyToOne
	@JoinColumn(name = "IdMotivoEgreso", nullable = true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeMovimiento motivoEgreso;

	@ManyToOne
	@JoinColumn(name = "IdComisaria")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Institucion comisaria;

	@Column(name = "PoliciaInterviniente")
	private String policiaInterviniente;

	@Column(name = "LegajoPolicial")
	private String legajoPolicial;

	@Column(name = "DatosMovilPolicial")
	private String datosMovilPolicial;

	@Column(name = "CircunstanciaAprehension")
	private String circunstanciaAprehension;

	@ManyToOne
	@JoinColumn(name = "IdIntervencion")
	private Intervencion intervencion;
	
	@ManyToOne
	@JoinColumn(name="IdSolicitudIntervencionSPPD")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private Informe solicitudIntervencion;
	
	
	public RegistroAdmision() {
		this.prorroga = false;
		this.solicitudIntervencion = null;
		this.fechaEgreso = null;
		this.motivoEgreso = null;		
	}
	
	public RegistroAdmision(Intervencion intervencion, AmbitoEjecucion institucion, Joven joven, Date fIngreso, 
			Date fAprehension, String lugarAprehension, ProcesoPenal procesoPenal, MotivoAprehension motivoAprehension,
			Institucion comisaria, String policiaInterviniente, String legajoPolicial, String datosMovilPolicial,
			String circunstanciaAprehension) {
		this.prorroga = false;
		this.solicitudIntervencion = null;
		this.fechaEgreso = null;
		this.motivoEgreso = null;
		
		this.intervencion = intervencion;
		this.institucion = institucion;
		this.joven = joven;
		this.fechaIngreso = fIngreso;
		
		this.fechaAprehension = fAprehension;
		this.lugarAprehension = lugarAprehension;
		this.proceso = procesoPenal;
		this.motivoAprehension = motivoAprehension;
		
		this.comisaria = comisaria;
		this.policiaInterviniente = policiaInterviniente;
		this.legajoPolicial = legajoPolicial;
		this.datosMovilPolicial = datosMovilPolicial;
		this.circunstanciaAprehension = circunstanciaAprehension;
	}

	
	@Override
	public int compareTo(RegistroAdmision o) {
		return this.getFechaIngreso().compareTo(o.getFechaIngreso()) * -1;
	}
		
	public Institucion getInstitucionAdmision() {
		return (Institucion) ((Recurso) this.getInstitucion()).getComponente();	
	}	
	
	public boolean estaPresente() {
		return this.getFechaEgreso() == null;
	}

	public boolean getTieneIntervencionSPPD() {
		return this.getSolicitudIntervencion() != null;
	}

	public long calcularTiempoRestante() {
		Integer tiempoLimite = 60 * 12 * ((this.getProrroga()) ? 2 : 1);
		long minutos = Calendario.calcularTiempoTranscurridoAhora(this.getFechaIngreso());

		return tiempoLimite - minutos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public AmbitoEjecucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(AmbitoEjecucion institucion) {
		this.institucion = institucion;
	}

	public Boolean getProrroga() {
		return prorroga;
	}

	public void setProrroga(Boolean prorroga) {
		this.prorroga = prorroga;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaAprehension() {
		return fechaAprehension;
	}

	public void setFechaAprehension(Date fechaAprehension) {
		this.fechaAprehension = fechaAprehension;
	}

	public String getLugarAprehension() {
		return lugarAprehension;
	}

	public void setLugarAprehension(String lugarAprehension) {
		this.lugarAprehension = lugarAprehension;
	}

	public ProcesoPenal getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoPenal proceso) {
		this.proceso = proceso;
	}

	public MotivoAprehension getMotivoAprehension() {
		return motivoAprehension;
	}

	public void setMotivoAprehension(MotivoAprehension motivoAprehension) {
		this.motivoAprehension = motivoAprehension;
	}

	public Date getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Date fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public TipoDeMovimiento getMotivoEgreso() {
		return motivoEgreso;
	}

	public void setMotivoEgreso(TipoDeMovimiento motivoEgreso) {
		this.motivoEgreso = motivoEgreso;
	}

	public Institucion getComisaria() {
		return comisaria;
	}

	public void setComisaria(Institucion comisaria) {
		this.comisaria = comisaria;
	}

	public String getPoliciaInterviniente() {
		return policiaInterviniente;
	}

	public void setPoliciaInterviniente(String policiaInterviniente) {
		this.policiaInterviniente = policiaInterviniente;
	}

	public String getLegajoPolicial() {
		return legajoPolicial;
	}

	public void setLegajoPolicial(String legajoPolicial) {
		this.legajoPolicial = legajoPolicial;
	}

	public String getDatosMovilPolicial() {
		return datosMovilPolicial;
	}

	public void setDatosMovilPolicial(String datosMovilPolicial) {
		this.datosMovilPolicial = datosMovilPolicial;
	}

	public String getCircunstanciaAprehension() {
		return circunstanciaAprehension;
	}

	public void setCircunstanciaAprehension(String circunstanciaAprehension) {
		this.circunstanciaAprehension = circunstanciaAprehension;
	}
	
	public Informe getSolicitudIntervencion() { 
		return solicitudIntervencion;
	}
	
	public void setSolicitudIntervencion(Informe solicitudIntervencion) {
		this.solicitudIntervencion = solicitudIntervencion; 
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	public void setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}
}

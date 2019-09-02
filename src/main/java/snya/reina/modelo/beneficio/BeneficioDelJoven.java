package snya.reina.modelo.beneficio;

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

import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.joven.Joven;

@Entity
@Table(name="Reina_BeneficioDelJoven", catalog="SistemasSNYA")
@Audited
public class BeneficioDelJoven implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4111940304343917817L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdBeneficioDelJoven")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeBeneficio")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeBeneficio tipo;
	
	@Column(name="FechaAlta")
	private Date fechaAlta;
	
	@Column(name="FechaBaja")
	private Date fechaBaja;
	
	@Column(name="FechaEntregaTarjeta")
	private Date fechaEntregaTarjeta;	
	
	@Column(name="Identificador")
	private String identificador;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@ManyToOne
	@JoinColumn(name="IdEstadoBeneficio")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private EstadoBeneficio estado;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="IdSupervisor")
	private Institucion supervisor; 
	
	@ManyToOne
	@JoinColumn(name="IdOperativoBeneficio")
	private Operativo operativo;
	

	//Constructor
	public BeneficioDelJoven() {
		
	}
	
	public BeneficioDelJoven(TipoDeBeneficio tipoDePension, String numeroPension, Date fechaAlta, EstadoBeneficio estado, String observacion, Institucion supervisor) {
		this.setTipo(tipoDePension);
		this.setIdentificador(numeroPension);
		this.setFechaAlta(fechaAlta);
		this.setEstado(estado);
		this.setObservacion(observacion);
		this.setSupervisor(supervisor);
	}
	
	public boolean estaVigente() {
		return !this.getEstado().getId().equals(EstadoBeneficio.BAJA);
	}

	public boolean estaSuspendido() {
		return !this.getEstado().getId().equals(EstadoBeneficio.SUSPENDIDO);
	}
	
	public boolean tieneSupervisionOResponsable() {
		return this.getSupervisor() != null || this.getTipo().getResponsable() != null;
	}	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoDeBeneficio getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeBeneficio tipo) {
		this.tipo = tipo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaEntregaTarjeta() {
		return fechaEntregaTarjeta;
	}

	public void setFechaEntregaTarjeta(Date fechaEntregaTarjeta) {
		this.fechaEntregaTarjeta = fechaEntregaTarjeta;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public EstadoBeneficio getEstado() {
		return estado;
	}

	public void setEstado(EstadoBeneficio estado) {
		this.estado = estado;
	}

	public Institucion getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Institucion supervisor) {
		this.supervisor = supervisor;
	}

	public Operativo getOperativo() {
		return operativo;
	}

	public void setOperativo(Operativo operativo) {
		this.operativo = operativo;
	}
}

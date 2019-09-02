package snya.reina.modelo.joven;

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

import snya.reina.ReinaException;
import snya.reina.datos.joven.JovenDAO;

@Entity
@Table(name="Reina_SituacionTramiteDNI", catalog="SistemasSNYA")
@Audited
public class SituacionTramite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7112518131917742418L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdSituacionTramiteDNI")
	private Integer id;
	
	@Column(name="FechaSituacion")
	private java.util.Date fechaSituacion = new java.util.Date(0);
	
	/*  */
	@ManyToOne
	@JoinColumn(name="IdTipoSituacionTramiteDNI", nullable=false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeSituacionTramite tipo;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@Column(name="PartidaNacimiento")
	private Boolean partidaNacimiento;
	
	@Column(name="CertificadoParto")
	private Boolean certificadoDeParto;
	
	@Column(name="Actualizado")
	private Boolean actualizado;
	
	@Column(name="ObservacionSituacion" )
	private String observacion;
	

	/* === Constructores === */
	public SituacionTramite() {
	}

	/* === Mètodos === */
	public String traerDetalle() {
		return this.getTipo().traerDetalle(this.getJoven(), this);
	}

	public String traerDetalleExtenso() {
		return this.getTipo().traerDetalleExtenso(this.getJoven(), this);
	}
	
	public void noSeRepiteDocumento(Joven joven, JovenDAO daoJoven) throws ReinaException {
		this.getTipo().noSeRepiteDocumento(joven, daoJoven);	
	}
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public java.util.Date getFechaSituacion() {
		return fechaSituacion;
	}

	public void setFechaSituacion(java.util.Date value) {
		fechaSituacion = value;
	}

	public TipoDeSituacionTramite getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeSituacionTramite value) {
		tipo = value;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String value) {
		observacion = value;
	}

	public Boolean getPartidaNacimiento() {
		return partidaNacimiento;
	}

	public void setPartidaNacimiento(Boolean value) {
		partidaNacimiento = value;
	}

	public Boolean getCertificadoDeParto() {
		return certificadoDeParto;
	}

	public void setCertificadoDeParto(Boolean value) {
		certificadoDeParto = value;
	}

	public Boolean getActualizado() {
		return actualizado;
	}

	public void setActualizado(Boolean actualizado) {
		this.actualizado = actualizado;
	}
}
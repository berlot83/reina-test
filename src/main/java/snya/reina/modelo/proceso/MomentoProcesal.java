package snya.reina.modelo.proceso;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


import snya.reina.modelo.institucion.Institucion;

@Entity
@Table(name="Reina_MomentoProcesal", catalog="SistemasSNYA")
@Audited
public class MomentoProcesal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8424215753761026055L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMomentoProcesal")
	private Integer id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdTipoDeMomentoProcesal")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeMomentoProcesal tipo;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdInstitucion")
	private Institucion juzgado;
	
	@Column(name="FechaImposicion")
	private java.util.Date fechaImposicion = new java.util.Date();
	
	@Column(name="FechaFin")
	private java.util.Date fechaFin;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdProcesoPenal")
	private ProcesoPenal proceso;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="Reina_MedidaEnMomentoProcesal",
			joinColumns=@JoinColumn(name="IdMomentoProcesal", referencedColumnName="IdMomentoProcesal"),
			inverseJoinColumns=@JoinColumn(name="IdMedidaEnProcesoPenal", referencedColumnName="IdMedidaEnProcesoPenal"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private java.util.List<MedidaEnProcesoPenal> medidasImpuestas;
	
	
	/* === Constructores === */
	public MomentoProcesal() {
		this.setMedidasImpuestas(new java.util.ArrayList<MedidaEnProcesoPenal>());
	}

	public MomentoProcesal(Date fecha,
			TipoDeMomentoProcesal tipo, Institucion organoJudicial,
			ProcesoPenal proceso) {
		this.fechaImposicion = fecha;
		this.tipo = tipo;
		this.juzgado = organoJudicial;
		this.proceso = proceso;
		this.setMedidasImpuestas(new java.util.ArrayList<MedidaEnProcesoPenal>());
	}

	/* === Metodos === */
	public void agregarMedida(MedidaEnProcesoPenal medida) {
		this.getMedidasImpuestas().add(medida);
	}

	public void quitarMedidaJudicial(MedidaEnProcesoPenal medida) {
		this.getMedidasImpuestas().remove(medida);
	}

	public boolean contieneMedidaJudicial(MedidaEnProcesoPenal medida) {
		boolean encontrado = false;
		Iterator<MedidaEnProcesoPenal> iter = this.getMedidasImpuestas().iterator();
		while (!encontrado && iter.hasNext()) {
			MedidaEnProcesoPenal mepp = (MedidaEnProcesoPenal) iter.next();
			encontrado = encontrado || mepp.getId().equals(medida.getId());
		}
		return encontrado;
	}
	
	public boolean contieneMedidas() {
		return (this.getTipo().getMedidas().size() > 0);
	}
	
	public boolean vigenteAl(Date fecha) {
		return this.getFechaImposicion().getTime() <= fecha.getTime() && (this.getFechaFin() == null || this.getFechaFin().getTime() >= fecha.getTime());
	}

	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public java.util.Date getFechaImposicion() {
		return fechaImposicion;
	}

	public void setFechaImposicion(java.util.Date value) {
		fechaImposicion = value;
	}

	public java.util.Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(java.util.Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ProcesoPenal getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoPenal proceso) {
		this.proceso = proceso;
	}

	public TipoDeMomentoProcesal getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMomentoProcesal value) {
		tipo = value;
	}

	public Institucion getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(Institucion juzgado) {
		this.juzgado = juzgado;
	}

	public java.util.List<MedidaEnProcesoPenal> getMedidasImpuestas() {
		return medidasImpuestas;
	}

	public void setMedidasImpuestas(java.util.List<MedidaEnProcesoPenal> value) {
		medidasImpuestas = value;
	}
}
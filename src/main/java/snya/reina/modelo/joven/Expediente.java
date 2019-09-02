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

import snya.reina.modelo.Calendario;

@Entity
@Table(name="Reina_ExpedienteTecnico", catalog="SistemasSNYA")
@Audited
public class Expediente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5942266938407898940L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdExpediente")
	private Integer id;
	
	@Column(name="ExpedienteTecnico")
	private Long numero;
	
	@Column(name="Legajo")
	private String legajo;
	
	@ManyToOne
	@JoinColumn(name="IdCaratulador", nullable=false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Caratulador caratulador;
	
	@ManyToOne
	@JoinColumn(name="IdEstado", nullable=false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeEstadoExpediente estado;
	
	@Column(name="FechaArchivado")
	private java.util.Date fechaArchivado;
	
	@Column(name="PaqueteArchivo")
	private String paqueteArchivo;

	@ManyToOne
	@JoinColumn(name="IdJoven", nullable=false)
	private Joven joven;

	
	public Expediente() {
		
	}
	
	public Expediente(Caratulador caratulador, Long numero, TipoDeEstadoExpediente estado) {
		this.caratulador = caratulador;
		this.numero = numero;
		this.estado = estado;
	}

	
	public Expediente(Caratulador caratulador, Long numero,
			String legajo, TipoDeEstadoExpediente estado) {
		this(caratulador, numero, estado);
		this.legajo = legajo;		
	}

	public boolean caratuladoPorSPRJ() {
		return this.getCaratulador().getCaracteristica().equals( Caratulador.CODIGO_SRPJ );
	}
	
	public boolean estaVigente() {
		return this.getEstado().getId() == TipoDeEstadoExpediente.VIGENTE;
	}
	
	public boolean estaAnulado() {
		return this.getEstado().getId() == TipoDeEstadoExpediente.ANULADO;
	}
	
	public String getCaratuladorNombre() {
		return this.getCaratulador().getNombre();
	}

	public String getEstadoNombre() {
		return this.getEstado().getNombre();
	}
	
	public void cambiarEstado(TipoDeEstadoExpediente estado) {
		this.setEstado(estado);
	}
	
	public String getDetalleEstado(){
		String detalle = "";
		detalle = this.getEstadoNombre();
		
		if ( this.getEstado().getId() == TipoDeEstadoExpediente.ARCHIVADO ) {
			String fecha = (this.getFechaArchivado() != null) ? Calendario.formatearFecha(this.getFechaArchivado()) : "";
			String paquete = (this.getPaqueteArchivo() != null) ? this.getPaqueteArchivo() : "";
			
			if (!fecha.equals("") || !paquete.equals(""))
				detalle += " (" + fecha + " - Paquete " + paquete + ")";
		}
		
		return detalle; 
	}
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public Caratulador getCaratulador() {
		return caratulador;
	}

	public void setCaratulador(Caratulador caratulador) {
		this.caratulador = caratulador;
	}

	public TipoDeEstadoExpediente getEstado() {
		return estado;
	}

	public void setEstado(TipoDeEstadoExpediente estado) {
		this.estado = estado;
	}

	public java.util.Date getFechaArchivado() {
		return fechaArchivado;
	}

	public void setFechaArchivado(java.util.Date fechaArchivado) {
		this.fechaArchivado = fechaArchivado;
	}

	public String getPaqueteArchivo() {
		return paqueteArchivo;
	}

	public void setPaqueteArchivo(String paqueteArchivo) {
		this.paqueteArchivo = paqueteArchivo;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public String getNumeroCompleto() {
		return (this.getNumero() != null) ? this.getNumero().toString() : "";
	}
}

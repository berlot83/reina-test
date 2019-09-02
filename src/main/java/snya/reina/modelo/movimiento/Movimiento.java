package snya.reina.modelo.movimiento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.resultado.ResultadoPresente;

@Entity
@Table(name="Reina_Mov_Movimiento", catalog="SistemasSNYA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="IdFuncionalidadMovimiento", discriminatorType = DiscriminatorType.INTEGER)
@Audited
public abstract class Movimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6290426016098242159L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMovimiento")
	private Integer id;
	
	@Column(name="FechaMovimiento")
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="IdEstadoMovimiento", nullable=false)	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private EstadoMovimiento estado;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeMovimiento", nullable=false)
	private TipoDeMovimiento tipo;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeMotivoMovimiento", nullable=false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeMotivoMovimiento motivo;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="IdMedidaEnProcesoPenal", nullable=true)
	private MedidaEnProcesoPenal medidaImpuesta;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="IdJoven", nullable=false)
	private Joven joven;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IdPermanencia", nullable=true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Permanencia permanencia;

	@ManyToOne
	@JoinColumn(name="IdIntervencion")
	private Intervencion intervencion;
	
	public abstract Date traerFecha();

	public abstract AmbitoEjecucion traerAmbitoEjecucionOrigen();

	public abstract AmbitoEjecucion traerAmbitoEjecucionDestino();

	public abstract ResultadoPresente asentarHistorial(Joven joven, Intervencion intervencion, ProcesoPenal proceso,
			AmbitoEjecucion ambitoEjecucionOrigen, AmbitoEjecucion ambitoEjecucionDestino, EscritorNarrativoDeHistoria escritor);

	public abstract void modificarHistorial(Joven joven, EscritorNarrativoDeHistoria escritor);
	
	public abstract void actualizarHistorial(EscritorNarrativoDeHistoria escritor);
		
	protected abstract void actualizarHistorialDelProceso(EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso);
	
	
	public void acomodarMovimientoSegun(ProcesoPenal proceso, EscritorNarrativoDeHistoria escritor) {
		if( this.getMedidaImpuesta() != null ) {
			if ( this.getMedidaImpuesta().getProceso().getId().equals(proceso.getId()) ) {
				MedidaEnProcesoPenal medida = proceso.traerMedidaImpuestaAl( this.getFecha() );
				if (medida != null && medida.getId() != this.getMedidaImpuesta().getId()) {
					this.setMedidaImpuesta(medida);
					
					if ( this.getPermanencia() != null ) {
						this.getPermanencia().setMedida(medida);
						if (this.getPermanencia().getProceso() == null) this.getPermanencia().setProceso(proceso);
						
						this.actualizarHistorial(escritor);
					}
				} else {
					if ( this.getPermanencia() != null ) {
						if (this.getPermanencia().getProceso() == null) {
							this.getPermanencia().setProceso(proceso);
							this.getPermanencia().setMedida(medida);
							
							this.actualizarHistorial(escritor);
						}
					}
				}
			}
		} else
			this.actualizarHistorialDelProceso(escritor, proceso);
	}
	
	public void actualizarMovimientoSegunMedida(EscritorNarrativoDeHistoria escritor) {
		if( this.getMedidaImpuesta() != null && this.getPermanencia() != null) {
			this.getPermanencia().setMedida(this.getMedidaImpuesta());
			this.getPermanencia().setProceso(this.getMedidaImpuesta().getProceso());
			this.actualizarHistorial(escritor);			
		}
	}

	public void acomodarProcesoDePermanencia(ProcesoPenal proceso, MedidaEnProcesoPenal medida, Date fecha, EscritorNarrativoDeHistoria escritor) {
		this.getPermanencia().acomodarProcesoDePermanencia(proceso, medida, fecha, escritor);		
	}
	
	public abstract void eventoConfirmar(ManejoEventoMovimiento manejo);
	
	public String traerDescripcion() {
		return this.getTipo().getNombre();
	}
	
	public boolean noEstaPendiente() {
		return this.getEstado().noEstaPendiente();
	}

	public void confirmar(EstadoMovimiento estado) {
		this.setEstado(estado);
	}

	public void cancelar(EstadoMovimiento estado) {
		this.setEstado(estado);
	}
	
	public boolean estaConfirmado() {
		return this.getEstado().getId() == EstadoMovimiento.CONFIRMADO;
	}
	
	public boolean estaCancelada() {
		return this.getEstado().getId() == EstadoMovimiento.CANCELADO;
	}
	
	public List<TipoDeMovimiento> traerPosiblesTraslados() {
		return this.getTipo().traerPosiblesTraslados();
	}
	
	public List<TipoDeMovimiento> traerPosiblesNotificaciones() {
		return this.getTipo().traerPosiblesNotificaciones();
	}
	
	public List<TipoDeMovimiento> traerPosiblesEgresos() {
		return this.getTipo().traerPosiblesEgresos();
	}
	
	public List<TipoDeMovimiento> traerPosiblesBajas() {
		return this.getTipo().traerPosiblesBajas();
	}
	
	public List<TipoDeMovimiento> traerPosiblesMovimientosAdmision() {
		return this.getTipo().traerPosiblesMovimientosAdmision();
	}
	
	public List<TipoDeMovimiento> traerPosiblesEgresosAdmision() {
		return this.getTipo().traerPosiblesEgresosAdmision();
	}
	
	/*=== Propiedades ===*/
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
	
	public EstadoMovimiento getEstado() {
		return estado;
	}

	public void setEstado(EstadoMovimiento estado) {
		this.estado = estado;
	}

	public TipoDeMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMovimiento tipo) {
		this.tipo = tipo;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}
	
	public Permanencia getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(Permanencia permanencia) {
		this.permanencia = permanencia;
	}

	public TipoDeMotivoMovimiento getMotivo() {
		return motivo;
	}

	public void setMotivo(TipoDeMotivoMovimiento motivoMovimiento) {
		this.motivo = motivoMovimiento;
	}

	public MedidaEnProcesoPenal getMedidaImpuesta() {
		return medidaImpuesta;
	}

	public void setMedidaImpuesta(MedidaEnProcesoPenal medidaImpuesta) {
		this.medidaImpuesta = medidaImpuesta;
	}
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	public void setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}
}
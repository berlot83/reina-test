package snya.reina.modelo.proceso;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


import snya.reina.modelo.institucion.Institucion;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.Calendario;

@Entity
@Table(name="Reina_MedidaEnProcesoPenal", catalog="SistemasSNYA")
@Audited
public class MedidaEnProcesoPenal implements Comparable<MedidaEnProcesoPenal>, Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3944491038818210075L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMedidaEnProcesoPenal")
	private Integer id;
	
	@Column(name="FechaMedida")
	private Date fechaMedida;
	
	@Column(name="FechaFinMedida")
	private Date fechaFinMedida;
	
	@ManyToOne
	@JoinColumn(name="IdInstitucion")
	private Institucion adoptaMedida;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdTipoDeMedidaEnProcesoPenal")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeMedidaEnProcesoPenal tipo;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true, mappedBy="medida")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DetalleMedidaEnProcesoPenal> detalles;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="IdProcesoPenal")
	private ProcesoPenal proceso;
	
	@Column(name="TiempoDeHorasMedida")
	private Integer tiempoDeHorasMedida;
	
	@Column(name="TiempoDeDiasMedida")
	private Integer tiempoDeDiasMedida;
	
	@Column(name="TiempoDeMesesMedida")
	private Integer tiempoDeMesesMedida;
	
	@Column(name="TiempoDeFechaFinMedida")
	private Date tiempoDeFechaFinMedida;
	
	@Column(name="TiempoCumplimiento")
	private int tiempoCumplimiento;
	
	@Column(name="EstaApelada")
	private Boolean estaApelada;
	

	/* === Constructores === */
	public MedidaEnProcesoPenal() {
		this.setDetalles(new java.util.ArrayList<DetalleMedidaEnProcesoPenal>());
	//	this.momentos = new ArrayList<MomentoProcesal>();
		this.setEstaApelada(false);
	}

	
	public MedidaEnProcesoPenal(Date fecha, Institucion institucionAdopta,
			String observacion, TipoDeMedidaEnProcesoPenal tipo,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFnDeMedida,
			ProcesoPenal proceso) {
		this();
		this.fechaMedida = fecha;
		this.adoptaMedida = institucionAdopta;
		this.tipo = tipo;
		this.observacion = observacion;
		this.proceso = proceso;
	//	this.momentos = new ArrayList<MomentoProcesal>();
		this.crearDetallesMedida(detalles);
		
		this.calcularTiempoMedida(horasDeMedida, diasDeMedida, mesesDeMedida, fechaFnDeMedida);
	}


	/* === Metodos === */
	public void marcarApelacion(Date fecha) throws ReinaException {	
		if (!this.vigenteAl(fecha))
			throw new ReinaException(ReinaCte.FECHA_APELACION_FUERA_RANGO_FECHA_MEDIDA);
		
		this.setEstaApelada(true);
		calcularCumplimientoSegunFechaSuspencion(fecha);
	}

	public void quitarApelacion() {
		this.setEstaApelada(false);
		this.recalcularTiempos();
	}
	
	public void volverAContarTiempoDeCumplimiento() {
		this.setEstaApelada(false);
	}
	
	@Override
	public int compareTo(MedidaEnProcesoPenal m) {
		return this.getFechaMedida().compareTo(m.getFechaMedida());
	}
	
	public String traerFechaMedida() {
		return Calendario.formatearFecha(this.getFechaMedida());
	}
	/*
	public void agregarSituacion(MomentoProcesal momentoProcesal) {
		this.momentos.add(momentoProcesal);		
	}


	public void quitarSituacion(MomentoProcesal momentoProcesal) {
		this.momentos.remove(momentoProcesal);
	}
*/
	public String traerDetalle() {
		return  ((this.getAdoptaMedida() != null) ? " adoptada por " + this.getAdoptaMedida().getNombre() : "") +
			//" durante el momento procesal de " + this.getSituacion().getTipo().getNombre() +
			" disponiendo " + this.traerDetalleTipos();
	}

	public String getDetalleTiempo() {
		String detalle = "";

		if (this.getTiempoDeMesesMedida() != 0)
			detalle = detalle + ((this.getTiempoDeMesesMedida() == 1) ? "un mes" : this.getTiempoDeMesesMedida() + " meses ");
		if (this.getTiempoDeDiasMedida() != 0) {
			if (!detalle.equals("")) detalle += ", ";
			detalle = detalle + ((this.getTiempoDeDiasMedida() == 1) ? "un dia" : this.getTiempoDeDiasMedida() + " dias ");
		}
		if (this.getTiempoDeHorasMedida() != 0) {
			if (!detalle.equals("")) detalle += " y ";			
			detalle = detalle + ((this.getTiempoDeHorasMedida() == 1) ? "una hora" : this.getTiempoDeHorasMedida() + " horas ");
		}
			
		return detalle;
	}

	public String getTiempoDeFechaFinMedidaTexto() {
		return (this.getTiempoDeFechaFinMedida() == null) ? "" : Calendario.formatearFecha(this.getTiempoDeFechaFinMedida());
	}
	
	public String getCalculoFinTiempoMedida() {
		if (this.getTiempoCumplimiento() == -1) {
			return "";
		} else {
			Calendar c = Calendar.getInstance();
			int tiempoDeCumplimiento = 0;
			
			if (this.getTiempoCumplimiento() == 0) {
				if (this.getTiempoDeFechaFinMedida() == null) {
					if (this.getTiempoDeMesesMedida() != 0 ||
						this.getTiempoDeDiasMedida() != 0 ||
						this.getTiempoDeHorasMedida() != 0) {
					
						Date fecha;
						if (this.getTipo().isContarDesdePrincipio())
							fecha = this.getProceso().getFechaIngreso();
						else
							fecha = this.getFechaMedida();
						c.setTime(fecha);
						
						
						if (this.getTiempoDeMesesMedida() != 0)
							c.add(Calendar.MONTH, this.getTiempoDeMesesMedida());
						if (this.getTiempoDeDiasMedida() != 0)
							c.add(Calendar.DATE, this.getTiempoDeDiasMedida());
						if (this.getTiempoDeHorasMedida() != 0)
							c.add(Calendar.HOUR, this.getTiempoDeHorasMedida());
				
						
						Calendar cF = Calendar.getInstance();
						Date diaHoy = new Date();
						cF.setTime( ( diaHoy.after(fecha) ) ? diaHoy : fecha );
						long diff = ( c.getTimeInMillis() - cF.getTimeInMillis() > 0 ) ? ( c.getTimeInMillis() - cF.getTimeInMillis() ) : 0;
						tiempoDeCumplimiento = (((Long)(diff / (24*60 * 60 * 1000))).intValue());
					}
				} else {
					c.setTime(this.getTiempoDeFechaFinMedida());

					Calendar cF = Calendar.getInstance();
					Date diaHoy = new Date();
					cF.setTime( diaHoy  );
					cF.set(Calendar.HOUR, 0);
					cF.set(Calendar.MINUTE, 0);
					cF.set(Calendar.SECOND, 0);
					cF.set(Calendar.MILLISECOND, 0);
					
					long diff = ( c.getTimeInMillis() - cF.getTimeInMillis() > 0 ) ? ( c.getTimeInMillis() - cF.getTimeInMillis() ) : 0;
					tiempoDeCumplimiento = ( ((Long)(diff / (24 * 60 * 60 * 1000))).intValue());
				}
			} else {			
				c.setTime(new Date());
				tiempoDeCumplimiento = this.getTiempoCumplimiento();
			}
			
			c.add(Calendar.DATE, tiempoDeCumplimiento);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(c.getTime());
		}
	}

	public void recalcularTiempos() {
		calcularTiempoDeCumplimiento();
	}
	
	private String traerDetalleTipos() {
		String texto = this.getTipo().getNombre() + " - ";
		
		for (DetalleMedidaEnProcesoPenal detalle : this.getDetalles()) {
			texto += detalle.getNombreCorto() + ", ";
		}
		if (texto.length() > 0) texto = texto.substring(0, texto.length() - 2);
		
		return texto;
	}
	
	public String traerDetalleTipificado() {
		String texto = "";
		
		for (DetalleMedidaEnProcesoPenal detalle : this.getDetalles()) {
			texto += detalle.getNombreCorto() + ", ";
		}
		if (texto.length() > 0) texto = texto.substring(0, texto.length() - 2);
		
		return texto;
	}
	
	public void modificarDetalles(List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles) {
		List<DetalleMedidaEnProcesoPenal> lista = new ArrayList<DetalleMedidaEnProcesoPenal>(this.detalles);
				
		for (DetalleMedidaEnProcesoPenal det : lista) {
			boolean encontrado = false;
			Iterator<TipoDeDetalleDeMedidaEnProcesoPenal> it = detalles.iterator();
			while (!encontrado && it.hasNext()) {
				encontrado = (it.next().getId().equals(det.getIdTipo()));				
			}
			if (!encontrado) {
				this.detalles.remove(det);
				det.setMedida(null);
			}
		}
		
		for (TipoDeDetalleDeMedidaEnProcesoPenal tipo : detalles) {
			boolean encontrado = false;
			Iterator<DetalleMedidaEnProcesoPenal> it = this.detalles.iterator();
			while (!encontrado && it.hasNext()) {
				encontrado = (it.next().getIdTipo().equals(tipo.getId()));				
			}
			if (!encontrado) {
				this.detalles.add(new DetalleMedidaEnProcesoPenal(tipo, this));
			}
		}
	}
	
	public List<TipoDeDetalleDeMedidaEnProcesoPenal> getTiposDetalles() {
		List<TipoDeDetalleDeMedidaEnProcesoPenal> lista = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		
		for (DetalleMedidaEnProcesoPenal det : this.detalles) {
			lista.add(det.getTipo());
		}
		
		return lista;
	}
		
	public Boolean tieneApelacionNoSuspensiva() {
		NotaProcesoPenal nota = this.getProceso().ultimaAccionSobreMedida(this.getId());
		return (nota != null && nota.getTipo().getId().equals(TipoDeNotaProcesoPenal.ID_APELACION_NO_SUSPENSIVA));
	}
	
	/*
	private MomentoProcesal getSituacion() {
		return this.getMomentos().get( this.getMomentos().size() -1 );
	}*/
	
	private void calcularTiempoMedida(Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) {
		this.setTiempoDeHorasMedida(horasDeMedida);
		this.setTiempoDeDiasMedida(diasDeMedida);
		this.setTiempoDeMesesMedida(mesesDeMedida);
		this.setTiempoDeFechaFinMedida(fechaFinDeMedida);	
		
		calcularTiempoDeCumplimiento();
	}

	private void calcularTiempoDeCumplimiento() {
		if (this.getTiempoDeFechaFinMedida() == null) {
			if (this.getTiempoDeMesesMedida() != 0 ||
				this.getTiempoDeDiasMedida() != 0 ||
				this.getTiempoDeHorasMedida() != 0) {
			
				Date fecha;
				if (this.getTipo().isContarDesdePrincipio())
					fecha = this.getProceso().getFechaIngreso();
				else
					fecha = this.getFechaMedida();
				Calendar c = Calendar.getInstance();
				c.setTime(fecha);
				
				
				if (this.getTiempoDeMesesMedida() != 0)
					c.add(Calendar.MONTH, this.getTiempoDeMesesMedida());
				if (this.getTiempoDeDiasMedida() != 0)
					c.add(Calendar.DATE, this.getTiempoDeDiasMedida());
				if (this.getTiempoDeHorasMedida() != 0)
					c.add(Calendar.HOUR, this.getTiempoDeHorasMedida());
		
				
				Calendar cF = Calendar.getInstance();
				Date diaHoy = new Date();
				cF.setTime( ( diaHoy.after(fecha) ) ? diaHoy : fecha );
				long diff = ( c.getTimeInMillis() - cF.getTimeInMillis() > 0 ) ? ( c.getTimeInMillis() - cF.getTimeInMillis() ) : 0;
				this.setTiempoCumplimiento( ((Long)(diff / (24*60 * 60 * 1000))).intValue());
			} else {
				this.setTiempoCumplimiento(-1);
			}
		} else {
			this.setTiempoDeMesesMedida(0);
			this.setTiempoDeDiasMedida(0);
			this.setTiempoDeHorasMedida(0);
			
			Date fecha;
			fecha = this.getTiempoDeFechaFinMedida();
			Calendar c = Calendar.getInstance();
			c.setTime(fecha);

			Calendar cF = Calendar.getInstance();
			Date diaHoy = new Date();
			cF.setTime( diaHoy  );
			cF.set(Calendar.HOUR, 0);
			cF.set(Calendar.MINUTE, 0);
			cF.set(Calendar.SECOND, 0);
			cF.set(Calendar.MILLISECOND, 0);
			
			long diff = ( c.getTimeInMillis() - cF.getTimeInMillis() > 0 ) ? ( c.getTimeInMillis() - cF.getTimeInMillis() ) : 0;
			this.setTiempoCumplimiento( ((Long)(diff / (24 * 60 * 60 * 1000))).intValue());
		}
	}
	
	private void calcularCumplimientoSegunFechaSuspencion(Date fecha) {
		Calendar cF = null;
		if (this.getTiempoDeFechaFinMedida() != null) {
			Date fechaFin  = this.getTiempoDeFechaFinMedida();
			cF = Calendar.getInstance();
			cF.setTime(fechaFin);
		} else {
			if (this.getTiempoDeMesesMedida() != 0 ||
				this.getTiempoDeDiasMedida() != 0 ||
				this.getTiempoDeHorasMedida() != 0) {
				
				Date fechaFin;
				if (this.getTipo().isContarDesdePrincipio())
					fechaFin = this.getProceso().getFechaIngreso();
				else
					fechaFin = this.getFechaMedida();
				cF = Calendar.getInstance();
				cF.setTime(fechaFin);
								
				if (this.getTiempoDeMesesMedida() != 0)
					cF.add(Calendar.MONTH, this.getTiempoDeMesesMedida());
				if (this.getTiempoDeDiasMedida() != 0)
					cF.add(Calendar.DATE, this.getTiempoDeDiasMedida());
				if (this.getTiempoDeHorasMedida() != 0)
					cF.add(Calendar.HOUR, this.getTiempoDeHorasMedida());
			}
		}
		
		if (cF != null) {
			Calendar cA = Calendar.getInstance();
			cA.setTime( fecha );
			long diff = ( cF.getTimeInMillis() - cA.getTimeInMillis() > 0 ) ? ( cF.getTimeInMillis() - cA.getTimeInMillis() ) : 0;
			this.setTiempoCumplimiento( ((Long)(diff / (24*60 * 60 * 1000))).intValue());			
		} else
			this.setTiempoCumplimiento( -1 );
	}
	
	private void crearDetallesMedida(List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles) {
		this.detalles = new ArrayList<DetalleMedidaEnProcesoPenal>();
		
		for (TipoDeDetalleDeMedidaEnProcesoPenal tipo : detalles) {
			this.detalles.add(new DetalleMedidaEnProcesoPenal(tipo, this));
		}
	}

	public boolean vigenteAl(Date fecha) {
		return this.getFechaMedida().getTime() <= fecha.getTime() && (this.getFechaFinMedida() == null || this.getFechaFinMedida().getTime() >= fecha.getTime());
	}

	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public java.util.Date getFechaMedida() {
		return fechaMedida;
	}

	public void setFechaMedida(java.util.Date value) {
		fechaMedida = value;
	}

	public Date getFechaFinMedida() {
		return fechaFinMedida;
	}

	public void setFechaFinMedida(Date fechaFinMedida) {
		this.fechaFinMedida = fechaFinMedida;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String value) {
		observacion = value;
	}

	public TipoDeMedidaEnProcesoPenal getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMedidaEnProcesoPenal tipo) {
		this.tipo = tipo;
	}

	public java.util.List<DetalleMedidaEnProcesoPenal> getDetalles() {
		return detalles;
	}

	public void setDetalles(java.util.List<DetalleMedidaEnProcesoPenal> detalles) {
		this.detalles = detalles;
	}

	public Institucion getAdoptaMedida() {
		return adoptaMedida;
	}

	public void setAdoptaMedida(Institucion institucion) {
		adoptaMedida = institucion;
	}

	public Integer getTiempoDeHorasMedida() {
		return tiempoDeHorasMedida;
	}

	public void setTiempoDeHorasMedida(Integer tiempoDeHorasMedida) {
		this.tiempoDeHorasMedida = tiempoDeHorasMedida;
	}

	public Integer getTiempoDeDiasMedida() {
		return tiempoDeDiasMedida;
	}

	public void setTiempoDeDiasMedida(Integer tiempoDeDiasMedida) {
		this.tiempoDeDiasMedida = tiempoDeDiasMedida;
	}

	public Integer getTiempoDeMesesMedida() {
		return tiempoDeMesesMedida;
	}

	public void setTiempoDeMesesMedida(Integer tiempoDeMesesMedida) {
		this.tiempoDeMesesMedida = tiempoDeMesesMedida;
	}

	public Date getTiempoDeFechaFinMedida() {
		return tiempoDeFechaFinMedida;
	}


	public void setTiempoDeFechaFinMedida(Date tiempoDeFechaFinMedida) {
		this.tiempoDeFechaFinMedida = tiempoDeFechaFinMedida;
	}


	public int getTiempoCumplimiento() {
		return tiempoCumplimiento;
	}


	public void setTiempoCumplimiento(int tiempoCumplimiento) {
		this.tiempoCumplimiento = tiempoCumplimiento;
	}


	public Boolean getEstaApelada() {
		return estaApelada;
	}


	public void setEstaApelada(Boolean estaApelada) {
		this.estaApelada = estaApelada;
	}

	public ProcesoPenal getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoPenal proceso) {
		this.proceso = proceso;
	}

/*
	public List<MomentoProcesal> getMomentos() {
		return momentos;
	}


	public void setMomentos(List<MomentoProcesal> momentos) {
		this.momentos = momentos;
	}
	*/
}
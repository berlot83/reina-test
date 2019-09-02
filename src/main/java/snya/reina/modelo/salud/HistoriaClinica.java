package snya.reina.modelo.salud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;

import snya.reina.ReinaException;
import snya.reina.modelo.joven.Joven;

@Entity
@Table(name = "Reina_HistoriaClinica", catalog = "SistemasSNYA")
@Audited
public class HistoriaClinica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5509246339497244134L;
	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdHistoriaClinica")
	private Integer id;
	@Column(name = "FechaInicio")
	private Date fechaInicio;
	@Column(name = "FechaFin", nullable = true)
	private Date fechaFin;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = "IdPrimerDiagnostico")
	private EvolucionHistoriaClinica primerDiagnositco;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = "IdDiagnosticoActual")
	private EvolucionHistoriaClinica diagnosticoActual;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "historia")
	@OrderBy("fecha ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EvolucionHistoriaClinica> evolucion;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	public HistoriaClinica() {
	}
	
	public HistoriaClinica(Date fecha, TipoDeDiagnostico tipo,
			String diagnostico, String fase, String tratamiento,
			String observacion) {
		this.fechaInicio = fecha;
		this.fechaFin = null;
		
		EvolucionHistoriaClinica trat = new EvolucionHistoriaClinica(fecha, tipo, diagnostico, fase, tratamiento, observacion);
		this.primerDiagnositco = trat;
		this.diagnosticoActual = trat;
		this.evolucion = new ArrayList<EvolucionHistoriaClinica>();
		this.evolucion.add(trat);
		trat.setHistoria(this);
	}

	public void actualizarTratamiento(TipoDeDiagnostico tipo,
			String diagnostico, String fase, String tratamiento,
			String observacion) {
		
		Date fecha = new Date();
		EvolucionHistoriaClinica trat = new EvolucionHistoriaClinica(fecha, tipo, diagnostico, fase, tratamiento, observacion);
		this.diagnosticoActual = trat;
		this.evolucion.add(trat);
		trat.setHistoria(this);		
	}
	
	public void cerrarTratamiento(Date fecha, String fase, String observacion) throws ReinaException {
		if(this.getFechaFin() != null)
			throw new ReinaException("El tratamiento ya se encuentra cerrado");	
		if(this.getFechaInicio().after(fecha))
			throw new ReinaException("La fecha del cierre del tratamiento debe ser posterior a la de inicio");	
		
		this.setFechaFin(fecha);
		
		EvolucionHistoriaClinica trat = this.getDiagnosticoActual().nuevo(fecha, fase, observacion);
		this.getEvolucion().add(trat);
		trat.setHistoria(this);		
		this.setDiagnosticoActual(trat);		
	}
	
	public Integer getIdDiagnostico() {
		return this.getDiagnosticoActual().getTipoDiagnostico().getId();
	}
	
	public String getTextoDiagnostico() {
		return this.getDiagnosticoActual().getTipoDiagnostico().getNombre();
	}
	
	public String getDetalleDiagnostico() {
		return this.getDiagnosticoActual().getDetalleDiagnostico();
	}

	public String getFase() {
		return this.getDiagnosticoActual().getFase();
	}

	public String getTratamiento() {
		return this.getDiagnosticoActual().getTratamiento();
	}


	public String getObservacionDiagnostico() {
		return this.getDiagnosticoActual().getObservacion();
	}
	
	public Date getFechaActual() {
		return this.getDiagnosticoActual().getFecha();
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

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EvolucionHistoriaClinica getPrimerDiagnositco() {
		return primerDiagnositco;
	}

	public void setPrimerDiagnositco(EvolucionHistoriaClinica primerDiagnositco) {
		this.primerDiagnositco = primerDiagnositco;
	}

	public List<EvolucionHistoriaClinica> getEvolucion() {
		return evolucion;
	}

	public void setEvolucion(List<EvolucionHistoriaClinica> evolucion) {
		this.evolucion = evolucion;
	}

	public EvolucionHistoriaClinica getDiagnosticoActual() {
		return diagnosticoActual;
	}

	public void setDiagnosticoActual(EvolucionHistoriaClinica diagnosticoActual) {
		this.diagnosticoActual = diagnosticoActual;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}
}

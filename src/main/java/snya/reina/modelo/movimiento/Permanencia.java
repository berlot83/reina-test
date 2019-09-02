package snya.reina.modelo.movimiento;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.IntervencionPenal;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.recurso.ComponeRecurso;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;

@Entity
@Table(name = "Reina_Permanencia", catalog="SistemasSNYA")
public class Permanencia implements Comparable<Permanencia>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4940840299911344921L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdPermanencia")
	private Integer id;

	@Column(name = "IdGrupo")
	private Integer grupo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "IdJoven")
	private Joven joven;

	@Column(name="EsAmbitoInstitucion")
	private String esInstitucion = "I";	
	@ManyToOne(targetEntity=snya.reina.modelo.recurso.Recurso.class)
    @JoinColumn(name="IdAmbitoEjecucion")
	private AmbitoEjecucion ambitoEjecucion;

	@Column(name = "FechaInicio")
	private Date fechaInicio;

	@Column(name = "FechaFin")
	private Date fechaFin;

	@Column(name = "Observacion")
	private String observacion;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "IdProcesoPenal")
	private ProcesoPenal proceso;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "IdMedidaEnProcesoPenal")
	private MedidaEnProcesoPenal medida;

	@Column(name = "EsPorObraSocial")
	private Boolean esPorObraSocial;

	@Column(name = "EsReconocido")
	private Boolean esReconocido;

	@Column(name = "IdIngreso")
	private Integer ingreso;
	
	@ManyToOne
	@JoinColumn(name="IdIntervencion")
	private Intervencion intervencion;
	
	@ManyToOne
	@JoinColumn(name = "IdAmbitoEjecucionInternacion")
	private Recurso ambitoEjecucionInternacion;

	public Permanencia() {
		this.grupo = 0;
		this.esPorObraSocial = false;
		this.esReconocido = false;
		this.observacion = "";
		this.ambitoEjecucionInternacion = null;
	}

	public Permanencia(Intervencion intervencion, Joven joven, Date fecha,
			AmbitoEjecucion ambitoEjecucion, ProcesoPenal proceso,
			MedidaEnProcesoPenal medida) {
		this();
		this.intervencion = intervencion;
		this.joven = joven;
		this.fechaInicio = fecha;
		this.ambitoEjecucion = ambitoEjecucion;
		this.proceso = proceso;
		this.medida = medida;
		this.calcularGrupo(joven);
		this.ambitoEjecucionInternacion = null;
	}

	public boolean cumplePermanencia(Date fecha) {
		boolean esta = this.getAmbitoEjecucion().seCumplePermanencia()
				&& (this.getFechaInicio().compareTo(fecha) <= 0);

		if (this.getFechaFin() != null
				&& this.getFechaFin().compareTo(fecha) < 0)
			esta = false;

		return esta;
	}

	public boolean estaAbierto() {
		return (this.getFechaFin() == null);
	}

	public boolean ambitoEjecucionEs(ComponeRecurso componente) {
		return this.ambitoEjecucion.es(componente);
	}

	@Override
	public int compareTo(Permanencia o) {
		return this.getFechaInicio().compareTo(o.getFechaInicio()) * -1;
	}

	public String getFechaInicioTexto() {
		return Calendario.formatearFecha(this.getFechaInicio());
	}

	public String getFechaFinTexto() {
		return (this.getFechaFin() != null) ? Calendario.formatearFecha(this
				.getFechaFin()) : "";
	}

	public String getNombreAmbitoEjecucion() {
		return this.getAmbitoEjecucion().getNombreCompleto();
	}

	// /
	// / Metodos mismo ambito cambio de condiciones
	// /
	public Permanencia armarContinuidadPorUnificacion(Date fecha,
			ProcesoPenal proceso, EscritorNarrativoDeHistoria escritor) {
		// Cierro la permanencia anterior
		String texto = "Se cambia el proceso judicial que dicta el atravesamiento de la medida, debido a la unificacion de las causas del joven.";
		texto = escritor.sumarA(this.getObservacion(),
				"proc_" + proceso.getId(), texto);
		this.setObservacion(texto);
		this.setFechaFin(fecha);

		Permanencia nuevo = new Permanencia(this.getIntervencion(), this.getJoven(), fecha,
				this.getAmbitoEjecucion(), proceso, proceso.getMedidaImpuesta());
		nuevo.setEsPorObraSocial(esPorObraSocial);
		nuevo.setEsReconocido(esReconocido);
		nuevo.setGrupo(grupo);

		texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " permanece en "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " a partir del dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((proceso.getMedidaImpuesta() != null) ? " bajo la medida "
						+ proceso.getMedidaImpuesta().traerDetalle() : "");
		texto = escritor.sumarA(nuevo.getObservacion(),
				"proc_" + proceso.getId(), texto);
		nuevo.setObservacion(texto);

		return nuevo;
	}

	public void quitarContinuidadPorUnificacion(ProcesoPenal proceso,
			EscritorNarrativoDeHistoria escritor) {
		String texto;

		Iterator<Permanencia> iter = this.getJoven().getPermanencias()
				.iterator();
		Permanencia siguiente = null;
		while (iter.hasNext()) {
			Permanencia permanencia = (Permanencia) iter.next();
			if (permanencia.getFechaInicio().equals(this.getFechaFin())
					&& permanencia.getGrupo().equals(this.getGrupo())
					&& !permanencia.getId().equals(this.getId())) {
				siguiente = permanencia;
			}
		}

		String textoSiguiente = escritor.quitarDe(siguiente.getObservacion(),
				"proc_" + proceso.getId());
		siguiente.setObservacion(textoSiguiente);
		getJoven().getPermanencias().remove(siguiente);

		this.setFechaFin(siguiente.getFechaFin());
		texto = escritor.quitarDe(this.getObservacion(),
				"proc_" + proceso.getId());
		texto = escritor.concatenar(this.getObservacion(), textoSiguiente);
		this.setObservacion(texto);
	}

	
	// /
	// / RETORNO INTERNACION
	// /
	public Permanencia armarContinuidadPorRetornoInternacion(MovimientoRetornoInternacion internacion,
			ProcesoPenal proceso, MedidaEnProcesoPenal medida, EscritorNarrativoDeHistoria escritor) {
		// Cierro la permanencia anterior
		String texto = "Se realiza el movimiento de " + internacion.getTipo().getNombre() + " al " + internacion.getAmbitoDestino().getNombreCompleto();
		texto = escritor.sumarA(this.getObservacion(),
				"rinter_" + internacion.getAmbitoDestino().getId(), texto);
		this.setObservacion(texto);
		this.setFechaFin(internacion.getFecha());

		Permanencia nuevo = new Permanencia(this.getIntervencion(), this.getJoven(), internacion.getFecha(),
				internacion.getAmbitoDestino(), proceso, medida);
		nuevo.setEsPorObraSocial(esPorObraSocial);
		nuevo.setEsReconocido(esReconocido);
		nuevo.setGrupo(grupo);
		
		texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " retorna a ser alojado en "
				+ internacion.getAmbitoDestino().getNombreCompleto()
				+ " a partir del "
				+ Calendario.formatearFecha(internacion.getFecha());
		texto = escritor.sumarA(nuevo.getObservacion(),
				"rinter_" + internacion.getAmbitoDestino().getId(), texto);
		nuevo.setObservacion(texto);

		return nuevo;
	}
	
	public void quitarRetornoInternacion(MovimientoRetornoInternacion internacion,
			EscritorNarrativoDeHistoria escritor) {

		String texto = escritor.quitarDe(this.getObservacion(), "rinter_" + internacion.getAmbitoDestino().getId());
		this.setObservacion(texto);
	}

	public void actualizarCierrePorRetornoInternacion(MovimientoRetornoInternacion internacion, EscritorNarrativoDeHistoria escritor) {
		String texto = "Se realiza el movimiento de " + internacion.getTipo().getNombre() + " al " + internacion.getAmbitoDestino().getNombreCompleto();
		texto = escritor.reemplazarEn(this.getObservacion(), "rinter_" + internacion.getAmbitoDestino().getId(), texto);

		this.setObservacion(texto);
	}
	
	public void actualizarRetornoInternacion(MovimientoRetornoInternacion internacion, EscritorNarrativoDeHistoria escritor) {
		this.setProceso(internacion.getMedidaImpuesta() != null ? internacion.getMedidaImpuesta().getProceso() : null);
		this.setMedida(internacion.getMedidaImpuesta());
		
		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " retorna a ser alojado en "
				+ internacion.getAmbitoDestino().getNombreCompleto()
				+ " a partir del "
				+ Calendario.formatearFecha(internacion.getFecha());
		texto = escritor.sumarA(this.getObservacion(),
				"rinter_" + internacion.getAmbitoDestino().getId(), texto);
		this.setObservacion(texto);
	}
	
	public void actualizarRetornoInternacionDelProceso(MovimientoRetornoInternacion internacion,
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.setProceso(internacion.getMedidaImpuesta() != null ? internacion.getMedidaImpuesta().getProceso() : null);
		this.setMedida(internacion.getMedidaImpuesta());
		
		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " retorna a ser alojado en "
				+ internacion.getAmbitoDestino().getNombreCompleto()
				+ " a partir del "
				+ Calendario.formatearFecha(internacion.getFecha());
		texto = escritor.reemplazarEn(this.getObservacion(),
				"rinter_" + internacion.getAmbitoDestino().getId(), texto);
		this.setObservacion(texto);
	}

	
	// /
	// / INTERNACION
	// /
	public Permanencia armarContinuidadPorInternacion(MovimientoInternacion internacion,
			ProcesoPenal proceso, MedidaEnProcesoPenal medida, EscritorNarrativoDeHistoria escritor) {
		// Cierro la permanencia anterior
		String texto = "Se realiza el movimiento de " + internacion.getTipo().getNombre() + " al " + internacion.getAmbitoDestino().getNombreCompleto();
		texto = escritor.sumarA(this.getObservacion(),
				"inter_" + internacion.getAmbitoDestino().getId(), texto);
		this.setObservacion(texto);
		this.setFechaFin(internacion.getFecha());

		Permanencia nuevo = new Permanencia(this.getIntervencion(), this.getJoven(), internacion.getFecha(),
				this.getAmbitoEjecucion(), proceso, medida);
		nuevo.setEsPorObraSocial(esPorObraSocial);
		nuevo.setEsReconocido(esReconocido);
		nuevo.setGrupo(grupo);
		nuevo.setAmbitoEjecucionInternacion((Recurso) internacion.getAmbitoDestino());
		
		texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " permanece internado en " 
				+ nuevo.getAmbitoEjecucionInternacion().getNombreCompleto()
				+ " bajo la supervisión del "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " a partir del "
				+ Calendario.formatearFecha(internacion.getFecha());
		texto = escritor.sumarA(nuevo.getObservacion(),
				"inter_" + internacion.getAmbitoDestino().getId(), texto);
		nuevo.setObservacion(texto);

		return nuevo;
	}
	
	public void quitarInternacion(MovimientoInternacion internacion,
			EscritorNarrativoDeHistoria escritor) {

		String texto = escritor.quitarDe(this.getObservacion(), "inter_" + internacion.getAmbitoDestino().getId());
		this.setObservacion(texto);
	}

	public void actualizarCierrePorInternacion(MovimientoInternacion internacion, EscritorNarrativoDeHistoria escritor) {
		String texto = "Se realiza el movimiento de " + internacion.getTipo().getNombre() + " al " + internacion.getAmbitoDestino().getNombreCompleto();
		texto = escritor.reemplazarEn(this.getObservacion(), "inter_" + internacion.getAmbitoDestino().getId(), texto);

		this.setObservacion(texto);
	}
	
	public void actualizarInternacion(MovimientoInternacion internacion, EscritorNarrativoDeHistoria escritor) {
		this.setProceso(internacion.getMedidaImpuesta() != null ? internacion.getMedidaImpuesta().getProceso() : null);
		this.setMedida(internacion.getMedidaImpuesta());
		
		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " permanece internado en " 
				+ this.getAmbitoEjecucionInternacion().getNombreCompleto()
				+ " bajo la supervisión del "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " a partir del "
				+ Calendario.formatearFecha(this.getFechaInicio());
		texto = escritor.reemplazarEn(this.getObservacion(),
				"inter_" + internacion.getAmbitoDestino().getId(), texto);
		this.setObservacion(texto);
	}
	
	public void actualizarInternacionDelProceso(MovimientoInternacion internacion,
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.setProceso(internacion.getMedidaImpuesta() != null ? internacion.getMedidaImpuesta().getProceso() : null);
		this.setMedida(internacion.getMedidaImpuesta());
		
		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " permanece internado en " 
				+ this.getAmbitoEjecucionInternacion().getNombreCompleto()
				+ " bajo la supervisión del "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " a partir del "
				+ Calendario.formatearFecha(this.getFechaInicio());
		texto = escritor.reemplazarEn(this.getObservacion(),
				"inter_" + internacion.getAmbitoDestino().getId(), texto);
		this.setObservacion(texto);
	}
	
	// /
	// / BAJA
	// /
	public void asentarBaja(MovimientoBaja movimiento, ProcesoPenal proceso,
			AmbitoEjecucion ambitoEjecucionOrigen,
			EscritorNarrativoDeHistoria escritor) {
		String texto = "El joven " + joven.getDescripcionJoven() + " se da de "
				+ movimiento.traerDescripcion() + " desde "
				+ ambitoEjecucionOrigen.getNombreCompleto() + " el dia "
				+ Calendario.formatearFecha(this.getFechaFin()) + ".";

		texto = escritor.sumarA(this.getObservacion(),
				"mov_" + movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void quitarBaja(MovimientoBaja movimiento,
			EscritorNarrativoDeHistoria escritor) {
		/*
		 * if (this.getObservacion() != null)
		 * this.setObservacion(this.getObservacion
		 * ().replace("\\. El joven (.)+ se da de (.)+ ", ""));
		 */

		String texto = escritor.quitarDe(this.getObservacion(), "mov_"
				+ movimiento.getId());
		this.setObservacion(texto);
	}

	public void actualizarBaja(MovimientoBaja movimiento,
			EscritorNarrativoDeHistoria escritor) {
		this.setProceso(movimiento.getMedidaImpuesta() != null ? movimiento
				.getMedidaImpuesta().getProceso() : null);
		this.setMedida(movimiento.getMedidaImpuesta());

		String texto = "El joven " + joven.getDescripcionJoven() + " se da de "
				+ movimiento.traerDescripcion() + " desde "
				+ this.getAmbitoEjecucion().getNombreCompleto() + " el dia "
				+ Calendario.formatearFecha(this.getFechaFin()) + ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void actualizarBajaDelProceso(MovimientoBaja movimiento,
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.setMedida(proceso.traerMedidaImpuestaAl(this.getFechaInicio()));
		this.setProceso(proceso);

		String texto = "El joven " + joven.getDescripcionJoven() + " se da de "
				+ movimiento.traerDescripcion() + " desde "
				+ this.getAmbitoEjecucion().getNombreCompleto() + " el dia "
				+ Calendario.formatearFecha(this.getFechaFin()) + ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	// /
	// / EGRESO
	// /
	public void asentarEgreso(MovimientoEgreso movimiento,
			ProcesoPenal proceso, AmbitoEjecucion ambitoEjecucionOrigen,
			EscritorNarrativoDeHistoria escritor) {
		String texto = "El joven " + joven.getDescripcionJoven()
				+ " egresa por " + movimiento.traerDescripcion() + " desde "
				+ ambitoEjecucionOrigen.getNombreCompleto() + " el dia "
				+ Calendario.formatearFecha(this.getFechaFin()) + ".";

		texto = escritor.sumarA(this.getObservacion(),
				"mov_" + movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void quitarEgreso(MovimientoEgreso movimiento,
			EscritorNarrativoDeHistoria escritor) {
		/*
		 * if (this.getObservacion() != null)
		 * this.setObservacion(this.getObservacion
		 * ().replace("\\. El joven (.)+ egresa por (.)+ ", ""));
		 */

		String texto = escritor.quitarDe(this.getObservacion(), "mov_"
				+ movimiento.getId());
		this.setObservacion(texto);
	}

	public void actualizarEgreso(MovimientoEgreso movimiento,
			EscritorNarrativoDeHistoria escritor) {
		this.setProceso(movimiento.getMedidaImpuesta() != null ? movimiento
				.getMedidaImpuesta().getProceso() : null);
		this.setMedida(movimiento.getMedidaImpuesta());

		String texto = "El joven " + joven.getDescripcionJoven()
				+ " egresa por " + movimiento.traerDescripcion() + " desde "
				+ movimiento.traerAmbitoEjecucionOrigen().getNombreCompleto()
				+ " el dia " + Calendario.formatearFecha(this.getFechaFin())
				+ ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void actualizarEgresoDelProceso(MovimientoEgreso movimiento,
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.setMedida(proceso.traerMedidaImpuestaAl(this.getFechaInicio()));
		this.setProceso(proceso);

		String texto = "El joven " + joven.getDescripcionJoven()
				+ " egresa por " + movimiento.traerDescripcion() + " desde "
				+ movimiento.traerAmbitoEjecucionOrigen().getNombreCompleto()
				+ " el dia " + Calendario.formatearFecha(this.getFechaFin())
				+ ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	// /
	// / INGRESO
	// /
	public void asentarIngreso(MovimientoIngreso movimiento,
			EscritorNarrativoDeHistoria escritor) {
		String resultado = "";
		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " ingresa a "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((medida != null) ? " bajo la medida "
						+ medida.traerDetalle() : " bajo "
						+ movimiento.getMotivo().getNombre()) + ".";

		if (movimiento.getId() != null)
			resultado = escritor.sumarA(this.getObservacion(), "mov_"
					+ movimiento.getId(), texto);
		if (movimiento.getId() != null)
			resultado = escritor.reemplazarEn(resultado, "mov_0", texto);
		else
			resultado = escritor.sumarA(this.getObservacion(), "mov_0", texto);

		this.setObservacion(resultado);
	}

	public void actualizarIngreso(MovimientoIngreso movimiento,
			EscritorNarrativoDeHistoria escritor) {
		String resultado = "";
		this.setProceso(movimiento.getMedidaImpuesta() != null ? movimiento
				.getMedidaImpuesta().getProceso() : null);
		this.setMedida(movimiento.getMedidaImpuesta());

		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " ingresa a "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((medida != null) ? " bajo la medida "
						+ medida.traerDetalle() : " bajo "
						+ movimiento.getMotivo().getNombre()) + ".";

		if (movimiento.getId() != null)
			resultado = escritor.reemplazarEn(this.getObservacion(), "mov_"
					+ movimiento.getId(), texto);
		if (movimiento.getId() != null)
			resultado = escritor.reemplazarEn(resultado, "mov_0", texto);
		else
			resultado = escritor.reemplazarEn(this.getObservacion(), "mov_0",
					texto);

		this.setObservacion(resultado);
	}

	public void actualizarIngresoDelProceso(MovimientoIngreso movimiento,
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		String resultado = "";
		this.setMedida(proceso.traerMedidaImpuestaAl(this.getFechaInicio()));
		this.setProceso(proceso);

		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " ingresa a "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((medida != null) ? " bajo la medida "
						+ medida.traerDetalle() : " bajo "
						+ movimiento.getMotivo().getNombre()) + ".";

		if (movimiento.getId() != null)
			resultado = escritor.reemplazarEn(this.getObservacion(), "mov_"
					+ movimiento.getId(), texto);
		if (movimiento.getId() != null)
			resultado = escritor.reemplazarEn(resultado, "mov_0", texto);
		else
			resultado = escritor.reemplazarEn(this.getObservacion(), "mov_0",
					texto);

		this.setObservacion(resultado);

	}

	// /
	// / TRASLADO
	// /
	public void asentarCierrePorTraslado(MovimientoTraslado movimiento,
			ProcesoPenal proceso, MedidaEnProcesoPenal medida,
			AmbitoEjecucion ambitoEjecucionDestino,
			EscritorNarrativoDeHistoria escritor) {
		String texto = "Trasladado hacia "
				+ ambitoEjecucion.getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaFin())
				+ (((movimiento.getMotivo().esOrdenJudicial()) ? " bajo la medida "
						+ medida.traerDetalle()
						: " bajo " + movimiento.getMotivo().getNombre())) + ".";

		texto = escritor.sumarA(this.getObservacion(),
				"mov_" + movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void asentarMovimientoTraslado(MovimientoTraslado movimiento,
			TipoDeMotivoMovimiento motivo, ProcesoPenal proceso,
			EscritorNarrativoDeHistoria escritor) {
		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " ingresa, desde traslado, a "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((motivo.esOrdenJudicial() && proceso != null) ? " bajo la medida "
						+ proceso.getMedidaImpuesta().traerDetalle()
						: " bajo " + motivo.getNombre()) + ".";

		texto = escritor.sumarA(this.getObservacion(),
				"mov_" + movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void quitarTraslado(MovimientoTraslado movimiento,
			EscritorNarrativoDeHistoria escritor) {
		/*
		 * if (this.getObservacion() != null)
		 * this.setObservacion(this.getObservacion
		 * ().replace("\\. Trasladado hacia (.)+ el dia (.)+ ", ""));
		 */

		String texto = escritor.quitarDe(this.getObservacion(), "mov_"
				+ movimiento.getId());
		this.setObservacion(texto);
	}

	public void actualizarCierrPorTraslado(MovimientoTraslado movimiento,
			EscritorNarrativoDeHistoria escritor) {
		String texto = "Trasladado hacia "
				+ ambitoEjecucion.getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaFin())
				+ (((movimiento.getMotivo().esOrdenJudicial() && medida != null) ? " bajo la medida "
						+ medida.traerDetalle()
						: " bajo " + movimiento.getMotivo().getNombre())) + ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void actualizarTraslado(MovimientoTraslado movimiento,
			EscritorNarrativoDeHistoria escritor) {
		this.setProceso(movimiento.getMedidaImpuesta() != null ? movimiento
				.getMedidaImpuesta().getProceso() : null);
		this.setMedida(movimiento.getMedidaImpuesta());

		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " ingresa, desde traslado, a "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((movimiento.getMotivo().esOrdenJudicial() && proceso != null) ? " bajo la medida "
						+ proceso.getMedidaImpuesta().traerDetalle()
						: " bajo " + movimiento.getMotivo().getNombre()) + ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	public void actualizarTrasladoDelProceso(MovimientoTraslado movimiento,
			EscritorNarrativoDeHistoria escritor, ProcesoPenal proceso) {
		this.setProceso(movimiento.getMedidaImpuesta() != null ? movimiento
				.getMedidaImpuesta().getProceso() : null);
		this.setMedida(movimiento.getMedidaImpuesta());

		String texto = "El joven "
				+ this.getJoven().getDescripcionJoven()
				+ " ingresa, desde traslado, a "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " el dia "
				+ Calendario.formatearFecha(this.getFechaInicio())
				+ ((movimiento.getMotivo().esOrdenJudicial() && proceso != null) ? " bajo la medida "
						+ proceso.getMedidaImpuesta().traerDetalle()
						: " bajo " + movimiento.getMotivo().getNombre()) + ".";

		texto = escritor.reemplazarEn(this.getObservacion(), "mov_"
				+ movimiento.getId(), texto);
		this.setObservacion(texto);
	}

	// /
	// / REFERENCIA
	// /
	public void abrirInscripcion(IntervencionPenal intervencion,
			EscritorNarrativoDeHistoria escritor) {
		String resultado = "";
		String texto = "Comienza a intervenir el Centro "
				+ intervencion.getCentroDeReferencia().getNombre()
				+ " el dia "
				+ (Calendario.formatearFecha(intervencion
						.getFechaIntervencion())) + " bajo la medida "
				+ intervencion.getMedida().traerDetalle() + ".";

		if (intervencion.getId() != null)
			resultado = escritor.sumarA(this.getObservacion(), "interv_"
					+ intervencion.getId(), texto);
		if (intervencion.getId() != null)
			resultado = escritor.reemplazarEn(resultado, "interv_0", texto);
		else
			resultado = escritor.sumarA(this.getObservacion(), "interv_0",
					texto);

		this.setObservacion(texto);
	}

	public void cerrarInscripcion(IntervencionPenal intervencion,
			EscritorNarrativoDeHistoria escritor) {
		this.setFechaFin(intervencion.getFechaBaja());

		String texto = "Concluida el dia "
				+ (Calendario.formatearFecha(intervencion.getFechaBaja()))
				+ " debido a "
				+ intervencion.getMotivoBajaIntervencion().getNombre() + ".";
		if (intervencion.getId() != null)
			texto = escritor.sumarA(this.getObservacion(), "interv_"
					+ intervencion.getId(), texto);
		texto = escritor.reemplazarEn(texto, "interv_0", texto);
		this.setObservacion(texto);
	}

	public void actualizarInscripcion(IntervencionPenal intervencion,
			EscritorNarrativoDeHistoria escritor) {
		String resultado = null;
		this.setProceso(intervencion.getMedida().getProceso());
		this.setMedida(intervencion.getMedida());

		String texto = "Comienza a intervenir el "
				+ intervencion.getCentroDeReferencia().getNombre()
				+ " el dia "
				+ (Calendario.formatearFecha(intervencion
						.getFechaIntervencion())) + " bajo la medida "
				+ intervencion.getMedida().traerDetalle() + ".";

		if (intervencion.getId() != null)
			resultado = escritor.reemplazarEn(this.getObservacion(), "interv_"
					+ intervencion.getId(), texto);
		if (intervencion.getId() != null)
			resultado = escritor.reemplazarEn(resultado, "interv_0", texto);
		else
			resultado = escritor.reemplazarEn(this.getObservacion(),
					"interv_0", texto);

		this.setObservacion(texto);
	}

	// /
	// / Metodos que acomodan la permanencia
	// /
	public void acomodarProcesoDePermanencia(ProcesoPenal proceso,
			MedidaEnProcesoPenal medida, Date fecha,
			EscritorNarrativoDeHistoria escritor) {
		Date fechaFin = this.getFechaFin();

		String texto = "El joven deja de estar cumpliendo a partir del dia "
				+ Calendario.formatearFecha(fecha) + " por "
				+ this.getProceso().getMedidaImpuesta().traerDetalle();
		texto = escritor.sumarA(this.getObservacion(), "proc_"
				+ this.getProceso().getId(), texto);
		this.setObservacion(texto);
		this.setFechaFin(fecha);

		Permanencia per = new Permanencia(this.getIntervencion(), this.getJoven(), fecha,
				this.getAmbitoEjecucion(), proceso, medida);
		per.setGrupo(this.getGrupo());
		texto = "El joven " + this.getJoven().getDescripcionJoven()
				+ " se encuentra en el "
				+ this.getAmbitoEjecucion().getNombreCompleto()
				+ " a partir del dia " + Calendario.formatearFecha(fecha)
				+ " bajo la medida " + medida.traerDetalle() + ".";
		texto = escritor.sumarA("", "proc_" + proceso.getId(), texto);
		per.setObservacion(texto);
		per.setFechaFin(fechaFin);

		joven.getPermanencias().add(per);
	}

	// /
	// / Metodos Varios
	// /
	public boolean seCumplePermanencia() {
		return this.getAmbitoEjecucion().seCumplePermanencia();
	}

	public boolean estaCumpliendoPermanencia() {
		return this.getAmbitoEjecucion().seCumplePermanencia()
				&& this.estaVigente();
	}

	public boolean estaVigente() {
		return (this.getFechaFin() == null);
	}

	private void calcularGrupo(Joven joven) {
		Permanencia per = null;
		int max = -1;

		for (Permanencia permanencia : joven.getPermanencias()) {
			if (permanencia.getGrupo() > max) {
				max = permanencia.getGrupo();
				per = permanencia;
			}
		}

		this.setGrupo(max + 1);
		if (per != null) this.setIngreso(per.getIngreso());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public AmbitoEjecucion getAmbitoEjecucion() {
		return ambitoEjecucion;
	}

	public void setAmbitoEjecucion(AmbitoEjecucion ambitoEjecucion) {
		this.ambitoEjecucion = ambitoEjecucion;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Boolean getEsPorObraSocial() {
		return esPorObraSocial;
	}

	public void setEsPorObraSocial(Boolean esPorObraSocial) {
		this.esPorObraSocial = esPorObraSocial;
	}

	public Boolean getEsReconocido() {
		return esReconocido;
	}

	public void setEsReconocido(Boolean esReconocido) {
		this.esReconocido = esReconocido;
	}

	public MedidaEnProcesoPenal getMedida() {
		return medida;
	}

	public void setMedida(MedidaEnProcesoPenal medida) {
		this.medida = medida;
	}

	public ProcesoPenal getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoPenal proceso) {
		this.proceso = proceso;
	}

	public Integer getIngreso() {
		return ingreso;
	}

	public void setIngreso(Integer ingreso) {
		this.ingreso = ingreso;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	public void setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}

	public Recurso getAmbitoEjecucionInternacion() {
		return ambitoEjecucionInternacion;
	}

	public void setAmbitoEjecucionInternacion(Recurso ambitoEjecucionInternacion) {
		this.ambitoEjecucionInternacion = ambitoEjecucionInternacion;
	}
}

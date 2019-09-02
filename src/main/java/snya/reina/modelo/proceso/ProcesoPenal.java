package snya.reina.modelo.proceso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.general.modelo.DepartamentoJudicial;
import snya.reina.modelo.institucion.*;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.intervencion.IntervencionPenal;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.serviciomodelo.proceso.AcomodadorMedida;
import snya.reina.serviciomodelo.proceso.AcomodadorMomento;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;
import snya.reina.serviciomodelo.resultado.ResultadoProceso;

@Entity
@Table(name="Reina_ProcesoPenal", catalog="SistemasSNYA")
@Audited
public class ProcesoPenal implements  Comparable<ProcesoPenal>, Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5076408096450277659L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdProcesoPenal")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@Column(name="IPP")
	private String IPP;
	
	@Column(name="Causa")
	private String nroCausa;
	
	@Column(name="Carpeta")
	private String nroCarpeta;
	
	@ManyToOne
	@JoinColumn(name="IdOrganoJudicialOriginal")
	private OrganoJudicial organoJudicialOriginal;
	
	@ManyToOne
	@JoinColumn(name="IdOrganoJudicial")
	private OrganoJudicial organoJudicial;
	
	@ManyToOne
	@JoinColumn(name="IdFiscalia")
	private Fiscalia fiscalia;
	
	@ManyToOne
	@JoinColumn(name="IdDefensoria")
	private Defensoria defensoria;
	
	@ManyToOne
	@JoinColumn(name="IdDefensor")
	private ContactoInstitucion defensor;
	
	@Column(name="Abogado")
	private String abogado;
	
	@Column(name="EsDefensorOficial")
	private Boolean esDefensorOficial;
	
	@Column(name="FechaIngreso")
	private java.util.Date fechaIngreso;
	
	@Column(name="EstaFinalizado")
	private Boolean estaFinalizado;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeSituacionProcesal")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeSituacionProcesal situacionProcesal;
	
	@ManyToOne
	@JoinColumn(name="IdProcesoUnificador")
	private ProcesoPenal procesoUnificador;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="IdMotivoIntervencion")
	private MotivoIntervencionEnProcesoPenal motivoIntervencion;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true, mappedBy="proceso")
	@OrderBy ("fechaMotivo ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private java.util.List<MotivoIntervencionEnProcesoPenal> motivosIntervencion;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true, mappedBy="proceso")
	@OrderBy ("fechaImposicion ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private java.util.List<MomentoProcesal> momentosProcesales;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true, mappedBy="proceso")
	@OrderBy ("fechaMedida ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private java.util.List<MedidaEnProcesoPenal> medidasImpuestas;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true, mappedBy="proceso")
	@OrderBy ("fecha ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NotaProcesoPenal> notas;

	
	/* === Constructores === */
	public ProcesoPenal() {
		this.setMotivosIntervencion(new java.util.ArrayList<MotivoIntervencionEnProcesoPenal>());
		this.setMomentosProcesales(new java.util.ArrayList<MomentoProcesal>());
		this.setMedidasImpuestas(new java.util.ArrayList<MedidaEnProcesoPenal>());
		this.setNotas(new ArrayList<NotaProcesoPenal>());
	}

	
	public ProcesoPenal(Date fechaIngreso, OrganoJudicial juzgado,
			Fiscalia fiscalia, Boolean esDefensorOficial,
			Defensoria defensoria, ContactoInstitucion defensor, String abogado, String iPP,
			String nroCarpeta, String nroCausa, Joven joven) {
		this.setFechaIngreso(fechaIngreso);
		this.setOrganoJudicial(juzgado);
		this.setOrganoJudicialOriginal(juzgado);
		this.setFiscalia(fiscalia);
		this.setEsDefensorOficial(esDefensorOficial);
		this.setDefensoria(defensoria);
		this.setDefensor(defensor);
		this.setAbogado(abogado);
		this.setIPP(iPP);
		this.setNroCarpeta(nroCarpeta);
		this.setNroCausa(nroCausa);
		this.setEstaFinalizado(false);
		this.setMotivosIntervencion(new java.util.ArrayList<MotivoIntervencionEnProcesoPenal>());
		this.setMomentosProcesales(new java.util.ArrayList<MomentoProcesal>());
		this.setMedidasImpuestas(new java.util.ArrayList<MedidaEnProcesoPenal>());
		this.setNotas(new ArrayList<NotaProcesoPenal>());
		this.setMotivoIntervencion(null);
		this.setJoven(joven);
	}


	/* === Metodos === */
	//
	// MOTIVO INTERVENCION
	//	
	public void agregarMotivoIntervencion(OrganoJudicial organoJudicial,
			Date fecha, TipoDeMotivoIntervencion tipo, Boolean gradoTentativa, String observacion) throws ReinaException {
		MotivoIntervencionEnProcesoPenal ultimo = this.getMotivoIntervencion();
		if (ultimo != null) fecha = Calendario.sumarHorario(fecha, ultimo.getFechaMotivo());
		
		// <<validaciones>>
		// valida que la medida a ingresar sea posterior a la actual
		if (this.getMotivosIntervencion().size() > 0) {
			if (this.getMotivoIntervencion().getFechaMotivo()
					.compareTo(fecha) >= 0) {
				throw new ReinaException(ReinaCte.FECHA_MOTIVO_FUERA_DE_TERMINO);
			}
		}
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		
		MotivoIntervencionEnProcesoPenal motivo = new MotivoIntervencionEnProcesoPenal(
				fecha, organoJudicial, tipo, gradoTentativa, observacion);
		
		this.getMotivosIntervencion().add(motivo);
		motivo.setProceso(this);
		this.setMotivoIntervencion(motivo);
	}
	
	public void modificarMotivoIntervencion(Integer id,
			OrganoJudicial organoJudicial, Date fecha,
			TipoDeMotivoIntervencion tipo, Boolean gradoTentativa,
			String observacion) throws ReinaException {
		MotivoIntervencionEnProcesoPenal anteUltimo = (this.getMotivosIntervencion().size() > 1) ? this.getMotivosIntervencion().get(this.getMotivosIntervencion().size() - 2) : null;
		MotivoIntervencionEnProcesoPenal ultimo = this.getMotivoIntervencion();
		if (anteUltimo != null) fecha = Calendario.sumarHorario(fecha, anteUltimo.getFechaMotivo());
		
		// <<validaciones>>
		// valida que sea el ultimo
		if (!ultimo.getId().equals(id)) {
			throw new ReinaException(ReinaCte.NO_MODIFICA_ULTIMO_MOTIVO);
		}
		// valida que la medida a ingresar sea posterior a la actual
		if (anteUltimo != null) {
			if (anteUltimo.getFechaMotivo().compareTo(fecha) >= 0) {
				throw new ReinaException(ReinaCte.FECHA_MOTIVO_FUERA_DE_TERMINO);
			}
		} else {
			if (this.getFechaIngreso().compareTo(fecha) >= 0 && !Calendario.mismoDia(this.getFechaIngreso(),fecha)) {
				throw new ReinaException(ReinaCte.FECHA_MOTIVO_FUERA_DE_TERMINO);
			}			
		}			
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		
		
		ultimo.setFechaMotivo(fecha);
		ultimo.setOrganoJudicial(organoJudicial);
		ultimo.setTipo(tipo);
		ultimo.setGradoTentativa(gradoTentativa);
		ultimo.setObservacion(observacion);
	}
	
	public void eliminarMotivoIntervencion(Integer id) throws ReinaException {
		MotivoIntervencionEnProcesoPenal ultimo = this.getMotivoIntervencion();
		
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		// valida que haya mas de uno
		if (this.getMotivosIntervencion().size() <= 1) {
			throw new ReinaException(ReinaCte.SOLO_UN_MOTIVO);
		}
		// valida que sea el ultimo
		if (!ultimo.getId().equals(id)) {
			throw new ReinaException(ReinaCte.NO_ELIMINA_ULTIMO_MOTIVO);
		}
		
		ultimo.setProceso(null);
		this.getMotivosIntervencion().remove(ultimo);
		this.setMotivoIntervencion(this.getMotivosIntervencion().get(this.getMotivosIntervencion().size() - 1));		
	}

	
	//
	// MOMENTOS PROCESALES
	//
	public ResultadoProceso agregarMomentoProcesal(AdministradorDeProceso dao, Date fecha, OrganoJudicial organoJudicial, TipoDeMomentoProcesal tipo,
			TipoDeMedidaEnProcesoPenal tipoMedida, List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles, String observacionMedida, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		return this.agregarMomentoProcesal(dao, tipo, fecha, organoJudicial, null, tipoMedida, detalles, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
	}
	
	public ResultadoProceso agregarMomentoProcesal(AdministradorDeProceso dao, TipoDeMomentoProcesal tipo,
			Date fecha, OrganoJudicial organoJudicial, Fiscalia fiscalia,
			TipoDeMedidaEnProcesoPenal tipoMedida, List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AcomodadorMomento acomodor = new AcomodadorMomento();	
		
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		
		// <<procesamiento>>
		// agregar momento
		acomodor.insertarMomento(dao, this, fecha, (organoJudicial != null) ? organoJudicial : fiscalia, tipo);
		
		// Agregar medida
		return this.agregarMedidas(dao, fecha, organoJudicial, fiscalia, tipoMedida, detalles, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);		
	}
	
	public void agregarMomentoProcesal(
			AdministradorDeProceso dao, Date fecha, OrganoJudicial organoJudicial, TipoDeMomentoProcesal tipo) throws ReinaException {
		AcomodadorMomento acomodor = new AcomodadorMomento();
		
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		// valida no poner el momento procesal dos veces
		acomodor.validarQueNoExisteMomento(this, tipo, null);
		
		// <<procesamiento>>		
		acomodor.insertarMomento(dao, this, fecha, organoJudicial, tipo);
	}
	
	public void modificarMomentoProcesal(AdministradorDeProceso dao, Integer id, Date fecha, OrganoJudicial organoJudicial, TipoDeMomentoProcesal tipo) throws ReinaException {
		AcomodadorMomento acomodor = new AcomodadorMomento();
		MomentoProcesal momento = this.traerMomentoProcesalPorId(id);
		
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		// valida no poner el momento procesal dos veces
		acomodor.validarQueNoExisteMomento(this, tipo, momento);
		
		// <<procesamiento>>
		// modificar el momento
		acomodor.modificarMomento(dao, this, momento, fecha, tipo, organoJudicial);		
	}

	public ResultadoProceso modificarMomentoProcesal(
			AdministradorDeProceso dao,
			Integer idMomento, Date fecha, OrganoJudicial organoJudicial,
			TipoDeMomentoProcesal tipo, Date fechaMedida, TipoDeMedidaEnProcesoPenal tipoMedida,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles,
			String observacionMedida, Integer horasDeMedida,
			Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AcomodadorMomento acomodor = new AcomodadorMomento();
		MomentoProcesal momento = this.traerMomentoProcesalPorId(idMomento);
		
		// <<procesamiento>>
		// modificar el momento
		acomodor.modificarMomento(dao, this, momento, fecha, tipo, organoJudicial);
		
		// modificar la medida
		return this.modificarMedida(dao, this.getMedidaImpuesta().getId(), fechaMedida, organoJudicial, null, tipoMedida, detalles, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
	}
		
	public void eliminarMomentoProcesal(AdministradorDeProceso dao, Integer id) throws ReinaException {
		AcomodadorMomento acomodor = new AcomodadorMomento();
		MomentoProcesal ultimo = this.getMomentoProcesal();
		
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		// valida que haya mas de uno
		if (this.getMomentosProcesales().size() <= 1) {
			throw new ReinaException(ReinaCte.SOLO_UN_MOMENTO);
		}
		// valida que sea el ultimo
		if (!ultimo.getId().equals(id)) {
			throw new ReinaException(ReinaCte.NO_ELIMINA_ULTIMO_MOMENTO);
		}
		
		// <<procesamiento>>
		// eliminar el momento
		acomodor.eliminarMomento(dao, this, ultimo);
		
		// acomodar los movimientos
		dao.acomodarMovimientos(this);
	}
	
	
	//
	// MEDIDAS
	//
	public ResultadoProceso agregarMedida(AdministradorDeProceso dao, Date fecha, OrganoJudicial organoJudicial,
			TipoDeMedidaEnProcesoPenal tipo,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles, String observacionMedida, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		return this.agregarMedidas(dao, fecha, organoJudicial, null,
				tipo, detalles, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);		
	}
	
	public ResultadoProceso agregarMedidas(AdministradorDeProceso dao, Date fecha,
			OrganoJudicial organoJudicial, Institucion institucionAdopta, TipoDeMedidaEnProcesoPenal tipo,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles, String observacion, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AcomodadorMedida acomodador = new AcomodadorMedida();
				
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);		
		
		// <<procesamiento>>
		// creo el objeto
		MedidaEnProcesoPenal medida = new MedidaEnProcesoPenal(fecha, (organoJudicial != null) ? organoJudicial : institucionAdopta, observacion, tipo,
				detalles, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida, this);
		
		// lo inserto en la historia de las medidas
		ResultadoProceso resultado = acomodador.insertarMedida(dao, this, medida);
		
		// acomodar los movimientos
		dao.acomodarMovimientos(this);
		
		return resultado;
	}
		
	public ResultadoProceso modificarMedida(Integer id,
			AdministradorDeProceso dao, Date fecha,
			OrganoJudicial organoJudicial, Institucion institucionAdopta,
			TipoDeMedidaEnProcesoPenal tipo,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles,
			String observacionMedida, Integer horasDeMedida,
			Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		return this.modificarMedida(dao, id, fecha, organoJudicial, institucionAdopta,
				tipo, detalles, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);		
	}
	
	public ResultadoProceso modificarMedida(AdministradorDeProceso dao, Integer id, Date fecha,
			OrganoJudicial organoJudicial, Institucion institucionAdopta, TipoDeMedidaEnProcesoPenal tipo,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles, String observacion, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AcomodadorMedida acomodador = new AcomodadorMedida();
		MedidaEnProcesoPenal medida = this.traerMedidaPorId(id);
				
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);		
		// valida que la medida no este afecada con la intervencion
		//this.validaQueMedidaNoEsteEnIntervencionesSRPJ(medida);
		
		// <<procesamiento>>		
		// modifico en la historia de las medidas
		ResultadoProceso resultado = acomodador.modificarMedida(dao, this, medida, fecha, organoJudicial, institucionAdopta, tipo,
				detalles, observacion, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		
		// acomodar los movimientos
		dao.acomodarMovimientos(this);
		
		return resultado;
	}
	
	public void eliminarMedida(AdministradorDeProceso dao, Integer id) throws ReinaException {
		AcomodadorMedida acomodador = new AcomodadorMedida();
		
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);		
		// valida que haya mas de uno
		if (this.getMedidasImpuestas().size() <= 1) {
			throw new ReinaException(ReinaCte.SOLO_UNA_MEDIDA);
		}
		// valida que sea el ultimo
		MedidaEnProcesoPenal primer = this.getPrimerMedidaImpuesta();
		if (primer.getId().equals(id)) {
			throw new ReinaException(ReinaCte.NO_ELIMINA_PRIMERA_MEDIDA);
		}		
		MedidaEnProcesoPenal medida =  this.traerMedidaPorId(id);
		// valida que la medida no tenga intervenciones territoriales srpj
		this.validaQueMedidaNoEsteEnIntervencionesSRPJ(medida);
		
		
		// <<procesamiento>>		
		// elimino la medida
		acomodador.eliminarMedida(dao, this, medida);
		
		// acomodar los movimientos
		dao.acomodarMovimientos(this);
	}

	
	//
	// OTROS METODOS
	//	
	public void reabrirProcesoPenal(AdministradorDeProceso dao) throws ReinaException {
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (!this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_NO_FINALIZADO);
		
		// <<procesamiento>>		
		MedidaEnProcesoPenal medida =  this.getMedidaImpuesta();
		if (medida.getTipo().isCierraProceso()) {
			this.validaQueMedidaNoEsteEnMovimientos(medida);
			
			this.getMomentoProcesal().setFechaFin(null);
			this.getMomentoProcesal().getMedidasImpuestas().remove(medida);
			
			this.getMedidasImpuestas().remove(medida);
			medida.setProceso(null);
			
			this.getMedidaImpuesta().setFechaFinMedida(null);
			this.getMedidaImpuesta().recalcularTiempos();
			
			this.actualizarSituacionProcesal(dao, this.getMomentoProcesal().getTipo(), this.getMedidaImpuesta().getTipo());
		}
		this.setEstaFinalizado(false);
	}
	
	public void unificarAlProceso(Date fecha, TipoDeSituacionProcesal tipoDeSituacion, ProcesoPenal unificador) throws ReinaException {
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_FINALIZADO);
		// valida que la fecha sea mas grande que la ultima medida adoptada
		if (fecha.before(this.getMedidaImpuesta().getFechaMedida()))
			throw new ReinaException(ReinaCte.FECHA_UNIFICACION_MENOR_MEDIDA);
			
		// <<procesamiento>>
		this.getMomentoProcesal().setFechaFin(fecha);
		this.getMedidaImpuesta().setFechaFinMedida(fecha);
		this.setEstaFinalizado(true);
		
		this.setProcesoUnificador(unificador);
		this.setSituacionProcesal(tipoDeSituacion);		
	}
	
	public void recobrarAutonomia(AdministradorDeProceso dao) throws ReinaException {
		// <<validaciones>>
		// valida que el proceso no este cerrado
		if (!this.getEstaFinalizado())
			throw new ReinaException(ReinaCte.PROCESO_NO_FINALIZADO);
			
		// <<procesamiento>>
		this.getMomentoProcesal().setFechaFin(null);
		this.getMedidaImpuesta().setFechaFinMedida(null);
		this.getMedidaImpuesta().recalcularTiempos();
		this.actualizarSituacionProcesal(dao, this.getMomentoProcesal().getTipo(), this.getMedidaImpuesta().getTipo());
		this.setEstaFinalizado(false);
		
		this.setProcesoUnificador(null);	
	}
	
	public void puedeSerEliminado() throws ReinaException {
		for (MedidaEnProcesoPenal medida : this.getMedidasImpuestas()) {
			// valida que la medida no tenga movimiento
			this.validaQueMedidaNoEsteEnMovimientos(medida);
			
			// valida que la medida no tenga intervenciones territoriales srpj
			this.validaQueMedidaNoEsteEnIntervencionesSRPJ(medida);
		}
		
		//TODO valida que el proceso no este en permanencia
	}

	public MedidaEnProcesoPenal getMedidaImpuesta() {
		List<MedidaEnProcesoPenal> lista = new ArrayList<MedidaEnProcesoPenal>(this.getMedidasImpuestas());
		Collections.sort(lista);
		
		return (lista.size() > 0) ?  lista.get(lista.size() - 1) : null;
	}

	public MedidaEnProcesoPenal getPrimerMedidaImpuesta() {
		List<MedidaEnProcesoPenal> lista = new ArrayList<MedidaEnProcesoPenal>(this.getMedidasImpuestas());
		Collections.sort(lista);
		
		return (lista.size() > 0) ?  lista.get(0) : null;
	}
	
	public MomentoProcesal getMomentoProcesal() {
		Comparator<MomentoProcesal> momento_orden = new Comparator<MomentoProcesal>() {
			public int compare(MomentoProcesal m1, MomentoProcesal m2) {
				return m1.getFechaImposicion().compareTo(m2.getFechaImposicion());
			}
		};
		List<MomentoProcesal> lista = new ArrayList<MomentoProcesal>(this.getMomentosProcesales());
		Collections.sort(lista, momento_orden);
		
		return (lista.size() > 0) ?  lista.get(lista.size() - 1) : null;
	}
	
	public String getDetalleDefensoria() {
		if (this.isEsDefensorOficial() != null) {
			if (this.isEsDefensorOficial()) {
				String datos = (this.getDefensor() != null) ? this.getDefensor().getNombre() : "";
				datos += " " + this.getDefensoria().getNombre();
				return datos;
			} else
				return this.getAbogado();
		} else {
			return "";
		}
	}
	
	public String getNumeroIdentificatorio() {
		String numero = "";

		if (this.getIPP() != null && !this.getIPP().equals("")) {
			numero = "I.P.P. " + this.getIPP();
		}
		if (!numero.equals("") && this.getNroCarpeta() != null) {
			numero += " - ";
		}
		if (this.getNroCarpeta() != null) {
			numero += "NºG. " + this.getNroCarpeta();
		}
		if (!numero.equals("") && this.getNroCausa() != null) {
			numero += " - ";
		}
		if (this.getNroCausa() != null) {
			numero += "NºResp. " + this.getNroCausa();
		}

		return numero;
	}

	private MomentoProcesal traerMomentoProcesalPorId(Integer id) {
		Iterator<MomentoProcesal> momentos = this.getMomentosProcesales().iterator();
		MomentoProcesal momentoEnFecha = null;
		
		while (momentos.hasNext()) {
			MomentoProcesal mom = (MomentoProcesal) momentos.next();
			if (mom.getId().equals(id))
				momentoEnFecha = mom;
		}
		
		return momentoEnFecha;
	}
	
	public MomentoProcesal traerMomentoProcesalAl(Date fecha) {
		Iterator<MomentoProcesal> momentos = this.getMomentosProcesales().iterator();
		MomentoProcesal momentoEnFecha = null;
		
		while (momentos.hasNext()) {
			MomentoProcesal mom = (MomentoProcesal) momentos.next();
			if (mom.vigenteAl(fecha))
				momentoEnFecha = mom;
		}
		
		return momentoEnFecha;
	}
	
	public MedidaEnProcesoPenal traerMedidaImpuestaAl(Date fecha) {
		List<MedidaEnProcesoPenal> lista = new ArrayList<MedidaEnProcesoPenal>( this.getMedidasImpuestas() );
		Collections.sort(lista);
		Iterator<MedidaEnProcesoPenal> medidas = lista.iterator();
		MedidaEnProcesoPenal medidaEnFecha = null;
		
		while (medidas.hasNext()) {
			MedidaEnProcesoPenal med = (MedidaEnProcesoPenal) medidas.next();
			if (med.vigenteAl(fecha))
				medidaEnFecha = med;
		}
		
		return medidaEnFecha;
	}
	
	public MedidaEnProcesoPenal traerMedidaPorId(Integer idMedida) {
		Iterator<MedidaEnProcesoPenal> medidas = this.getMedidasImpuestas().iterator();
		MedidaEnProcesoPenal medida = null;
		
		while (medidas.hasNext()) {
			MedidaEnProcesoPenal med = (MedidaEnProcesoPenal) medidas.next();
			if (med.getId().equals(idMedida))
				medida = med;
		}
		
		return medida;
	}
	

	public boolean estaCerradoPorNota() {
		boolean notaCierre = false;
		Iterator<NotaProcesoPenal> it = this.getNotas().iterator();
		while (!notaCierre && it.hasNext()) {
			NotaProcesoPenal nota = (NotaProcesoPenal) it.next();
			notaCierre = nota.esDeCierre();
		}
		
		return notaCierre && this.getEstaFinalizado(); 
	}
	
	public NotaProcesoPenal ultimaAccionSobreMedida(Integer idMedida) {
		Comparator<NotaProcesoPenal> nota_orden = new Comparator<NotaProcesoPenal>() {
			public int compare(NotaProcesoPenal m1, NotaProcesoPenal m2) {
				return m1.getFecha().compareTo(m2.getFecha());
			}
		};
		List<NotaProcesoPenal> lista = new ArrayList<NotaProcesoPenal>(this.getNotas());
		Collections.sort(lista, nota_orden);
		
		NotaProcesoPenal nota = null;		
		for (NotaProcesoPenal nPP : lista) {
			try {
				if (nPP.getTipo().getRequiereMedida() && nPP.getIdReferencia().equals(idMedida))
					nota = nPP;
			} catch (ReinaException e) { }
		}
		
		return nota;
	}
	
	@Override
	public int compareTo(ProcesoPenal o) {
		return this.getFechaIngreso().compareTo(o.getFechaIngreso()) * -1;
	}
	
	public void actualizarSituacionProcesal(AdministradorDeProceso dao, TipoDeMomentoProcesal momento, TipoDeMedidaEnProcesoPenal medida) {
		TipoDeSituacionProcesal situacion = dao.traerSituacionProcesal(momento.getId(), medida.getId());
		if (situacion != null) this.setSituacionProcesal(situacion);
	}
	
	private void validaQueMedidaNoEsteEnMovimientos(MedidaEnProcesoPenal medida) throws ReinaException {
		boolean tiene = false;
		for (Movimiento mov : this.getJoven().getMovimientos()){
			tiene = tiene || (mov.getMedidaImpuesta() != null && mov.getMedidaImpuesta().getId().equals(medida.getId()));
		}
		if (tiene)
			throw new ReinaException(ReinaCte.EXISTEN_MOVIMIENTOS_PARA_MEDIDA);
	}
	
	public void validaQueMedidaNoEsteEnIntervencionesSRPJ(MedidaEnProcesoPenal medida) throws ReinaException {
		boolean tiene = false;
		for (IntervencionPenal interv : this.getJoven().getIntervencionesSRPJ()){
			tiene = tiene || (interv.getMedida() != null && interv.getMedida().getId().equals(medida.getId()));
		}
		if (tiene)
			throw new ReinaException(ReinaCte.EXISTEN_INTERVENCIONES_PARA_MEDIDA);
	}
	
	public DepartamentoJudicial traerDepartamentoJudicial() {
		return (this.getOrganoJudicial() != null) 
				? this.getOrganoJudicial().getDepartamentoJudicial() 
				: ( (this.getFiscalia() != null) ? this.getFiscalia().getDepartamentoJudicial() : null);
	}
	
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven value) {
		joven = value;
	}

	public String getIPP() {
		return IPP;
	}

	public void setIPP(String value) {
		IPP = value;
	}

	public String getNroCausa() {
		return nroCausa;
	}

	public void setNroCausa(String value) {
		nroCausa = value;
	}

	public String getNroCarpeta() {
		return nroCarpeta;
	}

	public void setNroCarpeta(String value) {
		nroCarpeta = value;
	}

	public OrganoJudicial getOrganoJudicialOriginal() {
		return organoJudicialOriginal;
	}


	public void setOrganoJudicialOriginal(OrganoJudicial organoJudicialOriginal) {
		this.organoJudicialOriginal = organoJudicialOriginal;
	}


	public OrganoJudicial getOrganoJudicial() {
		return organoJudicial;
	}

	public void setOrganoJudicial(OrganoJudicial organoJudicial) {
		this.organoJudicial = organoJudicial;
	}

	public Fiscalia getFiscalia() {
		return fiscalia;
	}

	public void setFiscalia(Fiscalia fiscalia) {
		this.fiscalia = fiscalia;
	}

	public Defensoria getDefensoria() {
		return defensoria;
	}

	public void setDefensoria(Defensoria defensoria) {
		this.defensoria = defensoria;
	}

	public ContactoInstitucion getDefensor() {
		return defensor;
	}


	public void setDefensor(ContactoInstitucion defensor) {
		this.defensor = defensor;
	}


	public String getAbogado() {
		return abogado;
	}

	public void setAbogado(String abogado) {
		this.abogado = abogado;
	}

	public Boolean getEsDefensorOficial() {
		return esDefensorOficial;
	}
	
	public Boolean isEsDefensorOficial() {
		return esDefensorOficial;
	}

	public void setEsDefensorOficial(Boolean esDefensorOficial) {
		this.esDefensorOficial = esDefensorOficial;
	}

	public java.util.Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(java.util.Date value) {
		fechaIngreso = value;
	}

	public Boolean getEstaFinalizado() {
		return estaFinalizado;
	}


	public void setEstaFinalizado(Boolean estaFinalizado) {
		this.estaFinalizado = estaFinalizado;
	}


	public TipoDeSituacionProcesal getSituacionProcesal() {
		return situacionProcesal;
	}


	public void setSituacionProcesal(TipoDeSituacionProcesal situacionProcesal) {
		this.situacionProcesal = situacionProcesal;
	}

	public ProcesoPenal getProcesoUnificador() {
		return procesoUnificador;
	}


	public void setProcesoUnificador(ProcesoPenal procesoUnificador) {
		this.procesoUnificador = procesoUnificador;
	}


	public MotivoIntervencionEnProcesoPenal getMotivoIntervencion() {
		return motivoIntervencion;
	}

	public void setMotivoIntervencion(MotivoIntervencionEnProcesoPenal motivoIntervencion) {
		this.motivoIntervencion = motivoIntervencion;
	}


	public java.util.List<MotivoIntervencionEnProcesoPenal> getMotivosIntervencion() {
		return motivosIntervencion;
	}

	public void setMotivosIntervencion(
			java.util.List<MotivoIntervencionEnProcesoPenal> value) {
		motivosIntervencion = value;
	}

	public java.util.List<MomentoProcesal> getMomentosProcesales() {
		return momentosProcesales;
	}

	public void setMomentosProcesales(
			java.util.List<MomentoProcesal> value) {
		momentosProcesales = value;
	}

	public java.util.List<MedidaEnProcesoPenal> getMedidasImpuestas() {
		return medidasImpuestas;
	}

	public void setMedidasImpuestas(java.util.List<MedidaEnProcesoPenal> medidasImpuestas) {
		this.medidasImpuestas = medidasImpuestas;
	}

	public List<NotaProcesoPenal> getNotas() {
		return notas;
	}


	public void setNotas(List<NotaProcesoPenal> notas) {
		this.notas = notas;
	}


}
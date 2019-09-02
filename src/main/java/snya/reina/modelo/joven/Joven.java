package snya.reina.modelo.joven;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import snya.general.modelo.Barrio;
import snya.general.modelo.Domicilio;
import snya.general.modelo.EstadoCivil;
import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.ObraSocial;
import snya.reina.modelo.institucion.*;
import snya.general.modelo.Provincia;
import snya.general.modelo.TipoDeDiscapacidad;
import snya.general.modelo.TipoDeDocumento;
import snya.general.modelo.TipoDeTrabajo;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.Persona;
import snya.reina.modelo.beneficio.BeneficioDelJoven;
import snya.reina.modelo.beneficio.CoberturaObraSocial;
import snya.reina.modelo.beneficio.EstadoBeneficio;
import snya.reina.modelo.beneficio.EstadoObraSocial;
import snya.reina.modelo.beneficio.GrupoDeBeneficio;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.educacion.CapacitacionJoven;
import snya.reina.modelo.educacion.Escolaridad;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.informe.TipoDeEstadoInformeEnum;
import snya.reina.modelo.informe.TipoDeInforme;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.IntervencionPenal;
import snya.reina.modelo.intervencion.Lineamiento;
import snya.reina.modelo.intervencion.MotivoBajaIntervencion;
import snya.reina.modelo.intervencion.RegistroAdmision;
import snya.reina.modelo.intervencion.TipoDeIntervencion;
import snya.reina.modelo.movimiento.EstadoMovimiento;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.proceso.CorteDeProcesoPenal;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMomentoProcesal;
import snya.reina.modelo.proceso.TipoDeMotivoIntervencion;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.modelo.referente.Familiar;
import snya.reina.modelo.referente.TipoDeParentesco;
import snya.reina.modelo.salud.HistoriaClinica;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;
import snya.reina.serviciomodelo.joven.ArbolFamiliar;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.resultado.ResultadoFamiliar;
import snya.reina.serviciomodelo.resultado.ResultadoPresente;


@Entity
@Embeddable
@Table(name="Reina_Joven", catalog="SistemasSNYA")
@Audited
public class Joven implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3618310003397662529L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdJoven")
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IdPersona")
	private Persona persona;
	
	@Column(name="TieneFichaDactiloscopica")
	private Boolean tieneFichaDactiloscopica;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER )
	@JoinColumn(name="IdCoberturaObraSocial",unique=true)
	private CoberturaObraSocial obraSocial;
	
	@Column(name="Dedo")
	private String dedo;
	
	@Embedded
	private Legajo legajo;
		
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<Familiar> familiares;
	
	@ManyToOne
	@JoinColumn(name="IdSituacionTramiteDNI")
	private SituacionTramite ultimaSituacionesTramiteDocumento;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	@OrderBy ("fechaSituacion ASC")
	private java.util.List<SituacionTramite> situacionesTramiteDocumento;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	@OrderBy ("cicloLectivo ASC")
	private java.util.List<Escolaridad> escolaridades;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<CapacitacionJoven> capacitaciones;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<ProcesoPenal> procesos;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<IntervencionPenal> intervencionesSRPJ;
		
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	@OrderBy("fecha ASC")
	private java.util.List<Movimiento> movimientos;
	
	@ManyToOne
	@JoinColumn(name="IdExpediente")
	private Expediente expedienteIdentificador;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="joven")
	private java.util.List<Expediente> expedientes;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<Informe> informes;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<Lineamiento> lineamientos;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	@NotAudited
	private java.util.List<Permanencia> permanencias;
	
	/*private java.util.List<InscripcionPrograma> programas;*/
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<BeneficioDelJoven> beneficios;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<HistoriaClinica> tratamientos;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	@OrderBy("fechaIngreso ASC")
	private java.util.List<RegistroAdmision> admisiones;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="joven")
	private java.util.List<Intervencion> intervenciones;

	/* === Constructores === */
	public Joven() {
		this.setLegajo(new Legajo(new Date()));
		this.setDedo(null);
		this.setTieneFichaDactiloscopica(false);
		this.setFamiliares(new java.util.ArrayList<Familiar>());
		this.setUltimaSituacionesTramiteDocumento(null);
		this.setSituacionesTramiteDocumento(new java.util.ArrayList<SituacionTramite>());
		this.setEscolaridades(new java.util.ArrayList<Escolaridad>());
		this.setCapacitaciones(new ArrayList<CapacitacionJoven>());
		this.setProcesos(new java.util.ArrayList<ProcesoPenal>());
		this.setIntervencionesSRPJ(new java.util.ArrayList<IntervencionPenal>());
		this.setMovimientos(new java.util.ArrayList<Movimiento>());
		this.setExpedienteIdentificador(null);
		this.setExpedientes(new java.util.ArrayList<Expediente>());
		this.setPermanencias(new ArrayList<Permanencia>());
		//this.setProgramas(new ArrayList<InscripcionPrograma>());
		this.setLineamientos(new ArrayList<Lineamiento>());
		
		this.setBeneficios(new ArrayList<BeneficioDelJoven>());
		this.setTratamientos(new ArrayList<HistoriaClinica>());
		this.setAdmisiones(new ArrayList<RegistroAdmision>());
		this.setIntervenciones(new ArrayList<Intervencion>());
	}

	/* === Metodos === */
	//
	// Situacion de Identidad
	//
	public void agregarSituacionTramite(JovenDAO daoJoven, TipoDeSituacionTramite tipo, Date fecha, 
			TipoDeDocumento tipoDeDocumentoPosee, String numeroDocumentoPosee, Boolean actualizado, Boolean partidaDeNacimientoPosee, String observacionPosee,
			Boolean tieneDocumento, TipoDeDocumento tipoDeDocumentoEnTramite, String numeroDocumentoEnTramite, Boolean partidaDeNacimientoEnTramite, String observacionEnTramite,
			Boolean certificadoDeParto, Boolean partidaDeNacimiento,
			String observacionNoPosee, String observacionSeDesconoce)
			throws ReinaException {
		if (this.getSituacionesTramiteDocumento().size() > 0) {
			SituacionTramite ultima = this.getUltimaSituacionesTramiteDocumento();
			fecha = Calendario.sumarHorario(fecha, ultima.getFechaSituacion());
		}
		
		// <<validaciones>>
		this.compruebaPoderAgregarSituacionDocumento(tipo, fecha);
		
		// <<procesamiento>>
		SituacionTramite  situacion = tipo.crearSituacion(this.getPersona(), fecha, tipoDeDocumentoPosee, numeroDocumentoPosee, actualizado, partidaDeNacimientoPosee, observacionPosee,
				tieneDocumento, tipoDeDocumentoEnTramite, numeroDocumentoEnTramite, partidaDeNacimientoEnTramite, observacionEnTramite,
				certificadoDeParto, partidaDeNacimiento, observacionNoPosee, observacionSeDesconoce);
		
		situacion.noSeRepiteDocumento(this, daoJoven);
		
		this.getSituacionesTramiteDocumento().add(situacion);
		situacion.setJoven(this);
		this.setUltimaSituacionesTramiteDocumento(situacion);
	}

	public void modificarSituacionTramite(SituacionTramite situacion,
			SituacionTramite situacionNuevo) throws ReinaException {
		// <<validaciones>>
		// existen escolaridades
		if (this.getSituacionesTramiteDocumento().size() > 1) {
			// corrobora que el dato a ingresar sea mayor que el acutal segun el
			// tiempo
			SituacionTramite situacionAnterior = 
					this.getSituacionesTramiteDocumento().get(
							this.getSituacionesTramiteDocumento().size() - 1);
			if (situacionAnterior.getFechaSituacion().after(
					situacionNuevo.getFechaSituacion())) {
				throw new ReinaException(ReinaCte.Format(
						ReinaCte.FECHA_SITUACION_TRAMITE_ES_MENOR,
						situacionAnterior.getFechaSituacion().toString()));
			}
		}

		// <<procesamiento>>
		situacion.setTipo(situacionNuevo.getTipo());
		situacion.setFechaSituacion(situacionNuevo.getFechaSituacion());
		situacion.setActualizado(situacionNuevo.getActualizado());
		situacion.setPartidaNacimiento(situacionNuevo.getPartidaNacimiento());
		situacion.setCertificadoDeParto(situacionNuevo.getCertificadoDeParto());
		situacion.setObservacion(situacionNuevo.getObservacion());
	}

	public void quitarSituacionTramite(SituacionTramite situacion) {
		// <<procesamiento>>
		this.getSituacionesTramiteDocumento().remove(situacion);
	}
	
	
	//
	// Familiares
	//
	public Familiar agregarFamiliar(Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			ObraSocial obraSocial, TipoDeParentesco parentesco)
			throws ReinaException {
		ArbolFamiliar familia = new ArbolFamiliar();
		ResultadoFamiliar resultado;

		resultado = familia.generarFamiliar(this, persona, vive, convive,
				referente, tutor, observacion, obraSocial, parentesco);
		
		if (resultado.exitoso()) {
			if (referente) {
				for (Familiar f : this.getFamiliares()) {
					f.setReferente(false);
				}
			}

			Familiar familiar = resultado.getFamiliar();
			this.getFamiliares().add(familiar);
			familiar.setJoven(this);
			
			return familiar;
		} else
			throw new ReinaException(resultado.getMensaje());
	}

	public void modificarFamiliar(Familiar familiar, Boolean tieneDocumento,
			TipoDeDocumento tipoDeDocumento, String numeroDocumento,
			Boolean vive, Boolean convive, Boolean referente, Boolean tutor,
			String observacion, ObraSocial obraSocial,
			TipoDeParentesco parentesco) throws ReinaException {
		ArbolFamiliar familia = new ArbolFamiliar();
		ResultadoFamiliar resultado;

		resultado = familia.modificarFamiliar(this, familiar, tieneDocumento, tipoDeDocumento,
				numeroDocumento, vive, convive,
				referente, tutor, observacion, obraSocial, parentesco);

		if (resultado.exitoso()) {
			familiar = resultado.getFamiliar();
			
			if (familiar.getReferente()) {
				for (Familiar f : this.getFamiliares()) {
					if (!f.equals(familiar)) {
						f.setReferente(false);
					}
				}
			}
		} else
			throw new ReinaException(resultado.getMensaje());
	}
	
	//Modifica el domicilio del familiar que convive con el joven para que tenga el mismo domicilio
	public void modificarDomicilioFamiliarConviviente(Familiar familiar){
		if (this.getDomicilio() != null) familiar.setDomicilio(obtenerDomicilioJoven());
	}
	
	public Familiar getReferente(){
		Iterator<Familiar> iter = this.getFamiliares().iterator();
		Familiar referente = null;
		while (iter.hasNext() && referente == null) {
			Familiar familiar = (Familiar) iter.next();
			if (familiar.getReferente())
				referente = familiar;
		}
		
		return referente;
	}

	public String getDetalleReferentes() {
		Iterator<Familiar> iter = this.getFamiliares().iterator();
		String detalle = "";
		while ( iter.hasNext() ) {
			Familiar familiar = (Familiar) iter.next();
			detalle += familiar.getNombreCompleto() + " (" + familiar.getParentesco().getNombre() + ") - ";
		}
		
		return detalle;

	}
	
	// Obtiene los datos del domicilio del joven
	private Domicilio obtenerDomicilioJoven(){
			
			Provincia provinciaDomicilio;
			Municipio municipioDomicilio;
			Localidad localidadDomicilio;
			Barrio barrioDomicilio;
			
			Boolean domicilioExacto;
			String domicilioCalle,domicilioNumero,domicilioPiso,domicilioTorre,domicilioDepto,domicilioEntre1,domicilioEntre2,referenciaDomicilio;

		
			provinciaDomicilio = this.getDomicilio().getProvincia();
			municipioDomicilio = this.getDomicilio().getMunicipio();

			// Si posee localidad cargada
			if (this.getDomicilio().getLocalidad() != null) localidadDomicilio =  this.getDomicilio().getLocalidad();
			else localidadDomicilio = null;

								
			// Si tiene Barrio lo guarda
			if(this.getDomicilio().getBarrio() == null) barrioDomicilio = null; 
				else barrioDomicilio = this.getDomicilio().getBarrio();
					
			
			if ((this.getDomicilio().getTieneReferenciaDomiciliaria() != null)&&(this.getDomicilio().getTieneReferenciaDomiciliaria() != false)){
				domicilioExacto = true;
				domicilioCalle = this.getDomicilio().getCalle();
				domicilioNumero = this.getDomicilio().getNumero();
				domicilioPiso  = this.getDomicilio().getPiso();
				domicilioTorre = this.getDomicilio().getTorre();
				domicilioDepto = this.getDomicilio().getDepto();
				domicilioEntre1 = this.getDomicilio().getEntre1();
				domicilioEntre2	 = this.getDomicilio().getEntre2();
				
				
				} else {
					domicilioExacto = false;
					domicilioCalle = null;
					domicilioNumero = null;
					domicilioPiso  = null;
					domicilioTorre = null;
					domicilioDepto = null;
					domicilioEntre1 = null;
					domicilioEntre2	 = null;
					
					} 
			
			referenciaDomicilio = this.getDomicilio().getObservacion();
			
			return this.crearDomicilio(domicilioExacto, domicilioCalle, domicilioNumero, domicilioPiso,
					domicilioTorre, domicilioDepto, domicilioEntre1, domicilioEntre2,
					provinciaDomicilio, municipioDomicilio, localidadDomicilio, barrioDomicilio, referenciaDomicilio);
			
		}
	//Carga los datos del domicilio a una variable domicilio	
	private Domicilio crearDomicilio(Boolean domicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			Provincia provincia, Municipio municipio, Localidad localidad,
			Barrio barrio, String referenciaDomicilio) {
		Domicilio domicilio = new Domicilio();
		
		if (domicilioExacto) {
			domicilio.setTieneReferenciaDomiciliaria(true);
			domicilio.setCalle(domicilioCalle);
			domicilio.setNumero(domicilioNumero);
			domicilio.setPiso(domicilioPiso);
			domicilio.setTorre(domicilioTorre);
			domicilio.setDepto(domicilioDepto);
			domicilio.setEntre1(domicilioEntre1);
			domicilio.setEntre2(domicilioEntre2);
			
		} else
			domicilio.setTieneReferenciaDomiciliaria(false);
			
		
		domicilio.setProvincia(provincia);
		domicilio.setMunicipio(municipio);
		domicilio.setLocalidad(localidad);	
		domicilio.setBarrio(barrio);
		domicilio.setObservacion(referenciaDomicilio);

		return domicilio;		
	}

	
	//
	// Procesos
	//
	public List<ProcesoPenal> traerProcesosAbiertos() {
		List<ProcesoPenal> lista = new ArrayList<ProcesoPenal>();
		for (ProcesoPenal proceso : this.procesos) {
			if (!proceso.getEstaFinalizado())
				lista.add(proceso);
		}
		
		return lista;
	}
	
	public CorteDeProcesoPenal agregarProcesoPenal(AdministradorDeProceso dao, Date fechaIngreso,
			OrganoJudicial juzgado, Fiscalia fiscalia,
			Boolean esDefensorOficial, Defensoria defensoria, ContactoInstitucion defensor, String abogado,
			String iPP, String nroCarpeta, String nroCausa,
			TipoDeMotivoIntervencion tipoDeMotivo, Boolean gradoTentativa, String observacionCaratula,
			TipoDeMomentoProcesal tipoMomentoProcesal,
			TipoDeMedidaEnProcesoPenal tipoMedida,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detallesMedida,
			String observacionMedida, Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaDeMedida) throws ReinaException {
		ProcesoPenal proceso = new ProcesoPenal(fechaIngreso, juzgado,
				fiscalia, esDefensorOficial, defensoria, defensor, abogado, iPP,
				nroCarpeta, nroCausa, this);
		
		proceso.agregarMotivoIntervencion(juzgado, fechaIngreso, tipoDeMotivo,
				gradoTentativa, observacionCaratula);
		proceso.agregarMomentoProcesal(dao, tipoMomentoProcesal, fechaIngreso,
				juzgado, fiscalia, tipoMedida, detallesMedida, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaDeMedida);

		this.getProcesos().add(proceso);
		proceso.setJoven(this);

		return new CorteDeProcesoPenal(proceso, proceso.getMotivoIntervencion(), proceso.getMomentoProcesal(), proceso.getMedidaImpuesta(), proceso.getSituacionProcesal());
	}

	public void eliminarProceso(ProcesoPenal proceso) {
		this.getProcesos().remove(proceso);
		proceso.setJoven(null);
	}
	
	
	//
	// Intervenciones
	//
	public IntervencionPenal agregarIntervencionPenal(Intervencion intervencion, 
			Date fechaIntervencion, Recurso recurso, String observacion,
			MedidaEnProcesoPenal medidaImpuesta, EscritorNarrativoDeHistoria escritor) {
		
		CentroDeReferencia centroDeReferencia = (CentroDeReferencia) recurso.getComponente();
		IntervencionPenal intervencionP = new IntervencionPenal(intervencion, fechaIntervencion, centroDeReferencia, medidaImpuesta, observacion);

		this.getIntervencionesSRPJ().add(intervencionP);
		intervencionP.setJoven(this);
		
		return intervencionP;
	}


	public void cesarIntervencionPenal(IntervencionPenal intervencion, Date fechaBaja,
			MotivoBajaIntervencion motivoDeBaja, String observacionCese,
			EscritorNarrativoDeHistoria escritor) throws ReinaException {

		if (!intervencion.estaAbierta())
			throw new ReinaException("La intervencion en territorio ya se encuentra cesada");
		
		intervencion.setFechaBaja(fechaBaja);
		intervencion.setMotivoBajaIntervencion(motivoDeBaja);
		intervencion.setObservacion( intervencion.getObservacion() + "   " + observacionCese);
		
		this.cesarPresenteEnRecurso(intervencion, escritor);
		intervencion.getPermanencia().setFechaFin(fechaBaja);
		
		intervencion.getIntervencion().setEstaActivo(false);
	}

	public void eliminarIntervencionPenal(IntervencionPenal intervencion) throws ReinaException {
		
		intervencion.getIntervencion().setEstaActivo(false);
		intervencion.getIntervencion().setEliminada(true);

		this.getIntervencionesSRPJ().remove(intervencion);
		
		this.eliminarPresenteEnRecurso(intervencion);
	}
	
	public boolean tieneIntervencionesTerritorialesVigentes() {
		boolean tiene = false;
		Iterator<IntervencionPenal> it = this.getIntervencionesSRPJ().iterator();
		
		while (it.hasNext()) {
			IntervencionPenal interv = (IntervencionPenal) it.next();
			tiene = tiene || interv.estaAbierta();
		}
		return tiene;
	}
	
	
	//
	// Informes adoptadas por los equipos tecnicos
	//
	public Informe agregarInforme(Intervencion intervencion, Date fecha, TipoDeInforme tipo, String observacion, Institucion institucion, String autores, Boolean conArchivo, TipoDeEstadoInformeEnum estado) throws ReinaException {
		
		// valida que haya un tipo definido
		if (tipo == null)
			throw new ReinaException(ReinaCte.TIPO_Informe_NO_DEFINIDO);

		Informe informe = new Informe(intervencion, fecha, tipo, observacion, institucion, autores);
		informe.setArchivo(conArchivo);
		informe.setEstado(estado);
		informe.setJoven(this);
		this.getInformes().add(informe);
		
		return informe;
	}

	public void modificarInforme(Informe informe, Institucion institucion, String observacion) throws ReinaException {
		
		informe.setInstitucion(institucion);
		informe.setObservacion(observacion);
		informe.setEstado(TipoDeEstadoInformeEnum.EDICION);
	}

	public void eliminarInforme(Informe informe) {
		this.getInformes().remove(informe);
	}
	
	public Informe traerUltimoInformeValidoDelTipo(int idTipo) {
		List<Informe> informes = new ArrayList<Informe>();		
		for (Informe inf : this.getInformes()) {
			if (inf.estaImpreso() && inf.getTipo().getId().equals(idTipo))
				informes.add(inf);
		}
	
		Collections.sort(informes, new Comparator<Informe>() {
			@Override
			public int compare(Informe o1, Informe o2) {
				return ( o1.getFechaInforme().compareTo(o2.getFechaInforme()) ) * -1;
			}
			
		});
		
		return (informes.size() > 0) ? informes.get(0) : null;
	}
	
	//
	// Movimientos del joven por ambitos de ejecucion
	//
	public void confirmarMovimiento(Movimiento movimiento, EstadoMovimiento estado) throws ReinaException {
		if (!this.esElUltimoMovimiento(movimiento))
			throw new ReinaException(ReinaCte.NO_ES_ULTIMO_MOVIMIENTO);
		if (movimiento.noEstaPendiente())
			throw new ReinaException(ReinaCte.MOVIMIENTO_NO_ESTA_PENDIENTE);

		movimiento.confirmar(estado);
	}

	public void cancelarMovimiento(Movimiento movimiento, EstadoMovimiento estado) throws ReinaException {
		if (!this.esElUltimoMovimiento(movimiento))
			throw new ReinaException(ReinaCte.NO_ES_ULTIMO_MOVIMIENTO);
		if (movimiento.noEstaPendiente())
			throw new ReinaException(ReinaCte.MOVIMIENTO_NO_ESTA_PENDIENTE);
		
		movimiento.cancelar(estado);
	}
	
	public void eliminarMovimiento(Movimiento movimiento) throws ReinaException {
		if (!this.esElUltimoMovimiento(movimiento))
			throw new ReinaException(ReinaCte.NO_ES_ULTIMO_MOVIMIENTO);

		this.getMovimientos().remove(movimiento);
	}

	public Movimiento traerUltimoMovimiento() {
		return (this.getMovimientos().size() > 0) ?
			this.getMovimientos().get( this.getMovimientos().size() - 1)
			: null;
	}
	
	public Movimiento traerUltimoMovimientoCorrecto() {
		Movimiento movimiento = null;
			
		if ((this.getMovimientos().size() > 0)) {
			int longitud = this.getMovimientos().size();
			
			while ((movimiento == null) && longitud > 0) {
				if (!this.getMovimientos().get( longitud - 1).estaCancelada())
					movimiento = this.getMovimientos().get( longitud - 1);
				longitud--;
			}
		}

		return movimiento;
	}	
	
	// 
	// Manejo de admision
	//
	public RegistroAdmision traerAdmisionAbierta() {
		RegistroAdmision admision = null;
		
		for (RegistroAdmision registro : this.getAdmisiones()) {
			if (registro.getFechaEgreso() == null) {
				admision = registro;
				break;
			}			
		}
		return admision;
	}
	
	//
	// Manejo de Expediente
	//
	public Expediente traerExpedienteIdentificador() {
		return this.getExpedienteIdentificador();
	}

	public Expediente getExpedientePromocion() {
		Iterator<Expediente> iter = this.getExpedientes().iterator();
		Expediente exp = null;
		
		while (iter.hasNext()) {
			Expediente e = (Expediente) iter.next();
			if(e.getCaratulador().getId() == Caratulador.ID_PROMOCION)
				exp = e;
		}
		
		return exp;
	}
	
	//
	// Manejo de Legajo papel
	//
	public void recibirLegajo(Date fecha, Institucion institucion, String observacion) {
		this.getLegajo().setFecha(fecha);
		
		String obs = "El dia " + Calendario.formatearFecha(fecha) + " se recibe el legajo de la institucion " + institucion.getNombre();
		if (observacion != null && !observacion.equals("")) {
			obs = obs + ", donde " + observacion;
		}
		obs = ((this.getLegajo().getObservacion() == null || this.getLegajo().getObservacion().equals("")) ? "" : (this.getLegajo().getObservacion() + ". ")) + obs;
		this.getLegajo().setObservacion(obs);
		
		this.getLegajo().setEstaEnRegistro(true);
	}
	
	public void enviarLegajo(Date fecha, Institucion institucion, String observacion) {
		this.getLegajo().setFecha(fecha);
		
		String obs = "El dia " + Calendario.formatearFecha(fecha) + " se entrega el legajo a la institucion " + institucion.getNombre();
		if (observacion != null && !observacion.equals("")) {
			obs = obs + ", donde " + observacion;
		}
		obs = ((this.getLegajo().getObservacion() == null || this.getLegajo().getObservacion().equals("")) ? "" : (this.getLegajo().getObservacion() + ". ")) + obs;
		this.getLegajo().setObservacion(obs);
		
		this.getLegajo().setEstaEnRegistro(false);
	}
	
	
	//
	// Historial Presente
	//
	public void agregarPresenteEnRecurso(Intervencion intervencion, Movimiento movimiento, ProcesoPenal proceso, AmbitoEjecucion ambitoEjecucionOrigen, AmbitoEjecucion ambitoEjecucionDestino, EscritorNarrativoDeHistoria escritor) {
		ResultadoPresente resultado;
		
		resultado = movimiento.asentarHistorial(this, intervencion, proceso, ambitoEjecucionOrigen, ambitoEjecucionDestino, escritor);
		resultado.inscribirEnJoven(this);
	}
	
	public void cesarPresenteEnRecurso(IntervencionPenal intervencion, EscritorNarrativoDeHistoria escritor) throws ReinaException {
		Iterator<Permanencia> iter = this.getPermanencias().iterator();
		Permanencia presente = null;

		while ( (presente == null) && iter.hasNext()) {
			Permanencia h = (Permanencia) iter.next();
			if (h.estaAbierto() && h.ambitoEjecucionEs(intervencion.getCentroDeReferencia()) )
				presente = h;
		}
		
		if (presente == null)
			throw new ReinaException(
					"El joven no se encuentra actualmente vinculado al centro de referencia " + 
					intervencion.getCentroDeReferencia().getNombre()
					);
		
		presente.cerrarInscripcion(intervencion, escritor);
	}
	
	public void eliminarPresenteEnRecurso(IntervencionPenal intervencion) {
		Permanencia presente = intervencion.getPermanencia();
		this.getPermanencias().remove(presente);
	}
	
	public void modificarPresenteEnRecurso(Movimiento movimiento, EscritorNarrativoDeHistoria escritor) {
		movimiento.modificarHistorial(this, escritor);
	}
	
	public void quitarPermanencia(Permanencia permancia) {
		this.getPermanencias().remove(permancia);
		
		Iterator<Movimiento> it = this.getMovimientos().iterator();
		Movimiento mov = null;
		while ( (mov == null) && it.hasNext()) {
			Movimiento movi = (Movimiento) it.next();
			if ( movi.getPermanencia().equals(permancia.getId()) )
					mov = movi;
		}
		
		if (mov != null) {
			mov.setPermanencia(null);
		}
	}
		
	public void cambiarCondicionDePermanenciaPorUnificacion(Date fecha, ProcesoPenal proceso, EscritorNarrativoDeHistoria escritor) {
		List<Permanencia> complementos = new ArrayList<Permanencia>();
		
		for (Permanencia permanencia : this.getPermanencias()) {
			if ( permanencia.estaVigente() && permanencia.getProceso() != null && permanencia.getProceso().getId().equals(proceso.getId()) )
				complementos.add( permanencia.armarContinuidadPorUnificacion(fecha, proceso.getProcesoUnificador(), escritor) );
		}
		
		for (Permanencia permanencia : complementos) {
			this.getPermanencias().add(permanencia);
			permanencia.setJoven(this);
		}
	}
	

	public void retrotraerCondicionDePermanenciaPorUnificacion(Date fecha, ProcesoPenal proceso, EscritorNarrativoDeHistoria escritor) {
		HashMap<Integer, Permanencia> complementos = new HashMap<Integer, Permanencia>();	
		
		for (Permanencia permanencia : this.getPermanencias()) {
			if ( permanencia.getProceso() != null && permanencia.getProceso().getId().equals(proceso.getId()) && permanencia.getFechaFin().equals(fecha)  ) {
				complementos.put( permanencia.getGrupo(), permanencia );
			}
		}

		for (Integer grupo : complementos.keySet()) {
			complementos.get(grupo).quitarContinuidadPorUnificacion(proceso.getProcesoUnificador(), escritor);
		}		
	}
	
	public boolean estaPresenteEnAmbitoDeEjecucion(Date fecha) {
		Iterator<Permanencia> iter = this.getPermanencias().iterator();
		boolean esta = false;

		while (!esta && iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			esta = presente.cumplePermanencia(fecha);
		}

		return esta;
	}
			
	public Permanencia traerUltimaPermanenciaDeCumplimientoAbierta() {
		Iterator<Permanencia> iter = this.getPermanencias().iterator();
		Permanencia actual = null;

		while ((actual == null) && iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			if (presente.estaCumpliendoPermanencia())
				actual = presente;
		}
		
		return actual;
	}

	public Permanencia traerPrimerPermanenciaDeCumplimiento() {
		List<Permanencia> lista =  new ArrayList<Permanencia>(this.getPermanencias());		
		Comparator<Permanencia> permanencia_orden = new Comparator<Permanencia>() {
			public int compare(Permanencia p1, Permanencia p2) {
				return p1.getFechaInicio().compareTo(p2.getFechaInicio());
			}
		};
		Collections.sort(lista, permanencia_orden);
		
		Iterator<Permanencia> iter = lista.iterator();
		Permanencia actual = null;

		while (actual == null && iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			if (presente.seCumplePermanencia())
				actual = presente;
		}
		
		return actual;
	}
	
	public Permanencia traerUltimaPermanenciaDeCumplimiento() {
		List<Permanencia> lista =  new ArrayList<Permanencia>(this.getPermanencias());		
		Comparator<Permanencia> permanencia_orden = new Comparator<Permanencia>() {
			public int compare(Permanencia p1, Permanencia p2) {
				return p1.getFechaInicio().compareTo(p2.getFechaInicio());
			}
		};
		Collections.sort(lista, permanencia_orden);
		
		Iterator<Permanencia> iter = lista.iterator();
		Permanencia actual = null;

		while (iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			if (presente.seCumplePermanencia())
				actual = presente;
		}
		
		return actual;
	}
	
	public Permanencia traerUltimaPermanenciaDeCumplimientoAntesDe(Permanencia permanencia) {
		List<Permanencia> lista = traerListaPermanenciaOrdenada();
		
		Iterator<Permanencia> iter = lista.iterator();
		Permanencia actual = null;
		Permanencia elegida = null;
		boolean seguir = true;
		
		while (seguir && iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			if (presente.seCumplePermanencia()) {
				actual = presente;
				
				if (permanencia.getId() != actual.getId() ) 
					elegida = presente;
				else
					seguir = false;
			}
		}
		
		return elegida;
	}
	
	public AmbitoEjecucion ambitoPresente() {
		Iterator<Permanencia> iter = this.getPermanencias().iterator();
		AmbitoEjecucion ambito = null;

		while (ambito == null && iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			if (presente.estaCumpliendoPermanencia())
				ambito = presente.getAmbitoEjecucion();
		}
		
		return ambito;
	}
		
	public List<AmbitoEjecucion> traerAmbitosPresentes() {
		Iterator<Permanencia> iter = this.getPermanencias().iterator();
		List<AmbitoEjecucion> ambitos = new ArrayList<AmbitoEjecucion>();

		while (iter.hasNext()) {
			Permanencia presente = (Permanencia) iter.next();
			if (presente.estaVigente())
				ambitos.add( presente.getAmbitoEjecucion() );
		}
		
		return ambitos;
	}	
	
	
	//
	// INTERVENCION
	//
	public List<Intervencion> traerIntervencionesAbiertas() {
		Iterator<Intervencion> iter = this.getIntervenciones().iterator();
		List<Intervencion> lista = new ArrayList<Intervencion>();

		while (iter.hasNext()) {
			Intervencion interv = (Intervencion) iter.next();
			if (interv.getEstaActivo() && !interv.getEliminada())
				lista.add( interv );
		}
		
		return lista;
	}
	
	
	//
	// Escolaridad
	//
	public Escolaridad traerUltimaEscolaridad() {
		Iterator<Escolaridad> iter = this.getEscolaridades().iterator();
		Escolaridad ultimo = null;
		
		while (iter.hasNext()) {
			Escolaridad escolaridad = (Escolaridad) iter.next();
			if (escolaridad.isUltimo())
				ultimo = escolaridad;
		}
		
		return ultimo;
	}
		
	public List<CapacitacionJoven> getFormacionesLaborales() {
		List<CapacitacionJoven> formaciones = new ArrayList<CapacitacionJoven>();
		Iterator<CapacitacionJoven> iter = this.capacitaciones.iterator();
		
		while (iter.hasNext()) {
			CapacitacionJoven capacitacionJoven = (CapacitacionJoven) iter.next();
			
			if ( capacitacionJoven.getCapacitacion().getEsFormacionLaboral() ) {
				formaciones.add(capacitacionJoven);
			}
		}
		
		return formaciones;
	}
	
	//
	//Lineamiento
	//
	public Lineamiento traerLineamiento(Integer idInstitucion) {
		Iterator<Lineamiento> iter = this.getLineamientos().iterator();
		Lineamiento lineamiento = null;
		
		while (iter.hasNext()) {
			Lineamiento lin = (Lineamiento) iter.next();
			if (lin.getInstitucion().getId().equals(idInstitucion))
				lineamiento = lin;
		}
		
		return lineamiento;
	}
	
	public Lineamiento guardarLineamiento(String observacion, Institucion institucion) {
		Lineamiento lineamiento = new Lineamiento(this, observacion, institucion);
		this.getLineamientos().add(lineamiento);
		
		return lineamiento;
	}
	
	
	//
	// Metodos de visualizacion
	//
	public String traerFechaNacimiento() {
		return (this.getPersona().getFechaNacimiento() == null) 
				? ((this.getPersona().getEdad() != null) ? this.getPersona().getEdad().toString() : "") 
				: Calendario.formatearFecha(this.getPersona().getFechaNacimiento());
	}

	public String traerTextoFechaNacimiento() {
		return (this.getPersona().getFechaNacimiento() == null) 
				? "" 
				: Calendario.formatearFecha(this.getPersona().getFechaNacimiento());
	}
	
	public MedidaEnProcesoPenal traerUltimaMedida() {
		ArrayList<ProcesoPenal> procesos = new ArrayList<ProcesoPenal>(this
				.getProcesos().size());
		for (ProcesoPenal proceso : this.getProcesos()) {
			procesos.add(proceso);
		}
		Comparator<ProcesoPenal> proceso_orden = new Comparator<ProcesoPenal>() {
			public int compare(ProcesoPenal p1, ProcesoPenal p2) {
				return p1.getMedidaImpuesta().getFechaMedida()
						.compareTo(p2.getMedidaImpuesta().getFechaMedida());
			}
		};

		Collections.sort(procesos, proceso_orden);
		return procesos.get(0).getMedidaImpuesta();
	}

	public String getDetalleDocumento() {
		return this.getUltimaSituacionesTramiteDocumento().traerDetalle();
	}

	public String getDetalleSexo() {
		return (this.getPersona().getSexo().equals("M")) ? "Masculino" : "Femenino";
	}

	public String getDetalleLugarDeNacimiento() {
		String descripcion = "";
		
		if (this.getLocalidad() != null) descripcion = "Localidad de " + this.getLocalidad().getNombre();
		if (this.getMunicipio() != null) descripcion += ((descripcion.length() > 0) ? ", " : "") + "Municipio de " + this.getMunicipio().getNombre();
		if (this.getProvincia() != null) descripcion += ((descripcion.length() > 0) ? ", " : "") + "Provincia de " + this.getProvincia().getNombre();
		
		return descripcion;
	}
	
	public String getDescripcionJoven() {
		return this.getNombres() + " " + this.getApellidos().toUpperCase() + 
				( (this.getApellidoMaterno() != null) ? " " + this.getApellidoMaterno().toUpperCase() : "") +
				((this.isTieneDocumento()) ? " " + this.getTipoDeDocumento().getNombreCorto() + " " + this.getNumeroDocumento() : "" );
	}
	
	public String getDescripcion() {
		String detalle = "";
		if ( this.getExpedienteIdentificador() != null )
			detalle = "Exp " + this.getExpedienteIdentificador().getNumeroCompleto() + " - ";
		
		return detalle + this.getNombres() + " " + this.getApellidos();
	}
	
	public String getDetalleHistoricidad() {
		boolean tieneIntervCAD = false;					boolean estaIntervCAD = false;
		boolean tieneIntervContextoEncierro = false;	boolean estaIntervContextoEncierro = false;
		boolean tieneIntervMonitoreo = false;			boolean estaIntervMonitoreo = false;
		
		for (Intervencion intervencion : this.getIntervenciones()) {
			if (intervencion.getTipo().getId().equals(TipoDeIntervencion.ID_ADMISION_CAD)) {
				tieneIntervCAD = true;
				estaIntervCAD = estaIntervCAD || ( intervencion.getVigente() );
			}
			if (intervencion.getTipo().getId().equals(TipoDeIntervencion.ID_CONTEXTO_ENCIERRO)) {
				tieneIntervContextoEncierro = true;
				estaIntervContextoEncierro = estaIntervContextoEncierro || ( intervencion.getVigente() );		
			}
			if (intervencion.getTipo().getId().equals(TipoDeIntervencion.ID_MONITOREO_TERRITORIAL)) {
				tieneIntervMonitoreo = true;
				estaIntervMonitoreo = estaIntervContextoEncierro || ( intervencion.getVigente() );		
			}
		}
		
		String historicidad_Admision = "";
		if (tieneIntervCAD) {
			if (estaIntervCAD) {
				historicidad_Admision = " Se encuentra alojado en un Centro de Admisión y Derivación /";
			} else {
				historicidad_Admision = " Tuvo un paso por un Centro de Admisión y Derivación /";
			}
		}
				
		String historicidad_Contexto = "";
		if (tieneIntervContextoEncierro) {
			if (estaIntervContextoEncierro) {
				historicidad_Contexto = " Se encuentra bajo la modalidad de contexto de encierro /";	
			} else {
				historicidad_Contexto = " Tuvo un paso por dispositivos de contexto de encierro /";
			}
		}
		
		String historicidad_Monitoreo = "";
		if (tieneIntervMonitoreo) {
			if (estaIntervMonitoreo) {
				historicidad_Monitoreo = " Se encuentra bajo intervencion de monitoreo territorial";
			} else {
				historicidad_Monitoreo = " Tuvo intervencion de monitoreo territorial";
			}
		}
		
		return historicidad_Admision + " " + historicidad_Contexto + " " + historicidad_Monitoreo;
	}
	
	
	/* === Metodos internos === */
	private List<Permanencia> traerListaPermanenciaOrdenada() {
		List<Permanencia> lista =  new ArrayList<Permanencia>(this.getPermanencias());		
		Comparator<Permanencia> permanencia_orden = new Comparator<Permanencia>() {
			public int compare(Permanencia p1, Permanencia p2) {
				return p1.getFechaInicio().compareTo(p2.getFechaInicio());
			}
		};
		Collections.sort(lista, permanencia_orden);
		return lista;
	}
	
	private void compruebaPoderAgregarSituacionDocumento(TipoDeSituacionTramite tipo, Date fecha) throws ReinaException {
		if (this.getSituacionesTramiteDocumento().size() > 0) {
			SituacionTramite ultima = this.getUltimaSituacionesTramiteDocumento();
			
			// valida que la situacion exista como paso siguiente
			if (!ultima.getTipo().getPosibles().contains(tipo)) {
				throw new ReinaException(ReinaCte.Format(
						ReinaCte.NO_POSIBLE_SITUACION_TRAMITE_SIGUIENTE, ultima.getTipo().getNombre(), ultima.getTipo().getNombre()));
			}
			// valida que el dato a ingresar sea mayor que el acutal segun el tiempo
			if (ultima.getFechaSituacion().compareTo(fecha) > 0) {
				throw new ReinaException(ReinaCte.Format(
						ReinaCte.FECHA_SITUACION_TRAMITE_ES_MENOR, ultima.getFechaSituacion().toString()));
			}
		}
	}

	public List<IntervencionPenal> traerIntervencionesPenalesAbiertas() {
		List<IntervencionPenal> intervenciones = new ArrayList<IntervencionPenal>();
		
		for (IntervencionPenal intervencion : this.getIntervencionesSRPJ()) {
			if (intervencion.estaAbierta())
				intervenciones.add(intervencion);
		}
	
		return intervenciones;
	}
	
	public IntervencionPenal traerUltimaIntervencion() {
		List<IntervencionPenal> intervenciones = new ArrayList<IntervencionPenal>();
		
		for (IntervencionPenal intervencion : this.getIntervencionesSRPJ()) {
			if (intervencion.estaAbierta())
				intervenciones.add(intervencion);
		}
	
		Collections.sort(intervenciones, new Comparator<IntervencionPenal>() {
			@Override
			public int compare(IntervencionPenal o1, IntervencionPenal o2) {
				return o1.getFechaIntervencion().compareTo(o2.getFechaIntervencion());
			}
			
		});
		
		return (intervenciones.size() > 0) ? intervenciones.get(intervenciones.size() - 1) : null;
	}
	
	private boolean esElUltimoMovimiento(Movimiento movimiento) {
		return this.getMovimientos().get( this.getMovimientos().size() -1).getId().equals(movimiento.getId());
	}
	
	
	// Beneficios - Obra social
	public void agregarBeneficio(TipoDeBeneficio tipo, String numero,
			Date fechaAltaBeneficio, String observacionBeneficio, EstadoBeneficio estadoBeneficio, Date fechaEntregaTarjeta) {
		//TODO
		BeneficioDelJoven beneficio = new BeneficioDelJoven(tipo, numero, fechaAltaBeneficio, estadoBeneficio, observacionBeneficio, null);
		beneficio.setFechaEntregaTarjeta(fechaEntregaTarjeta);
		
		this.getBeneficios().add(beneficio);
		beneficio.setJoven(this);		
	}


	public void eliminarBeneficio(BeneficioDelJoven beneficio) {
		this.getBeneficios().remove(beneficio);
		beneficio.setJoven(null);
	}
	
	public void agregarObraSocial(ObraSocial obraSocial, String numeroCarnet,
			Date fVencimientoCarnet, String observacionObraSocial,EstadoObraSocial estado) {
		if (this.getObraSocial() == null) {
			this.setObraSocial(new CoberturaObraSocial(obraSocial, numeroCarnet, fVencimientoCarnet, observacionObraSocial, estado));			
		} else {
			this.getObraSocial().setObraSocial(obraSocial);
			this.getObraSocial().setNroAfiliado(numeroCarnet);
			this.getObraSocial().setEstado(estado);
			this.getObraSocial().setFechaVencimientoCarnet(fVencimientoCarnet);
			this.getObraSocial().setObservacionAfiliacion(observacionObraSocial);
		}
	}

	public void indicarEstadoObraSocial(EstadoObraSocial estado, String observacionObraSocial) {
		if (this.getObraSocial() == null) {
			this.setObraSocial(new CoberturaObraSocial(estado, observacionObraSocial));	
		} else {
			this.getObraSocial().setEstado(estado);
			this.getObraSocial().setObraSocial(null);
			this.getObraSocial().setNroAfiliado("");
			this.getObraSocial().setEstado(estado);
			this.getObraSocial().setFechaVencimientoCarnet(null);
			this.getObraSocial().setObservacionAfiliacion(observacionObraSocial);
		}
		
	}
	
	public void agregarDiscapacidad(TipoDeDiscapacidad tipoDeDiscapacidad,
			Integer porcentajeDiscapacidad, Boolean certificadoDiscapacidad,
			Date fDiscapacidad, String observacionDiscapacidad, EstadoObraSocial estadoSeDesconoce) {
		if (this.getObraSocial() == null) {
			this.setObraSocial(new CoberturaObraSocial(tipoDeDiscapacidad, porcentajeDiscapacidad, certificadoDiscapacidad, fDiscapacidad, observacionDiscapacidad));			
			this.getObraSocial().setEstado(estadoSeDesconoce);			
		} else {
			this.getObraSocial().setTipoDeDiscapacidad(tipoDeDiscapacidad);
			this.getObraSocial().setPorcentaje(porcentajeDiscapacidad);
			this.getObraSocial().setCertificadoDiscapacidad(certificadoDiscapacidad);
			this.getObraSocial().setFechaDiscapacidad(fDiscapacidad);
			this.getObraSocial().setObservacionDiscapacidad(observacionDiscapacidad);
		}
	}

	public void sinDiscapacidad() {
		if (this.getObraSocial() != null) {
			this.getObraSocial().setTipoDeDiscapacidad(null);
			this.getObraSocial().setPorcentaje(null);
			this.getObraSocial().setCertificadoDiscapacidad(false);
			this.getObraSocial().setFechaDiscapacidad(null);
			this.getObraSocial().setObservacionDiscapacidad("");
		}
	}
	
	public boolean tieneDatosDeCoberturaSocialOPension() {
		boolean tienePension = false;
		Iterator<BeneficioDelJoven> it = this.getBeneficios().iterator();
				
		while (!tienePension && it.hasNext()) {
			BeneficioDelJoven ben = (BeneficioDelJoven) it.next();
			
			if (ben.getTipo().getGrupo().getId().equals(GrupoDeBeneficio.ID_BENEFICIO_PENSION) && ben.getFechaBaja() != null)
				tienePension = true;
		}
		
		return (this.getObraSocial() != null || tienePension);
	}

	public HistoriaClinica getTratamientoActual() {
		HistoriaClinica historia = null;
		Iterator<HistoriaClinica> iter = this.getTratamientos().iterator();
		while (historia == null && iter.hasNext()) {
			HistoriaClinica historiaClinica = iter.next();
			if (historiaClinica.getFechaFin() == null)
				historia = historiaClinica;
		}
		
		return historia;
	}
	
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public Boolean getTieneFichaDactiloscopica() {
		return tieneFichaDactiloscopica;
	}

	public void setTieneFichaDactiloscopica(Boolean tieneFichaDactiloscopica) {
		this.tieneFichaDactiloscopica = tieneFichaDactiloscopica;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public CoberturaObraSocial getObraSocial() {
		return obraSocial;
	}
	
	public void setObraSocial(CoberturaObraSocial value) {
		obraSocial = value;
	}

	public java.util.List<Familiar> getFamiliares() {
		return familiares;
	}

	public void setFamiliares(java.util.List<Familiar> value) {
		familiares = value;
	}

	public java.util.List<Escolaridad> getEscolaridades() {
		return escolaridades;
	}

	public void setEscolaridades(java.util.List<Escolaridad> value) {
		escolaridades = value;
	}

	public java.util.List<CapacitacionJoven> getCapacitaciones() {
		return capacitaciones;
	}

	public void setCapacitaciones(java.util.List<CapacitacionJoven> capacitaciones) {
		this.capacitaciones = capacitaciones;
	}

	public java.util.List<SituacionTramite> getSituacionesTramiteDocumento() {
		return situacionesTramiteDocumento;
	}

	public void setSituacionesTramiteDocumento(
			java.util.List<SituacionTramite> value) {
		situacionesTramiteDocumento = value;
	}

	public SituacionTramite getUltimaSituacionesTramiteDocumento() {
		return ultimaSituacionesTramiteDocumento;
	}

	public void setUltimaSituacionesTramiteDocumento(
			SituacionTramite ultimaSituacionesTramiteDocumento) {
		this.ultimaSituacionesTramiteDocumento = ultimaSituacionesTramiteDocumento;
	}

	public java.util.List<ProcesoPenal> getProcesos() {
		return procesos;
	}

	public void setProcesos(java.util.List<ProcesoPenal> value) {
		procesos = value;
	}

	public java.util.List<IntervencionPenal> getIntervencionesSRPJ() {
		return intervencionesSRPJ;
	}

	public void setIntervencionesSRPJ(java.util.List<IntervencionPenal> value) {
		intervencionesSRPJ = value;
	}

	public java.util.List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(java.util.List<Movimiento> value) {
		movimientos = value;
	}

	public java.util.List<Informe> getInformes() {
		return informes;
	}

	public void setInformes(java.util.List<Informe> Informes) {
		this.informes = Informes;
	}

	public java.util.List<Expediente> getExpedientes() {
		return expedientes;
	}

	public Expediente getExpedienteIdentificador() {
		return expedienteIdentificador;
	}

	public void setExpedienteIdentificador(Expediente expedienteIdentificador) {
		this.expedienteIdentificador = expedienteIdentificador;
	}

	public void setExpedientes(java.util.List<Expediente> expediente) {
		expedientes = expediente;
	}

	public boolean isTieneDocumento() {
		return this.getPersona().isTieneDocumento();
	}

	public void setTieneDocumento(boolean tieneDocumento) {
		this.getPersona().setTieneDocumento(tieneDocumento);
	}

	public String getNombres() {
		return this.getPersona().getNombres();
	}

	public void setNombres(String nombres) {
		this.getPersona().setNombres(nombres);
	}

	public String getApellidos() {
		return this.getPersona().getApellidos();
	}

	public void setApellidos(String apellidos) {
		this.getPersona().setApellidos(apellidos);
	}

	public String getApellidoMaterno() {
		return this.getPersona().getApellidoMaterno();
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.getPersona().setApellidoMaterno(apellidoMaterno);
	}
	
	public String getNumeroDocumento() {
		return this.getPersona().getNumeroDocumento();
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.getPersona().setNumeroDocumento(numeroDocumento);
	}

	public String getSexo() {
		return this.getPersona().getSexo();
	}

	public void setSexo(String sexo) {
		this.getPersona().setSexo(sexo);
	}

	public Date getFechaNacimiento() {
		return this.getPersona().getFechaNacimiento();
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.getPersona().setFechaNacimiento(fechaNacimiento);
	}

	public String getCuil() {
		return this.getPersona().getCuil();
	}

	public void setCuil(String cuil) {
		this.getPersona().setCuil(cuil);
	}

	public Integer getEdad() {
		return this.getPersona().getEdad();
	}

	public void setEdad(Integer edad) {
		this.getPersona().setEdad(edad);
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return this.getPersona().getTipoDeDocumento();
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.getPersona().setTipoDeDocumento(tipoDeDocumento);
	}

	public Date getFechaFallecimiento() {
		return this.getPersona().getFechaFallecimiento();
	}

	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.getPersona().setFechaFallecimiento(fechaFallecimiento);
	}

	public Provincia getProvincia() {
		return this.getPersona().getProvincia();
	}

	public void setProvincia(Provincia provincia) {
		this.getPersona().setProvincia(provincia);
	}

	public Municipio getMunicipio() {
		return this.getPersona().getMunicipio();
	}

	public void setMunicipio(Municipio municipio) {
		this.getPersona().setMunicipio(municipio);
	}

	public Localidad getLocalidad() {
		return this.getPersona().getLocalidad();
	}

	public void setLocalidad(Localidad localidad) {
		this.getPersona().setLocalidad(localidad);
	}

	public Domicilio getDomicilio() {
		return this.getPersona().getDomicilio();
	}

	public void setDomicilio(Domicilio domicilio) {
		this.getPersona().setDomicilio(domicilio);
	}

	public Nacionalidad getNacionalidad() {
		return this.getPersona().getNacionalidad();
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.getPersona().setNacionalidad(nacionalidad);
	}

	public EstadoCivil getEstadoCivil() {
		return this.getPersona().getEstadoCivil();
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.getPersona().setEstadoCivil(estadoCivil);
	}

	public TipoDeTrabajo getTipoDeTrabajo() {
		return this.getPersona().getTipoDeTrabajo();
	}
	
	public java.util.List<Permanencia> getPermanencias() {
		return permanencias;
	}

	public void setPermanencias(java.util.List<Permanencia> permanencias) {
		this.permanencias = permanencias;
	}

	/*public java.util.List<InscripcionPrograma> getProgramas() {
		return programas;
	}

	public void setProgramas(java.util.List<InscripcionPrograma> programas) {
		this.programas = programas;
	}*/

	public java.util.List<BeneficioDelJoven> getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(java.util.List<BeneficioDelJoven> beneficios) {
		this.beneficios = beneficios;
	}

	public java.util.List<Lineamiento> getLineamientos() {
		return lineamientos;
	}

	public void setLineamientos(java.util.List<Lineamiento> lineamientos) {
		this.lineamientos = lineamientos;
	}

	public String getDedo() {
		return dedo;
	}

	public void setDedo(String dedo) {
		this.dedo = dedo;
	}

	public Legajo getLegajo() {
		return legajo;
	}

	public void setLegajo(Legajo legajo) {
		this.legajo = legajo;
	}

	public java.util.List<HistoriaClinica> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(java.util.List<HistoriaClinica> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public java.util.List<RegistroAdmision> getAdmisiones() {
		return admisiones;
	}

	public void setAdmisiones(java.util.List<RegistroAdmision> admisiones) {
		this.admisiones = admisiones;
	}

	public java.util.List<Intervencion> getIntervenciones() {
		return intervenciones;
	}

	public void setIntervenciones(java.util.List<Intervencion> intervenciones) {
		this.intervenciones = intervenciones;
	}

	public Intervencion agregarIntervencion(TipoDeIntervencion tipo) {
		Intervencion interv = new Intervencion(tipo, this);
		this.getIntervenciones().add(interv);
		
		return interv;
	}
	
	public Intervencion traerIntervencion(Integer idIntervencion) {
		Iterator<Intervencion> iter = this.getIntervenciones().iterator();
		Intervencion interv = null;
		
		while (iter.hasNext()) {
			Intervencion intervencion = (Intervencion) iter.next();
			if (intervencion.getId().equals(idIntervencion))
				interv = intervencion;
		}
		return interv;
	}
}
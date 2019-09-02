package snya.reina.serviciomodelo.proceso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import snya.reina.modelo.institucion.ContactoInstitucion;
import snya.reina.modelo.institucion.Defensoria;
import snya.reina.modelo.institucion.Fiscalia;
import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.movimiento.MotivoMovimientoDAO;
import snya.reina.datos.proceso.TipoDeDetalleDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMomentoProcesalDAO;
import snya.reina.datos.proceso.TipoDeMotivoIntervencionDAO;
import snya.reina.datos.proceso.TipoDeNotaProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeSituacionProcesalDAO;
import snya.reina.modelo.AsociacionAccionProcesos;
import snya.reina.modelo.intervencion.IntervencionPenal;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.TipoDeMotivoMovimiento;
import snya.reina.modelo.proceso.CorteDeProcesoPenal;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.MotivoIntervencionEnProcesoPenal;
import snya.reina.modelo.proceso.NotaProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMomentoProcesal;
import snya.reina.modelo.proceso.TipoDeMotivoIntervencion;
import snya.reina.modelo.proceso.TipoDeNotaProcesoPenal;
import snya.reina.modelo.proceso.TipoDeSituacionProcesal;
import snya.reina.modelo.proceso.comando.ComandoProceso;
import snya.reina.serviciomodelo.GeneradorDeComando;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;
import snya.reina.serviciomodelo.movimiento.GeneradorDeMovimiento;
import snya.reina.serviciomodelo.resultado.ResultadoProceso;

public class AdministradorDeProceso {

	private MotivoMovimientoDAO motivoMovimientoDAO;
	private TipoDeMotivoIntervencionDAO tipoDeMotivoIntervencionDAO;
	private TipoDeMomentoProcesalDAO tipoDeMomentoProcesalDAO;
	private TipoDeMedidaEnProcesoPenalDAO tipoDeMedidaEnProcesoPenalDAO;
	private TipoDeDetalleDeMedidaEnProcesoPenalDAO tipoDeDetalleDeMedidaEnProcesoPenalDAO;
	private TipoDeSituacionProcesalDAO tipoDeSituacionProcesalDAO;
	private TipoDeNotaProcesoPenalDAO tipoDeNotaProcesoPenalDAO;
	private GuiaDeRecursos recursero;
	private EscritorNarrativoDeHistoria escritor;
	
	
	public AdministradorDeProceso(
			MotivoMovimientoDAO motivoMovimientoDAO,
			TipoDeMotivoIntervencionDAO tipoDeMotivoIntervencionDAO,
			TipoDeMomentoProcesalDAO tipoDeMomentoProcesalDAO,
			TipoDeMedidaEnProcesoPenalDAO tipoDeMedidaEnProcesoPenalDAO,
			TipoDeDetalleDeMedidaEnProcesoPenalDAO tipoDeDetalleDeMedidaEnProcesoPenalDAO,
			TipoDeSituacionProcesalDAO tipoDeSituacionProccesalDAO,
			TipoDeNotaProcesoPenalDAO tipoDeNotaProcesoPenalDAO,
			GuiaDeRecursos recursero,
			EscritorNarrativoDeHistoria escritor) {
		super();
		this.motivoMovimientoDAO = motivoMovimientoDAO;
		this.tipoDeMotivoIntervencionDAO = tipoDeMotivoIntervencionDAO;
		this.tipoDeMomentoProcesalDAO = tipoDeMomentoProcesalDAO;
		this.tipoDeMedidaEnProcesoPenalDAO = tipoDeMedidaEnProcesoPenalDAO;
		this.tipoDeDetalleDeMedidaEnProcesoPenalDAO = tipoDeDetalleDeMedidaEnProcesoPenalDAO;
		this.tipoDeSituacionProcesalDAO = tipoDeSituacionProccesalDAO;
		this.tipoDeNotaProcesoPenalDAO = tipoDeNotaProcesoPenalDAO;
		this.recursero = recursero;
		this.escritor = escritor;
	}
	
	
	//
	// METODOS DE PROCESO
	//
	public CorteDeProcesoPenal agregarProcesoPenal(Joven joven,
			Integer idJuzgado, Integer idFiscalia, Boolean esDefensorOficial,
			Integer idDefensoria, Integer idDefensorOficial, String abogado, String iPP,
			String nroCarpeta, String nroCausa, Integer idCaratula, Boolean gradoTentativa,
			String observacionCaratula, Date fechaIngreso,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		OrganoJudicial juzgado = (idJuzgado != null) ? recursero.traerOrganoJudicialPorId(idJuzgado) : null;
		Fiscalia fiscalia = (idFiscalia != null) ? recursero.traerFiscalialPorId(idFiscalia) : null;
		Defensoria defensoria = (esDefensorOficial != null) 
				? ((esDefensorOficial) ? recursero.traerDefensoriaPorId(idDefensoria) : null)
				: null;
		ContactoInstitucion defensor = (esDefensorOficial != null) 
				? ((esDefensorOficial) ? recursero.traerContactoInstitucionDeLaDefensoriaPorId(idDefensoria,idDefensorOficial) : null)
				: null;
		TipoDeMotivoIntervencion tipoDeMotivo = tipoDeMotivoIntervencionDAO.traerPorId(idCaratula);
		TipoDeMomentoProcesal tipoMomentoProcesal = tipoDeMomentoProcesalDAO.traerPorId(idMomentoProcesal);
		TipoDeMedidaEnProcesoPenal tipoMedida = tipoDeMedidaEnProcesoPenalDAO.traerPorId(idTipoMedida);
		
		List<TipoDeDetalleDeMedidaEnProcesoPenal> detallesMedida = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		if (idsMedidasImpuestas != null)
			for (int i = 0; i < idsMedidasImpuestas.length; i++) {
				detallesMedida.add(tipoDeDetalleDeMedidaEnProcesoPenalDAO.traerPorId(idsMedidasImpuestas[i]));
			}

		return joven.agregarProcesoPenal(this, fechaIngreso, juzgado, fiscalia, esDefensorOficial, defensoria, defensor, abogado, iPP, nroCarpeta, nroCausa, tipoDeMotivo,
				gradoTentativa, observacionCaratula, tipoMomentoProcesal, tipoMedida, detallesMedida, observacionMedida,
				horasDeMedida, diasDeMedida, mesesDeMedida,	fechaFinDeMedida);
	}

	public void modificarProcesoPenal(Integer idJuzgadoOriginal, Integer idJuzgado, Integer idFiscalia,
			Boolean esDefensorOficial, Integer idDefensoria, Integer idDefensorOficial, String abogado,
			String iPP, String nroCarpeta, String nroCausa, ProcesoPenal proceso) {
		OrganoJudicial juzgadoOriginal = (idJuzgadoOriginal != null) ? recursero.traerOrganoJudicialPorId(idJuzgadoOriginal) : null;
		OrganoJudicial juzgado = (idJuzgado != null) ? recursero.traerOrganoJudicialPorId(idJuzgado) : null;
		Fiscalia fiscalia = (idFiscalia != null) ? recursero.traerFiscalialPorId(idFiscalia) : null;
		Defensoria defensoria = (esDefensorOficial != null) 
				? ((esDefensorOficial) ? recursero.traerDefensoriaPorId(idDefensoria) : null)
				: null;
		ContactoInstitucion defensor = (esDefensorOficial != null) 
				? ((esDefensorOficial) ? recursero.traerContactoInstitucionDeLaDefensoriaPorId(idDefensoria,idDefensorOficial) : null)
				: null;

				
		if (juzgadoOriginal != null) proceso.setOrganoJudicialOriginal(juzgadoOriginal);
				
		if (proceso.getOrganoJudicialOriginal() == null) proceso.setOrganoJudicialOriginal(juzgado);
		proceso.setOrganoJudicial(juzgado);
		proceso.setFiscalia(fiscalia);
		proceso.setEsDefensorOficial(esDefensorOficial);
		proceso.setDefensoria(defensoria);
		proceso.setDefensor(defensor);
		proceso.setAbogado(abogado);
		proceso.setIPP(iPP);
		proceso.setNroCarpeta(nroCarpeta);
		proceso.setNroCausa(nroCausa);
	}

	public void modificarMotivoIntervencion(Integer idCaratula, Boolean gradoTentativa, String observacionCaratula,
			ProcesoPenal procesoPenal) {
		MotivoIntervencionEnProcesoPenal motivo = procesoPenal.getMotivoIntervencion();
		
		TipoDeMotivoIntervencion tipo = tipoDeMotivoIntervencionDAO.traerPorId(idCaratula);
		motivo.setTipo(tipo);
		motivo.setGradoTentativa(gradoTentativa);
		motivo.setObservacion(observacionCaratula);		
	}
	
	public void reabrirProcesoPenal(ProcesoPenal proceso) throws ReinaException {
		proceso.reabrirProcesoPenal(this);
	}
	
	public void unificarAlProceso(Date fecha, ProcesoPenal proceso, Integer idProceso) throws ReinaException {
		ProcesoPenal unificador = this.traerProceso(proceso.getJoven(), idProceso);
		TipoDeSituacionProcesal tipoDeSituacion = tipoDeSituacionProcesalDAO.traerPorId(TipoDeSituacionProcesal.ID_UNIFICADO);
		
		proceso.unificarAlProceso(fecha, tipoDeSituacion, unificador);	
	}

	public void dividirProceso(ProcesoPenal proceso) throws ReinaException {
		proceso.recobrarAutonomia(this);
	}
	
	public void eliminarProceso(ProcesoPenal proceso) throws ReinaException {
		try {
			Joven joven = proceso.getJoven();
			
			proceso.puedeSerEliminado();
			joven.eliminarProceso(proceso);
		} catch (ReinaException e) {
			ReinaException ex = new ReinaException("Error al eliminar proceso", e);			
			throw ex;
		}	
	}
	
	
	//
	// METODOS DE MOMENTOS PROCESALES - MEDIDAS
	//	
	public CorteDeProcesoPenal agregarMomentoProcesal(Joven joven, ProcesoPenal proceso,
			Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;
		TipoDeMomentoProcesal tipo = tipoDeMomentoProcesalDAO.traerPorId(idMomentoProcesal);
		TipoDeMedidaEnProcesoPenal tipoMedida = tipoDeMedidaEnProcesoPenalDAO.traerPorId(idTipoMedida);
		List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		if (idsMedidasImpuestas != null)
			for (int i = 0; i < idsMedidasImpuestas.length; i++) {
				detalles.add(tipoDeDetalleDeMedidaEnProcesoPenalDAO.traerPorId(idsMedidasImpuestas[i]));
			}
		
		proceso.agregarMomentoProcesal(this, fecha, organoJudicial, tipo, tipoMedida, detalles, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida,	fechaFinDeMedida);
		
		return new CorteDeProcesoPenal(proceso, proceso.getMotivoIntervencion(), proceso.getMomentoProcesal(), proceso.getMedidaImpuesta(), proceso.getSituacionProcesal());
	}
	
	
	public void agregarMomentoProcesal(ProcesoPenal proceso, Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal) throws ReinaException {		
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;		
		TipoDeMomentoProcesal tipoMomentoProcesal = tipoDeMomentoProcesalDAO.traerPorId(idMomentoProcesal);

		proceso.agregarMomentoProcesal(this, fecha, organoJudicial, tipoMomentoProcesal);		
	}
	

	public void modificarMomentoProcesal(ProcesoPenal proceso, Integer id, Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal) throws ReinaException {
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;		
		TipoDeMomentoProcesal tipoMomentoProcesal = tipoDeMomentoProcesalDAO.traerPorId(idMomentoProcesal);

		proceso.modificarMomentoProcesal(this, id, fecha, organoJudicial, tipoMomentoProcesal);		
	}
	
	public ResultadoProceso modificarMomentoProcesal(Joven joven, ProcesoPenal proceso,
			Integer idMomento, Date fecha, Integer idOrganoJudicial,
			Integer idMomentoProcesal, Date fechaMedida,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas,
			String observacionMedida, Integer horasDeMedida,
			Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;
		TipoDeMomentoProcesal tipo = tipoDeMomentoProcesalDAO.traerPorId(idMomentoProcesal);
		TipoDeMedidaEnProcesoPenal tipoMedida = tipoDeMedidaEnProcesoPenalDAO.traerPorId(idTipoMedida);
		List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		if (idsMedidasImpuestas != null)
			for (int i = 0; i < idsMedidasImpuestas.length; i++) {
				detalles.add(tipoDeDetalleDeMedidaEnProcesoPenalDAO.traerPorId(idsMedidasImpuestas[i]));
			}
		
		return proceso.modificarMomentoProcesal(this, idMomento, fecha, organoJudicial, tipo, fechaMedida, tipoMedida, detalles, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida,	fechaFinDeMedida);
	}
	
	
	//
	// METODOS DE MEDIDAS
	//	
	public ResultadoProceso agregarMedida(ProcesoPenal proceso, Date fecha,
			Integer idOrganoJudicial, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;		
		TipoDeMedidaEnProcesoPenal tipoMedida = tipoDeMedidaEnProcesoPenalDAO.traerPorId(idTipoMedida);
		List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		if (idsMedidasImpuestas != null)
			for (int i = 0; i < idsMedidasImpuestas.length; i++) {
				detalles.add(tipoDeDetalleDeMedidaEnProcesoPenalDAO.traerPorId(idsMedidasImpuestas[i]));
			}

		return proceso.agregarMedida(this, fecha, organoJudicial, tipoMedida, detalles, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
	}
	
	public ResultadoProceso modificarMedida(ProcesoPenal proceso, Integer id, Date fecha,
			Integer idOrganoJudicial, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;		
		TipoDeMedidaEnProcesoPenal tipoMedida = tipoDeMedidaEnProcesoPenalDAO.traerPorId(idTipoMedida);
		List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		if (idsMedidasImpuestas != null)
			for (int i = 0; i < idsMedidasImpuestas.length; i++) {
				detalles.add(tipoDeDetalleDeMedidaEnProcesoPenalDAO.traerPorId(idsMedidasImpuestas[i]));
			}

		return proceso.modificarMedida(id, this, fecha, organoJudicial, null, tipoMedida, detalles, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
	}
	
	
	//
	// METODOS DE NOTAS
	//	
	public void agregarNota(ProcesoPenal proceso, Date fechaNota, Integer idTipo, String observacion, Integer idOrganoJudicial, Integer idMedida, Integer idProceso) throws ReinaException {
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;
		TipoDeNotaProcesoPenal tipo = tipoDeNotaProcesoPenalDAO.traerPorId(idTipo);
		
		NotaProcesoPenal nota = new NotaProcesoPenal(fechaNota, tipo, observacion);		
		ejecutarComando(proceso, nota, organoJudicial, idMedida, idProceso);

		proceso.getNotas().add(nota);
		nota.setProceso(proceso);		
	}

	public void modificiarNota(ProcesoPenal proceso, NotaProcesoPenal nota, String observacion) {
		nota.setObservacion(observacion);
	}

	public void eliminarNota(ProcesoPenal proceso, NotaProcesoPenal nota) throws ReinaException {	
		proceso.getNotas().remove(nota);
		
		deshacerComando(proceso, nota);
	}
		
	
	//
	// METODOS ASOCIACION CON MOVIMIENTOS - INTERVENCIONES
	//	
	public void asociarYEliminarProceso(Joven joven, ProcesoPenal proceso,
			Map<Integer, AsociacionAccionProcesos> mov,
			Map<Integer, AsociacionAccionProcesos> interv) throws ReinaException {
		TipoDeMotivoMovimiento motivoMovimiento = motivoMovimientoDAO.traerPorId(TipoDeMotivoMovimiento.ID_MOTIVO_NOTIFICACION);
		
		// Reasignacion en los movimientos
		for (Movimiento movimiento : joven.getMovimientos()) {
			if ( mov.containsKey( movimiento.getId() ) ) {
				MedidaEnProcesoPenal medida = this.recuperarMedida(joven, mov.get( movimiento.getId() ) );
				if (medida != null) {
					movimiento.setMedidaImpuesta(medida);
				} else {
					movimiento.setMotivo(motivoMovimiento);
					movimiento.setMedidaImpuesta(null);
				}
				
				movimiento.actualizarHistorial(escritor);
			}
		}
		
		// Reasignacion en las intervenciones
		for (IntervencionPenal intervencion : joven.getIntervencionesSRPJ()) {
			if ( interv.containsKey( intervencion.getId() ) ) {
				MedidaEnProcesoPenal medida = this.recuperarMedida(joven, interv.get( intervencion.getId() ) );
				if (medida != null) {
					intervencion.setMedida(medida);
				} else {
					throw new ReinaException(ReinaCte.INTERVENCION_SRPJ_SIN_MEDIDA);
				}
			}
		}
		
		joven.getProcesos().remove(proceso);
	}
	
	public void acomodarMovimientos(ProcesoPenal proceso) {
		GeneradorDeMovimiento generador = new GeneradorDeMovimiento();
		
		generador.acomodarMovimientosDelProceso(proceso.getJoven(), proceso);
	}
	
	public void asociarProcesoPresente(Joven joven, ProcesoPenal proceso, ProcesoPenal procesoSeleccionado, MedidaEnProcesoPenal medida) {
		GeneradorDeMovimiento generador = new GeneradorDeMovimiento();
		
		generador.acomodarMovimientosDelProcesoAPartirDelCierre(joven, proceso, procesoSeleccionado, medida);
	}
	
	
	//
	// METODOS VARIOS
	//	
	public ProcesoPenal traerProceso(Joven joven, Integer id) {
		java.util.Iterator<ProcesoPenal> iter = joven.getProcesos().iterator();
		ProcesoPenal proc = null;
		
		while (iter.hasNext()) {
			ProcesoPenal p = iter.next();
			if (p.getId().equals(id))
				proc = p;
		}
		
		return proc;
	}
	
	public MedidaEnProcesoPenal traerMedida(ProcesoPenal proceso, Integer id) {
		java.util.Iterator<MedidaEnProcesoPenal> iter = proceso.getMedidasImpuestas().iterator();
		MedidaEnProcesoPenal med = null;
		
		while (iter.hasNext()) {
			MedidaEnProcesoPenal m = iter.next();
			if (m.getId().equals(id))
				med = m;
		}
		
		return med;
	}
	
	public NotaProcesoPenal traerNota(ProcesoPenal proceso, Integer id) {
		NotaProcesoPenal nota = null;
		
		java.util.Iterator<NotaProcesoPenal> it = proceso.getNotas().iterator();
		while (nota == null && it.hasNext()) {
			NotaProcesoPenal medidaEnProceso = (NotaProcesoPenal) it.next();
			if (medidaEnProceso.getId().equals(id))
				nota = medidaEnProceso;
		}
		
		return nota;
	}
	
	public OrganoJudicial traerOrganoJudicial(Integer idOrg) {
		return recursero.traerOrganoJudicialPorId(idOrg);
	}
	
	public TipoDeSituacionProcesal traerSituacionProcesal(Integer momento, Integer medida) {
		return tipoDeSituacionProcesalDAO.calcularSituacionProcesal(momento, medida);
	}

	
	private MedidaEnProcesoPenal recuperarMedida(Joven joven, AsociacionAccionProcesos asociacion) {
		ProcesoPenal proceso = this.traerProceso(joven, asociacion.getIdProceso());
		MedidaEnProcesoPenal medida = this.traerMedida(proceso, asociacion.getIdMedidaEnProcesoPenal());
				
		return medida;
	}
	
	private void ejecutarComando(ProcesoPenal proceso, NotaProcesoPenal nota,
			OrganoJudicial organoJudicial, Integer idMedida, Integer idProceso) throws ReinaException {
		if (nota.getTipo().getComando() != null && !nota.getTipo().getComando().equals("") ) {
			ComandoProceso comando = (ComandoProceso) GeneradorDeComando.CrearComando(nota.getTipo().getComando());
			comando.configurar(this, proceso, nota, organoJudicial, idMedida, idProceso);
			comando.hacer();			
		}
	}
	
	private void deshacerComando(ProcesoPenal proceso, NotaProcesoPenal nota) throws ReinaException {
		if (nota.getTipo().getComando() != null && !nota.getTipo().getComando().equals("")) {
			ComandoProceso comando = (ComandoProceso) GeneradorDeComando.CrearComando(nota.getTipo().getComando());
			comando.configurar(this, proceso, nota);
			comando.deshacer();
		}
	}
}
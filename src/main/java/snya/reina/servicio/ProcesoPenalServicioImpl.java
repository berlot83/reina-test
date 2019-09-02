package snya.reina.servicio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.movimiento.MotivoMovimientoDAO;
import snya.reina.datos.proceso.TipoDeDetalleDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMomentoProcesalDAO;
import snya.reina.datos.proceso.TipoDeMotivoIntervencionDAO;
import snya.reina.datos.proceso.TipoDeNotaProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeSituacionProcesalDAO;
import snya.reina.modelo.AsociacionAccionProcesos;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.MedidaEnProcesoDelJoven;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.proceso.CorteDeProcesoPenal;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.MomentoProcesal;
import snya.reina.modelo.proceso.NotaProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMomentoProcesal;
import snya.reina.modelo.proceso.TipoDeMotivoIntervencion;
import snya.reina.modelo.proceso.TipoDeNotaProcesoPenal;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;
import snya.reina.serviciomodelo.resultado.ResultadoProceso;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;

@Service
public class ProcesoPenalServicioImpl {

	@Autowired
	private MotivoMovimientoDAO motivoMovimientoDAO;
	@Autowired
	private TipoDeMotivoIntervencionDAO tipoDeMotivoIntervencionDAO;
	@Autowired
	private TipoDeMomentoProcesalDAO tipoDeMomentoProcesalDAO;
	@Autowired
	private TipoDeMedidaEnProcesoPenalDAO tipoDeMedidaEnProcesoPenalDAO;
	@Autowired
	private TipoDeDetalleDeMedidaEnProcesoPenalDAO tipoDeDetalleDeMedidaEnProcesoPenalDAO;
	@Autowired
	private TipoDeSituacionProcesalDAO tipoDeSituacionProcesalDAO;
	@Autowired
	private TipoDeNotaProcesoPenalDAO tipoDeNotaProcesoPenalDAO;
	@Autowired
	private GuiaDeRecursos recursero;
	
	@Autowired
	private JovenDAO jovenDAO;
	
	
	public CorteDeProcesoPenal crearProcesoPenal(Joven joven, Integer idJuzgado,
			Integer idFiscalia, Boolean esDefensorOficial,
			Integer idDefensoria, Integer idDefensorOficial, String abogado, String iPP,
			String nroCarpeta, String nroCausa, Integer idCaratula, Boolean gradoTentativa,
			String observacionCaratula, String fechaImposicion,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Date fechaIngreso = null;				
		try {
			fechaIngreso = Calendario.parsearFecha(fechaImposicion);
		} catch (ParseException e) {
			throw new ReinaException(
					ReinaCte.FECHA_INICIO_PROCESO_PENAL_ERRONEA);
		}

		return administrador.agregarProcesoPenal(joven, idJuzgado, idFiscalia, esDefensorOficial,
				idDefensoria, idDefensorOficial, abogado, iPP, nroCarpeta, nroCausa, idCaratula, gradoTentativa,
				observacionCaratula, fechaIngreso, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);		
	}
	
	@Transactional
	public void guardarProcesoPenal(Integer idJoven, Date fechaImposicion,
			Integer idJuzgado, Integer idFiscalia, Boolean esDefensorOficial,
			Integer idDefensoria, Integer idDefensorOficial, String abogado, String iPP, String nroCarpeta,
			String nroCausa, Integer idCaratula, Boolean gradoTentativa, String observacionCaratula,
			Integer idMomentoProcesal, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);

		administrador.agregarProcesoPenal(joven, idJuzgado, idFiscalia, esDefensorOficial,
				idDefensoria, idDefensorOficial, abogado, iPP, nroCarpeta, nroCausa, idCaratula, gradoTentativa,
				observacionCaratula, fechaImposicion, idMomentoProcesal, idTipoMedida,
				idsMedidasImpuestas, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void actualizarProcesoPenal(Integer idJoven, Integer idProceso,
			Integer idJuzgadoOriginal, Integer idJuzgado, Integer idFiscalia, Boolean esDefensorOficial,
			Integer idDefensoria, Integer idDefensorOficial, String abogado, String iPP, String nroCarpeta,
			String nroCausa) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		administrador.modificarProcesoPenal(idJuzgadoOriginal, idJuzgado, idFiscalia, esDefensorOficial,
				idDefensoria, idDefensorOficial, abogado, iPP, nroCarpeta, nroCausa,
				proceso);		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void reabrirProcesoPenal(Integer idJoven, Integer id) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, id);
		
		administrador.reabrirProcesoPenal(proceso);		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void eliminarProceso(Integer idJoven, Integer id) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, id);
		
		administrador.eliminarProceso(proceso);		
		jovenDAO.actualizar(joven);	
	}
	
	public void agregarMotivoIntervencion(ProcesoPenal proceso,
			OrganoJudicial organoJudicial, Date fecha, Integer idCaratula, Boolean gradoTentativa,
			String observacion) throws ReinaException {	
		TipoDeMotivoIntervencion tipo = tipoDeMotivoIntervencionDAO.traerPorId(idCaratula);

		proceso.agregarMotivoIntervencion(organoJudicial, fecha, tipo, gradoTentativa, observacion);
	}

	@Transactional
	public void agregarMotivoIntervencion(Integer idJoven,
			Integer idProceso, Date fecha, Integer idOrganoJudicial,
			Integer idMotivoIntervencion, Boolean gradoTentativa, String observacion) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;
		TipoDeMotivoIntervencion tipoDeMotivo = tipoDeMotivoIntervencionDAO.traerPorId(idMotivoIntervencion);
		
		proceso.agregarMotivoIntervencion(organoJudicial, fecha, tipoDeMotivo, gradoTentativa, observacion);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void actualizarMotivoIntervencion(Integer idJoven, Integer idProceso,
			Integer id, Date fecha, Integer idOrganoJudicial, Integer idMotivoIntervencion,
			Boolean gradoTentativa, String observacion) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		OrganoJudicial organoJudicial = (idOrganoJudicial != null) ? recursero.traerOrganoJudicialPorId(idOrganoJudicial) : null;
		TipoDeMotivoIntervencion tipoDeMotivo = tipoDeMotivoIntervencionDAO.traerPorId(idMotivoIntervencion);
		
		proceso.modificarMotivoIntervencion(id, organoJudicial, fecha, tipoDeMotivo, gradoTentativa, observacion);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void eliminarMotivoIntervencion(Integer idJoven, Integer idProceso, Integer id) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		proceso.eliminarMotivoIntervencion(id);
		jovenDAO.actualizar(joven);		
	}
	
	public CorteDeProcesoPenal agregarMomentoProcesal(Joven joven, ProcesoPenal proceso, Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {	
		AdministradorDeProceso administrador = this.traerAdministrador();
		
		return administrador.agregarMomentoProcesal(joven, proceso, fecha, idOrganoJudicial, idMomentoProcesal, 
				idTipoMedida, idsMedidasImpuestas, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
	}
	
	@Transactional
	public void agregarMomentoProcesal(Integer idJoven, Integer idProceso,
			Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);		
		
		administrador.agregarMomentoProcesal(proceso, fecha, idOrganoJudicial, idMomentoProcesal);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public ResultadoProceso agregarMomentoProcesal(Integer idJoven, Integer idProceso,
			Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida, 
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {		
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);		

		CorteDeProcesoPenal corte = administrador.agregarMomentoProcesal(joven, proceso, fecha, idOrganoJudicial, idMomentoProcesal, 
				idTipoMedida, idsMedidasImpuestas, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		jovenDAO.actualizar(joven);	
		
		ResultadoProceso resultado = new ResultadoProceso(corte.cierraProceso(), proceso, corte.getMedidaImpuesta());
		resultado.analizarSiQuedaPresenteSinMedida(joven);		
		return resultado;
	}
	
	@Transactional
	public void modificarMomentoProcesal(Integer idJoven, Integer idProceso, Integer id,
			Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);		
		
		administrador.modificarMomentoProcesal(proceso, id, fecha, idOrganoJudicial, idMomentoProcesal);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public ResultadoProceso modificarMomentoProcesal(Integer idJoven, Integer idProceso,
			Integer idMomento, Date fecha, Integer idOrganoJudicial,
			Integer idMomentoProcesal, Date fechaSituacionMedida,
			Integer idTipoMedida,
			Integer[] idsMedidasImpuestas,
			String observacionMedida,
			Integer horasDeMedida,
			Integer diasDeMedida,
			Integer mesesDeMedida,
			Date fechaFinDeMedida) throws ReinaException {		
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);		

		ResultadoProceso resultado = administrador.modificarMomentoProcesal(joven, proceso, idMomento, fecha, idOrganoJudicial, idMomentoProcesal, 
				fechaSituacionMedida, idTipoMedida, idsMedidasImpuestas, observacionMedida, horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		jovenDAO.actualizar(joven);	
		
		resultado.analizarSiQuedaPresenteSinMedida(joven);		
		return resultado;
	}
	
	@Transactional
	public void eliminarMomentoProcesal(Integer idJoven, Integer idProceso, Integer id) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		proceso.eliminarMomentoProcesal(administrador, id);
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional
	public ResultadoProceso agregarMedidaEnProcesoPenal(Integer idJoven, Integer idProceso,
			Date fecha, Integer idOrganoJudicial, Integer idMomentoProcesal,
			Integer idTipoMedida, Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		ResultadoProceso resultado = administrador.agregarMedida(proceso, fecha, idOrganoJudicial, idTipoMedida, idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		jovenDAO.actualizar(joven);	
		
		resultado.analizarSiQuedaPresenteSinMedida(joven);		
		return resultado;
	}
	
	@Transactional
	public ResultadoProceso actualizarMedidaEnProcesoPenal(Integer idJoven,
			Integer idProceso, Integer id, Date fecha, Integer idOrganoJudicial,
			Integer idMomento, Integer idTipoMedida,
			Integer[] idsMedidasImpuestas, String observacionMedida,
			Integer horasDeMedida, Integer diasDeMedida, Integer mesesDeMedida, Date fechaFinDeMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		ResultadoProceso resultado = administrador.modificarMedida(proceso, id, fecha, idOrganoJudicial, idTipoMedida, idsMedidasImpuestas, observacionMedida, 
				horasDeMedida, diasDeMedida, mesesDeMedida, fechaFinDeMedida);
		jovenDAO.actualizar(joven);
		
		resultado.analizarSiQuedaPresenteSinMedida(joven);		
		return resultado;
	}
	
	@Transactional
	public void eliminarMedidaEnProcesoPenal(Integer idJoven, Integer idProceso, Integer id) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		proceso.eliminarMedida(administrador, id);
		jovenDAO.actualizar(joven);		
	}

	@Transactional
	public void agregarNota(Integer idJoven, Integer idProceso, Date fechaNota,
			Integer tipo, String observacion, Integer idOrganoJudicial, Integer idMedida, Integer idProcesoSeleccionado) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		administrador.agregarNota(proceso, fechaNota, tipo, observacion, idOrganoJudicial, idMedida, idProcesoSeleccionado);

		jovenDAO.actualizar(joven);	
	}

	@Transactional
	public void actualizarNota(Integer idJoven, Integer idProceso, Integer id, String observacion) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		NotaProcesoPenal nota = administrador.traerNota(proceso, id);
		
		administrador.modificiarNota(proceso, nota, observacion);
		
		jovenDAO.actualizar(joven);	
	}

	@Transactional
	public void eliminarNota(Integer idJoven, Integer idProceso, Integer id) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		NotaProcesoPenal nota = administrador.traerNota(proceso, id);
		
		administrador.eliminarNota(proceso, nota);
		
		jovenDAO.actualizar(joven);	
	}
	
	
	public List<AsociacionAccionProcesos> listaPosiblesParejasAnteRemplazoProceso(Integer idJoven, Integer idProceso) {
		return jovenDAO.listaPosiblesParejasAnteRemplazoProceso(idJoven, idProceso);
	}
	
	@Transactional
	public void asociarYEliminarProceso(Integer idJoven, Integer idProceso,
			Map<Integer, AsociacionAccionProcesos> mov, Map<Integer, AsociacionAccionProcesos> interv) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		
		administrador.asociarYEliminarProceso(joven, proceso, mov, interv);
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional
	public void asociarProcesoPresente(Integer idJoven, Integer idProceso, Integer idProcesoSeleccionado, Integer idMedida) throws ReinaException {
		AdministradorDeProceso administrador = this.traerAdministrador();
		Joven joven = jovenDAO.traerPorId(idJoven);
		ProcesoPenal proceso = administrador.traerProceso(joven, idProceso);
		ProcesoPenal procesoSeleccionado = administrador.traerProceso(joven, idProcesoSeleccionado);
		MedidaEnProcesoPenal medida = procesoSeleccionado.traerMedidaPorId(idMedida);
		
		administrador.asociarProcesoPresente(joven, proceso, procesoSeleccionado, medida);
		jovenDAO.actualizar(joven);
	}
	
	public List<MedidaEnProcesoDelJoven> listaMedidasDelJoven(Integer idJoven, Integer idProceso, Date fecha) {
		return jovenDAO.listaMedidasDelJoven(idJoven, idProceso, fecha);
	}
	
	public List<TipoDeMotivoIntervencion> traerTipoDeMotivoIntervencionTodosActivos() {
		return tipoDeMotivoIntervencionDAO.traerTodosActivos();
	}

	public List<TipoDeMomentoProcesal> traerTipoDeMomentoProcesalTodosActivos() {
		return tipoDeMomentoProcesalDAO.traerTodosActivos();
	}

	public List<TipoDeNotaProcesoPenal> traerTipoDeNotaProcesoPenalTodosAvtivos() {
		return tipoDeNotaProcesoPenalDAO.traerTodosActivos();
	}
	
	public List<TipoDeMedidaEnProcesoPenal> traerTipoDeMedidaActivosPorMomento(int idMomento, Integer idJoven, Integer idProceso, Date fecha) {
		List<TipoDeMedidaEnProcesoPenal> tipos = new ArrayList<TipoDeMedidaEnProcesoPenal>();
		
		if(idMomento != -1)
			tipos = tipoDeMedidaEnProcesoPenalDAO.traerTipoDeMedidaActivosPorMomento(idMomento);
		else {
			if (fecha != null) {
				AdministradorDeProceso admin = traerAdministrador();
				Joven joven = jovenDAO.traerPorId(idJoven);
				ProcesoPenal proceso = admin.traerProceso(joven, idProceso);
				
				MomentoProcesal momento = proceso.traerMomentoProcesalAl(fecha);
				if (momento != null) 
					tipos = tipoDeMedidaEnProcesoPenalDAO.traerTipoDeMedidaActivosPorMomento( momento.getTipo().getId() );
			}
		}
		
		return tipos;
	}

	public List<TipoDeDetalleDeMedidaEnProcesoPenal> traerTipoDeDetalleDeMedidaEnProcesoPenalActivosPorTipo(
			int idTipoMedida) {
		return tipoDeDetalleDeMedidaEnProcesoPenalDAO.traerTipoDeDetalleDeMedidaEnProcesoPenalActivosPorTipo(idTipoMedida);
	}
	
	
	private AdministradorDeProceso traerAdministrador() {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		return new AdministradorDeProceso(motivoMovimientoDAO, tipoDeMotivoIntervencionDAO, 
				tipoDeMomentoProcesalDAO, tipoDeMedidaEnProcesoPenalDAO, tipoDeDetalleDeMedidaEnProcesoPenalDAO, tipoDeSituacionProcesalDAO, tipoDeNotaProcesoPenalDAO, recursero, escritor);
	}
}

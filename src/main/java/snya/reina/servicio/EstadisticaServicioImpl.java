package snya.reina.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.DepartamentoJudicialDAO;
import snya.general.modelo.DepartamentoJudicial;
import snya.reina.datos.estadistica.EstadisticaDAO;
import snya.reina.datos.movimiento.GrupoDeMovimientoDAO;
import snya.reina.datos.movimiento.TipoDeMovimientoDAO;
import snya.reina.modelo.ElementoPanelControl;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.estadistica.EstadisticaMovimiento;
import snya.reina.modelo.estadistica.EstadisticaPresente;
import snya.reina.modelo.movimiento.GrupoDeMovimiento;
import snya.reina.modelo.movimiento.TipoDeMovimiento;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaBeneficio;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaFormacionLaboral;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaInforme;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaIntervencion;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaMovimiento;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaPresente;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.estadistica.EstadisticaInforme;
import snya.reina.modelo.estadistica.EstadisticaIntervencion;

@Service
public class EstadisticaServicioImpl {
	@Autowired
	private EstadisticaDAO estadisticaDAO;
	@Autowired
	private DepartamentoJudicialDAO departamentoJudicialDAO;
	@Autowired
	private TipoDeMovimientoDAO tipoDeMovimientoDAO;
	@Autowired
	private GrupoDeMovimientoDAO grupoDeMovimientoDAO;
	
	public List<ElementoPanelControl> listaDatosPanelControl(Date fecha, Integer[] tipos, Integer[] recursos){
		return estadisticaDAO.listaDatosPanelControl(fecha, tipos, recursos);
	}
	
	public List<JovenSimplificado> listaJovenPanelControl(Date fecha, Integer idRecurso, Integer idGrupo, Integer idCategoria) {
		return estadisticaDAO.listaJovenPanelControl(fecha, idRecurso, idGrupo, idCategoria);
	}

	public List<EstadisticaPresente> listaEstadisticaPresente(BuscadorEstadisticaPresente busqueda) {
		return estadisticaDAO.listaEstadisticaPresente(busqueda);
	}
	
	public List<EstadisticaMovimiento> listaEstadisticaMovimiento(BuscadorEstadisticaMovimiento busqueda) {
		return estadisticaDAO.listaEstadisticaMovimiento(busqueda);
	}
	
	public List<EstadisticaIntervencion> listaEstadisticaIntervencion(BuscadorEstadisticaIntervencion busqueda) {
		return estadisticaDAO.listaEstadisticaIntervencion(busqueda);
	}
	
	public List<EstadisticaInforme> listaEstadisticaInforme(BuscadorEstadisticaInforme busqueda) {
		return estadisticaDAO.listaEstadisticaInforme(busqueda);
	}
	
	public List<EstadisticaFormacionLaboral> listaEstadisticaFormacionLaboral(BuscadorEstadisticaFormacionLaboral busqueda) {
		return estadisticaDAO.listaEstadisticaFormacionLaboral(busqueda);
	}
	
	public List<EstadisticaBeneficio> listaEstadisticaBeneficio(BuscadorEstadisticaBeneficio busqueda) {
		return estadisticaDAO.listaEstadisticaBeneficio(busqueda);
	}
	
	public List<DepartamentoJudicial> traerDepartamentosJudicialesActivos() {
		return departamentoJudicialDAO.traerTodosActivos();
	}

	public List<GrupoDeMovimiento> traerGruposDeMovimientosActivos() {
		return grupoDeMovimientoDAO.traerTodosActivos();
	}

	public List<TipoDeMovimiento> treaerTiposDeMovimientosActivos() {
		return tipoDeMovimientoDAO.traerTodosActivos();
	}
}

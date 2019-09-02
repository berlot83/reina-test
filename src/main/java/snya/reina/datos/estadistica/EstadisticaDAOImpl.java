package snya.reina.datos.estadistica;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.ElementoPanelControl;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.estadistica.EstadisticaInforme;
import snya.reina.modelo.estadistica.EstadisticaIntervencion;
import snya.reina.modelo.estadistica.EstadisticaMovimiento;
import snya.reina.modelo.estadistica.EstadisticaPresente;
import snya.reina.utilidades.Parametro;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaBeneficio;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaFormacionLaboral;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaInforme;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaIntervencion;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaMovimiento;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaPresente;

@Repository
public class EstadisticaDAOImpl extends HibernateDAOGenericoImpl<ElementoPanelControl, Integer> implements EstadisticaDAO {

	@Override
	protected Class<ElementoPanelControl> getEntityClass() {
		return ElementoPanelControl.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ElementoPanelControl> listaDatosPanelControl(Date fecha, Integer[] tipos, Integer[] recursos) {
		String ids;
		if (recursos.length <= 0) {
			ids = null;
		} else {
			ids = armarIds(recursos);
		}	
		String idTipos;
		if (tipos.length <= 0) {
			idTipos = null;
		} else {
			idTipos = armarIds(tipos);
		}
		
		
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("fecha", Calendario.formatearFechaMySql(fecha));
		valores.put("tipos", idTipos);
		valores.put("recursos", ids);		
		
		return (List<ElementoPanelControl>) this.traerTodosPorConsultaSPQuery("ConsultaPanelControl", valores);		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<JovenSimplificado> listaJovenPanelControl(Date fecha, Integer idRecurso, Integer idGrupo, Integer idCategoria) {		
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("fecha", Calendario.formatearFechaMySql(fecha));
		valores.put("idRecurso", idRecurso);
		valores.put("idGrupo", idGrupo);
		valores.put("idCategoria", idCategoria);
		
		return (List<JovenSimplificado>) this.traerTodosPorConsultaSPQuery("ConsultaJovenPanelControl", valores);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaPresente> listaEstadisticaPresente(BuscadorEstadisticaPresente busqueda) {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta =  "ConsultaEstadisticaPresente";
		
		return (List<EstadisticaPresente>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaMovimiento> listaEstadisticaMovimiento(BuscadorEstadisticaMovimiento busqueda) {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta =  "ConsultaEstadisticaMovimiento";
		
		return (List<EstadisticaMovimiento>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaIntervencion> listaEstadisticaIntervencion(BuscadorEstadisticaIntervencion busqueda) {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta =  "ConsultaEstadisticaIntervencion";
		
		return (List<EstadisticaIntervencion>) this.traerTodosPorConsultaSPQuery(consulta, valores);			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaInforme> listaEstadisticaInforme(BuscadorEstadisticaInforme busqueda) {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta =  "ConsultaEstadisticaInforme";
		
		return (List<EstadisticaInforme>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaFormacionLaboral> listaEstadisticaFormacionLaboral(BuscadorEstadisticaFormacionLaboral busqueda) {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta =  "ConsultaJovenFormacionLaboral";
		
		return (List<EstadisticaFormacionLaboral>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaBeneficio> listaEstadisticaBeneficio(BuscadorEstadisticaBeneficio busqueda) {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta =  "ConsultaJovenBeneficio";
		
		return (List<EstadisticaBeneficio>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}	
	
	
	private String armarIds(Integer[] recursos) {
		String ids = "";
		for (int i = 0; i < recursos.length; i++) {
			ids += recursos[i] + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		return ids;
	}
}

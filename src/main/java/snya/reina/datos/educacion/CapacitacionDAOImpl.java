package snya.reina.datos.educacion;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.ReinaException;
import snya.reina.modelo.educacion.Capacitacion;
import snya.reina.modelo.educacion.Dictado;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;

@Repository
public class CapacitacionDAOImpl extends HibernateDAOGenericoImpl<Capacitacion, Integer> implements CapacitacionDAO {
		
	@Override
	protected Class<Capacitacion> getEntityClass() {
		return Capacitacion.class;
	}

	@Override
	public Capacitacion traerPorId(Integer id, boolean esFormacionLaboral) {
		Capacitacion cap = this.traerPorId(id);
		
		if (cap != null && cap.getEsFormacionLaboral() && esFormacionLaboral) return cap;
		if (cap != null && !cap.getEsFormacionLaboral() && !esFormacionLaboral) return cap;
		return null;
	}

	@Override
	public List<Capacitacion> traerTodosActivos(boolean esFormacionLaboral) {
		String query = "select c from Capacitacion c where c.estaActivo = true and " + ( (esFormacionLaboral) ? " c.esFormacionLaboral = true" : " c.esFormacionLaboral = false" ); 
		return (List<Capacitacion>)this.traerTodosPorConsulta(query);
	}

	@Override
	public List<Capacitacion> traerTodosActivosEnAmbito(boolean esFormacionLaboral, Integer[] idAmbitos){
		String ids = (idAmbitos.length <= 0)  ? "" : armarIds(idAmbitos);
		
		String query = "select distinct c from Capacitacion c inner join c.dictados d left join d.instituciones i ";
		query += " where c.estaActivo = true ";
		if (idAmbitos.length > 0) query += " and  (i.id IS NULL or i.id in ('" + ids + "')) ";
		query += " and " + ( (esFormacionLaboral) ? " c.esFormacionLaboral = true" : " c.esFormacionLaboral = false" ); 
		
		return (List<Capacitacion>)this.traerTodosPorConsulta(query);
	}

	
	@Override
	public List<Capacitacion> traerTodos(boolean esFormacionLaboral) {
		String query = "select c from Capacitacion c where " + ( (esFormacionLaboral) ? " c.esFormacionLaboral = true" : " c.esFormacionLaboral = false" ); 
		return (List<Capacitacion>)this.traerTodosPorConsulta(query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictado> traerDictadosTodosActivosPorId(Integer id, Integer[] idAmbitos) {
		String ids = (idAmbitos.length <= 0)  ? "" : armarIds(idAmbitos);
		
		String query = "select distinct d from Capacitacion c inner join c.dictados d left join d.instituciones i ";
		query += " where c.id = " + id + " and c.estaActivo = true ";
		if (idAmbitos.length > 0) query += " and  (i.id IS NULL or i.id in ('" + ids + "')) ";
		
		return (List<Dictado>)this.traerTodosPorConsulta(Dictado.class, query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaFormacionLaboral> listaFormacionLaboralCursante(Integer idFormacion, Integer idDictado, Integer idEstado) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", null);
		valores.put("idRecurso", null);
		valores.put("idFormacion", idFormacion);
		valores.put("idDictado", idDictado);
		valores.put("idEstado", idEstado);
		
		return (List<EstadisticaFormacionLaboral>) this.traerTodosPorConsultaSPQuery("ConsultaJovenFormacionLaboral", valores);
	}
	
	@Override
	public void insertar(Capacitacion capacitacion) throws ReinaException {
		try {
			this.guardar(capacitacion);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}

	@Override
	public void actualizar(Capacitacion capacitacion) throws ReinaException {
		try {
			this.modificar(capacitacion);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
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

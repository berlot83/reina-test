package snya.reina.datos.intervencion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.ReinaException;
import snya.reina.modelo.informe.TipoDeInforme;


@Repository
public class TipoDeInformeDAOImpl  extends HibernateDAOGenericoImpl<TipoDeInforme, Integer> implements TipoDeInformeDAO {

	@Override
	protected Class<TipoDeInforme> getEntityClass() {
		return TipoDeInforme.class;
	}
	
	public void insertar(TipoDeInforme tipo) throws ReinaException {
		try {
			this.guardar(tipo);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}
	
	public void actualizar(TipoDeInforme tipo) throws ReinaException {
		try {
			this.modificar(tipo);	
		} catch (Exception e) {
			throw new ReinaException("Error al actualizar los datos." + e.getMessage());
		}
	}	

	public List<TipoDeInforme> traerTodosActivos() {
		String query = "select t from TipoDeInforme t where t.estaActivo = 1 order by t.grupo.nombre, t.nombre "; 
		
		return (List<TipoDeInforme>) this.traerTodosPorConsulta(query);
	}
	
	public List<TipoDeInforme> traerActivosPorGrupo(int idGrupo) {
		String query = "select t from TipoDeInforme t where t.estaActivo = 1 and t.grupo.id = " + idGrupo + " order by t.grupo.nombre, t.nombre "; 
		
		return (List<TipoDeInforme>) this.traerTodosPorConsulta(query);
	}
}

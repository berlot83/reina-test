package snya.reina.datos.salud;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.salud.TipoDeDiagnostico;

@Repository
public class TipoDeDiagnosticoDAOImpl extends
		HibernateDAOGenericoImpl<TipoDeDiagnostico, Integer> implements
		TipoDeDiagnosticoDAO {

	@Override
	protected Class<TipoDeDiagnostico> getEntityClass() {
		return TipoDeDiagnostico.class;
	}

	@Override
	public List<TipoDeDiagnostico> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
	
	@Override
	public List<TipoDeDiagnostico> traerTodosActivos(String diagnostico) {
		return this.traerTodosPorConsulta("select t from TipoDeDiagnostico t where estaActivo = 1 and t.nombre like \'%" + diagnostico + "%\'");
	}	
}

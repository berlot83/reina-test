package snya.reina.datos.intervencion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.intervencion.MotivoAprehension;

@Repository
public class MotivoAprehensionDAOImpl extends HibernateDAOGenericoImpl<MotivoAprehension, Integer> implements MotivoAprehensionDAO  {

	@Override
	protected Class<MotivoAprehension> getEntityClass() {
		return MotivoAprehension.class;
	}
	
	public List<MotivoAprehension> traerTodosActivos() {
		String query = "select t from MotivoAprehension"; 
		
		return (List<MotivoAprehension>) this.traerTodosPorConsulta(query);
	}
	
}

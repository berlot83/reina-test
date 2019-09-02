package snya.reina.datos.institucion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.ProgramaSimplificado;

@Repository
public class ProgramaSimplificadoDAOImpl extends HibernateDAOGenericoImpl<ProgramaSimplificado, Integer> implements ProgramaSimplificadoDAO {

	@Override
	protected Class<ProgramaSimplificado> getEntityClass() {
		return ProgramaSimplificado.class;
	}
		
	public ProgramaSimplificado traerPorIdSimple(Integer id) {
		List<ProgramaSimplificado> prgs = this.traerPorPropiedad(ProgramaSimplificado.class, "idPrograma", id);
		return (prgs.size() > 0) ? prgs.get(0) : null;
	}
	
	@Override
	public List<ProgramaSimplificado> traerTodosActivos() {
		return this.traerTodos(ProgramaSimplificado.class);
		//return this.traerPorPropiedad("estaActivo", true);
	}
}


package snya.reina.datos.institucion;

import java.util.Collection;
import java.util.List;



import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.recurso.ComponeRecurso;

public interface ProgramaSimplificadoDAO {

	ProgramaSimplificado traerPorIdSimple(Integer id);
	
	List<ProgramaSimplificado> traerTodos();

	Collection<? extends ComponeRecurso> traerTodosActivos();
}

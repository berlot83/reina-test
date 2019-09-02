package snya.reina.datos.institucion;


import java.util.Collection;
import java.util.List;

import snya.reina.modelo.institucion.UnidadPenitenciaria;
import snya.reina.modelo.recurso.ComponeRecurso;

public interface ServicioPenitenciarioDAO {

	UnidadPenitenciaria traerPorId(Integer id);
	
	List<UnidadPenitenciaria> traerTodos();

	Collection<? extends ComponeRecurso> traerTodosActivos();
}
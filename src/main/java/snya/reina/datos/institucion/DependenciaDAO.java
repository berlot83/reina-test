package snya.reina.datos.institucion;

import snya.reina.modelo.institucion.Dependencia;

public interface DependenciaDAO {

	Dependencia traerPorId(Integer id);
	
	java.util.List<Dependencia> traerTodos();
}

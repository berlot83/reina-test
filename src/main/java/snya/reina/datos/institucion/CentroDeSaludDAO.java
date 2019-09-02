package snya.reina.datos.institucion;

import java.util.List;

import snya.reina.modelo.institucion.CentroDeSalud;

public interface CentroDeSaludDAO {

	CentroDeSalud traerPorId(Integer id);
	
	List<CentroDeSalud> traerTodos();

	List<CentroDeSalud> traerTodosActivos();
}

package snya.reina.datos.institucion;

import java.util.List;

import snya.reina.modelo.institucion.Fiscalia;

public interface FiscaliaDAO {

	Fiscalia traerPorId(Integer id);
	
	java.util.List<Fiscalia> traerTodos();

	List<Fiscalia> traerTodosActivos();
}

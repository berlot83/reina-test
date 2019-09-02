package snya.reina.datos.institucion;

import java.util.List;

import snya.reina.modelo.institucion.ContactoInstitucion;
import snya.reina.modelo.institucion.Defensoria;

public interface DefensoriaDAO {

	Defensoria traerPorId(Integer id);
	
	java.util.List<Defensoria> traerTodos();

	List<Defensoria> traerTodosActivos();

	void inicializarAlPeresozo(List<ContactoInstitucion> contactos);
}
package snya.reina.datos.salud;

import java.util.List;

import snya.reina.modelo.beneficio.EstadoObraSocial;

public interface EstadoObraSocialDAO {

	EstadoObraSocial traerPorId(Integer id);

	List<EstadoObraSocial> traerTodos();
}

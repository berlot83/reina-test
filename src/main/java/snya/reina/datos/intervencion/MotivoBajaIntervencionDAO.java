package snya.reina.datos.intervencion;

import java.util.List;

import snya.reina.modelo.intervencion.MotivoBajaIntervencion;

public interface MotivoBajaIntervencionDAO {

	List<MotivoBajaIntervencion> traerTodos();

	MotivoBajaIntervencion traerPorId(Integer idMotivoBaja);
}

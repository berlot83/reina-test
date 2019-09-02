package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.modelo.educacion.TipoDeCapacitacion;

public interface TipoDeCapacitacionDAO {

	TipoDeCapacitacion traerPorId(Integer idTipo);

	List<TipoDeCapacitacion> traerTodosActivos();

}

package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.modelo.educacion.TematicaDeCapacitacion;

public interface TematicaDeCapacitacionDAO {

	TematicaDeCapacitacion traerPorId(Integer idTematica);

	List<TematicaDeCapacitacion> traerTodosActivos();

}

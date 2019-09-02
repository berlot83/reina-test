package snya.reina.datos.proceso;

import java.util.List;

import snya.reina.modelo.proceso.TipoDeNotaProcesoPenal;

public interface TipoDeNotaProcesoPenalDAO {

	TipoDeNotaProcesoPenal traerPorId(Integer id);

	List<TipoDeNotaProcesoPenal> traerTodosActivos();
}

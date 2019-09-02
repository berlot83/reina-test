package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.modelo.educacion.TipoDeEstadoCapacitacionJoven;

public interface TipoDeEstadoCapacitacionJovenDAO {

	TipoDeEstadoCapacitacionJoven traerPorId(Integer id);

	List<TipoDeEstadoCapacitacionJoven> traerTodos();

}

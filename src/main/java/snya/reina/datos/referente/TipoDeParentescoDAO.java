package snya.reina.datos.referente;

import java.util.List;

import snya.reina.modelo.referente.TipoDeParentesco;

public interface TipoDeParentescoDAO {

	TipoDeParentesco traerPorId(Integer id);

	List<TipoDeParentesco> traerTodos();
}

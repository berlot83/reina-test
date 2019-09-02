package snya.reina.datos.institucion;


import java.util.List;

import snya.reina.modelo.institucion.OrganoJudicial;

public interface OrganoJudicialDAO {

	OrganoJudicial traerPorId(Integer id);
	
	java.util.List<OrganoJudicial> traerTodos();

	List<OrganoJudicial> traerTodosActivos();
}
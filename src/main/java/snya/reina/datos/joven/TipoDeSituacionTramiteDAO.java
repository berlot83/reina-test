package snya.reina.datos.joven;

import java.util.List;

import snya.reina.modelo.joven.TipoDeSituacionTramite;

public interface TipoDeSituacionTramiteDAO {

	List<TipoDeSituacionTramite> traerTodos();

	TipoDeSituacionTramite traerPorId(Integer idSituacionDocumentacion);
}

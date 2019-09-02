package snya.reina.datos.institucion;


import snya.reina.modelo.institucion.CentroDeReferencia;

public interface CentroDeReferenciaDAO {

	CentroDeReferencia traerPorId(Integer id);
	
	java.util.List<CentroDeReferencia> traerTodos();
}

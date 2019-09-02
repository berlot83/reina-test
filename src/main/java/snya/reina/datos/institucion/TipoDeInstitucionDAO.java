package snya.reina.datos.institucion;

import snya.reina.modelo.institucion.TipoDeInstitucion;

public interface TipoDeInstitucionDAO {

	TipoDeInstitucion traerPorId(Integer id);
	
	java.util.List<TipoDeInstitucion> traerTodos();
}


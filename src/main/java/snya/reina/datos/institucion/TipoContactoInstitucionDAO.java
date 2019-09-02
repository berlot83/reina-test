package snya.reina.datos.institucion;

import java.util.List;

import snya.reina.modelo.institucion.TipoContactoInstitucion;

public interface TipoContactoInstitucionDAO {

	TipoContactoInstitucion traerPorId(Integer id);

	List<TipoContactoInstitucion> traerTodos();

}

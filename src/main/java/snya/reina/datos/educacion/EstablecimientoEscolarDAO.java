package snya.reina.datos.educacion;

import snya.reina.modelo.educacion.EstablecimientoEscolar;

public interface EstablecimientoEscolarDAO {

	EstablecimientoEscolar traerPorId(Integer id);
	
	java.util.List<EstablecimientoEscolar> traerTodos();
}

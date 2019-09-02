package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.modelo.educacion.AnioEscolar;

public interface AnioEscolarDAO {

	AnioEscolar traerPorId(Integer id);
	
	java.util.List<AnioEscolar> traerPorNivel(int id);

	List<AnioEscolar> traerTodosActivas();
}

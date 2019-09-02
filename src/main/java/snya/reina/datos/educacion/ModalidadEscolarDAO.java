package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.modelo.educacion.ModalidadEscolar;

public interface ModalidadEscolarDAO {

	ModalidadEscolar traerPorId(Integer id);
	
	java.util.List<ModalidadEscolar> traerTodos();

	List<ModalidadEscolar> traerTodasActivas();
}

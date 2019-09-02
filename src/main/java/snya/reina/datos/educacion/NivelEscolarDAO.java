package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.modelo.educacion.NivelEscolar;

public interface NivelEscolarDAO {

	NivelEscolar traerPorId(Integer id);
	
	java.util.List<NivelEscolar> traerPorModalidad(int id);

	List<NivelEscolar> traerTodasActivas();
}

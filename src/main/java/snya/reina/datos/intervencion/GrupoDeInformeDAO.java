package snya.reina.datos.intervencion;

import java.util.List;

import snya.reina.ReinaException;
import snya.reina.modelo.informe.GrupoDeInforme;

public interface GrupoDeInformeDAO {

	void insertar(GrupoDeInforme grupo) throws ReinaException;

	void actualizar(GrupoDeInforme grupo);
	
	GrupoDeInforme traerPorId(Integer id);

	List<GrupoDeInforme> traerTodosActivos();
}

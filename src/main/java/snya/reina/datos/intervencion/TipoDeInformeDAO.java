package snya.reina.datos.intervencion;

import java.util.List;

import snya.reina.ReinaException;
import snya.reina.modelo.informe.TipoDeInforme;

public interface TipoDeInformeDAO {
	
	void insertar(TipoDeInforme tipo) throws ReinaException;

	void actualizar(TipoDeInforme tipo) throws ReinaException;
	
	TipoDeInforme traerPorId(Integer tipoInforme);

	List<TipoDeInforme> traerActivosPorGrupo(int idGrupo);
	
	List<TipoDeInforme> traerTodos();

	List<TipoDeInforme> traerTodosActivos();
}

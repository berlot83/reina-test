package snya.reina.datos.educacion;

import java.util.List;

import snya.reina.ReinaException;
import snya.reina.modelo.educacion.Capacitacion;
import snya.reina.modelo.educacion.Dictado;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;

public interface CapacitacionDAO {

	Capacitacion traerPorId(Integer idCapacitacion, boolean esFormacionLaboral);

	List<Capacitacion> traerTodosActivos(boolean esFormacionLaboral);

	List<Capacitacion> traerTodosActivosEnAmbito(boolean esFormacionLaboral, Integer[] idAmbitos);
	
	List<Capacitacion> traerTodos(boolean esFormacionLaboral);

	void insertar(Capacitacion capacitacion) throws ReinaException;

	void actualizar(Capacitacion capacitacion) throws ReinaException;
	
	List<Dictado> traerDictadosTodosActivosPorId(Integer id, Integer[] idAmbitos);

	List<EstadisticaFormacionLaboral> listaFormacionLaboralCursante(Integer idFormacion, Integer idDictado, Integer idEstado);
}

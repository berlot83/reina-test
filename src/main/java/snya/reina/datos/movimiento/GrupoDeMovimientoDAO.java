package snya.reina.datos.movimiento;

import java.util.List;

import snya.reina.modelo.movimiento.GrupoDeMovimiento;

public interface GrupoDeMovimientoDAO {

	GrupoDeMovimiento traerPorId(Integer id);

	List<GrupoDeMovimiento> traerTodosActivos();
}

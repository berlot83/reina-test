package snya.reina.datos.movimiento;

import java.util.List;

import snya.reina.modelo.movimiento.TipoDeMovimiento;

public interface TipoDeMovimientoDAO {

	TipoDeMovimiento traerPorId(Integer id);

	List<TipoDeMovimiento> traerTodosActivos();
}

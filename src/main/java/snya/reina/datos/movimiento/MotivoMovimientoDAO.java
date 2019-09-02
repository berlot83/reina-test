package snya.reina.datos.movimiento;

import java.util.List;

import snya.reina.modelo.movimiento.TipoDeMotivoMovimiento;

public interface MotivoMovimientoDAO {

	TipoDeMotivoMovimiento traerPorId(Integer idMotivoMovimiento);

	List<TipoDeMotivoMovimiento> traerTodos();
}

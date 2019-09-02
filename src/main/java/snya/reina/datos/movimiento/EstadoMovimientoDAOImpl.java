package snya.reina.datos.movimiento;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.movimiento.EstadoMovimiento;

@Repository
public class EstadoMovimientoDAOImpl extends HibernateDAOGenericoImpl<EstadoMovimiento, Integer> implements EstadoMovimientoDAO  {

	@Override
	protected Class<EstadoMovimiento> getEntityClass() {
		return EstadoMovimiento.class;
	}
}

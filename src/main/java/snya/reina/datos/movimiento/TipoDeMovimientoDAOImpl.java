package snya.reina.datos.movimiento;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.movimiento.TipoDeMovimiento;


@Repository
public class TipoDeMovimientoDAOImpl extends HibernateDAOGenericoImpl<TipoDeMovimiento, Integer> implements TipoDeMovimientoDAO {

	@Override
	protected Class<TipoDeMovimiento> getEntityClass() {
		return TipoDeMovimiento.class;
	}

	@Override
	public List<TipoDeMovimiento> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}
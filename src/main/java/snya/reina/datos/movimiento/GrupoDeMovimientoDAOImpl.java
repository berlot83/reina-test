package snya.reina.datos.movimiento;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.movimiento.GrupoDeMovimiento;


@Repository
public class GrupoDeMovimientoDAOImpl extends HibernateDAOGenericoImpl<GrupoDeMovimiento, Integer> implements GrupoDeMovimientoDAO {

	@Override
	protected Class<GrupoDeMovimiento> getEntityClass() {
		return GrupoDeMovimiento.class;
	}

	@Override
	public List<GrupoDeMovimiento> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}
package snya.reina.datos.movimiento;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.movimiento.TipoDeMotivoMovimiento;

@Repository
public class MotivoMovimientoDAOImpl extends HibernateDAOGenericoImpl<TipoDeMotivoMovimiento, Integer> implements MotivoMovimientoDAO  {

	@Override
	protected Class<TipoDeMotivoMovimiento> getEntityClass() {
		return TipoDeMotivoMovimiento.class;
	}
}

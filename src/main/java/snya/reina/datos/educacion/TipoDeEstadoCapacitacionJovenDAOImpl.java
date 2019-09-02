package snya.reina.datos.educacion;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.TipoDeEstadoCapacitacionJoven;

@Repository
public class TipoDeEstadoCapacitacionJovenDAOImpl extends HibernateDAOGenericoImpl<TipoDeEstadoCapacitacionJoven, Integer> implements TipoDeEstadoCapacitacionJovenDAO {
	
	
	@Override
	protected Class<TipoDeEstadoCapacitacionJoven> getEntityClass() {
		return TipoDeEstadoCapacitacionJoven.class;
	}
}

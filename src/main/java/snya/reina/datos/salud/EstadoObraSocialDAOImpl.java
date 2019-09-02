package snya.reina.datos.salud;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.beneficio.EstadoObraSocial;

@Repository
public class EstadoObraSocialDAOImpl extends
		HibernateDAOGenericoImpl<EstadoObraSocial, Integer> implements
		EstadoObraSocialDAO {

	@Override
	protected Class<EstadoObraSocial> getEntityClass() {
		return EstadoObraSocial.class;
	}
}

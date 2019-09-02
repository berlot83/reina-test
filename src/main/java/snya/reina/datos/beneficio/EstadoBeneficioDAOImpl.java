package snya.reina.datos.beneficio;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.beneficio.EstadoBeneficio;

@Repository
public class EstadoBeneficioDAOImpl extends
		HibernateDAOGenericoImpl<EstadoBeneficio, Integer> implements
		EstadoBeneficioDAO {

	@Override
	protected Class<EstadoBeneficio> getEntityClass() {
		return EstadoBeneficio.class;
	}

}

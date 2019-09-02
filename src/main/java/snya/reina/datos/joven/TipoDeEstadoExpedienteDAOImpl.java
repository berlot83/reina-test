package snya.reina.datos.joven;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.joven.TipoDeEstadoExpediente;

@Repository
public class TipoDeEstadoExpedienteDAOImpl extends
		HibernateDAOGenericoImpl<TipoDeEstadoExpediente, Integer> implements
		TipoDeEstadoExpedienteDAO {

	@Override
	protected Class<TipoDeEstadoExpediente> getEntityClass() {
		return TipoDeEstadoExpediente.class;
	}
}

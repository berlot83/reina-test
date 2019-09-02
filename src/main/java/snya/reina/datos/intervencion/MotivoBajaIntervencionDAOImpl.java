package snya.reina.datos.intervencion;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.intervencion.MotivoBajaIntervencion;

@Repository
public class MotivoBajaIntervencionDAOImpl extends HibernateDAOGenericoImpl<MotivoBajaIntervencion, Integer> implements MotivoBajaIntervencionDAO  {

	@Override
	protected Class<MotivoBajaIntervencion> getEntityClass() {
		return MotivoBajaIntervencion.class;
	}
}

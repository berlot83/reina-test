package snya.reina.datos.intervencion;

import org.springframework.stereotype.Repository;
import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.intervencion.TipoDeIntervencion;


@Repository
public class TipoDeIntervencionDAOImpl  extends HibernateDAOGenericoImpl<TipoDeIntervencion, Integer> implements TipoDeIntervencionDAO {

	@Override
	protected Class<TipoDeIntervencion> getEntityClass() {
		return TipoDeIntervencion.class;
	}

}

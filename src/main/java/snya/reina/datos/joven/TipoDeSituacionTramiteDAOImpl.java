package snya.reina.datos.joven;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.joven.TipoDeSituacionTramite;

@Repository
public class TipoDeSituacionTramiteDAOImpl extends HibernateDAOGenericoImpl<TipoDeSituacionTramite, Integer> implements TipoDeSituacionTramiteDAO {

	@Override
	protected Class<TipoDeSituacionTramite> getEntityClass() {
		return TipoDeSituacionTramite.class;
	}
}
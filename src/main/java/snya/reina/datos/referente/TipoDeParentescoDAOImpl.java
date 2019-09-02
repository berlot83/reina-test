package snya.reina.datos.referente;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.referente.TipoDeParentesco;

@Repository
public class TipoDeParentescoDAOImpl extends HibernateDAOGenericoImpl<TipoDeParentesco, Integer> implements TipoDeParentescoDAO {

	@Override
	protected Class<TipoDeParentesco> getEntityClass() {
		return TipoDeParentesco.class;
	}
}
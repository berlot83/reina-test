package snya.reina.datos.institucion;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.TipoDeInstitucion;

@Repository
public class TipoDeInstitucionDAOImpl extends HibernateDAOGenericoImpl<TipoDeInstitucion, Integer> implements TipoDeInstitucionDAO {
	
	@Override
	protected Class<TipoDeInstitucion> getEntityClass() {
		return TipoDeInstitucion.class;
	}

	@Override
	public TipoDeInstitucion traerPorId(Integer id) {
		return super.traerPorId(id);
	}
}
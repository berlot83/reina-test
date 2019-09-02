package snya.reina.datos.institucion;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.TipoContactoInstitucion;

@Repository
public class TipoContactoInstitucionDAOImpl extends
		HibernateDAOGenericoImpl<TipoContactoInstitucion, Integer> implements
		TipoContactoInstitucionDAO {

	@Override
	protected Class<TipoContactoInstitucion> getEntityClass() {
		return TipoContactoInstitucion.class;
	}

}

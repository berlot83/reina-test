package snya.reina.datos.institucion;

import org.springframework.stereotype.Repository;
import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.CentroDeReferencia;

@Repository
public class CentroDeReferenciaDAOImpl extends HibernateDAOGenericoImpl<CentroDeReferencia, Integer> implements CentroDeReferenciaDAO {
	
	@Override
	protected Class<CentroDeReferencia> getEntityClass() {
		return CentroDeReferencia.class;
	}
}

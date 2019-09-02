package snya.reina.datos.institucion;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.Dependencia;

@Repository
public class DependenciaDAOImpl extends HibernateDAOGenericoImpl<Dependencia, Integer> implements DependenciaDAO {
	
	@Override
	protected Class<Dependencia> getEntityClass() {
		return Dependencia.class;
	}

	@Override
	public Dependencia traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}
}

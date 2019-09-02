package snya.reina.datos.educacion;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.EstablecimientoEscolar;

@Repository
public class EstablecimientoEscolarDAOImpl extends HibernateDAOGenericoImpl<EstablecimientoEscolar, Integer> implements EstablecimientoEscolarDAO {
	
	@Override
	protected Class<EstablecimientoEscolar> getEntityClass() {
		return EstablecimientoEscolar.class;
	}

	@Override
	public EstablecimientoEscolar traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}
}

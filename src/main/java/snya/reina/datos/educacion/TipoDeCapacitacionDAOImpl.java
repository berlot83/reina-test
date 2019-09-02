package snya.reina.datos.educacion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.TipoDeCapacitacion;

@Repository
public class TipoDeCapacitacionDAOImpl extends HibernateDAOGenericoImpl<TipoDeCapacitacion, Integer> implements TipoDeCapacitacionDAO {
	
	
	@Override
	protected Class<TipoDeCapacitacion> getEntityClass() {
		return TipoDeCapacitacion.class;
	}

	@Override
	public TipoDeCapacitacion traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}

	@Override
	public List<TipoDeCapacitacion> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

package snya.reina.datos.educacion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.TematicaDeCapacitacion;

@Repository
public class TematicaDeCapacitacionDAOImpl extends HibernateDAOGenericoImpl<TematicaDeCapacitacion, Integer> implements TematicaDeCapacitacionDAO {
	
	
	@Override
	protected Class<TematicaDeCapacitacion> getEntityClass() {
		return TematicaDeCapacitacion.class;
	}

	@Override
	public TematicaDeCapacitacion traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}

	@Override
	public List<TematicaDeCapacitacion> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

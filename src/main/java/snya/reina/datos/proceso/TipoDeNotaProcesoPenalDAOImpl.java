package snya.reina.datos.proceso;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.proceso.TipoDeNotaProcesoPenal;

@Repository
public class TipoDeNotaProcesoPenalDAOImpl extends HibernateDAOGenericoImpl<TipoDeNotaProcesoPenal, Integer> implements TipoDeNotaProcesoPenalDAO {

	@Override
	protected Class<TipoDeNotaProcesoPenal> getEntityClass() {
		return TipoDeNotaProcesoPenal.class;
	}

	@Override
	public List<TipoDeNotaProcesoPenal> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

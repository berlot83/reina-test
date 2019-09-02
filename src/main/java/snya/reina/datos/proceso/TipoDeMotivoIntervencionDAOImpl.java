package snya.reina.datos.proceso;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.proceso.TipoDeMotivoIntervencion;

@Repository
public class TipoDeMotivoIntervencionDAOImpl extends HibernateDAOGenericoImpl<TipoDeMotivoIntervencion, Integer> implements TipoDeMotivoIntervencionDAO {

	@Override
	protected Class<TipoDeMotivoIntervencion> getEntityClass() {
		return TipoDeMotivoIntervencion.class;
	}

	@Override
	public List<TipoDeMotivoIntervencion> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}
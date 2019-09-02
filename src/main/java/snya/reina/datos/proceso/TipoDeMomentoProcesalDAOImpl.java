package snya.reina.datos.proceso;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.proceso.TipoDeMomentoProcesal;

@Repository
public class TipoDeMomentoProcesalDAOImpl extends HibernateDAOGenericoImpl<TipoDeMomentoProcesal, Integer> implements TipoDeMomentoProcesalDAO {

	@Override
	protected Class<TipoDeMomentoProcesal> getEntityClass() {
		return TipoDeMomentoProcesal.class;
	}

	@Override
	public List<TipoDeMomentoProcesal> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

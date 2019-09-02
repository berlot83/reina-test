package snya.reina.datos.institucion;

import java.util.List;

import org.springframework.stereotype.Repository;
import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.CentroDeSalud;

@Repository
public class CentroDeSaludDAOImpl extends HibernateDAOGenericoImpl<CentroDeSalud, Integer> implements CentroDeSaludDAO {

	@Override
	protected Class<CentroDeSalud> getEntityClass() {
		return CentroDeSalud.class;
	}
	
	@Override
	public List<CentroDeSalud> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}
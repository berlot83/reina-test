package snya.reina.datos.institucion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.UnidadPenitenciaria;

@Repository
public class ServicioPenitenciarioDAOImpl extends HibernateDAOGenericoImpl<UnidadPenitenciaria, Integer> implements ServicioPenitenciarioDAO {

	@Override
	protected Class<UnidadPenitenciaria> getEntityClass() {
		return UnidadPenitenciaria.class;
	}
	
	@Override
	public List<UnidadPenitenciaria> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}
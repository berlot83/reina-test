package snya.reina.datos.institucion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.Fiscalia;

@Repository
public class FiscaliaDAOImpl extends HibernateDAOGenericoImpl<Fiscalia, Integer> implements FiscaliaDAO {
	
	@Override
	protected Class<Fiscalia> getEntityClass() {
		return Fiscalia.class;
	}

	@Override
	public Fiscalia traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}
	
	@Override
	public List<Fiscalia> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

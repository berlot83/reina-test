package snya.reina.datos.institucion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.institucion.ContactoInstitucion;
import snya.reina.modelo.institucion.Defensoria;

@Repository
public class DefensoriaDAOImpl extends HibernateDAOGenericoImpl<Defensoria, Integer> implements DefensoriaDAO {
	
	@Override
	protected Class<Defensoria> getEntityClass() {
		return Defensoria.class;
	}

	@Override
	public Defensoria traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}
	
	@Override
	public List<Defensoria> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}

	@Override
	public void inicializarAlPeresozo(List<ContactoInstitucion> contactos) {
		super.inicializarAlPeresozo(contactos);		
	}
}


package snya.reina.datos.joven;

import java.util.List;
import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.Persona;

@Repository
public class PersonaDAOImpl extends HibernateDAOGenericoImpl<Persona, Integer> implements PersonaDAO {

	@Override
	protected Class<Persona> getEntityClass() {
		return Persona.class;
	}

	@Override
	public List<Persona> traerPorNombre(String apellido, String nombre) {
		String query = "select p from Persona p where p.nombres like \"%" + nombre + "%\" and p.apellidos like \"%" + apellido + "%\" ";
		return this.traerTodosPorConsulta(1000, query);
	}

	@Override
	public List<Persona> traerPorNombre(String dato) {
		String query = "select p from Persona p where p.nombres like \"%" + dato + "%\" and p.apellidos like \"%" + dato + "%\" ";
		return this.traerTodosPorConsulta(1000, query);
	}

	@Override
	public List<Persona> traerPorNroDocumento(Integer nro) {
		String query = "select p from Persona p where p.documento like \"%" + nro + "%\" ";
		return this.traerTodosPorConsulta(1000, query);
	}
}
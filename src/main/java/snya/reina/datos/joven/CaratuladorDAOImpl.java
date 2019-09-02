package snya.reina.datos.joven;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.joven.Caratulador;

@Repository
public class CaratuladorDAOImpl extends HibernateDAOGenericoImpl<Caratulador, Integer>
		implements CaratuladorDAO {

	@Override
	protected Class<Caratulador> getEntityClass() {
		return Caratulador.class;
	}
	
	@Override
	public Caratulador traerPorCaracteristica(String caracteristica) {
		return this.traerPrimeroPorPropiedad("caracteristica", caracteristica);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Caratulador> traerTodosExternos() {
		List caratuladores = traerTodosPorConsulta("select c  from Caratulador c where c.caracteristica != 'SRPJ'");
				
		return (List<Caratulador>)caratuladores;
	}
}

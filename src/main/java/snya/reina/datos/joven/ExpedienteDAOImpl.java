package snya.reina.datos.joven;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.joven.Caratulador;
import snya.reina.modelo.joven.Expediente;

@Repository
public class ExpedienteDAOImpl extends HibernateDAOGenericoImpl<Expediente, Integer> implements ExpedienteDAO {

	@Override
	protected Class<Expediente> getEntityClass() {
		return Expediente.class;
	}

	@Override
	public boolean existeNumeroExpediente(Long numeroExpediente) {
		List<Expediente> expedientes = this.traerTodosPorConsulta("select e  from Expediente e where e.numero = " + numeroExpediente);		
		return (expedientes.size() > 0);
	}

	@Override
	public boolean existeNumeroExpedienteExterno(Caratulador caratulador, String numeroExpediente) {
		List<Expediente> expedientes = this.traerTodosPorConsulta("select e  from Expediente e where e.legajo like '%" + numeroExpediente  + "%'");		
		return (expedientes.size() > 0);
	}
}

package snya.reina.datos.habeas;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.ReinaException;
import snya.reina.modelo.habeas.Habeas;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.joven.Joven;

@Repository
public class HabeasDAOImpl extends HibernateDAOGenericoImpl<Habeas, Integer>
	implements HabeasDAO {

	@Override
	protected Class<Habeas> getEntityClass() {
		return Habeas.class;
	}
	
	@Override
	public void insertar(Habeas habeas) throws ReinaException {
		try {
			this.guardar(habeas);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}

	public void actualizar(Habeas habeas) throws ReinaException {
		try {
			this.modificar(habeas);	
		} catch (Exception e) {
			throw new ReinaException("Error al actualizar los datos." + e.getMessage());
		}
	}
	
	@Override
	public List<Habeas> traerHabeasActivos() {
		String query = "select h from Habeas h where h.fechaFin is null "; 
		
		return (List<Habeas>)this.traerTodosPorConsulta(query);
	}

	@Override
	public 	List<Habeas> traerHabeasHistoricos() {
		String query = "select h from Habeas h where h.fechaFin is not null "; 
		
		return (List<Habeas>)this.traerTodosPorConsulta(query);
	}
	
	@Override
	public boolean tieneHABEASActivosElDestinatario(Joven joven) {		
		return this.traerHabeasActivosPorDestinatario(joven).size() > 0;
	}
	
	@Override
	public boolean tieneHABEASActivosElDestinatario(InstitucionCumplimiento instituto) {		
		return this.traerHabeasActivosPorDestinatario(instituto).size() > 0;
	}
	
	@Override
	public List<Habeas> traerHabeasActivosPorDestinatario(Joven joven) {
		String query = "select h from Habeas h where h.fechaFin is not null and h.destinatario.joven.id = " + joven.getId(); 
		
		return (List<Habeas>)this.traerTodosPorConsulta(query);
		/*
		String[] propiedades = new String[2];
		Object[] valores = new Object[2];
		
		propiedades[0] = "destinatario.joven";
		valores[0] = joven;
		propiedades[1] = "fechaFin";
		valores[1] = null;
		
		return this.traerPorPropiedades(propiedades, valores);
		*/		
	}
	
	@Override
	public List<Habeas> traerHabeasActivosPorDestinatario(InstitucionCumplimiento instituto) {
		String query = "select h from Habeas h where h.fechaFin is not null and h.destinatario.institucion.id = " + instituto.getId(); 
		
		return (List<Habeas>)this.traerTodosPorConsulta(query);
		/*
		String[] propiedades = new String[2];
		Object[] valores = new Object[2];
		
		propiedades[0] = "destinatario.institucion";
		valores[0] = instituto;
		propiedades[1] = "fechaFin";
		valores[1] = null;
		
		return this.traerPorPropiedades(propiedades, valores);			
		*/
	}
	
	@Override
	public void inicializarAlPeresozo(Object proxy){
		super.inicializarAlPeresozo(proxy);
	}
}

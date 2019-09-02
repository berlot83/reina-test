package snya.reina.datos.beneficio;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.beneficio.EstadoBeneficio;
import snya.reina.modelo.beneficio.GrupoDeBeneficio;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;

@Repository
public class TipoDeBeneficioDAOImpl extends
		HibernateDAOGenericoImpl<TipoDeBeneficio, Integer> implements
		TipoDeBeneficioDAO {

	@Override
	protected Class<TipoDeBeneficio> getEntityClass() {
		return TipoDeBeneficio.class;
	}
	
	@Override
	public void actualizar(TipoDeBeneficio tipo) {
		this._guardar(tipo);
	}
	
	
	@Override
	public List<TipoDeBeneficio> traerPensionesActivas() {
		// <<declaracion e inicializacion de variables>>
        String query;

		query = "select t from TipoDeBeneficio t where t.estaActivo = 1 and t.grupo.id = " + GrupoDeBeneficio.ID_BENEFICIO_PENSION;
        
		// <<procesamiento>> <<resultado>>        
        return (List<TipoDeBeneficio>) this.traerTodosPorConsulta(query);
	}

	
	@Override
	public List<TipoDeBeneficio> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
	
	@Override
	public List<TipoDeBeneficio> traerTodosTarjetaActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

		query = "select t from TipoDeBeneficio t where t.estaActivo = 1 and t.entregaTarjeta = 1 ";
        
		// <<procesamiento>> <<resultado>>        
        return (List<TipoDeBeneficio>) this.traerTodosPorConsulta(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaBeneficio> listaBeneficiariosActivos(Integer idBeneficio, String idInstitucion) {	
		String consulta =  "ConsultaJovenBeneficio";
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", null);
		valores.put("idRecurso", (idInstitucion == null || idInstitucion.equals("") || idInstitucion.equals("null")) ? null : idInstitucion);
		valores.put("idBeneficio", idBeneficio);
		valores.put("idEstado", null);

		return (List<EstadisticaBeneficio>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaBeneficio> listaBeneficiosParaOperativo(Integer idBeneficio, String idInstitucion) {		
		String consulta =  "ConsultaJovenBeneficio";
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", null);
		if( idInstitucion != null && !idInstitucion.equals("") ) 
			valores.put("idRecurso", idInstitucion);
		else
			valores.put("idRecurso", "0");
		valores.put("idBeneficio", idBeneficio);
		valores.put("idEstado", EstadoBeneficio.ACTIVO);
		
		return (List<EstadisticaBeneficio>) this.traerTodosPorConsultaSPQuery(consulta, valores);	
	}
}

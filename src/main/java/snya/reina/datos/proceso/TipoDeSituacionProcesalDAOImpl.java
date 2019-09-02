package snya.reina.datos.proceso;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.proceso.TipoDeSituacionProcesal;

@Repository
public class TipoDeSituacionProcesalDAOImpl extends
		HibernateDAOGenericoImpl<TipoDeSituacionProcesal, Integer> implements
		TipoDeSituacionProcesalDAO {

	@Override
	protected Class<TipoDeSituacionProcesal> getEntityClass() {
		return TipoDeSituacionProcesal.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TipoDeSituacionProcesal calcularSituacionProcesal(Integer idTipoMomento, Integer idTipoMedida) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("pIdTipoDeMomentoProcesal", idTipoMomento);
		valores.put("pIdTipoDeMedidaProcesal", idTipoMedida);
		
		List<TipoDeSituacionProcesal> tipos = (List<TipoDeSituacionProcesal>) this.traerTodosPorConsultaSPQuery("ConsultaTipoDeSituacionProcesal", valores);
		return (tipos.size() > 0) ? tipos.get(0) : null;
	}
}

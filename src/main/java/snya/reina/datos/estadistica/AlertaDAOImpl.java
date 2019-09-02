package snya.reina.datos.estadistica;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.MedidaImpuestaAlerta;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;

@Repository
public class AlertaDAOImpl extends HibernateDAOGenericoImpl<MedidaImpuestaAlerta, Integer>
		implements AlertaDAO {

	@Override
	protected Class<MedidaImpuestaAlerta> getEntityClass() {
		return MedidaImpuestaAlerta.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MedidaImpuestaAlerta> listaAletaVencia_y_AVencer(int dias, Integer[] tipos, Integer[] recursos, String propiedad, String orden) {
		List<MedidaImpuestaAlerta> medidas;
		
		String idTipos = (tipos.length <= 0)  ? "" : armarIds(tipos);
		
		if (recursos.length <= 0) {			
			HashMap<String, Object> valores = new HashMap<String, Object>();
			valores.put("dias",dias);
			valores.put("tipos", idTipos);
			valores.put("recursos", "");
			valores.put("propiedad",propiedad );
			valores.put("orden",orden );
			
			medidas = (List<MedidaImpuestaAlerta>) this.traerTodosPorConsultaSPQuery("ConsultaAlertaMedida", valores);
		} else {
			String ids = armarIds(recursos);
			HashMap<String, Object> valores = new HashMap<String, Object>();
			valores.put("dias",dias);
			valores.put("tipos", idTipos);
			valores.put("recursos", ids);
			valores.put("propiedad",propiedad );
			valores.put("orden",orden );
			
			medidas = (List<MedidaImpuestaAlerta>) this.traerTodosPorConsultaSPQuery("ConsultaAlertaMedida", valores);
		}
                
        return medidas;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaFormacionLaboral> listaAlertaFormacionLaboralInterrumpida(String idTipoRecurso, String idRecurso, Integer idFormacion) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", idTipoRecurso);
		valores.put("idRecurso", idRecurso);
		valores.put("idFormacion", idFormacion);
				
		return (List<EstadisticaFormacionLaboral>) this.traerTodosPorConsultaSPQuery("ConsultaAlertaJovenFormacionLaboralInterrumpida", valores);
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaFormacionLaboral> listaAlertaFormacionLaboralCertificacion(String idTipoRecurso, String idRecurso, Integer idFormacion) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", idTipoRecurso);
		valores.put("idRecurso", idRecurso);
		valores.put("idFormacion", idFormacion);
				
		return (List<EstadisticaFormacionLaboral>) this.traerTodosPorConsultaSPQuery("ConsultaAlertaJovenFormacionLaboralCertificacion", valores);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaBeneficio> listaAlertaBeneficioTarjeta(String idTipoRecurso, String idRecurso, Integer idBeneficio) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", idTipoRecurso);
		valores.put("idRecurso", idRecurso);
		valores.put("idBeneficio", idBeneficio);
		
		return (List<EstadisticaBeneficio>) this.traerTodosPorConsultaSPQuery("ConsultaAlertaJovenBeneficioTarjeta", valores);		
	}
	
	
	
	private String armarIds(Integer[] recursos) {
		String ids = "";
		for (int i = 0; i < recursos.length; i++) {
			ids += recursos[i] + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		return ids;
	}

}

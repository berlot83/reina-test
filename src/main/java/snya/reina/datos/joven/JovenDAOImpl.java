package snya.reina.datos.joven;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.ReinaException;
import snya.reina.modelo.AsociacionAccionProcesos;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.JovenEscolaridad;
import snya.reina.modelo.JovenParteIntervencionPenal;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.JovenSimplificadoCAD;
import snya.reina.modelo.JovenSituacionIrregular;
import snya.reina.modelo.JovenTratamiento;
import snya.reina.modelo.MedidaEnProcesoDelJoven;
import snya.reina.modelo.MovimientoSimplificado;
import snya.reina.modelo.beneficio.BeneficioDelJoven;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.educacion.Escolaridad;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.estadistica.EstadisticaJovenInformeIntervencionPenal;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.utilidades.Parametro;
import snya.reina.utilidades.busqueda.joven.BusquedaJoven;

@Repository
public class JovenDAOImpl extends HibernateDAOGenericoImpl<Joven, Integer>
		implements JovenDAO {
	
	@Override
	protected Class<Joven> getEntityClass() {
		return Joven.class;
	}

	@Override
	public void insertar(Joven joven) throws ReinaException {
		try {
			this.guardar(joven);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}
	
	public void actualizar(Joven joven) throws ReinaException {
		try {
			this.modificar(joven);	
		} catch (Exception e) {
			throw new ReinaException("Error al actualizar los datos." + e.getMessage());
		}
	}

	@Override
	public void guardarEscolaridad(Escolaridad escolaridad){
		this._guardar(escolaridad);
	}

	@Override
	public void guardarInforme(Informe informe){
		this._guardar(informe);
	}
	
	public void actualizarInforme(Informe informe){
		this._modificar(informe);
	}
		
	public void guardarIntervencion(Intervencion intervencion){
		this._guardar(intervencion);
	}
	
	public boolean existeJovenConBeneficioYNumero(BeneficioDelJoven beneficio, TipoDeBeneficio tipo, String numero) {		
		String query;
		
		if(beneficio != null) {
			query = "select b from BeneficioDelJoven b where b.id != " + beneficio.getId() + " and tipo.id = " + tipo.getId();
			query += (numero != null && !numero.equals("")) ? " and identificador = '" + numero + "' " : "";
		} else {
			query = "select b from BeneficioDelJoven b where tipo.id = " + tipo.getId();
			query += (numero != null && !numero.equals("")) ? " and identificador = '" + numero + "' " : "";
		}
		
		// <<procesamiento>> <<resultado>>
        return this.traerTodosPorConsulta(query).size() > 0;
	}
	
	
	@Override
	public Joven traerPorId(Integer id) {	
		return this.traerPorId(this.getEntityClass(), id);
	}
	
	@Override
	public Joven traerPorIdCapacitacion(Integer idCapacitacion) {		
		String query = "select j from Joven j inner join j.capacitaciones c where c.id = " + idCapacitacion;
				
        List<Joven> jovenes = this.traerTodosPorConsulta(query);
        return (jovenes.size() > 0) ? jovenes.get(0): null;
	}
	
	@Override
	public Joven traerPorIdBeneficio(Integer idBeneficio) {
		String query = "select j from Joven j inner join j.beneficios b where b.id = " + idBeneficio;
		
        List<Joven> jovenes = this.traerTodosPorConsulta(query);
        return (jovenes.size() > 0) ? jovenes.get(0): null;		
	}
	
	@SuppressWarnings("unchecked")
	public List<JovenSimplificado> listaJovenesSinExpediente(Integer[] tipos, Integer[] recursos, String propiedad, String orden) {
		String ids;
		if (recursos.length <= 0) {
			ids = null;
		} else {
			ids = armarIds(recursos);
		}	
		String idTipos;
		if (tipos.length <= 0) {
			idTipos = null;
		} else {
			idTipos = armarIds(tipos);
		}
				
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("tipos", idTipos);
		valores.put("recursos", ids);
		valores.put("propiedad",propiedad );
		valores.put("orden",orden );
		
		return (List<JovenSimplificado>) this.traerTodosPorConsultaSPQuery("ConsultaJovenSinExpediente", valores);	
	}
	
	@SuppressWarnings("unchecked")
	public List<JovenSituacionIrregular> listaJovenesEnSituacionIrregular(Integer[] tipos, Integer[] recursos, String propiedad, String orden) {
		String ids;
		if (recursos.length <= 0) {
			ids = null;
		} else {
			ids = armarIds(recursos);
		}	
		String idTipos;
		if (tipos.length <= 0) {
			idTipos = null;
		} else {
			idTipos = armarIds(tipos);
		}
		
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("tipos", idTipos);
		valores.put("recursos", ids);
		valores.put("propiedad",propiedad );
		valores.put("orden",orden );
		
		return (List<JovenSituacionIrregular>) this.traerTodosPorConsultaSPQuery("ConsultaJovenSituacionIrregular", valores);
	}
	
	public List<JovenSimplificado> listaJovenesPresentes(boolean reporte, Integer[] tipos, Integer[] recursos, String campo, String orden) {
		String idTipos = (tipos.length <= 0) ? null : armarIds(tipos);
		String ids;
		if (recursos.length <= 0) {
			ids = null;
		} else {
			ids = armarIds(recursos);
		}

		return traerJovenPorCombinado(reporte, idTipos, ids, null, null, null, null,null, null, null, null, null, null, null, null, null, campo, orden);
	}
	
	//TODO ListaJovenesPresentesCAD
	public List<JovenSimplificadoCAD> listaJovenesPresentesCAD(boolean reporte, Integer[] tipos, Integer[] recursos, String campo, String orden) {
		String idTipos = (tipos.length <= 0) ? null : armarIds(tipos);
		String ids;
		if (recursos.length <= 0) {
			ids = null;
		} else {
			ids = armarIds(recursos);
		}

		return traerJovenCAD(reporte, idTipos, ids, null, null, null, null,null, null, null, null, null, null, null, null, null, campo, orden);
	}
	//TODO
	
	@SuppressWarnings("unchecked")
	public List<MovimientoSimplificado> listaJovenesConMovimientosPendientes(Integer[] tipos, Integer[] recursos, String campo, String orden) {
		String idTipos = (tipos.length <= 0) ? null : armarIds(tipos);
		String ids;
		if (recursos.length <= 0) {
			ids = null;
		} else {
			ids = armarIds(recursos);
		}
		
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("tipos", idTipos);
		valores.put("recursos", ids);
		valores.put("propiedad", campo);
		valores.put("orden", orden);
		
		return (List<MovimientoSimplificado>) this.traerTodosPorConsultaSPQuery("ConsultaPendientesJoven", valores);
	}

	@SuppressWarnings("unchecked")
	public List<JovenSimplificado> listaJoven(BusquedaJoven busqueda, String propiedad, String orden) throws ReinaException {
		boolean reporte = busqueda.isReporte();
		Parametro param = busqueda.calcularParametro(propiedad, orden);
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta = (reporte) ? "ConsultaReporteJoven" : "ConsultaBusquedaJoven";
		
		return (List<JovenSimplificado>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}
	
	//TODO
	
	@SuppressWarnings("unchecked")
	public List<JovenSimplificadoCAD> listaJovenCAD(BusquedaJoven busqueda, String propiedad, String orden) throws ReinaException {
		boolean reporte = busqueda.isReporte();
		Parametro param = busqueda.calcularParametro(propiedad, orden);
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta = (reporte) ? ""/*"ConsultaReporteJoven"*/ : "ConsultaBusquedaJovenCAD";
		
		return (List<JovenSimplificadoCAD>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}
	
	//TODO

	@SuppressWarnings("unchecked")
	public List<JovenEscolaridad> listaJovenEscolaridad(BusquedaJoven busqueda) throws ReinaException {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta = "ConsultaJovenEscolaridad"; 
		
		return (List<JovenEscolaridad>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}

	// TODO mejorar el pasaje de parametros
	@SuppressWarnings("unchecked")
	public List<JovenParteIntervencionPenal> listaJovenPartePresente(Integer[] tipos, Integer[] destinos, Integer situacion, Integer mes, Integer anio) throws ReinaException {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		String idsT = (tipos == null || (tipos != null && tipos.length == 0)) ? null : this.armarIds(tipos);
		String ids = (destinos == null || (destinos != null && destinos.length == 0)) ? null : this.armarIds(destinos);
		valores.put("idTipo", idsT);
		valores.put("idRecurso", ids);
		valores.put("mes", mes);
		valores.put("anio", anio);
		valores.put("situacion", situacion);
		String consulta = "ParteIntervencionPenal"; 
		
		return (List<JovenParteIntervencionPenal>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}

	@SuppressWarnings("unchecked")
	public List<EstadisticaJovenInformeIntervencionPenal> listaInformesIntervencion(Integer[] tipos, Integer[] destinos, Integer mes, Integer anio) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		String idsT = (tipos == null || (tipos != null && tipos.length == 0)) ? null : this.armarIds(tipos);
		String ids = (destinos == null || (destinos != null && destinos.length == 0)) ? null : this.armarIds(destinos);
		valores.put("idTipo", idsT);
		valores.put("idRecurso", ids);
		valores.put("mes", mes);
		valores.put("anio", anio);
		String consulta = "InformesIntervencionPenal"; 
		
		return (List<EstadisticaJovenInformeIntervencionPenal>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}
	
	@SuppressWarnings("unchecked")
	public List<JovenTratamiento> listaJovenTratamiento(BusquedaJoven busqueda) throws ReinaException {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta = "ConsultaJovenTratamiento"; 
		
		return (List<JovenTratamiento>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}

	@SuppressWarnings("unchecked")
	public List<EstadisticaFormacionLaboral> listaJovenFormacionLaboral(BusquedaJoven busqueda, Integer idFormacion, Integer idEstado) throws ReinaException {
		Parametro param = busqueda.calcularParametro();
		HashMap<String, Object> valores = param.convertirParametro();
		String consulta = "ConsultaJovenFormacionLaboral"; 
		
		return (List<EstadisticaFormacionLaboral>) this.traerTodosPorConsultaSPQuery(consulta, valores);		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AsociacionAccionProcesos> listaPosiblesParejasAnteRemplazoProceso(Integer idJoven, Integer idProceso) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idJoven", idJoven);
		valores.put("idProceso", idProceso);
		
		return (List<AsociacionAccionProcesos>) this.traerTodosPorConsultaSPQuery("AsociacionProcesos", valores);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MedidaEnProcesoDelJoven> listaMedidasDelJoven(Integer idJoven, Integer idProceso, Date fecha) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idJoven", idJoven);
		valores.put("fecha", Calendario.formatearFechaMySql(fecha));
		valores.put("idProceso", idProceso);
		
		return (List<MedidaEnProcesoDelJoven>) this.traerTodosPorConsultaSPQuery("MedidasDelJoven", valores);
	}
	
	@SuppressWarnings("unchecked")
	private List<JovenSimplificado> traerJovenPorCombinado(boolean reporte, String idTipos, String ids, 
			String apellidos, String nombre, String nombreCompleto, Integer documento,String huella, String expediente,
			String nroJudicial, Integer dptoJudicial, Integer idOrganoJudicial, Integer idDefensoria, Integer idFiscalia, Integer nroPaquete, Date fechaPaquete,
			String propiedad, String orden) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", idTipos);
		valores.put("idRecurso", ids);
		valores.put("apellidos", apellidos);		
		valores.put("nombres", nombre);
		valores.put("nombreCompleto", nombreCompleto);
		valores.put("documento", documento);
		valores.put("huella", huella);
		valores.put("expediente", expediente);
		valores.put("nroJudicial", nroJudicial);
		valores.put("dptoJudicial", dptoJudicial);
		valores.put("idOrganoJudicial", idOrganoJudicial);
		valores.put("idDefensoria", idDefensoria);		
		valores.put("idFiscalia", idFiscalia);
		valores.put("nroPaquete", nroPaquete);
		valores.put("fechaPaquete", fechaPaquete);
		valores.put("propiedad", propiedad);
		valores.put("orden", orden);
		
		return (List<JovenSimplificado>) this.traerTodosPorConsultaSPQuery((reporte) ? "ConsultaReporteJoven" : "ConsultaBusquedaJoven", valores);		
	}
	//TODO TraerJovenCAD
	
	@SuppressWarnings("unchecked")
	private List<JovenSimplificadoCAD> traerJovenCAD(boolean reporte, String idTipos, String ids, 
			String apellidos, String nombre, String nombreCompleto, Integer documento,String huella, String expediente,
			String nroJudicial, Integer dptoJudicial, Integer idOrganoJudicial, Integer idDefensoria, Integer idFiscalia, Integer nroPaquete, Date fechaPaquete,
			String propiedad, String orden) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		valores.put("idTipo", idTipos);
		valores.put("idRecurso", ids);
		valores.put("apellidos", apellidos);		
		valores.put("nombres", nombre);
		valores.put("nombreCompleto", nombreCompleto);
		valores.put("documento", documento);
		valores.put("huella", huella);
		valores.put("expediente", expediente);
		valores.put("nroJudicial", nroJudicial);
		valores.put("dptoJudicial", dptoJudicial);
		valores.put("idOrganoJudicial", idOrganoJudicial);
		valores.put("idDefensoria", idDefensoria);		
		valores.put("idFiscalia", idFiscalia);
		valores.put("nroPaquete", nroPaquete);
		valores.put("fechaPaquete", fechaPaquete);
		valores.put("propiedad", propiedad);
		valores.put("orden", orden);
		
		return (List<JovenSimplificadoCAD>) this.traerTodosPorConsultaSPQuery((reporte) ? "ConsultaReporteJoven" : "ConsultaBusquedaJovenCAD", valores);		
	}
	
	//TODO
	private String armarIds(Integer[] recursos) {
		String ids = "";
		for (int i = 0; i < recursos.length; i++) {
			ids += recursos[i] + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		return ids;
	}
}

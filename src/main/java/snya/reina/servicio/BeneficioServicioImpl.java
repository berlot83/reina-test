package snya.reina.servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.ObraSocialDAO;
import snya.general.datos.TipoDeDiscapacidadDAO;
import snya.general.modelo.ObraSocial;
import snya.general.modelo.TipoDeDiscapacidad;
import snya.reina.ReinaException;
import snya.reina.datos.beneficio.EstadoBeneficioDAO;
import snya.reina.datos.beneficio.TipoDeBeneficioDAO;
import snya.reina.datos.estadistica.AlertaDAO;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.salud.EstadoObraSocialDAO;
import snya.reina.modelo.beneficio.EstadoBeneficio;
import snya.reina.modelo.beneficio.EstadoObraSocial;
import snya.reina.modelo.beneficio.Operativo;
import snya.reina.modelo.beneficio.BeneficioDelJoven;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.beneficio.GestorDeBeneficios;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;

@Service
public class BeneficioServicioImpl {

	@Autowired
	private TipoDeBeneficioDAO tipoDeBeneficioDAO;
	@Autowired
	private EstadoBeneficioDAO estadoBeneficioDAO;
	@Autowired	
	private ObraSocialDAO obraSocialDAO;
	@Autowired
	private EstadoObraSocialDAO estadoObraSocialDAO;	
	@Autowired
	private TipoDeDiscapacidadDAO tipoDeDiscapacidadDAO;	
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private GuiaDeRecursos recursero;
	@Autowired
	private AlertaDAO alertaDAO;
	@Autowired
	private RecursoREINADAO recursoDAO;
	
	//
	// JOVEN - BENEFICIO
	//
	@Transactional
	public void guardarOperativo(Integer idBeneficio, Date fFecha, String descripcion, String idInstitucion, String[] ids) throws ReinaException {
		TipoDeBeneficio tipo = tipoDeBeneficioDAO.traerPorId(idBeneficio);
		String[] idInsts = (idInstitucion == null || idInstitucion.equals("") || idInstitucion == "null") ? new String[0] : idInstitucion.split(",");
		List<Recurso> recursos = new ArrayList<Recurso>();
		for (String id : idInsts) {
			recursos.add( recursoDAO.traerPorId( new Integer(id.trim()) ) );
		}
		
		
		// Agrego operativo
		Operativo op = new Operativo(fFecha, tipo, descripcion, recursos);
		tipo.getOperativos().add(op);
		tipoDeBeneficioDAO.actualizar(tipo);
		
				
		// Actualizo el joven
		for (String id : ids) {
			Joven joven = jovenDAO.traerPorIdBeneficio(Integer.valueOf(id));
			BeneficioDelJoven beneficioDelJoven = this.traerBeneficio(joven, Integer.valueOf(id));
			
			beneficioDelJoven.setFechaEntregaTarjeta(fFecha);
			beneficioDelJoven.setOperativo(op);
			jovenDAO.actualizar(joven);
		}	
	}
	
	public TipoDeBeneficio traerPorId(Integer id) {
		return tipoDeBeneficioDAO.traerPorId(id);
	}
	
	public List<TipoDeBeneficio> traerBeneficiosTodosActivos() {
		List<TipoDeBeneficio> lista = tipoDeBeneficioDAO.traerTodosActivos();
		Collections.sort(lista);
		return lista;
	}
	
	public List<TipoDeBeneficio> traerBeneficiosConTarjetaTodosActivos() {
		List<TipoDeBeneficio> lista = tipoDeBeneficioDAO.traerTodosTarjetaActivos();
		Collections.sort(lista);
		return lista;
	}
	
	public List<TipoDeBeneficio> traerPensionesActivasTodas() {
		return tipoDeBeneficioDAO.traerPensionesActivas();
	}

	public List<Operativo> traerOperativosPorId(Integer id) {
		TipoDeBeneficio tipo = tipoDeBeneficioDAO.traerPorId(id);
		if (tipo != null) {
			for (Operativo operativo : tipo.getOperativos()) {
				for (Recurso recurso : operativo.getInstituciones()) {
					recurso.getComponente().getNombre();
				}
			}
		}
		
		return tipo.getOperativos();
	}
	
	public List<ObraSocial> traerObrasSocialesActivasTodas() {
		return obraSocialDAO.traerTodos();
	}

	public List<TipoDeDiscapacidad> traerDiscapacidadesActivosTodas() {
		return tipoDeDiscapacidadDAO.traerTodosActivos();
	}
	
	public List<EstadoBeneficio>  traerEstadosBeneficiosTodos() {
		return estadoBeneficioDAO.traerTodos();
	}
	
	public List<EstadoBeneficio> traerEstadosBeneficiosVigentes() {
		List<EstadoBeneficio> estados = new ArrayList<EstadoBeneficio>();
		
		estados.add( estadoBeneficioDAO.traerPorId(EstadoBeneficio.ACTIVO));
		estados.add( estadoBeneficioDAO.traerPorId(EstadoBeneficio.EN_TRAMITE));
		
		return estados;
	}
	
	public List<EstadisticaBeneficio> listaAlertaBeneficioTarjeta(String idTipoRecurso, String idRecurso, Integer idBeneficio) {
		return alertaDAO.listaAlertaBeneficioTarjeta(idTipoRecurso, idRecurso, idBeneficio);
	}

	
	//
	// JOVEN - BENEFICIO
	//
	@Transactional
	public void guardarBeneficioCompleto(Integer idJoven, 
			Integer idTipoPension, String numeroPension, Date fAltaBeneficio, String observacionBeneficio,
			Boolean tieneObraSocial, Integer idObraSocial, String numeroCarnet, Date fVencimientoCarnet, String observacionObraSocial,
			Boolean tieneDiscapacidad, Integer idTipoDiscapacidad, Integer porcentajeDiscapacidad,
			Boolean certificadoDiscapacidad, Date fDiscapacidad,
			String observacionDiscapacidad) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);		
		GestorDeBeneficios gestorBenef = new GestorDeBeneficios(jovenDAO, estadoBeneficioDAO);
		
		TipoDeBeneficio tipoDePension = tipoDeBeneficioDAO.traerPorId(idTipoPension);
		gestorBenef.agregarPension(numeroPension, fAltaBeneficio, observacionBeneficio, joven, tipoDePension);

		
		if(tieneObraSocial) {
			ObraSocial obraSocial = obraSocialDAO.traerPorId(idObraSocial);
			int idEstadoObraSocial = (fVencimientoCarnet != null && fVencimientoCarnet.before(new Date())) ? EstadoObraSocial.SUSPENDIDA : EstadoObraSocial.ACTIVA;
			EstadoObraSocial estado = estadoObraSocialDAO.traerPorId(idEstadoObraSocial);
			
			joven.agregarObraSocial(obraSocial, numeroCarnet, fVencimientoCarnet, observacionObraSocial, estado);
		} else {
			EstadoObraSocial estadoOS = estadoObraSocialDAO.traerPorId(EstadoObraSocial.SE_DESCONOCE);
			joven.indicarEstadoObraSocial(estadoOS, observacionObraSocial);
		}
		
		if(tieneDiscapacidad) {
			EstadoObraSocial estadoOS = estadoObraSocialDAO.traerPorId(EstadoObraSocial.SE_DESCONOCE);
			TipoDeDiscapacidad tipoDeDiscapacidad = tipoDeDiscapacidadDAO.traerPorId(idTipoDiscapacidad);
			joven.agregarDiscapacidad(tipoDeDiscapacidad, porcentajeDiscapacidad, certificadoDiscapacidad, fDiscapacidad, observacionDiscapacidad, estadoOS);
		}
				
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void guardarBeneficio(Integer idJoven, Integer idTipoBeneficio,
			String numero, Date fechaAltaBeneficio, String observacionBeneficio, Integer idEstadoBeneficio, Date fechaEntregaTarjeta) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		TipoDeBeneficio tipo = tipoDeBeneficioDAO.traerPorId(idTipoBeneficio);
		EstadoBeneficio estadoBeneficio = estadoBeneficioDAO.traerPorId(idEstadoBeneficio);
		
		GestorDeBeneficios gestorBenef = new GestorDeBeneficios(jovenDAO, estadoBeneficioDAO);
		gestorBenef.guardarBeneficio(joven, tipo, numero, fechaAltaBeneficio, observacionBeneficio, estadoBeneficio, fechaEntregaTarjeta);
		
		jovenDAO.actualizar(joven);
		
	}
	
	@Transactional
	public void modificarBeneficio(Integer idJoven, Integer idBeneficio,
			Integer idTipoBeneficio, String numero, Date fechaAltaBeneficio, Date fechaBajaBeneficio,
			String observacionBeneficio, Integer idEstadoBeneficio, Integer idSupervisor, Date fechaEntregaTarjeta) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		BeneficioDelJoven beneficio = this.traerBeneficio(joven, idBeneficio);
		TipoDeBeneficio tipo = tipoDeBeneficioDAO.traerPorId(idTipoBeneficio);
		EstadoBeneficio estadoBeneficio = estadoBeneficioDAO.traerPorId(idEstadoBeneficio);
		Institucion supervisor = (idSupervisor != null && idSupervisor != 0) ?  recursero.traerInstitucionPorId(idSupervisor) : null;
		
		GestorDeBeneficios gestorBenef = new GestorDeBeneficios(jovenDAO, estadoBeneficioDAO);
		gestorBenef.modificarBeneficio(joven, beneficio, tipo, numero, supervisor, estadoBeneficio, fechaAltaBeneficio, fechaBajaBeneficio,
				observacionBeneficio, fechaEntregaTarjeta);
		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void solicitarBeneficio(Integer idJoven, Integer idBeneficio) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		BeneficioDelJoven beneficio = this.traerBeneficio(joven, idBeneficio);
		EstadoBeneficio estadoBeneficio = estadoBeneficioDAO.traerPorId(EstadoBeneficio.EN_TRAMITE);
		
		GestorDeBeneficios gestorBenef = new GestorDeBeneficios(jovenDAO, estadoBeneficioDAO);
		gestorBenef.modificarBeneficio(joven, beneficio, estadoBeneficio);
		
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void eliminarBeneficio(Integer idJoven, Integer idBeneficio) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		BeneficioDelJoven beneficio = this.traerBeneficio(joven, idBeneficio);
		
		joven.eliminarBeneficio(beneficio);
		
		jovenDAO.actualizar(joven);
	}
	
	public List<EstadisticaBeneficio> listaBeneficiariosActivos(Integer idBeneficio, String idInstitucion) {		
		return tipoDeBeneficioDAO.listaBeneficiariosActivos(idBeneficio, idInstitucion);
	}
		
	public List<EstadisticaBeneficio> traerBeneficiosParaOperativo(Integer idBeneficio, String idInstitucion) {
		return tipoDeBeneficioDAO.listaBeneficiosParaOperativo(idBeneficio, idInstitucion);
	}
	
	private BeneficioDelJoven traerBeneficio(Joven joven, Integer idBeneficio) {
		java.util.Iterator<BeneficioDelJoven> iter = joven.getBeneficios().iterator();
		BeneficioDelJoven beneficio = null;
		
		while (iter.hasNext()) {
			BeneficioDelJoven ben = iter.next();
			if (ben.getId().equals(idBeneficio))
				beneficio = ben;
		}
		
		return beneficio;
	}
}

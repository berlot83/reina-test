package snya.reina.servicio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.archivoscliente.ArchivoCliente;
import snya.notificacionescliente.NotificacionCliente;
import snya.notificacionescliente.NotificacionException;
import snya.notificacionescliente.modelo.Mail;
import snya.reina.ReinaException;
import snya.reina.datos.intervencion.MotivoAprehensionDAO;
import snya.reina.datos.intervencion.TipoDeInformeDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.informe.TipoDeEstadoInformeEnum;
import snya.reina.modelo.informe.TipoDeInforme;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.intervencion.MotivoAprehension;
import snya.reina.modelo.intervencion.RegistroAdmision;
import snya.reina.modelo.intervencion.TipoDeIntervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.MovimientoIngreso;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.movimiento.TipoDeMovimiento;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.EnvioDeArchivos;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.serviciomodelo.intervencion.GeneradorIntervencion;
import snya.reina.serviciomodelo.movimiento.GeneradorDeMovimiento;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;
import snya.reina.serviciomodelo.proceso.AdministradorDeProcesoFactory;

@Service
public class JovenAdmisionServicioImpl {

	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private ArchivoCliente archivoCliente;
	@Autowired
	private MotivoAprehensionDAO motivoAprehensionDAO;
	@Autowired
	private TipoDeInformeDAO tipoDeInformeDAO;

	@Autowired
	private GeneradorDeMovimiento generador;
	@Autowired
	private GeneradorIntervencion generadorIntervencion;
	@Autowired
	private AdministradorDeProcesoFactory adminProcesoFactory;
	@Autowired
	private GuiaDeRecursos recursero;
	@Autowired
	private NotificacionCliente notificacionCliente;

	// PRORROGA
	@Transactional
	public void generarProrroga(Integer idJoven, Integer idTipoInforme, Date fecha, EnvioDeArchivos envio,
			String usuario) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);

		// guardado de prorroga
		RegistroAdmision admision = joven.traerAdmisionAbierta();
		admision.setProrroga(true);

		// prorrogar medida en proceso penal
		AdministradorDeProceso administrador = adminProcesoFactory.crearAdministradorDeProceso();
		ProcesoPenal procesoPenal = admision.getProceso();
		Integer idOrgJud = (procesoPenal.getOrganoJudicial() != null) ? procesoPenal.getOrganoJudicial().getId() : null;
		Integer[] idsMedidasImpuestas = new Integer[1];
		idsMedidasImpuestas[0] = TipoDeDetalleDeMedidaEnProcesoPenal.ID_APREHENSION;

		administrador.agregarMedida(procesoPenal, fecha, idOrgJud, TipoDeMedidaEnProcesoPenal.ID_APREHENSION,
				idsMedidasImpuestas, "", 12, 0, 0, null);

		// genero informe
		Informe informe = null;
		if (idTipoInforme != 0) {
			TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(idTipoInforme);
			informe = joven.agregarInforme(admision.getIntervencion(), fecha, tipo, "", admision.getInstitucionAdmision(),
					usuario, true, TipoDeEstadoInformeEnum.IMPRESO);
			jovenDAO.guardarInforme(informe);

			// guardado de archivo
			if (envio != null) {
				envio.guardarArchivoInforme(archivoCliente, idJoven, informe, fecha);
			}
		}

		jovenDAO.actualizar(joven);
	}

	// SOLICITUD DE INTERVENCION
	@Transactional
	public void guardarSolicitud(Integer idJoven, String observacion, String usuario) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		RegistroAdmision admision = joven.traerAdmisionAbierta();

		// genero informe
		TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(TipoDeInforme.ID_SOLICITUD_INTERVENCION);
		Date fecha = new Date();
		Informe informe = joven.agregarInforme(admision.getIntervencion(), fecha, tipo, observacion,
				admision.getInstitucionAdmision(), usuario, false, TipoDeEstadoInformeEnum.IMPRESO);
		jovenDAO.guardarInforme(informe);

		admision.setSolicitudIntervencion(informe);

		jovenDAO.actualizar(joven);

		// Enviar mail
		List<Institucion> instituciones = recursero.traerInstitucionesVinculadas(admision.getInstitucionAdmision().getId(),
				TipoDeInstitucion.ID_SERVICIOSZONAL);
		if (instituciones.size() > 0) {
			String email = instituciones.get(0).getEMail();
			String emailAdmision = admision.getInstitucionAdmision().getEMail();
			email += (emailAdmision != null && emailAdmision != "") ? ";" + emailAdmision : "";

			String subject = "SOLICITUD DE INTERVENCION para el NNoA " + joven.getDescripcionJoven().toUpperCase()
					+ " desde el " + admision.getInstitucionAdmision().getNombreCorto().toUpperCase();
			String body = informe.getObservacion();
			try {
				notificacionCliente.enviar(new Mail(email, subject, body));
			} catch (NotificacionException e) {
				// TODO ver como manejo el error del mail
			}
		}
	}

	// ACTUALIZAR ADMISION
	@Transactional
	public void actualizarAdmision(Integer idJoven, Integer idAdmision,
			String lugarAprehension, Date fechaAprehension, 
			Integer idMotivoAprehension, Integer idComisaria, String policiaInterviniente, String legajoPolicial,
			String datosMovilPolicial, String elementosSecuestrados, String circunstanciaAprehension, Integer idJuzgado,
			Integer idFiscalia, Boolean esDefensorOficial, Integer idDefensoria,
			Integer idDefensorOficial, String abogado, String iPP, String nroCarpeta, String nroCausa, Integer caratula,
			Boolean gradoTentativa, String observacionCaratula) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		RegistroAdmision admision = joven.traerAdmisionAbierta();
		
		AdministradorDeProceso administrador = adminProcesoFactory.crearAdministradorDeProceso();
		ProcesoPenal procesoPenal = admision.getProceso();

		administrador.modificarProcesoPenal(null, idJuzgado, idFiscalia, esDefensorOficial, idDefensoria, idDefensorOficial, abogado, iPP, nroCarpeta, nroCausa, procesoPenal);
		administrador.modificarMotivoIntervencion(caratula, gradoTentativa, observacionCaratula, procesoPenal);
		
		Institucion comisaria = recursero.traerInstitucionPorId(idComisaria);
		MotivoAprehension motivoAprehension = motivoAprehensionDAO.traerPorId(idMotivoAprehension);
		
		admision.setFechaAprehension(fechaAprehension);
		admision.setLugarAprehension(lugarAprehension);
		admision.setMotivoAprehension(motivoAprehension);			
		admision.setComisaria(comisaria);
		admision.setPoliciaInterviniente(policiaInterviniente);
		admision.setLegajoPolicial(legajoPolicial);
		admision.setDatosMovilPolicial(datosMovilPolicial);
		admision.setCircunstanciaAprehension(circunstanciaAprehension);
		
		jovenDAO.actualizar(joven);
	}
	
	// INGRESO A CAD
	public Joven crearIngreso(Joven joven, MedidaEnProcesoPenal medidaImpuesta,
			Date fechaIngreso, Integer idInstitucion, String observacion,
			Date fAprehension, String lugarAprehension, ProcesoPenal proceso, Integer idMotivoAprehension, 
			Integer idComisaria, String policiaInterviniente, String legajoPolicial, String datosMovilPolicial,
			String circunstanciaAprehension) throws ReinaException {
		Intervencion interv = generadorIntervencion.crearIntervencion(joven, TipoDeIntervencion.ID_ADMISION_CAD);				
		Movimiento movimiento = generador.agregarIngresoAdmision(interv, fechaIngreso, idInstitucion, observacion, joven, medidaImpuesta);

		agregarRegistroAdmision(interv, joven, fechaIngreso, fAprehension, lugarAprehension, proceso, idMotivoAprehension,
				idComisaria, policiaInterviniente, legajoPolicial, datosMovilPolicial, circunstanciaAprehension,
				movimiento);
		
		return joven;
	}

	@Transactional
	public Joven guardarIngreso(Integer idJoven, MedidaEnProcesoPenal medidaImpuesta,
			Date fechaIngreso, Integer idInstitucion, String observacion,
			Date fAprehension, String lugarAprehension, ProcesoPenal proceso, Integer idMotivoAprehension, 
			Integer idComisaria, String policiaInterviniente, String legajoPolicial, String datosMovilPolicial,
			String circunstanciaAprehension) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Intervencion interv = generadorIntervencion.crearIntervencion(joven, TipoDeIntervencion.ID_ADMISION_CAD);				
		Movimiento movimiento = generador.agregarIngresoAdmision(interv, fechaIngreso, idInstitucion, observacion, joven, medidaImpuesta);

		agregarRegistroAdmision(interv, joven, fechaIngreso, fAprehension, lugarAprehension, proceso, idMotivoAprehension,
				idComisaria, policiaInterviniente, legajoPolicial, datosMovilPolicial, circunstanciaAprehension,
				movimiento);

		jovenDAO.actualizar(joven);
		
		Permanencia per = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		per.setIngreso(per.getId());
		jovenDAO.actualizar(joven);
		return joven;
	}
	
	private void agregarRegistroAdmision(Intervencion intervencion, Joven joven, Date fechaIngreso, Date fAprehension, String lugarAprehension,
			ProcesoPenal proceso, Integer idMotivoAprehension, Integer idComisaria, String policiaInterviniente,
			String legajoPolicial, String datosMovilPolicial, String circunstanciaAprehension, Movimiento movimiento) {
		Institucion comisaria = recursero.traerInstitucionPorId(idComisaria);
		MotivoAprehension motivoAprehension = motivoAprehensionDAO.traerPorId(idMotivoAprehension);
		RegistroAdmision registroAdmision = new RegistroAdmision(intervencion, ((MovimientoIngreso)movimiento).getAmbitoEjecucion(), joven, fechaIngreso, fAprehension,
				lugarAprehension, proceso, motivoAprehension, comisaria, policiaInterviniente, legajoPolicial,
				datosMovilPolicial, circunstanciaAprehension);
		joven.getAdmisiones().add(registroAdmision);
		registroAdmision.setJoven(joven);
	}
	

	// MOVIMIENTOS
	@Transactional
	public void guardarMovimiento(Integer idJoven, Integer idTipoMovimiento, Date fechaMovimiento, String observacion,
			EnvioDeArchivos envio, Integer idDestino) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		RegistroAdmision admision = joven.traerAdmisionAbierta();
		Intervencion intervencion = admision.getIntervencion();

		// genero informe
		if (envio != null && envio.tieneArchivos()) {
			TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(TipoDeInforme.ID_COMUNICACION_MOVIMIENTO);
			Informe informe = joven.agregarInforme(intervencion, fechaMovimiento, tipo, observacion,
					admision.getInstitucionAdmision(), envio.getUsuario(), envio.tieneArchivos(),
					TipoDeEstadoInformeEnum.IMPRESO);
			jovenDAO.guardarInforme(informe);

			envio.guardarArchivoInforme(archivoCliente, idJoven, informe, fechaMovimiento);
		}

		// generar movimiento
		generador.agregarMovimientoAdmision(idTipoMovimiento, fechaMovimiento, observacion, idDestino, joven, admision,
				intervencion);

		jovenDAO.actualizar(joven);
	}

	// EGRESOS
	@Transactional
	public void guardarEgreso(Integer idJoven, Integer idTipoMovimiento, Date fechaMovimiento, String observacion,
			Integer idDestino, EnvioDeArchivos envio, String usuario) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		RegistroAdmision admision = joven.traerAdmisionAbierta();
		Intervencion intervencion = admision.getIntervencion();

		// generar informe
		/*
		 * if (envio != null && envio.tieneArchivos()) {
			TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(TipoDeInforme.ID_ACTA_EGRESO);
			Informe informe = joven.agregarInforme(intervencion, fechaMovimiento, tipo, observacion,
					admision.getInstitucionAdmision(), envio.getUsuario(), envio.tieneArchivos(),
					TipoDeEstadoInformeEnum.IMPRESO);
			jovenDAO.guardarInforme(informe);

			envio.guardarArchivoInforme(archivoCliente, idJoven, informe, fechaMovimiento);
		} */
		TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(TipoDeInforme.ID_ACTA_EGRESO);
		Informe informe = joven.agregarInforme(intervencion, fechaMovimiento, tipo, observacion,
				admision.getInstitucionAdmision(), usuario, false,
				TipoDeEstadoInformeEnum.IMPRESO);
		jovenDAO.guardarInforme(informe);
		
		// generar egreso
		TipoDeMovimiento tipoMovimiento = generador.agregarEgresoAdmision(idTipoMovimiento, fechaMovimiento,
				"", idDestino, joven, admision, intervencion);

		jovenDAO.actualizar(joven);

		// Enviar mail
		if (tipoMovimiento.getFuncionalidadMovimiento() != TipoDeMovimiento.FUNCIIONALIDAD_TRASLADO) {
			List<Institucion> instituciones = recursero.traerInstitucionesVinculadas(admision.getInstitucionAdmision().getId(),
					TipoDeInstitucion.ID_SERVICIOSZONAL);
			if (instituciones.size() > 0) {
				String email = instituciones.get(0).getEMail();
				String emailAdmision = admision.getInstitucionAdmision().getEMail();
				email += (emailAdmision != null && emailAdmision != "") ? ";" + emailAdmision : "";

				String subject = "COMUNICA EGRESO del NNoA " + joven.getDescripcionJoven().toUpperCase() + " desde el "
						+ admision.getInstitucionAdmision().getNombreCorto().toUpperCase() + " el "
						+ Calendario.formatearFechaHora(fechaMovimiento);
				String body = "<p style=\"text-align: justify;\">Tengo el agrado de dirigirme a Ud. en mi car&aacute;cter de Director/a del "
						+ admision.getInstitucionAdmision().getNombreCorto().toUpperCase()
						+ ", en relaci&oacute;n a&nbsp;<strong>" + joven.getDescripcionJoven().toUpperCase()
						+ "</strong> a fin de notificar el egreso el dia "
						+ Calendario.formatearFechaHora(fechaMovimiento) + " bajo el motivo de "
						+ tipoMovimiento.getNombre() + " </p> <br /> <p>Sin mas saludo atentamente</p>";
				try {
					notificacionCliente.enviar(new Mail(email, subject, body));
				} catch (NotificacionException e) {
					// TODO ver como manejo el error del mail
				}
			}
		}
	}

	public List<Recurso> traerDestinosPorTipoMovimiento(int idTipoDeMovimiento) {
		return generador.traerDestinosPorTipoMovimiento(idTipoDeMovimiento);
	}

	public List<TipoDeMovimiento> traerPosiblesMovimientosAdmision(Joven joven) {
		return joven.traerUltimoMovimientoCorrecto().traerPosiblesMovimientosAdmision();
	}

	public List<TipoDeMovimiento> traerPosiblesEgresosAdmision(Joven joven) {
		return joven.traerUltimoMovimientoCorrecto().traerPosiblesEgresosAdmision();
	}

	public List<MotivoAprehension> traerMotivosAprehensionTodos() {
		return motivoAprehensionDAO.traerTodos();
	}
}

package snya.reina.serviciomodelo.intervencion;

import java.util.Date;
import java.util.Iterator;

import snya.reina.modelo.Calendario;
import snya.reina.modelo.educacion.Escolaridad;
import snya.reina.modelo.informe.TipoDeInforme;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.intervencion.RegistroAdmision;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.EstadoMovimiento;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.MovimientoEgreso;
import snya.reina.modelo.movimiento.MovimientoIngreso;
import snya.reina.modelo.movimiento.MovimientoInternacion;
import snya.reina.modelo.movimiento.MovimientoRetornoInternacion;
import snya.reina.modelo.movimiento.MovimientoTraslado;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.movimiento.TipoDeMovimiento;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.referente.Familiar;
import snya.reina.modelo.referente.TipoDeParentesco;

public class ReemplazoDeVariablesInforme {

	public static String reemplazarVariables(Date fecha, TipoDeInforme tipo, Joven joven, ProcesoPenal proceso, Familiar referente, Institucion institucion, RegistroAdmision admision) {
		String nota =  tipo.getNota();
		nota = reemplazarVariablesSimples(nota, fecha);
		nota = reemplazarVariablesJoven(nota, joven);
		nota = reemplazarVariablesProceso(nota, proceso);
		nota = reemplazarVariablesReferente(nota, referente);
		nota = reemplazarVariablesPrograma(nota, null);
		nota = reemplazarVariablesEducacion(nota, joven);
		nota = reemplazarVariablesSalud(nota, null);
		nota = reemplazarVariablesInstitucion(nota, institucion);
		nota = reemplazarVariablesMovimiento(nota, joven);
		nota = reemplazarVariablesAdmision(nota, admision);
				
		return nota;
	}

	public static String reemplazarVariablesPorEspacio(TipoDeInforme tipo) {
		String nota =  tipo.getNota();
		nota = reemplazarVariablesSimplesPorEspacio(nota);
		nota = reemplazarVariablesJovenPorEspacio(nota);
		nota = reemplazarVariablesProceso(nota, null);
		nota = reemplazarVariablesReferente(nota, null);
		nota = reemplazarVariablesPrograma(nota, null);
		nota = reemplazarVariablesEducacion(nota, null);
		nota = reemplazarVariablesSalud(nota, null);
		nota = reemplazarVariablesInstitucion(nota, null);
		nota = reemplazarVariablesMovimiento(nota, null);
		nota = reemplazarVariablesAdmision(nota, null);
		
		return nota;
	}

	
	private static String reemplazarVariablesAdmision(String nota, RegistroAdmision admision) {
		nota = remplazoVariable(nota, "[:Admision_FechaIngresoTextoCompleto:]", (admision != null) ? Calendario.formatearFechaHoraTextoCompleto( admision.getFechaIngreso() ) : null);
		nota = remplazoVariable(nota, "[:Admision_FechaAprehensionTextoCompleto:]", (admision != null) ? Calendario.formatearFechaHoraTextoCompleto( admision.getFechaAprehension() ) : null);
		nota = remplazoVariable(nota, "[:Admision_FechaAprehension:]", (admision != null) ? Calendario.formatearFecha( admision.getFechaAprehension() ) : null);
		nota = remplazoVariable(nota, "[:Admision_HoraAprehension:]", (admision != null) ? Calendario.formatearHora( admision.getFechaAprehension() ) : null);
		nota = remplazoVariable(nota, "[:Admision_LugarAprehension:]", (admision != null) ? admision.getLugarAprehension() : null);
		nota = remplazoVariable(nota, "[:Admision_MotivoAprehension:]", (admision != null) ? admision.getMotivoAprehension().getNombre() : null);
		nota = remplazoVariable(nota, "[:Admision_FechaEgreso:]", (admision != null && admision.getFechaEgreso() != null) ? Calendario.formatearFechaHoraTextoCompleto( admision.getFechaEgreso() ) : null);
		nota = remplazoVariable(nota, "[:Admision_MotivoEgreso:]", (admision != null && admision.getMotivoEgreso() != null) ? admision.getMotivoEgreso().getNombre() : null);	
		nota = remplazoVariable(nota, "[:Admision_Comisiario:]", (admision != null && admision.getComisaria() != null) ? admision.getComisaria().getNombre() : null);	
		nota = remplazoVariable(nota, "[:Admision_PoliciaInterviniente:]", (admision != null) ? admision.getPoliciaInterviniente() : null);		
		nota = remplazoVariable(nota, "[:Admision_LegajoPolicial:]", (admision != null) ? admision.getLegajoPolicial() : null);		
		nota = remplazoVariable(nota, "[:Admision_DatosMovilPolicial:]", (admision != null) ? admision.getDatosMovilPolicial() : null);
		nota = remplazoVariable(nota, "[:Admision_CircunstanciaAprehension:]", (admision != null) ? admision.getCircunstanciaAprehension() : null);
				
		return nota;
	}
	
	private static String reemplazarVariablesInstitucion(String nota, Institucion institucion) {
		nota = remplazoVariable(nota, "[:Institucion_Nombre:]", (institucion != null) ? institucion.getNombre() : null);
		nota = remplazoVariable(nota, "[:Institucion_Domicilio:]", (institucion != null) ? institucion.getDomicilioEnTexto() : null);
		nota = remplazoVariable(nota, "[:Institucion_Telefono:]", (institucion != null) ? institucion.getTelefonoEnTexto() : null);
		nota = remplazoVariable(nota, "[:Institucion_Ciudad:]", 
				(institucion != null && institucion.getDomicilio() != null) 
					? ( (institucion.getDomicilio().getLocalidad() != null) ? institucion.getDomicilio().getLocalidad().getNombre() : institucion.getDomicilio().getMunicipio().getNombre() )
					: null);
		return nota;
	}
	
	private static String reemplazarVariablesMovimiento(String nota, Joven joven) {
		
		String fechaPrimera = null;
		if(joven != null){
			Permanencia perPrimer = joven.traerPrimerPermanenciaDeCumplimiento();
			fechaPrimera = (perPrimer != null) ? perPrimer.getFechaInicioTexto() : null;
		}
		nota = remplazoVariable(nota, "[:HojaDeRuta_FechaPrimerIngreso:]", fechaPrimera);
		
		String fechaUltima = null;
		if(joven != null){
		Permanencia perUltima = joven.traerUltimaPermanenciaDeCumplimiento();
		if (perUltima != null)
			for (Permanencia per : joven.getPermanencias()) {
				if ( per.getId().equals( perUltima.getIngreso() ) )
					fechaUltima = per.getFechaFinTexto();
			} 		
		}
		nota = remplazoVariable(nota, "[:HojaDeRuta_FechaUltimoIngreso:]", fechaUltima);

		nota = remplazoVariable(nota, "[:HojaDeRuta_Tabla:]", hojaDeRuta(joven));
		
		return nota;
	}

	private static String reemplazarVariablesSalud(String nota, Object object) {
		// TODO Auto-generated method stub
		return nota;
	}

	private static String reemplazarVariablesEducacion(String nota, Joven joven) {
		
		if (joven != null)
			if (joven.getEscolaridades().size() > 0) {
				Escolaridad escolaridad = joven.traerUltimaEscolaridad();
				nota = remplazoVariable(nota, "[:Educacion_Dato:]", escolaridad.traerDetalle());
				
				String anioNivel = ((escolaridad.getAnioEscolar() != null) ? escolaridad.getAnioEscolar().getNombre() + " - " : "")  + ((escolaridad.getNivel() != null) ? escolaridad.getNivel().getNombre() + " - " : "");
				nota = remplazoVariable(nota, "[:Educacion_AnioNivel:]", anioNivel);
				nota = remplazoVariable(nota, "[:Educacion_Modalidad:]", (escolaridad.getModalidad() != null) ? escolaridad.getModalidad().getNombre() : "");
				nota = remplazoVariable(nota, "[:Educacion_Establecimiento:]", escolaridad.getEstablecimientoNombre());
			}
			else {
				nota = remplazoVariable(nota, "[:Educacion_Dato:]", null);
				nota = remplazoVariable(nota, "[:Educacion_AnioNivel:]", null);
				nota = remplazoVariable(nota, "[:Educacion_Modalidad:]", null);
				nota = remplazoVariable(nota, "[:Educacion_Establecimiento:]", null);
			}
		else {
			nota = remplazoVariable(nota, "[:Educacion_Dato:]", null);
			nota = remplazoVariable(nota, "[:Educacion_AnioNivel:]", null);
			nota = remplazoVariable(nota, "[:Educacion_Modalidad:]", null);
			nota = remplazoVariable(nota, "[:Educacion_Establecimiento:]", null);			
		}
		
		return nota;
	}

	private static String reemplazarVariablesPrograma(String nota, Object object) {
		// TODO Auto-generated method stub
		return nota;
	}

	private static String reemplazarVariablesReferente(String nota, Familiar referente) {
		nota = remplazoVariable(nota, "[:Referente_Parentesco:]", (referente != null) ? referente.getParentesco().getNombre() : null);
		nota = remplazoVariable(nota, "[:Referente_Apellido:]", (referente != null) ? referente.getApellidos() : null);
		nota = remplazoVariable(nota, "[:Referente_Nombre:]",  (referente != null) ? referente.getNombres() : null);
		nota = remplazoVariable(nota, "[:Referente_NombreCompleto:]",  (referente != null) ? referente.getNombreCompleto() : null);
		nota = remplazoVariable(nota, "[:Referente_FechaNacimiento:]", (referente!= null && referente.getFechaFallecimiento() != null) ? Calendario.formatearFecha(referente.getFechaFallecimiento()) : null);
		nota = remplazoVariable(nota, "[:Referente_Edad:]", (referente != null && referente.getEdad() != null) ? referente.getEdad().toString() : null);
		nota = remplazoVariable(nota, "[:Referente_TipoDocumento:]", (referente!= null && referente.getPersona().getTipoDeDocumento() != null) ? referente.getPersona().getTipoDeDocumento().getNombre() : null);
		nota = remplazoVariable(nota, "[:Referente_NumeroDocumento:]", (referente != null) ? referente.getPersona().getNumeroDocumento() : null);
		nota = remplazoVariable(nota, "[:Referente_Sexo:]", (referente!= null && referente.getSexo() == "F") ? "Femenino" : "Masculino" );
		nota = remplazoVariable(nota, "[:Referente_Nacionalidad:]", (referente!= null && referente.getPersona().getNacionalidad() != null) ? referente.getPersona().getNacionalidad().getNombre() : null);
		nota = remplazoVariable(nota, "[:Referente_EstadoCivil:]", (referente!= null && referente.getPersona().getEstadoCivil() != null) ? referente.getPersona().getEstadoCivil().getNombre() : null);
		nota = remplazoVariable(nota, "[:Referente_Trabajo:]", (referente!= null && referente.getPersona().getTipoDeTrabajo() != null) ?  referente.getPersona().getTipoDeTrabajo().getNombre() : null);
		nota = remplazoVariable(nota, "[:Referente_Domicilio:]", (referente!= null && referente.getPersona().getDomicilio() != null) ? referente.getPersona().getDomicilio().getDetalle() : null);
		
		return nota;
	}

	private static String reemplazarVariablesProceso(String nota, ProcesoPenal proceso) {
		nota = remplazoVariable(nota, "[:Proceso_Juzgado:]", (proceso != null && proceso.getOrganoJudicial() != null) ? proceso.getOrganoJudicial().getNombre() : null);
		nota = remplazoVariable(nota, "[:Proceso_Fiscalia:]", (proceso != null && proceso.getFiscalia() != null) ? proceso.getFiscalia().getNombre() : null);
		nota = remplazoVariable(nota, "[:Proceso_Defensoria:]",  (proceso != null) ? proceso.getDetalleDefensoria() : null);
		nota = remplazoVariable(nota, "[:Proceso_Nros:]",  (proceso != null) ? proceso.getNumeroIdentificatorio() : null);
		nota = remplazoVariable(nota, "[:Proceso_MotivoIntervencion:]",  (proceso != null) ? proceso.getMotivoIntervencion().getTipo().getDescripcion() : null);
		nota = remplazoVariable(nota, "[:Proceso_MotivoIntervencionDetalle:]",  (proceso != null) ? proceso.getMotivoIntervencion().getObservacion() : null);
		nota = remplazoVariable(nota, "[:Proceso_MomentoProcesal:]",  (proceso != null) ? proceso.getMomentoProcesal().getTipo().getNombre() : null);
		nota = remplazoVariable(nota, "[:Proceso_SituacionProcesal:]",  (proceso != null) ? proceso.getSituacionProcesal().getNombre() : null);
		nota = remplazoVariable(nota, "[:Proceso_FechaImposicion:]",  (proceso != null) ? Calendario.formatearFecha( proceso.getMedidaImpuesta().getFechaMedida() ) : null);
		nota = remplazoVariable(nota, "[:Proceso_MedidaImpuesta:]",  (proceso != null) ? proceso.getMedidaImpuesta().getTipo().getNombre() : null);
		nota = remplazoVariable(nota, "[:Proceso_MedidaImpuestaTipo:]",  (proceso != null) ? proceso.getMedidaImpuesta().traerDetalleTipificado() : null);
		nota = remplazoVariable(nota, "[:Proceso_MedidaImpuestaDetalle:]",  (proceso != null) ? proceso.getMedidaImpuesta().getObservacion() : null);

		return nota;
	}

	private static String reemplazarVariablesJoven(String nota, Joven joven) {
		nota = remplazoVariable(nota, "[:Joven_ApellidoMaterno:]", joven.getPersona().getApellidoMaterno());
		nota = remplazoVariable(nota, "[:Joven_Apellido:]", joven.getPersona().getApellidos());
		nota = remplazoVariable(nota, "[:Joven_Nombre:]", joven.getPersona().getNombres());
		nota = remplazoVariable(nota, "[:Joven_NombreCompleto:]", joven.getPersona().getNombreCompleto());
		nota = remplazoVariable(nota, "[:Joven_FechaNacimiento:]", (joven.getFechaNacimiento() != null) ? Calendario.formatearFecha(joven.getFechaNacimiento()) : null);
		nota = remplazoVariable(nota, "[:Joven_FechaFallecimiento:]", (joven.getFechaFallecimiento() != null) ? Calendario.formatearFecha(joven.getFechaFallecimiento()) : null);
		nota = remplazoVariable(nota, "[:Joven_Edad:]", joven.getEdad().toString());
		nota = remplazoVariable(nota, "[:Joven_TipoDocumento:]", (joven.getPersona().getTipoDeDocumento() != null) ? joven.getPersona().getTipoDeDocumento().getNombre() : null);
		nota = remplazoVariable(nota, "[:Joven_NumeroDocumento:]", joven.getPersona().getNumeroDocumento());
		nota = remplazoVariable(nota, "[:Joven_SituacionDNI:]", joven.getUltimaSituacionesTramiteDocumento().getTipo().getNombre());
		nota = remplazoVariable(nota, "[:Joven_Sexo:]", (joven.getSexo() == "F") ? "Femenino" : "Masculino" );
		nota = remplazoVariable(nota, "[:Joven_Legajo:]", (joven.getExpedienteIdentificador() != null) ? joven.getExpedienteIdentificador().getNumeroCompleto() : null);		
		nota = remplazoVariable(nota, "[:Joven_ProvinciaNacimiento:]", (joven.getPersona().getProvincia() != null) ? joven.getPersona().getProvincia().getNombre() : null);
		nota = remplazoVariable(nota, "[:Joven_MunicipioNacimiento:]", (joven.getPersona().getMunicipio() != null) ? joven.getPersona().getMunicipio().getNombre() : null);
		nota = remplazoVariable(nota, "[:Joven_LocalidadNacimiento:]", (joven.getPersona().getLocalidad() != null) ? joven.getPersona().getLocalidad().getNombre() : null);
		nota = remplazoVariable(nota, "[:Joven_Nacionalidad:]", joven.getPersona().getNacionalidad().getNombre());
		nota = remplazoVariable(nota, "[:Joven_EstadoCivil:]", joven.getPersona().getEstadoCivil().getNombre());
		nota = remplazoVariable(nota, "[:Joven_Trabajo:]", joven.getPersona().getTipoDeTrabajo().getNombre());
		nota = remplazoVariable(nota, "[:Joven_Domicilio:]", (joven.getPersona().getDomicilio() != null) ? joven.getPersona().getDomicilio().getDetalle() : null);
	
		nota = remplazoVariable(nota, "[:Padre_NombreCompleto:]", nombrePadre(joven));
		nota = remplazoVariable(nota, "[:Madre_NombreCompleto:]", nombreMadre(joven));
		nota = remplazoVariable(nota, "[:Padre_NumeroDocumento:]", numeroDocumentoPadre(joven));
		nota = remplazoVariable(nota, "[:Madre_NumeroDocumento:]", numeroDocumentoMadre(joven));
		nota = remplazoVariable(nota, "[:Padre_Vive:]", vivePadre(joven));
		nota = remplazoVariable(nota, "[:Madre_Vive:]", viveMadre(joven));
		
		nota = remplazoVariable(nota, "[:Tabla_referente_no_conviviente:]", no_convivientes(joven));
		nota = remplazoVariable(nota, "[:Tabla_referente_conviviente:]", convivientes(joven));
		
		return nota;
	}
	

	private static String no_convivientes(Joven joven) {
		String fila = "<table><tr><th>Tipo de Relaci&oacute;n</th><th>Apellido y Nombre</th><th>Edad</th><th>Domicilio</th><th>Ocupacion</th></tr>";
		
		if (joven != null) {
			Iterator<Familiar> iter = joven.getFamiliares().iterator();
			while (iter.hasNext()) {
				Familiar familiar = (Familiar) iter.next();
				if(!familiar.getConvive())
					fila += 
						"<tr><td>" + familiar.getParentesco().getNombre()  + "</td>" +
						"<td>" + familiar.getNombreCompleto()  + "</td>" +
						"<td>" + ((familiar.getEdad() != null) ? familiar.getEdad().toString() : "")  + "</td>" +
						"<td>" + ((familiar.getDomicilio() != null) ? familiar.getDomicilio().getDetalle() : "")  + "</td>" +
						"<td>" + ((familiar.getTipoDeTrabajo() != null) ? familiar.getTipoDeTrabajo().getNombre() : "")  + "</td></tr>";
			}
		}
		fila += "</table>";
		
		return fila;
	}

	
	private static String convivientes(Joven joven) {
		String fila = "<table><tr><th>Tipo de Relaci&oacute;n</th><th>Apellido y Nombre</th><th>Edad</th><th>Domicilio</th><th>Ocupacion</th></tr>";
		
		if (joven != null) {
			Iterator<Familiar> iter = joven.getFamiliares().iterator();				
			while (iter.hasNext()) {
				Familiar familiar = (Familiar) iter.next();
				if(familiar.getConvive())
					fila += 
						"<tr><td>" + familiar.getParentesco().getNombre()  + "</td>" +
						"<td>" + familiar.getNombreCompleto()  + "</td>" +
						"<td>" + ((familiar.getEdad() != null) ? familiar.getEdad().toString() : "")  + "</td>" +
						"<td>" + ((familiar.getDomicilio() != null) ? familiar.getDomicilio().getDetalle() : "")  + "</td>" +
						"<td>" + ((familiar.getTipoDeTrabajo() != null) ? familiar.getTipoDeTrabajo().getNombre() : "")  + "</td></tr>";
			}
		}
		fila += "</table>";
		
		return fila;
	}

	private static String hojaDeRuta(Joven joven) {
		String fila = "<table><tr><th>Fecha</th><th>Movimiento</th><th>Origen</th><th>Destino</th></tr>";
		
		if (joven != null) {
			for (Movimiento mov : joven.getMovimientos()) {	
				if (mov.getEstado().getId().equals( EstadoMovimiento.CONFIRMADO )) {
					int idFuncionalidad = mov.getTipo().getFuncionalidadMovimiento();
					
					fila += 
						"<tr><td>" + Calendario.formatearFecha( mov.getFecha() )  + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>" +
						"<td>" + mov.getTipo().getNombre()  + "</td>";
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_INGRESO)
						fila += 
							"<td> </td>" +
							"<td>" + ((MovimientoIngreso) mov).getAmbitoEjecucion().traerNombre()  + "</td></tr>";
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_TRASLADO)
						fila += 
							"<td>" + ((MovimientoTraslado) mov).getAmbitoOrigen().traerNombre()  + "</td>" +
							"<td>" + ((MovimientoTraslado) mov).getAmbitoDestino().traerNombre()  + "</td></tr>";
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_NOTIFICACION)
						fila += 
						"<td> </td>" +
						"<td> </td></tr>";
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_EGRESO)
						fila += 
						"<td>" + ((MovimientoEgreso) mov).getAmbitoOrigen().traerNombre()  + "</td>" +
						"<td> </td></tr>";
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_BAJA)
						fila += 
						"<td> </td>" +
						"<td> </td></tr>";
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_INTERNACION)
						fila += 
							"<td>" + ((MovimientoInternacion) mov).getAmbitoOrigen().traerNombre()  + "</td>" +
							"<td>" + ((MovimientoInternacion) mov).getAmbitoDestino().traerNombre()  + "</td></tr>";						
					if (idFuncionalidad == TipoDeMovimiento.FUNCIIONALIDAD_RETORNOINTERNACION)
						fila += 
							"<td>" + ((MovimientoRetornoInternacion) mov).getAmbitoOrigen().traerNombre()  + "</td>" +
							"<td>" + ((MovimientoRetornoInternacion) mov).getAmbitoDestino().traerNombre()  + "</td></tr>";
				}
			}
		}
		fila += "</table>";
		
		return fila;
	}
	
	private static String viveMadre(Joven joven) {
		Familiar refe = traerReferenteConParentesco(joven, TipoDeParentesco.ID_MADRE);		
		String nombre = ""; 
		
		if (refe != null)
			nombre = refe.getVive() ? "Vive" : "NO Vive";
			
		return nombre;
	}


	private static String vivePadre(Joven joven) {
		Familiar refe = traerReferenteConParentesco(joven, TipoDeParentesco.ID_PADRE);		
		String nombre = ""; 
		
		if (refe != null)
			nombre = refe.getVive() ? "Vive" : "NO Vive";
			
		return nombre;	
	}


	private static String numeroDocumentoMadre(Joven joven) {
		Familiar refe = traerReferenteConParentesco(joven, TipoDeParentesco.ID_MADRE);		
		String nombre = ""; 
		
		if (refe != null)
			nombre = refe.getNumeroDocumento();
			
		return nombre;	
	}

	private static String numeroDocumentoPadre(Joven joven) {
		Familiar refe = traerReferenteConParentesco(joven, TipoDeParentesco.ID_PADRE);		
		String nombre = ""; 
		
		if (refe != null)
			nombre = refe.getNumeroDocumento();
			
		return nombre;	
	}
	
	private static String nombreMadre(Joven joven) {		
		Familiar refe = traerReferenteConParentesco(joven, TipoDeParentesco.ID_MADRE);		
		String nombre = ""; 
		
		if (refe != null)
			nombre = refe.getNombreCompleto();
			
		return nombre;	
	}

	private static String nombrePadre(Joven joven) {
		Familiar refe = traerReferenteConParentesco(joven, TipoDeParentesco.ID_PADRE);		
		String nombre = ""; 
		
		if (refe != null)
			nombre = refe.getNombreCompleto();
			
		return nombre;		
	}

	private static Familiar traerReferenteConParentesco(Joven joven, Integer idParentesco) {
		Iterator<Familiar> iter = joven.getFamiliares().iterator();
		Familiar padre = null;

		
		while (iter.hasNext()) {
			Familiar fam = (Familiar) iter.next();
			if (fam.getParentesco().getId().equals(idParentesco))
				padre = fam;
		}
		return padre;
	}
	
	private static String reemplazarVariablesJovenPorEspacio(String nota) {
		nota = remplazoVariable(nota, "[:Joven_ApellidoMaterno:]", null);
		nota = remplazoVariable(nota, "[:Joven_Apellido:]", null);
		nota = remplazoVariable(nota, "[:Joven_Nombre:]", null);
		nota = remplazoVariable(nota, "[:Joven_NombreCompleto:]", null);
		nota = remplazoVariable(nota, "[:Joven_FechaNacimiento:]", null);
		nota = remplazoVariable(nota, "[:Joven_FechaFallecimiento:]", null);
		nota = remplazoVariable(nota, "[:Joven_Edad:]", null);
		nota = remplazoVariable(nota, "[:Joven_TipoDocumento:]", null);
		nota = remplazoVariable(nota, "[:Joven_NumeroDocumento:]", null);
		nota = remplazoVariable(nota, "[:Joven_SituacionDNI:]", null);
		nota = remplazoVariable(nota, "[:Joven_Sexo:]", null );
		nota = remplazoVariable(nota, "[:Joven_Legajo:]", null);		
		nota = remplazoVariable(nota, "[:Joven_ProvinciaNacimiento:]", null);
		nota = remplazoVariable(nota, "[:Joven_MunicipioNacimiento:]", null);
		nota = remplazoVariable(nota, "[:Joven_LocalidadNacimiento:]", null);
		nota = remplazoVariable(nota, "[:Joven_Nacionalidad:]", null);
		nota = remplazoVariable(nota, "[:Joven_EstadoCivil:]", null);
		nota = remplazoVariable(nota, "[:Joven_Trabajo:]", null);
		nota = remplazoVariable(nota, "[:Joven_Domicilio:]", null);

		nota = remplazoVariable(nota, "[:Padre_NombreCompleto:]", null);
		nota = remplazoVariable(nota, "[:Madre_NombreCompleto:]", null);
		nota = remplazoVariable(nota, "[:Padre_NumeroDocumento:]", null);
		nota = remplazoVariable(nota, "[:Madre_NumeroDocumento:]", null);
		nota = remplazoVariable(nota, "[:Padre_Vive:]", null);
		nota = remplazoVariable(nota, "[:Madre_Vive:]", null);
		
		nota = remplazoVariable(nota, "[:Tabla_referente_no_conviviente:]", "<table><tr><th>Tipo de Relaci&oacute;n</th><th>Apellido y Nombre</th><th>Edad</th><th>Domicilio</th><th>Ocupacion</th></tr></table>");
		nota = remplazoVariable(nota, "[:Tabla_referente_conviviente:]", "<table><tr><th>Tipo de Relaci&oacute;n</th><th>Apellido y Nombre</th><th>Edad</th><th>Domicilio</th><th>Ocupacion</th></tr></table>");
		
		return nota;
	}

	
	private static String reemplazarVariablesSimples(String nota, Date fecha) {
		nota = nota.replace("[:Informe_Fecha:]", Calendario.formatearFecha(fecha));
		nota = nota.replace("[:Informe_FechaTexto:]", Calendario.formatearFechaTexto(fecha));
		nota = nota.replace("[:Informe_FechaTextoCompleto:]", Calendario.formatearFechaTextoCompleto(fecha));
		nota = nota.replace("[:Informe_Hora:]", Calendario.formatearHora(fecha));
		nota = nota.replace("[:Informe_FechaHoraTextoCompleto:]", Calendario.formatearFechaHoraTextoCompleto(fecha));
		
		return nota;
		
	}

	private static String reemplazarVariablesSimplesPorEspacio(String nota) {
		nota = remplazoVariable(nota, "[:Informe_Fecha:]", null);
		nota = nota.replace("[:Informe_FechaTexto:]", null);
		nota = nota.replace("[:Informe_FechaTextoCompleto:]", null);
		nota = nota.replace("[:Informe_Hora:]", null);
		nota = nota.replace("[:Informe_FechaHoraTextoCompleto:]", null);
		
		return nota;
	}

	private static String remplazoVariable(String nota, String variable, String dato) {
		dato = (dato == null) ? "................." : dato;
		return nota.replace(variable, dato);
	}
}

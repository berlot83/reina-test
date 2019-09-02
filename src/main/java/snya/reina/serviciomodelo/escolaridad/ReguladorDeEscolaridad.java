package snya.reina.serviciomodelo.escolaridad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import snya.reina.ReinaException;
import snya.reina.modelo.educacion.AnioEscolar;
import snya.reina.modelo.educacion.Capacitacion;
import snya.reina.modelo.educacion.CapacitacionJoven;
import snya.reina.modelo.educacion.Dictado;
import snya.reina.modelo.educacion.Escolaridad;
import snya.reina.modelo.educacion.ModalidadEscolar;
import snya.reina.modelo.educacion.NivelEscolar;
import snya.reina.modelo.educacion.TipoDeEstadoCapacitacionJoven;
import snya.reina.modelo.joven.Joven;

@Component
public class ReguladorDeEscolaridad {

	//
	// ESCOLARIDAD
	//
	public Escolaridad agregarEscolaridad(Joven joven, ModalidadEscolar modalidad,
			NivelEscolar nivel, AnioEscolar año, String establecimiento,
			Integer cicloLectivo, Boolean periodoEvaluacion, Boolean certificado, Boolean cursando,
			Boolean finalizado, String observacion) {
		Escolaridad escolaridad = new Escolaridad(joven, modalidad, nivel, año, establecimiento, cicloLectivo, periodoEvaluacion, certificado, cursando, finalizado, observacion);
		
		
		if (escolaridad.isCursando() || escolaridad.isPeriodoEvaluacion()) {
			for (Escolaridad esc : joven.getEscolaridades()) {
				if ( esc.isCursando() || (escolaridad.isPeriodoEvaluacion() && escolaridad.getCicloLectivo() >= esc.getCicloLectivo()) ) esc.setCursando(false);		
			}
		}		
		
		joven.getEscolaridades().add(escolaridad);
		
		recalcularUltimaEscolaridad(joven);
		return escolaridad;
	}
	
	public Escolaridad modificarEscolaridad(Joven joven, Escolaridad escolaridad, 
			ModalidadEscolar modalidad,
			NivelEscolar nivel, AnioEscolar año, String establecimiento,
			Integer cicloLectivo, Boolean periodoEvaluacion, Boolean certificado, Boolean cursando,
			Boolean finalizado, String observacion) {
		
		if(!escolaridad.isCursando() && cursando) {			
			for (Escolaridad esc : joven.getEscolaridades()) {
				if ( esc.isCursando() ) esc.setCursando(false);	
			}
		}
		
		// <<procesamiento>>
		escolaridad.setModalidad(modalidad);
		escolaridad.setNivel(nivel);
		escolaridad.setAnioEscolar(año);
		escolaridad.setEstablecimientoNombre(establecimiento);
		escolaridad.setCicloLectivo(cicloLectivo);
		escolaridad.setObservacion(observacion);
		escolaridad.setPeriodoEvaluacion(periodoEvaluacion);
		escolaridad.setCertificado(certificado);
		escolaridad.setCursando(cursando);
		escolaridad.setFinalizado(finalizado);
		
		recalcularUltimaEscolaridad(joven);
		
		// <<resultado>>
		return escolaridad;
	}
	
	public void	eliminarEscolaridad(Joven joven, Escolaridad escolaridad) {
		joven.getEscolaridades().remove(escolaridad);
				
		recalcularUltimaEscolaridad(joven);
	}

	
	private void recalcularUltimaEscolaridad(Joven joven) {
		if ( joven.getEscolaridades().size() > 0 ) {
			List<Escolaridad> lista = new ArrayList<Escolaridad>(joven.getEscolaridades());
			Collections.sort(lista, Escolaridad.comparador());
			
			for (Escolaridad esc : joven.getEscolaridades()) {
				esc.setUltimo(false);	
			}
			Escolaridad escUltima = lista.get(lista.size() - 1);
			escUltima.setUltimo(true);
		}
	}
	

	//
	// CAPACITACION
	//
	public void agregarCapacitacion(List<Capacitacion> capacitacionesHabilitadas, Joven joven, Capacitacion capacitacion, Dictado dictado, Date fInicio, Date fFin,
			TipoDeEstadoCapacitacionJoven estado, String observacion, Date fechaEntregaCertificado) throws ReinaException {
		CapacitacionJoven capJoven = new CapacitacionJoven(joven, capacitacion, dictado, fInicio, fFin, estado, observacion);
		capJoven.setFechaEntregaCertificado(fechaEntregaCertificado);
		
		// validar que el joven se encuentra en un ambito donde puede indicarse
		List<Capacitacion> capacitaciones = capacitacionesHabilitadas;
		boolean estaH = false;
		for (Capacitacion cap : capacitaciones) {
			estaH |= (cap.getId().equals(capacitacion.getId()));
		}
		if(!estaH)
			throw new ReinaException("Debe seleccionar una capacitacion, cuyos dictados sean en el ámbito donde cumple la medida");
		
		// validar que no exista otro registro para la misma capacitacion activa o finalizada
		boolean esta = false;
		for (CapacitacionJoven capacJoven : joven.getCapacitaciones()) {
			esta |= ( capacJoven.getCapacitacion().getId().equals(capacitacion.getId()) && capacJoven.estaVigente() );
		}
		if(esta)
			throw new ReinaException("El joven se encuentra o ha realizado la capacitación que se desea registrar");
		
		// que no quiera comenzar antes del inicio del dictado
		if (fFin != null && fInicio.after(fFin))
			throw new  ReinaException("La fecha de sucripción del joven a la formación laboral " + capacitacion.getNombre() + " debe ser anterior a la fecha de finalización");
			
		// que la fecha del certificado sea posterior a la de inicio
		if (fechaEntregaCertificado != null && fInicio.after(fechaEntregaCertificado))
			throw new  ReinaException("La fecha de sucripción del joven a la formación laboral " + capacitacion.getNombre() + " debe ser anterior a la fecha de entrega del certificado");

		joven.getCapacitaciones().add(capJoven);
	}

	public void modificarCapacitacion(Joven joven, CapacitacionJoven capacitacionJoven, Capacitacion capacitacion,
			Dictado dictado, Date fInicio, Date fFin, TipoDeEstadoCapacitacionJoven estado, String observacion, Date fechaEntregaCertificado) throws ReinaException {
		
		// validar que no exista otro registro para la misma capacitacion activa o finalizada
		boolean esta = false;
		for (CapacitacionJoven capacJoven : joven.getCapacitaciones()) {
			esta |= ( (capacitacionJoven.getId() != capacJoven.getId()) && capacJoven.getCapacitacion().getId().equals(capacitacion.getId()) && capacJoven.estaVigente() );
		}
		if(esta)
			throw new ReinaException("El joven se encuentra o ha realizado la capacitación que se desea registrar");
		
		// que no quiera comenzar antes del inicio del dictado
		if (fFin != null && fInicio.after(fFin))
			throw new  ReinaException("La fecha de sucripción del joven a la formación laboral " + capacitacion.getNombre() + " debe ser anterior a la fecha de finalización");
			
		// que la fecha del certificado sea posterior a la de inicio
		if (fechaEntregaCertificado != null && fInicio.after(fechaEntregaCertificado))
			throw new  ReinaException("La fecha de sucripción del joven a la formación laboral " + capacitacion.getNombre() + " debe ser anterior a la fecha de entrega del certificado");		
		
		
		// <<procesamiento>>
		capacitacionJoven.setCapacitacion(capacitacion);
		capacitacionJoven.setDictado(dictado);
		capacitacionJoven.setFechaInicio(fInicio);
		capacitacionJoven.setFechaFin(fFin);
		capacitacionJoven.setEstado(estado);
		capacitacionJoven.setObservacion(observacion);
		capacitacionJoven.setFechaEntregaCertificado(fechaEntregaCertificado);
	}

	public void eliminarCapacitacion(Joven joven, CapacitacionJoven capacitacion) {
		joven.getCapacitaciones().remove(capacitacion);		
	}
}

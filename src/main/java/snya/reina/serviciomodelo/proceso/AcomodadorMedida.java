package snya.reina.serviciomodelo.proceso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.proceso.DetalleMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.MomentoProcesal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;
import snya.reina.serviciomodelo.resultado.ResultadoProceso;

public class AcomodadorMedida {
	private boolean ultima;
			
	public boolean esLaUltima() {
		return ultima;
	}


	public ResultadoProceso insertarMedida(AdministradorDeProceso dao, ProcesoPenal proceso, MedidaEnProcesoPenal medida) throws ReinaException {
		return this.insertarMedida(dao, proceso, medida, false);
	}

	public ResultadoProceso modificarMedida(AdministradorDeProceso dao,
			ProcesoPenal proceso, MedidaEnProcesoPenal medida,
			Date fecha, OrganoJudicial organoJudicial,
			Institucion institucionAdopta, TipoDeMedidaEnProcesoPenal tipo,
			List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles,
			String observacion, Integer horasDeMedida,
			Integer diasDeMedida, Integer mesesDeMedida,
			Date fechaFinDeMedida) throws ReinaException {
		// Quito medida
		quitarMedidaDeLosMomentosProcesales(proceso, medida);
		
		// Modifco medida
		medida.setFechaMedida(fecha);
		medida.setAdoptaMedida(organoJudicial);
		medida.setTipo(tipo);
		medida.modificarDetalles(detalles);
		medida.setTiempoDeHorasMedida(horasDeMedida);
		medida.setTiempoDeDiasMedida(diasDeMedida);
		medida.setTiempoDeMesesMedida(mesesDeMedida);
		medida.setTiempoDeFechaFinMedida(fechaFinDeMedida);
		medida.recalcularTiempos();
		medida.setObservacion(observacion);
		
		// Inserto la medida nuevamente
		return this.insertarMedida(dao, proceso, medida, true);
	}
	
	
	public void eliminarMedida(AdministradorDeProceso dao, ProcesoPenal proceso, MedidaEnProcesoPenal medida) {
		quitarMedidaDeLosMomentosProcesales(proceso, medida);
		
		// Eliminar medida
		_eliminarMedida(proceso, medida);
		
		
		// Actualizar la medida anterior			
		MedidaEnProcesoPenal anterior = null;
		boolean cortar = false;
		List<MedidaEnProcesoPenal> lista = new ArrayList<MedidaEnProcesoPenal>( proceso.getMedidasImpuestas() );
		Collections.sort(lista);
		Iterator<MedidaEnProcesoPenal> it = lista.iterator();
		while (!cortar && it.hasNext()) {
			MedidaEnProcesoPenal medidaProceso = (MedidaEnProcesoPenal) it.next();
			
			cortar = (medidaProceso.getFechaMedida().getTime() > medida.getFechaMedida().getTime());
			if (!cortar) anterior = medidaProceso;
		}			
		// modifico la medida anterior
		if(anterior != null)
			anterior.setFechaFinMedida(null);
		
		// Actualizar la situacion procesal
		proceso.actualizarSituacionProcesal(dao, proceso.getMomentoProcesal().getTipo(), medida.getTipo());
	}


	private void _eliminarMedida(ProcesoPenal proceso, MedidaEnProcesoPenal medida) {
		for (DetalleMedidaEnProcesoPenal detalle : medida.getDetalles()) {
			detalle.setMedida(null);
		}
		medida.getDetalles().clear();
		proceso.getMedidasImpuestas().remove(medida);
	}
	
	private ResultadoProceso insertarMedida(AdministradorDeProceso dao, ProcesoPenal proceso, MedidaEnProcesoPenal medida, boolean existente) throws ReinaException {
		MedidaEnProcesoPenal anterior = null;
		MedidaEnProcesoPenal siguiente = null;
		boolean cortar = false;
		List<MedidaEnProcesoPenal> lista = new ArrayList<MedidaEnProcesoPenal>( proceso.getMedidasImpuestas() );
		if (existente) lista.remove(medida);
		Collections.sort(lista);
		
		Iterator<MedidaEnProcesoPenal> it = lista.iterator();
		while (!cortar && it.hasNext()) {
			MedidaEnProcesoPenal medidaProceso = (MedidaEnProcesoPenal) it.next();
			
			cortar = (medidaProceso.getFechaMedida().getTime() > medida.getFechaMedida().getTime());
			if (!cortar) anterior = medidaProceso;
			if (cortar) siguiente = medidaProceso;
		}
		ultima = (siguiente == null);
		
		// modifico la medida anterior
		if(anterior != null) {
			medida.setFechaMedida( Calendario.sumarHorario(medida.getFechaMedida(), anterior.getFechaMedida()) );
			anterior.setFechaFinMedida(medida.getFechaMedida());				
		} else {
			// es la primer medida
			proceso.setFechaIngreso(medida.getFechaMedida());
			proceso.getMomentosProcesales().get(0).setFechaImposicion(medida.getFechaMedida());
			proceso.getMotivosIntervencion().get(0).setFechaMotivo(medida.getFechaMedida());
		}
		
		// Si no es la ultima medida
		if (!ultima) {			
			siguiente.setFechaMedida( Calendario.sumarHorario(siguiente.getFechaMedida(), medida.getFechaMedida()) );
			
			medida.setFechaFinMedida( siguiente.getFechaMedida() );
		} else {
			// si es acutalizo el organo judicial
			if (medida.getAdoptaMedida() instanceof OrganoJudicial) proceso.setOrganoJudicial( (OrganoJudicial) medida.getAdoptaMedida() );
			proceso.actualizarSituacionProcesal(dao, proceso.getMomentoProcesal().getTipo(), medida.getTipo());
		}
		
		// Agrega la medida
		if(!existente) proceso.getMedidasImpuestas().add(medida);
		
		// Agregar la medida en los momentos
		this.agregarMedidaEnMomentos(proceso, medida);
					
		// si es una medida que cierra el proceso
		return actuarSiCierraProceso(proceso, medida);
	}

	private void quitarMedidaDeLosMomentosProcesales(ProcesoPenal proceso, MedidaEnProcesoPenal medida) {
		// Eliminar la medida de los momentos procesales
		for (MomentoProcesal momento : proceso.getMomentosProcesales()) {
			if (momento.contieneMedidaJudicial(medida))
				momento.getMedidasImpuestas().remove(medida);
		}
	}

	private void agregarMedidaEnMomentos(ProcesoPenal proceso, MedidaEnProcesoPenal medida) {
		List<MomentoProcesal> momentos = traerMomentosOrdenados(proceso);
		Iterator<MomentoProcesal> itm = momentos.iterator();
		boolean pasado = false;  
		while (!pasado && itm.hasNext()) {
			MomentoProcesal momento = (MomentoProcesal) itm.next();
			if( momento.vigenteAl(medida.getFechaMedida()) ) { 
			//if(	momento.getFechaImposicion().getTime() <= medida.getFechaMedida().getTime() ) {
				momento.getMedidasImpuestas().add(medida);
			} else
				pasado = true;
		}
	}

	private List<MomentoProcesal> traerMomentosOrdenados(ProcesoPenal proceso) {
		Comparator<MomentoProcesal> momento_orden = new Comparator<MomentoProcesal>() {
			public int compare(MomentoProcesal m1, MomentoProcesal m2) {
				return m1.getFechaImposicion().compareTo(m2.getFechaImposicion());
			}
		};
		List<MomentoProcesal> momentos = new ArrayList<MomentoProcesal>( proceso.getMomentosProcesales() );
		Collections.sort(momentos, momento_orden);
		return momentos;
	}		
	
	private ResultadoProceso actuarSiCierraProceso(ProcesoPenal proceso, MedidaEnProcesoPenal medida) throws ReinaException {
		ResultadoProceso resultado = new ResultadoProceso(false, proceso, medida);
		
		if (ultima && medida.getTipo().isCierraProceso()) {
			proceso.getMomentoProcesal().setFechaFin(medida.getFechaMedida());
			medida.setFechaFinMedida(medida.getFechaMedida());
			proceso.setEstaFinalizado(true);
			
			resultado = new ResultadoProceso(true, proceso, medida);
		} else
		{ 
			if (medida.getTipo().isCierraProceso())
				throw new ReinaException(ReinaCte.MEDIDA_CIERRA_PROCESO_NO_ES_ULTIMA);
		}
		
		return resultado;
	}
}

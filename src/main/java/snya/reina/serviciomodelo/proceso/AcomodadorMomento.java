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
import snya.reina.modelo.proceso.TipoDeMomentoProcesal;

public class AcomodadorMomento {
	
	public void validarQueNoExisteMomento(ProcesoPenal proceso, TipoDeMomentoProcesal tipo, MomentoProcesal momentoEx) throws ReinaException {
		for (MomentoProcesal momento : proceso.getMomentosProcesales()) {
			if (momento.getTipo().getId().equals(tipo.getId()) && 
					( (momentoEx == null) || (momentoEx != null && !momentoEx.getId().equals(momento.getId())) )  )
				throw new ReinaException(ReinaCte.PROCESO_YA_ESTA_EN_MOMENTO_PROCESAL);				
		}	
	}
	
	public void insertarMomento(AdministradorDeProceso dao, ProcesoPenal proceso, Date fecha, Institucion organoJudicial, TipoDeMomentoProcesal tipo) throws ReinaException {
		if ( puedeInsertarMomento(proceso, tipo) ) {
			
			MomentoProcesal momento = new MomentoProcesal(fecha, tipo, organoJudicial, proceso);

			MomentoProcesal anterior = null;
			MomentoProcesal siguiente = null;
			boolean cortar = false;
			boolean ultima = false;
			List<MomentoProcesal> lista = traerMomentosOrdenados( proceso );
			
			Iterator<MomentoProcesal> it = lista.iterator();
			while (!cortar && it.hasNext()) {
				MomentoProcesal mom = (MomentoProcesal) it.next();
				
				cortar = (mom.getFechaImposicion().getTime() > fecha.getTime());
				if (!cortar) anterior = mom;
			}
			ultima = !it.hasNext();
			
			// modifico la medida anterior
			if(anterior != null) {
				momento.setFechaImposicion( Calendario.sumarHorario(momento.getFechaImposicion(), anterior.getFechaImposicion()) );
				anterior.setFechaFin(momento.getFechaImposicion());				
			} else {
				// es el primer momento
				if ( !Calendario.mismoDia(fecha, proceso.getFechaIngreso()) )
					throw new ReinaException(ReinaCte.MOMENTO_PRIMERO_NO_TIENE_MISMA_FECHA_MEDIDA);
			}
			
			// Agrega el momento
			proceso.getMomentosProcesales().add(momento);
			
			// Si no es el ultimo momento
			if (!ultima) {
				siguiente = (MomentoProcesal) it.next();
				siguiente.setFechaImposicion( Calendario.sumarHorario(siguiente.getFechaImposicion(), momento.getFechaImposicion()) );
				
				momento.setFechaFin( siguiente.getFechaImposicion() );
			} else {
				// si es acutalizo el organo judicial
				if (momento.getJuzgado() instanceof OrganoJudicial) proceso.setOrganoJudicial( (OrganoJudicial) momento.getJuzgado() );
				if (proceso.getMedidaImpuesta() != null) proceso.actualizarSituacionProcesal(dao, proceso.getMomentoProcesal().getTipo(), proceso.getMedidaImpuesta().getTipo());
			}
			
			// Agregar la medida en los momentos
			this.acomodarMedidasEnMomento(proceso, anterior, momento, siguiente);
		}
	}

	public void modificarMomento(AdministradorDeProceso dao,
			ProcesoPenal proceso, MomentoProcesal momento, Date fecha,
			TipoDeMomentoProcesal tipo, OrganoJudicial organoJudicial) throws ReinaException {

		MomentoProcesal anterior = null;
		MomentoProcesal siguiente = null;
		boolean cortar = false;
		boolean ultima = false;
		List<MomentoProcesal> lista = traerMomentosOrdenados( proceso );
		
		Iterator<MomentoProcesal> it = lista.iterator();
		while (!cortar && it.hasNext()) {
			MomentoProcesal mom = (MomentoProcesal) it.next();
			
			cortar = (mom.getId().equals(momento.getId()));
			if (!cortar) anterior = mom;
			if (cortar) siguiente = mom;
		}
		ultima = (siguiente == null);
		
		// modifico la medida anterior
		if(anterior != null) {
			if( fecha.getTime() < anterior.getFechaImposicion().getTime() )
				throw new ReinaException(ReinaCte.MOMENTO_ANTERIOR_CON_FECHA_MAS_CHICA);
			
			momento.setFechaImposicion( Calendario.sumarHorario(fecha, anterior.getFechaImposicion()) );
			anterior.setFechaFin(momento.getFechaImposicion());		
		} else {
			// es el primer momento
			if ( !Calendario.mismoDia(fecha, proceso.getFechaIngreso()) )
				throw new ReinaException(ReinaCte.MOMENTO_PRIMERO_NO_TIENE_MISMA_FECHA_MEDIDA);
		}
		
		// Si no es el ultimo momento
		if (!ultima) {			
			if( fecha.getTime() > siguiente.getFechaImposicion().getTime() )
				throw new ReinaException(ReinaCte.MOMENTO_SIGUIENTE_CON_FECHA_MAS_CHICA);
			
			siguiente.setFechaImposicion( Calendario.sumarHorario(siguiente.getFechaImposicion(), fecha) );
			
			momento.setFechaImposicion( fecha );
			momento.setFechaFin( siguiente.getFechaImposicion() );
		} else {
			// si es acutalizo el organo judicial
			momento.setFechaImposicion( fecha );
			if (momento.getJuzgado() instanceof OrganoJudicial) proceso.setOrganoJudicial( (OrganoJudicial) momento.getJuzgado() );
			proceso.actualizarSituacionProcesal(dao, proceso.getMomentoProcesal().getTipo(), proceso.getMedidaImpuesta().getTipo());
		}
		
		// Agrega la medida
		momento.setTipo(tipo);
		momento.setJuzgado(organoJudicial);
		
		// Agregar la medida en los momentos
		this.acomodarMedidasEnMomentoExistente(proceso, anterior, momento, siguiente);		
	}
		
	public void eliminarMomento(AdministradorDeProceso dao, ProcesoPenal proceso, MomentoProcesal momento) throws ReinaException {
		
		// quito las medidas del momento
		List<MomentoProcesal> momentos = traerMomentosOrdenados(proceso);
		MomentoProcesal anteUltimo = (momentos.size() > 1) ? momentos.get(momentos.size() - 2) : null;
		
		// elimino el momento
		proceso.getMomentosProcesales().remove(momento);
		
		// restablesco el anteultimo como ultimo quitando la fecha fin
		int indice = proceso.getMomentosProcesales().indexOf(anteUltimo);
		if(indice != -1) {
			proceso.getMomentosProcesales().get(indice).setFechaFin(null);
			//anteUltimo.setFechaFin(null);	
		}
		
		//
		revisarMedidasDelMomento(proceso, anteUltimo); 

		// 
		List<MedidaEnProcesoPenal> lista = new ArrayList<MedidaEnProcesoPenal>(momento.getMedidasImpuestas());
		for (MedidaEnProcesoPenal medida : lista) {			
			if (anteUltimo != null && !anteUltimo.contieneMedidaJudicial(medida)) {
				proceso.validaQueMedidaNoEsteEnIntervencionesSRPJ(medida);
				
				_eliminarMedida(proceso, medida);
			}
		}
		momento.getMedidasImpuestas().clear();
		
		
		proceso.getMedidaImpuesta().setFechaFinMedida(null);
		proceso.getMedidaImpuesta().recalcularTiempos();
		proceso.actualizarSituacionProcesal(dao, proceso.getMomentoProcesal().getTipo(), proceso.getMedidaImpuesta().getTipo());
	}
	
	
	private void acomodarMedidasEnMomentoExistente(ProcesoPenal proceso, MomentoProcesal anterior, MomentoProcesal momento, MomentoProcesal siguiente) {
		// reviso las medidas del anterior
		revisarMedidasDelMomento(proceso, anterior);
		
		// reviso las medidas al momento
		revisarMedidasDelMomento(proceso, momento);
		
		// reviso las medidas del posterior
		revisarMedidasDelMomento(proceso, siguiente);
	}
	
	private void acomodarMedidasEnMomento(ProcesoPenal proceso, MomentoProcesal anterior, MomentoProcesal momento, MomentoProcesal siguiente) {
		// reviso las medidas del anterior
		revisarMedidasDelMomento(proceso, anterior);
		
		// agrego las medidas al nuevo momento
		agregarMedidasEnMomento(proceso, momento);
		
		// reviso las medidas del posterior
		revisarMedidasDelMomento(proceso, siguiente);
	}

	
	private void agregarMedidasEnMomento(ProcesoPenal proceso, MomentoProcesal momento) {
		for (MedidaEnProcesoPenal medida : proceso.getMedidasImpuestas()) {
			if (momento.vigenteAl(medida.getFechaMedida())) {
				momento.getMedidasImpuestas().add(medida);
			}
		}
	}

	private void revisarMedidasDelMomento(ProcesoPenal proceso, MomentoProcesal momento) {
		if (momento != null) {			
			for (MedidaEnProcesoPenal medida : proceso.getMedidasImpuestas()) {
				if (momento.getMedidasImpuestas().contains(medida)) {
					if (!momento.vigenteAl(medida.getFechaMedida())) {
						momento.getMedidasImpuestas().remove(medida);
					}					
				} else {
					if (momento.vigenteAl(medida.getFechaMedida())) {
						momento.getMedidasImpuestas().add(medida);
					}
				}
			}
		}
	}
	
	private boolean puedeInsertarMomento(ProcesoPenal proceso,
			TipoDeMomentoProcesal tipo) {
		boolean insertar = false;
		try {
			validarQueNoExisteMomento(proceso, tipo, null);
			insertar = true;
		} catch (ReinaException e) {
		}
		
		return insertar;
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
	
	private void _eliminarMedida(ProcesoPenal proceso, MedidaEnProcesoPenal medida) {
		for (DetalleMedidaEnProcesoPenal detalle : medida.getDetalles()) {
			detalle.setMedida(null);
		}
		medida.getDetalles().clear();
		proceso.getMedidasImpuestas().remove(medida);
	}
}

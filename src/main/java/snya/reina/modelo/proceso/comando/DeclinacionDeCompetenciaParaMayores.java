package snya.reina.modelo.proceso.comando;

import snya.reina.ReinaException;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;

public class DeclinacionDeCompetenciaParaMayores extends ComandoProceso {

	
	@Override
	public void hacer() throws ReinaException {
		Integer[] idsMedidasImpuestas = new Integer[0];
		
		getAdministrador().agregarMedida(getProceso(), getNota().getFecha(), getProceso().getOrganoJudicial().getId(), TipoDeMedidaEnProcesoPenal.ID_DECLINACION_MAYORES, idsMedidasImpuestas, 
				getNota().getObservacion(), 0, 0, 0, null);
	}
	
	@Override
	public void deshacer() throws ReinaException {		
		getAdministrador().reabrirProcesoPenal(getProceso());
	}
}

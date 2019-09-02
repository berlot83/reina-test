package snya.reina.modelo.proceso.comando;

import snya.reina.ReinaException;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;

public class ApelacionSuspensiva extends ComandoProceso {

	@Override
	public void hacer() throws ReinaException {
		Integer idMedida = (Integer) this.getParametros()[1];
		this.getNota().setIdReferencia(idMedida);
		
		MedidaEnProcesoPenal medida = this.getProceso().traerMedidaPorId(idMedida);
		medida.marcarApelacion(this.getNota().getFecha());
	}

	@Override
	public void deshacer() throws ReinaException {
		MedidaEnProcesoPenal medida = this.getProceso().traerMedidaPorId(this.getNota().getIdReferencia());
		medida.quitarApelacion();
		
		this.getNota().setIdReferencia(null);
	}
	
	public boolean requiereMedida() {
		return true;
	}	
}

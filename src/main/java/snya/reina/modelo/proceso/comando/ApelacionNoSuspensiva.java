package snya.reina.modelo.proceso.comando;

import snya.reina.ReinaException;

public class ApelacionNoSuspensiva extends ComandoProceso {

	@Override
	public void hacer() throws ReinaException {
		Integer idMedida = (Integer) this.getParametros()[1];
		this.getNota().setIdReferencia(idMedida);
	}

	@Override
	public void deshacer() throws ReinaException {
		this.getNota().setIdReferencia(null);
	}
	
	public boolean requiereMedida() {
		return true;
	}
}

package snya.reina.modelo.proceso.comando;

import snya.reina.ReinaException;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;

public class UnificarProcesos extends ComandoProceso {

	
	@Override
	public void hacer() throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		
		Integer idProceso = (Integer) this.getParametros()[2];
		
		getAdministrador().unificarAlProceso(getNota().getFecha(), getProceso(), idProceso);
		getProceso().getJoven().cambiarCondicionDePermanenciaPorUnificacion(getNota().getFecha(), getProceso(), escritor);
	}
	
	@Override
	public void deshacer() throws ReinaException {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
				
		getProceso().getJoven().retrotraerCondicionDePermanenciaPorUnificacion(getNota().getFecha(), getProceso(), escritor);
		getAdministrador().dividirProceso(getProceso());
	}

	public boolean requiereProceso() {
		return true;
	}	
}

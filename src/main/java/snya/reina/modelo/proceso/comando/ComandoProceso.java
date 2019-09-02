package snya.reina.modelo.proceso.comando;

import snya.reina.ReinaException;
import snya.reina.modelo.proceso.NotaProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;
import snya.reina.serviciomodelo.proceso.AdministradorDeProceso;
import snya.reina.utilidades.Comando;

public class ComandoProceso implements Comando {
	
	private AdministradorDeProceso administrador;
	private ProcesoPenal proceso;
	private NotaProcesoPenal nota;
	private Object[] parametros;
	
	public void configurar(AdministradorDeProceso administrador, ProcesoPenal proceso, NotaProcesoPenal nota, Object... parametros) {
		this.administrador = administrador;
		this.proceso = proceso;
		this.nota = nota;
		this.parametros = parametros;
	}

	@Override
	public void hacer() throws ReinaException {}

	@Override
	public void deshacer() throws ReinaException {}
	
	
	public boolean requiereJuzgado() {
		return false;
	}

	public boolean requiereMedida() {
		return false;
	}

	public boolean requiereProceso() {
		return false;
	}
	
	public ProcesoPenal getProceso() {
		return proceso;
	}

	public NotaProcesoPenal getNota() {
		return nota;
	}

	public Object[] getParametros() {
		return parametros;
	}

	public AdministradorDeProceso getAdministrador() {
		return administrador;
	}
}

package snya.reina.utilidades;

import snya.reina.ReinaException;

public interface Comando {

	public void hacer() throws ReinaException;
	
	public void deshacer() throws ReinaException;
}

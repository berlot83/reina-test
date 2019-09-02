package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorRecursos extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 903095452414420101L;


	public BusquedaJovenPorRecursos(Integer[] tipos, Integer[] ambitos, boolean reporte) {
		super(tipos, ambitos, reporte);
		
		this.setRestriccion(true);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		

	}
}

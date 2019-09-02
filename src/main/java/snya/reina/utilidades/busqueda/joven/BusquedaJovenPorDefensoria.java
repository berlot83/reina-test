package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorDefensoria extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9105622191451033437L;


	public BusquedaJovenPorDefensoria(Integer defensoria, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(defensoria.toString(), tipos, ambitos, restriccion, restriccionEdad, reporte);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(10, this.getDato());

	}
}

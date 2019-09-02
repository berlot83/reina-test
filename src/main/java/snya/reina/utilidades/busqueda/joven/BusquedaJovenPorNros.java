package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorNros extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1495243162473679095L;


	public BusquedaJovenPorNros(String nro, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(nro, tipos, ambitos, restriccion, restriccionEdad, reporte);
	}


	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(7, this.getDato());
		
	}
}

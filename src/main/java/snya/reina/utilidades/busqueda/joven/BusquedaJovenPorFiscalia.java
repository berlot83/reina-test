package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorFiscalia extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 782136166018775795L;


	public BusquedaJovenPorFiscalia(Integer fiscalia, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(fiscalia.toString(), tipos, ambitos, restriccion, restriccionEdad, reporte);
	}


	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(11, this.getDato());
		
	}
}

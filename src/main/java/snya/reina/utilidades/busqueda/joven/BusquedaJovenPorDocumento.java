package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorDocumento extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 453308357996400745L;


	public BusquedaJovenPorDocumento(String documento, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(documento, tipos, ambitos, restriccion, restriccionEdad, reporte);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(5, this.getDato());

	}
}

package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorNroPaquete extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2270532185800709607L;

	public BusquedaJovenPorNroPaquete(Integer nroPaquete, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(nroPaquete.toString(), tipos, ambitos, restriccion, restriccionEdad, reporte);
	}
	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(12, this.getDato());
	}
}

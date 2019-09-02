package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorExpediente extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1289947301897130220L;


	public BusquedaJovenPorExpediente(String expediente, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(expediente, tipos, ambitos, restriccion, restriccionEdad, reporte);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(6, this.getDato());
		
	}
}

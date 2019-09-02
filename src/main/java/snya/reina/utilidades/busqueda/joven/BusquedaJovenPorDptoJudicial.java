package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorDptoJudicial extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2823751620679966340L;


	public BusquedaJovenPorDptoJudicial(Integer dptoJudicial, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(dptoJudicial.toString(), tipos, ambitos, restriccion, restriccionEdad, reporte);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(8, this.getDato());

	}
}

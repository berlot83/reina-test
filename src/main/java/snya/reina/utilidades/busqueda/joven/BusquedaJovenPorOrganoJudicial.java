package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorOrganoJudicial extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3037616352213373637L;


	public BusquedaJovenPorOrganoJudicial(Integer juzgado, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(juzgado.toString(), tipos, ambitos, restriccion, restriccionEdad, reporte);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		param.setValor(9, this.getDato());

	}
}

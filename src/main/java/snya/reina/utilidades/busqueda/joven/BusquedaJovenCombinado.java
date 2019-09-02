package snya.reina.utilidades.busqueda.joven;

import java.util.List;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenCombinado extends BusquedaJoven {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782374952577448886L;
	private List<BusquedaJoven> busquedas;
	
	public BusquedaJovenCombinado(List<BusquedaJoven> busquedas, Integer[] tipos,
			Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(tipos, ambitos, reporte);

		this.busquedas = busquedas;
		this.setRestriccion(restriccion);
		this.setRestriccionEdad(restriccionEdad);
	}


	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		for (BusquedaJoven busq : busquedas) {
			busq.acumularParametro(param);
		}

	}
}

package snya.reina.utilidades.busqueda.joven;

import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorApellidoNombre extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7920595132090621273L;


	public BusquedaJovenPorApellidoNombre(String apyNom, Integer[] tipos,
			Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(apyNom, tipos, ambitos, restriccion, restriccionEdad, reporte);
	}

	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		String[] arreglo;
		// valida que se busque un dato
		if (this.getDato() == null || this.getDato().equals(""))
			throw new ReinaException(ReinaCte.BUSCAR_JOVEN_SIN_DATO_APYN);

		arreglo = this.getDato().split(",");
		if (arreglo.length > 1) {
			param.setValor(2, arreglo[0].trim());
			param.setValor(3, arreglo[1].trim());
		} else
			param.setValor(4, this.getDato());

	}
}

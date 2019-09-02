package snya.reina.utilidades.busqueda.joven;

import java.text.ParseException;
import java.util.Date;

import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.Calendario;
import snya.reina.utilidades.Parametro;

public class BusquedaJovenPorFechaDelPaquete extends BusquedaJoven {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5741642557187930518L;

	public BusquedaJovenPorFechaDelPaquete(Date fechaDelPaquete, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		super(Calendario.formatearFecha(fechaDelPaquete), tipos, ambitos, restriccion, restriccionEdad, reporte);
	}
	
	@Override
	protected void acumularParametro(Parametro param) throws ReinaException {		
		try {
			param.setValor(13, Calendario.formatearFechaMySql( Calendario.parsearFecha(this.getDato()) ) );
		} catch (ParseException e) {
			throw new ReinaException(ReinaCte.FECHA_ARCHIVO_MAL_FORMATO);
		}
	}
}
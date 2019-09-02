package snya.reina.serviciomodelo;

import snya.general.modelo.Telefono;
import snya.general.modelo.TipoDeTelefono;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;

public class GuiaTelefonica {

	public Telefono crearTelefono(
			TipoDeTelefono tipoTelefono, String caracteristica,
			String numero, String observacion) throws ReinaException {
		if (tipoTelefono == null)
			throw new ReinaException(ReinaCte.TELEFONO_SIN_TIPO);

		if (caracteristica == null || caracteristica.length() == 0)
			throw new ReinaException(ReinaCte.TELEFONO_SIN_CARACTERISTICA);
		
		if (numero == null || numero.length() == 0)
			throw new ReinaException(ReinaCte.TELEFONO_SIN_NUMERO);
		
		return  new Telefono(tipoTelefono, caracteristica, numero, observacion);
	}
}

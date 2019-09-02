package snya.reina.datos.proceso;

import snya.reina.modelo.proceso.TipoDeSituacionProcesal;

public interface TipoDeSituacionProcesalDAO {

	TipoDeSituacionProcesal traerPorId(Integer id);
	
	TipoDeSituacionProcesal calcularSituacionProcesal(Integer idTipoMomento, Integer idTipoMedida);
}

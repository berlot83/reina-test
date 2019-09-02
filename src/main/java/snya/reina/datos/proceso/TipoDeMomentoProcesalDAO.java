package snya.reina.datos.proceso;

import java.util.List;

import snya.reina.modelo.proceso.TipoDeMomentoProcesal;

public interface TipoDeMomentoProcesalDAO {

	List<TipoDeMomentoProcesal> traerTodosActivos();

	TipoDeMomentoProcesal traerPorId(Integer idMomentoProcesal);
}

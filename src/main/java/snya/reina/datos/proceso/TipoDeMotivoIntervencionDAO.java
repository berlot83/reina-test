package snya.reina.datos.proceso;

import java.util.List;

import snya.reina.modelo.proceso.TipoDeMotivoIntervencion;

public interface TipoDeMotivoIntervencionDAO {

	List<TipoDeMotivoIntervencion> traerTodosActivos();

	TipoDeMotivoIntervencion traerPorId(Integer idCaratula);

}

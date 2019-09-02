package snya.reina.datos.salud;

import java.util.List;

import snya.reina.modelo.salud.TipoDeDiagnostico;



public interface TipoDeDiagnosticoDAO {

	TipoDeDiagnostico traerPorId(Integer idDiagnostico);

	List<TipoDeDiagnostico> traerTodosActivos();

	List<TipoDeDiagnostico> traerTodosActivos(String diagnostico);
}

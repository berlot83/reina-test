package snya.reina.datos.institucion;

import java.util.List;

import snya.reina.modelo.recurso.ComponeRecurso;
import snya.reina.modelo.recurso.Recurso;

public interface RecursoREINADAO {

	Recurso traerPorId(Integer id);
	
	Recurso traerPor(ComponeRecurso componente);
	
	void inicializarAlPeresozo(Object proxy);
	
	void insertar(Recurso recurso);

	
	List<Recurso> traerInstitucionesCumpliminetoTodosActivos();
	
	List<Recurso> traerCentrosContencionTodosActivos();

	List<Recurso> traerCentrosRecepcionTodosActivos();

	List<Recurso> traerCentrosCerradosTodosActivos();

	List<Recurso> traerCentrosAdmisionTodosActivos();

	List<Recurso> traerCentrosDeSaludTodosActivos();

	List<Recurso> traerInstitucionesTerciarizadasTodosActivos();

	List<Recurso> traerUnidadesPenitenciariasTodos();

	List<Recurso> traerInstitucionesSPPDTodos();

	List<Recurso> traerCentroDeReferenciaTodos();

	List<Recurso> traerInstitucionesExtraJuridireccionalesTodosActivos();
}

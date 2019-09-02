package snya.reina.datos.institucion;

import java.util.List;

import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.ResultadoConsultaInstitucion;
import snya.reina.modelo.institucion.Dependencia;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.ReinaException;

public interface InstitucionDAO {

	void insertar(Institucion institucion) throws ReinaException;
	
	void actualizar(Institucion institucion);
	
	void insertarPrograma(ProgramaSimplificado programa) throws ReinaException;

	void actualizarPrograma(ProgramaSimplificado programa) throws ReinaException;
	
	Institucion traerPorIdCompleto(Integer id);	

	ProgramaSimplificado traerProgramaPorId(Integer id);
	
	List<ResultadoConsultaInstitucion> traerPorNombre(String nombre, String propiedad, String orden);
	
	List<Institucion> traerPosibleInstitucionPadreTodos();
	
	InstitucionCumplimiento traerAmbitoPorId(Integer idInstitucion);

	List<InstitucionCumplimiento> traerInstitucionCumplimientoTodas();

	List<InstitucionCumplimiento> traerInstitucionCumplimientoTodasActivas();

	List<Dependencia> traerDependenciasTodasActivas();

	List<Institucion> traerHijosDelPadre(Integer idSector);

	List<Institucion> traerInstitucionesVinculadas(Integer idInsittucion, Integer idTipoInstitucion);
	
	List<Institucion> traerTodosPorTipoActivos(Integer idTipo);	
}

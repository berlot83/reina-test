package snya.reina.datos.joven;

import java.util.Date;
import java.util.List;

import snya.reina.ReinaException;
import snya.reina.modelo.AsociacionAccionProcesos;
import snya.reina.modelo.JovenEscolaridad;
import snya.reina.modelo.JovenParteIntervencionPenal;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.JovenSimplificadoCAD;
import snya.reina.modelo.JovenSituacionIrregular;
import snya.reina.modelo.JovenTratamiento;
import snya.reina.modelo.MedidaEnProcesoDelJoven;
import snya.reina.modelo.MovimientoSimplificado;
import snya.reina.modelo.beneficio.BeneficioDelJoven;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.educacion.Escolaridad;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.estadistica.EstadisticaJovenInformeIntervencionPenal;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.utilidades.busqueda.joven.BusquedaJoven;

public interface JovenDAO {

	void insertar(Joven joven) throws ReinaException;

	void actualizar(Joven joven) throws ReinaException;

	void eliminar(Joven joven);
	
	void actualizarInforme(Informe informe);
	
	void inicializarAlPeresozo(Object proxy);
	
	boolean existeJovenConBeneficioYNumero(BeneficioDelJoven beneficio, TipoDeBeneficio tipo, String numero);
	
	Joven traerPorId(Integer id);

	Joven traerPorIdCapacitacion(Integer idCapacitacion);

	Joven traerPorIdBeneficio(Integer idBeneficio);
	
	List<JovenSimplificado> listaJoven(BusquedaJoven busqueda, String propiedad, String orden) throws ReinaException;
	//TODO
	List<JovenSimplificadoCAD> listaJovenCAD(BusquedaJoven busqueda, String propiedad, String orden) throws ReinaException;
	//TODO

	List<JovenSimplificado> listaJovenesPresentes(
			boolean reporte, Integer[] tipos, Integer[] recursos, String campo, String orden);
	
	List<JovenEscolaridad> listaJovenEscolaridad(BusquedaJoven busqueda) throws ReinaException;

	List<JovenParteIntervencionPenal> listaJovenPartePresente(Integer[] tipos, Integer[] destinos, Integer situacion, Integer mes, Integer anio) throws ReinaException;
	
	List<EstadisticaJovenInformeIntervencionPenal> listaInformesIntervencion(Integer[] tipos, Integer[] destinos, Integer mes, Integer anio);
	
	List<JovenTratamiento> listaJovenTratamiento(BusquedaJoven busqueda) throws ReinaException;
	
	List<EstadisticaFormacionLaboral> listaJovenFormacionLaboral(BusquedaJoven busqueda, Integer idFormacion, Integer idEstado) throws ReinaException;
	
	List<JovenSimplificado> listaJovenesSinExpediente(Integer[] tipos, Integer[] recursos, String propiedad, String orden);
		
	List<JovenSituacionIrregular> listaJovenesEnSituacionIrregular(
			Integer[] tipos, Integer[] recursos, String propiedad, String orden);

	List<MovimientoSimplificado> listaJovenesConMovimientosPendientes(
			Integer[] tipos, Integer[] recursos, String campo, String orden);

	List<AsociacionAccionProcesos> listaPosiblesParejasAnteRemplazoProceso(
			Integer idJoven, Integer idProceso);

	List<MedidaEnProcesoDelJoven> listaMedidasDelJoven(Integer idJoven, Integer idProceso, Date fecha);

	void guardarEscolaridad(Escolaridad escolaridad);

	void guardarInforme(Informe informe);

	void guardarIntervencion(Intervencion intervencion);
}

package snya.reina.datos.estadistica;

import java.util.Date;
import java.util.List;

import snya.reina.modelo.ElementoPanelControl;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.estadistica.EstadisticaInforme;
import snya.reina.modelo.estadistica.EstadisticaIntervencion;
import snya.reina.modelo.estadistica.EstadisticaMovimiento;
import snya.reina.modelo.estadistica.EstadisticaPresente;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaBeneficio;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaFormacionLaboral;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaInforme;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaIntervencion;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaMovimiento;
import snya.reina.utilidades.busqueda.estadistica.BuscadorEstadisticaPresente;

public interface EstadisticaDAO {

	List<ElementoPanelControl> listaDatosPanelControl(Date fecha, Integer[] tipos, Integer[] recursos);

	List<JovenSimplificado> listaJovenPanelControl(Date fecha, Integer idRecurso, Integer idGrupo, Integer idCategoria);

	List<EstadisticaPresente> listaEstadisticaPresente(BuscadorEstadisticaPresente busqueda);

	List<EstadisticaMovimiento> listaEstadisticaMovimiento(BuscadorEstadisticaMovimiento busqueda);

	List<EstadisticaIntervencion> listaEstadisticaIntervencion(BuscadorEstadisticaIntervencion busqueda);

	List<EstadisticaInforme> listaEstadisticaInforme(BuscadorEstadisticaInforme busqueda);

	List<EstadisticaFormacionLaboral> listaEstadisticaFormacionLaboral(BuscadorEstadisticaFormacionLaboral busqueda);

	List<EstadisticaBeneficio> listaEstadisticaBeneficio(BuscadorEstadisticaBeneficio busqueda);
}

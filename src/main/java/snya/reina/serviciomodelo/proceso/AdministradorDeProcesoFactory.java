package snya.reina.serviciomodelo.proceso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import snya.reina.datos.movimiento.MotivoMovimientoDAO;
import snya.reina.datos.proceso.TipoDeDetalleDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMedidaEnProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeMomentoProcesalDAO;
import snya.reina.datos.proceso.TipoDeMotivoIntervencionDAO;
import snya.reina.datos.proceso.TipoDeNotaProcesoPenalDAO;
import snya.reina.datos.proceso.TipoDeSituacionProcesalDAO;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.serviciomodelo.movimiento.EscritorNarrativoDeHistoria;

@Component
public class AdministradorDeProcesoFactory {

	@Autowired
	private MotivoMovimientoDAO motivoMovimientoDAO;
	@Autowired
	private TipoDeMotivoIntervencionDAO tipoDeMotivoIntervencionDAO;
	@Autowired
	private TipoDeMomentoProcesalDAO tipoDeMomentoProcesalDAO;
	@Autowired
	private TipoDeMedidaEnProcesoPenalDAO tipoDeMedidaEnProcesoPenalDAO;
	@Autowired
	private TipoDeDetalleDeMedidaEnProcesoPenalDAO tipoDeDetalleDeMedidaEnProcesoPenalDAO;
	@Autowired
	private TipoDeSituacionProcesalDAO tipoDeSituacionProcesalDAO;
	@Autowired
	private TipoDeNotaProcesoPenalDAO tipoDeNotaProcesoPenalDAO;
	@Autowired
	private GuiaDeRecursos recursero;
	

	
	public AdministradorDeProceso crearAdministradorDeProceso() {
		EscritorNarrativoDeHistoria escritor = new EscritorNarrativoDeHistoria();
		return new AdministradorDeProceso(motivoMovimientoDAO, tipoDeMotivoIntervencionDAO, 
				tipoDeMomentoProcesalDAO, tipoDeMedidaEnProcesoPenalDAO, tipoDeDetalleDeMedidaEnProcesoPenalDAO, tipoDeSituacionProcesalDAO, tipoDeNotaProcesoPenalDAO, recursero, escritor);
	}
}
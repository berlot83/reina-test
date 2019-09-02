package snya.reina.servicio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.reina.ReinaException;
import snya.reina.datos.joven.CaratuladorDAO;
import snya.reina.datos.joven.ExpedienteDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.joven.TipoDeEstadoExpedienteDAO;
import snya.reina.modelo.joven.Caratulador;
import snya.reina.modelo.joven.Expediente;
import snya.reina.modelo.joven.Joven;
import snya.reina.serviciomodelo.joven.AdministradorDeExpediente;

@Service
public class ExpedienteServicioImpl {

	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private CaratuladorDAO caratuladorDAO;
	@Autowired	
	private TipoDeEstadoExpedienteDAO tipoDeEstadoExpedienteDAO;
	@Autowired	
	private ExpedienteDAO expedienteDAO;
	
	@Transactional
	public void generarExpediente(Integer idJoven, Long numeroExpediente) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		AdministradorDeExpediente administrador = new AdministradorDeExpediente(caratuladorDAO, tipoDeEstadoExpedienteDAO, expedienteDAO);
		
		administrador.generarExpediente(joven, numeroExpediente);
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void agergarExpedienteExterno(Integer idJoven, Integer idCaratulador, String numeroExpediente) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		AdministradorDeExpediente administrador = new AdministradorDeExpediente(caratuladorDAO, tipoDeEstadoExpedienteDAO, expedienteDAO);
		Caratulador caratulador = caratuladorDAO.traerPorId(idCaratulador);
		
		administrador.agergarExpedienteExterno(joven, caratulador, numeroExpediente);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void archivarExpediente(Integer idJoven, Integer id, Date fechaArchivado, String paqueteArchivo) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Expediente expediente = this.traerExpediente(joven, id);
		AdministradorDeExpediente administrador = new AdministradorDeExpediente(caratuladorDAO, tipoDeEstadoExpedienteDAO, expedienteDAO);
		
		administrador.archivarExpediente(joven, expediente, fechaArchivado, paqueteArchivo);
		jovenDAO.actualizar(joven);	
	}

	@Transactional
	public void anularExpediente(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Expediente expediente = this.traerExpediente(joven, id);
		AdministradorDeExpediente administrador = new AdministradorDeExpediente(caratuladorDAO, tipoDeEstadoExpedienteDAO, expedienteDAO);
		
		administrador.anularExpediente(expediente);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void desarchivarExpediente(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Expediente expediente = this.traerExpediente(joven, id);
		AdministradorDeExpediente administrador = new AdministradorDeExpediente(caratuladorDAO, tipoDeEstadoExpedienteDAO, expedienteDAO);
		
		administrador.desarchivarExpediente(expediente);
		jovenDAO.actualizar(joven);
	}
		
	public List<Caratulador> traerCaratuladores() {
		return caratuladorDAO.traerTodosExternos();
	}
	
	private Expediente traerExpediente(Joven joven, Integer id) {
		java.util.Iterator<Expediente> iter = joven.getExpedientes().iterator();
		Expediente expediente = null;
		
		while (iter.hasNext()) {
			Expediente exp = iter.next();
			if (exp.getId().equals(id))
				expediente = exp;
		}
		
		return expediente;
	}
}

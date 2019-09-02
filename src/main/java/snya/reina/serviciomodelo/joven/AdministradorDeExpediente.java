package snya.reina.serviciomodelo.joven;

import java.util.Date;

import snya.reina.ReinaException;
import snya.reina.datos.joven.CaratuladorDAO;
import snya.reina.datos.joven.ExpedienteDAO;
import snya.reina.datos.joven.TipoDeEstadoExpedienteDAO;
import snya.reina.modelo.joven.Caratulador;
import snya.reina.modelo.joven.Expediente;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.joven.TipoDeEstadoExpediente;

public class AdministradorDeExpediente {

	private CaratuladorDAO caratuladorDAO;
	private TipoDeEstadoExpedienteDAO tipoDeEstadoExpedienteDAO;
	private ExpedienteDAO expedienteDAO;
	
	public AdministradorDeExpediente(CaratuladorDAO caratuladorDAO,
			TipoDeEstadoExpedienteDAO tipoDeEstadoExpedienteDAO,
			ExpedienteDAO expedienteDAO) {
		this.caratuladorDAO = caratuladorDAO;
		this.tipoDeEstadoExpedienteDAO = tipoDeEstadoExpedienteDAO;
		this.expedienteDAO= expedienteDAO;
	}
	
	public void generarExpediente(Joven joven, Long numeroExpediente) throws ReinaException {
		Caratulador caratulador = caratuladorDAO.traerPorCaracteristica( Caratulador.CODIGO_SRPJ  ); 
		TipoDeEstadoExpediente estado = tipoDeEstadoExpedienteDAO.traerPorId( TipoDeEstadoExpediente.VIGENTE );
		
		if (joven.getExpedienteIdentificador() != null)		
			throw new ReinaException("El joven ya tiene un expediente activo");
		
		if (expedienteDAO.existeNumeroExpediente(numeroExpediente))
			throw new ReinaException("El numero que ha seleccionado ya se encuentra seleccionado para otro joven");
		
		Expediente expediente = new Expediente(
				caratulador, 
				numeroExpediente,
				estado);
		
		joven.getExpedientes().add(expediente);
		expediente.setJoven(joven);
		joven.setExpedienteIdentificador(expediente);
	}

	public void agergarExpedienteExterno(Joven joven, Caratulador caratulador, String numeroExpediente) throws ReinaException {
		TipoDeEstadoExpediente estado = tipoDeEstadoExpedienteDAO.traerPorId( TipoDeEstadoExpediente.VIGENTE );
		
		if (caratulador.getNombre().equals( Caratulador.CODIGO_SRPJ ))		
			throw new ReinaException("No se puede ingresar un expediente tecnico como si fuese externo");
		
		if (expedienteDAO.existeNumeroExpedienteExterno(caratulador, numeroExpediente))
			throw new ReinaException("El numero que ha seleccionado ya se encuentra seleccionado para otro joven");
		
		Expediente expediente = new Expediente(
				caratulador,
				null,
				numeroExpediente,
				estado);
		
		joven.getExpedientes().add(expediente);
		expediente.setJoven(joven);
	}
	
	public void archivarExpediente(Joven joven, Expediente expediente, Date fechaArchivado, String paqueteArchivo) throws ReinaException {
		TipoDeEstadoExpediente estado = tipoDeEstadoExpedienteDAO.traerPorId( TipoDeEstadoExpediente.ARCHIVADO );
		
		if (!expediente.estaVigente())
			throw new ReinaException("El expediente no esta vigente");
		
		if (joven.ambitoPresente() != null)
			throw new ReinaException("El joven se encuentra presente y por lo tanto no se debe archivar el expediente");

		expediente.cambiarEstado(estado);
		expediente.setFechaArchivado(fechaArchivado);
		expediente.setPaqueteArchivo(paqueteArchivo);
	}

	public void anularExpediente(Expediente expediente) throws ReinaException {
		TipoDeEstadoExpediente estado = tipoDeEstadoExpedienteDAO.traerPorId( TipoDeEstadoExpediente.ANULADO );
		
		if (!expediente.estaVigente())
			throw new ReinaException("El expediente no esta vigente");
		
		expediente.cambiarEstado(estado);
	}
	
	public void desarchivarExpediente(Expediente expediente) throws ReinaException {
		TipoDeEstadoExpediente estado = tipoDeEstadoExpedienteDAO.traerPorId( TipoDeEstadoExpediente.VIGENTE );
		
		if (expediente.estaVigente() || expediente.estaAnulado())
			throw new ReinaException("El expediente esta vigente o anulado no puede desarchivarse");
		
		expediente.cambiarEstado(estado);
		expediente.setFechaArchivado(null);
		expediente.setPaqueteArchivo(null);	
	}
	
	
}

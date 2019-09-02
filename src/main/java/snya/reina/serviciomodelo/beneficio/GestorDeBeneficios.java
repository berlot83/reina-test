package snya.reina.serviciomodelo.beneficio;

import java.util.Date;
import java.util.Iterator;

import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.beneficio.EstadoBeneficioDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.beneficio.BeneficioDelJoven;
import snya.reina.modelo.beneficio.EstadoBeneficio;
import snya.reina.modelo.beneficio.GrupoDeBeneficio;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.joven.Joven;

public class GestorDeBeneficios {

	private JovenDAO jovenDAO;
	private EstadoBeneficioDAO estadoBeneficioDAO;
	
	public GestorDeBeneficios(JovenDAO jovenDAO, EstadoBeneficioDAO estadoBeneficioDAO) {
		this.jovenDAO = jovenDAO;
		this.estadoBeneficioDAO = estadoBeneficioDAO;
	}
	
	
	public void agregarPension(String numeroPension, Date fAltaBeneficio,
			String observacionBeneficio, Joven joven, TipoDeBeneficio tipoDePension) throws ReinaException {
		EstadoBeneficio estadoBeneficio = estadoBeneficioDAO.traerPorId(EstadoBeneficio.ACTIVO);
		
		// valida que no exista otra pension activa
		this.tienePensionActiva(joven);
		
		joven.agregarBeneficio(tipoDePension, numeroPension, fAltaBeneficio, observacionBeneficio, estadoBeneficio, null);
	}

	public void guardarBeneficio(Joven joven, TipoDeBeneficio tipo,	String numero, Date fechaAltaBeneficio,	String observacionBeneficio, EstadoBeneficio estadoBeneficio, Date fechaEntregaTarjeta) throws ReinaException {		
		
		if (!estadoBeneficio.estaVigente())
			throw new ReinaException(ReinaCte.EL_ESTADO_DEL_BENEFICIO_DEBE_SER_VIGENTE);
		
		if (fechaAltaBeneficio == null)
			throw new ReinaException(ReinaCte.FECHA_ALTA_BENEFICIO_NULA);
		
		this.noExisteOtroBeneficioVigente(joven, tipo, fechaAltaBeneficio);
		
		this.noExisteOtroBeneficioVigenteConNro(tipo, numero);
		
		this.tienePensionActiva(joven);
		
		joven.agregarBeneficio(tipo, numero, fechaAltaBeneficio, observacionBeneficio, estadoBeneficio, fechaEntregaTarjeta);
	}
	
	public void modificarBeneficio(Joven joven, BeneficioDelJoven beneficio,
			TipoDeBeneficio tipo, String numero,  Institucion supervisor, EstadoBeneficio estadoBeneficio, Date fechaAltaBeneficio, Date fechaBajaBeneficio,
			String observacionBeneficio, Date fechaEntregaTarjeta) throws ReinaException {
				
		if (fechaAltaBeneficio == null)
			throw new ReinaException(ReinaCte.FECHA_ALTA_BENEFICIO_NULA);
		
		if( estadoBeneficio.getId().equals(EstadoBeneficio.BAJA) && fechaBajaBeneficio == null)
			throw new ReinaException(ReinaCte.FALTA_CONSIGNAR_FECHA_BAJA_BENEFICIO);

		if( fechaBajaBeneficio != null && fechaAltaBeneficio.after(fechaBajaBeneficio))
			throw new ReinaException(ReinaCte.LA_FECHA_BAJA_BENEFICIO_MAYOR_FECHA_INICIO);
		
		this.noExisteOtroBeneficioVigente(joven, beneficio, tipo, fechaAltaBeneficio);
		
		this.noExisteOtroBeneficioVigenteConNro(beneficio, tipo, numero);
		
		this.tienePensionActiva(joven, beneficio, tipo);
		
		
		beneficio.setFechaAlta(fechaAltaBeneficio);
		beneficio.setFechaBaja(fechaBajaBeneficio);
		beneficio.setEstado(estadoBeneficio);
		beneficio.setIdentificador(numero);
		beneficio.setTipo(tipo);
		beneficio.setSupervisor(supervisor);
		beneficio.setObservacion(observacionBeneficio);
		beneficio.setFechaEntregaTarjeta(fechaEntregaTarjeta);
	}
		
	public void modificarBeneficio(Joven joven, BeneficioDelJoven beneficio, EstadoBeneficio estadoBeneficio) throws ReinaException {
		
		this.noExisteOtroBeneficioVigente(joven, beneficio, beneficio.getTipo(), beneficio.getFechaAlta());
		
		this.noExisteOtroBeneficioVigenteConNro(beneficio, beneficio.getTipo(), beneficio.getIdentificador());
		
		this.tienePensionActiva(joven, beneficio, beneficio.getTipo());
		
		beneficio.setEstado(estadoBeneficio);
	}
	
	
	private void tienePensionActiva(Joven joven) throws ReinaException {
		boolean activa = false;
		Iterator<BeneficioDelJoven> it = joven.getBeneficios().iterator();
		while (!activa && it.hasNext()) {
			BeneficioDelJoven beneficio = (BeneficioDelJoven) it.next();
			activa = (beneficio.getFechaBaja() != null && beneficio.getTipo().getGrupo().getId().equals(GrupoDeBeneficio.ID_BENEFICIO_PENSION));
		}
		
		if (activa)
			throw new ReinaException(ReinaCte.TIENE_PENSION_ACTIVA);
	}
	
	private void tienePensionActiva(Joven joven, BeneficioDelJoven benef, TipoDeBeneficio tipo) throws ReinaException {
		
		if (tipo.getGrupo().getId().equals(GrupoDeBeneficio.ID_BENEFICIO_PENSION)) {
			boolean activa = false;
			Iterator<BeneficioDelJoven> it = joven.getBeneficios().iterator();
			while (!activa && it.hasNext()) {
				BeneficioDelJoven beneficio = (BeneficioDelJoven) it.next();
				activa = (beneficio.getFechaBaja() != null && beneficio.getTipo().getGrupo().getId().equals(GrupoDeBeneficio.ID_BENEFICIO_PENSION)) && !benef.getId().equals(beneficio.getId());
			}
			
			if (activa)
				throw new ReinaException(ReinaCte.TIENE_PENSION_ACTIVA);			
		}
	}

	private void noExisteOtroBeneficioVigenteConNro(TipoDeBeneficio tipo, String numero) throws ReinaException {
		this.noExisteOtroBeneficioVigenteConNro(null, tipo, numero);
	}
	
	private void noExisteOtroBeneficioVigenteConNro(BeneficioDelJoven beneficio, TipoDeBeneficio tipo, String numero) throws ReinaException {
		if ( jovenDAO.existeJovenConBeneficioYNumero(beneficio, tipo, numero) )
			throw new ReinaException(ReinaCte.TIENE_PENSION_ACTIVA);
	}

	private void noExisteOtroBeneficioVigente(Joven joven, TipoDeBeneficio tipo, Date fechaAltaBeneficio) throws ReinaException {
		boolean vigente = false;
		Iterator<BeneficioDelJoven> it = joven.getBeneficios().iterator();
		while (!vigente && it.hasNext()) {
			BeneficioDelJoven beneficio = (BeneficioDelJoven) it.next();
			vigente =
					beneficio.getTipo().getId().equals(tipo.getId()) && 
					(beneficio.getFechaAlta().before(fechaAltaBeneficio) || (beneficio.getFechaBaja() != null && beneficio.getFechaBaja().after(fechaAltaBeneficio) ) );
		}
		
		if (vigente)
			throw new ReinaException(ReinaCte.Format(ReinaCte.EXISTE_BENEFICIO_VIGENTE, tipo.getNombre()));
	}
	
	private void noExisteOtroBeneficioVigente(Joven joven, BeneficioDelJoven benef, TipoDeBeneficio tipo, Date fechaAltaBeneficio) throws ReinaException {
		boolean vigente = false;
		Iterator<BeneficioDelJoven> it = joven.getBeneficios().iterator();
		while (!vigente && it.hasNext()) {
			BeneficioDelJoven beneficio = (BeneficioDelJoven) it.next();
			vigente = !benef.getId().equals(beneficio.getId()) &&
					beneficio.getTipo().getId().equals(tipo.getId()) && 
					(beneficio.getFechaAlta().before(fechaAltaBeneficio) || (beneficio.getFechaBaja() != null && beneficio.getFechaBaja().after(fechaAltaBeneficio) ) );
		}
		
		if (vigente)
			throw new ReinaException(ReinaCte.Format(ReinaCte.EXISTE_BENEFICIO_VIGENTE, tipo.getNombre()));
	}
}

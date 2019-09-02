package snya.reina.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.ObraSocialDAO;
import snya.general.datos.TipoDeDocumentoDAO;
import snya.general.datos.TipoDeTelefonoDAO;
import snya.general.modelo.ObraSocial;
import snya.general.modelo.Telefono;
import snya.general.modelo.TipoDeDocumento;
import snya.general.modelo.TipoDeTelefono;
import snya.reina.ReinaException;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.referente.FamiliarDAO;
import snya.reina.datos.referente.TipoDeParentescoDAO;
import snya.reina.modelo.Persona;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.referente.Familiar;
import snya.reina.modelo.referente.TipoDeParentesco;
import snya.reina.serviciomodelo.GuiaTelefonica;

@Service
public class FamiliarServicioImpl {

	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private ObraSocialDAO obraSocialDAO;
	@Autowired
	private TipoDeParentescoDAO tipoDeParentescoDAO;	
	@Autowired
	private TipoDeDocumentoDAO tipoDeDocumentoDAO;	
	@Autowired
	private TipoDeTelefonoDAO tipoDeTelefonoDAO;		
	@Autowired	
	private FamiliarDAO familiarDAO;
	
	@Transactional
	public Familiar agregarFamiliar(Joven joven, Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			Integer idObraSocial, Integer idParentesco) throws ReinaException{
		ObraSocial obraSocial = obraSocialDAO.traerPorId(idObraSocial);
		TipoDeParentesco parentesco = tipoDeParentescoDAO.traerPorId(idParentesco);
		
		return joven.agregarFamiliar(persona, vive, convive, referente, tutor, 
				observacion, obraSocial, parentesco);
	}
	
	@Transactional
	public Familiar agregarFamiliar(Joven joven, Persona persona,
			Integer idParentesco) throws ReinaException {
		TipoDeParentesco parentesco = tipoDeParentescoDAO.traerPorId(idParentesco);
		
		return joven.agregarFamiliar(persona, true, false, false, false, 
				null, null, parentesco);		
	}
	
	@Transactional
	public void modificarFamiliar(Joven joven, Familiar familiar, Boolean tieneDocumento,
			Integer idTipoDeDocumento, String numeroDocumento, Boolean vive, Boolean convive, Boolean referente,
			Boolean tutor, String observacion, Integer idObraSocial, Integer idParentesco) throws ReinaException {
		ObraSocial obraSocial = obraSocialDAO.traerPorId(idObraSocial);
		TipoDeParentesco parentesco = tipoDeParentescoDAO.traerPorId(idParentesco);
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDAO.traerPorId(idTipoDeDocumento);
			
		joven.modificarFamiliar(familiar, tieneDocumento, tipoDeDocumento, numeroDocumento,
				vive, convive, referente, tutor, observacion,
				obraSocial, parentesco);
	}
		
	public List<TipoDeParentesco> traerTipoDeParentescoTodos() {
		return tipoDeParentescoDAO.traerTodos();
	}

	public List<Telefono> traerTelefonosDelFamiliar(Integer idJoven, Integer id) {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Familiar familiar = this.traerFamiliar(joven, id);
				
		return familiar.getTelefonos();
	}

	@Transactional
	public void guardarTelefono(Integer idJoven, Integer id, Integer idTipo, String caracteristica,
			String numero, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Familiar familiar = this.traerFamiliar(joven, id);
		TipoDeTelefono tipoTelefono = tipoDeTelefonoDAO.traerPorId(idTipo);
		
		GuiaTelefonica guia = new GuiaTelefonica();
		Telefono telefono = guia.crearTelefono(tipoTelefono, caracteristica, numero, observacion);
		familiar.agregarTelefono(telefono);
		
		familiarDAO.actualizar(familiar);
	}

	@Transactional
	public void eliminarTelefono(Integer idJoven, Integer id, Integer idTelefono) {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Familiar familiar = this.traerFamiliar(joven, id);	
		familiar.eliminarTelefono(idTelefono);
		
		familiarDAO.actualizar(familiar);	
	}
	
	public List<TipoDeTelefono> traerTiposTelefonoTodos() {
		return tipoDeTelefonoDAO.traerTodos();
	}
	
	private Familiar traerFamiliar(Joven joven, Integer id) {
		java.util.Iterator<Familiar> iter = joven.getFamiliares().iterator();
		Familiar familiar = null;

		while (iter.hasNext()) {
			Familiar f = iter.next();
			if (f.getId().equals(id))
				familiar = f;
		}

		return familiar;
	}
}

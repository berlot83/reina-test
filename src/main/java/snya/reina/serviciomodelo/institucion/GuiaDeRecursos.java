package snya.reina.serviciomodelo.institucion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import snya.reina.datos.institucion.CentroDeReferenciaDAO;
import snya.reina.datos.institucion.DefensoriaDAO;
import snya.reina.datos.institucion.FiscaliaDAO;
import snya.reina.datos.institucion.InstitucionDAO;
import snya.reina.datos.institucion.OrganoJudicialDAO;
import snya.reina.modelo.institucion.CentroDeReferencia;
import snya.reina.modelo.institucion.ContactoInstitucion;
import snya.reina.modelo.institucion.Defensoria;
import snya.reina.modelo.institucion.Fiscalia;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.modelo.institucion.TipoDeInstitucion;

@Component
public class GuiaDeRecursos {

	@Autowired
	private InstitucionDAO institucionDAO;
	@Autowired
	private OrganoJudicialDAO organoJudicialDAO;
	@Autowired
	private FiscaliaDAO fiscaliaDAO;
	@Autowired
	private DefensoriaDAO defensoriaDAO;
	@Autowired
	private CentroDeReferenciaDAO centroDeReferenciaDAO;
	
	public OrganoJudicial traerOrganoJudicialPorId(Integer idJuzgado) {
		return organoJudicialDAO.traerPorId(idJuzgado);
	}

	public List<OrganoJudicial> traerOrganoJudicialTodosActivos() {
		return organoJudicialDAO.traerTodosActivos();
	}
	
	public Fiscalia traerFiscalialPorId(Integer idFiscalia) {
		return fiscaliaDAO.traerPorId(idFiscalia);
	}
	
	public List<Fiscalia> traerFiscaliaTodosActivos() {
		return fiscaliaDAO.traerTodosActivos();
	}

	public Defensoria traerDefensoriaPorId(Integer idDefensoria) {		
		Defensoria defensoria = defensoriaDAO.traerPorId(idDefensoria);
		if (defensoria != null)
			if (defensoria.getContactos().size() > 0)
				defensoria.getContactos().get(0);
		
		return defensoria;
	}

	public List<Defensoria> traerDefensoriaTodosActivos() {
		return defensoriaDAO.traerTodosActivos();
	}
	
	public CentroDeReferencia traerCentroDeReferenciaPorId(Integer idCentroDeReferencia) {
		return centroDeReferenciaDAO.traerPorId(idCentroDeReferencia);
	}

	public List<CentroDeReferencia> traerCentroDeReferenciaTodos() {
		return centroDeReferenciaDAO.traerTodos();
	}
	
	public Institucion traerInstitucionPorId(Integer id) {
		return institucionDAO.traerPorIdCompleto(id);
	}
	
	public ContactoInstitucion traerContactoInstitucionDeLaDefensoriaPorId(Integer idDefensoria, Integer id) {
		ContactoInstitucion contacto = null;
		Defensoria def = defensoriaDAO.traerPorId(idDefensoria);
		if (def != null)
			for (ContactoInstitucion con : def.getContactos()) {
				if (con.getId().equals(id))
					contacto = con;
			}
		return contacto;
	}
	
	public List<Institucion> traerInstitucionesVinculadas(Integer idInsittucion, Integer idTipoInstitucion){
		return institucionDAO.traerInstitucionesVinculadas(idInsittucion, idTipoInstitucion);
	}

	public List<Institucion> traerComisariasTodasActivas() {
		return institucionDAO.traerTodosPorTipoActivos(TipoDeInstitucion.ID_COMISARIA);
	}
}

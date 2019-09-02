package snya.reina.servicio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.DepartamentoJudicialDAO;
import snya.general.modelo.DepartamentoJudicial;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.habeas.HabeasDAO;
import snya.reina.datos.habeas.PromotorHabeasDAO;
import snya.reina.datos.institucion.InstitucionDAO;
import snya.reina.datos.institucion.OrganoJudicialDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.habeas.DestinatarioHabeas;
import snya.reina.modelo.habeas.Habeas;
import snya.reina.modelo.habeas.NotaHabeas;
import snya.reina.modelo.habeas.PromotorHabeas;
import snya.reina.modelo.joven.Joven;

@Service
public class HabeasServicioImpl {

	@Autowired
	private HabeasDAO habeaDAO;
	@Autowired
	private OrganoJudicialDAO organoJudicialDAO;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private InstitucionDAO institucionREINADAO;
	@Autowired
	private PromotorHabeasDAO promotorHabeasDAO;
	@Autowired
	private DepartamentoJudicialDAO departamentoJudicialDAO;
	
	@Transactional
	public void crearHabeas(Date fechaDeInicio, Integer idOrganoJudicial, String observacion, 
			Integer organoPromotor, Integer idDepartamentoJudicial, String nombreOtroOrganismo, 
			Integer destinatarioJoven, Integer idJoven, Integer idInstitucion) throws ReinaException {
		OrganoJudicial organoJudicial = organoJudicialDAO.traerPorId(idOrganoJudicial);
		PromotorHabeas promotor = promotorHabeasDAO.traerPorId(organoPromotor);
		DepartamentoJudicial deptoJudPromotor = (idDepartamentoJudicial == null) ? null : departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
		
		DestinatarioHabeas destinatario = null;
		switch (destinatarioJoven) {
			case 1:
				destinatario = new DestinatarioHabeas(jovenDAO.traerPorId(idJoven));
				break;
			case 2:
				destinatario = new DestinatarioHabeas(institucionREINADAO.traerAmbitoPorId(idInstitucion));
				break;			
			case 3:
				destinatario = new DestinatarioHabeas();
				break;			
			default:
				break;
		} 
		
		if (deptoJudPromotor == null && (nombreOtroOrganismo == null || nombreOtroOrganismo.equals("")))
			throw new ReinaException("Debe estar consignado el Deptarmaento Judicial o algun otro organismo como promotor del HABEAS");
			
		Habeas habeas = new Habeas(fechaDeInicio, organoJudicial, destinatario, promotor, deptoJudPromotor, nombreOtroOrganismo, observacion);
		
		habeaDAO.insertar(habeas);
	}
	
	@Transactional
	public void actualizarHabeas(Integer id, Date fechaInicio,
			Integer idOrganoJudicial, String observacion, Integer organoPromotor,
			Integer idDepartamentoJudicial, String organismoPromotor) throws ReinaException {
		OrganoJudicial organoJudicial = organoJudicialDAO.traerPorId(idOrganoJudicial);
		PromotorHabeas promotor = promotorHabeasDAO.traerPorId(organoPromotor);
		DepartamentoJudicial deptoJudPromotor = (idDepartamentoJudicial == null) ? null : departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
		Habeas habeas = habeaDAO.traerPorId(id);
		
		habeas.setFechaInicio(fechaInicio);
		habeas.setOrganoJudicial(organoJudicial);
		habeas.setPromotor(promotor);
		habeas.setDepartamentoJudicialPromotor(deptoJudPromotor);
		habeas.setOrganismoPromotor(organismoPromotor);
		habeas.setObservacion(observacion);
				
		habeaDAO.actualizar(habeas);		
	}
	
	@Transactional
	public void cesarHabeas(Integer id, Date fechaFin, String observacion) throws ReinaException {
		Habeas habeas = habeaDAO.traerPorId(id);
		
		if (habeas.getFechaInicio().after(fechaFin))
			throw new ReinaException(ReinaCte.FECHA_CESE_HABEAS_MENOR_FECHA_IMPOSICION);
		
		habeas.setFechaFin(fechaFin);
		habeas.setObservacion(observacion);
		
		habeaDAO.actualizar(habeas);
	}
	
	@Transactional
	public void agregarNota(Integer id, Date fechaNota, String observacion) throws ReinaException {
		Habeas habeas = habeaDAO.traerPorId(id);
		
		if (habeas.getFechaInicio().after(fechaNota))
			throw new ReinaException(ReinaCte.FECHA_NOTA_MAYOR_FECHA_HABEAS);

		NotaHabeas nota = new NotaHabeas(fechaNota, observacion);
		
		habeas.agregarNota(nota);
		habeaDAO.actualizar(habeas);
	}
	
	public Boolean tieneHABEASActivosElDestinatario(DestinatarioHabeas destinatario) {
		return 
			(destinatario.getJoven() != null) 
				? habeaDAO.tieneHABEASActivosElDestinatario(destinatario.getJoven())
				: habeaDAO.tieneHABEASActivosElDestinatario(destinatario.getInstitucion());
	}
	
	public boolean tieneHABEASActivosElDestinatario(Joven joven) {		
		return habeaDAO.tieneHABEASActivosElDestinatario(joven);
	}
	
	public boolean tieneHABEASActivosElDestinatario(InstitucionCumplimiento instituto) {		
		return habeaDAO.tieneHABEASActivosElDestinatario(instituto);
	}
	
	public Habeas traerPorId(Integer id) {
		return habeaDAO.traerPorId(id);
	}

	public Habeas traerHabeasPorId(Integer id) {
		Habeas habeas =habeaDAO.traerPorId(id);
		habeaDAO.inicializarAlPeresozo(habeas.getNotas());
		
		return habeas;
	}
	
	public List<Habeas> traerHabeasActivos() {
		return habeaDAO.traerHabeasActivos();
	}

	public List<Habeas> traerHabeasHistoricos() {
		return habeaDAO.traerHabeasHistoricos();
	}
	
	public List<Habeas> traerHabeasActivosPorDestinatarioJoven(Integer id) {
		Joven joven = jovenDAO.traerPorId(id);
		
		return habeaDAO.traerHabeasActivosPorDestinatario(joven);
	}
	
	public List<Habeas> traerHabeasActivosPorDestinatarioInstitucion(Integer id) {
		InstitucionCumplimiento destinatario = institucionREINADAO.traerAmbitoPorId(id);
		
		return habeaDAO.traerHabeasActivosPorDestinatario(destinatario);
	}

	public List<PromotorHabeas> traerPromotoresActivos() {
		return promotorHabeasDAO.traerTodosActivos();
	}

	public List<DepartamentoJudicial> traerDepartamentosJudicialesActivos() {
		return departamentoJudicialDAO.traerTodosActivos();
	}
}

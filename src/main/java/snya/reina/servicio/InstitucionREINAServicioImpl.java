package snya.reina.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.BarrioDAO;
import snya.general.datos.DepartamentoJudicialDAO;
import snya.general.datos.LocalidadDAO;
import snya.general.datos.MunicipioDAO;
import snya.general.datos.ProvinciaDAO;
import snya.reina.datos.institucion.InstitucionDAO;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.datos.institucion.TipoContactoInstitucionDAO;
import snya.reina.datos.institucion.TipoDeInstitucionDAO;
import snya.general.datos.TipoDeTelefonoDAO;
import snya.general.modelo.DepartamentoJudicial;
import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.ResultadoConsultaInstitucion;
import snya.reina.modelo.institucion.CentroDeReferencia;
import snya.reina.modelo.institucion.ContactoInstitucion;
import snya.reina.modelo.institucion.Defensoria;
import snya.reina.modelo.institucion.Dependencia;
import snya.reina.modelo.institucion.Fiscalia;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.modelo.institucion.TipoContactoInstitucion;
import snya.general.modelo.Telefono;
import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.recurso.ComponeRecurso;
import snya.reina.modelo.recurso.Recurso;
import snya.general.modelo.TipoDeTelefono;
import snya.reina.ReinaException;
import snya.reina.serviciomodelo.institucion.CreadorDeInstitucion;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.serviciomodelo.institucion.CreadorDeInstitucion.ResultadoConstructor;
import snya.reina.serviciomodelo.GuiaTelefonica;


@Service
public class InstitucionREINAServicioImpl {

	@Autowired
	private InstitucionDAO institucionREINADAO;
	@Autowired
	private TipoDeInstitucionDAO tipoDeInstitucionDAO;
	@Autowired
	private DepartamentoJudicialDAO departamentoJudicialDAO;
	@Autowired
	private ProvinciaDAO provinciaDAO;	
	@Autowired
	private MunicipioDAO municipioDAO;	
	@Autowired
	private LocalidadDAO localidadDAO;
	@Autowired
	private BarrioDAO barrioDAO;
	@Autowired
	private TipoDeTelefonoDAO tipoDeTelefonoDAO;
	@Autowired
	private TipoContactoInstitucionDAO tipoContactoDAO;
	@Autowired
	private RecursoREINADAO recursoDAO;
	@Autowired
	private GuiaDeRecursos recursero;
	
	@Transactional
	public Institucion crearInstitucion(String nombre, String nombreCorto,
			Integer idTipo, String observacion, Integer idDepartamentoJudicial,
			String idSexo, Integer edadDesde, Integer edadHasta,
			Boolean tieneDomicilio, Integer provinciaDomicilio, Integer municipioDomicilio,
			Integer localidadDomicilio, Integer domicilioCodigoPostal,
			Boolean chkDomicilioSinBarrio, Integer barrioDomicilio,
			Boolean chkSinDomicilioExacto, String domicilioCalle,
			String domicilioNumero, String domicilioPiso,
			String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio, Integer idInstitucionPadre, List<Telefono> telefonos, List<ContactoInstitucion> contactos) throws ReinaException {
		TipoDeInstitucion tipo = tipoDeInstitucionDAO.traerPorId(idTipo);
		CreadorDeInstitucion creador = new CreadorDeInstitucion(departamentoJudicialDAO, provinciaDAO, municipioDAO, localidadDAO, barrioDAO);
		Institucion institucionPadre = (idInstitucionPadre != 0 && idInstitucionPadre != null) ? institucionREINADAO.traerPorIdCompleto(idInstitucionPadre) : null;
		
		ResultadoConstructor resInstitucion = creador.crearSegunTipo(tipo, nombre, nombreCorto, observacion,
				idDepartamentoJudicial, idSexo, edadDesde, edadHasta,
				tieneDomicilio, provinciaDomicilio, municipioDomicilio,
				localidadDomicilio, domicilioCodigoPostal,
				chkDomicilioSinBarrio, barrioDomicilio, chkSinDomicilioExacto, domicilioCalle,
				domicilioNumero, domicilioPiso, domicilioTorre, domicilioDepto,
				domicilioEntre1, domicilioEntre2, referenciaDomicilio, institucionPadre, telefonos, contactos);
		resInstitucion.insertar(tipo, institucionREINADAO, recursoDAO);
				
		//TODO ver como dar el resultado
		return resInstitucion.traerInstitucion();
	}
	
	@Transactional
	public void actualizarInstitucion(Integer idInstitucion, String tipoInstitucion, String nombre,
			String nombreCorto, String observacion,
			Integer idDepartamentoJudicial, String idSexo, Integer edadDesde,
			Integer edadHasta, Boolean tieneDomicilio, Integer provinciaDomicilio,
			Integer municipioDomicilio, Integer localidadDomicilio,
			Integer domicilioCodigoPostal, Boolean chkDomicilioSinBarrio,
			Integer barrioDomicilio, Boolean chkSinDomicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio, Integer idInstitucionPadre) throws ReinaException {
		CreadorDeInstitucion creador = new CreadorDeInstitucion(departamentoJudicialDAO, provinciaDAO, municipioDAO, localidadDAO, barrioDAO);		
		Institucion institucion = (tipoInstitucion.toUpperCase() == "I") ? institucionREINADAO.traerPorIdCompleto(idInstitucion) : null;		
		ComponeRecurso componente = (tipoInstitucion.toUpperCase() != "I") ? institucionREINADAO.traerProgramaPorId(idInstitucion) : institucion;
		Institucion institucionPadre = (idInstitucionPadre != null && idInstitucionPadre != 0) ? institucionREINADAO.traerPorIdCompleto(idInstitucionPadre) : null;
		
		
		ResultadoConstructor resInstitucion = creador.modificar(componente, nombre, nombreCorto, observacion,
				idDepartamentoJudicial, idSexo, edadDesde, edadHasta,
				tieneDomicilio, provinciaDomicilio, municipioDomicilio,
				localidadDomicilio, domicilioCodigoPostal,
				chkDomicilioSinBarrio, barrioDomicilio, chkSinDomicilioExacto, domicilioCalle,
				domicilioNumero, domicilioPiso, domicilioTorre, domicilioDepto,
				domicilioEntre1, domicilioEntre2, referenciaDomicilio, institucionPadre);
		resInstitucion.actualizar(institucionREINADAO);
	}
	
	@Transactional
	public void anular(Integer id) {
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(id);
		institucion.setEstaActivo(false);
		
		institucionREINADAO.actualizar(institucion);
	}
	
	@Transactional
	public void guardarTelefono(Integer id, Integer idTipoTelefono,
			String caracteristica, String numero,
			String observacion) throws ReinaException {
		TipoDeTelefono tipoTelefono = tipoDeTelefonoDAO.traerPorId(idTipoTelefono);
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(id);
		
		GuiaTelefonica guia = new GuiaTelefonica();
		Telefono telefono = guia.crearTelefono(tipoTelefono, caracteristica, numero, observacion);
		institucion.agregarTelefono(telefono);
		
		institucionREINADAO.actualizar(institucion);
	}

	@Transactional
	public void eliminarTelefono(Integer id, Integer idTelefono) {
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(id);		
		institucion.eliminarTelefono(idTelefono);
		
		institucionREINADAO.actualizar(institucion);		
	}

	@Transactional
	public void guardarContacto(Integer id, Integer idTipoContacto, String nombreContacto) {
		TipoContactoInstitucion tipoContacto = tipoContactoDAO.traerPorId(idTipoContacto);
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(id);
		
		ContactoInstitucion con = new ContactoInstitucion(tipoContacto, nombreContacto, institucion);
		institucion.agregarContacto(con);
		
		institucionREINADAO.actualizar(institucion);
	}

	@Transactional
	public void eliminarContacto(Integer id, Integer idContacto) {
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(id);		
		institucion.eliminarContacto(idContacto);
		
		institucionREINADAO.actualizar(institucion);
	}
	
	
	@Transactional
	public void anularPrograma(Integer id) throws ReinaException {
		ProgramaSimplificado programa = institucionREINADAO.traerProgramaPorId(id);
		programa.setEstaActivo(false);
		
		institucionREINADAO.actualizarPrograma(programa);
	}
	
	@Transactional
	public void guardarProgramaTelefono(Integer id, Integer idTipoTelefono,
			String caracteristica, String numero,
			String observacion) throws ReinaException {
		TipoDeTelefono tipoTelefono = tipoDeTelefonoDAO.traerPorId(idTipoTelefono);
		ProgramaSimplificado programa = institucionREINADAO.traerProgramaPorId(id);
		
		GuiaTelefonica guia = new GuiaTelefonica();
		Telefono telefono = guia.crearTelefono(tipoTelefono, caracteristica, numero, observacion);
		programa.agregarTelefono(telefono);
		
		institucionREINADAO.actualizarPrograma(programa);
	}

	@Transactional
	public void eliminarProgramaTelefono(Integer id, Integer idTelefono) throws ReinaException {
		ProgramaSimplificado programa = institucionREINADAO.traerProgramaPorId(id);	
		programa.eliminarTelefono(idTelefono);
		
		institucionREINADAO.actualizarPrograma(programa);		
	}
	
	
	
	public List<ResultadoConsultaInstitucion> traerPorNombre(String nombre, String propiedad, String orden) {
		return institucionREINADAO.traerPorNombre(nombre, propiedad, orden);
	}
	

	public Institucion traerPorIdCompleto(Integer id) {
		return institucionREINADAO.traerPorIdCompleto(id);
	}
	
	public ProgramaSimplificado traerProgramaPorIdCompleto(Integer id) {
		return institucionREINADAO.traerProgramaPorId(id);
	}
	
	public List<Institucion> traerInstitucionPadreTodos() {
		return institucionREINADAO.traerPosibleInstitucionPadreTodos();
	}
	
	public List<InstitucionCumplimiento> traerInstitucionCumplimientoTodos() {
		return this.institucionREINADAO.traerInstitucionCumplimientoTodas();
	}

	public List<InstitucionCumplimiento> traerInstitucionCumplimientoTodasActivas() {
		return this.institucionREINADAO.traerInstitucionCumplimientoTodasActivas();
	}

	public List<Recurso> traerRecursoInstitucionCumplimientoTodasActivas() {
		List<Recurso> recursos = this.recursoDAO.traerInstitucionesCumpliminetoTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}

	private void iniciarRecurso(List<Recurso> recursos) {
		for (Recurso recurso : recursos) {
			recurso.getComponente().getNombre();
		}
	}
	
	public List<Recurso> traerCentrosContencionTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerCentrosContencionTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}
	
	public List<Recurso> traerCentrosRecepcionTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerCentrosRecepcionTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}

	public List<Recurso> traerCentrosCerradosTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerCentrosCerradosTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}

	public List<Recurso> traerCentrosAdmisionTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerCentrosAdmisionTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}	
	
	public List<Recurso> traerCentrosDeSaludTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerCentrosDeSaludTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}
	
	public List<Recurso> traerInstitucionesTerciarizadasTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerInstitucionesTerciarizadasTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}

	public List<Recurso> traerInstitucionesExtraJuridireccionalesTodosActivos() {
		List<Recurso> recursos = this.recursoDAO.traerInstitucionesExtraJuridireccionalesTodosActivos();
		iniciarRecurso(recursos);
		
		return recursos;
	}

	public List<Recurso> traerUnidadesPenitenciariasTodos() {
		List<Recurso> recursos = this.recursoDAO.traerUnidadesPenitenciariasTodos();
		iniciarRecurso(recursos);
		
		return recursos;
	}

	public List<Recurso> traerInstitucionesSPPDTodos() {
		List<Recurso> recursos = this.recursoDAO.traerInstitucionesSPPDTodos();
		iniciarRecurso(recursos);
		
		return recursos;
	}
	
	public List<Recurso> traerCentroDeReferenciaTodos() {
		List<Recurso> recursos = this.recursoDAO.traerCentroDeReferenciaTodos();
		iniciarRecurso(recursos);
		
		return recursos;
	}
	
	public List<Dependencia> traerDependenciasTodasActivas() {
		return institucionREINADAO.traerDependenciasTodasActivas();
	}
		
	public InstitucionCumplimiento traerAmbitoPorId(Integer id) {
		return this.institucionREINADAO.traerAmbitoPorId(id);
	}

	public List<OrganoJudicial> traerOrganoJudicialTodosActivos() {
		return recursero.traerOrganoJudicialTodosActivos();
	}

	public List<Fiscalia> traerFiscaliaTodosActivos() {
		return recursero.traerFiscaliaTodosActivos();
	}

	public List<Defensoria> traerDefensoriaTodosActivos() {
		return recursero.traerDefensoriaTodosActivos();
	}

	public Defensoria traerDefensoriaPorId(int idDefensoria) {
		return recursero.traerDefensoriaPorId(idDefensoria);
	}

	public Institucion traerPorId(Integer id) {
		return recursero.traerInstitucionPorId(id);
	}

	public List<CentroDeReferencia> traerInstitucionCentroDeReferenciaTodos() {
		return recursero.traerCentroDeReferenciaTodos();
	}	
	
	public List<TipoDeInstitucion> traerTiposInstitucionActivos() {
		return tipoDeInstitucionDAO.traerTodos();
	}

	public List<DepartamentoJudicial> traerDepartamentosJudiciales() {
		return departamentoJudicialDAO.traerTodos();
	}
	
	public TipoDeTelefono traetTipoTelefonoPoirId(Integer id) {
		return tipoDeTelefonoDAO.traerPorId(id);
	}
	
	public List<TipoDeTelefono> traerTiposTelefonoTodos() {
		return tipoDeTelefonoDAO.traerTodos();
	}

	public TipoContactoInstitucion traetTipoContactoPorId(Integer id) {
		return tipoContactoDAO.traerPorId(id);

	}
	
	public List<TipoContactoInstitucion> traerTiposContactoTodos() {
		return tipoContactoDAO.traerTodos();	
	}

	public List<Institucion> traerHijosDe(Institucion institucion) {
		// TODO Auto-generated method stub
		return null;
	}
		
	public Recurso traerPor(ComponeRecurso componente) {
		Recurso recurso = recursoDAO.traerPor(componente);
		if(recurso != null) recurso.getComponente().getNombre();
		return recurso;
	}
	
	public Recurso traerPorCompleto(ComponeRecurso componente) {
		Recurso r =  recursoDAO.traerPor(componente);
		recursoDAO.inicializarAlPeresozo(r.getComponente().getTelefonos());
		
		return r;
	}

	public List<Institucion> traerComisariasTodasActivas() {
		return recursero.traerComisariasTodasActivas();
	}
}

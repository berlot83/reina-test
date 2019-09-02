package snya.reina.serviciomodelo.institucion;

import java.util.List;

import snya.general.datos.BarrioDAO;
import snya.general.datos.DepartamentoJudicialDAO;
import snya.general.datos.LocalidadDAO;
import snya.general.datos.MunicipioDAO;
import snya.general.datos.ProvinciaDAO;
import snya.reina.ReinaException;
import snya.reina.datos.institucion.InstitucionDAO;
import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.institucion.*;
import snya.reina.modelo.recurso.ComponeRecurso;
import snya.reina.modelo.recurso.Recurso;
import snya.reina.serviciomodelo.CreadorDeDomicilio;
import snya.general.modelo.DepartamentoJudicial;
import snya.general.modelo.Domicilio;
import snya.general.modelo.Telefono;

public class CreadorDeInstitucion {

	private	DepartamentoJudicialDAO departamentoJudicialDAO;
	private ProvinciaDAO provinciaDAO;	
	private MunicipioDAO municipioDAO;	
	private LocalidadDAO localidadDAO;
	private BarrioDAO barrioDAO;
		
	
	public CreadorDeInstitucion(
			DepartamentoJudicialDAO departamentoJudicialDAO,
			ProvinciaDAO provinciaDAO, MunicipioDAO municipioDAO,
			LocalidadDAO localidadDAO, BarrioDAO barrioDAO) {
		super();
		this.departamentoJudicialDAO = departamentoJudicialDAO;
		this.provinciaDAO = provinciaDAO;
		this.municipioDAO = municipioDAO;
		this.localidadDAO = localidadDAO;
		this.barrioDAO = barrioDAO;
	}

	
	public ResultadoConstructor crearSegunTipo(TipoDeInstitucion tipo, String nombre,
			String nombreCorto, String observacion,
			Integer idDepartamentoJudicial, String sexo, Integer edadDesde,
			Integer edadHasta, Boolean tieneDomicilio, Integer provinciaDomicilio,
			Integer municipioDomicilio, Integer localidadDomicilio,
			Integer domicilioCodigoPostal, Boolean chkDomicilioSinBarrio,
			Integer barrioDomicilio, Boolean chkSinDomicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio, Institucion institucionPadre, List<Telefono> telefonos, List<ContactoInstitucion> contactos) {
		ResultadoConstructor resultado = null;

		if (tipo.getId().equals(TipoDeInstitucion.ID_SECRETARIA) || tipo.getId().equals(TipoDeInstitucion.ID_SUBSECRETARIA) ||
				tipo.getId().equals(TipoDeInstitucion.ID_DCIONPROVINCIAL) || tipo.getId().equals(TipoDeInstitucion.ID_DIRECCION) ||
				tipo.getId().equals(TipoDeInstitucion.ID_DEPARTAMENTO) || tipo.getId().equals(TipoDeInstitucion.ID_ONG) ||
				tipo.getId().equals(TipoDeInstitucion.ID_MUNICIPIO) || tipo.getId().equals(TipoDeInstitucion.ID_PROGRAMA) ||
				 tipo.getId().equals(TipoDeInstitucion.ID_SERVICIOSZONAL) )
			resultado = new ResultadoConstructor( new Dependencia(nombre, nombreCorto, tipo, institucionPadre, observacion) );			
		// Ambito Cumplimiento
		if (tipo.getId().equals(TipoDeInstitucion.ID_CONTENCION) || tipo.getId().equals(TipoDeInstitucion.ID_CERRADO) || tipo.getId().equals(TipoDeInstitucion.ID_RECEPCION))
			resultado = new ResultadoConstructor( new InstitucionCumplimiento(nombre, nombreCorto, tipo, institucionPadre, observacion, sexo, edadDesde, edadHasta) );
		//Centro de Referencia
		if (tipo.getId().equals(TipoDeInstitucion.ID_REFERENCIA)) {
			DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
			resultado = new ResultadoConstructor( new CentroDeReferencia(nombre, nombreCorto, tipo, institucionPadre, observacion, departamento) );
		}
		// Juzgados
		if (tipo.getId().equals(TipoDeInstitucion.ID_JRPJ) || tipo.getId().equals(TipoDeInstitucion.ID_JGJ)) {
			DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
			resultado = new ResultadoConstructor( new OrganoJudicial(nombre, nombreCorto, tipo, institucionPadre,  observacion, departamento) );
		}
		// Fiscalia
		if (tipo.getId().equals(TipoDeInstitucion.ID_FISCALIA)) {
			DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
			resultado = new ResultadoConstructor( new Fiscalia(nombre, nombreCorto, tipo, institucionPadre,  observacion, departamento) );
		}
		// Defensoria
		if (tipo.getId().equals(TipoDeInstitucion.ID_DEFENSORIA)) {
			DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
			resultado = new ResultadoConstructor( new Defensoria(nombre, nombreCorto, tipo, institucionPadre,  observacion, departamento) );
		}		
		// Unidad Penitenciaria
		if (tipo.getId().equals(TipoDeInstitucion.ID_UNIDADPENITENCIARIA))
			resultado = new ResultadoConstructor( new UnidadPenitenciaria(nombre, nombreCorto, tipo, institucionPadre,  observacion) );
		// Centro de Salud
		if (tipo.getId().equals(TipoDeInstitucion.ID_CENTROSALUD))
			resultado = new ResultadoConstructor( new CentroDeSalud(nombre, nombreCorto, tipo, institucionPadre,  observacion) );
		// Hogar Oficial - Centro de dia - CLinica Psiq - Comunidad Terapeutica - Hogares conveniados - Casa de Abrigo - Hogar de discapacitados - Centro de dia salud	
		if (tipo.getId().equals(TipoDeInstitucion.ID_CENTRODEDIA) || 
			tipo.getId().equals(TipoDeInstitucion.ID_CLINICA) || tipo.getId().equals(TipoDeInstitucion.ID_COMUNIDAD) ||
			tipo.getId().equals(TipoDeInstitucion.ID_HOGAR) || tipo.getId().equals(TipoDeInstitucion.ID_CASADEABRIGO)  ||
			tipo.getId().equals(TipoDeInstitucion.ID_HOGARDEDISCAPACITADOS) || tipo.getId().equals(TipoDeInstitucion.ID_CENTRODEDIASALUD) )
			resultado = new ResultadoConstructor( new ProgramaSimplificado(nombre, nombreCorto, tipo, institucionPadre) );		
		// Hogar Oficial - Casa de Abrigo	
		if ( tipo.getId().equals(TipoDeInstitucion.ID_HOGAROFICIAL) || tipo.getId().equals(TipoDeInstitucion.ID_CASADEABRIGO) ) {
			resultado = new ResultadoConstructor( new InstitucionCumplimientoSPPD(nombre, nombreCorto, tipo, institucionPadre, observacion, sexo, edadDesde, edadHasta) );
		}
					
		Institucion institucion = resultado.traerInstitucion();		
		if (institucion != null) {
			if (tieneDomicilio) {
				Domicilio domicilio = this.crearDomicilio(provinciaDomicilio, municipioDomicilio, localidadDomicilio,
						domicilioCodigoPostal, chkDomicilioSinBarrio,
						barrioDomicilio, chkSinDomicilioExacto,
						domicilioCalle, domicilioNumero,
						domicilioPiso, domicilioTorre, domicilioDepto,
						domicilioEntre1, domicilioEntre2, referenciaDomicilio);
				institucion.setDomicilio(domicilio);
			}
			for (Telefono telef : telefonos) {
				Telefono tel = new Telefono(telef.getTipoDeTelefono(), telef.getCaracteristica(), telef.getNumero(), telef.getNumero());
				institucion.agregarTelefono(tel);
			}
			
			
			for (ContactoInstitucion contacto : contactos) {
				ContactoInstitucion con = new ContactoInstitucion(contacto.getCargo(), contacto.getNombre(), institucion);
				institucion.agregarContacto(con);
			}
		} else {
			ProgramaSimplificado programa = resultado.traerPrograma();
			Domicilio domicilio = this.crearDomicilio(provinciaDomicilio, municipioDomicilio, localidadDomicilio,
					domicilioCodigoPostal, chkDomicilioSinBarrio,
					barrioDomicilio, chkSinDomicilioExacto,
					domicilioCalle, domicilioNumero,
					domicilioPiso, domicilioTorre, domicilioDepto,
					domicilioEntre1, domicilioEntre2, referenciaDomicilio);
			programa.setDomicilio(domicilio);
		}
		
		
		return resultado;
	}

	
	public ResultadoConstructor modificar(ComponeRecurso componente,
			String nombre, String nombreCorto, String observacion,
			Integer idDepartamentoJudicial, String idSexo, Integer edadDesde,
			Integer edadHasta, Boolean tieneDomicilio, Integer provinciaDomicilio,
			Integer municipioDomicilio, Integer localidadDomicilio,
			Integer domicilioCodigoPostal, Boolean chkDomicilioSinBarrio,
			Integer barrioDomicilio, Boolean chkSinDomicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio, Institucion institucionPadre) {
		ResultadoConstructor resultado = new ResultadoConstructor(componente);
		Institucion institucion = resultado.traerInstitucion();
				
		if (institucion != null) {
			TipoDeInstitucion tipo = institucion.getTipoDeInstitucion();
			
			institucion.setNombre(nombre);
			institucion.setNombreCorto(nombreCorto);
			institucion.setObservacion(observacion);
			institucion.setPadre(institucionPadre);
			
			if (tieneDomicilio) {
				if (institucion.getDomicilio() == null) {
					Domicilio domicilio = this.crearDomicilio(provinciaDomicilio, municipioDomicilio, localidadDomicilio,
							domicilioCodigoPostal, chkDomicilioSinBarrio,
							barrioDomicilio, chkSinDomicilioExacto,
							domicilioCalle, domicilioNumero,
							domicilioPiso, domicilioTorre, domicilioDepto,
							domicilioEntre1, domicilioEntre2, referenciaDomicilio);
					institucion.setDomicilio(domicilio);
				} else			
					this.modificarDomicilio(institucion, provinciaDomicilio, municipioDomicilio, localidadDomicilio,
							domicilioCodigoPostal, chkDomicilioSinBarrio,
							barrioDomicilio, chkSinDomicilioExacto,
							domicilioCalle, domicilioNumero,
							domicilioPiso, domicilioTorre, domicilioDepto,
							domicilioEntre1, domicilioEntre2, referenciaDomicilio);
			} else 
				institucion.setDomicilio(null);
				
			// Ambito Cumplimiento
			if (tipo.getId().equals(TipoDeInstitucion.ID_CONTENCION) || tipo.getId().equals(TipoDeInstitucion.ID_CERRADO) || tipo.getId().equals(TipoDeInstitucion.ID_RECEPCION)) {
				((InstitucionCumplimiento) institucion).setSexo(idSexo);
				((InstitucionCumplimiento) institucion).setEdadMinima(edadDesde);
				((InstitucionCumplimiento) institucion).setEdadMaxima(edadHasta);
			}
			//Centro de Referencia
			if (tipo.getId().equals(TipoDeInstitucion.ID_REFERENCIA)) {
				DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
				((CentroDeReferencia) institucion).setDepartamentoJudicial(departamento);
			}
			// Juzgados
			if (tipo.getId().equals(TipoDeInstitucion.ID_JRPJ) || tipo.getId().equals(TipoDeInstitucion.ID_JGJ)) {
				DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
				((OrganoJudicial) institucion).setDepartamentoJudicial(departamento);
			}
			// Fiscalia
			if (tipo.getId().equals(TipoDeInstitucion.ID_FISCALIA)) {
				DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
				((Fiscalia) institucion).setDepartamentoJudicial(departamento);
			}
			// Defensoria
			if (tipo.getId().equals(TipoDeInstitucion.ID_DEFENSORIA)) {
				DepartamentoJudicial departamento = departamentoJudicialDAO.traerPorId(idDepartamentoJudicial);
				((Defensoria) institucion).setDepartamentoJudicial(departamento);
			}
			// Hogar || Casa de abrigo
			if ( tipo.getId().equals(TipoDeInstitucion.ID_HOGAROFICIAL) || tipo.getId().equals(TipoDeInstitucion.ID_CASADEABRIGO) ) {
				((InstitucionCumplimientoSPPD) institucion).setSexo(idSexo);
				((InstitucionCumplimientoSPPD) institucion).setEdadMinima(edadDesde);
				((InstitucionCumplimientoSPPD) institucion).setEdadMaxima(edadHasta);
			}

			return new ResultadoConstructor(institucion);
		} else {
			ProgramaSimplificado programa = (ProgramaSimplificado) componente;
			
			programa.setNombre(nombre);
			programa.setNombreCorto(nombreCorto);				
			programa.setInstitucion(institucionPadre);
			
			if (tieneDomicilio) {			
				if (programa.getDomicilio() == null) {
					Domicilio domicilio = this.crearDomicilio(provinciaDomicilio, municipioDomicilio, localidadDomicilio,
							domicilioCodigoPostal, chkDomicilioSinBarrio,
							barrioDomicilio, chkSinDomicilioExacto,
							domicilioCalle, domicilioNumero,
							domicilioPiso, domicilioTorre, domicilioDepto,
							domicilioEntre1, domicilioEntre2, referenciaDomicilio);
					programa.setDomicilio(domicilio);
				} else			
					this.modificarDomicilio(programa, provinciaDomicilio, municipioDomicilio, localidadDomicilio,
							domicilioCodigoPostal, chkDomicilioSinBarrio,
							barrioDomicilio, chkSinDomicilioExacto,
							domicilioCalle, domicilioNumero,
							domicilioPiso, domicilioTorre, domicilioDepto,
							domicilioEntre1, domicilioEntre2, referenciaDomicilio);
			} else 
				programa.setDomicilio(null);			
			
			return new ResultadoConstructor(componente);
		}
	}
	

	private Domicilio crearDomicilio(Integer provinciaDomicilio, Integer municipioDomicilio,
			Integer localidadDomicilio, Integer domicilioCodigoPostal,
			Boolean chkDomicilioSinBarrio, Integer barrioDomicilio,
			Boolean chkSinDomicilioExacto, String domicilioCalle,
			String domicilioNumero, String domicilioPiso,
			String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio) {
		CreadorDeDomicilio creadorDom = new CreadorDeDomicilio(provinciaDAO, municipioDAO, localidadDAO, barrioDAO);
		return creadorDom.crear(provinciaDomicilio, municipioDomicilio, localidadDomicilio, domicilioCodigoPostal, 
				chkDomicilioSinBarrio, barrioDomicilio, chkSinDomicilioExacto, domicilioCalle, domicilioNumero, 
				domicilioPiso, domicilioTorre, domicilioDepto, domicilioEntre1, domicilioEntre2, referenciaDomicilio);
	}

	private void modificarDomicilio(ComponeRecurso institucion,
			Integer provinciaDomicilio, Integer municipioDomicilio,
			Integer localidadDomicilio, Integer domicilioCodigoPostal,
			Boolean chkDomicilioSinBarrio, Integer barrioDomicilio,
			Boolean chkSinDomicilioExacto, String domicilioCalle,
			String domicilioNumero, String domicilioPiso,
			String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio) {
		CreadorDeDomicilio creadorDom = new CreadorDeDomicilio(provinciaDAO, municipioDAO, localidadDAO, barrioDAO);
		creadorDom.modificar(institucion.getDomicilio(), provinciaDomicilio, municipioDomicilio, localidadDomicilio, domicilioCodigoPostal, 
				chkDomicilioSinBarrio, barrioDomicilio, chkSinDomicilioExacto, domicilioCalle, domicilioNumero, 
				domicilioPiso, domicilioTorre, domicilioDepto, domicilioEntre1, domicilioEntre2, referenciaDomicilio);

	}
	
	public class ResultadoConstructor {
		private ComponeRecurso resultado;
		
		public ResultadoConstructor(ComponeRecurso resultado) {
			this.resultado = resultado;
		}
		
		public ProgramaSimplificado traerPrograma() {
			 if (this.resultado instanceof ProgramaSimplificado) {
				return (ProgramaSimplificado) this.resultado;
			} else
				return null;
		}

		public Institucion traerInstitucion() {
			 if (this.resultado instanceof Institucion) {
				return (Institucion) this.resultado;
			} else
				return null;
		}

		public void insertar(TipoDeInstitucion tipo, InstitucionDAO institucionREINADAO, RecursoREINADAO recursoDAO) throws ReinaException {
			
			Institucion institucion = this.traerInstitucion();
			if (institucion != null)
				institucionREINADAO.insertar(institucion);
			else
				institucionREINADAO.insertarPrograma((ProgramaSimplificado) this.resultado);
			
			if (
					tipo.getId().equals(TipoDeInstitucion.ID_REFERENCIA) || 
					tipo.getId().equals(TipoDeInstitucion.ID_CONTENCION) ||
					tipo.getId().equals(TipoDeInstitucion.ID_RECEPCION) ||
					tipo.getId().equals(TipoDeInstitucion.ID_CERRADO) ||
					tipo.getId().equals(TipoDeInstitucion.ID_CENTROSALUD) ||
					tipo.getId().equals(TipoDeInstitucion.ID_UNIDADPENITENCIARIA) || 
					tipo.getId().equals(TipoDeInstitucion.ID_HOGAROFICIAL) || tipo.getId().equals(TipoDeInstitucion.ID_CENTRODEDIA) || 
					tipo.getId().equals(TipoDeInstitucion.ID_CLINICA) || tipo.getId().equals(TipoDeInstitucion.ID_COMUNIDAD) ||
					tipo.getId().equals(TipoDeInstitucion.ID_HOGAR) || tipo.getId().equals(TipoDeInstitucion.ID_CASADEABRIGO)  ||
					tipo.getId().equals(TipoDeInstitucion.ID_HOGARDEDISCAPACITADOS) || tipo.getId().equals(TipoDeInstitucion.ID_CENTRODEDIASALUD)
				) {
				Recurso recurso = new Recurso();
				recurso.setComponente(this.resultado);
				recursoDAO.insertar(recurso);
			}
		}

		public void actualizar(InstitucionDAO institucionREINADAO) throws ReinaException {
			
			Institucion institucion = this.traerInstitucion();
			if (institucion != null)
				institucionREINADAO.actualizar(institucion);
			else
				institucionREINADAO.actualizarPrograma((ProgramaSimplificado) this.resultado);
		}
	}
}

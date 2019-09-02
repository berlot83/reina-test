package snya.reina.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.general.datos.BarrioDAO;
import snya.general.datos.EstadoCivilDAO;
import snya.general.datos.LocalidadDAO;
import snya.general.datos.MunicipioDAO;
import snya.general.datos.NacionalidadDAO;
import snya.general.datos.ProvinciaDAO;
import snya.general.datos.TipoDeDocumentoDAO;
import snya.general.datos.TipoDeTrabajoDAO;
import snya.general.modelo.Barrio;
import snya.general.modelo.EstadoCivil;
import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.Provincia;
import snya.general.modelo.TipoDeDocumento;
import snya.general.modelo.TipoDeTrabajo;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.joven.PersonaDAO;
import snya.reina.modelo.Persona;
import snya.reina.serviciomodelo.joven.RegistroDePersona;
import snya.reina.serviciomodelo.resultado.ResultadoPersona;

@Service
public class PersonaServicioImpl {

	@Autowired
	private PersonaDAO personaDAO;
	@Autowired
	private TipoDeDocumentoDAO tipoDeDocumentoDAO;
	@Autowired
	private NacionalidadDAO nacionalidadDAO;
	@Autowired
	private EstadoCivilDAO estadoCivilDAO;
	@Autowired
	private TipoDeTrabajoDAO tipoDeTrabajoDAO;
	@Autowired
	private ProvinciaDAO provinciaDAO;	
	@Autowired
	private MunicipioDAO municipioDAO;	
	@Autowired
	private LocalidadDAO localidadDAO;
	@Autowired
	private BarrioDAO barrioDAO;

	public Persona crearPersona(String apellidoMaterno, String apellidos, String nombres,
			Date fechaNacimiento, Integer edad, String sexo, Integer idNacionalidad,
			Integer idEstadoCivil, Integer idProvinciaNacimiento, Integer idMunicipioNacimiento,
			Integer idLocalidadNacimiento, Integer idTipoDeTrabajo, boolean conFechaNacOEdad) throws ReinaException {
		RegistroDePersona creador = new RegistroDePersona();
		ResultadoPersona resultado;
		Nacionalidad nacionalidad = nacionalidadDAO.traerPorId(idNacionalidad);
		EstadoCivil estadoCivil = estadoCivilDAO.traerPorId(idEstadoCivil);
		Provincia provinciaNacimiento = (idProvinciaNacimiento != null) ? provinciaDAO.traerPorId(idProvinciaNacimiento) : null;
		Municipio municipioNacimiento = (idMunicipioNacimiento != null) ? municipioDAO.traerPorId(idMunicipioNacimiento) : null;
		Localidad localidadNacimiento = (idLocalidadNacimiento != null) ? localidadDAO.traerPorId(idLocalidadNacimiento) : null;
		TipoDeTrabajo tipoDeTrabajo = tipoDeTrabajoDAO.traerPorId(idTipoDeTrabajo);
		
		resultado = creador.crear(apellidoMaterno, apellidos, nombres, fechaNacimiento, edad, sexo, 
				nacionalidad, estadoCivil, 
				provinciaNacimiento, municipioNacimiento, localidadNacimiento,
				tipoDeTrabajo, conFechaNacOEdad);
		
		if (resultado.exitoso())
			return resultado.getPersona();
		
		throw new ReinaException(resultado.getMensaje());
	}

	public Persona crearPersona(String apellidoMaterno, String apellidos, String nombres,
			Date fechaNacimiento, Integer edad, String sexo,
			Boolean tieneDocumento, Integer idTipoDocumento, String numeroDocumento,
			Integer idNacionalidad, boolean conFechaNacOEdad) throws ReinaException {
		RegistroDePersona creador = new RegistroDePersona();
		ResultadoPersona resultado;
		Nacionalidad nacionalidad = nacionalidadDAO.traerPorId(idNacionalidad);
		TipoDeDocumento tipoDeDocumento = (tieneDocumento) ? tipoDeDocumentoDAO.traerPorId(idTipoDocumento) : null;
		
		resultado = creador.crear(apellidoMaterno, apellidos, nombres, fechaNacimiento, edad, sexo, 
				tieneDocumento, tipoDeDocumento, numeroDocumento,
				nacionalidad, conFechaNacOEdad);
		
		if (resultado.exitoso())
			return resultado.getPersona();
		
		throw new ReinaException(resultado.getMensaje());		
	}
	
	public void modificarPersona(Persona persona, String apellidoMaterno, String apellidos,
			String nombres, Date fechaNacimiento, Integer edad,
			String sexo, String cuil, Integer idNacionalidad, Integer idEstadoCivil,
			Integer idProvinciaNacimiento, Integer idMunicipioNacimiento, Integer idLocalidadNacimiento,
			Integer idTipoDeTrabajo, boolean conFechaNacOEdad) throws ReinaException{
		// <<declaracion e inicializacion de variables>>
		RegistroDePersona creador = new RegistroDePersona();
		ResultadoPersona resultado;
		Nacionalidad nacionalidad = nacionalidadDAO.traerPorId(idNacionalidad);
		EstadoCivil estadoCivil = estadoCivilDAO.traerPorId(idEstadoCivil);
		Provincia provinciaNacimiento = (idProvinciaNacimiento != null) ? provinciaDAO.traerPorId(idProvinciaNacimiento) : null;
		Municipio municipioNacimiento = (idMunicipioNacimiento != null) ? municipioDAO.traerPorId(idMunicipioNacimiento) : null;
		Localidad localidadNacimiento = (idLocalidadNacimiento != null) ? localidadDAO.traerPorId(idLocalidadNacimiento) : null;
		TipoDeTrabajo tipoDeTrabajo = tipoDeTrabajoDAO.traerPorId(idTipoDeTrabajo);
		
		
		resultado = creador.modificar(persona, apellidoMaterno, apellidos, nombres,
				fechaNacimiento, edad, sexo, cuil, nacionalidad, estadoCivil,
				provinciaNacimiento, municipioNacimiento, localidadNacimiento,
				tipoDeTrabajo, conFechaNacOEdad);
		
		if (!resultado.exitoso())
			throw new ReinaException(resultado.getMensaje());	
	}
	
	public void asociarDocumento(Persona persona, Boolean tieneDocumento, Integer idTipoDocumento,
			String numeroDocumento) {		
		// <<declaracion e inicializacion de variables>>
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDAO.traerPorId(idTipoDocumento);
		
		// <<procesamiento>>
		persona.asociarDocumentacion(tieneDocumento, tipoDeDocumento, numeroDocumento);
	}
	
	public void agregarDomicilio(Persona persona, Integer provinciaDomicilio, Integer municipioDomicilio,
			Integer localidadDomicilio, Integer domicilioCodigoPostal,
			Boolean chkDomicilioSinBarrio, Integer barrioDomicilio,
			Boolean chkSinDomicilioExacto, String domicilioCalle,
			String domicilioNumero, String domicilioPiso,
			String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio) {
		Provincia provincia = (provinciaDomicilio == null) ? null : provinciaDAO.traerPorId(provinciaDomicilio);
		Municipio municipio =  (municipioDomicilio == null) ? null : municipioDAO.traerPorId(municipioDomicilio);
		Localidad localidad =  (localidadDomicilio == null) ? null : localidadDAO.traerPorId(localidadDomicilio);
		Barrio barrio =  null;		
		if (chkDomicilioSinBarrio != null && !chkDomicilioSinBarrio) {
			barrio = barrioDAO.traerPorId(barrioDomicilio);
		}
		boolean domicilioExacto = (chkSinDomicilioExacto != null && !chkSinDomicilioExacto);
		
		persona.agregarDomicilio(domicilioExacto, domicilioCalle, domicilioNumero, domicilioPiso,
				domicilioTorre, domicilioDepto, domicilioEntre1, domicilioEntre2,
				provincia, municipio, localidad, barrio, referenciaDomicilio);
	}
	
	public void modificarDomicilio(Persona persona, Integer provinciaDomicilio, Integer municipioDomicilio,
			Integer localidadDomicilio, Integer codigoPostal,
			Boolean chkDomicilioSinBarrio, Integer barrioDomicilio,
			Boolean chkSinDomicilioExacto, String calle,
			String numero, String piso,
			String torre, String depto,
			String entre1, String entre2,
			String referenciaDomicilio) {
		Provincia provincia = (provinciaDomicilio == null) ? null : provinciaDAO.traerPorId(provinciaDomicilio);
		Municipio municipio =  (municipioDomicilio == null) ? null : municipioDAO.traerPorId(municipioDomicilio);
		Localidad localidad =  (localidadDomicilio == null) ? null : localidadDAO.traerPorId(localidadDomicilio);
		Barrio barrio =  null;		
		if (chkDomicilioSinBarrio != null && !chkDomicilioSinBarrio) {
			barrio = barrioDAO.traerPorId(barrioDomicilio);
		}
		boolean domicilioExacto = (chkSinDomicilioExacto != null && !chkSinDomicilioExacto);
		
		persona.modificarDomicilio(domicilioExacto, calle, numero, piso,
				torre, depto, entre1, entre2,
				provincia, municipio, localidad, barrio, referenciaDomicilio);
	}
	
	public void quitarDomicilio(Persona persona) {
		persona.setDomicilio(null);
	}
	
	public Persona traerPersonaPorId(Integer id) {
		return personaDAO.traerPorId(id);		
	}

	public List<Persona> traerPorNombre(String dato) throws ReinaException {
		// <<declaracion e inicializacion de variables>>
		String apellido;
		String nombre;
		String[] arreglo;
		
		// <<validaciones>>
		// valida que se busque un dato
		if (dato == null || dato.equals(""))
			throw new ReinaException(ReinaCte.BUSCAR_JOVEN_SIN_DATO_APYN);
		
		// <<procesamiento>>
		arreglo = dato.split(",");
		if (arreglo.length >= 1) {
			// busco por nombre y apellido
			apellido = arreglo[0];
			nombre = arreglo[1];
			return personaDAO.traerPorNombre(apellido, nombre);
		} else
			// busco sobre el nombre y sobre el apellido
			return personaDAO.traerPorNombre(dato);
	}

	public List<Persona> traerPorNroDocumento(String dato) throws ReinaException {
		// <<declaracion e inicializacion de variables>>
		Integer nro;
		
		// <<validaciones>>
		// valida que el dato no sea vacio
		if (dato == null || dato.equals(""))
			throw new ReinaException(ReinaCte.BUSCAR_JOVEN_SIN_DATO_NRO);
		// valida que el nro de documento sea un dato numerico
		try {
			nro = Integer.parseInt(dato);
		} catch (Exception e) {
			throw new ReinaException(ReinaCte.BUSCAR_JOVEN_NRO_ERRONEO);
		}
		
		// <<procesamiento>>
		return personaDAO.traerPorNroDocumento(nro);
	}
	
	public List<TipoDeDocumento> traerTipoDeDocumentoTodos() {
		return tipoDeDocumentoDAO.traerTodos();		
	}

	public List<Nacionalidad> traerNacionalidadTodos() {
		return nacionalidadDAO.traerTodos();		
	}
	
	public List<EstadoCivil> traerEstadoCivilTodos() {
		return estadoCivilDAO.traerTodos();
	}
	
	public List<TipoDeTrabajo> traerTipoDeTrabajoTodos() {
		return tipoDeTrabajoDAO.traerTodos();		
	}
}
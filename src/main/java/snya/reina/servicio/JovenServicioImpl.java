package snya.reina.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snya.archivoscliente.ArchivoCliente;
import snya.archivoscliente.modelo.Archivo;
import snya.archivoscliente.modelo.DocumentMetadata;
import snya.archivoscliente.modelo.SubTipoArchivo;
import snya.archivoscliente.modelo.TipoArchivo;
import snya.archivoscliente.modelo.TipoEntidad;
import snya.archivoscliente.modelo.TipoEntidadGeneral;
import snya.general.datos.TipoDeDocumentoDAO;
import snya.general.modelo.TipoDeDocumento;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.institucion.InstitucionDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.datos.joven.TipoDeSituacionTramiteDAO;
import snya.reina.modelo.JovenEscolaridad;
import snya.reina.modelo.JovenParteIntervencionPenal;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.JovenSimplificadoCAD;
import snya.reina.modelo.JovenSituacionIrregular;
import snya.reina.modelo.JovenTratamiento;
import snya.reina.modelo.MovimientoSimplificado;
import snya.reina.modelo.Persona;
import snya.reina.modelo.beneficio.BeneficioDelJoven;
import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;
import snya.reina.modelo.estadistica.EstadisticaJovenInformeIntervencionPenal;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.intervencion.Lineamiento;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.joven.SituacionTramite;
import snya.reina.modelo.joven.TipoDeSituacionTramite;
import snya.reina.modelo.movimiento.Movimiento;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.referente.Familiar;
import snya.reina.repositorios.PersonaRepositorio;
import snya.reina.rest.dto.JovenSimpleDTO;
import snya.reina.serviciomodelo.GenericBuilder;
import snya.reina.serviciomodelo.institucion.GuiaDeRecursos;
import snya.reina.utilidades.busqueda.joven.BusquedaJoven;

@Service
public class JovenServicioImpl {

	/* Provisorio no lo usan al momento Pero funcionan - Axel */
	@Autowired
	PersonaRepositorio jovenRepositorio;
	/* */
	
	@Autowired
	private TipoDeSituacionTramiteDAO tipoDeSituacionDAO;	
	@Autowired
	private TipoDeDocumentoDAO tipoDeDocumentoDAO;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private InstitucionDAO institucionREINADAO;
	@Autowired
	private GuiaDeRecursos recursero;
	@Autowired
	private ArchivoCliente archivoCliente;

	
	public Joven crearJoven(Boolean tieneFichaDactiloscopica, Persona persona) {
		// <<declaracion e inicializacion de variables>>
		Joven joven = new Joven();
		
		// <<procesamiento>>
		joven.setTieneFichaDactiloscopica(tieneFichaDactiloscopica);
		joven.setPersona(persona);
		
		// <<resultado>>
		return joven;
	}
	
	@Transactional	
	public void asentarHuella(Integer id, byte[] fieldData, String nombreArchivo, Date fecha, String descripcion, String tipoDeContenido, String usuario, Integer idSistema, Integer idSector, Integer idRol) throws ReinaException {
				
		TipoEntidadGeneral tipoEntidadGeneral = new TipoEntidadGeneral();
		tipoEntidadGeneral.setId(new Long(2));
		TipoEntidad tipoEntidad = new TipoEntidad();
		tipoEntidad.setId(new Long(2));
		TipoArchivo tipoArchivo = new TipoArchivo();
		tipoArchivo.setId(new Long(4));
		SubTipoArchivo subTipoArchivo = new SubTipoArchivo();
		subTipoArchivo.setId(new Long(8));

		Archivo document = GenericBuilder.of(Archivo::new)
				.with(Archivo::setFileData, fieldData)
				.with(Archivo::setNombreArchivo, nombreArchivo)
				.with(Archivo::setFecha, fecha)
				.with(Archivo::setDescripcion, descripcion)
				.with(Archivo::setTipoContenido, tipoDeContenido)
				.with(Archivo::setTipoEntidadGeneral, tipoEntidadGeneral)
				.with(Archivo::setIdEntidadGeneral, id.toString())
				.with(Archivo::setTipoEntidad, tipoEntidad)
				.with(Archivo::setIdEntidad, id.toString())
				.with(Archivo::setTipoArchivo, tipoArchivo)
				.with(Archivo::setSubTipoArchivo, subTipoArchivo)
				.build();
		DocumentMetadata documentMetadata = this.archivoCliente.guardar(document, usuario, idSistema, idSector, idRol);

		Joven joven = jovenDAO.traerPorId(id);
		joven.setDedo(documentMetadata.getUuid());
		
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional	
	public void recibirLegajo(Integer idJoven, Date fecha, Integer idInstitucion, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(idInstitucion);
		
		if(fecha == null)
			throw new ReinaException(ReinaCte.FECHA_RECIBIR_LEGAJO_NULA);
		
		joven.recibirLegajo(fecha, institucion, observacion);
		
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional	
	public void enviarLegajo(Integer idJoven, Date fecha, Integer idInstitucion, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Institucion institucion = institucionREINADAO.traerPorIdCompleto(idInstitucion);
		
		if(fecha == null)
			throw new ReinaException(ReinaCte.FECHA_RECIBIR_LEGAJO_NULA);
		
		joven.enviarLegajo(fecha, institucion, observacion);
		
		jovenDAO.actualizar(joven);			
	}
	
	public void agregarSituacionDocumentacion(Joven joven, Date fecha,
			Integer idSituacionDocumentacion, Integer idTipoDocumentoPosee,
			String numeroDocumentoPosee, Boolean actualizado, Boolean partidaDeNacimientoPosee, String observacionPosee, Boolean tieneDocumento,
			Integer idTipoDocumentoEnTramite, String numeroDocumentoEnTramite,  Boolean partidaDeNacimientoEnTramite, String observacionEnTramite,
			Boolean certificadoDeParto, Boolean partidaDeNacimiento, String observacionNoPosee, String observacionSeDesconoce) throws ReinaException {
		// <<declaracion e inicializacion de variables>>
		TipoDeSituacionTramite tipo = tipoDeSituacionDAO.traerPorId(idSituacionDocumentacion);
		TipoDeDocumento tipoDeDocumentoPosee = (idTipoDocumentoPosee == null) ? null : tipoDeDocumentoDAO.traerPorId(idTipoDocumentoPosee);
		TipoDeDocumento tipoDeDocumentoEnTramite = ((tieneDocumento == null || !tieneDocumento) && idTipoDocumentoEnTramite == null) ? null : tipoDeDocumentoDAO.traerPorId(idTipoDocumentoEnTramite);
		
		// <<procesamiento>>		
		joven.agregarSituacionTramite(jovenDAO, tipo, fecha, tipoDeDocumentoPosee, numeroDocumentoPosee, actualizado, partidaDeNacimientoPosee, observacionPosee,
				tieneDocumento, tipoDeDocumentoEnTramite, numeroDocumentoEnTramite, partidaDeNacimientoEnTramite, observacionEnTramite, certificadoDeParto, partidaDeNacimiento, 
				observacionNoPosee, observacionSeDesconoce);
		
	}

	@Transactional
	public void agregarSituacionDocumentacion(Integer idJoven, Date fecha,
			Integer idSituacionDocumentacion, Integer idTipoDocumentoPosee,
			String numeroDocumentoPosee, Boolean actualizado, Boolean partidaDeNacimientoPosee, String observacionPosee, Boolean tieneDocumento,
			Integer idTipoDocumentoEnTramite, String numeroDocumentoEnTramite, Boolean partidaDeNacimientoEnTramite, String observacionEnTramite,
			Boolean certificadoDeParto, Boolean partidaDeNacimiento,
			String observacionNoPosee, String observacionSeDesconoce) throws ReinaException {
		// <<declaracion e inicializacion de variables>>
		TipoDeSituacionTramite tipo = tipoDeSituacionDAO.traerPorId(idSituacionDocumentacion);
		TipoDeDocumento tipoDeDocumentoPosee = (idTipoDocumentoPosee == null) ? null : tipoDeDocumentoDAO.traerPorId(idTipoDocumentoPosee);
		TipoDeDocumento tipoDeDocumentoEnTramite = ((tieneDocumento == null || !tieneDocumento) && idTipoDocumentoEnTramite == null) ? null : tipoDeDocumentoDAO.traerPorId(idTipoDocumentoEnTramite);
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		// <<procesamiento>>		
		joven.agregarSituacionTramite(jovenDAO, tipo, fecha, tipoDeDocumentoPosee, numeroDocumentoPosee, actualizado, partidaDeNacimientoPosee, observacionPosee,
				tieneDocumento, tipoDeDocumentoEnTramite, numeroDocumentoEnTramite, partidaDeNacimientoEnTramite, observacionEnTramite, certificadoDeParto, partidaDeNacimiento, 
				observacionNoPosee, observacionSeDesconoce);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional	
	public void modifiarSituacionDocumentacion(Integer idJoven, Integer idSituacion, 
			Integer idSituacionDocumentacion, Integer idTipoDocumentoPosee, String numeroDocumentoPosee, Boolean actualizado, Boolean partidaDeNacimientoPosee, String observacionPosee,
			Boolean tieneDocumento, Integer idTipoDocumentoEnTramite, String numeroDocumentoEnTramite, Boolean partidaDeNacimientoEnTramite, String observacionEnTramite, 
			Boolean certificadoDeParto, Boolean partidaDeNacimiento,
			String observacionNoPosee, String observacionSeDesconoce) throws ReinaException {
		// <<declaracion e inicializacion de variables>>
		TipoDeSituacionTramite tipo = tipoDeSituacionDAO.traerPorId(idSituacionDocumentacion);
		TipoDeDocumento tipoDeDocumentoPosee = (idTipoDocumentoPosee == null) ? null : tipoDeDocumentoDAO.traerPorId(idTipoDocumentoPosee);
		TipoDeDocumento tipoDeDocumentoEnTramite = ((tieneDocumento == null || !tieneDocumento) && idTipoDocumentoEnTramite == null) ? null : tipoDeDocumentoDAO.traerPorId(idTipoDocumentoEnTramite);
		Joven joven = jovenDAO.traerPorId(idJoven);
		SituacionTramite situacion = this.traerSituacionDNI(joven, idSituacion);
		
		// <<procesamiento>>
		tipo.modificarSituacion(situacion, joven.getPersona(), tipoDeDocumentoPosee, numeroDocumentoPosee, actualizado, partidaDeNacimientoPosee, observacionPosee,
				tieneDocumento, tipoDeDocumentoEnTramite, numeroDocumentoEnTramite, partidaDeNacimientoEnTramite, observacionEnTramite,
				certificadoDeParto, partidaDeNacimiento, observacionNoPosee, observacionSeDesconoce);
		
		situacion.noSeRepiteDocumento(joven, jovenDAO);
		
		jovenDAO.actualizar(joven);
	}

	@Transactional	
	public Lineamiento guardarLineamiento(Integer idJoven, String observacion, Integer idInstitucion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Institucion institucion = recursero.traerInstitucionPorId(idInstitucion);
		Lineamiento lineamiento = joven.guardarLineamiento(observacion, institucion);
		
		jovenDAO.actualizar(joven);
		return lineamiento;
	}

	@Transactional	
	public Lineamiento actualizarLineamiento(Integer idJoven, String observacion, Integer idInstitucion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		Lineamiento lineamiento = joven.traerLineamiento(idInstitucion);
		lineamiento.setObservacion(observacion);
		jovenDAO.actualizar(joven);
		
		return lineamiento;
	}
	
	public Lineamiento traerLineamiento(Integer idJoven, Integer idInstitucion) {
		Joven joven = jovenDAO.traerPorId(idJoven);
		
		Lineamiento lineamiento = joven.traerLineamiento(idInstitucion);	
		return lineamiento;
	}
	
	@Transactional	
	public void insertar(Joven joven) throws ReinaException {
		// TODO ver toto el tema de intervencion.
			// <<validaciones>>
			this.validarJoven(joven);
			// <<procesamiento>>
			jovenDAO.insertar(joven);
			// procesar ingreso
			this.procesarIngreso(joven);
	}
	
	@Transactional	
	public void actualizar(Joven joven) throws ReinaException{
		// <<validaciones>>
		this.validarJoven(joven);
		// <<procesamiento>>
		jovenDAO.actualizar(joven);		
	}
	
	@Transactional	
	public void actualizar(Joven joven, Boolean tieneFichaDactiloscopica) throws ReinaException {
		// <<validaciones>>
		this.validarJoven(joven);
		
		joven.setTieneFichaDactiloscopica(tieneFichaDactiloscopica);
		
		//Agrega el domicilio del joven a los familiares que sean Convivientes
		   		for (Familiar f : joven.getFamiliares()) {
					if(f.getConvive()){
						joven.modificarDomicilioFamiliarConviviente(f);
						}	
		   			}
		 	
		// <<procesamiento>>
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void eliminarJoven(Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(id);
		
		if ( (joven.getExpedienteIdentificador() != null && !joven.getExpedienteIdentificador().estaAnulado()) )
			throw new ReinaException(ReinaCte.ELIMINAR_JOVEN_SIN_EXPEDIENTE_ANULADO);
		
		jovenDAO.eliminar(joven);
	}
		
	
	public Joven traerPorId(Integer id) {
		Joven joven = jovenDAO.traerPorId(id);
		jovenDAO.inicializarAlPeresozo(joven.getSituacionesTramiteDocumento());
		jovenDAO.inicializarAlPeresozo(joven.getFamiliares());
		jovenDAO.inicializarAlPeresozo(joven.getProcesos());
		jovenDAO.inicializarAlPeresozo(joven.getIntervencionesSRPJ());
		jovenDAO.inicializarAlPeresozo(joven.getEscolaridades());
		jovenDAO.inicializarAlPeresozo(joven.getCapacitaciones());
		//jovenDAO.inicializarAlPeresozo(joven.getProgramas());
		jovenDAO.inicializarAlPeresozo(joven.getInformes());
		jovenDAO.inicializarAlPeresozo(joven.getLineamientos());
		jovenDAO.inicializarAlPeresozo(joven.getMovimientos());
		for (Movimiento movimiento : joven.getMovimientos()) {
			jovenDAO.inicializarAlPeresozo(movimiento.traerAmbitoEjecucionOrigen());
			if (movimiento.traerAmbitoEjecucionOrigen() != null) movimiento.traerAmbitoEjecucionOrigen().traerNombre();
			jovenDAO.inicializarAlPeresozo(movimiento.traerAmbitoEjecucionDestino());
			if (movimiento.traerAmbitoEjecucionDestino() != null) movimiento.traerAmbitoEjecucionDestino().traerNombre();
		}		
		jovenDAO.inicializarAlPeresozo(joven.getExpedientes());
		jovenDAO.inicializarAlPeresozo(joven.getPermanencias());
		for (Permanencia presente : joven.getPermanencias()) {
			jovenDAO.inicializarAlPeresozo(presente);	
			presente.getAmbitoEjecucion().traerNombre();
		}
		jovenDAO.inicializarAlPeresozo(joven.getBeneficios());
		jovenDAO.inicializarAlPeresozo(joven.getTratamientos());
		jovenDAO.inicializarAlPeresozo(joven.getAdmisiones());
		jovenDAO.inicializarAlPeresozo(joven.getIntervenciones());
		
		return joven;
	}
	
	public List<JovenSimplificado> listaJoven(BusquedaJoven busqueda, String propiedad, String orden) throws ReinaException {
		return jovenDAO.listaJoven(busqueda, propiedad, orden);
	}
	
	//TODO
	public List<JovenSimplificadoCAD> listaJovenCAD(BusquedaJoven busqueda, String propiedad, String orden) throws ReinaException {
		return jovenDAO.listaJovenCAD(busqueda, propiedad, orden);
	}
	//TODO
	
	public List<JovenEscolaridad> listaJovenEscolaridad(BusquedaJoven busqueda) throws ReinaException {
		return jovenDAO.listaJovenEscolaridad(busqueda);
	}	

	public List<JovenParteIntervencionPenal> listaJovenPartePresente(Integer[] tipos, Integer[] destinos, Integer situacion, Integer mes, Integer anio) throws ReinaException {
		return jovenDAO.listaJovenPartePresente(tipos, destinos, situacion, mes, anio);
	}
	
	public List<EstadisticaJovenInformeIntervencionPenal> listaInformesIntervencion(Integer[] tipos, Integer[] destinos, Integer mes, Integer anio) {
		return jovenDAO.listaInformesIntervencion(tipos, destinos, mes, anio);
	}
	
	public List<JovenTratamiento> listaJovenTratamiento(BusquedaJoven busqueda) throws ReinaException {
		return jovenDAO.listaJovenTratamiento(busqueda);
	}
	
	public List<EstadisticaFormacionLaboral> listaJovenFormacionLaboral(BusquedaJoven busqueda, Integer idFormacion, Integer idEstado) throws ReinaException {
		return jovenDAO.listaJovenFormacionLaboral(busqueda, idFormacion, idEstado);
	}
	
	public List<JovenSimplificado> listaJovenesSinExpediente(Integer[] tipos, Integer[] recursos, String campo, String orden) {
		return jovenDAO.listaJovenesSinExpediente(tipos, recursos, campo, orden);
	}
	
	public List<JovenSituacionIrregular> listaJovenesEnSituacionIrregular(
			Integer[] tipos, Integer[] recursos, String campo, String orden) {
		return jovenDAO.listaJovenesEnSituacionIrregular(tipos, recursos, campo, orden);
	}

	public List<JovenSimplificado> listaJovenesPresentes(
			boolean reporte, Integer[] tipos, Integer[] recursos, String campo, String orden) {
		return jovenDAO.listaJovenesPresentes(reporte, tipos, recursos, campo, orden);
	}

	public List<MovimientoSimplificado> listaJovenesConMovimientosPendientes(
			Integer[] tipos, Integer[] recursos, String campo, String orden) {
		return jovenDAO.listaJovenesConMovimientosPendientes(tipos, recursos, campo, orden);
	}
	
	public List<TipoDeSituacionTramite> traerSituacionTramiteDNITodos() {
		return tipoDeSituacionDAO.traerTodos();
	}
	
	public List<TipoDeBeneficio> traerBeneficiosPorIdJoven(Integer id) {
		Joven joven = jovenDAO.traerPorId(id);
		List<TipoDeBeneficio> beneficios = new ArrayList<TipoDeBeneficio>();
		
		for (BeneficioDelJoven benef : joven.getBeneficios()) {
			if (benef.estaVigente())
				beneficios.add(benef.getTipo());
		}
		
		return beneficios;
	}
	
	private void validarJoven(Joven joven) throws ReinaException {
		// valido que el apellido haya sido ingresado
		if (joven.getApellidos() == null || joven.getApellidos().equals(""))
			throw new ReinaException(ReinaCte.APELLIDO_PERSONA_NULO);
		// valido que los nombres hayan sido ingresados
		if (joven.getNombres() == null || joven.getNombres().equals(""))
			throw new ReinaException(ReinaCte.NOMBRE_PERSONA_NULO);
		// valido que la fecha de nacimiento o edad hayan sido ingresados
		if (joven.getFechaNacimiento() == null && joven.getEdad()== null)
			throw new ReinaException(ReinaCte.FECHANACIMIENTO_EDAD_PERSONA_NULO);
		// valido que el sexo sea un dato valido
		if ( !joven.getSexo().equalsIgnoreCase("M") && !joven.getSexo().equals("F") )
			throw new ReinaException(ReinaCte.SEXO_PERSONA_INVALIDO);
		// valido que el joven tenga una situacion de tramite
		if (joven.getSituacionesTramiteDocumento().size() <= 0)
			throw new ReinaException(ReinaCte.Format(ReinaCte.JOVEN_SIN_SITUACION_TRAMITE, joven.getDescripcionJoven()));
	}
	
	private void procesarIngreso(Joven joven) throws ReinaException {
		Permanencia per = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
		if (per != null) {
			per.setIngreso(per.getId());
			jovenDAO.actualizar(joven);
		}
	}
	
	private SituacionTramite traerSituacionDNI(Joven joven, Integer id) {
		java.util.Iterator<SituacionTramite> iter = joven.getSituacionesTramiteDocumento().iterator();
		SituacionTramite sit = null;
		
		while (iter.hasNext()) {
			SituacionTramite s = iter.next();
			if (s.getId().equals(id))
				sit = s;
		}
		
		return sit;
	}
	
	/* Provisorio no lo usan al momento Pero funcionan */
	/* Sin uso al momento */
	/* Busqueda por atributo = 'Nombres' - Buscamos la Persona y luego traemos el Joven y lo simplificamos en el contructor */
	public List<JovenSimplificado> traerPorCriterioDeBusquedaNombres(String buscar) {
		/* Creamos una lista donde vamos a meter los resultados */
		List<JovenSimplificado> listaJovenes = new ArrayList<>();
		/* Buscamos primero las personas que matcheen con el nombre */
		List<Persona> personasNombres = jovenRepositorio.findByNombresContaining(buscar);
		/* Recorremos el resultado */
		Iterator<Persona> it = personasNombres.iterator();
		while (it.hasNext()) {
			/* Capturamos el id de la persona */
			int	idPersona = it.next().getId();
			/* buscamos el joven asociado */
			Joven joven = this.traerPorId(idPersona);
			JovenSimplificado jovenSimplificado = new JovenSimplificado(joven);
			/* Lo agregamos a la lista de resultados */
			listaJovenes.add(jovenSimplificado);
		}
		return listaJovenes;
	}
	
	/* Sin uso al momento */
	/* Busqueda por atributo = 'Apellidos' - Buscamos la Persona y luego traemos el Joven y lo simplificamos en el contructor */
	public List<JovenSimplificado> traerPorCriterioDeBusquedaApellidos(String buscar) {
		/* Creamos una lista donde vamos a meter los resultados */
		List<JovenSimplificado> listaJovenes = new ArrayList<>();
		/* Buscamos primero las personas que matcheen con el nombre */
		List<Persona> personasApellidos = jovenRepositorio.findByApellidosContaining(buscar);
		/* Recorremos el resultado */
		Iterator<Persona> it = personasApellidos.iterator();
		while (it.hasNext()) {
			/* Capturamos el id de la persona */
			int	idPersona = it.next().getId();
			/* buscamos el joven asociado */
			Joven joven = this.traerPorId(idPersona);
			JovenSimplificado jovenSimplificado = new JovenSimplificado(joven);
			/* Lo agregamos a la lista de resultados */
			listaJovenes.add(jovenSimplificado);
		}
		return listaJovenes;
	}
	
	/* Sin uso al momento */
	/* Busqueda por atributo = 'NumeroDocumento' - Buscamos la Persona y luego traemos el Joven y lo simplificamos en el contructor */
	public List<JovenSimplificado> traerPorCriterioDeBusquedaNumeroDocumento(String buscar) {
		/* Creamos una lista donde vamos a meter los resultados */
		List<JovenSimplificado> listaJovenes = new ArrayList<>();
		/* Buscamos primero las personas que matcheen con el nombre */
		List<Persona> personasNumeroDocumento = jovenRepositorio.findByNumeroDocumentoContaining(buscar);
		/* Recorremos el resultado */
		Iterator<Persona> it = personasNumeroDocumento.iterator();
		while (it.hasNext()) {
			/* Capturamos el id de la persona */
			int	idPersona = it.next().getId();
			/* buscamos el joven asociado */
			Joven joven = this.traerPorId(idPersona);
			JovenSimplificado jovenSimplificado = new JovenSimplificado(joven);
			/* Lo agregamos a la lista de resultados */
			listaJovenes.add(jovenSimplificado);
		}
		return listaJovenes;
	}
	
	/* Busqueda por atributos/propiedades múltiples = 'NumeroDocumento, Nombres, Apellidos' */
	public List<JovenSimpleDTO> traerPorCriterioDeBusquedaMixto1(String busquedaMixta) {
		/* Creamos una lista donde vamos a meter los resultados */
		List<JovenSimpleDTO> listaJovenes = new ArrayList<>();
		/* Buscamos primero las personas que matcheen con el nombre */
		List<Persona> personas = jovenRepositorio.findByNombresLikeOrApellidosLikeOrNumeroDocumentoLike("%"+busquedaMixta+"%", "%"+busquedaMixta+"%", "%"+busquedaMixta+"%");
		/* Recorremos el resultado */
		Iterator<Persona> it = personas.iterator();
		while (it.hasNext()) {
			/* Capturamos el id de la persona */
			int	idPersona = it.next().getId();
			/* buscamos el joven asociado */
			Joven joven = this.traerPorId(idPersona);
			JovenSimpleDTO jovenSimplificado = new JovenSimpleDTO(joven);
			/* Lo agregamos a la lista de resultados */
			listaJovenes.add(jovenSimplificado);
		}
		return listaJovenes;
	}
	
	public List<JovenSimpleDTO> traerPorCriterioDeBusquedaMixto2(String numeroDocumento, String apellidos, String nombres) {
		/* Creamos una lista donde vamos a meter los resultados */
		List<JovenSimpleDTO> listaJovenes = new ArrayList<>();
		/* Buscamos primero las personas que matcheen con el nombre */
		List<Persona> personas = jovenRepositorio.findByNumeroDocumentoLikeOrApellidosLikeOrNombresLike("%"+numeroDocumento+"%", "%"+apellidos+"%", "%"+nombres+"%");
		/* Recorremos el resultado */
		Iterator<Persona> it = personas.iterator();
		
		while (it.hasNext()) {
			/* Capturamos el id de la persona */
			int	idPersona = it.next().getId();
			/* buscamos el joven asociado */
			Joven joven = this.traerPorId(idPersona);
			JovenSimpleDTO jovenSimplificado = new JovenSimpleDTO(joven);
			/* Lo agregamos a la lista de resultados */
			listaJovenes.add(jovenSimplificado);
		}
		return listaJovenes;
	}
}

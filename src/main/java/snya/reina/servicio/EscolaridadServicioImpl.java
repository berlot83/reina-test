package snya.reina.servicio;

import java.util.ArrayList;
import java.util.Date;
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
import snya.reina.ReinaException;
import snya.reina.datos.educacion.AnioEscolarDAO;
import snya.reina.datos.educacion.ModalidadEscolarDAO;
import snya.reina.datos.educacion.NivelEscolarDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.educacion.AnioEscolar;
import snya.reina.modelo.educacion.Escolaridad;
import snya.reina.modelo.educacion.ModalidadEscolar;
import snya.reina.modelo.educacion.NivelEscolar;
import snya.reina.modelo.joven.Joven;
import snya.reina.serviciomodelo.GenericBuilder;
import snya.reina.serviciomodelo.escolaridad.ReguladorDeEscolaridad;

@Service
public class EscolaridadServicioImpl {

	@Autowired
	private ModalidadEscolarDAO modalidadEscolarDAO;
	@Autowired
	private NivelEscolarDAO nivelEscolarDAO;
	@Autowired
	private AnioEscolarDAO anioEscolarDAO;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private ReguladorDeEscolaridad regulador;
	@Autowired
	private ArchivoCliente archivoCliente;
	
	@Transactional
	public void agregarEscolaridad(Integer idJoven, Integer idModalidad,
			Integer idNivel, Integer idAnio, String establecimiento,
			Integer cicloLectivo, Boolean periodoEvaluacion, Boolean certificado, Boolean cursando, Boolean finalizado,
			String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		ModalidadEscolar modalidad = modalidadEscolarDAO.traerPorId(idModalidad);
		NivelEscolar nivel = nivelEscolarDAO.traerPorId(idNivel);
		AnioEscolar anio = anioEscolarDAO.traerPorId(idAnio);
		
		Escolaridad escolaridad = regulador.agregarEscolaridad(joven, modalidad, nivel, anio, establecimiento, cicloLectivo, 
				periodoEvaluacion, certificado, cursando, finalizado, observacion);
		jovenDAO.guardarEscolaridad(escolaridad);
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void actualizarEscolaridad(Integer idJoven, Integer id,
			Integer idModalidad, Integer idNivel, Integer idAnio,
			String establecimiento, Integer cicloLectivo, Boolean periodoEvaluacion, Boolean certificado,
			Boolean cursando, Boolean finalizado, String observacion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		ModalidadEscolar modalidad = modalidadEscolarDAO.traerPorId(idModalidad);
		NivelEscolar nivel = nivelEscolarDAO.traerPorId(idNivel);
		AnioEscolar anio = anioEscolarDAO.traerPorId(idAnio);
		Escolaridad escolaridad = this.traerEscolaridad(joven, id);
		
		regulador.modificarEscolaridad(joven, escolaridad, modalidad, nivel, anio, establecimiento, cicloLectivo, 
				periodoEvaluacion, certificado, cursando, finalizado, observacion);
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void eliminarEscolaridad(Integer idJoven, Integer id) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Escolaridad escolaridad = this.traerEscolaridad(joven, id);
		
		regulador.eliminarEscolaridad(joven, escolaridad);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void asentarCertificado(Integer idJoven, Integer id, String direccion) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Escolaridad escolaridad = this.traerEscolaridad(joven, id);
		
		escolaridad.asentarCertificado(direccion);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void asentarCertificado(Integer idJoven, Integer id, byte[] fieldData, String nombreArchivo, Date fecha,
			String descripcion, String tipoDeContenido, String usuario, Integer idSistema, Integer idSector, Integer idRol) throws ReinaException {
		Joven joven = jovenDAO.traerPorId(idJoven);
		Escolaridad escolaridad = this.traerEscolaridad(joven, id);
		
		TipoEntidadGeneral tipoEntidadGeneral = new TipoEntidadGeneral();
		tipoEntidadGeneral.setId(new Long(2));
		TipoEntidad tipoEntidad = new TipoEntidad();
		tipoEntidad.setId(new Long(5));
		TipoArchivo tipoArchivo = new TipoArchivo();
		tipoArchivo.setId(new Long(7));
		SubTipoArchivo subTipoArchivo = new SubTipoArchivo();
		subTipoArchivo.setId(new Long(15));
		
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
		
		escolaridad.asentarCertificado(documentMetadata.getUuid());
		jovenDAO.actualizar(joven);		
	}
	
	private Escolaridad traerEscolaridad(Joven joven, Integer id) {
		java.util.Iterator<Escolaridad> iter = joven.getEscolaridades().iterator();
		Escolaridad escolaridad = null;
		
		while (iter.hasNext()) {
			Escolaridad esc = iter.next();
			if (esc.getId().equals(id))
				escolaridad = esc;
		}
		
		return escolaridad;
	}

	public List<NivelEscolar> traerNivelesActivasPorModalidad(int idModalidad) {
		ModalidadEscolar modalidad = modalidadEscolarDAO.traerPorId(idModalidad);
		List<NivelEscolar> niveles = new ArrayList<NivelEscolar>();
		
		if (modalidad != null) {
			for (NivelEscolar nivelEscolar : modalidad.getNiveles()) {
				if (nivelEscolar.getEstaActivo())
					niveles.add(nivelEscolar);
			}
		}
		
		return niveles;
	}

	public List<AnioEscolar> traerAniosActivasPorNivel(int idNivel) {
		NivelEscolar nivel = nivelEscolarDAO.traerPorId(idNivel);
		List<AnioEscolar> anios = new ArrayList<AnioEscolar>();
		
		if (nivel != null) {
			for (AnioEscolar anio : nivel.getAnios()) {
				if (anio.getEstaActivo())
					anios.add(anio);
			}
		}
		
		return anios;
	}
	
	public List<ModalidadEscolar> traerModalidadesActivas() {
		return modalidadEscolarDAO.traerTodasActivas();
	}

	public List<NivelEscolar> traerNivelesActivas() {
		return nivelEscolarDAO.traerTodasActivas();
	}

	public List<AnioEscolar> traerAniosActivas() {
		return anioEscolarDAO.traerTodosActivas();
	}
}

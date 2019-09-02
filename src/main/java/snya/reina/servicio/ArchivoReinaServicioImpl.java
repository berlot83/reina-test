package snya.reina.servicio;

import java.io.IOException;
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
import snya.reina.serviciomodelo.GenericBuilder;

@Service
public class ArchivoReinaServicioImpl {

	@Autowired
	private ArchivoCliente archivoCliente;


	@Transactional
	public Long solicitarEliminacionDelArchivo(String uuid, String usuario, Integer idSistema, Integer idSector, Integer idRol) {

		Long idSolicitudEliminacion = this.archivoCliente.solicitarEliminacion(uuid, usuario, idSistema, idSector, idRol);

		return idSolicitudEliminacion;
	}

	@Transactional
	public DocumentMetadata subirArchivo(byte[] fieldData, String nombreArchivo, Date fecha, String descripcion,
			String tipoDeContenido, Long idTipoEntidadGeneral, String idEntidadGeneral, Long idTipoEntidad,
			String idEntidad, Long idTipoArchivo, Long idSubTipoArchivo, String usuario, Integer idSistema,
			Integer idSector, Integer idRol) throws IOException {

		TipoEntidadGeneral tipoEntidadGeneral = new TipoEntidadGeneral();
		tipoEntidadGeneral.setId(idTipoEntidadGeneral);
		TipoEntidad tipoEntidad = new TipoEntidad();
		tipoEntidad.setId(idTipoEntidad);
		TipoArchivo tipoArchivo = new TipoArchivo();
		tipoArchivo.setId(idTipoArchivo);
		SubTipoArchivo subTipoArchivo = new SubTipoArchivo();
		subTipoArchivo.setId(idSubTipoArchivo);

		Archivo document = GenericBuilder.of(Archivo::new)
				.with(Archivo::setFileData, fieldData)
				.with(Archivo::setNombreArchivo, nombreArchivo)
				.with(Archivo::setFecha, fecha)
				.with(Archivo::setDescripcion, descripcion)
				.with(Archivo::setTipoContenido, tipoDeContenido)
				.with(Archivo::setTipoEntidadGeneral, tipoEntidadGeneral)
				.with(Archivo::setIdEntidadGeneral, idEntidadGeneral)
				.with(Archivo::setTipoEntidad, tipoEntidad)
				.with(Archivo::setIdEntidad, idEntidad)
				.with(Archivo::setTipoArchivo, tipoArchivo)
				.with(Archivo::setSubTipoArchivo, subTipoArchivo)
				.build();

		DocumentMetadata documentMetadata = this.archivoCliente.guardar(document, usuario, idSistema, idSector, idRol);

		return documentMetadata;
	}

	public Archivo traerArchivo(String uuid) {
		return this.archivoCliente.traerArchivo(uuid);
	}
	
	public List<Archivo> listaArchivosPorEntidad(Long idTipoEntidad, Long idEntidad) {

		List<Archivo> archivos = this.archivoCliente.traerArchivosDeEntidad(idTipoEntidad, idEntidad);

		return archivos;
	}

	public List<TipoArchivo> traerTiposDeArchivosPorTipoEntidad(Integer idTipoEntidad) {

		List<TipoArchivo> lista = this.archivoCliente.traerTiposDeArchivo(idTipoEntidad);

		return lista;
	}
	
	public List<TipoArchivo> traerTiposDeArchivo() {

		List<TipoArchivo> lista = this.archivoCliente.traerTiposDeArchivo();

		return lista;
	}

	public List<SubTipoArchivo> traerSubTiposDeArchivo(TipoArchivo tipo) {

		List<SubTipoArchivo> lista = this.archivoCliente.traerSubTiposDeArchivoDelTipo(tipo);

		return lista;
	}

	public List<SubTipoArchivo> traerSubTiposDeArchivo(Long idTipoArchivo) {

		List<SubTipoArchivo> lista = this.archivoCliente.traerSubTiposDeArchivoDeIdTipo(idTipoArchivo);

		return lista;
	}
}

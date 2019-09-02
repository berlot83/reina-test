package snya.reina.serviciomodelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import snya.archivoscliente.ArchivoCliente;
import snya.archivoscliente.modelo.Archivo;
import snya.archivoscliente.modelo.SubTipoArchivo;
import snya.archivoscliente.modelo.TipoArchivo;
import snya.archivoscliente.modelo.TipoEntidad;
import snya.archivoscliente.modelo.TipoEntidadGeneral;
import snya.reina.modelo.informe.Informe;

public class EnvioDeArchivos {

	private String usuario;
	private Integer idSistema;
	private Integer idSector;
	private Integer idRol;
	private List<ArchivoEnviado> archivos;

	
	public EnvioDeArchivos(String usuario, Integer idSistema, Integer idSector, Integer idRol) {
		super();
		this.usuario = usuario;
		this.idSistema = idSistema;
		this.idSector = idSector;
		this.idRol = idRol;

		this.archivos = new ArrayList<ArchivoEnviado>();
	}

	
	public void agregarArchivo(byte[] archivo, String nombreArchivo, String tipoDeContenido, String descripcion){
		this.archivos.add(new ArchivoEnviado(archivo, nombreArchivo, tipoDeContenido, descripcion));
	}
	
	public void guardarArchivoInforme(ArchivoCliente archivoCliente, Integer idJoven, Informe informe, Date fecha) {
		
		TipoEntidadGeneral tipoEntidadGeneral = new TipoEntidadGeneral();
		tipoEntidadGeneral.setId(new Long(2));
		TipoEntidad tipoEntidad = new TipoEntidad();
		tipoEntidad.setId(new Long(4));
		TipoArchivo tipoArchivo = new TipoArchivo();
		tipoArchivo.setId(new Long(8));
		SubTipoArchivo subTipoArchivo = new SubTipoArchivo();
		subTipoArchivo.setId(new Long(16));
		
		for (ArchivoEnviado archivo : archivos) {
			Archivo document = GenericBuilder.of(Archivo::new)
					.with(Archivo::setFileData, archivo.getArchivo())
					.with(Archivo::setNombreArchivo, archivo.getNombreArchivo())
					.with(Archivo::setFecha, fecha)
					.with(Archivo::setDescripcion, archivo.getDescripcion())
					.with(Archivo::setTipoContenido, archivo.getTipoDeContenido())
					.with(Archivo::setTipoEntidadGeneral, tipoEntidadGeneral)
					.with(Archivo::setIdEntidadGeneral, idJoven.toString())
					.with(Archivo::setTipoEntidad, tipoEntidad)
					.with(Archivo::setIdEntidad, informe.getId().toString())
					.with(Archivo::setTipoArchivo, tipoArchivo)
					.with(Archivo::setSubTipoArchivo, subTipoArchivo)
					.build();
			
			archivoCliente.guardar(document, usuario, idSistema, idSector, idRol);			
		}
	}
	
	public boolean tieneArchivos() {
		return this.archivos.size() > 0;
	}
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public List<ArchivoEnviado> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<ArchivoEnviado> archivos) {
		this.archivos = archivos;
	}

	
	public class ArchivoEnviado {
		private byte[] archivo;
		private String nombreArchivo;
		private String tipoDeContenido;
		private String descripcion;
		
		public ArchivoEnviado(byte[] archivo, String nombreArchivo, String tipoDeContenido, String descripcion) {
			this.archivo = archivo;
			this.nombreArchivo = nombreArchivo;
			this.tipoDeContenido = tipoDeContenido;
			this.descripcion = descripcion;
		}
		

		public byte[] getArchivo() {
			return archivo;
		}

		public void setArchivo(byte[] archivo) {
			this.archivo = archivo;
		}

		public String getNombreArchivo() {
			return nombreArchivo;
		}

		public void setNombreArchivo(String nombreArchivo) {
			this.nombreArchivo = nombreArchivo;
		}

		public String getTipoDeContenido() {
			return tipoDeContenido;
		}

		public void setTipoDeContenido(String tipoDeContenido) {
			this.tipoDeContenido = tipoDeContenido;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

	}
}

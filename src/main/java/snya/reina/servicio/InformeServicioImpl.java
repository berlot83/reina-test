package snya.reina.servicio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snya.reina.modelo.informe.GrupoDeInforme;
import snya.reina.modelo.informe.Informe;
import snya.reina.modelo.informe.PermisoGrupoDeInforme;
import snya.reina.modelo.informe.TipoDeEstadoInformeEnum;
import snya.reina.modelo.informe.TipoDeInforme;
import snya.reina.modelo.institucion.Institucion;
import snya.archivoscliente.modelo.Archivo;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.intervencion.GrupoDeInformeDAO;
import snya.reina.datos.intervencion.TipoDeInformeDAO;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.intervencion.Intervencion;
import snya.reina.modelo.joven.Joven;
import snya.reina.repositorios.InformeRepositorio;
import snya.reina.rest.dto.InformeDTO;

@Service
public class InformeServicioImpl {

	@Autowired
	public InformeRepositorio informeRepositorio;
	@Autowired
	private GrupoDeInformeDAO grupoDeInformeDAO;
	@Autowired
	private TipoDeInformeDAO tipoDeInformeDAO;
	@Autowired
	private JovenDAO jovenDAO;
	@Autowired
	private ArchivoReinaServicioImpl archivoServicio;
	
	@Transactional
	public void guardarGrupoDeInforme(String grupoInforme, List<PermisoGrupoDeInforme> permisos) throws ReinaException {
		GrupoDeInforme grupo =  new GrupoDeInforme(grupoInforme);
		
		for (PermisoGrupoDeInforme permiso : permisos) {
			grupo.agregarPermiso(permiso.getRol(), permiso.getFunciones());
		}
		
		if(grupoInforme== null || grupoInforme.equals(""))
			throw new ReinaException(ReinaCte.GRUPO_INFORME_SIN_NOMBRE);

		if(permisos.size() == 0)
			throw new ReinaException(ReinaCte.GRUPO_INFORME_SIN_PERMISO);
		
		grupoDeInformeDAO.insertar(grupo);
	}
	
	@Transactional
	public void actualizarGrupoDeInforme(Integer id, String grupoInforme, List<PermisoGrupoDeInforme> permisos) throws ReinaException {
		GrupoDeInforme grupo =  grupoDeInformeDAO.traerPorId(id);
		
		if(grupoInforme== null || grupoInforme.equals(""))
			throw new ReinaException(ReinaCte.GRUPO_INFORME_SIN_NOMBRE);

		if(permisos.size() == 0)
			throw new ReinaException(ReinaCte.GRUPO_INFORME_SIN_PERMISO);
		
		grupo.setNombre(grupoInforme);
		grupo.indicarPermisos(permisos);
		
		grupoDeInformeDAO.actualizar(grupo);
	}
	
	@Transactional
	public Informe agregarInforme(Integer idIntervencion, Integer idJoven, Date fechaInforme,
			Integer tipoInforme, String observacion, Institucion institucion, String autores, TipoDeEstadoInformeEnum estado) throws ReinaException {
		TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(tipoInforme);
		Joven joven = jovenDAO.traerPorId(idJoven);
		Intervencion intervencion = joven.traerIntervencion(idIntervencion);
		
		Informe informe = joven.agregarInforme(intervencion, fechaInforme, tipo, observacion, institucion, autores, false, estado);
		jovenDAO.guardarInforme(informe);
		jovenDAO.actualizar(joven);
		
		return informe;	
	}
	
	@Transactional
	public Informe agregarInformeParaArchivo(Integer idIntervencion, Integer idJoven, Date fechaInforme, Integer tipoInforme, Institucion institucion, String autores, TipoDeEstadoInformeEnum estado) throws ReinaException {
		TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(tipoInforme);
		Joven joven = jovenDAO.traerPorId(idJoven);
		Intervencion intervencion = joven.traerIntervencion(idIntervencion);
		
		Informe informe = joven.agregarInforme(intervencion, fechaInforme, tipo, "", institucion, autores, true, estado);
		jovenDAO.guardarInforme(informe);
		jovenDAO.actualizar(joven);
		
		return informe;		
	}
		
	@Transactional
	public void modificarInforme(Integer idJoven, Integer idInforme, Institucion institucion, String observacion) throws ReinaException {		
		Joven joven = jovenDAO.traerPorId(idJoven);
		Informe informe = this.traerInforme(joven, idInforme);

		if (informe == null)
			throw new ReinaException(ReinaCte.EL_INFORME_NO_PERTENECE_AL_JOVEN);

		joven.modificarInforme(informe, institucion, observacion);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void acentarInforme(Integer idJoven, Integer idInforme, String observacion) throws ReinaException {		
		Joven joven = jovenDAO.traerPorId(idJoven);
		Informe informe = this.traerInforme(joven, idInforme);

		if (informe == null)
			throw new ReinaException(ReinaCte.EL_INFORME_NO_PERTENECE_AL_JOVEN);

		if (!informe.getArchivo()) informe.setObservacion(observacion);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void acentarInforme(Integer idJoven, Integer idInforme, Institucion institucion, String observacion) throws ReinaException {		
		Joven joven = jovenDAO.traerPorId(idJoven);
		Informe informe = this.traerInforme(joven, idInforme);

		if (informe == null)
			throw new ReinaException(ReinaCte.EL_INFORME_NO_PERTENECE_AL_JOVEN);

		if (!informe.getArchivo()) informe.setObservacion(observacion);
		informe.setEstado(TipoDeEstadoInformeEnum.EDICION);
		jovenDAO.actualizar(joven);
	}
	
	@Transactional
	public void cerrarInforme(Integer idJoven, Integer id) throws ReinaException {		
		Joven joven = jovenDAO.traerPorId(idJoven);
		Informe informe = this.traerInforme(joven, id);

		if (informe == null)
			throw new ReinaException(ReinaCte.EL_INFORME_NO_PERTENECE_AL_JOVEN);
		
		informe.setEstado(TipoDeEstadoInformeEnum.IMPRESO);
		jovenDAO.actualizarInforme(informe);
	}
	
	@Transactional
	public void eliminarInforme(Integer idJoven, Integer idInforme,
			String usuario, Integer idSistema, Integer idSector, Integer idRol) throws ReinaException {		
		Joven joven = jovenDAO.traerPorId(idJoven);
		Informe informe = this.traerInforme(joven, idInforme);

		if (informe == null)
			throw new ReinaException(ReinaCte.EL_INFORME_NO_PERTENECE_AL_JOVEN);
		
		if (informe.getArchivo()) {
			Long idTipoEntidad = new Long(4);
			Long idEntidad = new Long(idInforme);
			List<Archivo> archivos = archivoServicio.listaArchivosPorEntidad(idTipoEntidad, idEntidad);
			
			for (Archivo archivo : archivos) {
				archivoServicio.solicitarEliminacionDelArchivo(archivo.getUuid(), usuario, idSistema, idSector, idRol);
			}
		}		
		joven.eliminarInforme(informe);
		jovenDAO.actualizar(joven);
	}

	@Transactional
	public void imprimirInforme(Informe informe) {
		informe.setEstado(TipoDeEstadoInformeEnum.IMPRESO);
		jovenDAO.actualizarInforme(informe);
	}
	
	@Transactional
	public void guardarModeloDeInforme(Integer grupoDeInforme, String tipoInforme, 
			Boolean requiereHora, Boolean usoPredeterminado,
			Integer tiempoCierre, String descripcion,
			String observacion) throws ReinaException {
		GrupoDeInforme grupo = grupoDeInformeDAO.traerPorId(grupoDeInforme); 
		
		validarDatosTipoDeInforme(tipoInforme, observacion, grupo);
		
		TipoDeInforme tipo = new TipoDeInforme(grupo, tipoInforme, requiereHora, usoPredeterminado,
				tiempoCierre, descripcion, observacion);
		tipoDeInformeDAO.insertar(tipo);
	}

	@Transactional
	public void actualizarModeloDeInforme(Integer id, Integer grupoDeInforme, String tipoInforme, 
			Boolean requiereHora, Boolean usoPredeterminado,
			Integer tiempoCierre, String descripcion,
			String observacion) throws ReinaException {
		TipoDeInforme tipo = tipoDeInformeDAO.traerPorId(id);
		GrupoDeInforme grupo = grupoDeInformeDAO.traerPorId(grupoDeInforme);

		validarDatosTipoDeInforme(tipoInforme, observacion, grupo);
		
		tipo.setGrupo(grupo);
		tipo.setNombre(tipoInforme);
		tipo.setRequiereHora(requiereHora);
		tipo.setUsaPredeterminado(usoPredeterminado);
		tipo.setTiempoCierre(tiempoCierre);
		tipo.setDescripcion(descripcion);
		tipo.modificarNota(observacion);
		tipoDeInformeDAO.actualizar(tipo);
	}
	
	public List<TipoDeInforme> traerTipoDeInformeTodos() {
		return tipoDeInformeDAO.traerTodos();
	}
	
	public List<TipoDeInforme> traerTipoDeInformeActivos() {
		return tipoDeInformeDAO.traerTodosActivos();
	}
	
	public TipoDeInforme traerTipoDeInformePorId(int idTipoInforme) {
		return tipoDeInformeDAO.traerPorId(idTipoInforme);
	}

	public List<TipoDeInforme> traerTipoDeInformePorGrupo(int idGrupo) {
		return tipoDeInformeDAO.traerActivosPorGrupo(idGrupo);
	}
	
	public GrupoDeInforme traerGrupoDeInformePorId(Integer id) {
		return grupoDeInformeDAO.traerPorId(id);
	}
	
	public List<GrupoDeInforme> traerGrupoDeInformeActivos() {
		return grupoDeInformeDAO.traerTodosActivos();
	}
	
	public List<GrupoDeInforme> traerGrupoDeInformeActivosPorPermisoEscritura(int rol, int idFuncion) {
		List<GrupoDeInforme> grupos = new ArrayList<GrupoDeInforme>();
		
		for (GrupoDeInforme grupoDeInforme : grupoDeInformeDAO.traerTodosActivos()) {
			if (grupoDeInforme.getEstaActivo() && grupoDeInforme.estaPermitidoEscribir(rol, idFuncion))
				grupos.add(grupoDeInforme);
		}
		
		
		return grupos;
	}
	
	public List<GrupoDeInforme> traerGrupoDeInformeActivosPorPermisoLectura(int rol, int idFuncion) {
		List<GrupoDeInforme> grupos = new ArrayList<GrupoDeInforme>();
		
		for (GrupoDeInforme grupoDeInforme : grupoDeInformeDAO.traerTodosActivos()) {
			if (grupoDeInforme.getEstaActivo() && grupoDeInforme.estaPermitidoLeer(rol, idFuncion))
				grupos.add(grupoDeInforme);
		}
		
		return grupos;
	}
	
	public List<TipoDeInforme> traerTipoDeInformeActivosPorPermisoLectura(int rol, int idFuncion) {
		List<TipoDeInforme> tipos = new ArrayList<TipoDeInforme>();
		
		for (GrupoDeInforme grupoDeInforme : grupoDeInformeDAO.traerTodosActivos()) {
			if (grupoDeInforme.getEstaActivo() && grupoDeInforme.estaPermitidoLeer(rol, idFuncion))
				tipos.addAll( grupoDeInforme.getInformes() );
		}
		
		return tipos;
	}
		
	private void validarDatosTipoDeInforme(String tipoInforme,
			String observacion, GrupoDeInforme grupo) throws ReinaException {
		if (tipoInforme == null)
			throw new ReinaException("Debe consignar el nombre del modelo de informe");
		if (observacion == null || observacion.length() <= 0)
			throw new ReinaException("Debe consignar el modelo de informe, el cuerpo del informe no puese estar vacio");
		if (grupo == null)
			throw new ReinaException("Debe consignar el grupo al que pertenece el modelo de informe");
	}
	
	private Informe traerInforme(Joven joven, Integer id) {
		java.util.Iterator<Informe> iter = joven.getInformes().iterator();
		Informe estr = null;
		
		while (iter.hasNext()) {
			Informe e = iter.next();
			if (e.getId().equals(id))
				estr = e;
		}
		return estr;
	}
	
	public Informe traerInforme(Integer id) {
		Informe informe = informeRepositorio.findById(id);
		return informe;
	}

	public InformeDTO traerInformeSimplificado(Integer id) throws MalformedURLException {
		Informe informe = informeRepositorio.findById(id);
		InformeDTO informeDTO = null;
		if(informe != null) 
			informeDTO = new InformeDTO(informe);
		informeDTO.agregarUrlArchivos(new URL("http://google.com/"));
		return informeDTO;
	}
	
}

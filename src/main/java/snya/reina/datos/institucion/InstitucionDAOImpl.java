package snya.reina.datos.institucion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.general.modelo.Telefono;
import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.ResultadoConsultaInstitucion;
import snya.reina.modelo.institucion.ContactoInstitucion;
import snya.reina.modelo.institucion.Dependencia;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.ReinaException;

@Repository
public class InstitucionDAOImpl extends HibernateDAOGenericoImpl<InstitucionCumplimiento, Integer>
		implements InstitucionDAO {

	@Override
	protected Class<InstitucionCumplimiento> getEntityClass() {
		return InstitucionCumplimiento.class;
	}
	
	public Institucion traerPorIdCompleto(Integer id) {
		Institucion inst = this.traerPorId(Institucion.class, id);
		for (Telefono tel : inst.getTelefonos()) {
			tel.getCaracteristica();
		}
		for (ContactoInstitucion cont : inst.getContactos()) {
			cont.getNombre();
		}		
		return inst;
	}
	
	@Override
	public ProgramaSimplificado traerProgramaPorId(Integer id) {
		ProgramaSimplificado programa = this.traerPorId(ProgramaSimplificado.class, id);
		for (Telefono tel : programa.getTelefonos()) {
			tel.getCaracteristica();
		}
		return programa;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResultadoConsultaInstitucion> traerPorNombre(String nombre, String propiedad, String orden) {
		List<ResultadoConsultaInstitucion> lista;
		String query = "select new ResultadoConsultaInstitucion(i.id, i.nombre, i.nombreCorto, i.tipoDeInstitucion, 'I', i.estaActivo) from Institucion i where i.estaActivo = 1 and i.nombre like \'%" + nombre + "%\' ";
		query += " order by i." + propiedad + " " + orden;
		lista = (List<ResultadoConsultaInstitucion>) this.traerTodosPorConsulta(Institucion.class, query);
		
		query = "select new ResultadoConsultaInstitucion(p.id, p.nombre, p.nombreCorto, p.tipoDeInstitucion, 'P', p.estaActivo) from ProgramaSimplificado p where p.estaActivo = 1 and p.nombre like \'%" + nombre + "%\' ";		
		query += " order by p." + propiedad + " " + orden;
		lista.addAll(  (List<ResultadoConsultaInstitucion>) this.traerTodosPorConsulta(Institucion.class, query) );

		Collections.sort(lista, new Comparator<ResultadoConsultaInstitucion>() {
			@Override
			public int compare(ResultadoConsultaInstitucion o1, ResultadoConsultaInstitucion o2) {
				return o1.getNombre().compareTo(o2.getNombre());
			}
		});
		
		// <<procesamiento>> <<resultado>>        
        return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Institucion> traerPosibleInstitucionPadreTodos() {
		String query = "select i from Institucion i where i.tipoDeInstitucion.id in (5,6,7,8,9,14,25,26,27,28) and i.estaActivo = 1 order by i.nombre asc ";
		
		// <<procesamiento>> <<resultado>>        
        return (List<Institucion>) this.traerTodosPorConsulta(Institucion.class, query);		
	}
	
	@Override
	public void insertar(Institucion institucion) throws ReinaException {
		try {
			this._guardar(institucion);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}
	
	@Override
	public void insertarPrograma(ProgramaSimplificado programa) throws ReinaException {
		try {
			this._guardar(programa);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}
	
	@Override
	public void actualizar(Institucion institucion) {
		super._modificar(institucion);		
	}

	@Override
	public void actualizarPrograma(ProgramaSimplificado programa) throws ReinaException {
		try {
			this._guardar(programa);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}		
	}
	
	@Override
	public InstitucionCumplimiento traerAmbitoPorId(Integer idInstitucion) {
		return this.traerPorId(idInstitucion);
	}

	@Override
	public List<InstitucionCumplimiento> traerInstitucionCumplimientoTodas() {
		return this.traerTodos();
	}

	@Override
	public List<InstitucionCumplimiento> traerInstitucionCumplimientoTodasActivas() {
		return this.traerPorPropiedad("estaActivo", true);
	}

	@Override
	public List<Dependencia> traerDependenciasTodasActivas() {
		return this.traerPorPropiedad(Dependencia.class, "estaActivo", true);		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Institucion> traerHijosDelPadre(Integer id) {
		String query = "select i from Institucion i where i.padre.id = " + id + " and i.estaActivo = 1 ";
		return (List<Institucion>) this.traerTodosPorConsulta(Institucion.class, query);
	}
	
	@Override
	public List<Institucion> traerInstitucionesVinculadas(Integer idInsittucion, Integer idTipoInstitucion) {
		Institucion institucion = this.traerPorId(idInsittucion);
		List<Institucion> instituciones = new ArrayList<Institucion>();
		
		for (Institucion inst : institucion.getVinculadas()) {
			if (inst.getTipoDeInstitucion().getId() == idTipoInstitucion)
				instituciones.add(inst);
		}
		
		return instituciones;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Institucion> traerTodosPorTipoActivos(Integer idTipo) {
		String query = "select i from Institucion i where i.tipoDeInstitucion.id in (" + idTipo +") and i.estaActivo = 1 order by i.nombre asc ";
		
		// <<procesamiento>> <<resultado>>        
        return (List<Institucion>) this.traerTodosPorConsulta(Institucion.class, query);		
	}
}

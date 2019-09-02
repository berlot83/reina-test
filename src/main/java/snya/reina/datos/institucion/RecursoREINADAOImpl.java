package snya.reina.datos.institucion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.ProgramaSimplificado;
import snya.reina.modelo.institucion.CentroDeReferencia;
import snya.reina.modelo.institucion.CentroDeSalud;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.institucion.UnidadPenitenciaria;
import snya.reina.modelo.recurso.ComponeRecurso;
import snya.reina.modelo.recurso.Recurso;

@Repository
public class RecursoREINADAOImpl extends HibernateDAOGenericoImpl<Recurso, Integer> implements RecursoREINADAO {

	@Override
	protected Class<Recurso> getEntityClass() {
		return Recurso.class;
	}
	
	@Override
	public Recurso traerPor(ComponeRecurso componente) {
		// <<declaracion e inicializacion de variables>>
        String query;
        String tipoComp = "";
        
    	if ( CentroDeReferencia.class.isInstance(componente) ) tipoComp = "1";
    	if ( InstitucionCumplimiento.class.isInstance(componente) ) tipoComp = "2";
    	if ( CentroDeSalud.class.isInstance(componente) ) tipoComp = "3";
    	if ( UnidadPenitenciaria.class.isInstance(componente) ) tipoComp = "4";
    	if ( ProgramaSimplificado.class.isInstance(componente) ) tipoComp = "5";

    	if (!tipoComp.equals("")) {
			query = "select r from Recurso r where r.componente.class = '" + tipoComp + "' and r.componente.id = " + componente.getId();
	        
			// <<procesamiento>> <<resultado>>        
	        return this.traerTodosPorConsulta(query).get(0);
    	} else 
    		return null;
	}

	@Override
	public void insertar(Recurso recurso) {
		this.guardar(recurso);
	}	
	
	@Override
	public List<Recurso> traerInstitucionesCumpliminetoTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;
		query = "select r from Recurso r, InstitucionCumplimiento i where r.componente.class = '2' and r.componente.id = i.id and i.tipoDeInstitucion.id in (11,12,13, 30) and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}
	
	@Override
	public List<Recurso> traerCentrosContencionTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

		query = "select r from Recurso r, InstitucionCumplimiento i where r.componente.class = '2' and r.componente.id = i.id and i.tipoDeInstitucion.id = 11 and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerCentrosRecepcionTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

		query = "select r from Recurso r, InstitucionCumplimiento i where r.componente.class = '2' and r.componente.id = i.id and i.tipoDeInstitucion.id = 12 and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerCentrosCerradosTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

        query = "select r from Recurso r, InstitucionCumplimiento i where r.componente.class = '2' and r.componente.id = i.id and i.tipoDeInstitucion.id = 13 and i.estaActivo = 1";

        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerCentrosAdmisionTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

    	query = "select r from Recurso r, InstitucionCumplimiento i where r.componente.class = '2' and r.componente.id = i.id and i.tipoDeInstitucion.id = 30 and i.estaActivo = 1";

        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}
	
	@Override
	public List<Recurso> traerCentrosDeSaludTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

        query = "select r from Recurso r, CentroDeSalud i where r.componente.class = '3' and r.componente.id = i.id and i.tipoDeInstitucion.id = 14 and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerInstitucionesTerciarizadasTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

        query = "select r from Recurso r, ProgramaSimplificado i where r.componente.class = '5' and r.componente.id = i.id and i.tipoDeInstitucion.id in (19,20,23,24) and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerUnidadesPenitenciariasTodos() {
		// <<declaracion e inicializacion de variables>>
        String query;

        query = "select r from Recurso r, UnidadPenitenciaria i where r.componente.class = '4' and r.componente.id = i.id "; //and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerInstitucionesSPPDTodos() {
		// <<declaracion e inicializacion de variables>>
        String query;

        query = "select r from Recurso r, ProgramaSimplificado i where r.componente.class = '5' and r.componente.id = i.id and i.tipoDeInstitucion.id in (21) and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
		query = "select r from Recurso r, InstitucionCumplimientoSPPD i where r.componente.class = '6' and r.componente.id = i.id and i.tipoDeInstitucion.id in (17,22) and i.estaActivo = 1";       
        List<Recurso> listaSPPD = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : listaSPPD) {
			recurso.getComponente().getNombre();
			lista.add(recurso);
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerCentroDeReferenciaTodos() {
		// <<declaracion e inicializacion de variables>>
        String query;

		query = "select r from Recurso r, CentroDeReferencia i where r.componente.class = '1' and r.componente.id = i.id and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}

	@Override
	public List<Recurso> traerInstitucionesExtraJuridireccionalesTodosActivos() {
		// <<declaracion e inicializacion de variables>>
        String query;

        query = "select r from Recurso r, InstitucionCumplimiento i where r.componente.class = '2' and r.componente.id = i.id and i.tipoDeInstitucion.id = 16 ";//and i.estaActivo = 1";
        
		// <<procesamiento>> <<resultado>>        
        List<Recurso> lista = (List<Recurso>) this.traerTodosPorConsulta(query);
        for (Recurso recurso : lista) {
			recurso.getComponente().getNombre();
		}
        
        return lista;
	}	
}

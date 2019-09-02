package snya.reina.datos.educacion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.NivelEscolar;

@Repository
public class NivelEscolarDAOImpl extends HibernateDAOGenericoImpl<NivelEscolar, Integer> implements NivelEscolarDAO {
	
	
	@Override
	protected Class<NivelEscolar> getEntityClass() {
		return NivelEscolar.class;
	}

	@Override
	public NivelEscolar traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}

	@Override
	public List<NivelEscolar> traerPorModalidad(int id) {
		// <<procesamiento>> <<resultado>>
		String sql = "select a from AnioEscolar a where a.modalidad.id = " + id + " order by a.nombre asc";

		return this.traerTodosPorConsulta(sql);
	}
	
	@Override
	public List<NivelEscolar> traerTodasActivas() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

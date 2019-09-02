package snya.reina.datos.educacion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.AnioEscolar;

@Repository
public class AnioEscolarDAOImpl extends HibernateDAOGenericoImpl<AnioEscolar, Integer> implements AnioEscolarDAO {
		
	@Override
	protected Class<AnioEscolar> getEntityClass() {
		return AnioEscolar.class;
	}

	@Override
	public List<AnioEscolar> traerPorNivel(int id) {
		// <<procesamiento>> <<resultado>>
		Object[] valores = new Object[1]; valores[0] = id;
		return this.traerTodosPorConsulta("select a from AnioEscolar a where a.nivel.id = ?0 order by a.nombre", valores);
	}
	
	@Override
	public List<AnioEscolar> traerTodosActivas() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

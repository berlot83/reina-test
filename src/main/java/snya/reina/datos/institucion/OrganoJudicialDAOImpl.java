package snya.reina.datos.institucion;



import java.util.List;

import org.springframework.stereotype.Repository;

import snya.reina.modelo.institucion.OrganoJudicial;
import snya.general.datos.HibernateDAOGenericoImpl;

@Repository
public class OrganoJudicialDAOImpl extends HibernateDAOGenericoImpl<OrganoJudicial, Integer> implements OrganoJudicialDAO {
	
	@Override
	protected Class<OrganoJudicial> getEntityClass() {
		return OrganoJudicial.class;
	}
	
	@Override
	public List<OrganoJudicial> traerTodosActivos() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

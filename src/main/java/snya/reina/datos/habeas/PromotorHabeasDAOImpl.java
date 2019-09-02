package snya.reina.datos.habeas;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.habeas.PromotorHabeas;

@Repository
public class PromotorHabeasDAOImpl extends HibernateDAOGenericoImpl<PromotorHabeas, Integer>
	implements PromotorHabeasDAO {

	@Override
	protected Class<PromotorHabeas> getEntityClass() {
		return PromotorHabeas.class;
	}
	
	@Override
	public List<PromotorHabeas> traerTodosActivos() {
		return super.traerPorPropiedad("estaActivo", true);
	}
}

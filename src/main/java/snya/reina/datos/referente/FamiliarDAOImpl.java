package snya.reina.datos.referente;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.referente.Familiar;

@Repository
public class FamiliarDAOImpl extends HibernateDAOGenericoImpl<Familiar, Integer> implements FamiliarDAO {

	@Override
	protected Class<Familiar> getEntityClass() {
		return Familiar.class;
	}

	@Override
	public void actualizar(Familiar familiar) {
		this.modificar(familiar);
	}
}
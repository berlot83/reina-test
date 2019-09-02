package snya.reina.datos.intervencion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.ReinaException;
import snya.reina.modelo.informe.GrupoDeInforme;

@Repository
public class GrupoDeInformeDAOImpl  extends HibernateDAOGenericoImpl<GrupoDeInforme, Integer> implements GrupoDeInformeDAO {

	@Override
	protected Class<GrupoDeInforme> getEntityClass() {
		return GrupoDeInforme.class;
	}
	
	public void insertar(GrupoDeInforme grupo) throws ReinaException {
		try {
			this.guardar(grupo);	
		} catch (Exception e) {
			throw new ReinaException("Error al guardar los datos." + e.getMessage());
		}
	}
	
	@Override
	public void actualizar(GrupoDeInforme grupo) {
		this.modificar(grupo);
	}

	public List<GrupoDeInforme> traerTodosActivos() {
		return super.traerPorPropiedad("estaActivo", true);
	}
}

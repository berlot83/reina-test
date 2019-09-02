package snya.reina.datos.educacion;

import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.educacion.ModalidadEscolar;

@Repository
public class ModalidadEscolarDAOImpl extends HibernateDAOGenericoImpl<ModalidadEscolar, Integer> implements ModalidadEscolarDAO {
	
	@Override
	protected Class<ModalidadEscolar> getEntityClass() {
		return ModalidadEscolar.class;
	}

	@Override
	public ModalidadEscolar traerPorId(Integer id) {
		// <<procesamiento>> <<resultado>>
		return super.traerPorId(id);
	}

	@Override
	public List<ModalidadEscolar> traerTodasActivas() {
		return this.traerPorPropiedad("estaActivo", true);
	}
}

package snya.reina.datos.intervencion;

import java.util.List;

import snya.reina.modelo.intervencion.MotivoAprehension;

public interface MotivoAprehensionDAO {

	List<MotivoAprehension> traerTodos();

	MotivoAprehension traerPorId(Integer idMotivoAprehension);
}

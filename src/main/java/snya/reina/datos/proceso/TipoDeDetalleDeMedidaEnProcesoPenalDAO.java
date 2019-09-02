package snya.reina.datos.proceso;

import java.util.List;

import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;

public interface TipoDeDetalleDeMedidaEnProcesoPenalDAO {

	TipoDeDetalleDeMedidaEnProcesoPenal traerPorId(Integer id);

	List<TipoDeDetalleDeMedidaEnProcesoPenal> traerTipoDeDetalleDeMedidaEnProcesoPenalActivosPorTipo(
			int idTipoMedida);
}

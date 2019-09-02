package snya.reina.datos.proceso;

import java.util.List;

import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;

public interface TipoDeMedidaEnProcesoPenalDAO {

	List<TipoDeMedidaEnProcesoPenal> traerTipoDeMedidaActivosPorMomento(int idMomento);

	TipoDeMedidaEnProcesoPenal traerPorId(Integer id);
}

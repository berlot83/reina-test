package snya.reina.datos.proceso;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.proceso.TipoDeDetalleDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;

@Repository
public class TipoDeDetalleDeMedidaEnProcesoPenalDAOImpl extends
		HibernateDAOGenericoImpl<TipoDeDetalleDeMedidaEnProcesoPenal, Integer> implements
		TipoDeDetalleDeMedidaEnProcesoPenalDAO {

	@Override
	protected Class<TipoDeDetalleDeMedidaEnProcesoPenal> getEntityClass() {
		return TipoDeDetalleDeMedidaEnProcesoPenal.class;
	}

	@Override
	public List<TipoDeDetalleDeMedidaEnProcesoPenal> traerTipoDeDetalleDeMedidaEnProcesoPenalActivosPorTipo(
			int idTipoMedida) {
		List<TipoDeDetalleDeMedidaEnProcesoPenal> detalles = new ArrayList<TipoDeDetalleDeMedidaEnProcesoPenal>();
		TipoDeMedidaEnProcesoPenal tipo = this.traerPorId(TipoDeMedidaEnProcesoPenal.class, idTipoMedida);
		for (TipoDeDetalleDeMedidaEnProcesoPenal det : tipo.getDetalles()) {
			if (det.isEstaActivo()) detalles.add(det);
		}
		return detalles;
	}
}

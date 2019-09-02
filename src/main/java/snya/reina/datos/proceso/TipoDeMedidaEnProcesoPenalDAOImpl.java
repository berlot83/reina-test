package snya.reina.datos.proceso;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import snya.general.datos.HibernateDAOGenericoImpl;
import snya.reina.modelo.proceso.TipoDeMedidaEnProcesoPenal;
import snya.reina.modelo.proceso.TipoDeMomentoProcesal;

@Repository
public class TipoDeMedidaEnProcesoPenalDAOImpl extends
		HibernateDAOGenericoImpl<TipoDeMedidaEnProcesoPenal, Integer> implements
		TipoDeMedidaEnProcesoPenalDAO {

	@Override
	protected Class<TipoDeMedidaEnProcesoPenal> getEntityClass() {
		return TipoDeMedidaEnProcesoPenal.class;
	}

	@Override
	public List<TipoDeMedidaEnProcesoPenal> traerTipoDeMedidaActivosPorMomento(int idMomento) {
		ArrayList<TipoDeMedidaEnProcesoPenal> medidas = new ArrayList<TipoDeMedidaEnProcesoPenal>();
		TipoDeMomentoProcesal tipo = this.traerPorId(TipoDeMomentoProcesal.class, idMomento);
		for (TipoDeMedidaEnProcesoPenal med : tipo.getMedidas()) {
			if (med.getEstaActivo()) medidas.add(med);
		} 
		
		return medidas;
	}
}

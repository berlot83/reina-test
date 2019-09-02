package snya.reina.modelo.proceso.comando;

import java.util.Iterator;

import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.NotaProcesoPenal;
import snya.reina.modelo.proceso.TipoDeNotaProcesoPenal;

public class RespuestaApelacionSuspensiva extends ComandoProceso {

	@Override
	public void hacer() throws ReinaException {
		Integer idMedida = (Integer) this.getParametros()[1];
		
		NotaProcesoPenal notaAnterior = traerApelacionSuspensiva(idMedida);
		if (notaAnterior == null)
			throw new ReinaException(ReinaCte.FALTA_APELACION_SUSPENSIVA_PARA_RESPONDER);
		
		this.getNota().setIdReferencia(idMedida);
		MedidaEnProcesoPenal medida = this.getProceso().traerMedidaPorId(idMedida);
		medida.volverAContarTiempoDeCumplimiento();
	}
	
	@Override
	public void deshacer() throws ReinaException {
		MedidaEnProcesoPenal medida = this.getProceso().traerMedidaPorId(this.getNota().getIdReferencia());
		
		
		NotaProcesoPenal notaAnterior = traerApelacionSuspensiva(this.getNota().getIdReferencia());
		if (notaAnterior != null) {		
			medida.marcarApelacion(notaAnterior.getFecha());
		}
		
		this.getNota().setIdReferencia(null);
	}
	
	public boolean requiereMedida() {
		return true;
	}	
	
	
	private NotaProcesoPenal traerApelacionSuspensiva(Integer idMedida) {
		Iterator<NotaProcesoPenal> iter = this.getProceso().getNotas().iterator();
		NotaProcesoPenal notaAnterior = null;
		
		while (notaAnterior == null && iter.hasNext()) {
			NotaProcesoPenal notaProcesoPenal = (NotaProcesoPenal) iter.next();
			if ( notaProcesoPenal.getIdReferencia().equals(idMedida) && notaProcesoPenal.getTipo().getId().equals( TipoDeNotaProcesoPenal.ID_APELACION_SUSPENSIVA ) )
				notaAnterior = notaProcesoPenal;
		}
		return notaAnterior;
	}
}

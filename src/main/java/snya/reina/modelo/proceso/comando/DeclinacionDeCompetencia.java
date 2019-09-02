package snya.reina.modelo.proceso.comando;

import snya.reina.modelo.institucion.OrganoJudicial;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.MomentoProcesal;

public class DeclinacionDeCompetencia extends ComandoProceso {

	@Override
	public void hacer() throws ReinaException {
		OrganoJudicial organoJudicial = (OrganoJudicial) this.getParametros()[0];
		getNota().setIdReferencia(getProceso().getOrganoJudicial().getId());
		
		cambioEnOrganoJudicial(organoJudicial, organoJudicial);
	}

	@Override
	public void deshacer() throws ReinaException {
		try {
			Integer idRef = getNota().getIdReferencia();
			OrganoJudicial organoJudicial = getAdministrador().traerOrganoJudicial(idRef);
			
			OrganoJudicial organoJudicialOriginal = this.getProceso().getOrganoJudicialOriginal();
			if (this.getProceso().getOrganoJudicialOriginal().getId().equals(this.getProceso().getOrganoJudicial().getId()))
				organoJudicialOriginal = organoJudicial;
			
			cambioEnOrganoJudicial(organoJudicial, organoJudicialOriginal);
		} catch (NullPointerException e) {
			throw new ReinaException(ReinaCte.FALTA_INFORMACION_PARA_RETROCEDER_DECLINACION);
		} catch (ClassCastException e) {
			throw new ReinaException(ReinaCte.FALTA_INFORMACION_PARA_RETROCEDER_DECLINACION);
		}		 
	}

	public boolean requiereJuzgado() {
		return true;
	}
	
	private void cambioEnOrganoJudicial(OrganoJudicial organoJudicial, OrganoJudicial organoJudicialOriginal) {
		this.getProceso().setOrganoJudicial(organoJudicial);
		this.getProceso().setOrganoJudicialOriginal(organoJudicialOriginal);	
					
		for (MomentoProcesal momento : getProceso().getMomentosProcesales()) {
			if ( momento.getFechaImposicion().after(getNota().getFecha()) ) {
				momento.setJuzgado(organoJudicial);
			}
		}
		for (MedidaEnProcesoPenal medida : getProceso().getMedidasImpuestas()) {
			if ( medida.getFechaMedida().after(getNota().getFecha()) ) {
				medida.setAdoptaMedida(organoJudicial);
			}
		}
	}

}

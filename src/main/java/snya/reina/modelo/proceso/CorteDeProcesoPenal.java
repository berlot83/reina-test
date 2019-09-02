package snya.reina.modelo.proceso;

import java.io.Serializable;
import java.util.Date;


public class CorteDeProcesoPenal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5661881799995546318L;
	
	private ProcesoPenal proceso;
	private MotivoIntervencionEnProcesoPenal motivo;
	private MomentoProcesal momento;
	private MedidaEnProcesoPenal medida;
	private TipoDeSituacionProcesal situacionProcesal;

	
	public CorteDeProcesoPenal(ProcesoPenal proceso, MotivoIntervencionEnProcesoPenal motivo,
			MomentoProcesal momento, MedidaEnProcesoPenal medida, TipoDeSituacionProcesal situacionProcesal) {
		super();
		this.proceso = proceso;
		this.motivo = motivo;
		this.momento = momento;
		this.medida = medida;
		this.situacionProcesal = situacionProcesal;
	}

	public boolean cierraProceso() {
		return this.proceso.getEstaFinalizado();
	}
	
	
	public ProcesoPenal getProceso() {
		return proceso;
	}

	public MomentoProcesal getMomentoProcesal() {
		return momento;
	}
	
	public MotivoIntervencionEnProcesoPenal getMotivoIntervencion() {
		return motivo;
	}

	public MedidaEnProcesoPenal getMedidaImpuesta() {
		return medida;
	}

	public TipoDeSituacionProcesal getSituacionProcesal() {
		return situacionProcesal;
	}

	public Integer getIdFiscalia() {
		return (proceso.getFiscalia() == null) ?  null : proceso.getFiscalia().getId();
	}

	public Integer getIdOrganoJudicial() {
		return (proceso.getOrganoJudicial() == null) ?  null : proceso.getOrganoJudicial().getId();
	}
	
	public Boolean getDefensorOficial() {
		return proceso.isEsDefensorOficial();
	}
	
	public Integer getIdDefensoria() {
		return (proceso.getDefensoria() == null) ?  null : proceso.getDefensoria().getId();
	}

	public Integer getIdDefensorOficial() {
		return (proceso.getDefensor() == null) ?  null : proceso.getDefensor().getId();
	}
	
	public String getAbogado() {
		return proceso.getAbogado();
	}

	public String getIPP() {
		return proceso.getIPP();
	}
	
	public String getNroCarpeta() {
		return proceso.getNroCarpeta();
	}
	
	public String getNroCausa() {
		return proceso.getNroCausa();
	}
	
	public Integer getCaratula() {
		return motivo.getTipo().getId();
	}
	
	public Boolean getGradoTentativa() {
		return motivo.getGradoTentativa();
	}
	
	public String getObservacionCaratula() {
		return motivo.getObservacion();
	}
	
	public Date getFechaImposicion() {
		return proceso.getFechaIngreso();
	}
	
	public Integer getIdMomentoProcesal() {
		return momento.getTipo().getId();
	}

	public Integer getIdOrganoJudicialMedida() {
		return medida.getAdoptaMedida().getId();
	}

	public Integer getIdTipoMedida() {
		return medida.getTipo().getId();
	}

	public Integer[] getIdsMedidasImpuestas() {
		Integer[] ids = new Integer[medida.getDetalles().size()];
		int i = 0;
		
		for (DetalleMedidaEnProcesoPenal tipo : medida.getDetalles()) {
			ids[i] = tipo.getIdTipo();
			i++;
		}
		
		return ids;
	}
	
	public String getObservacionMedida() {
		return medida.getObservacion();
	}

	public Integer getTiempoDeHorasMedida() {
		return medida.getTiempoDeHorasMedida();
	}
	
	public Integer getTiempoDeDiasMedida() {
		return medida.getTiempoDeDiasMedida();
	}
	
	public Integer getTiempoDeMesesMedida() {
		return medida.getTiempoDeMesesMedida();
	}
	
	public Date getTiempoDeFechaFinMedida() {
		return medida.getTiempoDeFechaFinMedida();
	}
	
	public Date getFechaIngreso() {
		return proceso.getFechaIngreso();
	}

	public Date getFechaImposicionMedida() {
		return medida.getFechaMedida();
	}
}

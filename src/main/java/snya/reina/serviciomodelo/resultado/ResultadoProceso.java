package snya.reina.serviciomodelo.resultado;

import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Permanencia;
import snya.reina.modelo.proceso.MedidaEnProcesoPenal;
import snya.reina.modelo.proceso.ProcesoPenal;

public class ResultadoProceso {

	private boolean cierre;
	private ProcesoPenal proceso; 
	private MedidaEnProcesoPenal medida;
	private boolean cambiarProcesoMovimiento;
	//private boolean cambiarProcesoIntervencion;
	
	public ResultadoProceso(boolean cierre, ProcesoPenal proceso, MedidaEnProcesoPenal medida) {
		this.cierre = cierre;
		this.proceso = proceso;
		this.medida = medida;
	}

	public void analizarSiQuedaPresenteSinMedida(Joven joven) {
		if (this.cierre) {
			Permanencia permanencia = joven.traerUltimaPermanenciaDeCumplimientoAbierta();
			cambiarProcesoMovimiento = permanencia != null && permanencia.getProceso() != null && permanencia.getProceso().getId().equals(proceso.getId());
		}
	}
	
	public boolean cerrado() {
		return this.cierre;
	}
	
	public boolean cambiarProcesoMovimiento() {
		return this.cambiarProcesoMovimiento;
	}

	public Integer getIdMedida() {
		return this.medida.getId();
	}		
}

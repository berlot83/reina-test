package snya.reina.serviciomodelo.resultado;

import snya.reina.modelo.movimiento.Movimiento;

public class ResultadoMovimiento {

	private boolean exito;
	private String mensaje;
	private Movimiento movimiento;
	
	
	public ResultadoMovimiento(String mensaje) {
		this.exito = false;
		this.mensaje = mensaje;
		this.movimiento = null;
	}

	public ResultadoMovimiento(Movimiento movimiento) {
		this.exito = true;
		this.mensaje = null;
		this.movimiento = movimiento;
	}

	public boolean exitoso() {
		return this.exito;
	}

	public String getMensaje() {
		return this.mensaje;
	}
	
	public Movimiento getMovimiento() {
		return this.movimiento;
	}
}

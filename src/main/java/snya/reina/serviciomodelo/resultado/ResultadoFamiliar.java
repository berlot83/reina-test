package snya.reina.serviciomodelo.resultado;

import snya.reina.modelo.referente.Familiar;

public class ResultadoFamiliar {

	private boolean exito;
	private String mensaje;
	private Familiar familiar;
	
	
	public ResultadoFamiliar(String mensaje) {
		this.exito = false;
		this.mensaje = mensaje;
		this.familiar = null;
	}

	public ResultadoFamiliar(Familiar familiar) {
		this.exito = true;
		this.mensaje = null;
		this.familiar = familiar;
	}

	public boolean exitoso() {
		return this.exito;
	}

	public String getMensaje() {
		return this.mensaje;
	}
	
	public Familiar getFamiliar() {
		return this.familiar;
	}
}

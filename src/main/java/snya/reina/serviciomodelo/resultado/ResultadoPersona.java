package snya.reina.serviciomodelo.resultado;

import snya.reina.modelo.Persona;

public class ResultadoPersona {

	private boolean exito;
	private String mensaje;
	private Persona persona;
	
	
	public ResultadoPersona(String mensaje) {
		this.exito = false;
		this.mensaje = mensaje;
		this.persona = null;
	}

	public ResultadoPersona(Persona persona) {
		this.exito = true;
		this.mensaje = null;
		this.persona = persona;
	}

	public boolean exitoso() {
		return this.exito;
	}

	public String getMensaje() {
		return this.mensaje;
	}
	
	public Persona getPersona() {
		return this.persona;
	}
}

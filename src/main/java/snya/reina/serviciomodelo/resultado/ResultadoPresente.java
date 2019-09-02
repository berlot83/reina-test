package snya.reina.serviciomodelo.resultado;

import java.util.ArrayList;
import java.util.List;

import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.movimiento.Permanencia;

public class ResultadoPresente {

	private List<Permanencia> permanencias;
	

	public ResultadoPresente() {
		this.permanencias = new ArrayList<Permanencia>();
	}

	public ResultadoPresente(Permanencia presente) {
		this();
		this.permanencias.add(presente);
	}

	public void inscribirEnJoven(Joven joven) {
		for (Permanencia p : this.permanencias) {
			joven.getPermanencias().add(p);
			p.setJoven(joven);
		}
	}
}

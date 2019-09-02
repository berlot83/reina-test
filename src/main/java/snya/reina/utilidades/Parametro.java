package snya.reina.utilidades;

import java.util.HashMap;

public class Parametro {

	private String[] columnas;
	private Object[] valores;
	
	public Parametro(String[] columnas, Object[] valores) {
		this.columnas = columnas;
		this.valores = valores;
	}

	public String[] getColumnas() {
		return columnas;
	}
	
	public Object[] getValores() {
		return valores;
	}

	public void setValor(int i, Object valor) {
		valores[i] = valor;
	}
	
	
	public HashMap<String, Object> convertirParametro() {
		HashMap<String, Object> val = new HashMap<String, Object>();
		
		for (int i = 0; i < columnas.length; i++) {			
			val.put(columnas[i], valores[i]);	
		}

		return val;	
	}
}

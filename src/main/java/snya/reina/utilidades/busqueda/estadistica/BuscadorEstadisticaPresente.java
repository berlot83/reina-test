package snya.reina.utilidades.busqueda.estadistica;

import java.io.Serializable;

import snya.reina.utilidades.Parametro;

public class BuscadorEstadisticaPresente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6261589357739404607L;
	
	private String idTipo;
	private String idRecurso;
	private String idDptoJudicial;
	private String idOrganoJudicial;
	private String idDefensoria;
	private String idFiscalia;
	private String idMunicipio;
	private String idLocalidad;
	
	
	public BuscadorEstadisticaPresente(String idTipo, String idRecurso,
			String idDptoJudicial, String idOrganoJudicial,
			String idDefensoria, String idFiscalia, String idMunicipio,
			String idLocalidad) {
		this.idTipo = idTipo;
		this.idRecurso = idRecurso;
		this.idDptoJudicial = idDptoJudicial;
		this.idOrganoJudicial = idOrganoJudicial;
		this.idDefensoria = idDefensoria;
		this.idFiscalia = idFiscalia;
		this.idMunicipio = idMunicipio;
		this.idLocalidad = idLocalidad;
	}

	public Parametro calcularParametro() {
		String[] columnas = new String[8];
		String[] valores = new String[8];
		
		columnas[0] = "idTipo"; columnas[1] = "idRecurso"; columnas[2] = "idDptoJudicial";
		columnas[3] = "idOrganoJudicial"; columnas[4] = "idDefensoria"; columnas[5] = "idFiscalia";
		columnas[6] = "idMunicipio"; columnas[7] = "idLocalidad";

		valores[0] = idTipo; valores[1] = idRecurso; valores[2] = idDptoJudicial;
		valores[3] = idOrganoJudicial; valores[4] = idDefensoria; valores[5] = idFiscalia;
		valores[6] = idMunicipio; valores[7] = idLocalidad;
		
		
		return new Parametro(columnas, valores);
	}
	
	
}

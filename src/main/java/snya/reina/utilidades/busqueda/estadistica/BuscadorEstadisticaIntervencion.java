package snya.reina.utilidades.busqueda.estadistica;

import java.io.Serializable;
import java.util.Date;

import snya.reina.modelo.Calendario;
import snya.reina.utilidades.Parametro;

public class BuscadorEstadisticaIntervencion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7920221983376261843L;
	
	private String idRecurso;
	private String idDptoJudicial;
	private String idOrganoJudicial;
	private Date fechaDesde;
	private Date fechaHasta;
	private String idMunicipio;
	private String idEstado;
	
	public BuscadorEstadisticaIntervencion(String idRecurso, String idDptoJudicial, String idOrganoJudicial,
			Date fechaDesde, Date fechaHasta, String idMunicipio, Integer idEstado) {
		this.idRecurso = idRecurso;
		this.idDptoJudicial = idDptoJudicial;
		this.idOrganoJudicial = idOrganoJudicial;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.idMunicipio = idMunicipio;
		this.idEstado = (idEstado == null) ? null : idEstado.toString();
	}

	public Parametro calcularParametro() {
		String[] columnas = new String[7];
		String[] valores = new String[7];
		
		columnas[0] = "idRecurso"; columnas[1] = "idDptoJudicial";
		columnas[2] = "idOrganoJudicial"; columnas[3] = "fechaDesde";
		columnas[4] = "fechaHasta"; columnas[5] = "idMunicipio";		
		columnas[6] = "idEstado"; 
		
		valores[0] = idRecurso; valores[1] = idDptoJudicial;
		valores[2] = idOrganoJudicial; 
		valores[3] = Calendario.formatearFechaMySql(fechaDesde); valores[4] = Calendario.formatearFechaMySql(fechaHasta);
		valores[5] = idMunicipio; valores[6] = idEstado;
		
		
		return new Parametro(columnas, valores);
	}
	
	
}

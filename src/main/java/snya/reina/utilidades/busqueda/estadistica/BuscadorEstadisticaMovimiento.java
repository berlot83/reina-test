package snya.reina.utilidades.busqueda.estadistica;

import java.io.Serializable;
import java.util.Date;

import snya.reina.modelo.Calendario;
import snya.reina.utilidades.Parametro;

public class BuscadorEstadisticaMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7693975198312514598L;
	
	private String idTipoOrigen;
	private String idRecursoOrigen;
	private String idTipoDestino;
	private String idRecursoDestino;
	private String idGrupoDeMovimiento;
	private String idTipoDeMovimiento; 
	private String idDptoJudicial;
	private String idOrganoJudicial;
	private Date fechaDesde;
	private Date fechaHasta;
	private String idMunicipio;
	private String idLocalidad;
	
	
	public BuscadorEstadisticaMovimiento(String idTipoOrigen, String idRecursoOrigen,
			String idTipoDestino, String idRecursoDestino,
			String idGrupoDeMovimiento, String idTipoDeMovimiento, 
			String idDptoJudicial, String idOrganoJudicial, Date fechaDesde, Date fechaHasta, 
			String idMunicipio, String idLocalidad) {
		this.idTipoOrigen = idTipoOrigen;
		this.idRecursoOrigen = idRecursoOrigen;
		this.idTipoDestino = idTipoDestino;
		this.idRecursoDestino = idRecursoDestino;
		this.idGrupoDeMovimiento = idGrupoDeMovimiento;
		this.idTipoDeMovimiento = idTipoDeMovimiento; 
		this.idDptoJudicial = idDptoJudicial;
		this.idOrganoJudicial = idOrganoJudicial;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.idMunicipio = idMunicipio;
		this.idLocalidad = idLocalidad;
	}

	public Parametro calcularParametro() {
		String[] columnas = new String[12];
		String[] valores = new String[12];
		
		columnas[0] = "idTipoOrigen"; columnas[1] = "idRecursoOrigen";
		columnas[2] = "idTipoDestino"; columnas[3] = "idRecursoDestino";
		columnas[4] = "idGrupoDeMovimiento"; columnas[5] = "idTipoDeMovimiento";
		columnas[6] = "idDptoJudicial"; columnas[7] = "idOrganoJudicial";		
		columnas[8] = "fechaDesde"; columnas[9] = "fechaHasta";		
		columnas[10] = "idMunicipio"; columnas[11] = "idLocalidad";

		valores[0] = idTipoOrigen; valores[1] = idRecursoOrigen;
		valores[2] = idTipoDestino; valores[3] = idRecursoDestino;
		valores[4] = idGrupoDeMovimiento; valores[5] = idTipoDeMovimiento;
		valores[6] = idDptoJudicial; valores[7] = idOrganoJudicial;		
		valores[8] = Calendario.formatearFechaMySql(fechaDesde); valores[9] = Calendario.formatearFechaMySql(fechaHasta);		
		valores[10] = idMunicipio; valores[11] = idLocalidad;
		
		
		return new Parametro(columnas, valores);
	}
	
	
}

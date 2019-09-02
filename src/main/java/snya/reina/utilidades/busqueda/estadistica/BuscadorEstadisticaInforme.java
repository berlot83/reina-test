package snya.reina.utilidades.busqueda.estadistica;

import java.io.Serializable;
import java.util.Date;

import snya.reina.modelo.Calendario;
import snya.reina.utilidades.Parametro;

public class BuscadorEstadisticaInforme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2676668351273990993L;
	
	private String idTipoRecurso;
	private String idRecurso;
	private String idGrupo;
	private String idTipo;
	private Date fechaDesde;
	private Date fechaHasta;
	
	
	public BuscadorEstadisticaInforme(String idTipoRecurso, String idRecurso, String idGrupo, String idTipo,
			Date fechaDesde, Date fechaHasta) {
		this.idTipoRecurso = idTipoRecurso;
		this.idRecurso = idRecurso;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.idGrupo = idGrupo;
		this.idTipo = idTipo;
	}

	public Parametro calcularParametro() {
		String[] columnas = new String[6];
		String[] valores = new String[6];
		
		columnas[0] = "idTipoRecurso"; columnas[1] = "idRecurso";
		columnas[2] = "idGrupo"; columnas[3] = "idTipo";		
		columnas[4] = "fechaDesde"; columnas[5] = "fechaHasta";
		
		valores[0] = idTipoRecurso; valores[1] = idRecurso;
		valores[2] = idGrupo; valores[3] = idTipo;  
		valores[4] = Calendario.formatearFechaMySql(fechaDesde); valores[5] = Calendario.formatearFechaMySql(fechaHasta);		
		
		return new Parametro(columnas, valores);
	}
	
	
}

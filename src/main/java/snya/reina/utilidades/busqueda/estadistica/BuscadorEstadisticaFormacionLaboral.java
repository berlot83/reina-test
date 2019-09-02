package snya.reina.utilidades.busqueda.estadistica;

import java.io.Serializable;

import snya.reina.utilidades.Parametro;

public class BuscadorEstadisticaFormacionLaboral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4764012212948974479L;
	
	private String idTipoRecurso;
	private String idRecurso;
	private Integer idFormacion;
	private Integer idEstado;
	
	
	public BuscadorEstadisticaFormacionLaboral(String idTipoRecurso, String idRecurso, Integer idFormacion, Integer idEstado) {
		this.idTipoRecurso = idTipoRecurso;
		this.idRecurso = idRecurso;
		this.idFormacion = (idFormacion != null && idFormacion != 0) ? idFormacion : null;
		this.idEstado = (idEstado != null && idEstado != 0) ? idEstado : null;
	}

	public Parametro calcularParametro() {
		String[] columnas = new String[5];
		Object[] valores = new Object[5];
		
		columnas[0] = "idTipo"; columnas[1] = "idRecurso";
		columnas[2] = "idFormacion"; columnas[3] = "idDictado"; 
		columnas[4] = "idEstado";
		
		valores[0] = idTipoRecurso; valores[1] = idRecurso;
		valores[2] = idFormacion; valores[3] = null;
		valores[4] = idEstado;
		
		return new Parametro(columnas, valores);
	}
}

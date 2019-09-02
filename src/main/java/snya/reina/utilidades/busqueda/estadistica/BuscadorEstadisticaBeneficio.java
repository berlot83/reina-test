package snya.reina.utilidades.busqueda.estadistica;

import java.io.Serializable;

import snya.reina.utilidades.Parametro;

public class BuscadorEstadisticaBeneficio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5148992008701649448L;
	
	private String idTipoRecurso;
	private String idRecurso;
	private Integer idBeneficio;
	private Integer idEstado;
	
	
	public BuscadorEstadisticaBeneficio(String idTipoRecurso, String idRecurso, Integer idBeneficio, Integer idEstado) {
		this.idTipoRecurso = idTipoRecurso;
		this.idRecurso = idRecurso;
		this.idBeneficio = (idBeneficio != null && idBeneficio != 0) ? idBeneficio : null;
		this.idEstado = (idEstado != null && idEstado != 0) ? idEstado : null;
	}

	public Parametro calcularParametro() {
		String[] columnas = new String[4];
		Object[] valores = new Object[4];
		
		columnas[0] = "idTipo"; columnas[1] = "idRecurso";
		columnas[2] = "idBeneficio"; columnas[3] = "idEstado";
		
		valores[0] = idTipoRecurso; valores[1] = idRecurso;
		valores[2] = idBeneficio; valores[3] = idEstado;
		
		return new Parametro(columnas, valores);
	}
}

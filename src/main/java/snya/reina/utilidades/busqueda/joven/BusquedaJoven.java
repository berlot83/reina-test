package snya.reina.utilidades.busqueda.joven;

import java.io.Serializable;

import snya.reina.ReinaException;
import snya.reina.utilidades.Parametro;

public abstract class BusquedaJoven implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055534910796872412L;
	
	private boolean reporte;
	private boolean restriccion;
	private boolean restriccionEdad;
	private String dato;
	private Integer[] ambitos;	
	private Integer[] tipos;

	public BusquedaJoven(String dato, Integer[] tipos, Integer[] ambitos, boolean restriccion, boolean restriccionEdad, boolean reporte) {
		this.setDato(dato);
		this.ambitos = ambitos;
		this.tipos = tipos;
		this.reporte = reporte;
		this.restriccionEdad = restriccionEdad;
		this.restriccion = restriccion;
	}

	protected BusquedaJoven(Integer[] tipos, Integer[] ambitos, boolean reporte) {
		this.ambitos = ambitos;
		this.tipos = tipos;
		this.reporte = reporte;
	}
	
	public Parametro calcularParametro(String propiedad, String orden) throws ReinaException {
		Parametro param = this.nuevaParametrizacion(propiedad, orden);
		
		String idTipos = (this.traerTipos().length <= 0 || !this.tieneAmbitos()) ? null : armarIds(this.traerTipos());
		String ids = (this.traerAmbitos().length <= 0 || !this.tieneAmbitos()) ? null : armarIds(this.traerAmbitos());
		param.setValor(0, idTipos);
		param.setValor(1, ids);
		
		this.acumularParametro(param);
		
		return param;
	}
	
	public Parametro calcularParametro() throws ReinaException {
		Parametro param;
		// Columnas - Parametros
		String[] columnas = new String[15];
		columnas[0] = "idTipo"; columnas[1] = "idRecurso"; columnas[2] = "apellidos"; columnas[3] = "nombres"; columnas[4] = "nombreCompleto"; columnas[5] = "documento"; columnas[6] = "expediente"; 
		columnas[7] = "nroJudicial"; columnas[8] = "dptoJudicial"; columnas[9] = "idOrganoJudicial"; columnas[10] = "idDefensoria"; columnas[11] = "idFiscalia"; 
		columnas [12]="nroPaquete"; columnas[13]="fechaPaquete"; columnas[14]="restriccionEdad";
		// Valores - Filtros
		Object[] valores = new Object[15];
		valores[0] = null; valores[1] = null; valores[2] = null; valores[3] = null;; valores[4] = null; valores[5] = null; valores[6] = null; 
		valores[7] = null; valores[8] = null; valores[9] = null; valores[10] = null;  valores[11] = null; valores[12] = null; valores[13] = null; valores[14] = this.restriccionEdad;
		
		param = new Parametro(columnas, valores);
		
		String idTipos = (this.traerTipos().length <= 0 || !this.tieneAmbitos()) ? null : armarIds(this.traerTipos());
		String ids = (this.traerAmbitos().length <= 0 || !this.tieneAmbitos()) ? null : armarIds(this.traerAmbitos());
		param.setValor(0, idTipos);
		param.setValor(1, ids);
		
		this.acumularParametro(param);
		
		return param;		
	}
	
	protected abstract void acumularParametro(Parametro param) throws ReinaException;
	
	private Parametro nuevaParametrizacion(String propiedad, String orden) {
		// Columnas - Parametros
		String[] columnas = new String[17];
		columnas[0] = "idTipo"; columnas[1] = "idRecurso"; columnas[2] = "apellidos"; columnas[3] = "nombres"; columnas[4] = "nombreCompleto"; columnas[5] = "documento"; columnas[6] = "expediente"; 
		columnas[7] = "nroJudicial"; columnas[8] = "dptoJudicial"; columnas[9] = "idOrganoJudicial"; columnas[10] = "idDefensoria"; columnas[11] = "idFiscalia"; 
		columnas [12]="nroPaquete"; columnas[13]="fechaPaquete"; columnas[14]="restriccionEdad"; columnas[15] = "propiedad"; columnas[16] = "orden";
		// Valores - Filtros
		Object[] valores = new Object[17];
		valores[0] = null; valores[1] = null; valores[2] = null; valores[3] = null;; valores[4] = null; valores[5] = null; valores[6] = null; 
		valores[7] = null; valores[8] = null; valores[9] = null; valores[10] = null;  valores[11] = null; 
		valores[12] = null; valores[13] = null; valores[14] = this.restriccionEdad; valores[15] = propiedad; valores[16] = orden;
		
		return new Parametro(columnas, valores);
	};
	
	protected String armarIds(Integer[] recursos) {
		String ids = "";
		for (int i = 0; i < recursos.length; i++) {
			ids += recursos[i] + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		return ids;
	}
	

	protected boolean tieneAmbitos() {
		return restriccion;
	}
	
	protected void setRestriccion(boolean restriccion) {
		this.restriccion = restriccion;
	}
	
	public boolean getRestriccionEdad() {
		return restriccionEdad;
	}

	public void setRestriccionEdad(boolean restringirEdad) {
		this.restriccionEdad = restringirEdad;
	}

	protected Integer[] traerAmbitos() {
		return this.ambitos;
	}
	
	protected Integer[] traerTipos() {
		return this.tipos;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}
	
	public boolean isReporte() {
		return reporte;
	}

	public void setReporte(boolean reporte) {
		this.reporte = reporte;
	}
}
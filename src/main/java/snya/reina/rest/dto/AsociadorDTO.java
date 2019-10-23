package snya.reina.rest.dto;

/* Este objeto sólo se utiliza para tomar los datos de un front y enviarlos como parámetro a un endpoint por RequestBody */
public class AsociadorDTO {
	private String legajo;
	private Integer idCaratulador;

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public Integer getIdCaratulador() {
		return idCaratulador;
	}

	public void setIdCaratulador(Integer idCaratulador) {
		this.idCaratulador = idCaratulador;
	}
}

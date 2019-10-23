package snya.reina.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntervencionDTO {
	@JsonProperty(value="Id")
	private String id;
	@JsonProperty(value="Fecha")
	private String fecha;
	@JsonProperty(value="Zonal")
	private String zonal;
	@JsonProperty(value="Local")
	private String local;
	@JsonProperty(value="Derivado_Por")
	private String derivadoPor;
	@JsonProperty(value="Motivo")
	private String motivo;
	@JsonProperty(value="Nombre_Estado")
	private String nombreEstado;

	public IntervencionDTO(String id, String fecha, String zonal, String local, String derivadoPor, String motivo,
			String nombreEstado) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.zonal = zonal;
		this.local = local;
		this.derivadoPor = derivadoPor;
		this.motivo = motivo;
		this.nombreEstado = nombreEstado;
	}
	
	public IntervencionDTO() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getZonal() {
		return zonal;
	}
	public void setZonal(String zonal) {
		this.zonal = zonal;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getDerivadoPor() {
		return derivadoPor;
	}
	public void setDerivadoPor(String derivadoPor) {
		this.derivadoPor = derivadoPor;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

}

package snya.reina.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AmbitoDTO {
	@JsonProperty(value = "Id")
	private String id;
	@JsonProperty(value = "Ambito")
	private String ambito;
	@JsonProperty(value = "Lugar_de_Cumplimiento")
	private String lugarDeCumplimiento;
	@JsonProperty(value = "Direccion")
	private String direccion;
	@JsonProperty(value = "Telefono")
	private String telefono;
	@JsonProperty(value = "Municipio")
	private String municipio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getLugarDeCumplimiento() {
		return lugarDeCumplimiento;
	}

	public void setLugarDeCumplimiento(String lugarDeCumplimiento) {
		this.lugarDeCumplimiento = lugarDeCumplimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
}

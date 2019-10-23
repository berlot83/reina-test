package snya.reina.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FamiliarDTO {
	
	@JsonProperty(value="Nombre")
	private String nombre;
	@JsonProperty(value="Documento")
	private String documento;
	@JsonProperty(value="Telefono")
	private String telefono;
	@JsonProperty(value="Direccion")
	private String direccion;
	@JsonProperty(value="Provincia")
	private String provincia;
	@JsonProperty(value="Municipio")
	private String municipio;
	@JsonProperty(value="Localidad")
	private String localidad;
	@JsonProperty(value="Barrio")
	private String barrio;
	@JsonProperty(value="Vinculo")
	private String vinculo;
	
	public FamiliarDTO() {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String getVinculo() {
		return vinculo;
	}
	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}
	
}

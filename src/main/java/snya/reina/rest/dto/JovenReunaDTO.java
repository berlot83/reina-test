package snya.reina.rest.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/* Estructura de JSON del Ruena - sin objetos internos - FLAT - todo String para capturar los datos únicamente */
@JsonInclude(Include.NON_NULL)
public class JovenReunaDTO{
	/* Serializado nativo por Jackson */
	@JsonProperty(value="Id")
	private Integer id;
	@JsonProperty(value="Legajo")
	private String legajo;
	@JsonProperty(value="Nombre")
	private String nombre;
	@JsonProperty(value="Fecha_Nac")
	private String fechaNac;
	@JsonProperty(value="Tiene_Doc")
	private String tieneDoc;
	@JsonProperty(value="Documento")
	private String documento;
	@JsonProperty(value="Sdocumento")
	private String sDocumento;
	@JsonProperty(value="Cuil")
	private String cuil;
	@JsonProperty(value="Sexo")
	private String sexo;
	@JsonProperty(value="Nacionalidad")
	private String nacionalidad;
	@JsonProperty(value="Municipio")
	private String municipio;
	@JsonProperty(value="Provincia")
	private String provincia;
	@JsonProperty(value="Direccion")
	private String direccion;
	@JsonProperty(value="Telefono")
	private String telefono;
	@JsonProperty(value="Zonal")
	private String zonal;
	@JsonProperty(value="Local")
	private String local;
	@JsonProperty(value="Familiares")
	private List<FamiliarDTO> familiaresDTO;
	@JsonProperty(value="ambitos")
	private List<String> ambitos;
	@JsonProperty(value="intervenciones")
	private List<IntervencionDTO> intervencionesDTO;
	
	public JovenReunaDTO() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getTieneDoc() {
		return tieneDoc;
	}

	public void setTieneDoc(String tieneDoc) {
		this.tieneDoc = tieneDoc;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getsDocumento() {
		return sDocumento;
	}

	public void setsDocumento(String sDocumento) {
		this.sDocumento = sDocumento;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
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

	public List<FamiliarDTO> getFamiliaresDTO() {
		return familiaresDTO;
	}

	public void setFamiliaresDTO(List<FamiliarDTO> familiaresDTO) {
		this.familiaresDTO = familiaresDTO;
	}

	public List<String> getAmbitos() {
		return ambitos;
	}

	public void setAmbitos(List<String> ambitos) {
		this.ambitos = ambitos;
	}

	public List<IntervencionDTO> getIntervencionesDTO() {
		return intervencionesDTO;
	}

	public void setIntervencionesDTO(List<IntervencionDTO> intervencionesDTO) {
		this.intervencionesDTO = intervencionesDTO;
	}

}

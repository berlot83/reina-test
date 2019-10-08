package snya.reina.rest.seguridad.modelo;

public class Token {

	private String nombre;
	private String apellido;
	private String email;
	private String rol;
	private String sector;
	private String proveedorSeguridad;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getProveedorSeguridad() {
		return proveedorSeguridad;
	}

	public void setProveedorSeguridad(String proveedorSeguridad) {
		this.proveedorSeguridad = proveedorSeguridad;
	}

}

package snya.reina.rest.seguridad.modelo;

import javax.servlet.http.HttpSession;

public class UsuarioToken {
	private Integer id;
	private String nombre;
	private String apellido;
	private String usuario;
	private String email;
	private String rol;
	private String sector;
	private String proveedorSeguridad;
	private HttpSession session;

	public UsuarioToken() {
		
	}
	
	public UsuarioToken(HttpSession session) {
		this.setSession(session);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}

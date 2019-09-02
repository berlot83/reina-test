package snya.reina.rest.dto;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import snya.reina.modelo.informe.Informe;

/* Lógicamente extender de Informe sería ideal sin embargo algunos tipos de dato no se puede sobreeescribir como atributo, ejemplo TipoDocumento no se puede sobreeescribir como String */
public class InformeDTO {

	private Integer id;
	private String tipo;
	private Integer idJoven;
	private String institucion;
	private Date fechaInforme;
	private String texto;
	private String autores;
	private boolean archivo;
	private List<URL> listaUrlArchivos = new ArrayList<>();

	public InformeDTO() {

	}

	/* Creamos el objeto InformeDTO resumido sin objetos */
	public InformeDTO(Informe informe) {
		this.id = informe.getId();
		this.tipo = informe.getTipo().getNombre();
		this.idJoven = informe.getJoven().getId();
		this.institucion = informe.getInstitucion().getNombre();
		this.fechaInforme = informe.getFechaInforme();
		this.texto = informe.getObservacion();
		this.autores = informe.getAutores();
		this.archivo = informe.getArchivo();
		/*
		 * Notese que no está la lista de direcciones URL de los archivos, hay que
		 * setearla con los métodos de abajo una vez creado el objeto
		 */
	}

	public void agregarUrlArchivos(URL url) {
		this.listaUrlArchivos.add(url);
	}

	public void agregarUrlArchivos(List<URL> urls) {
		this.listaUrlArchivos.addAll(urls);
	}

	public void removerTodos(List<URL> urls) {
		this.listaUrlArchivos.removeAll(urls);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer idJoven) {
		this.idJoven = idJoven;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Date getFechaInforme() {
		return fechaInforme;
	}

	public void setFechaInforme(Date fechaInforme) {
		this.fechaInforme = fechaInforme;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public boolean isArchivo() {
		return archivo;
	}

	public void setArchivo(boolean archivo) {
		this.archivo = archivo;
	}

	public List<URL> getListaUrlArchivos() {
		return listaUrlArchivos;
	}

	public void setListaUrlArchivos(List<URL> listaUrlArchivos) {
		this.listaUrlArchivos = listaUrlArchivos;
	}

	/*
	 * Ejemplo de funcionamiento de la Lista URL - es un validador NATIVA de la Java
	 * API
	 */
	public static void main(String[] args) throws MalformedURLException {
		InformeDTO informe = new InformeDTO();
		/*
		 * Ejemplos de armado de URL - Si se da arranque al main sin descomentar las
		 * líneas de abajo deberian imprimir las URL
		 */
		informe.listaUrlArchivos.add(new URL("http://yahoo.com"));
		informe.listaUrlArchivos.add(new URL("http://bing.com"));
		informe.listaUrlArchivos.add(new URL("http://google.com"));
		informe.listaUrlArchivos.add(new URL("http://localhost:8080/api/jovenes/simple?buscar=edua/archivoX.doc"));
		informe.listaUrlArchivos.add(new URL("ftp://localhost:8080/api/jovenes/simple?buscar=edua/archivoX.doc"));

		/* Ejemplo remover todos los URL */
		// listaUrlArchivos.removerTodos(informe.archivos);

		/*
		 * Ejemplos de mal armado de URL - si se descomenta esta línea y se da arranque
		 * al main va a tirar excepción
		 */
		// informe.listaUrlArchivos.add(new
		// URL("localhost:8080/api/jovenes/simple?buscar=edua/archivoX.doc"));
		// informe.listaUrlArchivos.add(new
		// URL("../api/jovenes/simple?buscar=edua/archivoX.doc"));

		for (URL item : informe.listaUrlArchivos) {
			System.out.println(item);
		}
	}
}

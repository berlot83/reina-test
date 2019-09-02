package snya.reina.modelo.informe;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Reina_Informe_TipoDeInforme", catalog="SistemasSNYA")
public class TipoDeInforme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298050645674458737L;

	public final static Integer ID_ACTA_CONJUNTA = 40;
	public final static Integer ID_FICHA_SALUD = 41;
	public final static Integer ID_INFORME_TECNICO = 42;
	public final static Integer ID_FICHA_PERTENENCIAS = 43;
	public final static Integer ID_ACTA_DOCUMENTACION = 45;
	public final static Integer ID_OFICIO_DOCUMENTACION = 46;
	public final static Integer ID_SOLICITUD_INTERVENCION = 47;
	public final static Integer ID_ACTA_EGRESO = 48;
	public final static Integer ID_COMUNICACION_MOVIMIENTO = 49;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeInforme")
	private Integer id;
	
	@Column(name="TipoDeInforme")
	private String nombre;
	
	@Column(name="Nota")
	private String nota;
	
	@Column(name="TieneProceso")
	private Boolean tieneProceso;
	
	@Column(name="TieneReferente")
	private Boolean tieneReferente;
	
	@Column(name="TienePrograma")
	private Boolean tienePrograma;
	
	@Column(name="TieneEducativo")
	private Boolean tieneEducativo;
	
	@Column(name="TieneSalud")
	private Boolean tieneSalud;
	
	@ManyToOne
	@JoinColumn(name="IdGrupoDeInforme")
	private GrupoDeInforme grupo;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	@Column(name="PorDefecto")
	private Boolean porDefecto;

	@Column(name="IdTipoDeInstitucion")
	private String tipos;
	
	@Column(name="RequiereHora")
	private Boolean requiereHora;
	
	@Column(name="TiempoCierre")
	private Integer tiempoCierre;	
	
	@Column(name="UsaPredeterminado")
	private Boolean usaPredeterminado;	

	@Column(name="Descripcion")
	private String descripcion;
	
	
	public TipoDeInforme() {
		this.tipos = null;
		this.requiereHora = false;
		this.tiempoCierre = 5;
		this.usaPredeterminado = false;
	}
	
	public TipoDeInforme(GrupoDeInforme grupo, String nombre, 
			Boolean requiereHora, Boolean usoPredeterminado, Integer tiempoCierre, 
			String descripcion, String nota) {
		this();
		this.nombre = nombre;
		this.requiereHora = requiereHora;
		this.usaPredeterminado = usoPredeterminado;
		this.tiempoCierre = tiempoCierre;
		this.descripcion = descripcion;
		this.nota = nota;
		this.grupo = grupo;
		this.estaActivo = true;
		this.porDefecto = false;
		this.inicializarBanderas(nota);
	}

	public void modificarNota(String nota) {
		this.nota = nota;
		this.inicializarBanderas(nota);
	}
	
	public boolean getTieneDatoAdicional() {
		return this.tieneEducativo || this.tieneProceso || this.tienePrograma || this.tieneReferente || this.tieneSalud;
	}
	
	private void inicializarBanderas(String nota) {
		Pattern p;
		p = Pattern.compile("\\[:Proceso_(\\w+):\\]");
		this.tieneProceso = p.matcher(nota).find();
		
		p = Pattern.compile("\\[:Referente_(\\w+):\\]");
		this.tieneReferente = p.matcher(nota).find();
		
		p = Pattern.compile("\\[:Programa_(\\w+):\\]");
		this.tienePrograma = p.matcher(nota).find();
		
		p = Pattern.compile("\\[:Educacion_(\\w+):\\]");		
		this.tieneEducativo = p.matcher(nota).find();
		
		p = Pattern.compile("\\[:Salud_(\\w+):\\]");
		this.tieneSalud = p.matcher(nota).find();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Boolean getPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(Boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Boolean getTieneProceso() {
		return tieneProceso;
	}

	public void setTieneProceso(Boolean tieneProceso) {
		this.tieneProceso = tieneProceso;
	}

	public Boolean getTieneReferente() {
		return tieneReferente;
	}

	public void setTieneReferente(Boolean tieneReferente) {
		this.tieneReferente = tieneReferente;
	}

	public Boolean getTienePrograma() {
		return tienePrograma;
	}

	public void setTienePrograma(Boolean tienePrograma) {
		this.tienePrograma = tienePrograma;
	}

	public Boolean getTieneEducativo() {
		return tieneEducativo;
	}

	public void setTieneEducativo(Boolean tieneEducativo) {
		this.tieneEducativo = tieneEducativo;
	}

	public GrupoDeInforme getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDeInforme grupo) {
		this.grupo = grupo;
	}

	public Boolean getTieneSalud() {
		return tieneSalud;
	}

	public void setTieneSalud(Boolean tieneSalud) {
		this.tieneSalud = tieneSalud;
	}

	public String getTipos() {
		return tipos;
	}

	public void setTipos(String tipos) {
		this.tipos = tipos;
	}

	public Boolean getRequiereHora() {
		return requiereHora;
	}

	public void setRequiereHora(Boolean requiereHora) {
		this.requiereHora = requiereHora;
	}

	public Integer getTiempoCierre() {
		return tiempoCierre;
	}

	public void setTiempoCierre(Integer tiempoCierre) {
		this.tiempoCierre = tiempoCierre;
	}

	public Boolean getUsaPredeterminado() {
		return usaPredeterminado;
	}

	public void setUsaPredeterminado(Boolean usaPredeterminado) {
		this.usaPredeterminado = usaPredeterminado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}

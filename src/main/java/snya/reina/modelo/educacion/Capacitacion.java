package snya.reina.modelo.educacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.recurso.Recurso;

@Entity
@Table(name = "Reina_Capacitacion", catalog = "SistemasSNYA")
@Audited
public class Capacitacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536764834743692632L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdCapacitacion")
	private Integer id;

	@Column(name = "Capacitacion")
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "IdTipoDeCapacitacion")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeCapacitacion tipo; // Curso, Form Prof , Talleres, .....

	@ManyToOne
	@JoinColumn(name = "IdTematicaDeCapacitacion")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TematicaDeCapacitacion tematica; // Arte, madera ....

	@Column(name = "TieneCertificacion")
	private boolean tieneCertificacion; // Sin o con certificado

	@ManyToOne
	@JoinColumn(name = "IdInstitucionSupervisa")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Institucion supervisa;

	@Column(name = "TieneInscripcionCentralizada")
	private boolean tieneInscripcionCentralizada;

	@Column(name = "InstitucionFinancia")
	private String institucionFinancia;

	@Column(name = "InstitucionEjecutora")
	private String institucionEjecutora;

	@Column(name = "Duracion")
	private Integer duracion;	
	
	@Column(name = "Descripcion")
	private String descripcion;	

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="capacitacion")
	private List<Dictado> dictados;

	@Column(name="EsFormacionLaboral")
	private Boolean esFormacionLaboral;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;

	@ManyToOne
	@JoinColumn(name = "IdTipoDeBeneficio")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeBeneficio beneficio;
	
	
	public Capacitacion() {
		this.dictados = new ArrayList<Dictado>();
		this.estaActivo = true;
	}
	
	
	public Capacitacion(String nombre, TipoDeCapacitacion tipo, TematicaDeCapacitacion tematica,
			boolean tieneCertificacion, Institucion supervisa, boolean tieneInscripcionCentralizada,
			String institucionFinancia, String institucionEjecutora, Integer duracion, String descripcion, boolean esFormacionLaboral, TipoDeBeneficio beneficio) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.tematica = tematica;
		this.tieneCertificacion = tieneCertificacion;
		this.supervisa = supervisa;
		this.tieneInscripcionCentralizada = tieneInscripcionCentralizada;
		this.institucionFinancia = institucionFinancia;
		this.institucionEjecutora = institucionEjecutora;
		this.duracion = duracion;
		this.descripcion = descripcion;
		this.esFormacionLaboral = esFormacionLaboral;
		this.estaActivo = true;
		this.beneficio = beneficio;
		
		this.dictados = new ArrayList<Dictado>();
	}

	
	public Dictado traerDictado(Integer id) {
		Dictado dic = null;
		
		for (Dictado dictado : dictados) {
			if (dictado.getId().equals(id)) {
				dic = dictado;
				
		        for (Recurso recurso : dic.getInstituciones()) {
					recurso.getComponente().getNombre();
				}
			}
		}
		
		return dic;
	}
	
	public void agregarDictado(String nombre, List<Recurso> recursos, Date fInicio, Date fFin, String descripcion) {
		
		Dictado dic = new Dictado(this, nombre, recursos, fInicio, fFin, descripcion);
		this.getDictados().add(dic);
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

	public TipoDeCapacitacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeCapacitacion tipo) {
		this.tipo = tipo;
	}

	public TematicaDeCapacitacion getTematica() {
		return tematica;
	}

	public void setTematica(TematicaDeCapacitacion tematica) {
		this.tematica = tematica;
	}

	public boolean getTieneCertificacion() {
		return tieneCertificacion;
	}

	public void setTieneCertificacion(boolean tieneCertificacion) {
		this.tieneCertificacion = tieneCertificacion;
	}

	public Institucion getSupervisa() {
		return supervisa;
	}

	public void setSupervisa(Institucion supervisa) {
		this.supervisa = supervisa;
	}

	public boolean isTieneInscripcionCentralizada() {
		return tieneInscripcionCentralizada;
	}

	public void setTieneInscripcionCentralizada(boolean tieneInscripcionCentralizada) {
		this.tieneInscripcionCentralizada = tieneInscripcionCentralizada;
	}

	public String getInstitucionFinancia() {
		return institucionFinancia;
	}

	public void setInstitucionFinancia(String institucionFinancia) {
		this.institucionFinancia = institucionFinancia;
	}

	public String getInstitucionEjecutora() {
		return institucionEjecutora;
	}

	public void setInstitucionEjecutora(String institucionEjecutora) {
		this.institucionEjecutora = institucionEjecutora;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Dictado> getDictados() {
		return dictados;
	}

	public void setDictados(List<Dictado> dictados) {
		this.dictados = dictados;
	}

	public Boolean getEsFormacionLaboral() {
		return esFormacionLaboral;
	}

	public void setEsFormacionLaboral(Boolean esFormacionLaboral) {
		this.esFormacionLaboral = esFormacionLaboral;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
	
	public TipoDeBeneficio getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(TipoDeBeneficio beneficio) {
		this.beneficio = beneficio;
	}
}
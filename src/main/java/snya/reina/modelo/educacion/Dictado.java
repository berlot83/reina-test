package snya.reina.modelo.educacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.reina.modelo.recurso.Recurso;

@Entity
@Table(name="Reina_Dictado", catalog="SistemasSNYA")
@Audited
public class Dictado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294463616826064546L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdDictado")
	private Integer id;

	@Column(name = "Dictado")
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "IdCapacitacion")
	private Capacitacion capacitacion;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="Reina_DictadoEnRecurso",
			joinColumns=@JoinColumn(name="IdDictado", referencedColumnName="IdDictado"),
			inverseJoinColumns=@JoinColumn(name="IdRecurso", referencedColumnName="IdRecurso"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private java.util.List<Recurso> instituciones;
	
	@Column(name = "Descripcion")
	private String descripcion;	
	
	@Column(name="FechaInicio")
	private Date fechaInicio;
	
	@Column(name="FechaFin")
	private Date fechaFin;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;

	
	public Dictado() {
		this.instituciones = new ArrayList<Recurso>();
		this.estaActivo = true;
	}
	
	public Dictado(Capacitacion capacitacion, String nombre, List<Recurso> recursos, Date fInicio, Date fFin,
			String descripcion) {
		this.capacitacion = capacitacion;
		this.nombre = nombre;
		this.instituciones = new ArrayList<Recurso>();
		this.instituciones.addAll(recursos);
		this.fechaInicio = fInicio;
		this.fechaFin = fFin;
		this.descripcion = descripcion;
		this.estaActivo = true;
	}

	public void actualizarAlcance(List<Recurso> recursos) {
		// Agrego lo que falta
		for (Recurso recurso : recursos) {
			if ( !this.getInstituciones().contains(recurso) )
				this.getInstituciones().add(recurso);
		}
		// Elimino lo que sobra
		List<Recurso> lista =  new ArrayList<Recurso>(this.getInstituciones());
		for (Recurso recurso : lista) {
			if ( !recursos.contains(recurso) )
				this.getInstituciones().remove(recurso);
		}
	}
	
	public String getInstitucionesTexto() {
		String texto = "";

		if (instituciones.size() > 0) {
			for (Recurso institucion : instituciones) {
				texto += institucion.getNombreCompleto() + ", ";
			}
			if(texto.length() > 2) texto = texto.substring(0, texto.length() - 2);
		} else
			texto = "Todos los dispositivos";
		
		return texto;
	}
	
	
	public String getDetalle() {
		return this.getNombre();
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

	public Capacitacion getCapacitacion() {
		return capacitacion;
	}


	public void setCapacitacion(Capacitacion capacitacion) {
		this.capacitacion = capacitacion;
	}


	public java.util.List<Recurso> getInstituciones() {
		return instituciones;
	}

	public void setInstituciones(java.util.List<Recurso> instituciones) {
		this.instituciones = instituciones;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

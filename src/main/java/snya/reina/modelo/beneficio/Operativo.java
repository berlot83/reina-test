package snya.reina.modelo.beneficio;

import java.io.Serializable;
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
@Table(name="Reina_OperativoBeneficio", catalog="SistemasSNYA")
@Audited
public class Operativo implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -2567022790618412921L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdOperativoBeneficio")
	private Integer id;
	
	@Column(name="FechaOperativo")
	private Date fechaOperativo;
	
	@Column(name="Descripcion")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "IdTipoDeBeneficio")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeBeneficio beneficio;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="Reina_OperativoBeneficioEnRecurso",
			joinColumns=@JoinColumn(name="IdOperativoBeneficio", referencedColumnName="IdOperativoBeneficio"),
			inverseJoinColumns=@JoinColumn(name="IdRecurso", referencedColumnName="IdRecurso"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private java.util.List<Recurso> instituciones;
	
	
	public Operativo() {
		
	}
	
	public Operativo(Date fechaOperativo, TipoDeBeneficio beneficio, String descripcion, List<Recurso> recursos) {
		this.fechaOperativo = fechaOperativo;
		this.descripcion = descripcion;
		this.beneficio = beneficio;
		this.instituciones = recursos;
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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaOperativo() {
		return fechaOperativo;
	}

	public void setFechaOperativo(Date fechaOperativo) {
		this.fechaOperativo = fechaOperativo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoDeBeneficio getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(TipoDeBeneficio beneficio) {
		this.beneficio = beneficio;
	}

	public java.util.List<Recurso> getInstituciones() {
		return instituciones;
	}

	public void setInstituciones(java.util.List<Recurso> instituciones) {
		this.instituciones = instituciones;
	}
}

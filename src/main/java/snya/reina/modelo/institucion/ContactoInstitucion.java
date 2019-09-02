package snya.reina.modelo.institucion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Table(name="Entidades_ContactoInstitucion", catalog="SistemasSNYA")
@Audited
public class ContactoInstitucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475852336552032470L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdContactoInstitucion")
	private Integer id;
	@Column(name="Contacto")
	private String nombre;
	@ManyToOne
	@JoinColumn(name="IdTipoContactoInstitucion", nullable=false)
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private TipoContactoInstitucion cargo;
	@ManyToOne
	@JoinColumn(name="IdInstitucion")
	private Institucion institucion;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	public ContactoInstitucion() {
		this.estaActivo = true;
	}

	public ContactoInstitucion(TipoContactoInstitucion cargo, String nombre, Institucion institucion) {
		this.cargo = cargo;
		this.nombre = nombre;
		this.institucion = institucion;
		this.estaActivo = true;
	}

	
	public String getDescripcion() {
		return this.getNombre() + " ( " + this.getCargo().getNombre() + " ) ";
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoContactoInstitucion getCargo() {
		return cargo;
	}

	public void setCargo(TipoContactoInstitucion cargo) {
		this.cargo = cargo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
}

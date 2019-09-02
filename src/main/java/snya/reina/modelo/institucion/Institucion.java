package snya.reina.modelo.institucion;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.general.modelo.Domicilio;
import snya.general.modelo.Telefono;
import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.recurso.ComponeRecurso;


@Entity
@Table(name="Entidades_Institucion", catalog="SistemasSNYA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorFormula(value="case when IdTipoDeInstitucion in (1,2) then 1 when IdTipoDeInstitucion in (5, 6, 7, 8, 9, 25, 26, 27, 28, 31) then 5 when IdTipoDeInstitucion in (11, 12, 13, 16, 18, 30) then 11 when IdTipoDeInstitucion in (17, 22) then 17 else IdTipoDeInstitucion end")
@Audited
public abstract class Institucion implements ComponeRecurso, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4316053056651157383L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdInstitucion")
	private Integer id;
	@Column(name="Institucion")
	private String nombre;
	@Column(name="InstitucionCorto")
	private String nombreCorto;	
	@ManyToOne
	@JoinColumn(name="IdTipoDeInstitucion")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeInstitucion tipoDeInstitucion;
	@Column(name="Observacion")
	private String observacion;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IdDomicilio")	
	private Domicilio domicilio;
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinTable(
			name="Entidades_InstitucionTelefono",
			joinColumns=@JoinColumn(name="IdInstitucion", referencedColumnName="IdInstitucion"),
			inverseJoinColumns=@JoinColumn(name="IdTelefono", referencedColumnName="IdTelefono"))
	private List<Telefono> telefonos;
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,mappedBy="institucion")
	private List<ContactoInstitucion> contactos;
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinTable(
			name="Entidades_VinculoInstitucion",
			joinColumns=@JoinColumn(name="IdInstitucion", referencedColumnName="IdInstitucion"),
			inverseJoinColumns=@JoinColumn(name="IdInstitucionVinculada", referencedColumnName="IdInstitucion"))
	private List<Institucion> vinculadas;
	@Column(name="EstaActivo")
	private boolean estaActivo;
	@ManyToOne(optional=true)
	@JoinColumn(name="IdPadreInstitucion")	
	private Institucion padre;
	@Column(name="eMail")
	private String eMail;
	
	public Institucion(){
		
	}
	
	public Institucion(String nombre, String nombreCorto,
			TipoDeInstitucion tipo, Institucion institucionPadre, String observacion) {
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.tipoDeInstitucion = tipo;
		this.observacion = observacion;
		this.domicilio = null;
		this.telefonos = new ArrayList<Telefono>();
		this.contactos = new ArrayList<ContactoInstitucion>();
		this.vinculadas = new ArrayList<Institucion>();
		this.padre = institucionPadre;
		this.estaActivo = true;
	}

	public boolean estaDentroDelTipo(Integer[] tipos) {
		boolean esta = false;
		Integer idtipo = this.getTipoDeInstitucion().getId();
		for (Integer idt : tipos) {
			esta = esta || idt.equals(idtipo);
		}
		
		return esta;
	}
	
	public void agregarTelefono(Telefono telefono) {
		this.telefonos.add(telefono);		
	}
	
	public void eliminarTelefono(Integer idTelefono) {
		Telefono telefonoSeleccionado = null;
		for (Telefono telefono : telefonos) {
			if ( telefono.getId().equals(idTelefono) )
				telefonoSeleccionado = telefono;
		}
		this.telefonos.remove( telefonoSeleccionado );
	}
	
	public void agregarContacto(ContactoInstitucion con) {
		this.contactos.add(con);
	}
	
	public void eliminarContacto(Integer idContacto) {
		ContactoInstitucion contactoSeleccionado = null;
		for (ContactoInstitucion contacto : contactos) {
			if ( contacto.getId().equals(idContacto) )
				contactoSeleccionado = contacto;
		}
		
		if (contactoSeleccionado != null)
			contactoSeleccionado.setEstaActivo(false);
	}
	
	public String getDomicilioEnTexto() {
		return (this.getDomicilio() != null) ? this.getDomicilio().getDetalle() : "";
	}

	public String getDomicilioEnTextoCorto() {
		return (this.getDomicilio() != null) ? this.getDomicilio().getDetalleCorto() : "";
	}	

	public String getTelefonoEnTexto() {
		String telefonos = "";
		for (Telefono telefono : this.getTelefonos()) {
			telefonos += telefono.getDetalle() + " / ";
		}
		return telefonos;
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

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	
	public TipoDeInstitucion getTipoDeInstitucion() {
		return tipoDeInstitucion;
	}

	public void setTipoDeInstitucion(TipoDeInstitucion tipoDeInstitucion) {
		this.tipoDeInstitucion = tipoDeInstitucion;
	}
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	
	public Institucion getPadre() {
		return padre;
	}

	public void setPadre(Institucion padre) {
		this.padre = padre;
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public List<ContactoInstitucion> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoInstitucion> contacto) {
		this.contactos = contacto;
	}

	public List<Institucion> getVinculadas() {
		return vinculadas;
	}

	public void setVinculadas(List<Institucion> vinculadas) {
		this.vinculadas = vinculadas;
	}

	public boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean activo) {
		this.estaActivo = activo;
	}
	
	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
}

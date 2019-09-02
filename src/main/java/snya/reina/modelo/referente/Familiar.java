package snya.reina.modelo.referente;


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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import snya.general.modelo.Domicilio;
import snya.general.modelo.EstadoCivil;

import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.ObraSocial;
import snya.general.modelo.Provincia;
import snya.general.modelo.Telefono;
import snya.general.modelo.TipoDeDocumento;
import snya.general.modelo.TipoDeTrabajo;
import snya.reina.modelo.AmbitoEjecucion;
import snya.reina.modelo.Calendario;
import snya.reina.modelo.Persona;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.recurso.ComponeRecurso;

@Entity
@Table(name="Reina_Familiar", catalog="SistemasSNYA")
@Audited
public class Familiar implements AmbitoEjecucion, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2554931412909976872L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdFamiliar")
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IdPersona")
	private Persona persona;
	
	@Column(name="Vive")
	private Boolean vive;
	
	@Column(name="Convive")
	private Boolean convive;
	
	@Column(name="Referente")
	private Boolean referente;
	
	@Column(name="Tutor")
	private Boolean tutor;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="IdObraSocial")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ObraSocial obraSocial;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeParentesco")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeParentesco parentesco;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinTable(
			catalog="SistemasSNYA", name="Reina_FamiliarTelefono", 
			joinColumns=@JoinColumn(name="IdFamiliar", referencedColumnName="IdFamiliar"),
			inverseJoinColumns=@JoinColumn(name="IdTelefono", referencedColumnName="IdTelefono"))
	private List<Telefono> telefonos;

	/* === Constructores === */
	public Familiar(){
		
	}
	
	public Familiar(Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			ObraSocial obraSocial, TipoDeParentesco parentesco) {
		this.setPersona(persona);
		this.setVive(vive);
		this.setConvive(convive);
		this.setReferente(referente);
		this.setTutor(tutor);
		this.setObservacion(observacion);
		this.setObraSocial(obraSocial);
		this.setParentesco(parentesco);
		this.telefonos = new ArrayList<Telefono>();
	}


	public String traerFechaNacimiento() {
		if (this.getFechaNacimiento() == null)
			return "";
		else {
			return Calendario.formatearFecha(this.getFechaNacimiento());
		}
	}

	public String traerDetalleDocumentacion() {
		return (this.isTieneDocumento()) 
				? this.getTipoDeDocumento().getNombreCorto() + " " + this.getNumeroDocumento()
				: "";
	}
	
	public boolean seCumplePermanencia() {
		return true;
	}
	
	public 	boolean es(ComponeRecurso componente) {
		return false;
	}

	public 	boolean esInstitucional() {
		return false;
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
	
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public Boolean getVive() {
		return vive;
	}

	public void setVive(Boolean value) {
		vive = value;
	}

	public Boolean getConvive() {
		return convive;
	}

	public void setConvive(Boolean value) {
		convive = value;
	}

	public Boolean getReferente() {
		return referente;
	}

	public void setReferente(Boolean value) {
		referente = value;
	}

	public Boolean getTutor() {
		return tutor;
	}

	public void setTutor(Boolean value) {
		tutor = value;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String value) {
		observacion = value;
	}

	public ObraSocial getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(ObraSocial value) {
		obraSocial = value;
	}

	public TipoDeParentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(TipoDeParentesco value) {
		parentesco = value;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven value) {
		joven = value;
	}
	
	
	public boolean isTieneDocumento() {
		return this.getPersona().isTieneDocumento();
	}

	public void setTieneDocumento(boolean tieneDocumento) {
		this.getPersona().setTieneDocumento(tieneDocumento);
	}

	public String getNombres() {
		return this.getPersona().getNombres();
	}

	public void setNombres(String nombres) {
		this.getPersona().setNombres(nombres);
	}

	public String getApellidos() {
		return this.getPersona().getApellidos();
	}

	public void setApellidos(String apellidos) {
		this.getPersona().setApellidos(apellidos);
	}

	public String getNumeroDocumento() {
		return this.getPersona().getNumeroDocumento();
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.getPersona().setNumeroDocumento(numeroDocumento);
	}

	public String getSexo() {
		return this.getPersona().getSexo();
	}

	public void setSexo(String sexo) {
		this.getPersona().setSexo(sexo);
	}

	public Date getFechaNacimiento() {
		return this.getPersona().getFechaNacimiento();
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.getPersona().setFechaNacimiento(fechaNacimiento);
	}

	public String getCuil() {
		return this.getPersona().getCuil();
	}

	public void setCuil(String cuil) {
		this.getPersona().setCuil(cuil);
	}

	public Integer getEdad() {
		return this.getPersona().getEdad();
	}

	public void setEdad(Integer edad) {
		this.getPersona().setEdad(edad);
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return this.getPersona().getTipoDeDocumento();
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.getPersona().setTipoDeDocumento(tipoDeDocumento);
	}

	public Date getFechaFallecimiento() {
		return this.getPersona().getFechaFallecimiento();
	}

	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.getPersona().setFechaFallecimiento(fechaFallecimiento);
	}
	
	public Provincia getProvincia() {
		return this.getPersona().getProvincia();
	}

	public void setProvincia(Provincia provincia) {
		this.getPersona().setProvincia(provincia);
	}
	
	public Municipio getMunicipio() {
		return this.getPersona().getMunicipio();
	}

	public void setMunicipio(Municipio municipio) {
		this.getPersona().setMunicipio(municipio);
	}

	public Localidad getLocalidad() {
		return this.getPersona().getLocalidad();
	}

	public void setLocalidad(Localidad localidad) {
		this.getPersona().setLocalidad(localidad);
	}

	public String getNombreCompleto() {
		return this.getApellidos() + ", " + this.getNombres();
	}


	@Override
	public boolean estaDentroDelTipo(Integer[] tipos) {
		return false;
	}
	
	@Override
	public String traerNombre() {
		return this.getNombreCompleto();
	}
	
	public Domicilio getDomicilio() {
		return this.getPersona().getDomicilio();
	}

	public void setDomicilio(Domicilio domicilio) {
		this.getPersona().setDomicilio(domicilio);
	}

	public Nacionalidad getNacionalidad() {
		return this.getPersona().getNacionalidad();
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.getPersona().setNacionalidad(nacionalidad);
	}
	
	public EstadoCivil getEstadoCivil() {
		return this.getPersona().getEstadoCivil();
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.getPersona().setEstadoCivil(estadoCivil);
	}
	
	public TipoDeTrabajo getTipoDeTrabajo() {
		return this.getPersona().getTipoDeTrabajo();
	}
	
	public void setTipoDeTrabajo(TipoDeTrabajo tipo) {
		this.getPersona().setTipoDeTrabajo(tipo);
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}
}
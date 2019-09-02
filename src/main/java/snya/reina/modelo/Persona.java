package snya.reina.modelo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import snya.general.modelo.Barrio;
import snya.general.modelo.Domicilio;
import snya.general.modelo.EstadoCivil;
import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.Provincia;
import snya.general.modelo.TipoDeDocumento;
import snya.general.modelo.TipoDeTrabajo;

@Entity
@Table(name="Reina_Persona", catalog="SistemasSNYA")
@SuppressWarnings("serial")
public class Persona implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdPersona")
	private Integer id;
	
	@Column(name="Nombres", nullable=false)
	private String nombres;
	
	@Column(name="ApellidoMaterno")
	private String apellidoMaterno;
	
	@Column(name="Apellidos", nullable=false)
	private String apellidos;
	
	@Column(name="TieneDocumento", nullable=false)
	private boolean tieneDocumento;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeDocumento")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeDocumento tipoDeDocumento;
	
	@Column(name="NumeroDocumento")
	private String numeroDocumento;
	
	@Column(name="Sexo", nullable=false)
	private String sexo;
	
	@Column(name="FechaNacimiento")
	private Date fechaNacimiento;
	
	@Column(name="FechaFallecimiento")
	private Date fechaFallecimiento;
	
	@Column(name="Cuil")
	private String cuil;
	
	@Column(name="Edad")
	private Integer edad;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IdProvincia")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Provincia provincia;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IdMunicipio")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Municipio municipio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IdLocalidad")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Localidad localidad;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="IdDomicilio")
	private Domicilio domicilio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IdEstadoCivil")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private EstadoCivil estadoCivil;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IdNacionalidad")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Nacionalidad nacionalidad;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IdTipoDeTrabajo")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeTrabajo tipoDeTrabajo;
	
	@Column(name="FechaCreacion")
	private Date fechaCreacion = new Date();
	
	public void asociarDocumentacion(Boolean tieneDocumento,
			TipoDeDocumento tipoDeDocumento,
			String numeroDocumento) {
		this.setTieneDocumento(tieneDocumento);
		this.setTipoDeDocumento(tipoDeDocumento);
		this.setNumeroDocumento(numeroDocumento);
	}

	public void agregarDomicilio(boolean domicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			Provincia provincia, Municipio municipio, Localidad localidad,
			Barrio barrio, String referenciaDomicilio) {
		Domicilio domicilio = new Domicilio();
		
		if (domicilioExacto) {
			domicilio.setTieneReferenciaDomiciliaria(true);
			domicilio.setCalle(domicilioCalle);
			domicilio.setNumero(domicilioNumero);
			domicilio.setPiso(domicilioPiso);
			domicilio.setTorre(domicilioTorre);
			domicilio.setDepto(domicilioDepto);
			domicilio.setEntre1(domicilioEntre1);
			domicilio.setEntre2(domicilioEntre2);
		} else
			domicilio.setTieneReferenciaDomiciliaria(false);

		domicilio.setProvincia(provincia);
		domicilio.setMunicipio(municipio);
		domicilio.setLocalidad(localidad);	
		domicilio.setBarrio(barrio);
		domicilio.setObservacion(referenciaDomicilio);
		
		this.setDomicilio(domicilio);		
	}
	
	public void modificarDomicilio(boolean domicilioExacto, String calle,
			String numero, String piso, String torre, String depto,
			String entre1, String entre2, Provincia provincia,
			Municipio municipio, Localidad localidad, Barrio barrio,
			String referenciaDomicilio) {
		
		if (domicilioExacto) {
			domicilio.setTieneReferenciaDomiciliaria(true);
			domicilio.setCalle(calle);
			domicilio.setNumero(numero);
			domicilio.setPiso(piso);
			domicilio.setTorre(torre);
			domicilio.setDepto(depto);
			domicilio.setEntre1(entre1);
			domicilio.setEntre2(entre2);
		} else {
			domicilio.setTieneReferenciaDomiciliaria(false);
			domicilio.setCalle(null);
			domicilio.setNumero(null);
			domicilio.setPiso(null);
			domicilio.setTorre(null);
			domicilio.setDepto(null);
			domicilio.setEntre1(null);
			domicilio.setEntre2(null);
		}

		domicilio.setProvincia(provincia);
		domicilio.setMunicipio(municipio);
		domicilio.setLocalidad(localidad);	
		domicilio.setBarrio(barrio);
		domicilio.setObservacion(referenciaDomicilio);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isTieneDocumento() {
		return tieneDocumento;
	}

	public void setTieneDocumento(boolean tieneDocumento) {
		this.tieneDocumento = tieneDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public TipoDeDocumento getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}

	public Date getFechaFallecimiento() {
		return fechaFallecimiento;
	}

	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.fechaFallecimiento = fechaFallecimiento;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getNombreCompleto() {
		return this.getApellidos() + 
				((this.getApellidoMaterno() != null) ? " " + this.getApellidoMaterno() : "") +
				", " + this.getNombres();
	}


	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public TipoDeTrabajo getTipoDeTrabajo() {
		return tipoDeTrabajo;
	}

	public void setTipoDeTrabajo(TipoDeTrabajo tipoDeTrabajo) {
		this.tipoDeTrabajo = tipoDeTrabajo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}

package snya.reina.modelo;

import java.io.Serializable;
import java.util.ArrayList;
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
import snya.general.modelo.Telefono;
import snya.reina.modelo.institucion.TipoDeInstitucion;
import snya.reina.modelo.institucion.Institucion;
import snya.reina.modelo.recurso.ComponeRecurso;

@Entity
@Table(catalog="SistemasSNYA",name="Entidades_Programa")
@Audited
public class ProgramaSimplificado implements ComponeRecurso, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7948470923232301630L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="IdPrograma")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="IdTipoDeInstitucion")
	@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeInstitucion tipoDeInstitucion;
	@Column(name="NroLinea")
	private Integer nroLinea;
	@Column(name="Programa")
	private String nombre;
	@Column(name="ProgramaCorto")
	private String nombreCorto;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IdInstitucion")
	private Institucion institucion;
	@Column(name="Conveniado")
	private Boolean conveniado;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="IdDomicilio")	
	private Domicilio domicilio;
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinTable(
			name="Entidades_ProgramaTelefono",
			joinColumns=@JoinColumn(name="IdPrograma", referencedColumnName="IdPrograma"),
			inverseJoinColumns=@JoinColumn(name="IdTelefono", referencedColumnName="IdTelefono"))
	private List<Telefono> telefonos;
	
	
	public ProgramaSimplificado() {
	}
	
	public ProgramaSimplificado(String nombre, String nombreCorto, TipoDeInstitucion tipo, Institucion institucion) {
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.tipoDeInstitucion = tipo;
		this.conveniado = true;
		this.estaActivo = true;
		this.domicilio = null;
		this.institucion = institucion;
		this.telefonos = new ArrayList<Telefono>();
	}
	
	public void eliminarTelefono(Integer idTelefono) {
		Telefono telefonoSeleccionado = null;
		for (Telefono telefono : telefonos) {
			if ( telefono.getId().equals(idTelefono) )
				telefonoSeleccionado = telefono;
		}
		this.telefonos.remove( telefonoSeleccionado );
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
	
	
	public boolean seCumplePermanencia() {
		return true;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public TipoDeInstitucion getTipoDeInstitucion() {
		return tipoDeInstitucion;
	}

	public void setTipoDeInstitucion(TipoDeInstitucion tipoDeInstitucion) {
		this.tipoDeInstitucion = tipoDeInstitucion;
	}
	
	public Integer getNroLinea() {
		return nroLinea;
	}

	public void setNroLinea(Integer nroLinea) {
		this.nroLinea = nroLinea;
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

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Boolean getConveniado() {
		return conveniado;
	}

	public void setConveniado(Boolean conveniado) {
		this.conveniado = conveniado;
	}

	public boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}
}

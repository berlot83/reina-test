package snya.reina.modelo.joven;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import snya.general.modelo.TipoDeDocumento;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.datos.joven.JovenDAO;
import snya.reina.modelo.JovenSimplificado;
import snya.reina.modelo.Persona;
import snya.reina.utilidades.busqueda.joven.BusquedaJovenPorDocumento;

@Entity
@Table(name="Reina_TipoSituacionTramiteDNI", catalog="SistemasSNYA")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeSituacionTramite implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7291817582582617857L;
	
	/* === Constante === */
	public static final int POSEE = 1;
	public static final int NO_POSEE = 2;
	public static final int EN_TRAMITE = 3;
	public static final int SE_DESCONOCE = 4;
		
	/* === Variables de instancia === */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoSituacionTramiteDNI")
	private Integer id;
	
	@Column(name="TipoSituacionTramiteDNI")
	private String nombre;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
			name="Reina_TipoSituacionTramiteDNIPosibles",
			joinColumns=@JoinColumn(name="IdTipoSituacionTramiteDNIAnterior", referencedColumnName="IdTipoSituacionTramiteDNI"),
			inverseJoinColumns=@JoinColumn(name="IdTipoSituacionTramiteDNISiguiente", referencedColumnName="IdTipoSituacionTramiteDNI"))
	private java.util.List<TipoDeSituacionTramite> posibles;

	/* === Constructores === */
	public TipoDeSituacionTramite() {
		this.setPosibles(new ArrayList<TipoDeSituacionTramite>());
	}

	
	public SituacionTramite crearSituacion(Persona persona, Date fecha, TipoDeDocumento tipoDeDocumentoPosee,
			String numeroDocumentoPosee, Boolean actualizado, Boolean partidaDeNacimientoPosee, String observacionPosee, Boolean tieneDocumento, 
			TipoDeDocumento tipoDeDocumentoEnTramite, String numeroDocumentoEnTramite, Boolean partidaDeNacimientoEnTramite, String observacionEnTramite, 
			Boolean certificadoDeParto, Boolean partidaDeNacimiento, String observacionNoPosee, String observacionSeDesconoce) throws ReinaException {
		SituacionTramite situacion = new SituacionTramite();
		situacion.setFechaSituacion(fecha);
		
		// <<procesamiento>>
		// Posee
		if (this.getId().equals(TipoDeSituacionTramite.POSEE)) {
			persona.asociarDocumentacion(true, tipoDeDocumentoPosee, numeroDocumentoPosee);
			situacion.setObservacion(observacionPosee);
			situacion.setActualizado( (actualizado == null) ? false : actualizado );
			situacion.setPartidaNacimiento( (partidaDeNacimientoPosee == null) ? false : partidaDeNacimientoPosee );
			situacion.setCertificadoDeParto( false );
		}
		// No Posee
		if (this.getId().equals(TipoDeSituacionTramite.NO_POSEE)) {
			persona.asociarDocumentacion(false, null, null);
			situacion.setPartidaNacimiento( (partidaDeNacimiento == null) ? false : partidaDeNacimiento );
			situacion.setCertificadoDeParto( (certificadoDeParto == null) ? false : certificadoDeParto );
			situacion.setObservacion(observacionNoPosee);
		}
		// En tramite
		if (this.getId().equals(TipoDeSituacionTramite.EN_TRAMITE)){
			if (persona.isTieneDocumento() && !tieneDocumento)
				throw new ReinaException("No puede quitar el documento una vez ingresado");
			persona.asociarDocumentacion(tieneDocumento, tipoDeDocumentoEnTramite, numeroDocumentoEnTramite);
			situacion.setPartidaNacimiento( (partidaDeNacimientoEnTramite == null) ? false : partidaDeNacimientoEnTramite );
			situacion.setCertificadoDeParto( false );
			situacion.setObservacion(observacionEnTramite);
		}
		// Se Desconoce
		if (this.getId().equals(TipoDeSituacionTramite.SE_DESCONOCE)){
			persona.asociarDocumentacion(false, null, null);
			situacion.setPartidaNacimiento( false );
			situacion.setCertificadoDeParto( false );
			situacion.setObservacion(observacionSeDesconoce);
		}
		situacion.setTipo(this);
		
		// <<resultado>>
		return situacion;
	}

	public void modificarSituacion(SituacionTramite situacion, Persona persona,
			TipoDeDocumento tipoDeDocumentoPosee,
			String numeroDocumentoPosee, Boolean actualizado, Boolean partidaDeNacimientoPosee, String observacionPosee, 
			Boolean tieneDocumento, TipoDeDocumento tipoDeDocumentoEnTramite, String numeroDocumentoEnTramite, 
			Boolean partidaDeNacimientoEnTramite, String observacionEnTramite, 
			Boolean certificadoDeParto, Boolean partidaDeNacimiento, 
			String observacionNoPosee, String observacionSeDesconoce) {	
		// <<procesamiento>>
		// Posee
		if (this.getId().equals(TipoDeSituacionTramite.POSEE)) {
			persona.asociarDocumentacion(true, tipoDeDocumentoPosee, numeroDocumentoPosee);
			situacion.setActualizado( (actualizado == null) ? false : actualizado );
			situacion.setPartidaNacimiento( (partidaDeNacimientoPosee == null) ? false : partidaDeNacimientoPosee );
			situacion.setCertificadoDeParto( false );
			situacion.setObservacion(observacionPosee);
		}
		// No Posee
		if (this.getId().equals(TipoDeSituacionTramite.NO_POSEE)) {
			persona.asociarDocumentacion(false, null, null);
			situacion.setPartidaNacimiento( (partidaDeNacimiento == null) ? false : partidaDeNacimiento );
			situacion.setCertificadoDeParto( (certificadoDeParto == null) ? false : certificadoDeParto );
			situacion.setObservacion(observacionNoPosee);
		}
		// En tramite
		if (this.getId().equals(TipoDeSituacionTramite.EN_TRAMITE)) {
			persona.asociarDocumentacion(tieneDocumento, tipoDeDocumentoEnTramite, numeroDocumentoEnTramite);
			situacion.setPartidaNacimiento( (partidaDeNacimientoEnTramite == null) ? false : partidaDeNacimientoEnTramite );
			situacion.setCertificadoDeParto( false );
			situacion.setObservacion(observacionEnTramite);
		}
		// Se Desconoce
		if (this.getId().equals(TipoDeSituacionTramite.SE_DESCONOCE)) {
			persona.asociarDocumentacion(false, null, null);
			situacion.setPartidaNacimiento( false );
			situacion.setCertificadoDeParto( false );
			situacion.setObservacion(observacionSeDesconoce);
		}
		situacion.setTipo(this);		
	}

	public String traerDetalle(Joven joven, SituacionTramite situacion) {
		// <<declaracion e inicializacion de variables>>
		String detalle = "";
		
		// <<procesamiento>>
		// Posee
		if (this.getId().equals(TipoDeSituacionTramite.POSEE)) {
			detalle = joven.getTipoDeDocumento().getNombreCorto() + " " + joven.getNumeroDocumento()  + ((situacion.getActualizado() != null && situacion.getActualizado()) ? "": " (No Actualizado)");
		}
		// No Posee
		if (this.getId().equals(TipoDeSituacionTramite.NO_POSEE)) {
			detalle = "No Posee. " 
					+ ((situacion.getPartidaNacimiento()) ? "Con Part." : "Sin Part.") 
					+  ((situacion.getCertificadoDeParto()) ? " Con Cert." : " Sin Cert."); 
		}
		// En tramite
		if (this.getId().equals(TipoDeSituacionTramite.EN_TRAMITE)) {
			detalle = "En tramite. " + ((joven.isTieneDocumento()) ? (joven.getTipoDeDocumento().getNombreCorto() + " " + joven.getNumeroDocumento()) : "");
		}
		// Se Desconoce
		if (this.getId().equals(TipoDeSituacionTramite.SE_DESCONOCE)) {
			detalle = "Se desconoce";
		}

		// <<resultado>>
		return detalle;
	}

	public String traerDetalleExtenso(Joven joven, SituacionTramite situacion) {
		// <<declaracion e inicializacion de variables>>
		String detalle = "";
		
		// <<procesamiento>>
		// Posee
		if (this.getId().equals(TipoDeSituacionTramite.POSEE)) {
			detalle = joven.getTipoDeDocumento().getNombreCorto() + " " + joven.getNumeroDocumento()  
					+ ((situacion.getActualizado() != null && situacion.getActualizado()) ? "": " (No Actualizado)")
					+ ((situacion.getPartidaNacimiento() != null && situacion.getPartidaNacimiento()) ? " - Consta partida de nacimiento." : " - No consta partida de nacimiento. ");
		}
		// No Posee
		if (this.getId().equals(TipoDeSituacionTramite.NO_POSEE)) {
			detalle = "No Posee. " 
					+ ((situacion.getPartidaNacimiento()) ? "Consta partida de nacimiento." : "No consta partida de nacimiento.") 
					+  ((situacion.getCertificadoDeParto()) ? "Consta certificado de parto." : "No consta certificado de parto."); 
		}
		// En tramite
		if (this.getId().equals(TipoDeSituacionTramite.EN_TRAMITE)) {
			detalle = "En tramite. " 
					+ ((situacion.getPartidaNacimiento()) ? " Consta partida de nacimiento." : " No consta partida de nacimiento. ") 
					+ ((joven.isTieneDocumento()) ? (joven.getTipoDeDocumento().getNombreCorto() + " " + joven.getNumeroDocumento()) : "");
		}
		// Se Desconoce
		if (this.getId().equals(TipoDeSituacionTramite.SE_DESCONOCE)) {
			detalle = "Se desconoce";
		}

		// <<resultado>>
		return detalle;
	}

	public void noSeRepiteDocumento(Joven joven, JovenDAO daoJoven) throws ReinaException {
		// <<procesamiento>>
		// Posee
		// En tramite
		if (this.getId().equals(TipoDeSituacionTramite.POSEE) || 
			( this.getId().equals(TipoDeSituacionTramite.EN_TRAMITE) && joven.getPersona().getNumeroDocumento() != null ) 
		) {
			String documento = joven.getPersona().getNumeroDocumento();
			Integer[] tipos = new Integer[0];
			Integer[] ambitos = new Integer[0];
			boolean restriccion = false;
			boolean reporte = false;
			boolean restriccionEdad = false;
			String propiedad = "Apellidos";
			String orden = "asc";
			java.util.List<JovenSimplificado> lista = daoJoven.listaJoven(new BusquedaJovenPorDocumento(documento, tipos, ambitos, restriccion, restriccionEdad, reporte), propiedad, orden);
			if ( (lista.size() > 1) ||
				 (lista.size() == 1 && (joven.getId() == null || !joven.getId().equals(lista.get(0).getIdJoven())))
			)
				throw new ReinaException(ReinaCte.Format(ReinaCte.DOCUMENTO_REPETIDO_PARA_JOVEN, documento));					
		}

	}
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String value) {
		nombre = value;
	}

	public boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean value) {
		estaActivo = value;
	}

	public boolean getPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(boolean value) {
		porDefecto = value;
	}

	public java.util.List<TipoDeSituacionTramite> getPosibles() {
		return posibles;
	}

	public void setPosibles(java.util.List<TipoDeSituacionTramite> value) {
		posibles = value;
	}
}
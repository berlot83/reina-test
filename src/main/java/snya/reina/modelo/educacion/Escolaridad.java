package snya.reina.modelo.educacion;

import java.io.Serializable;
import java.util.Comparator;

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

import snya.reina.modelo.joven.Joven;

@Entity
@Table(name="Reina_Escolaridad", catalog="SistemasSNYA")
@Audited
public class Escolaridad implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2184334123282384276L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdEscolaridad")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@ManyToOne
	@JoinColumn(name="IdModalidadEscolar")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ModalidadEscolar modalidad;
	
	@ManyToOne
	@JoinColumn(name="IdNivelEscolar")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private NivelEscolar nivel;
	
	@ManyToOne
	@JoinColumn(name="IdAnioEscolar")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private AnioEscolar anioEscolar;
	// private EstablecimientoEscolar establecimiento;
	
	@Column(name="EstablecimientoNombre")
	private String establecimientoNombre;
	
	@Column(name="CicloLectivo")
	private Integer cicloLectivo;
	
	@Column(name="ObservacionEscolaridad")
	private String observacion;
	
	@Column(name="ArchivoCertificado")
	public String archivoCertificado;
	
	@Column(name="PeriodoEvaluacion")
	private boolean periodoEvaluacion;

	@Column(name="Certificado")
	private boolean certificado;
	
	@Column(name="Cursando")
	private boolean cursando;
	
	@Column(name="Finalizado")
	private boolean finalizado;
	
	@Column(name="Ultimo")
	private boolean ultimo;


	/* === Constructores === */
	public Escolaridad() {
	}

	public Escolaridad(Joven joven, ModalidadEscolar modalidad, NivelEscolar nivel,
			AnioEscolar anio, String establecimiento, Integer cicloLectivo,
			Boolean periodoEvaluacion, Boolean certificado, Boolean cursando, Boolean finalizado, String observacion) {
		this.joven = joven;
		this.modalidad = modalidad;
		this.nivel = nivel;
		this.anioEscolar = anio;
		this.establecimientoNombre = establecimiento;
		this.periodoEvaluacion = periodoEvaluacion;
		this.cicloLectivo = cicloLectivo;
		this.certificado = certificado;
		this.setCursando(cursando);
		this.setFinalizado(finalizado);
		this.setObservacion(observacion);
		this.setUltimo(false);
	}

	public String traerDetalle() {
		return 
			"En ciclo " + this.getCicloLectivo().toString() + 
			((this.isPeriodoEvaluacion()) ? " esta siendo evaluado " : "") +
			((this.isFinalizado()) ? " alcanza el " : "") +
			((this.isCursando()) ? " cursa el " : "") +
			((this.getAnioEscolar() != null) ? this.getAnioEscolar().getNombre() + " - " : "")  + 
			((this.getNivel() != null) ? this.getNivel().getNombre() + " - " : "")  +
			((this.getModalidad() != null) ? this.getModalidad().getNombre() : "") +
			((this.getEstablecimientoNombre() != null) ? " en el establecimiento " + this.getEstablecimientoNombre() : "");
	}
	
	public void asentarCertificado(String direccion) {
		this.setArchivoCertificado(direccion);
		this.setCertificado(true);
	}
	
	/* === Propiedades === */
	public Integer getId() {
		return id;
	}

	public void setId(Integer value) {
		id = value;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public NivelEscolar getNivel() {
		return nivel;
	}

	public void setNivel(NivelEscolar value) {
		nivel = value;
	}

	public ModalidadEscolar getModalidad() {
		return modalidad;
	}

	public void setModalidad(ModalidadEscolar value) {
		modalidad = value;
	}

	public AnioEscolar getAnioEscolar() {
		return anioEscolar;
	}

	public void setAnioEscolar(AnioEscolar value) {
		anioEscolar = value;
	}

	/*public EstablecimientoEscolar getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(EstablecimientoEscolar value) {
		establecimiento = value;
	}*/

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public String getEstablecimientoNombre() {
		return establecimientoNombre;
	}

	public void setEstablecimientoNombre(String establecimientoNombre) {
		this.establecimientoNombre = establecimientoNombre;
	}

	public void setCicloLectivo(Integer value) {
		cicloLectivo = value;
	}

	public boolean isCursando() {
		return cursando;
	}

	public void setCursando(boolean cursando) {
		this.cursando = cursando;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String value) {
		observacion = value;
	}

	public String getArchivoCertificado() {
		return archivoCertificado;
	}

	public void setArchivoCertificado(String archivoCertificado) {
		this.archivoCertificado = archivoCertificado;
	}

	public boolean isPeriodoEvaluacion() {
		return periodoEvaluacion;
	}

	public void setPeriodoEvaluacion(boolean periodoEvaluacion) {
		this.periodoEvaluacion = periodoEvaluacion;
	}
	
	public boolean getCertificado() {
		return certificado;
	}

	public void setCertificado(boolean value) {
		certificado = value;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public boolean isUltimo() {
		return ultimo;
	}

	public void setUltimo(boolean ultimo) {
		this.ultimo = ultimo;
	}

	public int Menor(Escolaridad en) {
		// <<procesamiento>> <<resultado>>
		return 0;
	}
	
	public static Comparator<Escolaridad> comparador(){
		return new Comparator<Escolaridad>() {
			public int compare(Escolaridad e1, Escolaridad e2) {
				return ordenarAPartirDeCiclo(e1, e2);		
			}

			private int ordenarAPartirDeCiclo(Escolaridad e1, Escolaridad e2) {
				if ( e1.getCicloLectivo() < e2.getCicloLectivo() ) {
					return -1;					
				}
				
				if ( e1.getCicloLectivo() > e2.getCicloLectivo() ) {
					return 1;					
				}
				
				
				return ordenarAPartirDeEvaluacion(e1, e2);
			}
			
			private int ordenarAPartirDeEvaluacion(Escolaridad e1, Escolaridad e2) {
				if (e1.isPeriodoEvaluacion()) return 1;
				if (e2.isPeriodoEvaluacion()) return -1;
				
				return ordenarAPartirDeCursando(e1, e2);
			}
			
			private int ordenarAPartirDeCursando(Escolaridad e1, Escolaridad e2) {
				if (e1.isCursando()) return 1;
				if (e2.isCursando()) return -1;
				
				return ordenarAPartirDeNivel(e1, e2);
			}
			
			private int ordenarAPartirDeNivel(Escolaridad e1, Escolaridad e2) {
				if (e1.getNivel() == null) return -1;
				if (e2.getNivel() == null) return 1;
				
				if ( e1.getNivel().getId() < e2.getNivel().getId() ) {
					return -1;
				}

				if ( e1.getNivel().getId() > e2.getNivel().getId() ) {
					return 1;	
				}
				
				return ordenarAPartirDeAnio(e1, e2);
			}
		
			private int ordenarAPartirDeAnio(Escolaridad e1, Escolaridad e2) {
				if (e1.getAnioEscolar() == null) return -1;
				if (e2.getAnioEscolar() == null) return 1;
				
				if ( e1.getAnioEscolar() .getId() < e2.getAnioEscolar().getId() ) {
					return -1;
				} else {
					if ( e1.getAnioEscolar().getId() > e2.getAnioEscolar().getId() ) {
						return 1;	
					} else
						return 0;
				}
			}
		};
	}
}
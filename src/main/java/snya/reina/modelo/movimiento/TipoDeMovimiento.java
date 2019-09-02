package snya.reina.modelo.movimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Cacheable;
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
import org.hibernate.envers.Audited;

import snya.reina.datos.institucion.RecursoREINADAO;
import snya.reina.modelo.recurso.Recurso;

@Entity
@Table(name="Reina_Mov_TipoDeMovimiento", catalog="SistemasSNYA")
@Audited
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeMovimiento implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2997879746789285003L;

	
	public final static Integer FUNCIIONALIDAD_INGRESO = 1;
	public final static Integer FUNCIIONALIDAD_TRASLADO = 2;
	public final static Integer FUNCIIONALIDAD_NOTIFICACION = 3;
	public final static Integer FUNCIIONALIDAD_EGRESO = 4;
	public final static Integer FUNCIIONALIDAD_BAJA = 5;
	public final static Integer FUNCIIONALIDAD_INTERNACION = 6;
	public final static Integer FUNCIIONALIDAD_RETORNOINTERNACION = 7;
		
	public final static Integer GRUPO_INGRESO = 1;
	public final static Integer GRUPO_TRASLADO = 2;
	public final static Integer GRUPO_NOTIFICACION = 3;
	public final static Integer GRUPO_EGRESO = 4;
	public final static Integer GRUPO_BAJA = 5;
	
	public final static Integer GRUPO_INGRESO_CAD = 6;
	public final static Integer GRUPO_MOVIMIENTO_CAD = 7;
	public final static Integer GRUPO_EGRESO_CAD = 8;

	
	public static final Integer ID_INGRESO = 1;
	public static final Integer ID_INGRESO_CAD = 63;
	
	public static final Integer ID_DESTINO_AMBITO_SRPJ = 1;
	public static final Integer ID_DESTINO_PROGRAMA = 2;
	public static final Integer ID_DESTINO_EXTRAJURISDIRECCIONAL = 3;
	public static final Integer ID_DESTINO_SALUD = 4;
	public static final Integer ID_DESTINO_MONITOREOTERRITORIAL = 5;
	public static final Integer ID_DESTINO_SERVICIOPENITENCIARIO = 6;
	public static final Integer ID_DESTINO_AMBITO_SPPD = 7;
	public static final Integer ID_DESTINO_DEPENDENCIA = 8;
	public static final Integer ID_DESTINO_ADMISION = 9;
	
	
	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeMovimiento")
	private Integer id;
	
	@Column(name="TipoDeMovimiento")
	private String nombre;
	
	@Column(name="IdFuncionalidadMovimiento")
	private Integer funcionalidadMovimiento;

	@Column(name="IdGrupoDeMovimiento")
	private Integer grupoDeMovimiento;

	@Column(name="IdTipoDestino")
	private Integer tipoDestino;	

	@Column(name="requiereHora")
	private Boolean requiereHora;
	
	@Column(name="EstaActivo")
	private boolean estaActivo;
	
	@Column(name="PorDefecto")
	private boolean porDefecto;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="Reina_Mov_TipoDeMovimientoSiguiente",
			joinColumns=@JoinColumn(name="IdTipoDeMovimientoAnterior", referencedColumnName="IdTipoDeMovimiento"),
			inverseJoinColumns=@JoinColumn(name="IdTipoDeMovimientoPosterior", referencedColumnName="IdTipoDeMovimiento"))
	private List<TipoDeMovimiento> posibles;


	
	public List<Recurso> traerDestinos(RecursoREINADAO recursoDAO) {
		List<Recurso> componentes = new ArrayList<Recurso>();
		
		if ( this.correspondeAmbitoDelSRPJ() ) {
			List<Recurso> lista = new ArrayList<Recurso>();
			lista.addAll(recursoDAO.traerCentrosCerradosTodosActivos());
			lista.addAll(recursoDAO.traerCentrosContencionTodosActivos());
			lista.addAll(recursoDAO.traerCentrosRecepcionTodosActivos());
			componentes = lista;
		}
		
		if ( this.correspondeExtraJurisdiccional() )			
			componentes = recursoDAO.traerInstitucionesExtraJuridireccionalesTodosActivos();

		if ( this.correspondeAmbitoCentroSalud() )
			componentes = recursoDAO.traerCentrosDeSaludTodosActivos();
		
		if ( this.correspondeAmbitoServicioPenitenciario() )
			componentes = recursoDAO.traerUnidadesPenitenciariasTodos();
		
		if ( this.correspondeAmbitoPrograma() )
			componentes = recursoDAO.traerInstitucionesTerciarizadasTodosActivos();
		
		if ( this.correspondeAmbitoProgramaSPPD() )			
			componentes = recursoDAO.traerInstitucionesSPPDTodos();
		
		return componentes;
	}

	public List<TipoDeMovimiento> traerPosiblesIngresos() {
		return this.traerPosible(GRUPO_INGRESO);
	}

	public List<TipoDeMovimiento> traerPosiblesTraslados() {
		return this.traerPosible(GRUPO_TRASLADO);
	}
	
	public List<TipoDeMovimiento> traerPosiblesNotificaciones() {
		return this.traerPosible(GRUPO_NOTIFICACION);
	}
	
	public List<TipoDeMovimiento> traerPosiblesEgresos() {
		return this.traerPosible(GRUPO_EGRESO);
	}
	
	public List<TipoDeMovimiento> traerPosiblesBajas() {
		return this.traerPosible(GRUPO_BAJA);
	}
	
	public List<TipoDeMovimiento> traerPosiblesMovimientosAdmision() {
		return this.traerPosible(GRUPO_MOVIMIENTO_CAD);
	}

	public List<TipoDeMovimiento> traerPosiblesEgresosAdmision() {
		return this.traerPosible(GRUPO_EGRESO_CAD);
	}
	
	
	/* === Metodos privados === */
	private List<TipoDeMovimiento> traerPosible(Integer idGrupo){
		List<TipoDeMovimiento> lista = new ArrayList<TipoDeMovimiento>();
		Iterator<TipoDeMovimiento> iter = this.getPosibles().iterator();
		while (iter.hasNext()) {
			TipoDeMovimiento tipoDeMovimiento = (TipoDeMovimiento) iter.next();
			if (tipoDeMovimiento.getGrupoDeMovimiento().equals(idGrupo))
				lista.add(tipoDeMovimiento);
		}
		
		return lista;		
	}
	
	private boolean correspondeExtraJurisdiccional() {
		return this.getTipoDestino() == TipoDeMovimiento.ID_DESTINO_EXTRAJURISDIRECCIONAL;
	}
	
	private boolean correspondeAmbitoProgramaSPPD() {
		return this.getTipoDestino() == TipoDeMovimiento.ID_DESTINO_AMBITO_SPPD;
	}

	private boolean correspondeAmbitoPrograma() {
		return this.getTipoDestino() == TipoDeMovimiento.ID_DESTINO_PROGRAMA;
	}

	private boolean correspondeAmbitoServicioPenitenciario() {
		return this.getTipoDestino() == TipoDeMovimiento.ID_DESTINO_SERVICIOPENITENCIARIO;
	}

	private boolean correspondeAmbitoCentroSalud() {
		return this.getTipoDestino() == TipoDeMovimiento.ID_DESTINO_SALUD;
	}

	private boolean correspondeAmbitoDelSRPJ() {
		return this.getTipoDestino() == TipoDeMovimiento.ID_DESTINO_AMBITO_SRPJ;
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

	public Integer getFuncionalidadMovimiento() {
		return funcionalidadMovimiento;
	}

	public void setFuncionalidadMovimiento(Integer funcionalidadMovimiento) {
		this.funcionalidadMovimiento = funcionalidadMovimiento;
	}
	
	public List<TipoDeMovimiento> getPosibles() {
		return posibles;
	}

	public void setPosibles(List<TipoDeMovimiento> posibles) {
		this.posibles = posibles;
	}

	public Integer getGrupoDeMovimiento() {
		return grupoDeMovimiento;
	}

	public void setGrupoDeMovimiento(Integer grupoDeMovimiento) {
		this.grupoDeMovimiento = grupoDeMovimiento;
	}

	public Integer getTipoDestino() {
		return tipoDestino;
	}

	public void setTipoDestino(Integer tipoDestino) {
		this.tipoDestino = tipoDestino;
	}

	public Boolean getRequiereHora() {
		return requiereHora;
	}

	public void setRequiereHora(Boolean requiereHora) {
		this.requiereHora = requiereHora;
	}
}
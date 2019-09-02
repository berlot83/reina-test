package snya.reina.modelo.informe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="Reina_Informe_GrupoDeInforme", catalog="SistemasSNYA")
public class GrupoDeInforme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4584362338110135988L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdGrupoDeInforme")
	private Integer id;
	
	@Column(name="GrupoDeInforme")
	private String nombre;
		
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	@Column(name="PorDefecto")
	private Boolean porDefecto;
	
	@OneToMany(mappedBy="grupo")
	@OrderBy("nombre ASC")
	private List<TipoDeInforme> informes;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="grupo", fetch=FetchType.EAGER)
	private List<PermisoGrupoDeInforme> permisos;
	
	public GrupoDeInforme() {
		this.nombre = "";;
		this.estaActivo = true;
		this.porDefecto = false;
		this.informes = new ArrayList<TipoDeInforme>();
		this.permisos = new ArrayList<PermisoGrupoDeInforme>();
	}
	
	public GrupoDeInforme(String nombre) {
		this.nombre = nombre;
		this.estaActivo = true;
		this.porDefecto = false;
		this.informes = new ArrayList<TipoDeInforme>();
		this.permisos = new ArrayList<PermisoGrupoDeInforme>();
	}
	
	public boolean estaPermitidoEscribir(int rol, int idFuncion) {
		PermisoGrupoDeInforme actual = this.traerPermiso(rol);
		
		if (actual != null)
			return actual.puedeEscribir(idFuncion);
		else
			return false;
	}
	
	public boolean estaPermitidoLeer(int rol, int idFuncion) {
		PermisoGrupoDeInforme actual = this.traerPermiso(rol);
		
		if (actual != null)
			return actual.puedeLeer(idFuncion);
		else
			return false;
	}
		
	public void agregarPermiso(Integer rol, String funciones) {
		PermisoGrupoDeInforme actual = this.traerPermiso(rol);
		
		if (actual != null)
			actual.setFunciones( funciones );
		else
			this.permisos.add( new PermisoGrupoDeInforme( this, rol, funciones ) );
	}
	
	public void indicarPermisos(List<PermisoGrupoDeInforme> lPermisos) {		
		for (PermisoGrupoDeInforme per : lPermisos) {
			this.agregarPermiso(per.getRol(), per.getFunciones());
		}
		
		this.eliminarPermisos(lPermisos);
	}
	
	private void eliminarPermisos(List<PermisoGrupoDeInforme> lPermisos) {
		List<PermisoGrupoDeInforme> lista = new ArrayList<PermisoGrupoDeInforme>(this.permisos);
		
		for (PermisoGrupoDeInforme permiso : lista) {
			PermisoGrupoDeInforme encontrado = null;
			
			Iterator<PermisoGrupoDeInforme> iter= lPermisos.iterator();	
			while (encontrado == null && iter.hasNext()) {
				PermisoGrupoDeInforme p = (PermisoGrupoDeInforme) iter.next();
				if (permiso.getRol().equals(p.getRol())) encontrado = permiso;
			}
			
			if (encontrado == null)
				this.permisos.remove(permiso);
		}		
	}

	private PermisoGrupoDeInforme traerPermiso(Integer rol) {
		PermisoGrupoDeInforme permiso = null;
		Iterator<PermisoGrupoDeInforme> iter= this.permisos.iterator();
		
		while (permiso == null && iter.hasNext()) {
			PermisoGrupoDeInforme p = (PermisoGrupoDeInforme) iter.next();
			if ( p.getRol().equals(rol) )
				permiso = p;
		}
		
		return permiso;
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

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Boolean getPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(Boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public List<TipoDeInforme> getInformes() {
		return informes;
	}

	public void setInformes(List<TipoDeInforme> informes) {
		this.informes = informes;
	}
}

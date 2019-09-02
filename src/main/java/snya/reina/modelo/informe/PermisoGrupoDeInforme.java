package snya.reina.modelo.informe;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reina_Informe_PermisoGrupoDeInforme", catalog = "SistemasSNYA")
public class PermisoGrupoDeInforme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940573749555437323L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdPermisoGrupoDeInforme")
	private Integer id;
	@Column(name = "IdRol")
	private Integer rol;
	@Column(name = "Funciones")
	private String funciones;
	@ManyToOne
	@JoinColumn(name = "IdGrupoDeInforme")
	private GrupoDeInforme grupo;

	public PermisoGrupoDeInforme() {
		this.funciones = "";
	}
	
	public PermisoGrupoDeInforme(GrupoDeInforme grupo, Integer rol, String funciones) {
		this.grupo = grupo;
		this.rol = rol;
		this.funciones = funciones;
	}

	public void agregarPermisoFuncion(Integer idFuncion, String accion) {
		String resultado = "";
		String[] funRW = this.funciones.split(Pattern.quote("|"));
		boolean encontrado = false;
		
		for (int i = 0; i < funRW.length; i++) {
			if (funRW[i].contains("-" + idFuncion)) {
				encontrado = true;
				if (!funRW[i].contains(accion)) {
					String[] partes = funRW[i].split(Pattern.quote("-"));
					funRW[i] = partes[0] + accion + "-" + idFuncion;
				}
			}
		}
		for (String item : funRW) {
			resultado += ( (!resultado.isEmpty()) ? "|" : "") + item;
		}
		
		if(!encontrado) {
			resultado += ( (!resultado.isEmpty()) ? "|" : "") + accion + "-" + idFuncion;
		}
		
		this.funciones = resultado;
	}
	
	public boolean puedeEscribir(Integer idFuncion) {
		String accion = "W";
		return puedeRealizarAccion(idFuncion, accion);
	}

	public boolean puedeLeer(Integer idFuncion) {
		String accion = "R";
		return puedeRealizarAccion(idFuncion, accion);
	}
	
	
	private boolean puedeRealizarAccion(Integer idFuncion, String accion) {
		String[] funRW = this.funciones.split(Pattern.quote("|"));
		String encontrado = null;
		for (String item : funRW) {
			if (item.contains("-" + idFuncion))
				encontrado = item;
		}
		return (encontrado != null) ? encontrado.contains(accion) : false;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	public String getFunciones() {
		return funciones;
	}

	public void setFunciones(String funciones) {
		this.funciones = funciones;
	}

	public GrupoDeInforme getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDeInforme grupo) {
		this.grupo = grupo;
	}
}

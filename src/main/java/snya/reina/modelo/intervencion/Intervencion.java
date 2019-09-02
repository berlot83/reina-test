package snya.reina.modelo.intervencion;


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

import snya.reina.modelo.joven.Joven;

@Entity
@Table(name="Reina_Interv_Intervencion", catalog="SistemasSNYA")
@Audited
public class Intervencion implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2065290077105400144L;

	/* === Variables de instancia === */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdIntervencion")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IdJoven")
	private Joven joven;
	
	@ManyToOne
	@JoinColumn(name="IdTipoDeIntervencion")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TipoDeIntervencion tipo;
	
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	
	@Column(name="EstaEliminada")
	private Boolean eliminada;	
	
	/* === Constructores === */		
	public Intervencion(){
		this.estaActivo = true;
		this.eliminada = false;
	}
	
	public Intervencion(TipoDeIntervencion tipo, Joven joven) {
		this.tipo = tipo;
		this.joven = joven;
		this.estaActivo = true;
		this.eliminada = false;
	}
	
	
	public boolean getVigente() {
		return this.getEstaActivo() && !this.getEliminada();
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Joven getJoven() {
		return joven;
	}

	public void setJoven(Joven joven) {
		this.joven = joven;
	}

	public TipoDeIntervencion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeIntervencion tipo) {
		this.tipo = tipo;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Boolean getEliminada() {
		return eliminada;
	}

	public void setEliminada(Boolean eliminada) {
		this.eliminada = eliminada;
	}
}
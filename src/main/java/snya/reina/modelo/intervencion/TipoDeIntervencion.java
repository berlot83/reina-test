package snya.reina.modelo.intervencion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reina_Interv_TipoDeIntervencion", catalog="SistemasSNYA")
public class TipoDeIntervencion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298050645674458737L;

	public final static Integer ID_MONITOREO_TERRITORIAL = 1;
	public final static Integer ID_CONTEXTO_ENCIERRO = 2;
	public final static Integer ID_ADMISION_CAD = 3;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeIntervencion")
	private Integer id;
	
	@Column(name="TipoDeIntervencion")
	private String nombre;

	
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
}

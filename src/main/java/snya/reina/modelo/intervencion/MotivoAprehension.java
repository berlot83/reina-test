package snya.reina.modelo.intervencion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reina_Interv_MotivoAprehension", catalog="SistemasSNYA")
public class MotivoAprehension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8966229556041892925L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMotivoAprehension")
	private Integer id;
	
	@Column(name="MotivoAprehension")
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

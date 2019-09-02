package snya.reina.modelo.habeas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import snya.reina.modelo.Calendario;

@Entity
@Table(name="Reina_NotaHabeas", catalog="SistemasSNYA")
@Audited
public class NotaHabeas implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3298055298165943775L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdNotaHabeas")
	private Integer id;
	
	@Column(name="Fecha", nullable = false )
	private Date fecha;
	
	@Column(name="Observacion")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="IdHabeas")
	private Habeas habeas;


	public NotaHabeas() {
		
	}

	public NotaHabeas(Date fecha, String observacion) {
		this.fecha = fecha;
		this.observacion = observacion;
	}
	
	
	public String getFechaTexto() {
		return Calendario.formatearFecha(this.getFecha());
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.util.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Habeas getHabeas() {
		return habeas;
	}

	public void setHabeas(Habeas habeas) {
		this.habeas = habeas;
	}
}

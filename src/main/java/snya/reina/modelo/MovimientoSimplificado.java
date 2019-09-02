package snya.reina.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(name = "ConsultaPendientesJoven", procedureName = "SistemasSNYA.Reina_TraerJovenPendientes", resultClasses = snya.reina.modelo.MovimientoSimplificado.class, parameters = {
	@StoredProcedureParameter(name = "tipos", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "recursos", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "propiedad", mode = ParameterMode.IN, type = String.class),
	@StoredProcedureParameter(name = "orden", mode = ParameterMode.IN, type = String.class) })
public class MovimientoSimplificado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4951566745427026815L;
	
	@Id
	private Integer id;
	private Integer idJoven;
	private String expediente;
	private String apellidos;
	private String nombres;
	private String documento;
	private String sexo;
	private Date fechaNac;
	private Integer edad;
	private Date fecha;
	private String estado;
	private String tipo;
	private String origen;
	private String destino;
	private String situacionProcesal;
	private String proceso;
	
	
	public MovimientoSimplificado() {
		
	}
	
	public MovimientoSimplificado(Date fecha, String estado, String tipo, String origen, String destino) {
		this.fecha = fecha;
		this.estado = estado;
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdJoven() {
		return idJoven;
	}

	public void setIdJoven(Integer idJoven) {
		this.idJoven = idJoven;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getSituacionProcesal() {
		return situacionProcesal;
	}

	public void setSituacionProcesal(String situacionProcesal) {
		this.situacionProcesal = situacionProcesal;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
}

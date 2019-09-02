package snya.reina.modelo.institucion;
import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="Entidades_TipoDeInstitucion", catalog="SistemasSNYA" )
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class TipoDeInstitucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405529293426756003L;
	
	public final static Integer ID_JRPJ = 1; //Juzgado de Responsabilidad Penal Juvenil
	public final static Integer ID_JGJ = 2; //Juzgados de Garantias del Joven
	public final static Integer ID_FISCALIA = 3; //Fiscalia
	public final static Integer ID_DEFENSORIA = 4; //Defensoria
	public final static Integer ID_SECRETARIA = 5; //Secretaria
	public final static Integer ID_SUBSECRETARIA = 6; //Subsecretaria
	public final static Integer ID_DCIONPROVINCIAL = 7; //Direccion Provincial
	public final static Integer ID_DIRECCION = 8; //Direccion
	public final static Integer ID_DEPARTAMENTO = 9; //Departamento
	public final static Integer ID_REFERENCIA = 10; //Centro de Referencia
	public final static Integer ID_CONTENCION = 11; //Centro de Contencion
	public final static Integer ID_RECEPCION = 12; //Centro de Recepcion
	public final static Integer ID_CERRADO = 13; //Centro Cerrado
	public final static Integer ID_CENTROSALUD = 14; //Centro de Salud
	public final static Integer ID_UNIDADPENITENCIARIA = 15; //Unidad Penitenciaria
	public final static Integer ID_EXTRAJURIDIRECCIONAL = 16; //Institucion Nacion
	public final static Integer ID_HOGAROFICIAL = 17; //Hogar Oficial
	public final static Integer ID_CENTRODEDIA = 18; //Centro de dia
	public final static Integer ID_CLINICA = 19; //CLinica Psiq
	public final static Integer ID_COMUNIDAD = 20; // Comunidad Terapeutica
	public final static Integer ID_HOGAR = 21; // Hogares conveniados
	public final static Integer ID_CASADEABRIGO = 22; //Casa de Abrigo
	public final static Integer ID_HOGARDEDISCAPACITADOS = 23; //Hogar de discapacitados
	public final static Integer ID_CENTRODEDIASALUD = 24; //Centro de dia salud
	public final static Integer ID_ONG = 25; //ONG
	public final static Integer ID_MUNICIPIO = 26; //Municipio
	public final static Integer ID_PROGRAMA = 27; //Programa
	public final static Integer ID_SERVICIOSZONAL = 28; //ServicioZonal
	public final static Integer ID_CAD = 30; //CAD
	public final static Integer ID_COMISARIA = 31; //Comisaria
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdTipoDeInstitucion")
	private Integer id;
	@Column(name="TipoDeInstitucion")
	private String nombre;
	@Column(name="EstaActivo")
	private Boolean estaActivo;
	@Column(name="PorDefecto")
	private Boolean porDefecto;

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

}

package snya.reina.modelo;

import java.io.Serializable;

import snya.reina.modelo.recurso.ComponeRecurso;

public interface AmbitoEjecucion extends Serializable {

	Integer getId();
	
	String traerNombre();
	
	String getNombreCompleto();
	
	boolean seCumplePermanencia();

	boolean es(ComponeRecurso componente);
	
	boolean esInstitucional();

	boolean estaDentroDelTipo(Integer[] tipos);
}

package snya.reina.modelo.recurso;

import java.io.Serializable;
import java.util.List;

import snya.general.modelo.Domicilio;
import snya.general.modelo.Telefono;

public interface ComponeRecurso extends Serializable {

	Integer getId();
	
	String getNombre();
	
	String getDomicilioEnTexto();
	
	String getDomicilioEnTextoCorto();

	String getTelefonoEnTexto();
	
	List<Telefono> getTelefonos();
	
	boolean seCumplePermanencia();

	boolean estaDentroDelTipo(Integer[] tipos);

	Domicilio getDomicilio();

	boolean getEstaActivo();
}

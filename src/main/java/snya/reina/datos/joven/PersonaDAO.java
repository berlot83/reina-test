package snya.reina.datos.joven;

import java.util.List;

import snya.reina.modelo.Persona;

public interface PersonaDAO {
	
	Persona traerPorId(Integer id);
	
    List<Persona> traerPorNombre(String apellido, String nombre);
    
    List<Persona> traerPorNombre(String dato);
    
    List<Persona> traerPorNroDocumento(Integer nro);
}
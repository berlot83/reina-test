package snya.reina.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snya.reina.modelo.Persona;

/* Utiliza Spring-data-jpa-starter no quietar del Pom.xml y está conectado a JovenServicioImpl / Provisorio hasta aceptación */

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
	/* sin uso Estos métodos funcionan por defecto con el comando LIKE % variable % de SQL */
	List<Persona> findByNombresContaining(String nombres);
	List<Persona> findByApellidosContaining(String apellidos);
	List<Persona> findByNumeroDocumentoContaining(String numeroDocumento);
	
	/* En uso - Búsqueda mixta de datos en DB */
	List<Persona> findByNombresIgnoreCaseLikeOrApellidosIgnoreCaseLikeOrNumeroDocumentoIgnoreCaseLike(String nombres, String apellidos, String numeroDocumento);
	
	List<Persona> findByNumeroDocumentoLikeAndApellidosLikeAndNombresLike(String numeroDocumento, String apellidos, String nombres);
}

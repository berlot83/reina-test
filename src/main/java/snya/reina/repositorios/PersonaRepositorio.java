package snya.reina.repositorios;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snya.reina.modelo.Persona;

/* Utiliza Spring-data-jpa-starter no quietar del Pom.xml y está conectado a JovenServicioImpl / Provisorio hasta aceptación */

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
	/* sin uso Estos métodos funcionan por defecto con el comando LIKE % variable % de SQL */
	List<Persona> findByNombresIgnoreCaseContaining(String nombres);
	List<Persona> findByApellidosIgnoreCaseContaining(String apellidos);
	
	/* Busqueda mixta 1 */
	List<Persona> findByNumeroDocumentoLikeOrApellidosLikeOrNombresLike(String numeroDocumento, String apellidos, String nombres);

	/* Busqueda mixta 2 */
	List<Persona> findByNumeroDocumentoContainingAndApellidosContainingAndNombresContaining(String numeroDocumento, String apellidos, String nombres);

	/* Busqueda mixta 3 */
	List<Persona> findByApellidosLikeAndNombresLike(String apellidos, String nombres);
	List<Persona> findByNumeroDocumentoIgnoreCaseContaining(String numeroDocumento);
	List<Persona> findByApellidosIgnoreCaseContainingOrNombresIgnoreCaseContaining(String apellidos, String nombres);
}

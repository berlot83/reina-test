package snya.reina.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snya.reina.modelo.joven.Expediente;

@Repository
public interface ExpedienteRepositorio  extends JpaRepository<Expediente, Integer>{

}

package snya.reina.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snya.reina.modelo.joven.Joven;

@Repository
public interface JovenRepositorio extends JpaRepository<Joven, Integer> {
	
	List<Joven> findByIdIn(List<Integer> ids);
}

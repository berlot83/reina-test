package snya.reina.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snya.reina.modelo.joven.Joven;

@Repository
public interface JovenRepositorio extends JpaRepository<Joven, Integer> {
}

package snya.reina.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snya.reina.modelo.informe.Informe;

@Repository
public interface InformeRepositorio extends JpaRepository<Informe, Long> {
	/* Por alguna razon no vino por defecto este método, debería ya que extiende JpaRepository, sin embargo tuve que declararlo, Axel */
	Informe findById(Integer id);
}

package snya.reina.datos.beneficio;

import java.util.List;

import snya.reina.modelo.beneficio.EstadoBeneficio;

public interface EstadoBeneficioDAO {

	EstadoBeneficio traerPorId(Integer activo);

	List<EstadoBeneficio> traerTodos();

}

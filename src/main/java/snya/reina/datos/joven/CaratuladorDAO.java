package snya.reina.datos.joven;

import java.util.List;

import snya.reina.modelo.joven.Caratulador;

public interface CaratuladorDAO {

	Caratulador traerPorId(Integer idCaratulador);
	
	Caratulador traerPorCaracteristica(String caracteristica);

	List<Caratulador> traerTodosExternos();
}

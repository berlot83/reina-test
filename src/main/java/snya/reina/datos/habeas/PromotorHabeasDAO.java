package snya.reina.datos.habeas;

import java.util.List;

import snya.reina.modelo.habeas.PromotorHabeas;

public interface PromotorHabeasDAO {

	List<PromotorHabeas> traerTodosActivos();

	PromotorHabeas traerPorId(Integer organoPromotor);

}

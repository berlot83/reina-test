package snya.reina.datos.beneficio;

import java.util.List;

import snya.reina.modelo.beneficio.TipoDeBeneficio;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;

public interface TipoDeBeneficioDAO {

	void actualizar(TipoDeBeneficio tipo);
	
	TipoDeBeneficio traerPorId(Integer idTipoPension);
	
	List<TipoDeBeneficio> traerPensionesActivas();

	List<TipoDeBeneficio> traerTodosActivos();

	List<TipoDeBeneficio> traerTodosTarjetaActivos();

	List<EstadisticaBeneficio> listaBeneficiariosActivos(Integer idBeneficio, String idInstitucion);

	List<EstadisticaBeneficio> listaBeneficiosParaOperativo(Integer idBeneficio, String idInstitucion);

}

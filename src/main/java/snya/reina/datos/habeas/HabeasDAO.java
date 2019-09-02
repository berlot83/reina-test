package snya.reina.datos.habeas;

import java.util.List;

import snya.reina.ReinaException;
import snya.reina.modelo.habeas.Habeas;
import snya.reina.modelo.institucion.InstitucionCumplimiento;
import snya.reina.modelo.joven.Joven;

public interface HabeasDAO {

	void insertar(Habeas habeas) throws ReinaException;

	void actualizar(Habeas habeas) throws ReinaException;
	
	boolean tieneHABEASActivosElDestinatario(Joven joven);
	
	boolean tieneHABEASActivosElDestinatario(InstitucionCumplimiento instituto);
	
	List<Habeas> traerHabeasActivosPorDestinatario(Joven joven);
	
	List<Habeas> traerHabeasActivosPorDestinatario(InstitucionCumplimiento instituto);
	
	Habeas traerPorId(Integer id);
	
	List<Habeas> traerHabeasActivos();

	List<Habeas> traerHabeasHistoricos();
	
	void inicializarAlPeresozo(Object proxy);
}

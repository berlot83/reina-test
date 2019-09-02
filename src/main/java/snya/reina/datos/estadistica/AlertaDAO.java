package snya.reina.datos.estadistica;

import java.util.List;

import snya.reina.modelo.MedidaImpuestaAlerta;
import snya.reina.modelo.estadistica.EstadisticaBeneficio;
import snya.reina.modelo.estadistica.EstadisticaFormacionLaboral;

public interface AlertaDAO {

	List<MedidaImpuestaAlerta> listaAletaVencia_y_AVencer(int dias, Integer[] tipos, Integer[] recursos, String propiedad, String orden);

	List<EstadisticaFormacionLaboral> listaAlertaFormacionLaboralInterrumpida(String idTipoRecurso, String idRecurso, Integer idFormacion);

	List<EstadisticaFormacionLaboral> listaAlertaFormacionLaboralCertificacion(String idTipoRecurso, String idRecurso, Integer idFormacion);

	List<EstadisticaBeneficio> listaAlertaBeneficioTarjeta(String idTipoRecurso, String idRecurso, Integer idBeneficio);
}

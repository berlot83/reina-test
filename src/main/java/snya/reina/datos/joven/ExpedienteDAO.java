package snya.reina.datos.joven;

import snya.reina.modelo.joven.Caratulador;

public interface ExpedienteDAO {

	boolean existeNumeroExpediente(Long numeroExpediente);

	boolean existeNumeroExpedienteExterno(Caratulador caratulador, String numeroExpediente);
}

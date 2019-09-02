package snya.reina;

public class ReinaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReinaException(String mensaje) {
		super(mensaje);
	}

	public ReinaException(String mensaje, ReinaException e) {
		super(mensaje, e);
	}
}

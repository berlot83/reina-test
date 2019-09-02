package snya.reina.modelo.informe;

public enum TipoDeEstadoInformeEnum {
	BORRADOR(1), EDICION(2), IMPRESO(3);
	
	private Integer value;

	TipoDeEstadoInformeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	private static TipoDeEstadoInformeEnum[] allValues = values();

	public static TipoDeEstadoInformeEnum fromValue(Integer n) {

		for (TipoDeEstadoInformeEnum e : allValues) {
			if (n == e.getValue())
				return e;
		}

		return null;
	}
}

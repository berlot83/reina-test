package snya.reina.utilidades;

public class Utilidades {

	/* Sólo números */
	public static String validadorDNI(String numeroDocumento) {
		/* Matcher */
		String regex = "[?<=\\s|^)[a-zA-Z]*(?=[.,;:-]?\\s|$]";
		String validado = numeroDocumento.replaceAll(regex, "").trim();
		return validado;
	}
	
	/* Prueba */
	public static void main(String[] args) {
		System.out.println(Utilidades.validadorDNI("30.158-619wefwef.  0"));
	}

}

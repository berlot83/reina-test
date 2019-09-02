package snya.reina.serviciomodelo;

import snya.reina.ReinaException;
import snya.reina.utilidades.Comando;

public class GeneradorDeComando {

	public static Comando CrearComando(String nomComando) throws ReinaException {
		try {
			Class<?> clazz = Class.forName(nomComando);
			Comando comando = (Comando) clazz.newInstance();
			/*
			Class<?> clazz = Class.forName("com.foo.MyClass");
			Constructor<?> constructor = clazz.getConstructor(String.class, Integer.class);
			Object instance = constructor.newInstance("stringparam", 42);
			*/
			
			return comando;
		} catch (ClassNotFoundException e) {
			throw new ReinaException("No se encuentra el comando a realizar segun el tipo de nota");
		} catch (InstantiationException e) {
			throw new ReinaException("Error en el comando vinculado a la nota");
		} catch (IllegalAccessException e) {
			throw new ReinaException("Error en el comando vinculado a la nota");
		}
	}
}

package snya.reina.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import snya.reina.ReinaException;

public class Calendario {

	public static String formatearFecha(Date fecha) {
		return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
	}

	public static String formatearHora(Date fecha) {
		return new SimpleDateFormat("HH:mm").format(fecha);
	}
	
	public static String formatearFechaHora(Date fecha) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fecha);
	}

	public static String formatearFechaHoraTextoCompleto(Date fecha) {
		String[] parteDeFecha = new SimpleDateFormat("dd/MM/yyyy").format(fecha).split("/");		
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		String[] parteDeHora = new SimpleDateFormat("HH:mm").format(fecha).split(":");
		
		return parteDeFecha[0] + " dias del mes de " + mesEnPalabra(cFecha.get(Calendar.MONTH) + 1) + " del año " + parteDeFecha[2] + " a las "  + parteDeHora[0] + "." + parteDeFecha[1]; 
	}
	
	public static String formatearFechaTexto(Date fecha) {
		String[] parteDeFecha = new SimpleDateFormat("dd/MM/yyyy").format(fecha).split("/");
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		
		return parteDeFecha[0] + " de " + mesEnPalabra(cFecha.get(Calendar.MONTH) + 1) + " de " + parteDeFecha[2];
	}
	
	public static String formatearFechaTextoCompleto(Date fecha) {
		String[] parteDeFecha = new SimpleDateFormat("dd/MM/yyyy").format(fecha).split("/");
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		
		return parteDeFecha[0] + " dias del mes de " + mesEnPalabra(cFecha.get(Calendar.MONTH) + 1) + " del año " + parteDeFecha[2];
	}

	public static String formatearFechaMesAnioTexto(Date fecha) {
		String[] parteDeFecha = new SimpleDateFormat("dd/MM/yyyy").format(fecha).split("/");
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		
		return mesEnPalabra(cFecha.get(Calendar.MONTH) + 1) + " / " + parteDeFecha[2];
	}
	
	public static String formatearFechaMySql(Date fecha) {
		return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
	}

	public static Date parsearFecha(String fecha) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
	}

	public static Date parsearFechaHora(String fecha) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fecha);
	}
	
	public static Date parsearFecha(String fecha, String mensaje)
			throws ReinaException {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		} catch (ParseException e) {
			throw new ReinaException(mensaje);
		}
	}

	public static Date sumarHorario(Date fecha, Date fechaUltima) {
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		Calendar cFechaUltima = Calendar.getInstance();
		cFechaUltima.setTime(fechaUltima);
		if (cFecha.get(Calendar.DAY_OF_MONTH) == cFechaUltima.get(Calendar.DAY_OF_MONTH)
				&& cFecha.get(Calendar.MONTH) == cFechaUltima.get(Calendar.MONTH)
				&& cFecha.get(Calendar.YEAR) == cFechaUltima.get(Calendar.YEAR)) {
			cFecha.add(Calendar.HOUR_OF_DAY, cFechaUltima.get(Calendar.HOUR_OF_DAY));
			cFecha.add(Calendar.MINUTE, cFechaUltima.get(Calendar.MINUTE) + 5);
			return cFecha.getTime();
		}
		return fecha;
	}

	public static boolean mismoDia(Date fecha, Date fechaDos) {
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		Calendar cFechaDos = Calendar.getInstance();
		cFechaDos.setTime(fechaDos);
		return (cFecha.get(Calendar.DAY_OF_MONTH) == cFechaDos.get(Calendar.DAY_OF_MONTH)
				&& cFecha.get(Calendar.MONTH) == cFechaDos.get(Calendar.MONTH) && cFecha.get(Calendar.YEAR) == cFechaDos.get(Calendar.YEAR));
	}

	public static Date diaSinHora(Date fecha) {
		Calendar cFecha = Calendar.getInstance();
		cFecha.setTime(fecha);
		cFecha.set(Calendar.HOUR_OF_DAY, 0);
		cFecha.set(Calendar.MINUTE, 0);
		cFecha.set(Calendar.SECOND, 0);
		cFecha.set(Calendar.MILLISECOND, 0);

		return cFecha.getTime();
	}
	
	public static Date fechaHoy() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		return today.getTime();
	}
	
	public static int mesHoy() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.MONTH) + 1;
	}
	
	public static int anioHoy() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR);
	}
	
	public static long calcularTiempoTranscurridoAhora(Date fecha) {
		Calendar today = Calendar.getInstance();
		
		long diff = today.getTime().getTime() - fecha.getTime();
		long diffMinutes = diff / (60 * 1000);
		
		return diffMinutes;
	}
	
	public static String mesEnPalabra(int mes) {
		String texto = "";

		switch (mes) {
		case 1:
			texto = "Enero";
			break;
		case 2:
			texto = "Febrero";
			break;
		case 3:
			texto = "Marzo";
			break;
		case 4:
			texto = "Abril";
			break;
		case 5:
			texto = "Mayo";
			break;
		case 6:
			texto = "Junio";
			break;
		case 7:
			texto = "Julio";
			break;
		case 8:
			texto = "Agosto";
			break;
		case 9:
			texto = "Septiembre";
			break;
		case 10:
			texto = "Octubre";
			break;
		case 11:
			texto = "Noviembre";
			break;
		case 12:
			texto = "Diciembre";
			break;
		default:
			break;
		}
		return texto;
	}
}

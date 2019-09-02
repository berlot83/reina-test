package snya.reina.serviciomodelo.joven;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import snya.general.modelo.EstadoCivil;
import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.Provincia;
import snya.general.modelo.TipoDeDocumento;
import snya.general.modelo.TipoDeTrabajo;
import snya.reina.ReinaCte;
import snya.reina.modelo.Persona;
import snya.reina.serviciomodelo.resultado.ResultadoPersona;

public class RegistroDePersona {

	public ResultadoPersona crear(String apellidoMaterno, String apellidos, String nombres,
			Date fechaNacimiento, Integer edad, String sexo,
			Nacionalidad nacionalidad, EstadoCivil estadoCivil,
			Provincia provinciaNacimiento, Municipio municipioNacimiento,
			Localidad localidadNacimiento, TipoDeTrabajo tipoDeTrabajo,
			boolean conFechaNacOEdad) {

		// <<validaciones>>
		// valido que el apellido haya sido ingresado
		if (apellidos == null || apellidos.equals(""))
			return new ResultadoPersona(ReinaCte.APELLIDO_PERSONA_NULO);
		// valido que los nombres hayan sido ingresados
		if (nombres == null || nombres.equals(""))
			return new ResultadoPersona(ReinaCte.NOMBRE_PERSONA_NULO);
		// valido que la fecha de nacimiento o edad hayan sido ingresados		
		if (conFechaNacOEdad && fechaNacimiento == null && edad == null)
			return new ResultadoPersona(ReinaCte.FECHANACIMIENTO_EDAD_PERSONA_NULO);
		// valido que el sexo sea un dato valido
		if (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F"))
			return new ResultadoPersona(ReinaCte.SEXO_PERSONA_INVALIDO);

		// <<procesamiento>>
		Persona persona = new Persona();
		persona.setApellidoMaterno(apellidoMaterno);
		persona.setApellidos(apellidos);
		persona.setNombres(nombres);
		if (fechaNacimiento != null) {
			persona.setFechaNacimiento(fechaNacimiento);
			persona.setEdad(this.calcularEdad(fechaNacimiento, new Date()));
		} else {
			persona.setEdad(edad);
		}
		persona.setSexo(sexo);
		persona.setNacionalidad(nacionalidad);
		persona.setEstadoCivil(estadoCivil);
		persona.setProvincia(provinciaNacimiento);
		persona.setMunicipio(municipioNacimiento);
		persona.setLocalidad(localidadNacimiento);
		persona.setTipoDeTrabajo(tipoDeTrabajo);
		persona.setFechaCreacion(new Date());

		return new ResultadoPersona(persona);
	}

	public ResultadoPersona crear(String apellidoMaterno, String apellidos, String nombres,
			Date fechaNacimiento, Integer edad, String sexo,
			Boolean tieneDocumento, TipoDeDocumento tipoDeDocumento,
			String numeroDocumento, Nacionalidad nacionalidad,
			boolean conFechaNacOEdad) {

		// <<validaciones>>
		// valido que el apellido haya sido ingresado
		if (apellidos == null || apellidos.equals(""))
			return new ResultadoPersona(ReinaCte.APELLIDO_PERSONA_NULO);
		// valido que los nombres hayan sido ingresados
		if (nombres == null || nombres.equals(""))
			return new ResultadoPersona(ReinaCte.NOMBRE_PERSONA_NULO);
		// valido que la fecha de nacimiento o edad hayan sido ingresados		
		if (fechaNacimiento == null && edad == null)
			return new ResultadoPersona(ReinaCte.FECHANACIMIENTO_EDAD_PERSONA_NULO);
		// valido que el sexo sea un dato valido
		if (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F"))
			return new ResultadoPersona(ReinaCte.SEXO_PERSONA_INVALIDO);

		// <<procesamiento>>
		Persona persona = new Persona();
		persona.setApellidoMaterno(apellidoMaterno);
		persona.setApellidos(apellidos);
		persona.setNombres(nombres);
		if (fechaNacimiento != null) {
			persona.setFechaNacimiento(fechaNacimiento);
			persona.setEdad(this.calcularEdad(fechaNacimiento, new Date()));
		} else {
			persona.setEdad(edad);
		}
		persona.setSexo(sexo);	
		persona.setNacionalidad(nacionalidad);
		persona.asociarDocumentacion(tieneDocumento, tipoDeDocumento, numeroDocumento);

		return new ResultadoPersona(persona);
	}

	public ResultadoPersona modificar(Persona persona, String apellidoMaterno, String apellidos, String nombres,
			Date fechaNacimiento, Integer edad, String sexo, String cuil,
			Nacionalidad nacionalidad, EstadoCivil estadoCivil,
			Provincia provinciaNacimiento, Municipio municipioNacimiento,
			Localidad localidadNacimiento, TipoDeTrabajo tipoDeTrabajo,
			boolean conFechaNacOEdad) {
		
		// <<validaciones>>
		// valido que el apellido haya sido ingresado
		if (apellidos == null || apellidos.equals(""))
			return new ResultadoPersona(ReinaCte.APELLIDO_PERSONA_NULO);
		// valido que los nombres hayan sido ingresados
		if (nombres == null || nombres.equals(""))
			return new ResultadoPersona(ReinaCte.NOMBRE_PERSONA_NULO);
		// valido que la fecha de nacimiento o edad hayan sido ingresados		
		if (conFechaNacOEdad && fechaNacimiento == null && edad == null)
			return new ResultadoPersona(ReinaCte.FECHANACIMIENTO_EDAD_PERSONA_NULO);
		// valido que el sexo sea un dato valido
		if ( !sexo.equalsIgnoreCase("M") && !sexo.equals("F") )
			return new ResultadoPersona(ReinaCte.SEXO_PERSONA_INVALIDO);
		// valido que el cuil sea valido
		if ( cuil != null && !cuil.equals("")) {
			ResultadoPersona resultado = validarCuil(cuil);
			if (resultado != null) return resultado;
		}
		
		// <<procesamiento>>	
		persona.setApellidoMaterno(apellidoMaterno);
		persona.setApellidos(apellidos);
		persona.setNombres(nombres);
		if (fechaNacimiento != null) {
			persona.setFechaNacimiento(fechaNacimiento);
			persona.setEdad( this.calcularEdad(fechaNacimiento, new Date()) );
		} else {
			persona.setEdad(edad);
		}
		
		persona.setSexo(sexo);
		persona.setCuil(cuil);
		persona.setNacionalidad(nacionalidad);
		persona.setEstadoCivil(estadoCivil);
		persona.setProvincia(provinciaNacimiento);
		persona.setMunicipio(municipioNacimiento);
		persona.setLocalidad(localidadNacimiento);
		persona.setTipoDeTrabajo(tipoDeTrabajo);

		return new ResultadoPersona(persona);
	}
	
	private int calcularEdad(Date fechaNacimiento, Date fechaActual) {
		Calendar fechaAct = Calendar.getInstance();
		fechaAct.setTime(fechaActual);

		Calendar fechaNac = Calendar.getInstance();
		fechaNac.setTime(fechaNacimiento);

		int dif_anios = fechaAct.get(Calendar.YEAR)
				- fechaNac.get(Calendar.YEAR);
		int dif_meses = fechaAct.get(Calendar.MONTH)
				- fechaNac.get(Calendar.MONTH);
		int dif_dias = fechaAct.get(Calendar.DAY_OF_MONTH)
				- fechaNac.get(Calendar.DAY_OF_MONTH);

		// Si esta en ese año pero todavia no los ha cumplido
		if (dif_meses < 0 || (dif_meses == 0 && dif_dias < 0)) {
			dif_anios--;
		}
		return dif_anios;
	}
	
	private ResultadoPersona validarCuil(String cuil) {
		if (cuil == null || cuil.equals(""))
			return new ResultadoPersona("Debe consignar el cuil del agente");
		
		if (cuil.length() != 11)
			return new ResultadoPersona("El cuil debe ser de 11 digitos");
		
		Pattern patron = Pattern.compile("\\d+");
		if (!patron.matcher(cuil).matches())		
			return new ResultadoPersona("El cuil debe componerse de 11 digitos");
		
		if ( !this.compruebaCuitCorrecto(cuil) )
			return new ResultadoPersona("El CUIL no es valido");
		
		return null;
	}
		
	private boolean compruebaCuitCorrecto(String cuit) {
		int mk_suma;
		boolean mk_valido;
		String mk_p_nro = cuit;
		
		mk_suma = 0;
		mk_suma += Integer.parseInt(mk_p_nro.substring(0, 1)) * 5;
		mk_suma += Integer.parseInt(mk_p_nro.substring(1, 2)) * 4;
		mk_suma += Integer.parseInt(mk_p_nro.substring(2, 3)) * 3;
		mk_suma += Integer.parseInt(mk_p_nro.substring(3, 4)) * 2;
		mk_suma += Integer.parseInt(mk_p_nro.substring(4, 5)) * 7;
		mk_suma += Integer.parseInt(mk_p_nro.substring(5, 6)) * 6;
		mk_suma += Integer.parseInt(mk_p_nro.substring(6, 7)) * 5;
		mk_suma += Integer.parseInt(mk_p_nro.substring(7, 8)) * 4;
		mk_suma += Integer.parseInt(mk_p_nro.substring(8, 9)) * 3;
		mk_suma += Integer.parseInt(mk_p_nro.substring(9, 10)) * 2;
		mk_suma += Integer.parseInt(mk_p_nro.substring(10, 11)) * 1;

		if (mk_suma / 11 == (new Double(mk_suma) / 11) )
			mk_valido = true;
		else
			mk_valido = false;
					
		return mk_valido;
	}
}

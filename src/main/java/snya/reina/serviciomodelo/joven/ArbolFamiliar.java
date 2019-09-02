package snya.reina.serviciomodelo.joven;

import java.util.Iterator;

import snya.general.modelo.ObraSocial;
import snya.general.modelo.TipoDeDocumento;
import snya.reina.ReinaCte;
import snya.reina.modelo.Persona;
import snya.reina.modelo.joven.Joven;
import snya.reina.modelo.referente.Familiar;
import snya.reina.modelo.referente.TipoDeParentesco;
import snya.reina.serviciomodelo.resultado.ResultadoFamiliar;

public class ArbolFamiliar {

	// Familiares
	public ResultadoFamiliar generarFamiliar(Joven joven, Persona persona, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			ObraSocial obraSocial, TipoDeParentesco parentesco) {
		
		if (parentesco.isUnico() && this.existeUnFamiliarConParentescoUnico(joven, parentesco))		
			return new ResultadoFamiliar(
					String.format(ReinaCte.EXISTE_FAMILIAR_JOVEN, joven.getDescripcionJoven(),
							parentesco.getNombre(),
							persona.getNombreCompleto()));
				
		Familiar familiar = new Familiar(persona, vive, convive, referente, 
				tutor, observacion, obraSocial, parentesco);
		
		return new ResultadoFamiliar(familiar);
	}
	
	public ResultadoFamiliar modificarFamiliar(Joven joven, Familiar familiar,
			Boolean tieneDocumento, TipoDeDocumento tipoDeDocumento,
			String numeroDocumento, Boolean vive, Boolean convive,
			Boolean referente, Boolean tutor, String observacion,
			ObraSocial obraSocial, TipoDeParentesco parentesco) {
		
		if (parentesco.isUnico() && this.existeUnFamiliarConParentescoUnicoDistintoA(joven, familiar, parentesco))		
			return new ResultadoFamiliar(
					String.format(ReinaCte.EXISTE_FAMILIAR_JOVEN, joven.getDescripcionJoven(),
							parentesco.getNombre(),
							familiar.getNombreCompleto()));
		
		familiar.getPersona().asociarDocumentacion(tieneDocumento,
				tipoDeDocumento, numeroDocumento);
		familiar.setVive(vive);
		familiar.setConvive(convive);
		familiar.setReferente(referente);
		familiar.setTutor(tutor);
		familiar.setObservacion(observacion);
		familiar.setObraSocial(obraSocial);
		familiar.setParentesco(parentesco);
		
		return new ResultadoFamiliar(familiar);
	}

	
	private boolean existeUnFamiliarConParentescoUnico(Joven joven, TipoDeParentesco parentesco) {
		Iterator<Familiar> iter = joven.getFamiliares().iterator();
		boolean existe = false;
		
		while (!existe && iter.hasNext()) {
			Familiar f = iter.next();

			existe = f.getParentesco().getId().equals(parentesco.getId());
		}

		return existe;
	}
	
	private boolean existeUnFamiliarConParentescoUnicoDistintoA(Joven joven,
			Familiar familiar, TipoDeParentesco parentesco) {
		Iterator<Familiar> iter = joven.getFamiliares().iterator();
		boolean existe = false;
		
		while (!existe && iter.hasNext()) {
			Familiar f = iter.next();

			existe = !f.getId().equals(familiar.getId()) && f.getParentesco().getId().equals(parentesco.getId());
		}

		return existe;		
	}
}

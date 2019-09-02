package snya.reina.serviciomodelo;

import snya.general.datos.BarrioDAO;
import snya.general.datos.LocalidadDAO;
import snya.general.datos.MunicipioDAO;
import snya.general.datos.ProvinciaDAO;
import snya.general.modelo.Barrio;
import snya.general.modelo.Domicilio;
import snya.general.modelo.Localidad;
import snya.general.modelo.Municipio;
import snya.general.modelo.Provincia;



public class CreadorDeDomicilio {
	
	private ProvinciaDAO provinciaDAO;	
	private MunicipioDAO municipioDAO;	
	private LocalidadDAO localidadDAO;
	private BarrioDAO barrioDAO;
	
	public CreadorDeDomicilio(ProvinciaDAO provinciaDAO, MunicipioDAO municipioDAO,
			LocalidadDAO localidadDAO, BarrioDAO barrioDAO) {
		this.provinciaDAO = provinciaDAO;	
		this.municipioDAO = municipioDAO;	
		this.localidadDAO = localidadDAO;
		this.barrioDAO = barrioDAO;		
	}
	
	public Domicilio crear(Integer provinciaDomicilio, Integer municipioDomicilio,
			Integer localidadDomicilio, Integer domicilioCodigoPostal,
			Boolean chkDomicilioSinBarrio, Integer barrioDomicilio,
			Boolean chkSinDomicilioExacto, String domicilioCalle,
			String domicilioNumero, String domicilioPiso,
			String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio) {			
		Provincia provincia = (provinciaDomicilio == null) ? null : provinciaDAO.traerPorId(provinciaDomicilio);
		Municipio municipio =  (municipioDomicilio == null) ? null : municipioDAO.traerPorId(municipioDomicilio);
		Localidad localidad =  (localidadDomicilio == null) ? null : localidadDAO.traerPorId(localidadDomicilio);
		Barrio barrio =  null;		
		if (chkDomicilioSinBarrio != null && !chkDomicilioSinBarrio) {
			barrio = barrioDAO.traerPorId(barrioDomicilio);
		}
		boolean domicilioExacto = (chkSinDomicilioExacto != null && !chkSinDomicilioExacto);
	
		return this.crearDomicilio(domicilioExacto, domicilioCalle, domicilioNumero, domicilioPiso,
				domicilioTorre, domicilioDepto, domicilioEntre1, domicilioEntre2,
				provincia, municipio, localidad, barrio, referenciaDomicilio);
	}
	
	public void modificar(Domicilio domicilio, Integer provinciaDomicilio,
			Integer municipioDomicilio, Integer localidadDomicilio,
			Integer domicilioCodigoPostal, Boolean chkDomicilioSinBarrio,
			Integer barrioDomicilio, Boolean chkSinDomicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			String referenciaDomicilio) {	
		Provincia provincia = (provinciaDomicilio == null) ? null : provinciaDAO.traerPorId(provinciaDomicilio);
		Municipio municipio =  (municipioDomicilio == null) ? null : municipioDAO.traerPorId(municipioDomicilio);
		Localidad localidad =  (localidadDomicilio == null) ? null : localidadDAO.traerPorId(localidadDomicilio);
		Barrio barrio =  null;		
		if (chkDomicilioSinBarrio != null && !chkDomicilioSinBarrio) {
			barrio = barrioDAO.traerPorId(barrioDomicilio);
		}
		boolean domicilioExacto = (chkSinDomicilioExacto != null && !chkSinDomicilioExacto);
	
		this.modificarDomicilio(domicilio, domicilioExacto, domicilioCalle, domicilioNumero, domicilioPiso,
				domicilioTorre, domicilioDepto, domicilioEntre1, domicilioEntre2,
				provincia, municipio, localidad, barrio, referenciaDomicilio);
	}
	
	
	private Domicilio crearDomicilio(Boolean domicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			Provincia provincia, Municipio municipio, Localidad localidad,
			Barrio barrio, String referenciaDomicilio) {
		Domicilio domicilio = new Domicilio();
		
		if (domicilioExacto) {
			domicilio.setTieneReferenciaDomiciliaria(true);
			domicilio.setCalle(domicilioCalle);
			domicilio.setNumero(domicilioNumero);
			domicilio.setPiso(domicilioPiso);
			domicilio.setTorre(domicilioTorre);
			domicilio.setDepto(domicilioDepto);
			domicilio.setEntre1(domicilioEntre1);
			domicilio.setEntre2(domicilioEntre2);
		} else
			domicilio.setTieneReferenciaDomiciliaria(false);

		domicilio.setProvincia(provincia);
		domicilio.setMunicipio(municipio);
		domicilio.setLocalidad(localidad);	
		domicilio.setBarrio(barrio);
		domicilio.setObservacion(referenciaDomicilio);
		
		return domicilio;		
	}
	
	private Domicilio modificarDomicilio(Domicilio domicilio, Boolean domicilioExacto,
			String domicilioCalle, String domicilioNumero,
			String domicilioPiso, String domicilioTorre, String domicilioDepto,
			String domicilioEntre1, String domicilioEntre2,
			Provincia provincia, Municipio municipio, Localidad localidad,
			Barrio barrio, String referenciaDomicilio) {
		
		if (domicilioExacto) {
			domicilio.setTieneReferenciaDomiciliaria(true);
			domicilio.setCalle(domicilioCalle);
			domicilio.setNumero(domicilioNumero);
			domicilio.setPiso(domicilioPiso);
			domicilio.setTorre(domicilioTorre);
			domicilio.setDepto(domicilioDepto);
			domicilio.setEntre1(domicilioEntre1);
			domicilio.setEntre2(domicilioEntre2);
		} else
			domicilio.setTieneReferenciaDomiciliaria(false);

		domicilio.setProvincia(provincia);
		domicilio.setMunicipio(municipio);
		domicilio.setLocalidad(localidad);	
		domicilio.setBarrio(barrio);
		domicilio.setObservacion(referenciaDomicilio);
		
		return domicilio;		
	}
}



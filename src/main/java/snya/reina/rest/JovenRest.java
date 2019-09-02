package snya.reina.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import snya.general.modelo.Nacionalidad;
import snya.general.modelo.TipoDeDocumento;
import snya.reina.ReinaCte;
import snya.reina.ReinaException;
import snya.reina.modelo.Persona;
import snya.reina.modelo.joven.Joven;
import snya.reina.rest.dto.JovenSimpleDTO;
import snya.reina.servicio.JovenServicioImpl;
import snya.reina.servicio.PersonaServicioImpl;
import snya.reina.utilidades.Utilidades;

/* No usamos ninguna librería para conversión a JSON porque Spring-Boot lo devuelve por defecto con Jackson2 - algunos métodos son nativos de la JpaRepository interface */
/* Estructura básica REST CRUD start */
@RestController
@RequestMapping("/api/jovenes")
@Api("Joven - Servicio web REST")
public class JovenRest {

	@Autowired
	JovenServicioImpl jovenServicioImpl;

	@Autowired
	PersonaServicioImpl personaServicioImpl;

	/* Productivo */
	/* Joven ahora imprime un Joven pero va a imprimir un JovenDTO */
	@GetMapping("/{id}")
	public Joven obtenerJoven(@PathVariable Integer id) {
		return jovenServicioImpl.traerPorId(id);
	}

	/* Productivo */
	/* JovenSimpleDTO */
	@GetMapping("/simple/{id}")
	public JovenSimpleDTO obtenerJovenSimplificado(@PathVariable Integer id) {
		/* Encontramos el Joven a quien resumir la información */
		Joven joven = jovenServicioImpl.traerPorId(id);
		/* Creamos Joven simplificado */
		JovenSimpleDTO js = new JovenSimpleDTO(joven);
		/* Etc etc los atributos que quieras que se vean en Joven simplificado */
		return js;
	}
	
	/* Estas dos rutas tiran ERROR DE MAPEO, auqnue cambien los parámetros ya que son las mismas
	 * si ponemos /api/personas?buscar= a que pongamos api/personas?numeroDocumento=&apellidos=&nombres=
	 * Spring tira error de mapeo aunque sean diferentes los parámetros.
	 * */

	/* Productivo */
	@GetMapping("/simple/busqueda1")
	public List<JovenSimpleDTO> obtenerJovenSimplificadoBusquedaMixto1(@RequestParam String buscar) throws ReinaException {
		/* Para hacer una búsqueda más eficiente reducimos la búsqueda a dos caractéres*/
		if(buscar.length() >= 2) {
			return jovenServicioImpl.traerPorCriterioDeBusquedaMixto1(buscar);
		}else {
			throw new ReinaException(ReinaCte.POCOS_CARACTERES_DE_BUSQUEDA);
		}
	}
	
	/* Productivo */
	@GetMapping("/simple/busqueda2")
	public List<JovenSimpleDTO> obtenerJovenSimplificadoBusquedaMixto2(@RequestParam String numeroDocumento, @RequestParam String apellidos, @RequestParam String nombres) throws ReinaException {
		if(Utilidades.validadorDNI(numeroDocumento).length() > 3 || apellidos.length() >= 2 || nombres.length() >= 2 ) {
			return jovenServicioImpl.traerPorCriterioDeBusquedaMixto2(numeroDocumento, apellidos, nombres);
		}else {
			throw new ReinaException(ReinaCte.POCOS_CARACTERES_DE_BUSQUEDA);
		}
	}

	/* Recomiendo una respuesta http Response por sobre el boolean, porque devolver un boolean va a dar siempre 200 
	 * por ende puede ser confuso para el otro lado de la API, si devolvemos un 400 o 401, puede ser más acertado
	 * Funcionaría de la misma manera como boolean el método */
	@PutMapping("/{id}")
	public ResponseEntity<String> actualizarJoven(@PathVariable Integer id, @RequestParam String legajo, @RequestParam Integer idCaratulador) throws Exception {
		Joven jovenEnDB = null;
		if (id != null){
				if(jovenServicioImpl.traerPorId(id) != null) {
					jovenEnDB = jovenServicioImpl.traerPorId(id);
				}else {
					throw new ReinaException(ReinaCte.VALOR_NULO + ", el objeto buscado es nulo y no se puede encontrar");
				}
			if (legajo != null) {
				/* No sé qué hay que setear para la actualización, es un boceto tipo Dummy */
				jovenEnDB.getLegajo().setObservacion("");
				/* debería ser 201 "Created" o 204 "No Content" para PUT, sin embargo puede dejarse 200 por defecto */
				return ResponseEntity.ok().body("Operación realizada con éxito");
			} else {
				return ResponseEntity.badRequest().body("El legajo es nulo");
			}
		}else {
			throw new ReinaException(ReinaCte.VALOR_NULO + ", parámetro 'id' null");
		}
	}

	@DeleteMapping("/{id}")
	public void eliminarJoven(@PathVariable Integer id) throws ReinaException {
		if (id != null)
			jovenServicioImpl.eliminarJoven(id);
	}

	/*
	 * Para no pasar todos los parámetros por separado, declaramos el objeto en
	 * cuestion como parámetro y si o si del lado del front ponemos exactamente el
	 * mismo nombre de atributo en la llamada ajax.
	 * 
	 * Primer boceto, funciona como DUMMY para ingreso de algun dato, NO ES PRODUCTIVO
	 */
	@PostMapping("/")
	public void insertarJoven(@ModelAttribute Persona persona, @ModelAttribute TipoDeDocumento tipoDocumento,
			@ModelAttribute Nacionalidad nacionalidad, boolean fichaDactiloscopica) throws ReinaException {
		// /* Creo Persona */
		// Persona persona = personaServicioImpl.crearPersona("Perez", "Martin",
		// "asdasd", new Date(), 25, "M", true, 4, "5656565", 1, true);
		// /* Creo al Joven */
		// Joven joven = (Joven) jovenServicioImpl.crearJoven(true, persona);
		// /* Creo el Una situación y lo agrego a una lista*/
		// jovenServicioImpl.agregarSituacionDocumentacion(joven, new Date(), 7, 8,
		// "4534546546", true, true, "fdgfg", true, 23, "asdasd", true , "dsfdfg", true,
		// true, "No", "No");
		// jovenServicioImpl.insertar(joven);

	}
	
//	@GetMapping("/simplificado/test")
//	public List<JovenSimplificado> obtenerJovenSimplificadoBusquedaMixto2(@RequestParam String numeroDocumento, @RequestParam String apellidosNombres) throws ReinaException {
//		List<String> params = new ArrayList<>();
//		String apellidos = null;
//		String nombres = null;
//		
//		String[] array = apellidosNombres.split(" ");
//		System.out.println(array.length);
//		for (String item : array) {
//			params.add(item);
//			
//		}
//		if(params.get(0) != null) {
//			apellidos = params.get(0);
//			System.out.println(apellidos);
//			if(params.get(1) != null) {
//				nombres = params.get(1);
//				System.out.println(nombres);
//			}
//		}
//	
//		//		if(numeroDocumento.length() > 3) {
//			return jovenServicioImpl.traerPorCriterioDeBusquedaMixto2(numeroDocumento, apellidos, nombres);
////		}else {
////			throw new ReinaException(ReinaCte.POCOS_CARACTERES_DE_BUSQUEDA);
////		}
//	}

}

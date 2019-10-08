package snya.reina.rest.seguridad;

import snya.reina.rest.seguridad.modelo.UsuarioToken;

public class FuncionesSeguridad {
    /* Derivador de Roles */
    public static boolean autorizar(UsuarioToken usuarioToken, String nivelDeSeguridad) {
    	boolean autorizado = false;
    	switch(nivelDeSeguridad) {
    	case "ROL_FUNCIONARIO":
    		autorizado = true;
    	case "ROL_CONSULTA":
    		autorizado = true;
      	case "ROL_VISTA_LEGAJO":
      		autorizado = true;
      	case "ROL_VISTA_LEGAJO_UBICACION":
      		autorizado = true;
      	case "ROL_VISTA_LEGAJO_TECNICO":
      		autorizado = true;
    	}
    	return autorizado;
    }
}

package snya.reina;


public class ReinaCte {
	public static final String LOGIN_VACIO = "Debe consignar el nombre de usuario y contraseña.";
	public static final String PASSWORD_VACIO = "Debe consignar el nombre de usuario y contraseña.";
	public static final String PASSWORD_NO_VALIDA = "Usuario o contraseña invalidos.";
	public static final String SISTEMA_NO_ACTIVO_PARA_USUARIO = "El usuario no esta autorizado para el sistema.";
	public static final String NO_POSIBLE_SITUACION_TRAMITE_SIGUIENTE = "No se puede indicar la situacion de tramite %2$s luego de haber registrado el estado %1$s.";
	public static final String EXISTE_FAMILIAR_JOVEN = "Para el joven ya existe un familiar para el joven %1$s con parentesco %2$s, que no es %3$s .";
	public static final String FECHA_INICIO_PROCESO_PENAL_ERRONEA = "Debe consignar una fecha de proceso valida.";
	public static final String FECHA_SITUACION_TRAMITE_ES_MENOR = "La nueva situacion del tramite debe ser mayor a %s.";
	public static final String FECHA_INTERVENCION_ERRONEA = "Debe consignar una fecha de intervencion valida";
	public static final String FECHA_BAJA_INTERVENCION_ERRONEA = "Debe consignar una fecha de baja de la intervencion valida.";
	public static final String APELLIDO_PERSONA_NULO = "Debe consignar el apellido";
	public static final String NOMBRE_PERSONA_NULO = "Debe consignar el nombre";
	public static final String FECHANACIMIENTO_EDAD_PERSONA_NULO = "Debe consignar la fecha de nacimiento o su edad";
	public static final String SEXO_PERSONA_INVALIDO = "Debe consignar un valor correcto para el sexo (M o F)";
	public static final String CUIL_PERSONA_INVALIDO = "Debe consignar un valor correcto para el cuil, 11 digitos.";
	public static final String FECHANACIMIENTO_PERSONA_INVALIDA = "Debe consignar una fecha de nacimiento valida.";
	public static final String JOVEN_SIN_SITUACION_TRAMITE = "El joven %1$s debe poseer al menos una situacion sobre su documentacion de identidad.";
	public static final String BUSCAR_JOVEN_SIN_DATO_APYN = "Debe ingresar el apellido y/o nombre con el que desea buscar.";
	public static final String BUSCAR_JOVEN_SIN_DATO_NRO = "Debe ingresar el documento con el que desea buscar.";
	public static final String BUSCAR_JOVEN_NRO_ERRONEO = "Debe consignar un numero de documento valido.";
	public static final String BUSCAR_JOVEN_SIN_DATO_RECURSO = "Debe indicar el recurso por el cual se desea buscar.";

	public static final String FECHA_MOTIVO_FUERA_DE_TERMINO = "La fecha del motivo del proceso no es valida.";
	public static final String NO_MODIFICA_ULTIMO_MOTIVO = "El motivo a modificar debe ser el ultimo ingresado.";
	public static final String NO_ELIMINA_ULTIMO_MOTIVO = "El motivo a eliminar debe ser el ultimo ingresado.";
	public static final String SOLO_UN_MOTIVO = "Existe un solo motivo en el proceso, por lo cual no se puede eliminar.";
	
	
	public static final String FECHA_MEDIDA_FUERA_DE_TERMINO = "La fecha de la medida adoptada no es valida.";
	public static final String NO_MODIFICA_ULTIMA_MEDIDA = "La medida a modificar debe ser el ultimo ingresado.";
	public static final String NO_ELIMINA_ULTIMA_MEDIDA = "La medida a eliminar debe ser el ultimo ingresado.";
	public static final String NO_ELIMINA_PRIMERA_MEDIDA =  "No se puede eliminar la primer medida impuesta.";
	public static final String SOLO_UNA_MEDIDA = "Existe una sola medida en el proceso, por lo cual no se puede eliminar.";
	public static final String EXISTEN_MOVIMIENTOS_PARA_MEDIDA = "Existen movimientos asociados a la medida por lo que no puede eliminar dicha medida judicial";
	public static final String EXISTEN_INTERVENCIONES_PARA_MEDIDA = "Existen intervenciones de los centros de referencia asociadas a la medida por lo que no puede eliminar dicha medida judicial";
	
	
	public static final String FECHA_MOMENTO_FUERA_DE_TERMINO = "La fecha del momento procesal que desea agregar no es valida.";
	public static final String NO_MODIFICA_ULTIMO_MOMENTO = "El momento procesal a modificar debe ser el ultimo ingresado.";
	public static final String NO_ELIMINA_ULTIMO_MOMENTO = "El momento procesal a eliminar debe ser el ultimo ingresado.";
	public static final String SOLO_UN_MOMENTO = "Existe un solo momento en el proceso, por lo cual no se puede eliminar.";
	
	
	public static final String TIPO_Informe_NO_DEFINIDO = "Se debe indicar la tipificacion de la Informe adoptada.";
	public static final String NO_ES_ULTIMO_MOVIMIENTO = "No se puede realizar la operacion pues no es el ultimo movimiento.";
	public static final String MOVIMIENTO_NO_ESTA_PENDIENTE = "El movimiento no esta pendiente de confirmacion o cancelacion.";
	public static final String PROCESO_FINALIZADO = "El proceso no se puede modificar pues se encuentra cerrado";
	public static final String PROCESO_NO_FINALIZADO = "El proceso que se desea reabrir no se encuentra cerrado";
	public static final String PROCESO_YA_ESTA_EN_MOMENTO_PROCESAL = "El proceso ya se encuentra en dicho momento procesal";
	
	public static final String TELEFONO_SIN_TIPO = "Debe consignar el tipo de telefono";
	public static final String TELEFONO_SIN_CARACTERISTICA = "Se debe indicar la caracteristica del telefono. ej: 011";
	public static final String TELEFONO_SIN_NUMERO = "Se debe indicar el número del teléfono";
	public static final String FECHA_CESE_HABEAS_MENOR_FECHA_IMPOSICION = "La fecha de cese del HABEAS debe ser posterior a la fecha de imposicion";
	public static final String FECHA_NOTA_MAYOR_FECHA_HABEAS = "La fecha de la nota debe ser posterior al del HABEAS";
	public static final String EL_INFORME_NO_PERTENECE_AL_JOVEN = "El informe que selecciono no pertenece al joven";
	
	public static final String ELIMINAR_JOVEN_SIN_EXPEDIENTE_ANULADO = "El joven a eliminar no debe tener expediente o el mismo debe estar anulado";
	
	public static final String GRUPO_INFORME_SIN_NOMBRE = "Debe consignar un nombre para el grupo.";
	public static final String GRUPO_INFORME_SIN_PERMISO = "Un grupo de informe debe tener al menos un permiso asignado.";

	public static final String DOCUMENTO_REPETIDO_PARA_JOVEN = "El documento %s se encuentra cargado para otro Joven.";
	public static final String FECHA_ARCHIVO_MAL_FORMATO = "La fecha es erronea";
	
	public static final String TIENE_PENSION_ACTIVA = "El joven ya cuenta con una pension activa.";
	public static final String FECHA_ALTA_BENEFICIO_NULA = "El beneficio siempre debe tener una fecha de alta.";
	public static final String LA_FECHA_BAJA_BENEFICIO_MAYOR_FECHA_INICIO = "No puede consignar la fecha de baja del beneficio anterior a la fecha de alta.";
	public static final String EXISTE_BENEFICIO_VIGENTE = "El joven ya tiene un beneficio %1$s vigente.";
	public static final String FALTA_CONSIGNAR_FECHA_BAJA_BENEFICIO = "Debe consignar la fecha de baja del beneficio si el mismo se indica 'Dado de baja'.";
	public static final String EL_ESTADO_DEL_BENEFICIO_DEBE_SER_VIGENTE = "El estado del beneficio debe ser 'En tramite' o 'Activo'.";
	
	public static final String FECHA_RECIBIR_LEGAJO_NULA = "Debe consignar la fecha de recepcion del legajo del joven.";
	public static final String INTERVENCION_SRPJ_SIN_MEDIDA = "La intervencion del centro de referencia debe ser en el marco de una medida judicial.";
	public static final String FECHA_APELACION_FUERA_RANGO_FECHA_MEDIDA = "La fecha de apelacion debe encontrarse dentro de la vigencia de la medida.";
	public static final String FALTA_INFORMACION_PARA_RETROCEDER_DECLINACION = "No se cuenta con la informacion para retroceder la accion de declinar competencia.";
	public static final String FECHA_UNIFICACION_MENOR_MEDIDA = "La fecha de unificacion de procesos debe ser mayor a la fecha de la medida judicial en curso para el proceso.";
	public static final String FALTA_APELACION_SUSPENSIVA_PARA_RESPONDER = "No se puede realizar la carga de la respuesta ya que no existe una apelacion suspensiva sobre la medida";
	public static final String FALTA_APELACION_NO_SUSPENSIVA_PARA_RESPONDER = "No se puede realizar la carga de la respuesta ya que no existe una apelacion no suspensiva sobre la medida";
	
	public static final String MEDIDA_CIERRA_PROCESO_NO_ES_ULTIMA = "La medida judicial impuesta cierra el proceso, pero no puede ser cargada dado que existen otras medidas adoptadas con posterioridad";
	public static final String MOMENTO_PRIMERO_NO_TIENE_MISMA_FECHA_MEDIDA = "El primer momento procesal debe la misma fecha que la primer medida impuesta.";
	public static final String MOMENTO_ANTERIOR_CON_FECHA_MAS_CHICA = "El momento procesal anterior al modificado tiene una fecha de imposicion menor a la indicada.";
	public static final String MOMENTO_SIGUIENTE_CON_FECHA_MAS_CHICA = "El momento procesal siguiente al modificado tiene una fecha de imposicion menor a la indicada.";
	
	public static final String POCOS_CARACTERES_DE_BUSQUEDA = "La cantidad de caractéres es insuficiente para realizar la búsqueda.";
	public static final String VALOR_NULO = "La operación no fue exitosa ya que el dato fue Nulo";
	public static final String NO_AUTORIZADO = "No autorizado porque no posee los permisos/ roles adecuados para ver esta información";

	
	
	public static String Format(String mensaje,
			Object... parametros) {
		return String.format(mensaje, parametros);
	}

}

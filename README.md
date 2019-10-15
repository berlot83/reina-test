Codigo fuente java.

El ingreso de prueba Remoto a este proyecto se realiza desde:

Path principal con documentación.
http://186.19.113.224:8080/index

Path de visualización de resultados tanto de Reina como de Reuna o User Interface al momento.
http://localhost:8080/asociador.html

Cada llamada debe estar acompañada en el header con un "token", con los siguientes atributos:  Nombre, Apellidos, Email, Usuario, Rol, Sector. Los roles al momento permitidos son los siguientes:  ROL_FUNCIONARIO, ROL_CONSULTA, ROL_VISTA_LEGAJO, ROL_VISTA_LEGAJO_UBICACION, ROL_VISTA_LEGAJO_TECNICO.

Tener en cuenta que el CORS en este proyecto está desactivado del lado del servidor, sin embargo es probable que haya que desactivarlo también del lado del cliente.

snya.reina.rest/** Todos los paquetes pertenecientes a este paquete almacenan Clases e interfaces dedicadas a los Endpoints del proyecto.

Se Agregaron parcialmente datos a JovenSimplificado, sin embargo quedó en deshuso ya que se creó JovenDTO

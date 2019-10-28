				
/* Calls a Reina */
$(document).ready(function() {
	/* Inicializo la variable de reusultados */
	var data;
	/* Inicializo la tabla general para todos los resultados */
	var tabla= 	$('#tablaReina').DataTable( {
			"data": data,
			"columns": [
	            { "data" : "apellidoYNombre" },
				{ "data" : "nombres" },
				{ "data" : "apellidos"},
				{ 
					"data" : "numeroDocumento",
					"defaultContent": ""
				},
				{
					"data" : "tipoDeDocumento.nombreCorto",
					"defaultContent": ""
				},
				{ "data" : "situacionDocumentacion" },
				{ "data" : "sexo" },
				{ "data" : "historicidad" },
				{
				      "data": null,
				      "defaultContent": "<img  alt='Asociar con Reuna' src='../src/img/edit-icon.png'/>",
			}]
	} );
	
	/* Busqueda en un parámetro */
	$("#botonBusquedaMixta1").click(function(){
		tabla.clear().draw();
		var buscar = document.getElementById("busquedaMixta1").value;
		$.ajax({
	    	type : "GET",
	    	url: "api/jovenes/simple/busqueda1?buscar="	+ buscar,
	        headers: {
	        	"token":
	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
	        },
		}).done(function(data) {	
			console.log(JSON.stringify(data, null, 2));
			for (var i = 0; i < data.length; i++) {
				tabla.row.add(data[i]).draw(false);
			}
		})	
	});
	
	/* Busqueda en tres parámetros */
	$("#botonBusquedaMixta2").click(function(){
		tabla.clear().draw();
		var documento = document.getElementById("paramDocumento").value;
		var nombre = document.getElementById("paramNombre").value;
		var apellido = document.getElementById("paramApellido").value;
		$.ajax({
	    	type : "GET",
	    	url : "api/jovenes/simple/busqueda2?numeroDocumento=" + documento + "&apellidos="+ apellido + "&nombres=" + nombre,
	        headers: {
	        	"token":
	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
	        },
		}).done(function(data) {	
			console.log(JSON.stringify(data, null, 2));
			for (var i = 0; i < data.length; i++) {
				tabla.row.add(data[i]).draw(false);
			}
		})	
	});

	/* Busqueda en un parámetro */
	$("#botonBusquedaMixta3").click(function(){
		tabla.clear().draw();
		var buscar = document.getElementById("busquedaMixta3").value;
		$.ajax({
	    	type : "GET",
	    	url: "api/jovenes/simple/busqueda3?buscar="	+ buscar,
	        headers: {
	        	"token":
	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
	        },
		}).done(function(data) {	
			console.log(JSON.stringify(data, null, 2));
			for (var i = 0; i < data.length; i++) {
				tabla.row.add(data[i]).draw(false);
			}
		})	
	});
	
	/* Busqueda individual por id */
	$("#botonBusquedaIndivual").click(function(){
		tabla.clear().draw();
		var id = document.getElementById("idBuscarIndividual").value;
		$.ajax({
	    	type : "GET",
	    	url: "api/jovenes/simple/"+ id,
	        headers: {
	        	"token":
	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
	        },
		}).done(function(data) {	
			console.log(JSON.stringify(data, null, 2));
			tabla.row.add(data).draw(false);
		})	
	});

	$("#botonBusquedaInforme").click(function(){
		var idInforme = document.getElementById("busquedaInforme").value;
		$.ajax({
	    	type : "GET",
	    	url: "api/informes/simple/"+ idInforme,
	        headers: {
	        	"token":
	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
	        },
		}).done(function(data) {
				$.alert({
					type: 'blue',
					title: data.institucion,
					content: "<html><body><div>"+ data.texto +"</div></body></html>",
					columnClass: 'large/l'
				});
				console.log(JSON.stringify(data, null, 2));
		}).fail(function(error) {
			$.alert({
				type: 'red',
				title: 'Error',
				content: "<html><body><div>"+ error.responseJSON.message +"</div></body></html>",
				columnClass: 'small/s'
			});
			/* Quitar en producción */
			console.log('Error:', error);
		});	
	});

} );

/* Calls a Reuna */
$(document).ready(function() {
	/* Inicializo la variable de reusultados */
	var data;
	/* Inicializo la tabla de Reuna */
	var tabla= 	$('#tablaReuna').DataTable( {
		"data": data,
		"columns": [
            { "data" : "Legajo" },
			{ "data" : "Nombre" },
			{ "data" : "Fecha_Nac"},
			{ 
				"data" : "Doc_Tiene",
				"defaultContent": ""
			},
			{
				"data" : "Documento",
				"defaultContent": ""
			},
			{ "data" : "Cuil" },
			{ "data" : "Sexo" },
			{ "data" : "Nacionalidad" },
			{ "data" : "Municipio" },
			{ "data" : "Provincia" },
			{ "data" : "Direccion" },
			{ "data" : "Telefono" },
			{ "data" : "Zonal" },
			{ "data" : "Local" },
			
			{
			      "data": null,
			      "defaultContent": "<img  alt='Asociar con Reuna' src='../src/img/edit-icon.png'/>"
		}]
} );
	
	$("#botonBusquedaReuna1").click(function(){
		tabla.clear().draw();
		var buscar = document.getElementById("busquedaReuna1").value;
		$.ajax({
	    	type : "GET",
	    	url: "http://163.10.35.7:8080/reuna/api2/jovenes?buscar="+ buscar,
	        headers: {
	        	"Content-Type": "application/json; charset=UTF-8",
	        	"Access-Control-Allow-Origin": "*",
	        	"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg",
	        	"Access-Control-Allow-Methods": "GET, POST, PUT, DELETE",
	        	"Access-Control-Max-Age":" 3600",
	        	"Access-Control-Allow-Headers":"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
	        },
		}).done(function(data) {
			console.log(JSON.stringify(data, null, 2));
			for (var i = 0; i < data.length; i++) {
				tabla.row.add(data[i]).draw(false);
			}
		}).fail(function(error) {
			$.alert({
				type: 'red',
				title: 'Error',
				content: "No encontrado",
				columnClass: 'small/s'
			});
		});	
	});	
});

/* Call a Reuna Legajo */
$(document).ready(function() {
	var data;
	var tabla= 	$('#tablaReunaLegajo').DataTable( {
		"data": data,
		"columns": [
            { "data" : "Id" },
			{ "data" : "Nombre" },
			{ "data" : "Fecha_nac"},
			{
				"data" : "Documento",
				"defaultContent": ""
			},
			{ "data" : "Cuil" },
			{ "data" : "Sexo" },
			{ "data" : "Municipio" },
			{ "data" : "Provincia" },
			{ "data" : "Zonal" },
			{ "data" : "Local" },
			
			{
			      "data": null,
			      "defaultContent": "<img  alt='Asociar con Reuna' src='../src/img/edit-icon.png'/>"
		}]
} );
	
		$("#botonBusquedaLegajo").click(function(){
		tabla.clear().draw();
		var buscar = document.getElementById("busquedaLegajo").value;
		$.ajax({
	    	type : "GET",
	    	url: "http://163.10.35.7:8080/reuna/api2/dameLegajo?id="+ buscar,
	        headers: {
	        	"Content-Type": "application/json; charset=UTF-8",
	        	"Access-Control-Allow-Origin": "*",
	        	"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg",
	        	"Access-Control-Allow-Methods": "GET, POST, PUT, DELETE",
	        	"Access-Control-Max-Age":" 3600",
	        	"Access-Control-Allow-Headers":"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
	        },
		}).done(function(data) {
			console.log(JSON.stringify(data, null, 2));
			for (var i = 0; i < data.length; i++) {
				tabla.row.add(data[i]).draw(false);
			}
		}).fail(function(error) {
			$.alert({
				type: 'red',
				title: 'Error',
				content: "No encontrado",
				columnClass: 'small/s'
			});
		});	
	});

});


/* Call a Reuna Intervenciones */
$(document).ready(function() {
	var data;
	var tabla= 	$('#tablaReunaIntervencion').DataTable( {
		"data": data,
		"columns": [
            { "data" : "Id" },
			{ "data" : "Id_Legajo" },
			{ "data" : "Fecha"},
			{ "data" : "Zonal" },
			{ "data" : "Local" },
			{
				"data" : "Derivado_Por",
				"defaultContent": ""
			},
			{ "data" : "Motivo" },
			{ "data" : "Nombre_Estado" },
			{ "data" : "Detalle_Intervencion" },
			{ "data" : "Observaciones" },
			
			{
			      "data": null,
			      "defaultContent": "<img  alt='Asociar con Reuna' src='../src/img/edit-icon.png'/>"
		}]
} );
	
		$("#botonBusquedaIntervencion").click(function(){
		tabla.clear().draw();
		var buscar = document.getElementById("busquedaIntervencion").value;
		$.ajax({
	    	type : "GET",
	    	url: "http://163.10.35.7:8080/reuna/api2/dameIntervencion?id="+ buscar,
	        headers: {
	        	"Content-Type": "application/json; charset=UTF-8",
	        	"Access-Control-Allow-Origin": "*",
	        	"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbmluc3RyYWRvciIsIk5vbWJyZSI6Ik1hcmNlbG8iLCJBcGVsbGlkbyI6IlBlcmV5cmEiLCJFbWFpbCI6Im1hcmNlbG9Ac255YS5jb20uYXIiLCJSb2wiOiJBZG1pbmluc3RyYWRvciIsIlNlY3RvciI6IlNOWUEgTGEgUGxhdGEifQ.UdvtF3a-lFlDghso8EK4e3bCVY5t5YdzsxhbbjCqGGteqbaKSwnvNJAoKWF1E9sRCWJWNi3o1-Z6X9ujdj4uPg",
	        	"Access-Control-Allow-Methods": "GET, POST, PUT, DELETE",
	        	"Access-Control-Max-Age":" 3600",
	        	"Access-Control-Allow-Headers":"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
	        },
		}).done(function(data) {
			console.log(JSON.stringify(data, null, 2));
			for (var i = 0; i < data.length; i++) {
				tabla.row.add(data[i]).draw(false);
			}
		}).fail(function(error) {
			$.alert({
				type: 'red',
				title: 'Error',
				content: "No encontrado",
				columnClass: 'small/s'
			});
		});	
	});

});


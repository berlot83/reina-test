				
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
} );


$(document).ready(function() {
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
});

/* Backup de funciones individuales,que llaman a endpoints, hay que volver a agregar los tags html si se llegan a usar */
//function buscarIndividual(){
//	$(document).ready(function() {
//		var id = document.getElementById("idBuscarIndividual").value;
//		
//		$.ajax({
//	    	type : "GET",
//	        url: "api/jovenes/simple/"+ id,
//	        headers: {
//	        	"token":
//	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
//	        },
//		}).done(function(data) {	
//			console.log(JSON.stringify(data, null, 2));
//			var jsonData = [
//			   data
//			];
//				$('#buscarIndividualTabla').DataTable( {
//					"data": jsonData,
//			        "columns": [
//				            { "data" : "apellidoYNombre" },
//							{ "data" : "nombres" },
//							{ "data" : "tieneDocumento" },
//							{ "data" : "tipoDeDocumento.nombre" },
//							{ "data" : "tipoDeDocumento.nombreCorto" },
//							{ "data" : "situacionDocumentacion" },
//							{ "data" : "sexo" },
//							{ "data" : "historicidad" },
//							{
//						      "data": null,
//						      "defaultContent": "<img  alt='Asociar con Reuna' src='../src/img/edit-icon.png'/>"
//							}
//						]
//				} );
//		})
//	} );
//}

//function busquedaMixta1(){
//	
//	
//		var busquedaMixta1Div = document.getElementById("busquedaMixta1Div");
//		var buscar = document.getElementById("busquedaMixta1").value;
//		$.ajax({
//	    	type : "GET",
//	    	url: "api/jovenes/simple/busqueda1?buscar="	+ buscar,
//	        headers: {
//	        	"token":
//	   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
//	        },
//		}).done(function(data) {	
//			console.log(JSON.stringify(data, null, 2));
//				$('#busquedaMixta1Tabla').DataTable( {
//					"data": data,
//					"columns": [
//			            { "data" : "apellidoYNombre" },
//						{ "data" : "nombres" },
//						{ "data" : "tieneDocumento" },
//						{ "data" : "tipoDeDocumento.nombre" },
//						{ "data" : "tipoDeDocumento.nombreCorto" },
//						{ "data" : "situacionDocumentacion" },
//						{ "data" : "sexo" },
//						{ "data" : "historicidad" },
//						{
//						      "data": null,
//						      "defaultContent": "<img  alt='Asociar con Reuna' src='../src/img/edit-icon.png'/>",
//						    }]
//				} );
//		})
//}

/* Busqueda mixta1 */
//function busquedaMixta1() {
//	var busquedaMixta1Div = document
//			.getElementById("busquedaMixta1Div");
//	busquedaMixta1Div.innerHTML = "";
//	var buscar = document.getElementById("busquedaMixta1").value;
//	$
//			.ajax({
//				type : "GET",
//				beforeSend : function(request) {
//					request
//							.setRequestHeader(
//									"token",
//									"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA");
//				},
//				url : "api/jovenes/simple/busqueda1?buscar="	+ buscar,
//				dataType : "json",
//				processData : false,
//				success : function(data) {
//					busquedaMixta1Div.innerHTML = JSON.stringify(data, null, 2);
//				}
//			});
//}

/* Busqueda mixta2 */
//function busquedaMixta2() {
//	var busquedaMixta2Div = document.getElementById("busquedaMixta2Div");
//	busquedaMixta2Div.innerHTML = "";
//	var documento = document.getElementById("paramDocumento").value;
//	var nombre = document.getElementById("paramNombre").value;
//	var apellido = document.getElementById("paramApellido").value;
//	$
//			.ajax({
//				type : "GET",
//				beforeSend : function(request) {
//					request
//							.setRequestHeader(
//									"token",
//									"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA");
//				},
//				url : "api/jovenes/simple/busqueda2?numeroDocumento=" + documento + "&apellidos="+ apellido + "&nombres=" + nombre,
//				dataType : "json",
//				processData : false,
//				success : function(data) {
//					alert(data);
//					
//					$("#busquedaMixta2Div").append(
//							JSON.stringify(data, null, 2));
//				}
//			});
//}



//$(document).ready(function() {
//	var objeto = {};
//	var result = [];
//	objeto.data = result;
//	var id = document.getElementById("idBuscarIndividual").value;
//	
//	$.ajax({
//    	type : "GET",
//        url: "api/TipoBeneficio/1",
//        headers: {
//        	"token":
//   			"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJVc3VhcmlvIjoiYXhlbCIsIk5vbWJyZSI6IkF4ZWwiLCJBcGVsbGlkbyI6IkJlcmxvdCIsIkVtYWlsIjoiYmVybG90ODNAeWFob28uY29tLmFyIiwiUm9sIjoiUk9MX0ZVTkNJT05BUklPIiwiU2VjdG9yIjoiTEEgUExBVEEifQ.XaWl34KzNxYkJO5PeMBhA-zv5P3a4yA2FDvYyfweSO7enbOR_J8gm16tn3wW_RJLrddRitmfn-ECW0riEd3EpA"
//        },
//	}).done(function(data) {	
//		var jsonData = [
//		   data
//		];
//			$('#buscarIndividualTabla').DataTable( {
//				"data": jsonData,
//		        "columns": [
//		            { "data" : "id" },
//					{ "data" : "nombre" },
//					{ "data" : "nombreCompleto" },
//					{ "data" : "porDefecto" },
//		        ]
//			} );
//	})
//} );
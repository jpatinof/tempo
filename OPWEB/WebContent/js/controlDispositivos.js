'use strict'

function uuidv4()
{
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c)
	{
		var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

var opened = false;
var connection = null;

function connect(av_url)
{
	var trama = av_url;

	if (connection == null) {
		console.log('CON No asignada aún');
	}
	else { // asignada
		console.log("CON cerrar oconexión previa");
		connection.close(3001, "inicio de petición");
		connection = null;
	}
	console.log('CON connect start');

	connection = new WebSocket('ws://localhost:1104/');

	console.log('CON setup onopen');
	connection.onopen = function()
	{
		console.log('CON OPEN opened');
		opened = true;
		PF('statusDispositivo').show();
		console.log('CON OPEN enviar trama >' + trama);
		connection.send(trama);
	}

	console.log('CON setup onmessage');
	connection.onmessage = function(message)
	{
		HoldOn.close();
		console.log("CON MSG BIOCLIENT AVISA:");

		var response = JSON.parse(message.data);
		console.log(response.event + ' : ' + response.response);

		if (response.event === 'enroll') {
			if (response.response === 'success') {
				console.log('CON enroll OK');
				enroll();
			}
			else {
				console.log('CON enroll fallido');
				fail([ {
					name : 'event',
					value : response.event
				} ]);
			}
		}
		else if (response.event === 'verify') {
			if (response.response === 'success') {
				console.log('CON verify OK');
				verify();
			}
			else {
				console.log('CON verify falla');
				fail([ {
					name : 'event',
					value : response.event
				} ]);
			}
		}
		else if (response.event === 'reset') {
			if (response.response === 'success') {
				console.log('CON reset OK');
				reset();
			}
			else {
				console.log('CON reset falla');
				fail([ {
					name : 'event',
					value : response.event
				} ]);
			}
		}
		else if (response.event === 'sign') {
			if (response.response === 'success') {
				console.log('CON sign OK');
				firma();
			}
			else {
				console.log('CON sign falla');
				fail([ {
					name : 'event',
					value : response.event
				} ]);
			}
		}
		else if (response.event === 'pdf') {
			if (response.response === 'success') {
				console.log("CON Pdf OK");
				impresionCorrecta = true;
			}
			else {
				console.log("CON Pdf fail");
				impresionCorrecta = false;
			}
			console.log("CON Imprimir siguiente");

			setTimeout(function()
			{
				impresionCorrecta ? imprimirDocumento() : failImpresion();
				console.log("CON Se reportó al servidor el resultado");
			}, 1000);
		}

		connection.close(3002, 'Fin operación');
	}

	console.log('CON setup onclose');
	connection.onclose = function()
	{
		console.log('CON closing');
		PF('statusDispositivo').hide();
		opened = false;
	}

	console.log('CON setup end');
}

function procesar()
{
    segundaVerificacion([ {
		name : 'generarId',
		value : uuidv4()
	} ]);

}

function reset()
{
	alert('Contraseña actualizada con éxito');
};

function enroll()
{
	alert('Usuario enrolado con éxito');
};

function cambiarEstadoImpresion(av_id , estado)
{
	if (isValidString(av_id) && isValidString(estado)) {
		document.getElementById(av_id).textContent = estado;
	}
}

function abrirURLBioClient(av_url)
{
	HoldOn.open();
	// window.location.href = av_url;
	setTimeout(function()
	{
		connect(av_url);
	}, 5);
}

var connectionP = null

function connectImpresion(av_url)
{
	var trama = av_url;

	if (connectionP == null) {
		console.log('CONP No asignada aún');
	}
	else { // asignada
		console.log("CONP cerrar oconexión previa");
		connectionP.close(3003, "inicio impresion");
		connectionP = null;
	}

	console.log('CONP setup start');
	var connectionP = new WebSocket('ws://localhost:1104/');

	console.log('CONP setup onopen');
	connectionP.onopen = function()
	{
		console.log('CONP open para imprimir');
		connectionP.send(trama);
	}

	console.log('CONP setup onmessage');
	connectionP.onmessage = function(message)
	{
		console.log("CONP mensaje desde bioclient");
		var response = JSON.parse(message.data);
		var impresionCorrecta = false;
		console.log(response);

		if (response.event === 'pdf') {
			if (response.response === 'success') {
				console.log("CONP Pdf OK");
				impresionCorrecta = true;
			}
			else {
				console.log("CONP Pdf fail");
				impresionCorrecta = false;
			}
			connectionP.close(3004, 'Fin operación');
			console.log("CONP Imprimir siguiente");

			setTimeout(function()
			{
				impresionCorrecta ? imprimirDocumento() : failImpresion();
				console.log("CONP Se reportó al servidor el resultado");
			}, 1000);
		}
	}

	console.log('CONP setup onclose');
	connectionP.onclose = function()
	{
		console.log('CONP closed');
	}

	console.log('CONP setup end');
}

function abrirURLBioClientImpresion(av_url)
{
	// window.location.href = av_url;
	console.log("Conectar a Bioclient");
	setTimeout(function()
	{
		connectImpresion(av_url);
	}, 500);
}

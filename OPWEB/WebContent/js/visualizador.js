"use strict";
var lv_reporte;
var lv_tipoReporte;

lv_reporte = undefined;
lv_tipoReporte = undefined;

function openReportSingleBar() {
	google.charts.load("current", {
		packages : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		var data = google.visualization.arrayToDataTable(lv_reporte);

		var view = new google.visualization.DataView(data);

		view.setColumns([ 0, 1, {
			calc : "stringify",
			sourceColumn : 1,
			type : "string",
			role : "annotation"
		}, 2 ]);

		var options = {
			width : '100%',
			height : '500px',
			bar : {
				groupWidth : "80%"
			},
			legend : {
				position : "none"
			},
		};

		chartDraw(view, options);
	}
}
function openReportBars() {
	google.charts.load("current", {
		packages : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = google.visualization.arrayToDataTable(lv_reporte);

		var view = new google.visualization.DataView(data);

		var options = {
			width : '100%',
			height : '500px',
			legend : {
				position : 'top',
				maxLines : 3
			},
			bar : {
				groupWidth : '80%'
			},
			isStacked : true,
		};

		chartDraw(view, options);
	}
}
function openReportPie() {
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {

		var view = google.visualization.arrayToDataTable(lv_reporte);

		var options = {
			is3D : true,
		};

		chartDraw(view, options);
	}
}

function chartDraw(view, options) {
	if (lv_tipoReporte == 1) {
		var chart = new google.visualization.PieChart(document
				.getElementById('reporte'));
	} else {
		var chart = new google.visualization.ColumnChart(document
				.getElementById("reporte"));
	}
	chart.draw(view, options);
}

function cargarReporteArray() {
	let ll_reporte;

	ll_reporte = document.getElementById("fReportes:reporte").value;

	if (isValidString(ll_reporte)) {

		let ll_reporteJson;

		ll_reporteJson = JSON.parse(ll_reporte);

		if (ll_reporteJson) {
			let ll_tipoReporte;

			ll_tipoReporte = parseInt(document
					.getElementById("fReportes:tipoReporte").value);

			if (ll_tipoReporte) {
				lv_reporte = ll_reporteJson.RESULTADOS;
				lv_tipoReporte = ll_tipoReporte;
				switch (ll_tipoReporte) {
				case 2:
					openReportSingleBar();
					break;
				case 3:
					openReportBars();
					break;

				default:
					openReportPie();
					break;
				}
			} else {
				console.log('Tipo reporte invalido revisar el Bean');
			}

		} else {
			console.log('Reporte invalido revisar el Bean');
		}
	} else {
		console.log('Reporte invalido revisar el Bean');
	}
}

cargarReporteArray();
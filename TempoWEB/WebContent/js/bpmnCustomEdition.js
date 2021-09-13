"use strict";
var lv_bpmnModeler;
var lv_elementoDOM;
var lae_etapas;
var lap_procesos;
var lasp_subProcesos;
var lab_monitoreo;
var las_nir;
var las_turno;
var lvp_proceso;
var lvsp_subproceso;

lv_bpmnModeler = new BpmnJS({
  container: "#canvas",
  keyboard: {
    bindTo: window
  }
});
lv_elementoDOM = undefined;
lae_etapas = undefined;
lap_procesos = undefined;
lasp_subProcesos = undefined;
lab_monitoreo = undefined;
las_nir = undefined;
las_turno = undefined;
lvp_proceso = undefined;
lvsp_subproceso = undefined;

function exportDiagram() {
  guardarCambiosProceso();
  try {
    lv_bpmnModeler.saveXML({
      format: true
    },
      function (err, xml) {
        if (err) {
          return console.error("No se pudo guardar el diagrama BPMN 2.0", err);
        }
        procesar([{
          name: "xml",
          value: xml
        }]);
        console.log(xml);
      }
    );
  } catch (le_e) { }
}

function guardarCambiosReglaNegocio(av_reglas) {
  console.log(av_reglas);
  if (isValidString(av_reglas)) {
    if (lv_elementoDOM != null) {
      let ll_datosMotivo;
      ll_datosMotivo = validarDatosMotivoTramite();
      if (ll_datosMotivo != null) {
        let ll_businessObject;
        ll_businessObject = lv_elementoDOM.businessObject;
        if (ll_businessObject != null) {
          let ll_attrs;
          ll_attrs = ll_businessObject.$attrs;
          if (ll_attrs != null) {
            ll_attrs.reglasNegocio = av_reglas;
            PF('dialogReglasNegocio').hide();
          }
        }
      }
    }
  }
}

function accionRegresarReglasNegocio() {
  PF('dialogReglasNegocio').hide();
  PF("SBMotivoTramite").show();
}

function cargarReglasNegocio() {
  cargarReglasNegocioMotivo([{
    name: "reglas",
    value: lv_elementoDOM.businessObject.$attrs.reglasNegocio || ''
  }]);
}

function openDiagram(bpmnXML) {
  lv_bpmnModeler.importXML(bpmnXML, function (err) {
    if (err) {
      abrirDiagrama();
    }
    var lv_canvas = lv_bpmnModeler.get("canvas");
    var lv_overlays = lv_bpmnModeler.get("overlays");

    monitoreo(lv_canvas);
    cargarInfoProceso();
    cargarInfoColors();
    cargarRecargarMonitoreo();
    lv_canvas.zoom("fit-viewport");
    lv_overlays.add("SCAN_OK", "note", {
      position: {
        bottom: 0,
        right: 0
      },
      html: '<div class="diagram-note">Mixed up the labels?</div>'
    });
    lv_canvas.addMarker("SCAN_OK", "needs-discussion");
  });
}

function monitoreo(av_canvas) {
  if (av_canvas != null) {
    let ll_azul;
    let ll_amarillo;
    let ll_verde;
    let ll_rojo;

    ll_azul = lv_bpmnModeler.getDefinitions().rootElements[0].$attrs.azul;
    ll_amarillo = lv_bpmnModeler.getDefinitions().rootElements[0].$attrs.amarillo;
    ll_verde = lv_bpmnModeler.getDefinitions().rootElements[0].$attrs.verde;
    ll_rojo = lv_bpmnModeler.getDefinitions().rootElements[0].$attrs.rojo;
    if (isValidString(ll_azul)) {
      let ll_coleccion;

      ll_coleccion = crearColeccionDeTexto(ll_azul);

      if (isValidCollection(ll_coleccion)) {
        ll_coleccion.forEach(item => {
          if (isValidString(item)) {
            cambiarColorEtapa(av_canvas, item, "azul");
          }
        });
      }
    }
    if (isValidString(ll_amarillo)) {

      let ll_coleccion;

      ll_coleccion = crearColeccionDeTexto(ll_amarillo);

      if (isValidCollection(ll_coleccion)) {
        ll_coleccion.forEach(item => {
          if (isValidString(item)) {
            cambiarColorEtapa(av_canvas, item, "amarillo");
          }
        });
      }
    }
    if (isValidString(ll_verde)) {

      let ll_coleccion;

      ll_coleccion = crearColeccionDeTexto(ll_verde);

      if (isValidCollection(ll_coleccion)) {
        ll_coleccion.forEach(item => {
          if (isValidString(item)) {
            cambiarColorEtapa(av_canvas, item, "verde");
          }
        });
      }
    }
    if (isValidString(ll_rojo)) {

      let ll_coleccion;

      ll_coleccion = crearColeccionDeTexto(ll_rojo);

      if (isValidCollection(ll_coleccion)) {
        ll_coleccion.forEach(item => {
          if (isValidString(item)) {
            cambiarColorEtapa(av_canvas, item, "rojo");
          }
        });
      }
    }
  }
}

function crearColeccionDeTexto(av_texto) {
  if (isValidString(av_texto) && av_texto.includes(",")) {
    return av_texto.split(",");
  }
  else {
    let ll_return;

    ll_return = [av_texto];

    return ll_return;
  }
}

function seleccionarClaseCss(av_color) {
  let ll_return;

  ll_return = undefined;

  if (isValidString(av_color)) {
    switch (av_color) {
      case "azul":
        ll_return = "highlight-overlay-blue";
        break;
      case "amarillo":
        ll_return = "highlight-overlay-yellow";
        break;
      case "verde":
        ll_return = "highlight-overlay-green";
        break;
      case "rojo":
        ll_return = "highlight-overlay-red";
        break;

      default:
        break;
    }
  }

  return ll_return;
}

function cambiarColorEtapa(av_canvas, av_idEtapa, av_color) {
  if (av_canvas != null && isValidString(av_idEtapa) && isValidString(av_color)) {
    var lv_elementRegistry = lv_bpmnModeler.get('elementRegistry');
    var lv_stage = lv_elementRegistry.get(av_idEtapa);
    if (lv_stage != null) {
      av_canvas.addMarker(av_idEtapa, seleccionarClaseCss(av_color));
    }
  }
}

function inicializar() {
  ocultarElementosModeler(
    "entry bpmn-icon-subprocess-expanded",
    "entry bpmn-icon-participant",
    "entry bpmn-icon-group",
    "entry bpmn-icon-data-store",
    "entry bpmn-icon-data-object"
  );
  abrirDiagrama();
}

function cargarXML(xhr, status, args) {
  if (args != null) {
    if (args.validationFailed) {
      PrimeFaces.debug("Error recibiendo los argumentos");
    } else {
      openDiagram(args.xml);
      {
        let ll_etapas;
        ll_etapas = args.etapas;
        if (ll_etapas != null) lae_etapas = JSON.parse(ll_etapas);
      }
      {
        let ll_proceso;
        ll_proceso = args.proceso;
        if (ll_proceso != null) lvp_proceso = JSON.parse(ll_proceso);
      }
      {
        let ll_subprocesoVersion;
        ll_subprocesoVersion = args.subproceso;
        if (ll_subprocesoVersion != null) lvsp_subproceso = JSON.parse(ll_subprocesoVersion);
      }
      {
        let ll_procesos;
        ll_procesos = args.procesos;
        if (ll_procesos != null) lap_procesos = JSON.parse(ll_procesos);
      }
      {
        let ll_subProcesos;
        ll_subProcesos = args.subProcesos;
        if (ll_subProcesos != null) lasp_subProcesos = JSON.parse(ll_subProcesos);
      }
      {
        let ll_monitoreo;
        ll_monitoreo = args.monitoreo;
        if (ll_monitoreo != null) lab_monitoreo = JSON.parse(ll_monitoreo);
      }
      {
        let ll_nir;
        ll_nir = args.nir;
        if (ll_nir != null) las_nir = JSON.parse(ll_nir);
      }
      {
        let ll_turno;
        ll_turno = args.turno;
        if (ll_turno != null) las_turno = JSON.parse(ll_turno);
      }
    }
  }
}

function recargarXML(xhr, status, args) {
	if (args != null) {
		if (args.validationFailed) {
			PrimeFaces.debug("Error recibiendo los argumentos");
		} else {
			openDiagram(args.xml);
		}
	}
}

function cargarVersion(xhr, status, args) {
  if (args != null) {
    if (args.validationFailed) {
      PrimeFaces.debug("Error recibiendo los argumentos");
    } else {
      {
        let ll_subprocesoVersion;
        ll_subprocesoVersion = args.subproceso;
        if (ll_subprocesoVersion != null) lvsp_subproceso = JSON.parse(ll_subprocesoVersion);
      }
    }
  }
}

function validarBotones() {
  if (obtenerValorCheckBox('checkEnviarAlAprobador') == 'S') {
    activarEnvioAprobador();
  }
  else {
    activarGuardar();
  }
}

function accionInformacionProceso() {
  let ll_attributes;
  ll_attributes = lv_bpmnModeler.getDefinitions();
  if (ll_attributes != null) {
    let ll_rootElements;
    ll_rootElements = ll_attributes.rootElements;
    if (ll_rootElements != null) {
      let ll_attrs;
      ll_attrs = ll_rootElements[0].$attrs;
      if (ll_attrs != null) {
        controlNuevoProceso(true, ll_attrs);
        controlNuevoSubProceso(true, ll_attrs);
        PF("SBProceso").show();
        PF('tabProceso').select(0);
      }
    }
  }
}

function accionTabSubproceso() {
  let ll_attributes;
  ll_attributes = lv_bpmnModeler.getDefinitions();
  if (ll_attributes != null) {
    let ll_rootElements;
    ll_rootElements = ll_attributes.rootElements;
    if (ll_rootElements != null) {
      let ll_attrs;
      ll_attrs = ll_rootElements[0].$attrs;
      if (ll_attrs != null) {
        let ll_campoSubProceso;
        ll_campoSubProceso = document.getElementById("tabProceso:idSubprocesoNuevo");
        if (ll_campoSubProceso != null) {
          let ll_valorIdSubproceso;
          ll_valorIdSubproceso = ll_campoSubProceso.value;
          controlNuevoSubProceso(isValidString(ll_attrs.idSubproceso), ll_attrs);
          {
            let ll_subprocesoSom;
            ll_subprocesoSom = PF('subProcesoSOM');
            if (ll_subprocesoSom != null) {
              let ll_valor;
              ll_valor = ll_subprocesoSom.getSelectedValue();
              if (!isValidString(ll_valor) && !isValidString(ll_valorIdSubproceso)) {
                controlNuevoSubProceso(false, null);
              }
            }
          }
        }
      }
    }
  }
}

function ocultarElementosModeler(...av_classesToHide) {
  if (isValidCollection(av_classesToHide)) {
    av_classesToHide.forEach(item => {
      var lv_coleccionHTML;
      lv_coleccionHTML = document.getElementsByClassName(item);
      if (lv_coleccionHTML != null) {
        let lv_div;
        lv_div = lv_coleccionHTML[0];
        if (lv_div) {
          lv_div.style.visibility = "hidden";
          lv_div.style.display = "none";
        }
      }
    });
  } else {
    throw "Es necesario el nombre de la clase a ocultar";
  }
}

function accionHoverTooltip(av_element) {
  if (av_element != null) {
    let ll_tipoElemento;
    ll_tipoElemento = av_element.type;
    if (isValidString(ll_tipoElemento) &&
      (ll_tipoElemento == "bpmn:Task" || ll_tipoElemento == "bpmn:UserTask" || ll_tipoElemento == "bpmn:ReceiveTask" || ll_tipoElemento == "bpmn:ServiceTask")) {
      let ll_businessObject;
      ll_businessObject = av_element.businessObject;
      if (ll_businessObject != null) {
        let ll_attrs;
        ll_attrs = ll_businessObject.$attrs;
        if (ll_attrs != null) {
          let ll_nombre;
          ll_nombre = ll_attrs.nombre;
          if (isValidString(ll_nombre)) return overlib(ll_nombre);
          else return nd();
        }
      }
    }
  }
}

function accionOutTooltip(av_element) {
  if (av_element != null) {
    let ll_tipoElemento;
    ll_tipoElemento = av_element.type;
    if (isValidString(ll_tipoElemento) &&
      (ll_tipoElemento == "bpmn:Task" || ll_tipoElemento == "bpmn:UserTask" || ll_tipoElemento == "bpmn:ReceiveTask" || ll_tipoElemento == "bpmn:ServiceTask")) {
      return nd();
    }
  }
}

function accionInformacion(av_element) {
  if (av_element != null) {
    lv_elementoDOM = av_element; {
      let ll_tipoElemento;
      ll_tipoElemento = lv_elementoDOM.type;
      if (ll_tipoElemento != null) {
        switch (ll_tipoElemento) {
          case "bpmn:Task":
          case "bpmn:UserTask":
          case "bpmn:ReceiveTask":
          case "bpmn:ServiceTask":
            if (lab_monitoreo)
              accionInformacionEtapaMonitoreo();
            else
              accionInformacionEtapa();
            break;
          case "label":
          case "bpmn:SequenceFlow":
            accionInformacionMotivoTramite();
            break;
          default:
            break;
        }
      }
    }
  }
}

function accionInformacionEtapaMonitoreo() {
  PF("SBEtapaMonitoreo").show();
  document.getElementById("idUsuario").value = lv_elementoDOM.businessObject.$attrs.idUsuario || "";
  document.getElementById("fechaCreacion").value = lv_elementoDOM.businessObject.$attrs.fechaCreacion || "";
  document.getElementById("fechaReparto").value = lv_elementoDOM.businessObject.$attrs.fechaReparto || "";
  document.getElementById("fechaInicial").value = lv_elementoDOM.businessObject.$attrs.fechaInicial || "";
  document.getElementById("fechaFinal").value = lv_elementoDOM.businessObject.$attrs.fechaFinal || "";
  document.getElementById("motivoTramite").value = lv_elementoDOM.businessObject.$attrs.motivoTramite || "";
  document.getElementById("indicadorDevolucion").value = lv_elementoDOM.businessObject.$attrs.indicadorDevolucion || "";
  document.getElementById("estadoActividadEtapa").value = lv_elementoDOM.businessObject.$attrs.estadoActividadEtapa || "";
}

function accionObservaciones() {
  PF('dialogObservaciones').show();
  document.getElementById("fDialog:observaciones").innerHTML = lv_elementoDOM.businessObject.$attrs.observaciones || "";
  PF("SBEtapaMonitoreo").hide();
}

function accionReglasNegocio() {
  PF('dialogReglasNegocio').show();
  PF("SBMotivoTramite").hide();
}

function accionInformacionEtapa() {
  let ll_etapa;
  ll_etapa = lv_elementoDOM.businessObject.$attrs.etapa || "";
  if (!isValidString(ll_etapa)) {
    limpiarCamposEtapa();
    PF("SBEtapa").show();
    mostrarElementos("divLabelEtapa", "labelEtapa", "divEtapa", "etapaNueva");
    document.getElementById("etapa").title = "Nueva etapa";
    return;
  } else {
    let ll_domSOM;
    ll_domSOM = PF("etapaSOM");
    PF("SBEtapa").show(); {
      let lle_etapaDatos;
      lle_etapaDatos = encontrarEtapa(ll_etapa);
      if (lle_etapaDatos != null) {
        ll_domSOM.selectValue(ll_etapa);
        ocultarElementos(
          "divLabelEtapa",
          "labelEtapa",
          "divEtapa",
          "etapaNueva"
        );
        llenarInformacionEtapa(lv_elementoDOM);
        document.getElementById("etapa").title = ll_domSOM.getSelectedLabel();
        return;
      } else {
        let ll_label;
        ll_label =
          (lv_elementoDOM.businessObject.$attrs.etapa || "") +
          " - " +
          (lv_elementoDOM.businessObject.$attrs.nombre || ""); {
          let ll_dom;
          ll_dom = document.getElementById("etapa");
          if (ll_dom) {
            ll_dom.title = ll_label;
            ll_domSOM.selectValue("");
            ll_domSOM.setLabel(ll_label);
            document.getElementById("etapaNueva").value =
              lv_elementoDOM.businessObject.$attrs.etapa || "";
            mostrarElementos(
              "divLabelEtapa",
              "labelEtapa",
              "divEtapa",
              "etapaNueva"
            );
            llenarInformacionEtapa(lv_elementoDOM);
            return;
          }
        }
      }
    }
  }
}

function encontrarEtapa(av_etapa) {
  let lle_etapaDatos;
  lle_etapaDatos = null;
  if (isValidString(av_etapa)) {
    lle_etapaDatos = lae_etapas.find(
      lle_etapa => lle_etapa.il_idEtapa == av_etapa
    );
  }
  return lle_etapaDatos;
}

function encontrarProceso(av_proceso) {
  let llp_procesoDatos;
  llp_procesoDatos = null;
  if (isValidString(av_proceso)) {
    llp_procesoDatos = lap_procesos.find(
      llp_proceso => llp_proceso.is_id == av_proceso
    );
  }
  return llp_procesoDatos;
}
function encontrarSubProceso(av_subProceso) {
  let llsp_subProcesoDatos;
  llsp_subProcesoDatos = null;
  if (isValidString(av_subProceso)) {
    let ll_domSOM;
    ll_domSOM = PF('procesoSOM');
    if (ll_domSOM != null) {
      llsp_subProcesoDatos = lasp_subProcesos.find(
        llsp_subProceso => llsp_subProceso.is_idSubproceso == av_subProceso && llsp_subProceso.is_idProceso == ll_domSOM.getSelectedValue()
      );
    }
  }
  return llsp_subProcesoDatos;
}

function llenarInformacionEtapa(av_elementoDOM, av_etapaValida = false) {
  if (av_elementoDOM != null) {
    document.getElementById("nombreEtapa").value = av_etapaValida ? av_elementoDOM.is_nombre || "" :
      av_elementoDOM.businessObject.$attrs.nombre || "";
    document.getElementById("nombreEtapa").title = av_etapaValida ? av_elementoDOM.is_nombre || "" :
      document.getElementById(
        "nombreEtapa"
      ).value;
    document.getElementById("descripcionEtapa").value = av_etapaValida ? av_elementoDOM.is_descripcion || "" :
      av_elementoDOM.businessObject.$attrs.descripcion || "";
    document.getElementById("descripcionEtapa").title = av_etapaValida ? av_elementoDOM.is_descripcion || "" :
      document.getElementById(
        "descripcionEtapa"
      ).value;
    {
      let ll_dom;
      ll_dom = PF("estadoEtapa");
      if (ll_dom != null) {
        ll_dom.selectValue(av_etapaValida ? av_elementoDOM.is_activo || "" : av_elementoDOM.businessObject.$attrs.estadoEtapa);
      }
    } {
      let ll_dom;
      ll_dom = PF("estadoActividad");
      if (ll_dom != null) {
        ll_dom.selectValue(!av_etapaValida ? av_elementoDOM.businessObject.$attrs.estadoActividad || "" : "");
      }
    }
    setValorCheckBox(av_etapaValida ? av_elementoDOM.is_indicadorPeso || "" :
      av_elementoDOM.businessObject.$attrs.indPeso,
      "indPesoEtapa"
    );
    setValorCheckBox(av_etapaValida ? av_elementoDOM.is_indicadorBloqueo || "" :
      av_elementoDOM.businessObject.$attrs.indBloqueo,
      "indBloqueoEtapa"
    );
    setValorCheckBox(av_etapaValida ? av_elementoDOM.is_indicadorDesborde || "" :
      av_elementoDOM.businessObject.$attrs.indDesborde,
      "indDesbordeEtapa"
    );
    setValorCheckBox(av_etapaValida ? av_elementoDOM.is_indicadorTope || "" :
      av_elementoDOM.businessObject.$attrs.indTope,
      "indTopeEtapa"
    );
    {
      let ll_dom;
      ll_dom = PF("unidadTiempoEsperaEtapa");
      if (ll_dom != null) {
        ll_dom.selectValue(av_etapaValida ? av_elementoDOM.is_idUnidadTiempoEspera || "" : av_elementoDOM.businessObject.$attrs.unidadTiempoEspera);
      }
    }{
      let ll_dom;
      ll_dom = PF("TipoRepartoSOM");
      if (ll_dom != null) {
        ll_dom.selectValue(av_etapaValida ? av_elementoDOM.is_tipoReparto || "" : av_elementoDOM.businessObject.$attrs.tipoReparto);
      }
    }{
      let ll_dom;
      ll_dom = PF("LineaProduccionSOM");
      if (ll_dom != null) {
        ll_dom.selectValue(av_etapaValida ? av_elementoDOM.il_idLineaProduccion || "" : av_elementoDOM.businessObject.$attrs.lineaProduccionEtapa);
      }
    }
    document.getElementById("pesoEtapa").value = av_etapaValida ? av_elementoDOM.ibd_peso || "" :
    av_elementoDOM.businessObject.$attrs.pesoEtapa || "";
    document.getElementById("porcentajeAVencerEtapa").value = av_etapaValida ? av_elementoDOM.ibd_porcentajeAVencerEtapa || "" :
    	av_elementoDOM.businessObject.$attrs.porcentajeAVencerEtapa || "";
  }
}

function controlNuevaEtapa() {
  let ll_dom;
  ll_dom = PF("etapaSOM");
  if (ll_dom != null) {
    let ll_valor;
    ll_valor = ll_dom.getSelectedValue();
    if (!isValidString(ll_valor)) {
      mostrarElementos("divLabelEtapa", "labelEtapa", "divEtapa", "etapaNueva");
      limpiarCamposEtapa();
    } else {
      ocultarElementos("divLabelEtapa", "labelEtapa", "divEtapa", "etapaNueva"); {
        let ll_etapa;
        ll_etapa = encontrarEtapa(ll_valor);
        if (ll_etapa != null)
          llenarInformacionEtapa(ll_etapa, true);
      }
    }
  }
}

function controlNuevoProceso(av_accionInformacion = false, av_proceso = undefined) {
  let ll_dom;
  ll_dom = PF("procesoSOM");
  if (ll_dom != null) {
    let ll_valor;
    ll_valor = ll_dom.getSelectedValue();
    if (!isValidString(ll_valor)) {
      mostrarElementos("divLabelProceso", "labelProceso", "divProceso", "procesoNuevo");
      llenarDatosProceso(av_accionInformacion ? retornarProceso(av_proceso) : null, av_accionInformacion ? (encontrarProceso(av_proceso.idProceso) == null) : false);
    }
    else {
      let ll_procesoValido;
      let ll_proceso;
      ll_procesoValido = av_proceso != null;
      ll_proceso = ll_procesoValido ? av_proceso : null;
      ocultarElementos("divLabelProceso", "labelProceso", "divProceso", "procesoNuevo");
      if (ll_proceso == null) {
        ll_proceso = encontrarProceso(ll_valor);
      }
      if (ll_proceso != null)
        llenarDatosProceso(ll_procesoValido ? retornarProceso(av_proceso) : ll_proceso, false);
    }
  }
}
function controlNuevoSubProceso(av_accionInformacion = false, av_subProceso = undefined) {
  let ll_dom;
  ll_dom = PF("subProcesoSOM");
  if (ll_dom != null) {
    let ll_valor;
    ll_valor = ll_dom.getSelectedValue();
    if (!isValidString(ll_valor)) {
      mostrarElementos('subprocesoDiv', 'subprocesoLabel', 'idSubprocesoNuevo', 'subprocesoNuevoDiv');
      llenarDatosSubProceso(av_accionInformacion ? retornarSubProceso(av_subProceso) : null, av_accionInformacion ? (encontrarSubProceso(av_subProceso.idSubproceso) == null) : false);
    } else {
      ocultarElementos('subprocesoDiv', 'subprocesoLabel', 'idSubprocesoNuevo', 'subprocesoNuevoDiv');
      {
        let ll_subProcesoValido;
        let ll_subProceso;
        ll_subProcesoValido = av_subProceso != null;
        ll_subProceso = ll_subProcesoValido ? av_subProceso : null;
        if (ll_subProceso == null) {
          ll_subProceso = encontrarSubProceso(ll_valor);
        }
        if (ll_subProceso != null)
          llenarDatosSubProceso(ll_subProcesoValido ? retornarSubProceso(av_subProceso) : ll_subProceso, false);
      }
    }
  }
}

function retornarProceso(av_proceso) {
  var lv_objetoRetorno;

  lv_objetoRetorno = {};

  if (av_proceso != null) {
    lv_objetoRetorno.is_id = av_proceso.idProceso || "";
    lv_objetoRetorno.is_nombre = av_proceso.nombreProceso || "";
    lv_objetoRetorno.is_activo = av_proceso.activoProceso || "";
  }
  return lv_objetoRetorno;
}

function zoom(alejar = false) {
  var lv_canvas;
  lv_canvas = lv_bpmnModeler.get('canvas');
  if (lv_canvas != null) {
    let ll_zoom;
    ll_zoom = lv_canvas.zoom();
    if (alejar) {
      ll_zoom = ll_zoom - 0.1
      if (ll_zoom > 0)
        lv_canvas.zoom(ll_zoom);
    }
    else
      lv_canvas.zoom(ll_zoom + 0.1);
  }
}

function retornarSubProceso(av_subProceso) {
  var lv_objetoRetorno;

  lv_objetoRetorno = {};

  if (av_subProceso != null) {
    lv_objetoRetorno.is_idSubproceso = av_subProceso.idSubproceso || "";
    lv_objetoRetorno.is_nombre = av_subProceso.nombreSubproceso || "";
    lv_objetoRetorno.is_activo = av_subProceso.activoSubProceso || "";
  }
  return lv_objetoRetorno;
}

function mostrarLineaProduccion(){
  let ll_dom;

  ll_dom = PF('TipoRepartoSOM');

  if(ll_dom){
      if(ll_dom.getSelectedValue() == 'L')
        mostrarElementos('outputTextLineaProduccion','lineaProduccionEtapa');
      else
        ocultarElementos('outputTextLineaProduccion','lineaProduccionEtapa');
  }
}

function limpiarCamposEtapa() {
  {
    let ll_dom;
    ll_dom = PF("etapaSOM");
    if (ll_dom != null) {
      ll_dom.selectValue("");
      ll_dom.setLabel("Nueva");
    }
  }
  document.getElementById("etapaNueva").value = "";
  document.getElementById("nombreEtapa").value = "";
  document.getElementById("descripcionEtapa").value = ""; {
    let ll_dom;
    ll_dom = PF("estadoEtapa");
    if (ll_dom != null) {
      ll_dom.selectValue("S");
    }
  } {
    let ll_dom;
    ll_dom = PF("estadoActividad");
    if (ll_dom != null) {
      ll_dom.selectValue("");
    }
  }
  setValorCheckBox(false, "indPesoEtapa");
  setValorCheckBox(false, "indBloqueoEtapa");
  setValorCheckBox(false, "indDesbordeEtapa");
  setValorCheckBox(false, "indTopeEtapa");
  document.getElementById("cantidadTiempoEsperaEtapa").value = "";
  document.getElementById("unidadTiempoEsperaEtapa").value = "";
  {
    let ll_dom;
    ll_dom = PF("LineaProduccionSOM");
    if (ll_dom != null) {
      ll_dom.selectValue("");
    }
  }
  {
    let ll_dom;
    ll_dom = PF("TipoRepartoSOM");
    if (ll_dom != null) {
      ll_dom.selectValue("");
    }
  }
  document.getElementById("pesoEtapa").value = "";
}

function llenarDatosProceso(av_proceso, av_procesoNuevo) {
  let ll_attributes;
  ll_attributes = lv_bpmnModeler.getDefinitions();
  if (ll_attributes != null) {
    let ll_rootElements;
    ll_rootElements = ll_attributes.rootElements;
    if (ll_rootElements != null) {
      let ll_attrs;
      ll_attrs = ll_rootElements[0].$attrs;
      if (ll_attrs != null) {
        let ll_proceso;
        let ll_procesoSOM;
        let ll_activoSOM;
        let ll_nombreProceso;

        ll_proceso = document.getElementById("tabProceso:procesoNuevo");
        ll_procesoSOM = PF('procesoSOM');
        ll_activoSOM = PF('activoProceso');
        ll_nombreProceso = document.getElementById("tabProceso:nombreProceso");

        if (av_proceso != null) {
          ll_proceso.value = av_proceso.is_id;
          ll_nombreProceso.value = av_proceso.is_nombre;
          ll_procesoSOM.selectValue(av_procesoNuevo ? "" : av_proceso.is_id);
          ll_activoSOM.selectValue(av_procesoNuevo ? "N" : av_proceso.is_activo);
        }
        else {
          ll_proceso.value = "";
          ll_nombreProceso.value = "";
          ll_procesoSOM.selectValue("");
          ll_activoSOM.selectValue("N");
        }
      }
    }
  }
}

function llenarDatosSubProceso(av_subProceso, av_subProcesoNuevo) {
  let ll_attributes;
  ll_attributes = lv_bpmnModeler.getDefinitions();
  if (ll_attributes != null) {
    let ll_rootElements;
    ll_rootElements = ll_attributes.rootElements;
    if (ll_rootElements != null) {
      let ll_attrs;
      ll_attrs = ll_rootElements[0].$attrs;
      if (ll_attrs != null) {
        let ll_subProceso;
        let ll_subProcesoSOM;
        let ll_nombreSubProceso;
        let ll_activoSOM;

        ll_subProceso = document.getElementById("tabProceso:idSubprocesoNuevo");
        ll_subProcesoSOM = PF('subProcesoSOM');
        ll_nombreSubProceso = document.getElementById("tabProceso:nombreSubproceso");
        ll_activoSOM = PF('activoSubProceso');

        if (av_subProceso != null) {
          ll_subProceso.value = av_subProceso.is_idSubproceso || "";
          ll_nombreSubProceso.value = av_subProceso.is_nombre || "";

          ll_subProcesoSOM.selectValue(av_subProcesoNuevo ? "" : av_subProceso.is_idSubproceso);
          ll_activoSOM.selectValue(av_subProcesoNuevo ? "N" : av_subProceso.is_activo);
        }
        else {
          ll_subProceso.value = "";
          ll_nombreSubProceso.value = "";
          ll_subProcesoSOM.selectValue("");
          ll_activoSOM.selectValue("N");
        }
      }
    }
  }
}

function accionInformacionMotivoTramite() {
  setValorCheckBox(
    lv_elementoDOM.businessObject.$attrs.indDevolucion,
    "indDevolucionMotivo"
  );
  document.getElementById("motivo").value =
    lv_elementoDOM.businessObject.$attrs.motivo || "";
  document.getElementById("descripcionMotivo").value =
    lv_elementoDOM.businessObject.$attrs.descripcion || "";
  PF("SBMotivoTramite").show();
}

function setValorCheckBox(av_valor, av_widgetVar) {
  let ll_dom;
  ll_dom = PF(av_widgetVar);
  if (typeof av_valor == "boolean") {
    if (av_valor == true) {
      if (!ll_dom.isChecked()) ll_dom.toggle();
    } else {
      ll_dom.uncheck();
    }
  } else if (isValidString(av_valor)) {
    if (isValidString(av_widgetVar)) {
      if (ll_dom != null) {
        if (av_valor === "S") {
          if (!ll_dom.isChecked()) ll_dom.toggle();
        } else {
          ll_dom.uncheck();
        }
      }
    }
  } else if (isValidString(av_widgetVar)) {
    let ll_dom;
    ll_dom = PF(av_widgetVar);
    if (ll_dom != null) {
      ll_dom.uncheck();
    }
  }
}

function validarDatosEtapa() {
  var lv_objetoRetorno;
  lv_objetoRetorno = undefined; 
  {
    let ll_domSOM;
    ll_domSOM = PF("etapaSOM");
    if (ll_domSOM != null) {
      let ll_valor;
      ll_valor = ll_domSOM.getSelectedValue();
      if (!isValidString(ll_valor)) {
        let ll_dom;
        let ll_valor;
        ll_dom = document.getElementById("etapaNueva");
        ll_valor = ll_dom.value;
        if (!isValidString(ll_valor) || isNaN(ll_valor)) {
          ll_dom.style.borderColor = "red";
          addMessage(
            "globalMsg",
            "Advertencia: ",
            "Debe ingresar una etapa válida",
            "warn"
          );
          throw "La etapa no es válida.";
        } else {
          lv_objetoRetorno = {};
          lv_objetoRetorno.etapa = ll_valor.trim();
          ll_dom.style.borderColor = "#799DB0"; {
            let ll_label;
            ll_label =
              lv_objetoRetorno.etapa +
              (document.getElementById("nombreEtapa") ?
                " - " + document.getElementById("nombreEtapa").value :
                ""); {
              let ll_dom;
              ll_dom = document.getElementById("etapa");
              if (ll_dom) {
                ll_dom.title = ll_label;
                ll_domSOM.setLabel(ll_label);
              }
            }
          }
        }
      } else {
        let ll_dom;
        ll_dom = document.getElementById("etapa");
        lv_objetoRetorno = {};
        lv_objetoRetorno.etapa = ll_valor.trim();
        ll_dom.style.borderColor = "#799DB0";
        ll_dom.title = ll_domSOM.getSelectedLabel();
      }
    }
  } {
    let ll_dom;
    ll_dom = document.getElementById("nombreEtapa");
    if (ll_dom != null) {
      let ll_valor;
      ll_valor = ll_dom.value;
      if (!isValidString(ll_valor)) {
        ll_dom.style.borderColor = "red";
        addMessage(
          "globalMsg",
          "Advertencia: ",
          "Debe ingresar un nombre de etapa válido",
          "warn"
        );
        throw "El nombre de etapa no es válido";
      } else {
        lv_objetoRetorno.nombreEtapa = ll_valor;
        ll_dom.style.borderColor = "#799DB0";
        ll_dom.title = ll_valor;
      }
    }
  }
  {
    let ll_dom;
    ll_dom = document.getElementById("tipoRepartoEtapa");
    if (ll_dom != null) {
      let ll_domSOM;
      let ll_valor;
      ll_domSOM = PF("TipoRepartoSOM");
      if (ll_domSOM != null) {
        ll_valor = ll_domSOM.getSelectedValue();
        if (!isValidString(ll_valor)) {
          ll_dom.style.borderColor = "red";
          addMessage(
            "globalMsg",
            "Advertencia: ",
            "Debe ingresar un tipo reparto válido",
            "warn"
          );
          throw "El tipo reparto no es válido.";
        } else {
          ll_valor = ll_valor.trim();
          lv_objetoRetorno.tipoRepartoEtapa = ll_valor;
          ll_dom.style.borderColor = "#799DB0";
          if(ll_valor == 'L'){
              let ll_dom;
              ll_dom = document.getElementById("lineaProduccion");
              if (ll_dom != null) {
                let ll_domSOM;
                let ll_valor;
                ll_domSOM = PF("LineaProduccionSOM");
                if (ll_domSOM != null) {
                  ll_valor = ll_domSOM.getSelectedValue();
                  if (!isValidString(ll_valor)) {
                    ll_dom.style.borderColor = "red";
                    addMessage(
                      "globalMsg",
                      "Advertencia: ",
                      "Debe ingresar una linea producción válida",
                      "warn"
                    );
                    throw "La linea producción no es válida.";
                  } else {
                    lv_objetoRetorno.lineaProduccionEtapa = ll_valor.trim();
                    ll_dom.style.borderColor = "#799DB0";
                  }
                }
              }
          }
        }
      }
    }
  }
  {
  let ll_dom;
  ll_dom = document.getElementById("pesoEtapa");
  if (ll_dom != null) {
    let ll_valor;
    ll_valor = ll_dom.value;
    if (!isValidString(ll_valor)) {
      ll_dom.style.borderColor = "red";
      addMessage(
        "globalMsg",
        "Advertencia: ",
        "Debe ingresar un peso de etapa válido",
        "warn"
      );
      throw "El nombre de peso no es válido";
    } else {
      lv_objetoRetorno.pesoEtapa = ll_valor;
      ll_dom.style.borderColor = "#799DB0";
      ll_dom.title = ll_valor;
    }
  }
}
  {
    let ll_dom;
    ll_dom = document.getElementById("descripcionEtapa");
    if (ll_dom != null) {
      lv_objetoRetorno.descripcionEtapa = ll_dom.value;
      ll_dom.title = ll_dom.value;
    }
  } 
  {
    let ll_dom;
    ll_dom = document.getElementById("estadoEtapa");
    if (ll_dom != null) {
      let ll_domSOM;
      let ll_valor;
      ll_domSOM = PF("estadoEtapa");
      if (ll_domSOM != null) {
        ll_valor = ll_domSOM.getSelectedValue();
        if (!isValidString(ll_valor)) {
          ll_dom.style.borderColor = "red";
          addMessage(
            "globalMsg",
            "Advertencia: ",
            "Debe ingresar un estado válido",
            "warn"
          );
          throw "El estado no es válido.";
        } else {
          lv_objetoRetorno.estadoEtapa = ll_valor.trim();
          ll_dom.style.borderColor = "#799DB0";
        }
      }
    }
  } 
  {
    let ll_dom;
    ll_dom = document.getElementById("estadoActividad");
    if (ll_dom != null) {
      let ll_domSOM;
      let ll_valor;
      ll_domSOM = PF("estadoActividad");
      if (ll_domSOM != null) {
        ll_valor = ll_domSOM.getSelectedValue();
        if (!isValidString(ll_valor)) {
          ll_dom.style.borderColor = "red";
          addMessage(
            "globalMsg",
            "Advertencia: ",
            "Debe ingresar un estado actividad válido",
            "warn"
          );
          throw "El estado actividad no es válido.";
        } else {
          lv_objetoRetorno.estadoActividad = ll_valor.trim();
          ll_dom.style.borderColor = "#799DB0";
        }
      }
    }
  } {
    let ll_valor;
    ll_valor = obtenerValorCheckBox("indPesoEtapa");
    if (isValidString(ll_valor)) lv_objetoRetorno.indPesoEtapa = ll_valor;
  } {
    let ll_valor;
    ll_valor = obtenerValorCheckBox("indBloqueoEtapa");
    if (isValidString(ll_valor)) lv_objetoRetorno.indBloqueoEtapa = ll_valor;
  } {
    let ll_valor;
    ll_valor = obtenerValorCheckBox("indDesbordeEtapa");
    if (isValidString(ll_valor)) lv_objetoRetorno.indDesbordeEtapa = ll_valor;
  } {
	  
    let ll_valor;
    ll_valor = obtenerValorCheckBox("indTopeEtapa");
    if (isValidString(ll_valor)) {
      lv_objetoRetorno.indTopeEtapa = ll_valor;
    }
  }
    {
        let ll_dom;
        ll_dom = document.getElementById("porcentajeAVencerEtapa");
        if (ll_dom != null) {
          lv_objetoRetorno.porcentajeAVencerEtapa = ll_dom.value;
          ll_dom.title = ll_dom.value;
        }
      }
 {
    let ll_dom;
    ll_dom = document.getElementById("cantidadTiempoEsperaEtapa");
    if (ll_dom != null) {
      let ll_valor;
      ll_valor = ll_dom.value;
      if (isValidString(ll_valor)) {
        if (isNaN(ll_valor)) {
          ll_dom.style.borderColor = "red";
          addMessage(
            "globalMsg",
            "Advertencia: ",
            "Debe ingresar un tiempo de espera válido",
            "warn"
          );
          throw "El tiempo de espera no es válido.";
        }
      }
      ll_dom.style.borderColor = "#799DB0";
      lv_objetoRetorno.cantidadTiempoEsperaEtapa = ll_valor;
    }
  }
  {
    let ll_dom;
    ll_dom = document.getElementById("unidadTiempoEsperaEtapa");
    if (ll_dom != null) {
      let ll_domSOM;
      let ll_valor;
      ll_domSOM = PF("unidadTiempoEsperaEtapa");
      if (ll_domSOM != null) {
        ll_valor = ll_domSOM.getSelectedValue();
        lv_objetoRetorno.unidadTiempoEsperaEtapa = ll_valor.trim();
        ll_dom.style.borderColor = "#799DB0";
      }
    }
  }
  return lv_objetoRetorno;
}

function obtenerValorCheckBox(av_widgetVar) {
  var lv_objetoRetorno;
  lv_objetoRetorno = undefined;
  if (isValidString(av_widgetVar)) {
    let ll_dom;
    ll_dom = PF(av_widgetVar);
    if (ll_dom != null) lv_objetoRetorno = ll_dom.isChecked() ? "S" : "N";
  }
  return lv_objetoRetorno;
}

function validarDatosMotivoTramite() {
  var lv_objetoRetorno;
  lv_objetoRetorno = undefined; {
    let ll_dom;
    ll_dom = document.getElementById("motivo");
    if (ll_dom != null) {
      let ll_valor;
      ll_valor = ll_dom.value;
      if (!isValidString(ll_valor) || isNaN(ll_valor)) {
        ll_dom.style.borderColor = "red";
        addMessage(
          "globalMsg",
          "Advertencia: ",
          "Debe ingresar un motivo válido",
          "warn"
        );
        throw "Motivo no válido.";
      } else {
        lv_objetoRetorno = {};
        		let ll_validador;
        		ll_validador = parseInt(ll_valor.trim());
        		if(ll_validador >= 0 && ll_validador < 100){
        			ll_dom.style.borderColor = "red";
        	        addMessage(
        	          "globalMsg",
        	          "Advertencia: ",
        	          "El motivo que intenta ingresar es invalido, por favor ingresar un motivo mayor a 100",
        	          "warn"
        	        );
        	        throw "Motivo no válido.";
        		}
        lv_objetoRetorno.motivo = ll_valor.trim();
        ll_dom.style.borderColor = "#799DB0";
      }
    }
  } {
    let ll_valor;
    ll_valor = obtenerValorCheckBox("indDevolucionMotivo");
    if (isValidString(ll_valor)) lv_objetoRetorno.indDevolucion = ll_valor;
  }{
    let ll_dom;
    ll_dom = document.getElementById("descripcionMotivo");
    if (ll_dom != null) {
      let ll_valor;
      ll_valor = ll_dom.value;
      if (!isValidString(ll_valor)) {
        addMessage(
          "globalMsg",
          "Advertencia: ",
          "Debe ingresar una descripción válida.",
          "warn"
        );
        ll_dom.style.borderColor = "red";
        throw "La descripción no es válida";
      } else {
        lv_objetoRetorno.descripcion = ll_valor;
        ll_dom.style.borderColor = "#799DB0";
      }
    }
  } {
    let ll_dom;
    ll_dom = document.getElementById("estadoMotivo");
    if (ll_dom != null) {
      let ll_domSOM;
      let ll_valor;
      ll_domSOM = PF("estadoMotivo");
      if (ll_domSOM != null) {
        ll_valor = ll_domSOM.getSelectedValue();
        if (!isValidString(ll_valor)) {
          ll_dom.style.borderColor = "red";
          addMessage(
            "globalMsg",
            "Advertencia: ",
            "Debe ingresar un estado válido",
            "warn"
          );
          throw "El estado no es válido.";
        } else {
          lv_objetoRetorno.estado = ll_valor.trim();
          ll_dom.style.borderColor = "#799DB0";
        }
      }
    }
  }
  return lv_objetoRetorno;
}

function guardarCambiosEtapa() {
  if (lv_elementoDOM != null) {
    let ll_datosEtapa;
    ll_datosEtapa = validarDatosEtapa();
    if (ll_datosEtapa != null) {
      let ll_businessObject;
      ll_businessObject = lv_elementoDOM.businessObject;
      if (ll_businessObject != null) {
        let ll_attrs;
        ll_attrs = ll_businessObject.$attrs;
        if (ll_attrs != null) {
          let ll_modeling;
          let ll_estadoActividad;
          ll_modeling = lv_bpmnModeler.get("modeling");
          ll_estadoActividad = ll_datosEtapa.estadoActividad;
          ll_attrs.etapa = ll_datosEtapa.etapa;
          ll_businessObject.name = ll_businessObject.$attrs.etapa.concat(" ", ll_estadoActividad);
          ll_attrs.nombre = ll_datosEtapa.nombreEtapa;
          ll_attrs.descripcion = ll_datosEtapa.descripcionEtapa;
          ll_attrs.estadoEtapa = ll_datosEtapa.estadoEtapa;
          ll_attrs.estadoActividad = ll_datosEtapa.estadoActividad;
          ll_attrs.indPeso = ll_datosEtapa.indPesoEtapa;
          ll_attrs.indBloqueo = ll_datosEtapa.indBloqueoEtapa;
          ll_attrs.indDesborde = ll_datosEtapa.indDesbordeEtapa;
          ll_attrs.indTope = ll_datosEtapa.indTopeEtapa;
          ll_attrs.tipoReparto = ll_datosEtapa.tipoRepartoEtapa;
          ll_attrs.pesoEtapa = ll_datosEtapa.pesoEtapa;
          ll_attrs.lineaProduccionEtapa = ll_datosEtapa.lineaProduccionEtapa ? ll_datosEtapa.lineaProduccionEtapa :"";
          ll_attrs.cantidadTiempoEspera =
            ll_datosEtapa.cantidadTiempoEsperaEtapa;
          ll_attrs.porcentajeAVencerEtapa =
        	  ll_datosEtapa.porcentajeAVencerEtapa;
          ll_attrs.unidadTiempoEspera = ll_datosEtapa.unidadTiempoEsperaEtapa;
          ll_modeling.updateLabel(
            lv_elementoDOM,
            ll_businessObject.name,
            null,
            null		
          );
          bloquearAprobador();
          PF("SBEtapa").hide();
          addMessage(
            "globalMsg",
            "Información: ",
            "Etapa guardada correctamente.",
            "info"
          );
        }
      }
    }
  }
}

function guardadoExitoso() {
  PF('dialogGuardar').hide();
}

function activarGuardar() {
  ocultarElementos('fPrincipal\\:botonEnviarAprobador');
  mostrarElementos('fPrincipal\\:save-button');
}

function activarEnvioAprobador() {
  mostrarElementos('fPrincipal\\:botonEnviarAprobador');
  ocultarElementos('fPrincipal\\:save-button');
}

function guardarCambiosMotivoTramite() {
  if (lv_elementoDOM != null) {
    let ll_datosMotivo;
    ll_datosMotivo = validarDatosMotivoTramite();
    if (ll_datosMotivo != null) {
      let ll_businessObject;
      ll_businessObject = lv_elementoDOM.businessObject;
      if (ll_businessObject != null) {
        let ll_attrs;
        ll_attrs = ll_businessObject.$attrs;
        if (ll_attrs != null) {
          let ll_modeling;
          ll_modeling = lv_bpmnModeler.get("modeling");
          ll_attrs.motivo = ll_datosMotivo.motivo;
          ll_businessObject.name = ll_attrs.motivo;
          ll_attrs.indDevolucion = ll_datosMotivo.indDevolucion;
          ll_attrs.descripcion = ll_datosMotivo.descripcion;
          ll_attrs.estado = ll_datosMotivo.estado;
          ll_modeling.updateLabel(
            lv_elementoDOM,
            ll_businessObject.name,
            null,
            null
          );
          bloquearAprobador();
          PF("SBMotivoTramite").hide();
          addMessage(
            "globalMsg",
            "Información: ",
            "Motivo trámite guardado correctamente.",
            "info"
          );
        }
      }
    }
  }
}

function cambiarProceso(av_element) {
  if (av_element != null) {
    let ll_attributes;
    ll_attributes = lv_bpmnModeler.getDefinitions();
    if (ll_attributes != null) {
      let ll_rootElements;
      ll_rootElements = ll_attributes.rootElements;
      if (ll_rootElements != null) {
        let ll_attrs;
        ll_attrs = ll_rootElements[0].$attrs;
        if (ll_attrs != null) {
          let ll_valor;
          ll_valor = ll_attrs.idProceso;
          if (!procesoValido(ll_valor)) {
            addMessage(
              "globalMsg",
              "Advertencia: ",
              "Debe ingresar un proceso válido.",
              "warn"
            );
            PF("dialogGuardar").hide();
            PF("SBProceso").show();
            throw "El proceso no es válido.";
          }
        }
      }
    }
  }
}

function procesoValido(av_proceso) {
  return isValidString(av_proceso) && !isNaN(av_proceso);
}

function guardarCambiosProceso() {
  let ll_proceso;
  ll_proceso = document.getElementById("tabProceso:procesoNuevo").value.trim();
  if (procesoValido(ll_proceso)) {
    let ll_attributes;
    ll_attributes = lv_bpmnModeler.getDefinitions();
    document.getElementById("tabProceso:procesoNuevo").style.borderColor = "#799DB0";
    if (ll_attributes != null) {
      let ll_rootElements;
      ll_rootElements = ll_attributes.rootElements;
      if (ll_rootElements != null) {
        let ll_attrs;
        ll_attrs = ll_rootElements[0].$attrs;
        if (ll_attrs != null) {
          {
            let ll_nombre;
            ll_nombre = document.getElementById("tabProceso:nombreProceso")
              .value;
            if (isValidString(ll_nombre)) {
              ll_attrs.nombreProceso = ll_nombre;
              document.getElementById("tabProceso:nombreProceso").style.borderColor = "#799DB0";
            } else {
              document.getElementById("tabProceso:nombreProceso").style.borderColor = 'red';
              PF('tabProceso').select(0);
              accionInformacionProceso();
              PF("dialogGuardar").hide();
              addMessage(
                "globalMsg",
                "Advertencia: ",
                "Debe ingresar un nombre de proceso válido.",
                "warn"
              );
              throw "El nombre de proceso no es válido.";
            }
          }
          {
        	  let ll_activo;
        	  ll_activo = PF('activoProceso').getSelectedValue();
        	  if (isValidString(ll_activo)) {
        		  ll_attrs.activoProceso = ll_activo;
        		  document.getElementById("tabProceso:activoProceso").style.borderColor = "#799DB0";
        	  }
        	  else
        	  {
                  document.getElementById("tabProceso:activoProceso").style.borderColor = 'red';
                  PF('tabProceso').select(0);
                  accionInformacionProceso();
                  PF("dialogGuardar").hide();
                  addMessage(
                    "globalMsg",
                    "Advertencia: ",
                    "Debe ingresar un activo de proceso válido.",
                    "warn"
                  );
                  throw "El activo de proceso no es válido.";
                }
          }
          ll_attrs.idProceso = ll_proceso;
          guardarCambiosSubproceso(ll_attrs);
        }
      }
    }
  } else {
    PF('tabProceso').select(0);
    accionInformacionProceso();
    PF("dialogGuardar").hide();
    document.getElementById("tabProceso:procesoNuevo").style.borderColor = 'red';
    addMessage(
      "globalMsg",
      "Advertencia: ",
      "Debe ingresar un proceso válido.",
      "warn"
    );
    throw "El proceso no es válido.";
  }
  cargarInfoProceso();
}

function guardarCambiosSubproceso(av_attrs) {
  let ll_subProceso;
  ll_subProceso = document.getElementById("tabProceso:idSubprocesoNuevo").value.trim();
  if (procesoValido(ll_subProceso)) {
    if (av_attrs != null) {
      {
        let ll_dom;
        ll_dom = document.getElementById("tabProceso:nombreSubproceso");
        if (ll_dom != null) {
          let ll_nombre;
          ll_nombre =
            ll_dom.value;
          if (isValidString(ll_nombre)) {
            av_attrs.nombreSubproceso = ll_nombre;
            ll_dom.style.borderColor = "#799DB0";
          }
          else {
            PF('tabProceso').select(1);
            ll_dom.style.borderColor = "red";
            addMessage(
              "globalMsg",
              "Advertencia: ",
              "Debe ingresar un nombre de subproceso válido",
              "warn"
            );
            throw ("Debe ingresar un nombre de subproceso válido");
          }
        }
      }
      {
        let ll_activo;
        ll_activo = PF('activoSubProceso').getSelectedValue();
        if (isValidString(ll_activo)) {
          av_attrs.activoSubProceso = ll_activo;
          document.getElementById("tabProceso:activoSubProceso").style.borderColor = "#799DB0";
        }
        else
        {
              document.getElementById("tabProceso:activoSubProceso").style.borderColor = 'red';
              PF('tabProceso').select(1);
              addMessage(
                "globalMsg",
                "Advertencia: ",
                "Debe ingresar un activo de subproceso válido",
                "warn"
              );
              throw "El activo de subproceso no es válido.";
            }
      }
      av_attrs.idSubproceso = ll_subProceso
      addMessage(
        "globalMsg",
        "Información: ",
        "Proceso guardado correctamente",
        "info"
      );
      bloquearAprobador();
      PF("SBProceso").hide();
    }
  }
  else {
    PF('tabProceso').select(1);
    addMessage(
      "globalMsg",
      "Advertencia: ",
      "Debe ingresar un subproceso válido",
      "warn"
    );
    ll_dom.style.borderColor = "red";
    throw ("Debe ingresar un subproceso válido");
  }
}

function addMessage(
  av_widgetVar,
  av_titulo,
  av_detalleMensaje,
  av_tipoMensaje
) {
  var lv_growl;
  lv_growl = PF(av_widgetVar);
  if (lv_growl != null) {
    lv_growl.removeAll();
    lv_growl.renderMessage({
      summary: av_titulo,
      detail: av_detalleMensaje,
      severity: av_tipoMensaje
    });
  } else {
    throw "El growl no existe en la pantalla";
  }
}

function cargarInfoProceso() {
  let ll_proceso;

  ll_proceso = lv_bpmnModeler.getDefinitions().rootElements[0].$attrs;

  if (ll_proceso != null) {
    document.getElementById('infoProcesos').innerHTML =
      '<div class="ui-g" align="center">' +
      '<div class="ui-g-6 ui-md-6 ui-lg-6">' +
      '<p style="color:black">' +
      '<b>Proceso:</b> <i>' + ll_proceso.idProceso + '</i><br/>' +
      '<b>Sub proceso:</b> <i>' + ll_proceso.idSubproceso + '</i><br/>' +
      '<b>Versión:</b> <i>' + (lvsp_subproceso != null ? lvsp_subproceso.ii_consecutivoVersion : '1') + '</i><br/>' +
      '</p>' +
      '</div>' +
      '<div class="ui-g-6 ui-md-6 ui-lg-6">' +
      '<p style="color:black">' +
      '<b>Nombre:</b> <i>' + ll_proceso.nombreProceso + '</i><br/>' +
      '<b>Nombre:</b> <i>' + ll_proceso.nombreSubproceso + '</i><br/>' +
      (lab_monitoreo ? '<b>Instancia:</b> <i>' + (las_turno || 'N/A') + '</i>' : '') +
      '</p>' +
      '</div>' +
      '</div>';
  }
}

function cargarInfoColors() {
  if (!lab_monitoreo)
    document.getElementById('infoColors').style.display = 'none';
  else {
    document.getElementById('infoColors').innerHTML =
      '<div class="ui-g" align="center">' +
      '<div class="entry fa fa-info-circle" style="font-size: 244%;" draggable="true" onclick="abrirInfoColores();" title="Convenciones"></div>'
    '</div>';
  }
}

function cargarRecargarMonitoreo() {
	if (!lab_monitoreo)
		document.getElementById('refreshButton').style.display = 'none';
	else {
		document.getElementById('refreshButton').innerHTML =
			'<div class="ui-g" align="center">' +
			'<div class="entry fa fa-refresh" style="font-size: 244%;" draggable="true" onclick="refreshMonitoreo();" title="Refrescar"></div>'
			'</div>';
	}
}

function abrirInfoColores() {
  PF('dialogInfoColores').show();
}
inicializar();
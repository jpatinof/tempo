//window.onload = function () {
//    if (window.addEventListener) {
//        window.addEventListener('keypress', forceKeyPressUppercase, false);
//    } else if (document.body.attachEvent) {// IE
//        document.body.attachEvent('keypress', forceKeyPressUppercase);
//    } else {
//        document.onkeydown = forceKeyPressUppercase;
//    };
//};

function mostrarElementos(...aav_elementosAMostrar) {
    if (isValidCollection(aav_elementosAMostrar)) {
        aav_elementosAMostrar.forEach((item) => {
            let ll_elemento;
            ll_elemento = $('#' + item);

            if (ll_elemento != null) {
                ll_elemento.fadeIn(1000);
            }
        });
    }
}

function contarCaracteres(av_idTextArea, av_idCounter) {
    var lv_textoArea;
    var lv_numeroCaracteres;

    lv_textoArea = document.getElementById(av_idTextArea).value;
    lv_numeroCaracteres = lv_textoArea.length;

    document.getElementById(av_idCounter).innerHTML = lv_numeroCaracteres;
}

function isValidCollection(av_coleccion) {
    return (av_coleccion != null) && (av_coleccion.length > 0);
}

function isValidString(av_string) {
    return (av_string != null) && (av_string.trim().length > 0);
}

function ocultarElemento(av_idElemento) {
    var lo_boton;
    lo_boton = document.getElementById(av_idElemento);

    if (lo_boton != null) {
        lo_boton.style.display = 'none';
    }
}
function ocultarElementos(...aav_elementosAOcultar) {
    if (isValidCollection(aav_elementosAOcultar)) {
        aav_elementosAOcultar.forEach((item) => {
            let ll_elemento;
            ll_elemento = $('#' + item);

            if (ll_elemento != null) {
                ll_elemento.fadeOut(500);
            }
        });
    }
}

function limpiarUpload(elemento) {
    if (elemento != null)
        elemento.clear();
}

function forceKeyPressUppercase(av_event) {
    var charInput = av_event.keyCode;
    if ((charInput >= 97) && (charInput <= 122) || (charInput == 241 || charInput == 225 || charInput == 233 || charInput == 237 || charInput == 250 || charInput == 252 || charInput == 243 || charInput == 231)) {
        if (!av_event.ctrlKey && !av_event.metaKey && !av_event.altKey) {
            var newChar = charInput - 32;
            var start = av_event.target.selectionStart;
            var end = av_event.target.selectionEnd;
            av_event.target.value = av_event.target.value.substring(0, start) + String.fromCharCode(newChar) + av_event.target.value.substring(end);
            av_event.target.setSelectionRange(start + 1, start + 1);
            av_event.preventDefault();
        }
    }
}

function stop() {
}

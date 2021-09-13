package com.cromasoft.cromaflow.common.constants;


/**
 * Interface para obtener sentencias de scripts para ejecutar desde el bean.
 *
 * @author jpatino
 */
public interface ScriptsCommon
{
	/** Propiedad abrir confirmacion avisos. */
	public final String ABRIR_CONFIRMACION_AVISOS = "PF('dlgConfirmacion232').show()";

	/** Propiedad abrir confirmacion log. */
	public final String ABRIR_CONFIRMACION_LOG = "PF('dialogDescarga').show();";

	/** Propiedad accion digitalizacion. */
	public final String ACCION_DIGITALIZACION = "accionBotonDigitalizar();";

	/** Propiedad cerrar dialog exenta. */
	public final String CERRAR_DIALOG_EXENTA = "PF('idDexentaPersona').hide()";

	/** Propiedad dialog nir visitas show. */
	public final String DIALOG_NIR_VISITAS_SHOW = "PF('idDialogNir').show();";

	/** Propiedad fail impresion. */
	public final String FAIL_IMPRESION = "failImpresion();";

	/** Propiedad fin funcion. */
	public final String FIN_FUNCION = "');";

	/** Propiedad guardado exitoso. */
	public final String GUARDADO_EXITOSO = "guardadoExitoso();";

	/** Propiedad guardar cambios regla negocio. */
	public final String GUARDAR_CAMBIOS_REGLA_NEGOCIO = "guardarCambiosReglaNegocio('";

	/** Propiedad imprimir documento. */
	public final String IMPRIMIR_DOCUMENTO = "imprimirDocumento();";

	/** Propiedad limpiar filtros logs. */
	public final String LIMPIAR_FILTROS_LOGS = "PF('dTLogs').clearFilters()";

	/** Propiedad mostrar digitalizar. */
	public final String MOSTRAR_DIGITALIZAR = " mostrarElemento('fDetalleEntrega:idBotonDigitalizar');";

	/** Propiedad mostrar pad firmas. */
	public final String MOSTRAR_PAD_FIRMAS = " mostrarElemento('fDetalleEntrega:idBotonPadFirmas');";

	/** Propiedad ocultar digitalizar. */
	public final String OCULTAR_DIGITALIZAR = " ocultarElemento('fDetalleEntrega:idBotonDigitalizar');";

	/** Propiedad ocultar pad firmas. */
	public final String OCULTAR_PAD_FIRMAS = " ocultarElemento('fDetalleEntrega:idBotonPadFirmas');";

	/** Propiedad wizard next. */
	public final String WIZARD_NEXT = "PF('wizardWorkflow').next();";
}

package com.cromasoft.cromaflow.constants;


/**
 * Interface para tipo estado de una solicitud
 *
 * @author hcastaneda
 */
public interface TipoEstadoSolicitudCommon
{
	/** Propiedad en tramite. */
	public final long EN_TRAMITE = 4L;

	/** Propiedad finalizado. */
	public final long FINALIZADO = 5L;

	/** Propiedad generado. */
	public final long GENERADO = 1L;

	/** Propiedad liquidado. */
	public final long LIQUIDADO = 2L;

	/** Propiedad pagado. */
	public final long PAGADO = 3L;
}

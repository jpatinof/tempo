package com.cromasoft.cromaflow.constants;


/**
 * Interface comun para formato de fechas.
 *
 * @author garias
 */
public interface FormatoFechaCommon
{
	/** Propiedad anio mes dia. */
	public final String ANIO_MES_DIA = "yyMMdd";

	/** Propiedad anio mes dia hora minuto segundo. */
	public final String ANIO_MES_DIA_HORA_MINUTO_SEGUNDO = "yyyy-MM-dd'T'HH:mm:ss";

	/** Propiedad anio mes dia hora minuto segundo owcc. */
	public final String ANIO_MES_DIA_HORA_MINUTO_SEGUNDO_OWCC = "yyyy-MM-dd HH:mm:ss";

	/** Propiedad anio yyyy mes dia. */
	public final String ANIO_YYYY_MES_DIA = "yyyyMMdd";

	/** Propiedad dia mes anio. */
	public final String DIA_MES_ANIO = "dd/MM/yyyy";

	/** Propiedad dia mes anio hora minuto. */
	public final String DIA_MES_ANIO_HORA_MINUTO = "dd/MM/yyyy HH:mm";

	/** Propiedad dia mes anio hora minuto segundo. */
	public final String DIA_MES_ANIO_HORA_MINUTO_SEGUNDO = "dd/MM/yyyy HH:mm:ss";

	/** Propiedad hora. */
	public final String HORA = "HH:mm:ss";
}

package com.cromasoft.cromaflow.common.utils;

import com.b2bsg.common.logging.LoggerHandler;


/**
 * Helper para cargar el bundle del log.
 *
 * @author Julian Vaca
 */
public class LoggingHelper
{
	/** Constante cs_DEFAULT_BUNDLE. */
	private static final String cs_DEFAULT_BUNDLE = "conf/procf.xml";

	/**
	 * Obtiene el log.
	 *
	 * @param ac_class Clase que se implementa para el log
	 * @return lloger instancia del log
	 * @see org.apache.log4j.Logger
	 */
	public static org.apache.log4j.Logger getLogger(Class<?> ac_class)
	{
		LoggerHandler.setDefaultBundle(cs_DEFAULT_BUNDLE);

		return LoggerHandler.getLogger(ac_class);
	}
}

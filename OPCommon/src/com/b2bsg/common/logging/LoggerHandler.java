package com.b2bsg.common.logging;

import com.b2bsg.common.util.StringUtils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;


/**
 * Clase que contiene todos las propiedades de LoggerHandler para el manejo de log
 *
 * @author Edgar Prieto
 */
public class LoggerHandler extends Logger
{
	/** Propiedad cs default bundle. */
	private static String cs_defaultBundle;

	/** Constante clhf_FACTORY. */
	private static final LoggerHandlerFactory clhf_FACTORY = new LoggerHandlerFactory();

	/** Constante DEFAULT_BUNDLE. */
	public static final String DEFAULT_BUNDLE = "com/b2bsg/common/logging/conf/log.xml";

	/** Propiedad cu url. */
	private static URL cu_url;

	/**
	 * Instancia un nuevo objeto logger handler.
	 *
	 * @param as_name correspondiente al valor del tipo de objeto String
	 */
	public LoggerHandler(String as_name)
	{
		super(as_name);

		init();
	}

	/**
	 * Modifica el valor de default bundle.
	 *
	 * @param as_defaultBundle asigna el valor a la propiedad default bundle
	 */
	public static synchronized void setDefaultBundle(String as_defaultBundle)
	{
		if((cs_defaultBundle == null) && StringUtils.isValidString(as_defaultBundle))
			cs_defaultBundle = as_defaultBundle;
	}

	/**
	 * Retorna el valor de logger.
	 *
	 * @param ac_class correspondiente al valor del tipo de objeto Class
	 * @return el valor de logger
	 */
	public static Logger getLogger(Class ac_class)
	{
		return getLogger(ac_class.getName());
	}

	/**
	 * Retorna el valor de logger.
	 *
	 * @param as_name correspondiente al valor del tipo de objeto String
	 * @return el valor de logger
	 */
	public static Logger getLogger(String as_name)
	{
		return Logger.getLogger(as_name, clhf_FACTORY);
	}

	/** {@inheritdoc} */
	public void debug(Object ao_message)
	{
		if(isEnabledFor(Level.DEBUG))
			super.debug(getMessage(ao_message));
	}

	/**
	 * Debug.
	 *
	 * @param as_method correspondiente al valor del tipo de objeto String
	 * @param obj correspondiente al valor del tipo de objeto Object
	 */
	public void debug(String as_method, Object obj)
	{
		if(isEnabledFor(Level.DEBUG))
			super.debug(getMessage(as_method, obj));
	}

	/** {@inheritdoc} */
	public void error(Object ao_message)
	{
		if(isEnabledFor(Level.ERROR))
			super.error(getMessage(ao_message));
	}

	/**
	 * Error.
	 *
	 * @param as_method correspondiente al valor del tipo de objeto String
	 * @param as_description correspondiente al valor del tipo de objeto String
	 */
	public void error(String as_method, String as_description)
	{
		if(isEnabledFor(Level.ERROR))
			super.error(getMessage(as_method, as_description));
	}

	/** {@inheritdoc} */
	public void fatal(Object ao_message)
	{
		if(isEnabledFor(Level.FATAL))
			super.fatal(getMessage(ao_message));
	}

	/** {@inheritdoc} */
	public void info(Object ao_message)
	{
		if(isEnabledFor(Level.INFO))
			super.info(getMessage(ao_message));
	}

	/**
	 * Info.
	 *
	 * @param as_method correspondiente al valor de nombre de metodo
	 * @param as_description correspondiente al valor de descripcion
	 */
	public void info(String as_method, String as_description)
	{
		if(isEnabledFor(Level.INFO))
			super.info(getMessage(as_method, as_description));
	}

	/** {@inheritdoc} */
	public void warn(Object ao_message)
	{
		if(isEnabledFor(Level.WARN))
			super.warn(getMessage(ao_message));
	}

	/**
	 * Retorna el valor de basic message.
	 *
	 * @return el valor de basic message
	 */
	private StringBuilder getBasicMessage()
	{
		StringBuilder lsb_basicMessage;

		lsb_basicMessage = new StringBuilder();

		lsb_basicMessage.append("[");
		lsb_basicMessage.append(getName());
		lsb_basicMessage.append("] ");

		return lsb_basicMessage;
	}

	/**
	 * Retorna el valor de message.
	 *
	 * @param ao_message correspondiente al valor del tipo de objeto Object
	 * @return el valor de message
	 */
	private String getMessage(Object ao_message)
	{
		StringBuilder lsb_message;

		lsb_message = getBasicMessage();

		lsb_message.append(ao_message);

		return lsb_message.toString();
	}

	/**
	 * Retorna el valor de message.
	 *
	 * @param as_method correspondiente al valor del tipo de objeto String
	 * @param as_description correspondiente al valor del tipo de objeto Object
	 * @return el valor de message
	 */
	private String getMessage(String as_method, Object as_description)
	{
		StringBuilder lsb_message;

		lsb_message = getBasicMessage();

		lsb_message.append(as_method);
		lsb_message.append(": ");
		lsb_message.append(as_description);

		return lsb_message.toString();
	}

	/**
	 * Configurator log.
	 */
	private void configuratorLog()
	{
		if(cu_url == null)
		{
			cu_url = LoggerHandler.class.getResource(
				    StringUtils.isValidString(cs_defaultBundle) ? cs_defaultBundle : DEFAULT_BUNDLE
				);

			if(cu_url == null)
				cs_defaultBundle = null;
			else
				org.apache.log4j.xml.DOMConfigurator.configure(cu_url);
		}
	}

	/**
	 * Configura el log
	 */
	private void init()
	{
		configuratorLog();
	}
}

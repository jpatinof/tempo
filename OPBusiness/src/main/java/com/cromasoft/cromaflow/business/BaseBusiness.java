package com.cromasoft.cromaflow.business;

import com.aspose.words.License;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.messages.Messages;


/**
 * Clase que contiene todos la fucionalidades para la base de todos los business.
 *
 * @author hcastaneda
 */
@SuppressWarnings("restriction")
public class BaseBusiness
{
	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(BaseBusiness.class);

	/** Constante CONFIG_MESSAGES. */
	public static final String CONFIG_MESSAGES = "com.bachue.snr.prosnr01.web.bean.resources.Messages";

	/** Constante CONFIG_MESSAGES_22. */
	public static final String CONFIG_ERROR_MESSAGES = "com.bachue.snr.prosnr01.web.bean.resources.Errors";

	/** Constante cs_IOEXCEPTION. */
	protected static final String cs_IOEXCEPTION = "IOException";

	/** Constante cs_BAD_ELEMENT_EXCEPTION. */
	protected static final String cs_BAD_ELEMENT_EXCEPTION = "BadElementException";

	/** Constante cs_DOCUMENT_EXCEPTION. */
	protected static final String cs_DOCUMENT_EXCEPTION = "DocumentException";

	/** Constante cs_SQL_ERROR. */
	protected static final String cs_SQL_ERROR = "errorSql";

	/** Propiedad im messages. */
	private static Messages im_messages;

	/** Propiedad im error messages. */
	private static Messages im_errorMessages;

	/**
	 * Retorna el valor de error messages.
	 *
	 * @return el valor de error messages
	 */
	public static Messages getErrorMessages()
	{
		if(im_errorMessages == null)
			im_errorMessages = new Messages(CONFIG_ERROR_MESSAGES);

		return im_errorMessages;
	}

	/**
	 * Retorna el valor de messages.
	 *
	 * @return el valor de messages
	 */
	public static Messages getMessages()
	{
		if(im_messages == null)
			im_messages = new Messages(CONFIG_MESSAGES);

		return im_messages;
	}
}

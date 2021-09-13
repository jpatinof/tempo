package com.b2bsg.common.serviceLocator;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.messages.Messages;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.ConverterUtils;
import com.b2bsg.common.util.StringUtils;

import java.util.Hashtable;
import java.util.Map;

import javax.mail.Session;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;


/**
 * Clase que contiene todos las propiedades de ServiceLocator.
 *
 * @author Edgar Prieto
 */
public class ServiceLocator
{
	/** Propiedad im messages. */
	private static Messages im_messages;

	/** Propiedad csl SERVIC E LOCATOR. */
	private static ServiceLocator csl_SERVICE_LOCATOR;

	/** Propiedad cs BUNDLE. */
	private static String cs_BUNDLE;

	/** Constante DEFAUL_ENV. */
	public static final boolean DEFAUL_ENV = true;

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)LoggerHandler.getLogger(ServiceLocator.class);

	/** Constante cs_DEFAULT_BUNDLE. */
	private static final String cs_DEFAULT_BUNDLE = "com.b2bsg.common.serviceLocator.conf.context";

	/** Constante cs_PREFIX. */
	private static final String cs_PREFIX = "java:comp/env/";

	/** Constante cs_DEFAULT_MAIL_SESSION_PROPERTY. */
	private static final String cs_DEFAULT_MAIL_SESSION_PROPERTY = "jndi.mail";

	/** Propiedad ic ctx. */
	private Context ic_ctx;

	/** Propiedad im cache. */
	private Map im_cache;

	/**
	 * Instancia un nuevo objeto service locator.
	 *
	 * @param as_bundle correspondiente al valor del tipo de objeto String
	 */
	private ServiceLocator(String as_bundle)
	{
		try
		{
			Hashtable lh_environment;

			cs_BUNDLE          = as_bundle;
			lh_environment     = new Hashtable();
			im_cache           = java.util.Collections.synchronizedMap(new java.util.HashMap());

			setProperty(lh_environment, Context.INITIAL_CONTEXT_FACTORY);
			setProperty(lh_environment, Context.PROVIDER_URL);
			setProperty(lh_environment, Context.SECURITY_AUTHENTICATION);
			setProperty(lh_environment, Context.SECURITY_CREDENTIALS);
			setProperty(lh_environment, Context.SECURITY_PRINCIPAL);
			setProperty(lh_environment, Context.SECURITY_PROTOCOL);

			ic_ctx = CollectionUtils.isValidCollection(lh_environment) ? new InitialContext(lh_environment)
				                                                       : new InitialContext();
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("ServiceLocator", le_e);
		}
	}

	/**
	 * Get instance from static class.
	 *
	 * @return el valor de service locator
	 */
	public static ServiceLocator getServiceLocator()
	{
		return getServiceLocator(null);
	}

	/**
	 * Retorna Objeto o variable de valor config mail session.
	 *
	 * @param ab_env de ab env
	 * @return el valor de config mail session
	 */
	public Session getConfigMailSession(boolean ab_env)
	{
		return getConfigMailSession(cs_DEFAULT_MAIL_SESSION_PROPERTY, ab_env);
	}

	/**
	 * Retorna Objeto o variable de valor config mail session.
	 *
	 * @param as_property de as property
	 * @return el valor de config mail session
	 */
	public Session getConfigMailSession(String as_property)
	{
		return getMailSession(getConfigValue(as_property));
	}

	/**
	 * Retorna Objeto o variable de valor config mail session.
	 *
	 * @param as_property de as property
	 * @param ab_env de ab env
	 * @return el valor de config mail session
	 */
	public Session getConfigMailSession(String as_property, boolean ab_env)
	{
		return getMailSession(getConfigValue(as_property), ab_env);
	}

	/**
	 * Retorna el valor de config registered object.
	 *
	 * @param as_property correspondiente al valor del tipo de objeto String
	 * @param ab_env correspondiente al valor del tipo de objeto boolean
	 * @return el valor de config registered object
	 */
	public Object getConfigRegisteredObject(String as_property, boolean ab_env)
	{
		return getRegisteredObject(getConfigValue(as_property), ab_env);
	}

	/**
	 * Retorna el valor de data source.
	 *
	 * @param as_jndi correspondiente al valor del tipo de objeto String
	 * @param ab_env correspondiente al valor del tipo de objeto boolean
	 * @return el valor de data source
	 */
	public DataSource getDataSource(String as_jndi, boolean ab_env)
	{
		DataSource lds_dataSource;

		try
		{
			if(im_cache.containsKey(as_jndi))
			{
				lds_dataSource = CollectionUtils.getDataSource(im_cache, as_jndi);

				if(clh_LOGGER.isDebugEnabled())
					clh_LOGGER.debug("getDataSource", "Already in cache: " + as_jndi);
			}
			else
			{
				Object lo_ref;

				lo_ref             = getRegisteredObject(as_jndi, ab_env);
				lds_dataSource     = ConverterUtils.getDataSource(lo_ref);

				if(lds_dataSource != null)
				{
					im_cache.put(as_jndi, lds_dataSource);

					if(clh_LOGGER.isDebugEnabled())
						clh_LOGGER.debug("getDataSource", "Add to cache: " + as_jndi);
				}
				else
				{
					if(clh_LOGGER.isDebugEnabled())
						clh_LOGGER.debug("getDataSource", "Lookup failed: " + as_jndi);
				}
			}
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("getDataSource", le_e);

			lds_dataSource = null;
		}

		return lds_dataSource;
	}

	/**
	 * Retorna Objeto o variable de valor mail session.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_env de ab env
	 * @return el valor de mail session
	 */
	public Session getMailSession(String as_jndi, boolean ab_env)
	{
		Session ls_session;

		try
		{
			if(im_cache.containsKey(as_jndi))
			{
				ls_session = CollectionUtils.getMailSession(im_cache, as_jndi);

				if(clh_LOGGER.isDebugEnabled())
					clh_LOGGER.debug("getMailSession", "Already in cache: " + as_jndi);
			}
			else
			{
				Object lo_ref;

				lo_ref         = getRegisteredObject(as_jndi, ab_env);
				ls_session     = ConverterUtils.getMailSession(lo_ref);

				if(ls_session != null)
				{
					im_cache.put(as_jndi, ls_session);

					if(clh_LOGGER.isDebugEnabled())
						clh_LOGGER.debug("getMailSession", "Add to cache: " + as_jndi);
				}
				else
				{
					if(clh_LOGGER.isDebugEnabled())
						clh_LOGGER.debug("getMailSession", "Lookup failed: " + as_jndi);
				}
			}
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("getMailSession", le_e);

			ls_session = null;
		}

		return ls_session;
	}

	/**
	 * Retorna Objeto o variable de valor mail session.
	 *
	 * @param as_jndi de as jndi
	 * @return el valor de mail session
	 */
	public Session getMailSession(String as_jndi)
	{
		return getMailSession(as_jndi, DEFAUL_ENV);
	}

	/**
	 * Get instance from static class.
	 *
	 * @param as_bundle correspondiente al valor del tipo de objeto String
	 * @return el valor de service locator
	 */
	private static ServiceLocator getServiceLocator(String as_bundle)
	{
		if(csl_SERVICE_LOCATOR == null)
			csl_SERVICE_LOCATOR = new ServiceLocator(as_bundle);

		return csl_SERVICE_LOCATOR;
	}

	/**
	 * Retorna el valor de config value.
	 *
	 * @param as_property correspondiente al valor del tipo de objeto String
	 * @return el valor de config value
	 */
	private String getConfigValue(String as_property)
	{
		return getMessages().getString(as_property);
	}

	/**
	 * Retorna el valor de messages.
	 *
	 * @return el valor de messages
	 */
	private Messages getMessages()
	{
		if(im_messages == null)
		{
			String ls_bundle;

			ls_bundle       = StringUtils.isValidString(cs_BUNDLE) ? cs_BUNDLE : cs_DEFAULT_BUNDLE;
			im_messages     = new Messages(ls_bundle);

			if(clh_LOGGER.isDebugEnabled())
				clh_LOGGER.debug("getMessages", "Using configuration from: " + ls_bundle);
		}

		return im_messages;
	}

	/**
	 * Sets the property.
	 *
	 * @param ah_environment correspondiente al valor del tipo de objeto Hashtable
	 * @param as_property correspondiente al valor del tipo de objeto String
	 */
	private void setProperty(Hashtable ah_environment, String as_property)
	{
		boolean lb_debug;
		String  ls_tmp;

		lb_debug     = clh_LOGGER.isDebugEnabled();
		ls_tmp       = getConfigValue(as_property);

		if(StringUtils.isValidString(ls_tmp))
		{
			ah_environment.put(as_property, ls_tmp);

			if(lb_debug)
				clh_LOGGER.debug("setProperty", "Value for property " + as_property + "=" + ls_tmp);
		}
		else if(lb_debug)
			clh_LOGGER.debug("setProperty", "Value for property " + as_property + " not found. Using default value");
	}

	/**
	 * Obtener un servicio <i>JNDI</i> genérico.
	 *
	 * @param as_jndi Entrada <i>JNDI</i> asociada al servicio.
	 * @param ab_env correspondiente al valor del tipo de objeto boolean
	 * @return Servicio genérico.
	 */
	private Object getRegisteredObject(String as_jndi, boolean ab_env)
	{
		Object lo_o;

		synchronized(ic_ctx)
		{
			String ls_jndi;

			{
				StringBuilder lsb_jndi;

				lsb_jndi = new StringBuilder();

				if(ab_env)
					lsb_jndi.append(cs_PREFIX);

				lsb_jndi.append(as_jndi);

				ls_jndi = lsb_jndi.toString();
			}

			if(clh_LOGGER.isDebugEnabled())
				clh_LOGGER.debug("getRegisteredObject", "lookup: " + ls_jndi);

			try
			{
				lo_o = ic_ctx.lookup(ls_jndi);
			}
			catch(Exception le_e)
			{
				clh_LOGGER.error("getRegisteredObject", le_e);

				lo_o = null;
			}
		}

		return lo_o;
	}
}

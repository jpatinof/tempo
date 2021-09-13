package com.b2bsg.common.messages;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Clase que contiene todos las propiedades de Messages de los logs.
 *
 * @author Edgar Prieto
 */
public class Messages
{
	/** Constante is_EXTENSION. */
	public static final String is_EXTENSION = ".properties";

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)LoggerHandler.getLogger(Messages.class);

	/** Propiedad icl loader. */
	private ClassLoader icl_loader;

	/** Propiedad il locale. */
	private Locale il_locale;

	/** Propiedad irb bundle. */
	private ResourceBundle irb_bundle;

	/** Propiedad is bundle name. */
	private String is_bundleName;

	/**
	 * Instancia un nuevo objeto messages.
	 */
	public Messages()
	{
		this(null);
	}

	/**
	 * Instancia un nuevo objeto messages.
	 *
	 * @param as_bundleName correspondiente al valor del tipo de objeto String
	 */
	public Messages(String as_bundleName)
	{
		this(as_bundleName, Locale.getDefault());
	}

	/**
	 * Instancia un nuevo objeto messages.
	 *
	 * @param as_bundleName correspondiente al valor del tipo de objeto String
	 * @param al_locale correspondiente al valor del tipo de objeto Locale
	 * @param acl_loader correspondiente al valor del tipo de objeto ClassLoader
	 */
	private Messages(String as_bundleName, Locale al_locale, ClassLoader acl_loader)
	{
		setBundle(as_bundleName, al_locale, acl_loader);
	}

	/**
	 * Instancia un nuevo objeto messages.
	 *
	 * @param as_bundleName correspondiente al valor del tipo de objeto String
	 * @param al_locale correspondiente al valor del tipo de objeto Locale
	 */
	private Messages(String as_bundleName, Locale al_locale)
	{
		this(as_bundleName, al_locale, getThreadClassLoader());
	}

	/**
	 * Modifica el valor de bundle name.
	 *
	 * @param as_bundleName asigna el valor a la propiedad bundle name
	 */
	public void setBundleName(String as_bundleName)
	{
		irb_bundle        = null;
		is_bundleName     = StringUtils.getString(as_bundleName);
	}

	/**
	 * Retorna el valor de string.
	 *
	 * @param as_key correspondiente al valor del tipo de objeto String
	 * @return el valor de string
	 */
	public String getString(String as_key)
	{
		try
		{
			return getBundle().getString(as_key);
		}
		catch(Exception le_e)
		{
			return null;
		}
	}

	/**
	 * Retorna el valor de string.
	 *
	 * @param as_key correspondiente al valor del tipo de objeto String
	 * @param loa_oa correspondiente al valor del tipo de objeto Object[]
	 * @return el valor de string
	 */
	public String getString(String as_key, Object[] loa_oa)
	{
		String ls_message;
		String ls_pattern;

		ls_message = null;

		try
		{
			ls_pattern = getString(as_key);

			if(StringUtils.isValidString(ls_pattern))
				ls_message = java.text.MessageFormat.format(ls_pattern, loa_oa);
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("getString", le_e);
		}

		return ls_message;
	}

	/**
	 * Retorna el valor de thread class loader.
	 *
	 * @return el valor de thread class loader
	 */
	private static ClassLoader getThreadClassLoader()
	{
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * Sets the bundle.
	 *
	 * @param as_bundleName correspondiente al valor del tipo de objeto String
	 * @param al_locale correspondiente al valor del tipo de objeto Locale
	 * @param acl_loader correspondiente al valor del tipo de objeto ClassLoader
	 */
	private void setBundle(String as_bundleName, Locale al_locale, ClassLoader acl_loader)
	{
		setBundleName(as_bundleName);
		setClassLoader(acl_loader);
		setLocale(al_locale);
	}

	/**
	 * Retorna el valor de bundle.
	 *
	 * @return el valor de bundle
	 */
	private ResourceBundle getBundle()
	{
		if(irb_bundle == null)
		{
			boolean     lb_bundleName;
			boolean     lb_debug;
			boolean     lb_loader;
			boolean     lb_locale;
			ClassLoader lcl_loader;
			Locale      ll_locale;
			String      ls_bundleName;

			lb_debug          = clh_LOGGER.isDebugEnabled();
			lcl_loader        = getClassLoader();
			ll_locale         = getLocale();
			ls_bundleName     = getBundleName();
			lb_bundleName     = StringUtils.isValidString(ls_bundleName);
			lb_loader         = lcl_loader != null;
			lb_locale         = ll_locale != null;

			if(lb_bundleName && lb_debug)
				clh_LOGGER.debug("getBundle", "Bundle Name: " + ls_bundleName);

			if(lb_locale && lb_debug)
				clh_LOGGER.debug("getBundle", "Locale: " + ll_locale.getDisplayName());

			if(lb_loader && lb_debug)
				clh_LOGGER.debug("getBundle", "ClassLoader: " + lcl_loader.toString());

			irb_bundle = (lb_bundleName)
				? ((lb_locale)
				? ((lb_loader) ? ResourceBundle.getBundle(ls_bundleName, ll_locale, lcl_loader)
				               : ResourceBundle.getBundle(ls_bundleName, ll_locale))
				: ResourceBundle.getBundle(ls_bundleName)) : null;
		}

		return irb_bundle;
	}

	/**
	 * Retorna el valor de bundle name.
	 *
	 * @return el valor de bundle name
	 */
	private String getBundleName()
	{
		return is_bundleName;
	}

	/**
	 * Modifica el valor de class loader.
	 *
	 * @param acl_loader asigna el valor a la propiedad class loader
	 */
	private void setClassLoader(ClassLoader acl_loader)
	{
		icl_loader     = acl_loader;
		irb_bundle     = null;
	}

	/**
	 * Retorna el valor de class loader.
	 *
	 * @return el valor de class loader
	 */
	private ClassLoader getClassLoader()
	{
		return icl_loader;
	}

	/**
	 * Modifica el valor de locale.
	 *
	 * @param al_locale asigna el valor a la propiedad locale
	 */
	private void setLocale(Locale al_locale)
	{
		il_locale      = al_locale;
		irb_bundle     = null;
	}

	/**
	 * Retorna el valor de locale.
	 *
	 * @return el valor de locale
	 */
	private Locale getLocale()
	{
		return il_locale;
	}
	
}

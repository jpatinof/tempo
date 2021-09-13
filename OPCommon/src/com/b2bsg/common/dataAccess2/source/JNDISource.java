package com.b2bsg.common.dataAccess2.source;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.messages.Messages;

import com.b2bsg.common.util.StringUtils;

import java.sql.Connection;


/**
 * Clase que contiene todos las propiedades de JNDISource para establecer
 * conexiones por datasource
 *
 * @author EdgarPrieto
 */
public class JNDISource implements com.b2bsg.common.dataAccess2.source.ConnectionSource
{
	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)LoggerHandler.getLogger(JNDISource.class);

	/** Propiedad ids datasource. */
	javax.sql.DataSource ids_datasource;

	/** Propiedad is password. */
	private String is_password;

	/** Propiedad is user. */
	private String is_user;

	/** Propiedad is vendor. */
	private String is_vendor;

	/** Propiedad ib include connection id in thread name. */
	private boolean ib_includeConnectionIdInThreadName;

	/** Propiedad ib user. */
	private boolean ib_user;

	/**
	 * Instancia un nuevo objeto JNDI source.
	 *
	 * @param as_bundle correspondiente al valor del tipo de objeto String
	 */
	public JNDISource(String as_bundle)
	{
		init(as_bundle);
	}

	/** {@inheritdoc} */
	public boolean isIncludeConnectionIdInThreadName()
	{
		return ib_includeConnectionIdInThreadName;
	}

	/** {@inheritdoc} */
	public void setPassword(String as_password)
	{
		is_password = StringUtils.getString(as_password);
	}

	/** {@inheritdoc} */
	public String getPassword()
	{
		return is_password;
	}

	/** {@inheritdoc} */
	public void setUser(String as_user)
	{
		is_user = StringUtils.getString(as_user);
	}

	/** {@inheritdoc} */
	public String getUser()
	{
		return is_user;
	}

	/** {@inheritdoc} */
	public String getVendor()
	{
		return is_vendor;
	}

	/** {@inheritdoc} */
	public Connection create()
	    throws B2BException
	{
		Connection lc_c;

		lc_c = null;

		if(clh_LOGGER.isDebugEnabled())
			clh_LOGGER.debug("create");

		try
		{
			lc_c = (ib_user) ? ids_datasource.getConnection(getUser(), getPassword()) : ids_datasource.getConnection();
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("create", le_e);

			throw new B2BException(com.b2bsg.common.dataAccess2.BaseDAO.SQL_ERROR, le_e);
		}

		return lc_c;
	}

	/**
	 * Inits the.
	 *
	 * @param as_bundle correspondiente al valor del tipo de objeto String
	 */
	private void init(String as_bundle)
	{
		boolean  lb_log;
		Messages lm_messages;
		String   ls_bundle;
		String   ls_contextSearch;
		String   ls_datasource;
		String   ls_user;

		lb_log        = clh_LOGGER.isDebugEnabled();
		ls_bundle     = StringUtils.isValidString(as_bundle) ? as_bundle : ConnectionSource.DEFAULT_BUNDLE;

		lm_messages = new Messages(ls_bundle);

		if(lb_log)
			clh_LOGGER.debug("init", "Using configuration from: " + ls_bundle);

		ib_includeConnectionIdInThreadName     = com.b2bsg.common.util.BooleanUtils.getBooleanValue(
			    StringUtils.getString(lm_messages.getString("jdbc.includeConnectionIdInThreadName"))
			);

		ls_contextSearch     = StringUtils.getString(lm_messages.getString("jdbc.contextSearch"));
		ls_datasource        = StringUtils.getString(lm_messages.getString("jdbc.datasource"));
		is_vendor            = StringUtils.getString(lm_messages.getString("jdbc.vendor"));
		ls_user              = StringUtils.getString(lm_messages.getString("jdbc.user"));

		setUser(ls_user);

		{
			String ls_password;

			ls_password = lm_messages.getString("jdbc.password");

			setPassword(ls_password);

			ib_user = StringUtils.isValidString(ls_user) && StringUtils.isValidString(ls_password);
		}

		if(ls_contextSearch == null)
			ls_contextSearch = StringUtils.getTrueString();

		ids_datasource = com.b2bsg.common.serviceLocator.ServiceLocator.getServiceLocator()
				                                                           .getDataSource(
				    ls_datasource, com.b2bsg.common.util.BooleanUtils.getBooleanValue(ls_contextSearch)
				);

		if(lb_log)
		{
			clh_LOGGER.debug("init", "Datasource JNDI                     : " + ls_datasource);
			clh_LOGGER.debug("init", "Context Search                      : " + ls_contextSearch);
			clh_LOGGER.debug("init", "Include Connection Id In Thread Name: " + ib_includeConnectionIdInThreadName);
			clh_LOGGER.debug("init", "User                                : " + ls_user);
			clh_LOGGER.debug("init", "Vendor                              : " + is_vendor);
			clh_LOGGER.debug(
			    "init", "Datasoruce Creation                 : " + ((ids_datasource != null) ? "Correct" : "Error")
			);
		}
	}
}

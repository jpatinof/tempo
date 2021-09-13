package com.b2bsg.common.dataAccess2.source;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.messages.Messages;

import com.b2bsg.common.util.StringUtils;


/**
 * Clase que contiene todos las propiedades DriverSource.
 *
 * @author  Julain Vaca
 * Fecha de Creacion: 13/11/2019
 */
public class DriverSource implements com.b2bsg.common.dataAccess2.source.ConnectionSource
{
	
	/**
	 * Constante clh_LOGGER.
	 */
	private static final LoggerHandler clh_LOGGER                         = (LoggerHandler)LoggerHandler
			.getLogger(DriverSource.class);
	
	/**
	 * Propiedad is driver.
	 */
	private String                     is_driver;
	
	/**
	 * Propiedad is password.
	 */
	private String                     is_password;
	
	/**
	 * Propiedad is url.
	 */
	private String                     is_url;
	
	/**
	 * Propiedad is user.
	 */
	private String                     is_user;
	
	/**
	 * Propiedad is vendor.
	 */
	private String                     is_vendor;
	
	/**
	 * Propiedad ib include connection id in thread name.
	 */
	private boolean                    ib_includeConnectionIdInThreadName;

	/**
	 * Instancia un nuevo objeto driver source.
	 */
	public DriverSource()
	{
		this(null);
	}

	/**
	 * Instancia un nuevo objeto driver source.
	 *
	 * @param as_bundle de as bundle
	 */
	public DriverSource(String as_bundle)
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
		is_password                                                       = StringUtils.getString(as_password);
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
	public java.sql.Connection create()
	    throws B2BException
	{
		if(clh_LOGGER.isDebugEnabled())
			clh_LOGGER.debug("create");

		try
		{
			return com.b2bsg.common.dataAccess.DaoFactory.getConnection(is_driver, is_url, getUser(), getPassword());
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("create", le_e);

			throw new B2BException(com.b2bsg.common.dataAccess2.BaseDAO.SQL_ERROR, le_e);
		}
	}

	/**
	 * Inits the.
	 *
	 * @param as_bundle de as bundle
	 */
	private void init(String as_bundle)
	{
		boolean  lb_log;
		Messages lm_messages;
		String   ls_bundle;
		String   ls_user;

		lb_log        = clh_LOGGER.isDebugEnabled();
		ls_bundle     = StringUtils.isValidString(as_bundle) ? as_bundle : ConnectionSource.DEFAULT_BUNDLE;

		lm_messages = new Messages(ls_bundle);

		if(lb_log)
			clh_LOGGER.debug("init", "Using configuration from: " + ls_bundle);

		ib_includeConnectionIdInThreadName     = com.b2bsg.common.util.BooleanUtils.getBooleanValue(
			    StringUtils.getString(lm_messages.getString("jdbc.includeConnectionIdInThreadName"))
			);

		is_driver     = StringUtils.getString(lm_messages.getString("jdbc.driver"));
		is_url        = StringUtils.getString(lm_messages.getString("jdbc.url"));
		is_vendor     = StringUtils.getString(lm_messages.getString("jdbc.vendor"));
		ls_user       = StringUtils.getString(lm_messages.getString("jdbc.user"));

		setPassword(lm_messages.getString("jdbc.password"));
		setUser(ls_user);

		if(lb_log)
		{
			clh_LOGGER.debug("init", "Driver                              : " + is_driver);
			clh_LOGGER.debug("init", "Include Connection Id In Thread Name: " + ib_includeConnectionIdInThreadName);
			clh_LOGGER.debug("init", "JDBC URL                            : " + is_url);
			clh_LOGGER.debug("init", "User                                : " + ls_user);
			clh_LOGGER.debug("init", "Vendor                              : " + is_vendor);
		}
	}
}

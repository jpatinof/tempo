package com.b2bsg.common.dataAccess2;

import com.b2bsg.common.dataAccess.DaoFactory;

import com.b2bsg.common.dataAccess2.source.ConnectionSource;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.DateUtils;
import com.b2bsg.common.util.NumericUtils;
import com.b2bsg.common.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.text.MessageFormat;

import java.util.Map;


/**
 * Clase que contiene todos las propiedades de BaseDAO para manejo de DAO
 *
 * @author Edgar Prieto
 */
public abstract class BaseDAO
{
	/** Constante TL_NONE. */
	public static final int TL_NONE = Connection.TRANSACTION_NONE;

	/** Constante TL_READ_COMMITTED. */
	public static final int TL_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;

	/** Constante TL_READ_UNCOMMITTED. */
	public static final int TL_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

	/** Constante TL_REPEATABLE_READ. */
	public static final int TL_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;

	/** Constante TL_SERIALIZABLE. */
	public static final int TL_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)LoggerHandler.getLogger(BaseDAO.class);

	/** Constante cs_DEFAULT_SOURCE. */
	private static final String cs_DEFAULT_SOURCE = "com.b2bsg.common.dataAccess2.source.DriverSource";

	/** Constante cs_NEXT_SEQ_VAL_DB2. */
	private static final String cs_NEXT_SEQ_VAL_DB2 = "SELECT NEXTVAL FOR {0} AS VAL FROM SYSIBM.SYSDUMMY1";

	/** Constante cs_NEXT_SEQ_VAL_ORACLE. */
	private static final String cs_NEXT_SEQ_VAL_ORACLE = "SELECT {0}.NEXTVAL AS VAL FROM DUAL";

	/** Constante cs_NEXT_SEQ_VAL_POSTGRES. */
	private static final String cs_NEXT_SEQ_VAL_POSTGRES = "SELECT NEXTVAL('{0}' AS VAL)";

	/** Constante cs_SPID_SYBASE. */
	private static final String cs_SPID_SYBASE = "SELECT @@spid";

	/** Constante SQL_ERROR. */
	public static final String SQL_ERROR = "errorSql";

	/** Constante SQL_ERROR_INVALID_VENDOR. */
	public static final String SQL_ERROR_INVALID_VENDOR = "errorSqlVendorInvalid";

	/** Constante ci_VENDOR_DB2. */
	private static final int ci_VENDOR_DB2 = 1;

	/** Constante ci_VENDOR_NONE. */
	private static final int ci_VENDOR_NONE = 0;

	/** Constante ci_VENDOR_ORACLE. */
	private static final int ci_VENDOR_ORACLE = 2;

	/** Constante ci_VENDOR_SYBASE. */
	private static final int ci_VENDOR_SYBASE = 3;

	/** Constante ci_VENDOR_POSTGRES. */
	private static final int ci_VENDOR_POSTGRES = 4;

	/** Constante ci_VENDOR_MAX_CODE. */
	private static final int ci_VENDOR_MAX_CODE = ci_VENDOR_POSTGRES;

	/** Constante cs_VENDOR_DB2. */
	private static final String cs_VENDOR_DB2 = "DB2";

	/** Constante cs_VENDOR_ORACLE. */
	private static final String cs_VENDOR_ORACLE = "ORACLE";

	/** Constante cs_VENDOR_SYBASE. */
	private static final String cs_VENDOR_SYBASE = "SYBASE";

	/** Constante cs_VENDOR_POSTGRES. */
	private static final String cs_VENDOR_POSTGRES = "POSTGRES";

	/** Constante cs_THREAD_NAME_APPENDIX. */
	private static final String cs_THREAD_NAME_APPENDIX = " :: (Connection SPID ";

	/** Propiedad ib read only. */
	private Boolean ib_readOnly;

	/** Propiedad ic connection. */
	private Connection ic_connection;

	/** Propiedad ics source. */
	private ConnectionSource ics_source;

	/** Propiedad ii transaction isolation level. */
	private Integer ii_transactionIsolationLevel;

	/** Propiedad im context. */
	private Map im_context;

	/** Propiedad ib include connection id in thread name. */
	private boolean ib_includeConnectionIdInThreadName;

	/** Propiedad ib shared. */
	private boolean ib_shared;

	/** Propiedad ii vendor code. */
	private int ii_vendorCode;

	/** Propiedad il spid. */
	private long il_spid;

	/**
	 *  Creates an instance of this dao without a connection.
	 */
	public BaseDAO()
	{
		ib_includeConnectionIdInThreadName     = false;
		ib_shared                              = false;
		ics_source                             = null;
		ii_vendorCode                          = ci_VENDOR_NONE;
	}

	/**
	 * Sets auto commit on or off.
	 *
	 * @param ab_b asigna el valor a la propiedad auto commit
	 */
	public void setAutoCommit(boolean ab_b)
	{
		try
		{
			if((ic_connection != null) && !ic_connection.isClosed())
				ic_connection.setAutoCommit(ab_b);
		}
		catch(SQLException lse_e)
		{
			clh_LOGGER.error("setAutoCommit(boolean)", lse_e);
		}
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param as_column correspondiente al valor del tipo de objeto String
	 * @return el valor de big decimal
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public BigDecimal getBigDecimal(ResultSet ars_rs, String as_column)
	    throws SQLException
	{
		return getBigDecimal(ars_rs, ars_rs.getDouble(as_column));
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param as_column correspondiente al valor del tipo de objeto String
	 * @return el valor de big integer
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public BigInteger getBigInteger(ResultSet ars_rs, String as_column)
	    throws SQLException
	{
		return getBigInteger(ars_rs, ars_rs.getLong(as_column));
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @return el valor de big integer
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public BigInteger getBigInteger(ResultSet ars_rs, int ai_column)
	    throws SQLException
	{
		return getBigInteger(ars_rs, ars_rs.getLong(ai_column));
	}

	/**
	 * Creates an instance of a DAO, passing the connection to use.
	 *
	 * @param ac_c asigna el valor a la propiedad connection
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public void setConnection(Connection ac_c)
	    throws B2BException
	{
		close();

		ib_shared         = true;
		ic_connection     = ac_c;
	}

	/**
	 * Retorna el valor de connection.
	 *
	 * @return el valor de connection
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public Connection getConnection()
	    throws B2BException
	{
		return getConnection(true);
	}

	/**
	 * Returns a connection to the database.
	 *
	 * @param as_driverSourceClassName correspondiente al valor del tipo de objeto String
	 * @param ab_autoCommit correspondiente al valor del tipo de objeto boolean
	 * @return el valor de connection
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public Connection getConnection(String as_driverSourceClassName, boolean ab_autoCommit)
	    throws B2BException
	{
		try
		{
			if((ic_connection == null) || ic_connection.isClosed())
			{
				ib_shared         = false;
				ic_connection     = null;

				try
				{
					if(ics_source == null)
					{
						Class  lc_source;
						String ls_driverSourceClassName;

						ls_driverSourceClassName     = com.b2bsg.common.util.StringUtils.isValidString(
							    as_driverSourceClassName
							) ? as_driverSourceClassName : cs_DEFAULT_SOURCE;

						lc_source = Class.forName(ls_driverSourceClassName);

						if(clh_LOGGER.isDebugEnabled())
							clh_LOGGER.debug(
							    "getConnection", "Using " + ls_driverSourceClassName + " as connection source"
							);

						ics_source = (ConnectionSource)lc_source.newInstance();
					}

					if(getVendorCode() == ci_VENDOR_NONE)
						setVendorCode(ics_source.getVendor());

					ic_connection = ics_source.create();
					//TODO solo se usa para debug
//					ic_connection.setSchema("workshape_sch");

					{
						boolean lb_setAutoCommit;
						Boolean lB_readOnly;

						lb_setAutoCommit     = true;
						lB_readOnly          = getReadOnly();

						if(lB_readOnly != null)
						{
							boolean lb_readOnly;

							lb_readOnly = lB_readOnly.booleanValue();

							ic_connection.setReadOnly(lb_readOnly);

							if(lb_readOnly)
								lb_setAutoCommit = false;
						}

						if(lb_setAutoCommit)
							setAutoCommit(ab_autoCommit);
					}

					{
						Integer li_transactionIsolationLevel;

						li_transactionIsolationLevel = getTransactionIsolationLevel();

						if(li_transactionIsolationLevel != null)
							ic_connection.setTransactionIsolation(li_transactionIsolationLevel.intValue());
					}

					{
						boolean lb_spid;
						long    ll_spid;

						ll_spid     = findSPID();
						lb_spid     = ll_spid > 0;

						setSpid(ll_spid);

						if(clh_LOGGER.isDebugEnabled())
							clh_LOGGER.debug("Connection opened: " + (lb_spid ? ll_spid : ic_connection.hashCode()));

						if(lb_spid && isIncludeConnectionIdInThreadName())
						{
							Thread lt_thread;

							lt_thread = Thread.currentThread();

							if(lt_thread != null)
							{
								String        ls_threadName;
								StringBuilder lsb_threadName;

								ls_threadName      = lt_thread.getName();
								lsb_threadName     = new StringBuilder();

								if(ls_threadName != null)
								{
									int li_pos;

									li_pos = ls_threadName.indexOf(cs_THREAD_NAME_APPENDIX);

									if(li_pos > -1)
										ls_threadName = ls_threadName.substring(0, li_pos);

									lsb_threadName.append(ls_threadName);
								}

								lsb_threadName.append(cs_THREAD_NAME_APPENDIX);
								lsb_threadName.append(ll_spid);
								lsb_threadName.append(")");

								lt_thread.setName(lsb_threadName.toString());
							}
						}
					}
				}
				catch(Exception le_e)
				{
					clh_LOGGER.error("getConnection", le_e);

					throw new B2BException(SQL_ERROR, le_e);
				}
			}
		}
		catch(SQLException lse_e)
		{
			throw new B2BException(SQL_ERROR, lse_e);
		}

		return ic_connection;
	}

	/**
	 * Modifica el valor de connection source.
	 *
	 * @param acs_cs asigna el valor a la propiedad connection source
	 */
	public void setConnectionSource(ConnectionSource acs_cs)
	{
		ics_source = acs_cs;

		if(acs_cs != null)
		{
			setIncludeConnectionIdInThreadName(acs_cs.isIncludeConnectionIdInThreadName());
			setVendorCode(acs_cs.getVendor());
		}
	}

	/**
	 * Modifica el valor de context.
	 *
	 * @param am_m asigna el valor a la propiedad context
	 */
	public void setContext(Map am_m)
	{
		im_context = am_m;
	}

	/**
	 * Retorna el valor de context.
	 *
	 * @return el valor de context
	 */
	public Map getContext()
	{
		if(im_context == null)
			im_context = new java.util.HashMap();

		return im_context;
	}

	/**
	 * Sets the date.
	 *
	 * @param aps_ps correspondiente al valor del tipo de objeto PreparedStatement
	 * @param ad_d correspondiente al valor del tipo de objeto Date
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public void setDate(PreparedStatement aps_ps, java.util.Date ad_d, int ai_column)
	    throws SQLException
	{
		aps_ps.setDate(ai_column, DateUtils.getSQLDate(ad_d));
	}

	/**
	 * Sets the double.
	 *
	 * @param aps_ps correspondiente al valor del tipo de objeto PreparedStatement
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public void setDouble(PreparedStatement aps_ps, Double ad_d, int ai_column)
	    throws SQLException
	{
		if(ad_d == null)
			aps_ps.setNull(ai_column, Types.DOUBLE);
		else
			aps_ps.setDouble(ai_column, ad_d.doubleValue());
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param as_column correspondiente al valor del tipo de objeto String
	 * @return el valor de double
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public Double getDouble(ResultSet ars_rs, String as_column)
	    throws SQLException
	{
		return getDouble(ars_rs, ars_rs.getDouble(as_column));
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @return el valor de double
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public Double getDouble(ResultSet ars_rs, int ai_column)
	    throws SQLException
	{
		return getDouble(ars_rs, ars_rs.getDouble(ai_column));
	}

	/**
	 * Sets the integer.
	 *
	 * @param aps_ps correspondiente al valor del tipo de objeto PreparedStatement
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public void setInteger(PreparedStatement aps_ps, Integer ai_i, int ai_column)
	    throws SQLException
	{
		if(ai_i == null)
			aps_ps.setNull(ai_column, Types.INTEGER);
		else
			aps_ps.setLong(ai_column, ai_i.longValue());
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param as_column correspondiente al valor del tipo de objeto String
	 * @return el valor de integer
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public Integer getInteger(ResultSet ars_rs, String as_column)
	    throws SQLException
	{
		return getIntegerPrivate(ars_rs, ars_rs.getInt(as_column));
	}

	/**
	 * Returns a logger handler.
	 *
	 * @return el valor de log
	 */
	public LoggerHandler getLog()
	{
		return clh_LOGGER;
	}

	/**
	 * Sets the long.
	 *
	 * @param aps_ps correspondiente al valor del tipo de objeto PreparedStatement
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public void setLong(PreparedStatement aps_ps, Long al_l, int ai_column)
	    throws SQLException
	{
		if(al_l == null)
			aps_ps.setNull(ai_column, Types.INTEGER);
		else
			aps_ps.setLong(ai_column, al_l.longValue());
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param as_column correspondiente al valor del tipo de objeto String
	 * @return el valor de long
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public Long getLong(ResultSet ars_rs, String as_column)
	    throws SQLException
	{
		return getLong(ars_rs, ars_rs.getLong(as_column));
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param ai_column correspondiente al valor del tipo de objeto int
	 * @return el valor de long
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public Long getLong(ResultSet ars_rs, int ai_column)
	    throws SQLException
	{
		return getLong(ars_rs, ars_rs.getLong(ai_column));
	}

	/**
	 * Sets the read only.
	 */
	public void setReadOnly()
	{
		setReadOnly(true);
	}

	/**
	 * Modifica el valor de read only.
	 *
	 * @param ab_b asigna el valor a la propiedad read only
	 */
	public void setReadOnly(Boolean ab_b)
	{
		ib_readOnly = ab_b;
	}

	/**
	 * Retorna el valor de read only.
	 *
	 * @return el valor de read only
	 */
	public Boolean getReadOnly()
	{
		return ib_readOnly;
	}

	/**
	 * Valida la propiedad shared.
	 *
	 * @return Returns the shared.
	 */
	public boolean isShared()
	{
		return ib_shared;
	}

	/**
	 * Modifica el valor de spid.
	 *
	 * @param al_l asigna el valor a la propiedad spid
	 */
	public void setSpid(long al_l)
	{
		il_spid = al_l;
	}

	/**
	 * Retorna el valor de spid.
	 *
	 * @return el valor de spid
	 */
	public long getSpid()
	{
		return il_spid;
	}

	/**
	 * Modifica el valor de transaction isolation level.
	 *
	 * @param ai_i asigna el valor a la propiedad transaction isolation level
	 */
	public void setTransactionIsolationLevel(Integer ai_i)
	{
		ii_transactionIsolationLevel = ai_i;
	}

	/**
	 * Retorna el valor de transaction isolation level.
	 *
	 * @return el valor de transaction isolation level
	 */
	public Integer getTransactionIsolationLevel()
	{
		return ii_transactionIsolationLevel;
	}

	/**
	 * Sets the transaction isolation level read uncommited.
	 */
	public void setTransactionIsolationLevelReadUncommited()
	{
		setTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED);
	}

	/**
	 * Modifica el valor de vendor code.
	 *
	 * @param ai_i asigna el valor a la propiedad vendor code
	 */
	public void setVendorCode(int ai_i)
	{
		ii_vendorCode = ((ai_i < ci_VENDOR_NONE) || (ai_i > ci_VENDOR_MAX_CODE)) ? ci_VENDOR_NONE : ai_i;
	}

	/**
	 * Retorna el valor de vendor code.
	 *
	 * @return el valor de vendor code
	 */
	public int getVendorCode()
	{
		return ii_vendorCode;
	}

	/**
	 * Closes a statement.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Statement
	 */
	public void close(Statement as_s)
	{
		try
		{
			DaoFactory.close(as_s);
		}
		catch(SQLException lse_e)
		{
			clh_LOGGER.error("close", lse_e);
		}
	}

	/**
	 * Closes a result set.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 */
	public void close(ResultSet ars_rs)
	{
		try
		{
			DaoFactory.close(ars_rs);
		}
		catch(SQLException lse_e)
		{
			clh_LOGGER.error("close", lse_e);
		}
	}

	/**
	 * Closes the daos connection to the database.
	 *
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public void close()
	    throws B2BException
	{
		close(false);
	}

	/**
	 * Commits the current transaction.
	 *
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public void commit()
	    throws B2BException
	{
		try
		{
			if(isValidTransaction())
				ic_connection.commit();
		}
		catch(SQLException lse_e)
		{
			clh_LOGGER.error("commit", lse_e);

			throw new B2BException(SQL_ERROR, lse_e);
		}
	}

	/**
	 * Returns the next value of the specified sequence.
	 *
	 * @param as_name correspondiente al valor del tipo de objeto String
	 * @return the next value
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see long
	 */
	public long nextSeqVal(String as_name)
	    throws B2BException
	{
		long              ll_value;
		PreparedStatement lps_ps;
		ResultSet         lrs_rs;
		String            ls_template;

		ll_value     = 0;
		lps_ps       = null;
		lrs_rs       = null;

		switch(getVendorCode())
		{
			case ci_VENDOR_DB2:
				ls_template = cs_NEXT_SEQ_VAL_DB2;

				break;

			case ci_VENDOR_ORACLE:
				ls_template = cs_NEXT_SEQ_VAL_ORACLE;

				break;

			case ci_VENDOR_POSTGRES:
				ls_template = cs_NEXT_SEQ_VAL_POSTGRES;

				break;

			default:
				ls_template = null;

				break;
		}

		if(ls_template == null)
			throw new B2BException(SQL_ERROR_INVALID_VENDOR);

		try
		{
			String ls_query;

			ls_query     = MessageFormat.format(ls_template, new Object[]{as_name});
			lps_ps       = getConnection().prepareStatement(ls_query);
			lrs_rs       = lps_ps.executeQuery();

			if(lrs_rs.next())
				ll_value = lrs_rs.getLong("VAL");
		}
		catch(SQLException lse_e)
		{
			getLog().error("nextValSeq(String)", lse_e);

			throw new B2BException(SQL_ERROR, lse_e);
		}
		finally
		{
			close(lrs_rs);
			close(lps_ps);
		}

		return ll_value;
	}

	/**
	 * Rollbacks the current transaction.
	 *
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public void rollback()
	    throws B2BException
	{
		try
		{
			if(isValidTransaction())
				ic_connection.rollback();
		}
		catch(SQLException lse_e)
		{
			clh_LOGGER.error("rollback", lse_e);

			throw new B2BException(SQL_ERROR, lse_e);
		}
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param ad_d correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	protected Double getDouble(ResultSet ars_rs, double ad_d)
	    throws SQLException
	{
		return ars_rs.wasNull() ? null : NumericUtils.getDoubleWrapper(ad_d);
	}

	/**
	 * Log error.
	 *
	 * @param abd_dao correspondiente al valor del tipo de objeto BaseDAO
	 * @param as_method correspondiente al valor del tipo de objeto String
	 * @param ase_error correspondiente al valor del tipo de objeto SQLException
	 */
	protected void logError(BaseDAO abd_dao, String as_method, SQLException ase_error)
	{
		if(ase_error != null)
		{
			StringBuilder lsb_error;

			lsb_error = new StringBuilder();

			if(abd_dao != null)
				lsb_error.append(abd_dao.getClass().getName());

			if(as_method != null)
			{
				if(lsb_error.length() > 0)
					lsb_error.append(" :: ");

				lsb_error.append(as_method);
			}

			{
				long ll_spid;

				ll_spid = getSpid();

				if(ll_spid > 0)
				{
					if(lsb_error.length() > 0)
						lsb_error.append(" :: ");

					lsb_error.append("(Connection SPID ");
					lsb_error.append(ll_spid);
					lsb_error.append(")");
				}
			}

			clh_LOGGER.error(lsb_error, ase_error);
		}
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param ad_d correspondiente al valor del tipo de objeto double
	 * @return el valor de big decimal
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	private BigDecimal getBigDecimal(ResultSet ars_rs, double ad_d)
	    throws SQLException
	{
		return ars_rs.wasNull() ? null : NumericUtils.getBigDecimal(ad_d);
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de big integer
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	private BigInteger getBigInteger(ResultSet ars_rs, long al_l)
	    throws SQLException
	{
		return ars_rs.wasNull() ? null : NumericUtils.getBigInteger(al_l);
	}

	/**
	 * Retorna el valor de connection.
	 *
	 * @param ab_autoCommit correspondiente al valor del tipo de objeto boolean
	 * @return el valor de connection
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	private Connection getConnection(boolean ab_autoCommit)
	    throws B2BException
	{
		return getConnection(null, ab_autoCommit);
	}

	/**
	 * Modifica el valor de include connection id in thread name.
	 *
	 * @param ab_b asigna el valor a la propiedad include connection id in thread name
	 */
	private void setIncludeConnectionIdInThreadName(boolean ab_b)
	{
		ib_includeConnectionIdInThreadName = ab_b;
	}

	/**
	 * Valida la propiedad include connection id in thread name.
	 *
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en include connection id in thread name
	 */
	private boolean isIncludeConnectionIdInThreadName()
	{
		return ib_includeConnectionIdInThreadName;
	}

	/**
	 * Retorna el valor de integer private.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de integer private
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	private Integer getIntegerPrivate(ResultSet ars_rs, int ai_i)
	    throws SQLException
	{
		return ars_rs.wasNull() ? null : NumericUtils.getInteger(ai_i);
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	private Long getLong(ResultSet ars_rs, long al_l)
	    throws SQLException
	{
		return ars_rs.wasNull() ? null : NumericUtils.getLongWrapper(al_l);
	}

	/**
	 * Modifica el valor de read only.
	 *
	 * @param ab_b asigna el valor a la propiedad read only
	 */
	private void setReadOnly(boolean ab_b)
	{
		setReadOnly(new Boolean(ab_b));
	}

	/**
	 * Modifica el valor de transaction isolation level.
	 *
	 * @param ai_i asigna el valor a la propiedad transaction isolation level
	 */
	private void setTransactionIsolationLevel(int ai_i)
	{
		setTransactionIsolationLevel(NumericUtils.getInteger(ai_i));
	}

	/**
	 * Valida la propiedad valid transaction.
	 *
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid transaction
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	private boolean isValidTransaction()
	    throws SQLException
	{
		return (ic_connection != null) && !ic_connection.isClosed() && !ic_connection.getAutoCommit();
	}

	/**
	 * Modifica el valor de vendor code.
	 *
	 * @param as_vendorName asigna el valor a la propiedad vendor code
	 */
	private void setVendorCode(String as_vendorName)
	{
		int    li_code;
		String ls_vendorName;

		ls_vendorName = (as_vendorName != null) ? StringUtils.getStringUpperCase(as_vendorName) : new String();

		if(ls_vendorName.equals(cs_VENDOR_DB2))
			li_code = ci_VENDOR_DB2;
		else if(ls_vendorName.equals(cs_VENDOR_ORACLE))
			li_code = ci_VENDOR_ORACLE;
		else if(ls_vendorName.equals(cs_VENDOR_SYBASE))
			li_code = ci_VENDOR_SYBASE;
		else if(ls_vendorName.equals(cs_VENDOR_POSTGRES))
			li_code = ci_VENDOR_POSTGRES;
		else
			li_code = ci_VENDOR_NONE;

		setVendorCode(li_code);
	}

	/**
	 * Close.
	 *
	 * @param ab_forced correspondiente al valor del tipo de objeto boolean
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	private void close(boolean ab_forced)
	    throws B2BException
	{
		try
		{
			boolean lb_sharedOff;

			lb_sharedOff = false;

			if(ic_connection != null)
			{
				if(ab_forced || !ib_shared)
				{
					if(!ic_connection.isClosed())
						ic_connection.close();

					if(clh_LOGGER.isDebugEnabled())
						clh_LOGGER.debug("Connection closed: " + ic_connection.hashCode());
				}

				lb_sharedOff      = true;
				ic_connection     = null;
			}

			if(im_context != null)
			{
				if(!ib_shared)
					im_context.clear();

				ib_shared      = lb_sharedOff || true;
				im_context     = null;
			}

			if(lb_sharedOff)
				ib_shared = false;
		}
		catch(SQLException lse_e)
		{
			clh_LOGGER.error("close", lse_e);

			throw new B2BException(SQL_ERROR, lse_e);
		}

		if(isIncludeConnectionIdInThreadName())
		{
			Thread lt_thread;

			lt_thread = Thread.currentThread();

			if(lt_thread != null)
			{
				String ls_threadName;

				ls_threadName = lt_thread.getName();

				if(ls_threadName != null)
				{
					int li_pos;

					li_pos = ls_threadName.indexOf(cs_THREAD_NAME_APPENDIX);

					if(li_pos > -1)
						ls_threadName = ls_threadName.substring(0, li_pos);

					lt_thread.setName(ls_threadName);
				}
			}
		}
	}

	/**
	 * Returns the spid for this connection.
	 *
	 * @return spid
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see long
	 */
	private long findSPID()
	    throws B2BException
	{
		long   ll_id;
		String ls_template;

		ll_id = 0;

		if(ic_connection != null)
		{
			switch(getVendorCode())
			{
				case ci_VENDOR_SYBASE:
					ls_template = cs_SPID_SYBASE;

					break;

				default:
					ls_template = null;

					break;
			}

			if(ls_template != null)
			{
				PreparedStatement lps_ps;
				ResultSet         lrs_rs;

				lps_ps     = null;
				lrs_rs     = null;

				try
				{
					lps_ps     = ic_connection.prepareStatement(ls_template);
					lrs_rs     = lps_ps.executeQuery();

					if(lrs_rs.next())
						ll_id = lrs_rs.getLong(1);
				}
				catch(SQLException lse_e)
				{
					getLog().error("findSPID()", lse_e);

					throw new B2BException(SQL_ERROR, lse_e);
				}
				finally
				{
					close(lrs_rs);
					close(lps_ps);
				}
			}
		}

		return ll_id;
	}
}

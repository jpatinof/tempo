package com.b2bsg.common.dataAccess2;

import com.b2bsg.common.dataAccess2.source.ConnectionSource;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Clase que contiene todos las propiedades de DAOManager para el manejo de acceso a datos
 *
 * @author Edgar Prieto
 */
public class DAOManager extends BaseDAO
{
	/** Constante ci_VENDOR_POSTGRES. */
	private static final int ci_VENDOR_POSTGRES = 4;

	/** Constante cs_PATH_POSTGRES. */
	private static final String cs_PATH_POSTGRES = "postgres.";

	/** Constante cs_PATH_ORACLE. */
	private static final String cs_PATH_ORACLE = "oracle.";

	/** Constante ci_VENDOR_ORACLE. */
	private static final int ci_VENDOR_ORACLE = 2;

	/** Propiedad im daos. */
	private Map im_daos;

	/** Propiedad is connection source class name. */
	private String is_connectionSourceClassName;

	/** Propiedad ib auto commit. */
	private boolean ib_autoCommit;

	/** Propiedad ib rollback only. */
	private boolean ib_rollbackOnly;

	/**
	 * Instancia un nuevo objeto DAO manager.
	 */
	public DAOManager()
	{
		this(null, false);
	}

	/**
	 * Instancia un nuevo objeto DAO manager.
	 *
	 * @param as_driverSourceClassName correspondiente al valor del tipo de objeto String
	 * @param ab_autoCommit correspondiente al valor del tipo de objeto boolean
	 */
	private DAOManager(String as_driverSourceClassName, boolean ab_autoCommit)
	{
		ib_autoCommit                    = ab_autoCommit;
		ib_rollbackOnly                  = false;
		im_daos                          = new HashMap();
		is_connectionSourceClassName     = as_driverSourceClassName;
	}

	/** {@inheritdoc} */
	public void setAutoCommit(boolean ab_b)
	{
		ib_autoCommit = ab_b;

		super.setAutoCommit(ib_autoCommit);
	}

	/** {@inheritdoc} */
	public void setConnectionSource(ConnectionSource acs_cs)
	{
		is_connectionSourceClassName = acs_cs.getClass().getName();

		super.setConnectionSource(acs_cs);
	}

	/**
	 * Retorna el valor de dao.
	 *
	 * @param ac_daoClass correspondiente al valor del tipo de objeto Class
	 * @return el valor de dao
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public BaseDAO getDAO(Class ac_daoClass)
	    throws B2BException
	{
		BaseDAO lbdao_dao;

		lbdao_dao = null;

		try
		{
			lbdao_dao = (BaseDAO)im_daos.get(ac_daoClass);

			if((lbdao_dao == null) || !lbdao_dao.isShared())
			{
				int   li_vendorCode;
				Class lc_daoClass;

				li_vendorCode     = getVendorCode();
				lc_daoClass       = generateClass(ac_daoClass, li_vendorCode);
				lbdao_dao         = (BaseDAO)lc_daoClass.newInstance();

				lbdao_dao.setConnection(getConnection(is_connectionSourceClassName, ib_autoCommit));
				lbdao_dao.setContext(getContext());
				lbdao_dao.setReadOnly(getReadOnly());
				lbdao_dao.setSpid(getSpid());
				lbdao_dao.setTransactionIsolationLevel(getTransactionIsolationLevel());
				lbdao_dao.setVendorCode(li_vendorCode);

				im_daos.put(lc_daoClass, lbdao_dao);
			}
		}
		catch(Exception le_e)
		{
			getLog().error("getDAO", le_e);

			throw new B2BException(SQL_ERROR, le_e);
		}

		return lbdao_dao;
	}

	/**
	 * Sets the rollback only.
	 */
	public void setRollbackOnly()
	{
		ib_rollbackOnly = true;
	}

	/** {@inheritdoc} */
	public void close()
	    throws B2BException
	{
		if(com.b2bsg.common.util.CollectionUtils.isValidCollection(im_daos))
		{
			java.util.Iterator li_daos;

			li_daos = im_daos.values().iterator();

			while(li_daos.hasNext())
			{
				BaseDAO lbdao_dao;

				lbdao_dao = (BaseDAO)li_daos.next();

				lbdao_dao.close();
			}
		}

		im_daos.clear();

		if(!ib_autoCommit)
		{
			ib_autoCommit = true;

			if(isRollbackOnly())
				rollback();
			else
				commit();

			if(!com.b2bsg.common.util.BooleanUtils.getBooleanValue(getReadOnly()))
				setAutoCommit(ib_autoCommit);
		}

		super.close();
	}

	/**
	 * Valida la propiedad rollback only.
	 *
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en rollback only
	 */
	private boolean isRollbackOnly()
	{
		return ib_rollbackOnly;
	}

	/**
	 * Método encargado de generar la clase DAO para postgres u oracle según el vendor code.
	 *
	 * @param ac_daoClass Clase base del DAO a generar.
	 * @param ai_vendorCode Indicador del vendor code.
	 * @return Clase DAO para postgres u oracle.
	 * @throws ClassNotFoundException
	 */
	private Class generateClass(Class ac_daoClass, int ai_vendorCode)
	    throws ClassNotFoundException
	{
		Class lc_return;

		lc_return = null;

		if(ac_daoClass != null)
		{
			String ls_fullClassName;

			ls_fullClassName = ac_daoClass.getName();

			if(StringUtils.isValidString(ls_fullClassName))
			{
				String[] lsa_pathClass;

				lsa_pathClass = ls_fullClassName.split("\\.");

				if(CollectionUtils.isValidCollection(lsa_pathClass))
				{
					StringBuilder ls_sb;
					int           ls_length;

					ls_sb         = new StringBuilder();
					ls_length     = lsa_pathClass.length;

					for(int li_count = 0; li_count < (ls_length - 1); li_count++)
					{
						ls_sb.append(lsa_pathClass[li_count]);
						ls_sb.append(".");
					}

					if(ai_vendorCode == ci_VENDOR_ORACLE)
						ls_sb.append(cs_PATH_ORACLE);
					else if(ai_vendorCode == ci_VENDOR_POSTGRES)
						ls_sb.append(cs_PATH_POSTGRES);

					ls_sb.append(ac_daoClass.getSimpleName());

					lc_return = Class.forName(ls_sb.toString());
				}
			}
		}

		return lc_return;
	}
}

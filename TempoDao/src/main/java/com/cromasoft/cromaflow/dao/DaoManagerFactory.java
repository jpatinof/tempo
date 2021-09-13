package com.cromasoft.cromaflow.dao;

import com.b2bsg.common.dataAccess2.DAOManager;

import com.b2bsg.common.dataAccess2.source.ConnectionSource;
import com.b2bsg.common.dataAccess2.source.JNDISource;


/**
 * Una fábrica para crear objetos DaoManager.
 * @author  Julián David Vaca Rodriguez
 * Fecha de Creacion: 20/02/2019
 */
public class DaoManagerFactory
{
	/** Constante cs CONFIG_FILE_OSTEOPHOENIX. */
	private static final String cs_CONFIG_FILE_OSTEOPHOENIX = "com.cromasoft.cromaflow.dataAccess2.source.jndiOp";

	/** Propiedad ccs OSTEOPHOENIX. */
	private static ConnectionSource ccs_OSTEOPHOENIX;

	/**
	 * Retorna el valor de DAO manager.
	 *
	 * @return el valor de DAO manager
	 */
	public static DAOManager getDAOManager()
	{
		return getDAOManager(false);
	}

	/**
	 * Retorna el valor de DAO manager.
	 *
	 * @param ab_readOnly correspondiente al valor del tipo de objeto boolean
	 * @return el valor de DAO manager
	 */
	public static DAOManager getDAOManager(boolean ab_readOnly)
	{
		DAOManager ldm_manager;

		ldm_manager = new DAOManager();

		ldm_manager.setConnectionSource(getSource());

		return ab_readOnly ? setReadOnly(ldm_manager) : ldm_manager;
	}

	/**
	 * Retorna el valor del objeto de tipo Sets the read only.
	 *
	 * @param adm_manager correspondiente al valor del tipo de objeto DAOManager
	 * @return devuelve el valor del objeto DAO manager
	 */
	private static DAOManager setReadOnly(DAOManager adm_manager)
	{
		if(adm_manager != null)
		{
			adm_manager.setReadOnly();
			adm_manager.setTransactionIsolationLevelReadUncommited();
		}

		return adm_manager;
	}

	/**
	 * Retorna el valor de source.
	 *
	 * @return el valor de source
	 */
	private static synchronized ConnectionSource getSource()
	{
		if(ccs_OSTEOPHOENIX == null)
			ccs_OSTEOPHOENIX = new JNDISource(cs_CONFIG_FILE_OSTEOPHOENIX);

		return ccs_OSTEOPHOENIX;
	}
}

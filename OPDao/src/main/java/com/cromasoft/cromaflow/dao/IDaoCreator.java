package com.cromasoft.cromaflow.dao;

import com.b2bsg.common.dataAccess2.DAOManager;

import com.b2bsg.common.exception.B2BException;


/**
 * Interface funcional para obtener cualquier tipo de DAO
 *
 * @author garias
 * @param <T> de tipo generico de DAO a obotener
 */
@FunctionalInterface
public interface IDaoCreator<T>
{
	/**
	 * Retorna el valor de dao.
	 *
	 * @param ldm_manager correspondiente al valor del tipo de objeto DAOManager
	 * @param ac_dao correspondiente al valor del tipo de objeto Class<?>
	 * @return el valor de dao
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	T getDao(DAOManager ldm_manager, Class<?> ac_dao)
	    throws B2BException;
}

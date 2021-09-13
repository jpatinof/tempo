package com.cromasoft.cromaflow.dao;

import com.b2bsg.common.dataAccess2.DAOManager;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.dao.aut.ImageDAO;
import com.cromasoft.cromaflow.dao.aut.RoleDAO;
import com.cromasoft.cromaflow.dao.aut.UserDAO;
import com.cromasoft.cromaflow.dao.aut.UserProfileImageDAO;
import com.cromasoft.cromaflow.dao.aut.UserRoleDAO;


/**
 * Clase que contiene todos las propiedades de DaoCreator para obtener los diferentes tipos de DAO a utilizar.
 *
 * @author dbeltran
 */
public class DaoCreator
{
	/**
	 * Retorna el valor de usuario DAO.
	 *
	 * @param adm_manager correspondiente al valor del tipo de objeto DAOManager
	 * @return el valor de usuario DAO
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public static UserDAO getUserDAO(DAOManager adm_manager)
	    throws B2BException
	{
		return (UserDAO)adm_manager.getDAO(UserDAO.class);
	}

	/**
	 * Retorna Objeto o variable de valor role DAO.
	 *
	 * @param adm_manager de adm manager
	 * @return el valor de role DAO
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public static RoleDAO getRoleDAO(DAOManager adm_manager)
	    throws B2BException
	{
		return (RoleDAO)adm_manager.getDAO(RoleDAO.class);
	}

	/**
	 * Retorna Objeto o variable de valor user role DAO.
	 *
	 * @param adm_manager de adm manager
	 * @return el valor de user role DAO
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public static UserRoleDAO getUserRoleDAO(DAOManager adm_manager)
	    throws B2BException
	{
		return (UserRoleDAO)adm_manager.getDAO(UserRoleDAO.class);
	}

	/**
	 * Retorna Objeto o variable de valor user profile image DAO.
	 *
	 * @param adm_manager de adm manager
	 * @return el valor de user profile image DAO
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public static UserProfileImageDAO getUserProfileImageDAO(DAOManager adm_manager)
	    throws B2BException
	{
		return (UserProfileImageDAO)adm_manager.getDAO(UserProfileImageDAO.class);
	}

	/**
	 * Retorna Objeto o variable de valor image DAO.
	 *
	 * @param adm_manager de adm manager
	 * @return el valor de image DAO
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public static ImageDAO getImageDAO(DAOManager adm_manager)
	    throws B2BException
	{
		return (ImageDAO)adm_manager.getDAO(ImageDAO.class);
	}
}

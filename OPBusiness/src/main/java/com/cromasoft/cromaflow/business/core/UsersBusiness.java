package com.cromasoft.cromaflow.business.core;

import com.b2bsg.common.dataAccess2.DAOManager;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.BooleanUtils;
import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.business.BaseBusiness;

import com.cromasoft.cromaflow.dao.DaoCreator;
import com.cromasoft.cromaflow.dao.DaoManagerFactory;

import com.cromasoft.cromaflow.dao.aut.RoleDAO;

import com.cromasoft.cromaflow.model.aut.Image;
import com.cromasoft.cromaflow.model.aut.Login;
import com.cromasoft.cromaflow.model.aut.Role;
import com.cromasoft.cromaflow.model.aut.User;
import com.cromasoft.cromaflow.model.aut.UserProfileImage;
import com.cromasoft.cromaflow.model.aut.UserRole;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Clase que contiene todos las propiedades UsersBusiness.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 11/09/2021
 */
public class UsersBusiness extends BaseBusiness
{
	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(UsersBusiness.class);

	/**
	 * Login.
	 *
	 * @param as_userId de as user id
	 * @param as_password de as password
	 * @return el valor de login
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public synchronized Login login(String as_userId, String as_password)
	    throws B2BException
	{
		DAOManager ldm_manager;
		Login      ll_loginData;

		ldm_manager      = DaoManagerFactory.getDAOManager();
		ll_loginData     = null;

		try
		{
			User lu_user;

			lu_user = DaoCreator.getUserDAO(ldm_manager).findById(as_userId);

			if(lu_user == null)
				throw new B2BException("Usuario no existe");
			else if(BooleanUtils.getBooleanValue(lu_user.getActive()))
			{
				String ls_password;

				ls_password = lu_user.getPassword();

				if(StringUtils.isValidString(ls_password))
				{
					if(!as_password.equals(ls_password))
						throw new B2BException(
						    "Las credenciales ingresadas son incorrectas, por favor verifique e intente nuevamente"
						);
					else
					{
						ll_loginData = new Login();

						ll_loginData.setUser(lu_user);
						ll_loginData.setUserProfileImage(findUserProfilePhoto(as_userId, ldm_manager));
						ll_loginData.setUserRoles(findUserRoles(as_userId, ldm_manager));
					}
				}
				else
					throw new B2BException("El usuario no posee una clave :(");
			}
			else
				throw new B2BException("Usuario esta inactivo");
		}
		catch(B2BException lb2be_e)
		{
			ldm_manager.setRollbackOnly();

			clh_LOGGER.error("findUserById", lb2be_e);

			throw lb2be_e;
		}
		finally
		{
			ldm_manager.close();
		}

		return ll_loginData;
	}

	/**
	 * Find user profile photo.
	 *
	 * @param as_userId de as user id
	 * @return el valor de byte[]
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	private synchronized byte[] findUserProfilePhoto(String as_userId, DAOManager adm_manager)
	    throws B2BException
	{
		byte[] lba_image;

		lba_image = null;

		try
		{
			UserProfileImage lu_user;

			lu_user = DaoCreator.getUserProfileImageDAO(adm_manager).findByUserId(as_userId);

			if(lu_user != null)
			{
				int li_imageId;

				li_imageId = lu_user.getIdImage();

				if(li_imageId > 0)
				{
					Image li_image;

					li_image = DaoCreator.getImageDAO(adm_manager).findById(li_imageId);

					if(li_image != null)
						lba_image = li_image.getData();
				}
			}
		}
		catch(B2BException lb2be_e)
		{
			clh_LOGGER.error("findUserProfilePhoto", lb2be_e);

			throw lb2be_e;
		}

		return lba_image;
	}

	private synchronized Collection<Role> findUserRoles(String as_userId, DAOManager adm_manager)
	    throws B2BException
	{
		Collection<Role> lcr_userRolesLogin;

		lcr_userRolesLogin = null;

		try
		{
			Collection<UserRole> lcr_userRoles;

			lcr_userRoles = DaoCreator.getUserRoleDAO(adm_manager).findByUserId(as_userId);

			if(!CollectionUtils.isValidCollection(lcr_userRoles))
				throw new B2BException("Usuario no posee roles");
			else
			{
				RoleDAO lrd_DAO;

				lcr_userRolesLogin     = new ArrayList<Role>(1);
				lrd_DAO                = DaoCreator.getRoleDAO(adm_manager);

				for(UserRole lur_role : lcr_userRoles)
				{
					if(lur_role != null)
					{
						Role lr_role;

						lr_role = lrd_DAO.findById(lur_role.getRoleId());

						if(lr_role != null)
							lcr_userRolesLogin.add(lr_role);
					}
				}
			}
		}
		catch(B2BException lb2be_e)
		{
			clh_LOGGER.error("findUserRoles", lb2be_e);

			throw lb2be_e;
		}

		return lcr_userRolesLogin;
	}
}

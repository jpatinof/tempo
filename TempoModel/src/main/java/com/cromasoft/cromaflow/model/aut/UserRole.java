package com.cromasoft.cromaflow.model.aut;

import com.cromasoft.cromaflow.model.core.Auditoria;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * Clase que contiene todos las propiedades User.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 9/09/2021
 */
public class UserRole extends Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = -5325646860057959253L;

	/** Propiedad is active. */
	private String is_active;

	/** Propiedad is user id. */
	private String is_userId;

	/** Propiedad ii role id. */
	private int ii_roleId;

	/** Propiedad ii user role id. */
	private int ii_userRoleId;

	/**
	 * Retorna Objeto o variable de valor user role id.
	 *
	 * @return el valor de user role id
	 */
	public int getUserRoleId()
	{
		return ii_userRoleId;
	}

	/**
	 * Modifica el valor de UserRoleId.
	 *
	 * @param ai_i de ai i
	 */
	public void setUserRoleId(int ai_i)
	{
		ii_userRoleId = ai_i;
	}

	/**
	 * Retorna Objeto o variable de valor role id.
	 *
	 * @return el valor de role id
	 */
	public int getRoleId()
	{
		return ii_roleId;
	}

	/**
	 * Modifica el valor de RoleId.
	 *
	 * @param ai_i de ai i
	 */
	public void setRoleId(int ai_i)
	{
		ii_roleId = ai_i;
	}

	/**
	 * Retorna Objeto o variable de valor active.
	 *
	 * @return el valor de active
	 */
	public String getActive()
	{
		return is_active;
	}

	/**
	 * Modifica el valor de Active.
	 *
	 * @param as_s de as s
	 */
	public void setActive(String as_s)
	{
		is_active = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor user id.
	 *
	 * @return el valor de user id
	 */
	public String getUserId()
	{
		return is_userId;
	}

	/**
	 * Modifica el valor de UserId.
	 *
	 * @param as_s de as s
	 */
	public void setUserId(String as_s)
	{
		is_userId = as_s;
	}
}

package com.cromasoft.cromaflow.model.aut;

import com.cromasoft.cromaflow.model.core.Auditoria;

import java.io.Serializable;

import java.util.Collection;


/**
 * Clase que contiene todos las propiedades User.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 9/09/2021
 */
public class Login extends Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = -4763773543448133L;

	/** Propiedad icr user roles. */
	private Collection<Role> icr_userRoles;

	/** Propiedad ir role. */
	private Role ir_role;

	/** Propiedad iu user. */
	private User iu_user;

	/** Propiedad iba user profile image. */
	private byte[] iba_userProfileImage;

	/**
	 * Retorna Objeto o variable de valor user.
	 *
	 * @return el valor de user
	 */
	public User getUser()
	{
		return iu_user;
	}

	/**
	 * Modifica el valor de User.
	 *
	 * @param au_u de au u
	 */
	public void setUser(User au_u)
	{
		iu_user = au_u;
	}

	/**
	 * Retorna Objeto o variable de valor role.
	 *
	 * @return el valor de role
	 */
	public Role getRole()
	{
		return ir_role;
	}

	/**
	 * Modifica el valor de Role.
	 *
	 * @param ar_r de ar r
	 */
	public void setRole(Role ar_r)
	{
		ir_role = ar_r;
	}

	/**
	 * Retorna Objeto o variable de valor user profile image.
	 *
	 * @return el valor de user profile image
	 */
	public byte[] getUserProfileImage()
	{
		return iba_userProfileImage;
	}

	/**
	 * Modifica el valor de UserProfileImage.
	 *
	 * @param aba_ba de aba ba
	 */
	public void setUserProfileImage(byte[] aba_ba)
	{
		iba_userProfileImage = aba_ba;
	}

	/**
	 * Retorna Objeto o variable de valor user roles.
	 *
	 * @return el valor de user roles
	 */
	public Collection<Role> getUserRoles()
	{
		return icr_userRoles;
	}

	/**
	 * Modifica el valor de UserRoles.
	 *
	 * @param acr_cr de acr cr
	 */
	public void setUserRoles(Collection<Role> acr_cr)
	{
		icr_userRoles = acr_cr;
	}
}

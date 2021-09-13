package com.cromasoft.cromaflow.model.aut;

import com.cromasoft.cromaflow.model.core.Auditoria;

import java.io.Serializable;


/**
 * Clase que contiene todos las propiedades UserProfileImage.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 9/09/2021
 */
public class UserProfileImage extends Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 5676330423306366155L;

	/** Propiedad is user id. */
	private String is_userId;

	/** Propiedad ii id image. */
	private int ii_idImage;

	/**
	 * Constructor por defecto.
	 */
	public UserProfileImage()
	{
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
	 * @param as_s de is user id
	 */
	public void setUserId(String as_s)
	{
		is_userId = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor id image.
	 *
	 * @return el valor de id image
	 */
	public int getIdImage()
	{
		return ii_idImage;
	}

	/**
	 * Modifica el valor de IdImage.
	 *
	 * @param ai_i de ai i
	 */
	public void setIdImage(int ai_i)
	{
		ii_idImage = ai_i;
	}
}

package com.cromasoft.cromaflow.model.aut;

import com.cromasoft.cromaflow.model.core.Auditoria;

import java.io.Serializable;


/**
 * Clase que contiene todos las propiedades UserProfileImage.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 9/09/2021
 */
public class Image extends Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 5676330423306366155L;

	/** Propiedad iba data. */
	private byte[] iba_data;

	/** Propiedad ii id image. */
	private int ii_idImage;

	/**
	 * Constructor por defecto.
	 */
	public Image()
	{
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

	/**
	 * Retorna Objeto o variable de valor data.
	 *
	 * @return el valor de data
	 */
	public byte[] getData()
	{
		return iba_data;
	}

	/**
	 * Modifica el valor de Data.
	 *
	 * @param aba_data de aba data
	 */
	public void setData(byte[] aba_data)
	{
		iba_data = aba_data;
	}
}

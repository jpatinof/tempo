package com.cromasoft.cromaflow.model.aut;

import com.cromasoft.cromaflow.model.core.Auditoria;

import java.io.Serializable;


/**
 * Clase que contiene todos las propiedades Role.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 11/09/2021
 */
public class Role extends Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 4011096668809673135L;

	/** Propiedad is active. */
	private String is_active;

	/** Propiedad is description. */
	private String is_description;

	/** Propiedad ii role id. */
	private int ii_roleId;

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
	 * Retorna Objeto o variable de valor description.
	 *
	 * @return el valor de description
	 */
	public String getDescription()
	{
		return is_description;
	}

	/**
	 * Modifica el valor de Description.
	 *
	 * @param as_i de as i
	 */
	public void setDescription(String as_i)
	{
		is_description = as_i;
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
}

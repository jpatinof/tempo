package com.cromasoft.cromaflow.model.aut;

import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.constants.IdentificadoresCommon;

import com.cromasoft.cromaflow.model.core.Auditoria;

import java.io.Serializable;


/**
 * Clase que contiene todos las propiedades User.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 9/09/2021
 */
public class User extends Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 7424062662342101313L;

	/** Propiedad is activo. */
	private String is_active;

	/** Propiedad is id documento tipo. */
	private String is_documentType;

	/** Propiedad is primer apellido. */
	private String is_firstLastName;

	/** Propiedad is primer nombre. */
	private String is_firstName;

	/** Propiedad is gender. */
	private String is_gender;

	/** Propiedad is password. */
	private String is_password;

	/** Propiedad is segundo apellido. */
	private String is_secondLastName;

	/** Propiedad is segundo nombre. */
	private String is_secondName;

	/** Propiedad is id usuario. */
	private String is_userId;

	/** Propiedad ii id number. */
	private int ii_idNumber;

	/**
	 * Constructor por defecto.
	 */
	public User()
	{
	}

	/**
	 * Retorna Objeto o variable de valor nombre completo.
	 *
	 * @return el valor de nombre completo
	 */
	public String getNombreCompleto()
	{
		StringBuilder lsb_builder;

		lsb_builder = new StringBuilder(StringUtils.getStringNotNull(is_firstName));

		lsb_builder.append(IdentificadoresCommon.ESPACIO_VACIO);
		lsb_builder.append(StringUtils.getStringNotNull(is_secondName));
		lsb_builder.append(IdentificadoresCommon.ESPACIO_VACIO);
		lsb_builder.append(StringUtils.getStringNotNull(is_firstLastName));
		lsb_builder.append(IdentificadoresCommon.ESPACIO_VACIO);
		lsb_builder.append(StringUtils.getStringNotNull(is_secondLastName));

		return lsb_builder.toString();
	}

	/**
	 * Retorna Objeto o variable de valor first last name.
	 *
	 * @return el valor de first last name
	 */
	public String getFirstLastName()
	{
		return is_firstLastName;
	}

	/**
	 * Modifica el valor de FirstLastName.
	 *
	 * @param is_firstLastName de is first last name
	 */
	public void setFirstLastName(String is_firstLastName)
	{
		this.is_firstLastName = is_firstLastName;
	}

	/**
	 * Retorna Objeto o variable de valor document type.
	 *
	 * @return el valor de document type
	 */
	public String getDocumentType()
	{
		return is_documentType;
	}

	/**
	 * Modifica el valor de DocumentType.
	 *
	 * @param is_documentType de is document type
	 */
	public void setDocumentType(String is_documentType)
	{
		this.is_documentType = is_documentType;
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
	 * @param is_active de is active
	 */
	public void setActive(String is_active)
	{
		this.is_active = is_active;
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
	 * @param is_userId de is user id
	 */
	public void setUserId(String is_userId)
	{
		this.is_userId = is_userId;
	}

	/**
	 * Retorna Objeto o variable de valor id number.
	 *
	 * @return el valor de id number
	 */
	public int getIdNumber()
	{
		return ii_idNumber;
	}

	/**
	 * Modifica el valor de IdNumber.
	 *
	 * @param ai_i de ai i
	 */
	public void setIdNumber(int ai_i)
	{
		this.ii_idNumber = ai_i;
	}

	/**
	 * Retorna Objeto o variable de valor first name.
	 *
	 * @return el valor de first name
	 */
	public String getFirstName()
	{
		return is_firstName;
	}

	/**
	 * Modifica el valor de FirstName.
	 *
	 * @param is_firstName de is first name
	 */
	public void setFirstName(String is_firstName)
	{
		this.is_firstName = is_firstName;
	}

	/**
	 * Retorna Objeto o variable de valor second last name.
	 *
	 * @return el valor de second last name
	 */
	public String getSecondLastName()
	{
		return is_secondLastName;
	}

	/**
	 * Modifica el valor de SecondLastName.
	 *
	 * @param is_secondLastName de is second last name
	 */
	public void setSecondLastName(String is_secondLastName)
	{
		this.is_secondLastName = is_secondLastName;
	}

	/**
	 * Retorna Objeto o variable de valor second name.
	 *
	 * @return el valor de second name
	 */
	public String getSecondName()
	{
		return is_secondName;
	}

	/**
	 * Modifica el valor de SecondName.
	 *
	 * @param is_secondName de is second name
	 */
	public void setSecondName(String is_secondName)
	{
		this.is_secondName = is_secondName;
	}

	/**
	 * Retorna Objeto o variable de valor password.
	 *
	 * @return el valor de password
	 */
	public String getPassword()
	{
		return is_password;
	}

	/**
	 * Modifica el valor de Password.
	 *
	 * @param as_s de as s
	 */
	public void setPassword(String as_s)
	{
		this.is_password = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor gender.
	 *
	 * @return el valor de gender
	 */
	public String getGender()
	{
		return is_gender;
	}

	/**
	 * Modifica el valor de Gender.
	 *
	 * @param as_s de as s
	 */
	public void setGender(String as_s)
	{
		is_gender = as_s;
	}
}

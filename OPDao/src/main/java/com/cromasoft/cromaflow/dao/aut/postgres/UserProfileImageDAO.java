package com.cromasoft.cromaflow.dao.aut.postgres;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla TEMPOAPP.USERS.
 *
 */
public class UserProfileImageDAO extends com.cromasoft.cromaflow.dao.aut.UserProfileImageDAO
{
	/** Constante cs_FIND_BY_ID. */
	private static final String cs_FIND_BY_USER_ID = "SELECT * FROM APP.USER_PROFILE_IMAGE WHERE USER_ID = ? AND ACTIVE = 'S'";

	@Override
	public String csFindByUserId()
	{
		return cs_FIND_BY_USER_ID;
	}
}

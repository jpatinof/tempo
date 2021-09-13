package com.cromasoft.cromaflow.dao.aut.postgres;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla TEMPOAPP.USERS.
 *
 */
public class UserRoleDAO extends com.cromasoft.cromaflow.dao.aut.UserRoleDAO
{
	/** Constante cs_FIND_BY_ID. */
	private static final String cs_FIND_BY_ID = "SELECT * FROM APP.USER_ROLE WHERE USER_ID = ? AND ACTIVE = 'S'";

	@Override
	public String csFindByUserId()
	{
		return cs_FIND_BY_ID;
	}
}

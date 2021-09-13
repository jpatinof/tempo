package com.cromasoft.cromaflow.dao.aut.postgres;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla TEMPOAPP.USERS.
 *
 */
public class UserDAO extends com.cromasoft.cromaflow.dao.aut.UserDAO
{
	/** Constante cs_FIND_BY_ID. */
	private static final String cs_FIND_BY_ID = "SELECT * FROM APP.USER WHERE USER_ID = ?";

	@Override
	public String csFindById()
	{
		return cs_FIND_BY_ID;
	}
}

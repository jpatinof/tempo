package com.cromasoft.cromaflow.dao.aut.postgres;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla TEMPOAPP.USERS.
 *
 */
public class RoleDAO extends com.cromasoft.cromaflow.dao.aut.RoleDAO
{
	/** Constante cs_FIND_BY_ID. */
	private static final String cs_FIND_BY_ID = "SELECT * FROM APP.ROLE WHERE ROLE_ID = ? AND ACTIVE = 'S'";

	@Override
	public String csFindById()
	{
		return cs_FIND_BY_ID;
	}
}

package com.cromasoft.cromaflow.dao.aut.postgres;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla TEMPOAPP.USERS.
 *
 */
public class ImageDAO extends com.cromasoft.cromaflow.dao.aut.ImageDAO
{
	/** Constante cs_FIND_BY_ID. */
	private static final String cs_FIND_BY_ID = "SELECT * FROM APP.IMAGE WHERE IMAGE_ID = ? AND ACTIVE = 'S'";

	@Override
	public String csFindById()
	{
		return cs_FIND_BY_ID;
	}
}

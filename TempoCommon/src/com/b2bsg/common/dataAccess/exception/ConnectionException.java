package com.b2bsg.common.dataAccess.exception;

import java.sql.SQLException;


/**
* Excepci�n que debe ser lanzada al encontrar cualquier error en la obtenci�n de una conexi�n a una
* fuente de datos.
*
* @author Edgar Prieto
* @version Sep 05, 2004
*/
public class ConnectionException extends SQLException
{
	private static final long serialVersionUID = 1L;

	/** Crea una nueva excepci�n de conexi�n a una fuente de datos. */
	public ConnectionException()
	{
		super();
	}

	/**
	* Crea una nueva excepci�n de conexi�n a una fuente de datos.
	*
	* @param as_msg Mensaje de error de la excepci�n.
	*/
	public ConnectionException(String as_msg)
	{
		super(as_msg);
	}
}

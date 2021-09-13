package com.b2bsg.common.dataAccess;

import com.b2bsg.common.dataAccess.exception.ConnectionException;

import com.b2bsg.common.logging.LoggerHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase de utilidades para de acceso a base de datos
 *
 * @author Edgar Prieto
 * @version Sep 05, 2004
 */
public class DaoFactory
{
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(DaoFactory.class);

	/**
	 * Obtener una conexión a una fuente de datos a través de una URL JDBC, utilizando un conector
	 * JDBC arbitrario, autenticandosé a traves de un usuario y clave especifico.
	 *
	 * @param as_driver        Conector jdbc a utilizar.
	 * @param as_url            URL de conexión a utilizar.
	 * @param as_user        Usuario de la fuente de datos a utilizar.
	 * @param as_password    Clave del usuario de la fuente de datos.
	 * @return    <code>java.sql.Connection</code> a la fuente de datos especificada por el
	 *            <i>as_url</i>, utilizando el conector JDBC especificado en <i>as_driver</i>.
	 * @throws    ConnectionException si no se pudo obtener una conexión a la fuente de datos.
	 */
	public static Connection getConnection(String as_driver, String as_url, String as_user, String as_password)
	    throws ConnectionException
	{
		try
		{
			/* Cargar el conector JDBC especificado */
			Class.forName(as_driver);

			/* Obtener la conexión a la fuente de datos */
			return DriverManager.getConnection(as_url, as_user, as_password);
		}
		catch(ClassNotFoundException lcnfe_e)
		{
			/* No se pudo encontrar el conector JDBC especificado */
			clh_LOGGER.error("getConnection", lcnfe_e);

			throw new ConnectionException(lcnfe_e.getMessage());
		}
		catch(SQLException lse_e)
		{
			/* Error al obtener una conexión a la fuente de datos */
			String ls_message;

			ls_message = null;

			{
				Exception le_cause;

				le_cause = lse_e.getNextException();

				if(le_cause != null)
				{
					ls_message = le_cause.getMessage();

					clh_LOGGER.error("getConnection", le_cause);
				}
			}

			if(ls_message == null)
			{
				Throwable lt_cause;

				lt_cause = lse_e.getCause();

				if(lt_cause != null)
				{
					ls_message = lt_cause.getMessage();

					clh_LOGGER.error("getConnection", lt_cause);
				}
			}

			if(ls_message == null)
			{
				ls_message = lse_e.getMessage();

				clh_LOGGER.error("getConnection", lse_e);
			}

			if(ls_message == null)
				ls_message = new String();
			else
				ls_message = ls_message.trim();

			throw new ConnectionException(ls_message);
		}
	}

	/**
	 * Cierra un objeto  <code>java.sql.PreparedStatement</code> y lo libera de la memoria
	 *
	 * @param as_s <code>java.sql.Statement</code> a cerrar
	 *
	 * @throws <code>java.sql.SQLException</code> si se presenta un error en la base de datos
	 */
	public static void close(java.sql.Statement as_s)
	    throws SQLException
	{
		if(as_s != null)
			as_s.close();
	}

	/**
	 * Cierra un objeto  <code>java.sql.ResultSet</code> y lo libera de la memoria
	 *
	 * @param ars_rs <code>java.sql.ResultSet</code> a cerrar
	 *
	 * @throws <code>java.sql.SQLException</code> si se presenta un error en la base de datos
	 */
	public static void close(java.sql.ResultSet ars_rs)
	    throws SQLException
	{
		if(ars_rs != null)
			ars_rs.close();
	}
}

package com.b2bsg.common.dataAccess.oracle;

import java.sql.SQLException;


/**
 * Clase que convierte de string a clob oracle y viceversa
 *
 * @author Luis Fernando Garzón
 * @22/03/2007
 */
public class ClobUtils
{
	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param ac_clob correspondiente al valor del tipo de objeto Clob
	 * @return devuelve el valor de String
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public static String clobToString(java.sql.Clob ac_clob)
	    throws SQLException
	{
		return (ac_clob != null) ? ac_clob.getSubString(1, (int)ac_clob.length()) : "";
	}
	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param as_string correspondiente al valor del tipo de objeto String
	 * @param ac_clob correspondiente al valor del tipo de objeto Clob
	 * @return devuelve el valor de CLOB
	 * @throws SQLException Señala que se ha producido una excepción
	 */
	public static java.sql.Clob stringToClob(String as_string, java.sql.Clob ac_clob)
		    throws SQLException
		{
		

		ac_clob.setString(1, as_string);

			return ac_clob;
		}
}

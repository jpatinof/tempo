package com.b2bsg.common.util;

import java.math.BigInteger;


/**
* Clase de utilidades para el manejo de objetos <code>java.lang.Boolean</code>
* @author Edgar Prieto
*/
public class BooleanUtils
{
	/**
	 * Obtiene la representación boolean de una cadena de caracteres
	 *
	 * @param as_s Cadena sobre la cual se evaluara la representación boolean
	 *
	 * @return true si la cadena tiene los valores (sin importar mayusculas o minusculas) on, s, si, true, y, yes 1.
	 * false de lo contrario
	 */
	public static Boolean getBoolean(String as_s)
	{
		Boolean lb_b;

		if(StringUtils.isValidString(as_s))
		{
			String ls_s;

			ls_s     = as_s.toLowerCase();

			lb_b = new Boolean(
				    ls_s.equals("on") || ls_s.equals("s") || ls_s.equals("si") || ls_s.equals("true")
					    || ls_s.equals("y") || ls_s.equals("yes") || ls_s.equals("1")
				);
		}
		else
			lb_b = null;

		return lb_b;
	}

	/**
	* Obtener el valor la representación <code>boolean</code> de un objeto
	* <code>java.lang.Boolean</code>.
	* @param ab_b    <code>java.lang.Boolean</code> representado una booleano.
	* @return        <code>false</code> si ab_b es <code>null</code> ó el valor de ab_b es <false>.
	*                <code>true</code> de lo contrario.
	* @author Edgar Prieto
	*/
	public static boolean getBooleanValue(Boolean ab_b)
	{
		return (ab_b != null) ? ab_b.booleanValue() : false;
	}

	/**
	* Obtener el valor la representación <code>boolean</code> de un objeto
	* <code>java.math.BigInteger</code>.
	* @param abi_bi    <code>java.math.BigInteger</code> representado una BigInteger.
	* @return        <code>false</code> si abi_bi es <code>null</code> ó el valor de abi_bi es <false>.
	*                <code>true</code> de lo contrario.
	* @author Edgar Prieto
	*/
	public static boolean getBooleanValue(BigInteger abi_bi)
	{
		return (abi_bi != null) ? getBooleanValue(abi_bi.intValue()) : false;
	}

	/**
	* Obtener el valor la representación <code>boolean</code> de un tipo primitivo <code>int</code>.
	* @param ai_i    <code>int</code> sobre el que se quiere obtener la representación binarias
	* @return        <code>false</code> si el valor de abi_bi es 0. <code>true</code> de lo contrario.
	* @author Edgar Prieto
	*/
	public static boolean getBooleanValue(int ai_i)
	{
		return (ai_i == 0) ? false : true;
	}

	/**
	* Obtener el valor la representación <code>boolean</code> de un objeto
	* <code>java.lang.String</code>.
	* @param as_s    <code>java.lang.String</code> representado un valor booleano.
	* @return        <code>true</code> si as_s no es <code>null</code> y es igual, sin importar
	*                mayúsculas o minúsculas, a <code>1</code>, <code>yes</code>, <code>true</code>
	*                ó <code>on</code>. <code>false</code> de lo contrario
	* @author Edgar Prieto
	*/
	public static boolean getBooleanValue(String as_s)
	{
		Boolean lb_b;

		lb_b = getBoolean(as_s);

		return (lb_b != null) ? lb_b.booleanValue() : false;
	}
}

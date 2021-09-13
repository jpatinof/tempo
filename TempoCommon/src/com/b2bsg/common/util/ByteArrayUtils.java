package com.b2bsg.common.util;


/**
 * Clase que contiene todos las propiedades ByteArrayUtils.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 18/03/2020
 */
public class ByteArrayUtils
{
	/**
	 * Valida que el <code>byte[]</code> sea válido.
	 *
	 * @param aba_array <code>byte[]</code> a validar
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code>.
	 */
	public static boolean isValidArray(byte[] aba_array)
	{
		return (aba_array != null) && (aba_array.length > 0);
	}
}
